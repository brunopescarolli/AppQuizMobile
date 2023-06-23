package com.example.quiz

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class MyAdapter(private val context : Activity, private val arrayList: ArrayList<User>) : ArrayAdapter<User>(context, R.layout.activity_item_usuario,arrayList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.activity_item_usuario, null)

        val nick : TextView = view.findViewById(R.id.txtNick)
        val pontuacao : TextView = view.findViewById(R.id.txtPontuação)

        nick.text = arrayList[position].nick
        pontuacao.text = arrayList[position].pontuacao

        return view
    }
}