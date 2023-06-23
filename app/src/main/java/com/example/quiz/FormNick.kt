package com.example.quiz

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import com.example.quiz.databinding.ActivityFormLoginBinding
import com.example.quiz.databinding.ActivityFormNickBinding
import com.google.android.gms.common.util.UidVerifier
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore

class FormNick : AppCompatActivity() {

    private lateinit var binding: ActivityFormNickBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormNickBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.botaoCadastrarNick.setOnClickListener {view ->
            val usuarioAtual = FirebaseAuth.getInstance().currentUser.toString()
            val nick = binding.EditNick.text.toString()

            val UID = FirebaseAuth.getInstance().currentUser?.uid.toString()

            if(nick.isEmpty()){
                val snackbar = Snackbar.make(view, "Preencha todos os campos!", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }else{
                val usuariosMap = hashMapOf(
                    "Nick" to nick,
                    "Pontuação" to 0
                )

                db.collection("Usuários").document(UID).set(usuariosMap).addOnCompleteListener{
                    Log.d("db","foi")
                }
                val intent = Intent(this, SelectQuiz::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

}