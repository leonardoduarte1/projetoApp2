package com.example.projetoapp2

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.projetoapp2.mvp.Home.MainContract
import com.example.projetoapp2.mvp.Home.MainPresenter

class MainActivity : AppCompatActivity(), MainContract.View {

    override lateinit var presenter : MainPresenter

    lateinit var valorCarteira : TextView
    lateinit var imagemEditarPerfil : ImageView
    lateinit var imagemCalendario : ImageView
    lateinit var imagemMensagens : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(this)

        presenter.start() // Chama o método responsável por dizer a View o que deve ser inicializado.
        imagemEditarPerfil.setOnClickListener {
            val intent = Intent(this, EditarPerfilActivity::class.java)
            startActivity(intent)
        }

        imagemCalendario.setOnClickListener {
            val intent = Intent(this, CalendarioActivity::class.java)
            startActivity(intent)
        }

        imagemMensagens.setOnClickListener {
            val intent = Intent(this, MensagensActivity::class.java)
            startActivity(intent)
        }
    }


    override fun bindViews() {
        imagemEditarPerfil = findViewById(R.id.imagemEditarPerfil)
        imagemCalendario = findViewById(R.id.imagemCalendario)
        imagemMensagens = findViewById(R.id.imagemMensagens)
    }


}
