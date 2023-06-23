package com.example.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.quiz.databinding.ActivityItemUsuarioBinding
import com.example.quiz.databinding.ActivityTabelaBinding
import com.google.firebase.firestore.FirebaseFirestore

private lateinit var binding: ActivityItemUsuarioBinding
private var db = FirebaseFirestore.getInstance()

class item_usuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}