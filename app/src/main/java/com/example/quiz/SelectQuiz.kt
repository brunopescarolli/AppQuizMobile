package com.example.quiz

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quiz.databinding.ActivitySelectQuizBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SelectQuiz : AppCompatActivity() {

    private lateinit var binding: ActivitySelectQuizBinding
    private var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val usuarioAtual = FirebaseAuth.getInstance().currentUser?.uid.toString()

        db.collection("Usuários").document(usuarioAtual).addSnapshotListener { value, error ->
            if (value != null){
                binding.nomeUsuario.text = value.getString("Nick")
            }
        }

        binding.botaoComecar.setOnClickListener {
            val intent = Intent(this, MainQuiz::class.java)
            startActivity(intent)
            finish()
        }

        binding.botaoSair.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val voltarLogin = Intent(this, FormLogin::class.java)
            startActivity(voltarLogin)
            finish()
        }

        binding.botaoTabela.setOnClickListener {
            val intent = Intent(this, Tabela::class.java)
            startActivity(intent)
            finish()
        }
    }
}