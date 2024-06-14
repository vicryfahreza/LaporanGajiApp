package com.vicry.laporgajiapp.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vicry.laporgajiapp.database.Gaji
import com.vicry.laporgajiapp.databinding.ItemGajiBinding
import com.vicry.laporgajiapp.helper.GajiDiffCallback
import com.vicry.laporgajiapp.ui.add.AddActivity
import com.vicry.laporgajiapp.ui.detail.DetailActivity
import com.vicry.laporgajiapp.ui.main.GajiAdapter.GajiViewHolder
import com.vicry.laporgajiapp.ui.update.UpdateActivity
import java.util.ArrayList

interface DeleteItemListener {
    fun onItemDeleted(gaji: Gaji)
}

class GajiAdapter(private val listener: DeleteItemListener) : RecyclerView.Adapter<GajiViewHolder>() {
    private val listGaji = ArrayList<Gaji>()
    fun setListGaji(listGaji: List<Gaji>) {
        val diffCallback = GajiDiffCallback(this.listGaji, listGaji)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listGaji.clear()
        this.listGaji.addAll(listGaji)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GajiViewHolder {
        val binding = ItemGajiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GajiViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listGaji.size
    }

    override fun onBindViewHolder(holder: GajiViewHolder, position: Int) {
        holder.bind(listGaji[position])
    }

    inner class GajiViewHolder(private val binding: ItemGajiBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(gaji: Gaji) {
            with(binding) {
                tvNamaPegawai.text = "Nama: ${gaji.nama}"
                tvGolonganPegawai.text = "Golongan: ${gaji.golongan}"
                tvGajiPegawai.text = "Gaji: Rp ${gaji.gaji}"
                tvTglMasukPegawai.text = gaji.tanggalMasuk
                btnDelete.setOnClickListener {
                    listener.onItemDeleted(gaji)
                }
                btnUpdate.setOnClickListener {
                    val intent = Intent(it.context, UpdateActivity::class.java)
                    intent.putExtra(AddActivity.EXTRA_GAJI, gaji)
                    it.context.startActivity(intent)
                }
                btnDetail.setOnClickListener {
                    val intent = Intent(it.context, DetailActivity::class.java)
                    intent.putExtra(AddActivity.EXTRA_GAJI, gaji)
                    it.context.startActivity(intent)
                }
            }
        }
    }
}