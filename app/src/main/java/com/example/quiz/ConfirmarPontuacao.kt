package com.example.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.quiz.databinding.ActivityConfirmarPontuacaoBinding
import com.example.quiz.databinding.ActivityFormLoginBinding
import com.example.quiz.databinding.ActivityFormNickBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

private lateinit var binding: ActivityConfirmarPontuacaoBinding
private val db = FirebaseFirestore.getInstance()

class ConfirmarPontuacao : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmarPontuacaoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val usuarioAtual = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val pontos = intent.getStringExtra("pontos")
        Log.d("pontos2","${pontos}")

        binding.pontos.text = pontos.toString()

        binding.sim.setOnClickListener {
            db.collection("Usuários").document(usuarioAtual).update("Pontuação", pontos)
            val intent = Intent(this, SelectQuiz::class.java)
            startActivity(intent)
            finish()
        }
        binding.nao.setOnClickListener {
            val intent = Intent(this, SelectQuiz::class.java)
            startActivity(intent)
            finish()
        }
    }
}