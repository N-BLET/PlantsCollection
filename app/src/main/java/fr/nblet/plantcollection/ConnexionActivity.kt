package fr.nblet.plantcollection

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.helper.widget.MotionEffect.TAG
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.firebase.auth.FirebaseAuth

class ConnexionActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var addUserConnexion: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connexion)
        auth = FirebaseAuth.getInstance()
        addUserConnexion = findViewById(R.id.add_user_connexion)


        addUserConnexion.setOnClickListener{
            Intent(this, AddUserActivity::class.java).also { startActivity(it) }
        }

    }

    fun handleLogin(view: View) {
        val textView2 = findViewById<EditText>(R.id.email_connexion)
        val textView3 = findViewById<EditText>(R.id.passwd_connexion)
        val redColor = ResourcesCompat.getColor(resources, R.color.red, null)
        if (findViewById<EditText>(R.id.email_connexion).text.isEmpty()) {
            textView2.hint = "L'email est vide"
            textView2.setHintTextColor(redColor)
        } else if (textView3.text.isEmpty()) {
            textView3.hint = "Veuillez saisir votre mot de passe"
            textView3.setHintTextColor(redColor)
        } else {
            auth.signInWithEmailAndPassword(textView2.text.toString(), textView3.text.toString())
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        // Connexion réussie, afficher un message à l'utilisateur.
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        Toast.makeText(
                            this,
                            "Connexion réussie.",
                            Toast.LENGTH_SHORT
                        ).show()
                        // Rediriger vers l'activité suivante
                        goMainActivity()
                    } else {
                        // En cas d'échec de la connexion, afficher un message à l'utilisateur.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            this,
                            "Échec de l'authentification.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    private fun goMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}