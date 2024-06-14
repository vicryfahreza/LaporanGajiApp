package com.vicry.laporgajiapp.helper

import androidx.recyclerview.widget.DiffUtil
import com.vicry.laporgajiapp.database.Gaji

class GajiDiffCallback(private val oldGajiList: List<Gaji>, private val newGajiList: List<Gaji>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldGajiList.size
    override fun getNewListSize(): Int = newGajiList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldGajiList[oldItemPosition].id == newGajiList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldGaji = oldGajiList[oldItemPosition]
        val newGaji = newGajiList[newItemPosition]
        return oldGaji.nama == newGaji.nama
                && oldGaji.nip == newGaji.nip
                && oldGaji.alamat == newGaji.alamat
                && oldGaji.golongan == newGaji.golongan
                && oldGaji.gaji == newGaji.gaji
                && oldGaji.tanggalMasuk == newGaji.tanggalMasuk
                && oldGaji.tanggalLahir == newGaji.tanggalLahir
    }
}