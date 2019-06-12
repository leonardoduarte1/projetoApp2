package com.example.projetoapp2.mvp.contract

import com.example.projetoapp2.mvp.presenter.BasePresenter
import com.example.projetoapp2.mvp.view.BaseView
import com.example.projetoapp2.mvp.presenter.LoginPresenter

/**
 * No contrato podemos definir todos os métodos que são mandatórios
 * de implementação pelas camadas View e Presenter de cada uma de nossas
 * Activities ou Fragments
 */
interface LoginContract {

    /**
     * Nossa LoginActivity precisa implementar os métodos definidos abaixo
     */
    interface View : BaseView<LoginPresenter> {
        fun displayErrorMessage()
        fun displaySucessToast()
        fun startHomeActivity()
    }

    /**
     * Nosso Presenter precisa implementar os seguintes métodos
     */
    interface Presenter : BasePresenter {
        fun isLoginValid(userName: String, password : String)
    }

    interface Model {
        fun autenticar(usuario: String, senha : String): Boolean
    }
}