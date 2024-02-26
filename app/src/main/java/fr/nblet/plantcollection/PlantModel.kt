package fr.nblet.plantcollection

class PlantModel(
    val id: String = "plant0",
    val name: String = "Nom de la plante",
    val description: String = "Petite description",
    val imageUrl: String = "http://graven.yt/plante.jpg",
    var grow: String = "Faible",
    var water: String = "Moyenne",
    var liked: Boolean = false
)