package fr.nblet.plantcollection.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import fr.nblet.plantcollection.MainActivity
import fr.nblet.plantcollection.PlantModel
import fr.nblet.plantcollection.PlantRepository
import fr.nblet.plantcollection.PlantRepository.Singleton.downloadUri
import fr.nblet.plantcollection.R
import java.util.UUID


class AddPlantFragment(
    private val context: MainActivity
): Fragment() {
    private var file: Uri? = null
    private var uploadedImage:ImageView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       val view = inflater.inflate(R.layout.fragment_add_plant, container, false)

        // Récupération de l'image uploadée pour l'associer au composant
        uploadedImage = view.findViewById(R.id.preview_image)

        //Récupération du bouton pour charger une image
        val pickupImageButton = view.findViewById<Button>(R.id.upload_button)

        // Lors du clic ouverture des images du téléphone
        pickupImageButton.setOnClickListener{pickupImage()}
       return view

        // Récupération du bouton confirmer
        val confirmButton = view.findViewById<Button>(R.id.confirm_button)
        confirmButton.setOnClickListener{ sendForm(view) }
    }

    private fun sendForm(view: View) {
        val repo = PlantRepository()
        repo.uploadImage(file!!){
            val plantName = view.findViewById<EditText>(R.id.name_input).text.toString()
            val plantDescription = view.findViewById<EditText>(R.id.description_input).text.toString()
            val grow = view.findViewById<Spinner>(R.id.grow_spinner).selectedItem.toString()
            val water = view.findViewById<Spinner>(R.id.water_spinner).selectedItem.toString()
            val downloadImageUrl = downloadUri

            // Création d'un nouvel objet PlantModel
            val plant = PlantModel(
                UUID.randomUUID().toString(),
                plantName,
                plantDescription,
                downloadImageUrl.toString(),
                grow,
                water
            )

            // Envoi en BDD
            repo.insertPlant(plant)
        }
    }

    private fun pickupImage() {
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 47)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 47 && resultCode == Activity.RESULT_OK){

            // Vérification des données si null
            if(data == null || data.data == null)return

            // Récupération de l'image
            file = data.data

            // MAJ de l'image
            uploadedImage?.setImageURI(file)
        }
    }

}