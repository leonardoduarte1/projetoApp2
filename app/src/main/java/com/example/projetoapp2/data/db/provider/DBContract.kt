package com.example.projetoapp2.data.db.provider

import android.provider.BaseColumns

object DBContract {

    /* Inner class that defines the table contents */
    class Usuario : BaseColumns {
        companion object {
            val TABLE_NAME = "usuarios"
            val COLUMN_ID = "id"
            val COLUMN_NOME = "nome"
            val COLUMN_SENHA = "senha"
            val COLUMN_EMAIL = "email"
        }
    }
}