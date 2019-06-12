package com.example.projetoapp2.mvp.model

import com.example.projetoapp2.mvp.contract.LoginContract

class LoginModel : LoginContract.Model {

    override fun autenticar(usuario: String, senha: String) : Boolean{
        return (senha == "teste" && usuario == "teste")
    }
}