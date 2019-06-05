package com.example.projetoapp2.mvp.Home

import com.example.projetoapp2.mvp.Login.LoginContract

class MainPresenter(private val view : MainContract.View) : MainContract.Presenter {

    override fun atualizarValorCarteira() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun start() {
        view.bindViews()
    }


}