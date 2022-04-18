package com.example.mindGame.data.database

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.mindGame.data.GameRecord
import com.example.mindGame.data.GameRecord.CREATOR.GAME_RECORD_SQL_SELECT_ALL
import com.example.mindGame.data.GameRecord.GameRecordEntry.Companion.PAIRS
import com.example.mindGame.data.GameRecord.GameRecordEntry.Companion.SPEED
import com.example.mindGame.data.GameRecord.GameRecordEntry.Companion.TABLE_NAME
import com.example.mindGame.data.GameRecord.GameRecordEntry.Companion.TIME
import com.example.mindGame.data.GameSettings


fun insertGameRecord(gameRecord: GameRecord, database: SQLiteDatabase) {
    val contentvalue = gameRecord.getContentValues()
    database.insert(TABLE_NAME, null, contentvalue)
}

fun getAllGameRecordsGivenSettings(gameSettings: GameSettings, database: SQLiteDatabase): Cursor {
    val selectQuery = GAME_RECORD_SQL_SELECT_ALL + " WHERE " + PAIRS + "='" + gameSettings.gridSize + "' AND " + SPEED + "='" + gameSettings.speed + "' ORDER BY " + TIME + " ASC"
    return database.rawQuery(selectQuery, null)
}