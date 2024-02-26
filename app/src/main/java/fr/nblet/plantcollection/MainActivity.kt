package fr.nblet.plantcollection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.nblet.plantcollection.fragments.AddPlantFragment
import fr.nblet.plantcollection.fragments.CollectionFragment
import fr.nblet.plantcollection.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(HomeFragment(this), R.string.home_page_title)

        // Import de la bottomNavigationView
        val navigationView = findViewById<BottomNavigationView>(R.id.navigation_view)

        navigationView.setOnItemSelectedListener setOnNavigationItemSelectedListener@{
            when(it.itemId) {
                R.id.home_page -> {
                    loadFragment(HomeFragment(this), R.string.home_page_title)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.collection_page -> {
                    loadFragment(CollectionFragment(this), R.string.collection_page_title)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.add_plant_page -> {
                    loadFragment(AddPlantFragment(this), R.string.add_plant_page_title)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.add_user_page -> {
                    loadFragment(AddPlantFragment(this), R.string.profil_page_title)
                    return@setOnNavigationItemSelectedListener true
                }

                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment, string: Int) {
        // Chargement du repository
        val repo = PlantRepository()

        // Actualisation du titre de la page
        findViewById<TextView>(R.id.page_title).text = resources.getString(string)

        //MAJ de la liste de plantes
        repo.updateData{
            // Injecter le fragment dans la boîte (fragment_container)
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}