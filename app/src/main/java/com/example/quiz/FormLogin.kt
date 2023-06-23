package com.example.quiz

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quiz.databinding.ActivityFormLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class FormLogin : AppCompatActivity() {

    private lateinit var binding: ActivityFormLoginBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.botaoLogin.setOnClickListener{view ->
            val email = binding.EditEmail.text.toString()
            val senha = binding.EditSenha.text.toString()

            if(email.isEmpty() || senha.isEmpty()){
                val snackbar = Snackbar.make(view, "Preencha todos os campos!", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }
            else{
                auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener{autentication ->
                    if(autentication.isSuccessful){
                        navegarTelaPrincipal()
                    }
                }.addOnFailureListener { exception ->
                    val mensagemErro = when (exception) {
                        is FirebaseAuthWeakPasswordException -> "Digite uma senha com no mínimo 6 caracteres"
                        is FirebaseAuthInvalidCredentialsException -> "Digite um e-mail válido"
                        is FirebaseNetworkException -> "Sem conexão com a internet"
                        else -> exception.toString()
                    }

                }
            }
        }
        binding.txtTelaCadastro.setOnClickListener{
            val intent = Intent(this,FormCadastro::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun navegarTelaPrincipal(){
        val intent = Intent(this, SelectQuiz::class.java)
        startActivity(intent)
        finish()
    }

    override fun onStart() {
        super.onStart()

        val usuarioAtual = FirebaseAuth.getInstance().currentUser
        if (usuarioAtual != null){
            navegarTelaPrincipal()
        }
    }
}