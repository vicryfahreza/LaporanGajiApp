package com.vicry.laporgajiapp.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vicry.laporgajiapp.database.Gaji
import com.vicry.laporgajiapp.repository.GajiRepository

class MainViewModel(application: Application) : ViewModel() {
    private val mGajiRepository: GajiRepository = GajiRepository(application)
    fun searchPreviousMonth(cari: String): LiveData<List<Gaji>> = mGajiRepository.searchPreviousMonth(cari)

    fun getAllGaji(): LiveData<List<Gaji>> = mGajiRepository.getAllGaji()

    fun delete(gaji: Gaji) {
        mGajiRepository.delete(gaji)
    }
}