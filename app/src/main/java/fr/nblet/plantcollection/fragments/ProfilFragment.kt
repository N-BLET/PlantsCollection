package fr.nblet.plantcollection.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import fr.nblet.plantcollection.MainActivity
import fr.nblet.plantcollection.R


class ProfilFragment (
    private val context: MainActivity
):Fragment(){

    private lateinit var auth: FirebaseAuth
    private lateinit var db: Firebase
    private var currentUser: FirebaseUser? = null
    private lateinit var imgUser: ShapeableImageView
    private lateinit var nameUser: EditText
    private lateinit var password: EditText
    private lateinit var email:EditText
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view = inflater.inflate(R.layout.fragment_profil, container, false)
            auth = Firebase.auth
//            db = Firebase.firestore
            currentUser = auth.currentUser
//            val submitSave = view.findViewById<Button>(R.id.submit_connexion)
//             submitSave.setOnClickListener {
//
//             }

            return view
        }
    }
