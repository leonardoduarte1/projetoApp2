package com.example.projetoapp2.mvp

interface BaseView<T> {
    var presenter : T
    fun bindViews()
}