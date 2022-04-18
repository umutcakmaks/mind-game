package com.example.mindGame.data.database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mindGame.data.GameRecord
import com.example.mindGame.data.GameRecord.CREATOR.GAME_RECORD_SQL_CREATE_ENTRIES
import com.example.mindGame.data.GameSettings


class DataBaseHelper(mcontext: Context) : SQLiteOpenHelper(mcontext, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "MEMORY_GAME"
    }


    private lateinit var database: SQLiteDatabase
    override fun onCreate(database: SQLiteDatabase) {
        database.execSQL(GAME_RECORD_SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    fun insertGameRecord(gameRecord: GameRecord){
        database = this.writableDatabase
        insertGameRecord(gameRecord, database)
    }

    fun getAllGameRecordsGivenSettings(gameSettings: GameSettings):Cursor{
        database = this.readableDatabase
        return getAllGameRecordsGivenSettings(gameSettings, database)
    }
}