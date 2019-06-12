package com.example.projetoapp2.mvp.view

interface BaseView<T> {
    var presenter : T
    fun bindViews()
}