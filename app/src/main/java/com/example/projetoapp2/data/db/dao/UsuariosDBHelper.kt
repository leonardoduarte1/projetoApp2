package com.example.projetoapp2.data.db.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.projetoapp2.data.db.model.Usuario
import com.example.projetoapp2.data.db.provider.DBContract

class UsuariosDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insert(usuario: Usuario): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DBContract.Usuario.COLUMN_ID, usuario.id)
        values.put(DBContract.Usuario.COLUMN_NOME, usuario.nome)
        values.put(DBContract.Usuario.COLUMN_SENHA, usuario.senha)
        values.put(DBContract.Usuario.COLUMN_EMAIL, usuario.email)

        // Insert the new row, returning the primary key value of the new row
        val newRowId = db.insert(DBContract.Usuario.TABLE_NAME, null, values)

        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun delete(id: String): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = DBContract.Usuario.COLUMN_ID + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(id)
        // Issue SQL statement.
        db.delete(DBContract.Usuario.TABLE_NAME, selection, selectionArgs)

        return true
    }

    fun get(id: String): ArrayList<Usuario> {
        val users = ArrayList<Usuario>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.Usuario.TABLE_NAME + " WHERE " + DBContract.Usuario.COLUMN_ID + "='" + id + "'", null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var id: Long
        var nome: String
        var senha: String
        var email: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                id = cursor.getLong(cursor.getColumnIndex(DBContract.Usuario.COLUMN_ID))
                nome = cursor.getString(cursor.getColumnIndex(DBContract.Usuario.COLUMN_NOME))
                senha = cursor.getString(cursor.getColumnIndex(DBContract.Usuario.COLUMN_SENHA))
                email = cursor.getString(cursor.getColumnIndex(DBContract.Usuario.COLUMN_EMAIL))


                users.add(Usuario(id, nome, senha, email))
                cursor.moveToNext()
            }
        }
        return users
    }

    fun getAll(): ArrayList<Usuario> {
        val users = ArrayList<Usuario>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.Usuario.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var id: Long
        var nome: String
        var senha: String
        var email: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                id = cursor.getLong(cursor.getColumnIndex(DBContract.Usuario.COLUMN_ID))
                nome = cursor.getString(cursor.getColumnIndex(DBContract.Usuario.COLUMN_NOME))
                senha = cursor.getString(cursor.getColumnIndex(DBContract.Usuario.COLUMN_SENHA))
                email = cursor.getString(cursor.getColumnIndex(DBContract.Usuario.COLUMN_EMAIL))


                users.add(Usuario(id, nome, senha, email))
                cursor.moveToNext()
            }
        }
        return users
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "app.db"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContract.Usuario.TABLE_NAME + " (" +
                    DBContract.Usuario.COLUMN_NOME + " TEXT PRIMARY KEY," +
                    DBContract.Usuario.COLUMN_EMAIL + " TEXT," +
                    DBContract.Usuario.COLUMN_SENHA + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.Usuario.TABLE_NAME
    }

}
