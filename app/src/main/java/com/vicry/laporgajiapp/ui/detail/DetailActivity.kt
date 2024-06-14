package com.vicry.laporgajiapp.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vicry.laporgajiapp.database.Gaji
import com.vicry.laporgajiapp.databinding.ActivityDetailBinding
import com.vicry.laporgajiapp.ui.add.AddActivity.Companion.EXTRA_GAJI

class DetailActivity : AppCompatActivity() {

    private var _activityDetailBinding: ActivityDetailBinding? = null
    private val binding get() = _activityDetailBinding

    private var gaji: Gaji? = null
    private var isEdit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        gaji = intent.getParcelableExtra(EXTRA_GAJI)
        if (gaji != null) {
            isEdit = true
        } else {
            gaji = Gaji()
        }

            gaji?.let { gaji ->
                binding?.tvNamaPegawai?.setText("Nama Pegawai : ${gaji.nama}")
                binding?.tvNIP?.setText("NIP Pegawai : ${gaji.nip}")
                binding?.tvAlamat?.setText("Alamat Pegawai : ${gaji.alamat}")
                binding?.tvGolongan?.setText("Golongan Pegawai : ${gaji.golongan}")
                binding?.tvTgLahir?.setText("Tanggal Lahir : ${gaji.tanggalLahir}")
                binding?.tvTgMasuk?.setText("Tanggal Masuk Kerja : ${gaji.tanggalMasuk}")
                binding?.tvGaji?.setText("Total Gaji : ${gaji.gaji}")
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityDetailBinding = null
    }
}