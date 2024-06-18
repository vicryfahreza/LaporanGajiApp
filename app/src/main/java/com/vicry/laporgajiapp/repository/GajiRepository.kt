package com.vicry.laporgajiapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.vicry.laporgajiapp.database.Gaji
import com.vicry.laporgajiapp.database.GajiDao
import com.vicry.laporgajiapp.database.GajiRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class GajiRepository(application: Application) {
    private val mGajiDao: GajiDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = GajiRoomDatabase.getDatabase(application)
        mGajiDao = db.gajiDao()
    }

    fun getAllGaji(): LiveData<List<Gaji>> = mGajiDao.getAllGaji()

    fun searchPreviousMonth(cari: String): LiveData<List<Gaji>> = mGajiDao.searchPreviousMonth(cari)

    fun insert(gaji: Gaji) {
        executorService.execute { mGajiDao.insert(gaji) }
    }

    fun delete(gaji: Gaji) {
        executorService.execute { mGajiDao.delete(gaji) }
    }

    fun update(gaji: Gaji) {
        executorService.execute { mGajiDao.update(gaji) }
    }
}