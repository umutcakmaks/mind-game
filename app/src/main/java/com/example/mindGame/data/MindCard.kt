package com.example.mindGame.data

import android.os.Parcel
import android.os.Parcelable

data class MindCard (val id:Long, val title:String?, val image:String?):Parcelable{
    var isVisible = false

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString()
    ) {
        isVisible = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(title)
        parcel.writeString(image)
        parcel.writeByte(if (isVisible) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MindCard> {
        override fun createFromParcel(parcel: Parcel): MindCard {
            return MindCard(parcel)
        }

        override fun newArray(size: Int): Array<MindCard?> {
            return arrayOfNulls(size)
        }
    }
}