package com.example.quiz

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.quiz.R
import com.example.quiz.databinding.ActivityFormCadastroBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class FormCadastro : AppCompatActivity() {

    private lateinit var binding: ActivityFormCadastroBinding
    private val auth = FirebaseAuth.getInstance()
    private var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtTelaLogin.setOnClickListener{
            val intent = Intent(this,FormLogin::class.java)
            startActivity(intent)
        }

        binding.botaoCadastrar.setOnClickListener{view ->
            val email = binding.EditEmail.text.toString()
            val senha = binding.EditSenha.text.toString()


            if(email.isEmpty() || senha.isEmpty()){
                val snackbar = Snackbar.make(view, "Preencha todos os campos!", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }else{
                auth.createUserWithEmailAndPassword(email,senha).addOnCompleteListener { cadastro ->
                    if(cadastro.isSuccessful){
                        val snackbar = Snackbar.make(view, "Sucesso ao cadastrar usuário", Snackbar.LENGTH_SHORT)
                        snackbar.setBackgroundTint(Color.GREEN)
                        snackbar.show()
                        binding.EditEmail.setText("")
                        binding.EditSenha.setText("")
                        val intent = Intent(this,FormNick::class.java)
                        startActivity(intent)
                        finish()
                    }
                }.addOnFailureListener{exception ->
                    val mensagemErro = when(exception){
                        is FirebaseAuthWeakPasswordException -> "Digite uma senha com no mínimo 6 caracteres"
                        is FirebaseAuthInvalidCredentialsException -> "Digite um e-mail válido"
                        is FirebaseAuthUserCollisionException -> "Esta conta já foi cadastrada"
                        is FirebaseNetworkException -> "Sem conexão com a internet"
                        else -> exception.toString()
                    }
                    val snackbar = Snackbar.make(view,mensagemErro, Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.show()
                }
            }
        }
    }
}