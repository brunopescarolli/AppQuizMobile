package com.example.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quiz.databinding.ActivityTabelaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

private lateinit var binding: ActivityTabelaBinding
private var db = FirebaseFirestore.getInstance()
private lateinit var userArrayList : ArrayList<User>

class Tabela : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTabelaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.botaoVoltar.setOnClickListener {
            val intent = Intent(this, SelectQuiz::class.java)
            startActivity(intent)
            finish()
        }

        val arrayPontuacao = ArrayList<String>()
        val arrayNick = ArrayList<String>()

        db.collection("Usuários").orderBy("Pontuação",Query.Direction.DESCENDING).get().addOnSuccessListener { result ->
            for (document in result) {
                Log.d("db", "${document.data["Nick"]} ${document.data["Pontuação"]}")
                arrayNick.add(document.data["Nick"].toString())
                arrayPontuacao.add(document.data["Pontuação"].toString())
            }
            userArrayList = ArrayList()

            for(i in arrayNick.indices){

                val user = User(arrayNick[i],arrayPontuacao[i])
                userArrayList.add(user)

            }
            binding.tabela.adapter = MyAdapter(this, userArrayList)
        }
    }
}
