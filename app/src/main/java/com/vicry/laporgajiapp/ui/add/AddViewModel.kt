package com.vicry.laporgajiapp.ui.add

import android.app.Application
import androidx.lifecycle.ViewModel
import com.vicry.laporgajiapp.database.Gaji
import com.vicry.laporgajiapp.repository.GajiRepository

class AddViewModel(application: Application) : ViewModel() {

    private val mgajiRepository: GajiRepository = GajiRepository(application)

    fun insert(gaji: Gaji) {
        mgajiRepository.insert(gaji)
    }

    fun update(gaji: Gaji) {
        mgajiRepository.update(gaji)
    }

    fun delete(gaji: Gaji) {
        mgajiRepository.delete(gaji)
    }

}