package fr.nblet.plantcollection.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.nblet.plantcollection.MainActivity
import fr.nblet.plantcollection.PlantModel
import fr.nblet.plantcollection.PlantPopup
import fr.nblet.plantcollection.PlantRepository
import fr.nblet.plantcollection.R

class PlantAdapter(
    val context: MainActivity,
    private val plantList: List<PlantModel>,
    private val layoutId: Int
) : RecyclerView.Adapter<PlantAdapter.ViewHolder>(){

    // Boîte pour stocker les composants à controler
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val plantImage = view.findViewById<ImageView>(R.id.image_item)
        val plantName:TextView? = view.findViewById(R.id.name_item)
        val plantDescription:TextView? =  view.findViewById(R.id.description_item)
        val starIcon = view.findViewById<ImageView>(R.id.star_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater
            .from(parent.context)
            .inflate(layoutId, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Récupération des informations de la plante
        val currentPlant = plantList[position]

        // Récupération du repository
        val repo = PlantRepository()

        //Utilisation de glide pour récupérer une image à partir d'un lien
        Glide.with(context).load(Uri.parse(currentPlant.imageUrl)).into(holder.plantImage)

        //MAJ des infos de la plante
        holder.plantName?.text = currentPlant.name
        holder.plantDescription?.text = currentPlant.description

        // Vérification si la plante a été likée
        if(currentPlant.liked){
            holder.starIcon.setImageResource(R.drawable.ic_star)
        }else{
            holder.starIcon.setImageResource(R.drawable.ic_unstar)
        }

        // Rajout d'une interaction sur l'étoile
        holder.starIcon.setOnClickListener{
            // Inversion du like
            currentPlant.liked = !currentPlant.liked
            // MAJ l'objet plante
            repo.updatePlant(currentPlant)
        }

        // Intéraction sur le clic d'une plante
        holder.itemView.setOnClickListener{
            //affichage de la popup
            PlantPopup(this, currentPlant).show()
        }

    }

    override fun getItemCount(): Int = plantList.size

}