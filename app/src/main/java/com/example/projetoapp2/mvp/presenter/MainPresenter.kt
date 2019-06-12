package com.example.projetoapp2.mvp.presenter

import com.example.projetoapp2.mvp.contract.MainContract

class MainPresenter(private val view : MainContract.View) :
    MainContract.Presenter {

    override fun atualizarValorCarteira() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun start() {
        view.bindViews()
    }


}