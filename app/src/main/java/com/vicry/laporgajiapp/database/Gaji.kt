package com.vicry.laporgajiapp.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "Gaji")
@Parcelize
data class Gaji(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "nip")
    var nip: String? = null,

    @ColumnInfo(name = "nama")
    var nama: String? = null,

    @ColumnInfo(name = "alamat")
    var alamat: String? = null,

    @ColumnInfo(name = "golongan")
    var golongan: String? = null,

    @ColumnInfo(name = "tanggalLahir")
    var tanggalLahir: String? = null,

    @ColumnInfo(name = "tanggalMasuk")
    var tanggalMasuk: String? = null,

    @ColumnInfo(name = "gaji")
    var gaji: String? = null,

    @ColumnInfo(name = "gajiPokok")
    var gajiPokok: String? = null,

    @ColumnInfo(name = "gajiBonus")
    var gajiBonus: String? = null,

) : Parcelable
