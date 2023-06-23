package com.example.quiz

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import com.example.quiz.databinding.ActivityItemUsuarioBinding
import com.example.quiz.databinding.ActivityMainQuizBinding
import com.example.quiz.Pergunta
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.random.Random

private lateinit var binding: ActivityMainQuizBinding
private var db = FirebaseFirestore.getInstance()

class MainQuiz : AppCompatActivity() {
        val perguntasN = ArrayList<Pergunta>()
        var perguntaAtual = Pergunta()
        var pontos = 0
override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pergunta0 = Pergunta(
            enunciado = "lala0",
            respostas = arrayListOf("lala1", "lala2", "lala3", "lala4", "lala5"),
            correta = "lala1"
        )

        val pergunta1 = Pergunta(
            enunciado = "lala1",
            respostas = arrayListOf("lala1", "lala2", "lala3", "lala4", "lala5"),
            correta = "lala2"
        )

        val pergunta2 = Pergunta(
            enunciado = "lala2",
            respostas = arrayListOf("lala1", "lala2", "lala3", "lala4", "lala5"),
            correta = "lala3"
        )

        val pergunta3 = Pergunta(
            enunciado = "lala3",
            respostas = arrayListOf("lala1", "lala2", "lala3", "lala4", "lala5"),
            correta = "lala4"
        )

        val pergunta4 = Pergunta(
            enunciado = "lala4",
            respostas = arrayListOf("lala1", "lala2", "lala3", "lala4", "lala5"),
            correta = "lala5"
        )

        perguntasN.add(pergunta0)
        perguntasN.add(pergunta1)
        perguntasN.add(pergunta2)
        perguntasN.add(pergunta3)
        perguntasN.add(pergunta4)

        atualizar()

        binding.botaoProximo.setOnClickListener {
            val lala = atualizar()
            if(lala<0){
                startActivity(Intent(this, ConfirmarPontuacao::class.java).putExtra("pontos",pontos.toString()))
            }
        }

        binding.botaoConfirmar.setOnClickListener{
            pontos = checarPergunta(perguntaAtual)
            Log.d("pontosAtualizado","${pontos}")
        }
    }
    fun atualizar(): Int {
        val start = 0
        val end = perguntasN.size - 1
        Log.d("qntPerguntas","${end}")
        if(end < 0){
            val intent = Intent(this, ConfirmarPontuacao::class.java)
            startActivity(intent)
            finish()
        }else{
        val ale = rand(start,end)

        val pergunta = perguntasN.removeAt(ale)
        perguntaAtual = pergunta

        binding.enunciadoTxt.text = pergunta.enunciado
        binding.botao1.text = pergunta.respostas[0]
        binding.botao2.text = pergunta.respostas[1]
        binding.botao3.text = pergunta.respostas[2]
        binding.botao4.text = pergunta.respostas[3]
        binding.botao5.text = pergunta.respostas[4]

        binding.botao1.setBackgroundResource(R.color.white)
        binding.botao2.setBackgroundResource(R.color.white)
        binding.botao3.setBackgroundResource(R.color.white)
        binding.botao4.setBackgroundResource(R.color.white)
        binding.botao5.setBackgroundResource(R.color.white)

        binding.grupoBotao.clearCheck()
        }
        binding.botaoConfirmar.isEnabled = true
        return end
    }
    fun rand(start: Int, end: Int): Int {
        require(start <= end) { "Illegal Argument" }
        return (Math.random() * (end - start + 1)).toInt() + start
    }
    fun checarPergunta(pergunta: Pergunta): Int {
        val idReposta = binding.grupoBotao.checkedRadioButtonId
        val botaoMarcado = binding.root.findViewById<RadioButton>(idReposta)
        val respostaMarcada = botaoMarcado.text

        val arrayIds = ArrayList<Int>();

        var botaoCorreto : RadioButton = RadioButton(this)

        arrayIds.add(binding.botao1.id)
        arrayIds.add(binding.botao2.id)
        arrayIds.add(binding.botao3.id)
        arrayIds.add(binding.botao4.id)
        arrayIds.add(binding.botao5.id)

        arrayIds.forEach() {
            val botao = binding.root.findViewById<RadioButton>(it)
            if (botao.text == pergunta.correta){
                botaoCorreto = botao
            }
        }

        if(respostaMarcada == pergunta.correta){
            botaoMarcado.setBackgroundResource(R.color.green)
            pontos = pontos + 10
            binding.botaoConfirmar.isEnabled = false
        }else{
            botaoMarcado.setBackgroundResource(R.color.red)
            botaoCorreto.setBackgroundResource(R.color.green)
        }
        return pontos
    }
}