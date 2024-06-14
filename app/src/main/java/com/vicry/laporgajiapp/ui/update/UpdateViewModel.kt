package com.vicry.laporgajiapp.ui.update

import android.app.Application
import androidx.lifecycle.ViewModel
import com.vicry.laporgajiapp.database.Gaji
import com.vicry.laporgajiapp.repository.GajiRepository

class UpdateViewModel(application: Application) : ViewModel() {

    private val mgajiRepository: GajiRepository = GajiRepository(application)

    fun update(gaji: Gaji) {
        mgajiRepository.update(gaji)
    }

}