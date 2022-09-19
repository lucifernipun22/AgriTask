package com.nipun.agritask.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nipun.agritask.data.Contact

@Database(entities = arrayOf(Contact::class), version = 1, exportSchema = false)
abstract class ContactRoomDatabase : RoomDatabase(){

    abstract fun contactDao(): ContactDao
}