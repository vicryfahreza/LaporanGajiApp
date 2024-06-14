package com.vicry.laporgajiapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Gaji::class], version = 1)
abstract class GajiRoomDatabase : RoomDatabase() {

    abstract fun gajiDao(): GajiDao

    companion object {
        @Volatile
        private var INSTANCE: GajiRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): GajiRoomDatabase {
            if (INSTANCE == null) {
                synchronized(GajiRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        GajiRoomDatabase::class.java, "gaji_database")
                        .build()
                }
            }
            return INSTANCE as GajiRoomDatabase
        }
    }
}