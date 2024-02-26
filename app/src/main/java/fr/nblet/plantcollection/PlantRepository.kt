package fr.nblet.plantcollection

import android.net.Uri
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import fr.nblet.plantcollection.PlantRepository.Singleton.databaseRef
import fr.nblet.plantcollection.PlantRepository.Singleton.downloadUri
import fr.nblet.plantcollection.PlantRepository.Singleton.plantList
import fr.nblet.plantcollection.PlantRepository.Singleton.storageReference
import java.util.UUID

class PlantRepository {

    object Singleton{
        // Lien pour accéder au bucket
        private val BUCKET_URL: String = "gs://plant-collection-3974b.appspot.com"

        // Connection base de stockage
        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(BUCKET_URL)

        // Connexion à la référence "plants"
        val databaseRef = FirebaseDatabase.getInstance().getReference("plants")

        // créer une liste de plantes
        val plantList = arrayListOf<PlantModel>()

        // Contient le lien de l'image courant
        var downloadUri: Uri? = null
    }

    fun updateData(callback: () -> Unit){
        // Absorption des données depuis la databaseRef -> liste de plantes
        databaseRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                // Retirer les anciennes plantes
                plantList.clear()

                // Récolter la liste
                for(ds in snapshot.children){
                    // Construction d'un objet plante
                    val plant = ds.getValue(PlantModel::class.java)

                    // Vérification que la plante n'est pas null
                    if (plant != null){
                        // Ajout de la plante à notre liste
                        plantList.add(plant)
                    }
                }
                // Action du callback
                callback()
            }

            override fun onCancelled(error: DatabaseError) {}

        })
    }

    // Envoi de fichier sur le storage
    fun uploadImage(file: Uri, callback: () -> Unit){
        // Vérification si le fichier non null
        if (file != null){
            val fileName = UUID.randomUUID().toString() + ".jpg"
            val ref = storageReference.child(fileName)
            val uploadTask = ref.putFile(file)

            // Démarrage de l'envoi
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>>{ task ->
                // Vérification de l'envoi du fichier
                if(!task.isSuccessful)
                    task.exception?.let { throw it }

                return@Continuation ref.downloadUrl

            }).addOnCompleteListener { task ->
                // Vérification de l'état
                if (task.isSuccessful){
                    // Récupération de l'image
                    downloadUri = task.result
                    callback()
                }
            }
        }

    }

    // MAJ objet plante en BDD
    fun updatePlant(plant: PlantModel)=databaseRef.child(plant.id).setValue(plant)

    // Insertion nouvelle plante en BDD
    fun insertPlant(plant: PlantModel)=databaseRef.child(plant.id).setValue(plant)

    // Suppression d'une plante de la BDD
    fun deletePlant(plant: PlantModel) = databaseRef.child(plant.id).removeValue()


}