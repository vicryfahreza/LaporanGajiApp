package com.vicry.laporgajiapp.ui.update

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.vicry.laporgajiapp.R
import com.vicry.laporgajiapp.database.Gaji
import com.vicry.laporgajiapp.databinding.ActivityUpdateBinding
import com.vicry.laporgajiapp.helper.ViewModelFactory
import com.vicry.laporgajiapp.ui.add.AddActivity
import com.vicry.laporgajiapp.ui.main.MainActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class UpdateActivity : AppCompatActivity() {

    private var _activityUpdateBinding: ActivityUpdateBinding? = null
    private val binding get() = _activityUpdateBinding

    private lateinit var updateViewModel: UpdateViewModel

    private var gaji: Gaji? = null
    private var isEdit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityUpdateBinding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        updateViewModel = obtainViewModel(this@UpdateActivity)

        gaji = intent.getParcelableExtra(AddActivity.EXTRA_GAJI)
        if (gaji != null) {
            isEdit = true
        } else {
            gaji = Gaji()
        }

        gaji?.let { gaji ->
            binding?.etNamaPegawai?.setText(gaji.nama)
            binding?.etNIP?.setText(gaji.nip)
            binding?.etAlamat?.setText(gaji.alamat)
            binding?.tvGolonganGaji?.setText(gaji.golongan)
            binding?.tvTglLahirInput?.setText(gaji.tanggalLahir)
            binding?.tvTglMasukInput?.setText(gaji.tanggalMasuk)
            binding?.tvTotalGaji?.setText(gaji.gaji)
            binding?.tvTotalGajiPokok?.setText(gaji.gajiPokok)
            binding?.tvTotalGajiBonus?.setText(gaji.gajiBonus)
        }

        binding?.btnUpdatePegawai?.setOnClickListener {

            val nama = binding?.etNamaPegawai?.text.toString().trim()
            val nip = binding?.etNIP?.text.toString().trim()
            val alamat = binding?.etAlamat?.text.toString().trim()
            val golongan = binding?.tvGolonganGaji?.text.toString().trim()
            val totalgaji = binding?.tvTotalGaji?.text.toString()
            val gajiPokok = binding?.tvTotalGajiPokok?.text.toString()
            val gajiBonus = binding?.tvTotalGajiBonus?.text.toString()

            val tglLahir = binding?.tvTglLahirInput?.text.toString().trim()
            val tglMasuk = binding?.tvTglMasukInput?.text.toString().trim()

            when {
                nama.isEmpty() -> {
                    binding?.etNamaPegawai?.error = getString(R.string.empty)
                }
                nip.isEmpty() -> {
                    binding?.etNIP?.error = getString(R.string.empty)
                }
                alamat.isEmpty() -> {
                    binding?.etAlamat?.error = getString(R.string.empty)
                }
                golongan.isEmpty() -> {
                    binding?.tvGolonganGaji?.error = getString(R.string.empty)
                }
                totalgaji.isEmpty() -> {
                    binding?.tvTotalGaji?.error = getString(R.string.empty)
                }
                else -> {
                    gaji.let { gaji ->
                        gaji?.nama = nama
                        gaji?.nip = nip
                        gaji?.alamat = alamat
                        gaji?.golongan = golongan
                        gaji?.gaji = totalgaji
                        gaji?.tanggalLahir = tglLahir
                        gaji?.tanggalMasuk = tglMasuk
                        gaji?.gajiPokok = gajiPokok
                        gaji?.gajiBonus = gajiBonus
                    }
                    updateViewModel.update(gaji as Gaji)
                    showToast(getString(R.string.update))
                    val intent = Intent(this@UpdateActivity, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        binding?.ibTglLahir?.setOnClickListener {
            setTanggal("tglLahir")
        }
        binding?.ibTglMasuk?.setOnClickListener {
            setTanggal("tglMasuk")
        }

        val spinner = binding?.golPegawaiSpinner
        ArrayAdapter.createFromResource(
            this,
            R.array.gol_pegawai_spinner,
            android.R.layout.simple_spinner_item
        ).also {adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner?.adapter = adapter
        }

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val golItem: String = parent?.getItemAtPosition(position).toString()
                binding?.tvGolonganGaji?.text = golItem
                binding?.tvTotalGaji?.text = golItem

                val gajiGol1 = 1500000
                val gajiGol2 = 1000000
                val gajiGol3 = 500000
                var totalGaji = 0.0
                val bonusGol1 = (gajiGol1*0.5)
                val bonusGol2 = (gajiGol2*0.4)
                val bonusGol3 = (gajiGol3*0.3)
                when (golItem) {
                    "Gol.1 Manager" -> {
                        totalGaji = gajiGol1 - (gajiGol1*0.05) + bonusGol1
                        binding?.tvTotalGaji?.text = totalGaji.toString()
                        binding?.tvTotalGajiPokok?.text = gajiGol1.toString()
                        binding?.tvTotalGajiBonus?.text = bonusGol1.toString()
                    }
                    "Gol.2 Supervisor" -> {
                        totalGaji = gajiGol2 - (gajiGol2*0.05) + bonusGol2
                        binding?.tvTotalGaji?.text = totalGaji.toString()
                        binding?.tvTotalGajiPokok?.text = gajiGol2.toString()
                        binding?.tvTotalGajiBonus?.text = bonusGol2.toString()
                    }
                    "Gol.3 Staff" -> {
                        totalGaji = gajiGol3 - (gajiGol3*0.05) + bonusGol3
                        binding?.tvTotalGaji?.text = totalGaji.toString()
                        binding?.tvTotalGajiPokok?.text = gajiGol3.toString()
                        binding?.tvTotalGajiBonus?.text = bonusGol3.toString()
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityUpdateBinding = null
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun obtainViewModel(activity: AppCompatActivity): UpdateViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[UpdateViewModel::class.java]
    }

    private fun setTanggal(status: String){
        val datePicker = Calendar.getInstance()
        val date = DatePickerDialog.OnDateSetListener{
                view: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
            datePicker[Calendar.YEAR] = year
            datePicker[Calendar.MONTH] = month
            datePicker[Calendar.DAY_OF_MONTH] = dayOfMonth
            val dateFormat = "yyyy/MM/dd"
            val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.getDefault())
            if (status == "tglLahir"){
                binding?.tvTglLahirInput?.text = simpleDateFormat.format(datePicker.time)
            } else if (status == "tglMasuk"){
                binding?.tvTglMasukInput?.text = simpleDateFormat.format(datePicker.time)
            }
        }
        DatePickerDialog(
            this@UpdateActivity, date,
            datePicker[Calendar.YEAR],
            datePicker[Calendar.MONTH],
            datePicker[Calendar.DAY_OF_MONTH],
        ).show()
    }


}