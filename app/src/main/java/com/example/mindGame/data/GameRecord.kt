package com.example.mindGame.data

import android.content.ContentValues
import android.database.Cursor
import android.os.Parcel
import android.os.Parcelable
import android.provider.BaseColumns
import com.example.mindGame.data.GameRecord.GameRecordEntry.Companion.ID
import com.example.mindGame.data.GameRecord.GameRecordEntry.Companion.PAIRS
import com.example.mindGame.data.GameRecord.GameRecordEntry.Companion.POINTS
import com.example.mindGame.data.GameRecord.GameRecordEntry.Companion.SPEED
import com.example.mindGame.data.GameRecord.GameRecordEntry.Companion.TIME


data class GameRecord(val points:Int, val time:Long, val gameSettings: GameSettings) :Parcelable {
    var id: Long = 0

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readLong(),
        parcel.readParcelable(GameSettings::class.java.classLoader)!!
    ) {
        id = parcel.readLong()
    }

    constructor(id:Long, points: Int, time: Long, speed: Double, numberOfPairs: Int):this(points, time, GameSettings(speed, numberOfPairs)){
        this.id = id
    }

    fun getContentValues(): ContentValues {
        val contentvalue = ContentValues()
        contentvalue.put(POINTS, points)
        contentvalue.put(TIME, time)
        contentvalue.put(PAIRS, gameSettings.gridSize)
        contentvalue.put(SPEED, gameSettings.speed)
        return contentvalue
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(points)
        parcel.writeLong(time)
        parcel.writeParcelable(gameSettings, flags)
        parcel.writeLong(id)
    }

    override fun describeContents(): Int {
        return 0
    }


    class GameRecordEntry : BaseColumns{
        companion object {
            const val TABLE_NAME = "GameRecords"
            const val ID = "_id"
            const val POINTS = "points"
            const val TIME = "time"
            const val SPEED = "speed"
            const val PAIRS = "numberOfPairs"
        }

    }
    companion object CREATOR : Parcelable.Creator<GameRecord> {
        override fun createFromParcel(parcel: Parcel): GameRecord {
            return GameRecord(parcel)
        }

        override fun newArray(size: Int): Array<GameRecord?> {
            return arrayOfNulls(size)
        }

        const val GAME_RECORD_SQL_CREATE_ENTRIES = ("CREATE TABLE " + GameRecordEntry.TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY," + POINTS + " INTEGER," + SPEED + " REAL, " + PAIRS + " INTEGER," + TIME + " INTEGER)")

        const val GAME_RECORD_SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + GameRecordEntry.TABLE_NAME

        const val GAME_RECORD_SQL_SELECT_ALL = "SELECT * FROM " + GameRecordEntry.TABLE_NAME

        fun fillGameRecordsFromCursor(mcursor: Cursor): ArrayList<GameRecord> {
            val articles: ArrayList<GameRecord> = arrayListOf()
            if (mcursor.count != 0) {
                mcursor.moveToFirst()
                do {
                    articles.add(fillSingleGameRecordFromCursor(mcursor))
                } while (mcursor.moveToNext())
            }
            return articles
        }

        private fun fillSingleGameRecordFromCursor(mcursor: Cursor):GameRecord{
            val gameRecord:GameRecord
            val id = mcursor.getLong(mcursor.getColumnIndex(ID))
            val points = mcursor.getInt(mcursor.getColumnIndex(POINTS))
            val time = mcursor.getLong(mcursor.getColumnIndex(TIME))
            val pairs = mcursor.getInt(mcursor.getColumnIndex(PAIRS))
            val speed = mcursor.getDouble(mcursor.getColumnIndex(SPEED))
            gameRecord = GameRecord(id, points, time, speed, pairs)
            return gameRecord
        }
    }


}