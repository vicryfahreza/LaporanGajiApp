package com.vicry.laporgajiapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vicry.laporgajiapp.R
import com.vicry.laporgajiapp.database.Gaji
import com.vicry.laporgajiapp.databinding.ActivityMainBinding
import com.vicry.laporgajiapp.helper.ViewModelFactory
import com.vicry.laporgajiapp.ui.add.AddActivity

class MainActivity : AppCompatActivity(), DeleteItemListener {

    private var _activityMainBinding: ActivityMainBinding? = null
    private val binding get() = _activityMainBinding

    private lateinit var adapter: GajiAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.addGaji?.setOnClickListener {
            val intent = Intent(this@MainActivity, AddActivity::class.java)
            startActivity(intent)
        }

        mainViewModel = obtainViewModel(this@MainActivity)
        mainViewModel.getAllGaji().observe(this) { gajiList ->
            if (gajiList != null) {
                adapter.setListGaji(gajiList)
            }
        }

        adapter = GajiAdapter(this)

        binding?.rvGaji?.layoutManager = LinearLayoutManager(this)
        binding?.rvGaji?.setHasFixedSize(true)
        binding?.rvGaji?.adapter = adapter

        binding?.btnPrevMonth?.setOnClickListener {
            val search = binding?.etCari?.text.toString().trim()
            mainViewModel.searchPreviousMonth(search).observe(this) { gajiList ->
                if(gajiList != null) {
                    adapter.setListGaji(gajiList)
                }
            }
        }

        binding?.btnAllGaji?.setOnClickListener {
            mainViewModel.getAllGaji().observe(this) { gajiList ->
                if(gajiList != null) {
                    adapter.setListGaji(gajiList)
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _activityMainBinding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[MainViewModel::class.java]
    }

    override fun onItemDeleted(gaji: Gaji) {
        alertBuilder(gaji)
    }

    private fun alertBuilder(gaji: Gaji){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Hapus Data Pegawai")
        builder.setMessage("Anda yakin Menghapus data?")

        builder.setPositiveButton(android.R.string.yes) { _, _ ->
            mainViewModel.delete(gaji)
        }

        builder.setNegativeButton(android.R.string.no) { _, _ ->
            showToast(getString(R.string.no))
        }

        builder.show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}