package com.vicry.laporgajiapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface GajiDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(gaji: Gaji)

    @Update
    fun update(gaji: Gaji)

    @Delete
    fun delete(gaji: Gaji)

    @Query("SELECT * from gaji ORDER BY id ASC")
    fun getAllGaji(): LiveData<List<Gaji>>

//    '%2024/04%'
    @Query("SELECT * from gaji WHERE tanggalMasuk LIKE '%' || :cari || '%' ")
    fun searchPreviousMonth(cari: String): LiveData<List<Gaji>>
}