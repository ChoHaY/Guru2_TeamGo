package com.example.teamgo

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBManager(
    context: Context, name: String? ,factory: SQLiteDatabase.CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, name , factory, version){

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE projectlist_ (PJname text, PJdate text, PJgap text)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}