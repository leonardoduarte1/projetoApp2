package com.example.projetoapp2.mvp.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.projetoapp2.R
import com.example.projetoapp2.mvp.contract.LoginContract
import com.example.projetoapp2.mvp.presenter.LoginPresenter

class LoginActivity : AppCompatActivity(), LoginContract.View {

    override lateinit var presenter : LoginPresenter

    lateinit var loginInput : EditText
    lateinit var passwordInput : EditText
    lateinit var btnLogin : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Bind do Presenter com a nossa View
        presenter = LoginPresenter(this)

        presenter.start() // Chama o método responsável por dizer a View o que deve ser inicializado.

        btnLogin.setOnClickListener {
            //Comunica e Transfere a responsabilidade do Login para o Presenter
            //Que então irá validar se o Login é válido ou não.
            presenter.isLoginValid(loginInput.text.toString(), passwordInput.text.toString())
        }
    }

    override fun displayErrorMessage() {
        Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
        loginInput.error = "Login Failed!"
        passwordInput.error = "Login Failed!"
    }

    override fun displaySucessToast() {
        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
    }

    override fun startHomeActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun bindViews() {
        loginInput = findViewById(R.id.txtLogin)
        passwordInput = findViewById(R.id.txtSenha)
        btnLogin = findViewById(R.id.btnLogin)
    }
}