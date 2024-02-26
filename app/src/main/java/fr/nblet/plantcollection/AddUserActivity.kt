package fr.nblet.plantcollection


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.helper.widget.MotionEffect
import androidx.core.content.res.ResourcesCompat
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class AddUserActivity  : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var fullname_add_user : EditText
    private lateinit var email_add_user : EditText
    private lateinit var passwd_add_user : EditText
    private lateinit var confirm_passwd_add_user : EditText


    private lateinit var submitAddUser: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        auth = Firebase.auth

       submitAddUser = findViewById<Button>(R.id.submit_add_user)
    }

    override fun onStart(){
        super.onStart()

        submitAddUser.setOnClickListener{
            val name = findViewById<EditText>(R.id.fullname_add_user).text.toString()
            val email = findViewById<EditText>(R.id.email_add_user).text.toString()
            val password = findViewById<EditText>(R.id.passwd_add_user).text.toString()
            val confirmPassword = findViewById<EditText>(R.id.confirm_passwd_add_user).text.toString()
            val redColor = ResourcesCompat.getColor(resources, R.color.red, null)
            if(name.isEmpty()){
                fullname_add_user.hint = "Renseigner votre nom et prénom"
                fullname_add_user.setHintTextColor(redColor)
            } else if (email.isEmpty()) {
                email_add_user.hint = "L'email est vide"
                email_add_user.setHintTextColor(redColor)
            } else if (password.isEmpty()) {
                passwd_add_user.hint = "Veuillez saisir votre mot de passe"
                passwd_add_user.setHintTextColor(redColor)
            } else if (confirmPassword.isEmpty()
                && findViewById<EditText>(R.id.passwd_add_user).text.toString() != findViewById<EditText>(R.id.confirm_passwd_add_user).text.toString()){
                confirm_passwd_add_user.hint = "Votre confirmation ne correspond pas à votre mot de passe"
                confirm_passwd_add_user.setHintTextColor(redColor)
            } else {
                // Création d'un utilisateur dans le module authentification de firebase
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Création d'un utilisateur
                        val user = hashMapOf(
                            "fullname" to name,
                            "email" to email
                        )

                        val currentUser = auth.currentUser
                        // Création de l'utilisateur dans le module firestore
                        val db = Firebase.firestore
                        db.collection("users").document(currentUser!!.uid).set(user as Map<String, Any>).addOnSuccessListener {
                            // Rediriger vers l'activité connexion
                            Intent(this, ConnexionActivity::class.java).also{
                                startActivity(it)
                                finish()
                            }
                        }.addOnFailureListener {

                        }
                        // Enregistrement du compte réussi, afficher un message à l'utilisateur.
                        Log.d(MotionEffect.TAG, "add_user:success")
                        Toast.makeText(
                            this,
                            "Enregistrement réussi.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else{
                        // En cas d'échec de la connexion, afficher un message à l'utilisateur.
                        Log.w(MotionEffect.TAG, "add_user:failure", task.exception)
                        Toast.makeText(
                            this,
                            "Échec de l'enregistrement.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        }

    }

}