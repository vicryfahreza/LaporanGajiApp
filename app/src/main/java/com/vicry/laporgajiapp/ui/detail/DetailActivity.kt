package com.vicry.laporgajiapp.ui.detail

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.vicry.laporgajiapp.R
import com.vicry.laporgajiapp.database.Gaji
import com.vicry.laporgajiapp.databinding.ActivityDetailBinding
import com.vicry.laporgajiapp.ui.add.AddActivity.Companion.EXTRA_GAJI
import java.io.File
import java.io.FileOutputStream

class DetailActivity : AppCompatActivity() {

    private var _activityDetailBinding: ActivityDetailBinding? = null
    private val binding get() = _activityDetailBinding

    private var gaji: Gaji? = null
    private var isEdit = false

    lateinit var bmp: Bitmap
    lateinit var scaledbmp: Bitmap

    var pageHeight = 1120
    var pageWidth = 792

    var PERMISSION_CODE = 101

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
            binding?.tvGaji?.setText("Total Bonus : ${gaji.gajiBonus}")
            binding?.tvGaji?.setText("Gaji Pokok: ${gaji.gajiPokok}")

            binding?.btnPdf?.setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    generatePDF(
                        gaji.nama,
                        gaji.alamat,
                        gaji.nip,
                        gaji.tanggalLahir,
                        gaji.tanggalMasuk,
                        gaji.gaji,
                        gaji.golongan,
                        gaji.gajiBonus,
                        gaji.gajiPokok,
                    )
                }
            }
        }

//        bmp = BitmapFactory.decodeResource(baseContext.resources, R.drawable.city)
//        scaledbmp = Bitmap.createScaledBitmap(bmp, 100, 100, false)

        if (checkPermissions()) {
            Toast.makeText(this, "Permissions Granted..", Toast.LENGTH_SHORT).show()
        } else {
            requestPermission()
        }


    }


    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun generatePDF(nama: String?, alamat: String?, nip: String?, tanggalLahir: String?, tanggalMasuk: String?, totalGaji : String?, golongan: String?, gajiBonus: String?, gajiPokok: String?) {
        val pdfDocument = PdfDocument()

        val title = Paint()

        val myPageInfo: PdfDocument.PageInfo? =
            PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()

        val myPage: PdfDocument.Page = pdfDocument.startPage(myPageInfo)

        val canvas: Canvas = myPage.canvas

        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))

        title.textSize = 20F

        title.setColor(ContextCompat.getColor(this, R.color.black))

        canvas.drawText("Laporan Gaji Pegawai", 209F, 100F, title)
        canvas.drawText("PT. Baroqah tbk.", 209F, 80F, title)
        title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
        title.setColor(ContextCompat.getColor(this, R.color.black))
        title.textSize = 20F

        title.textAlign = Paint.Align.CENTER
        canvas.drawText("Gaji Pokok: $gajiPokok", 396F, 650F, title)
        canvas.drawText("Total Bonus: $gajiBonus", 396F, 620F, title)
        canvas.drawText("Total Gaji: $totalGaji", 396F, 590F, title)
        canvas.drawText("Tanggal Masuk: $tanggalMasuk", 396F, 560F, title)
        canvas.drawText("TanggalLahir: $tanggalLahir", 396F, 530F, title)
        canvas.drawText("Golongan: $golongan", 396F, 500F, title)
        canvas.drawText("Alamat: $alamat", 396F, 470F, title)
        canvas.drawText("NIP: $nip", 396F, 440F, title)
        canvas.drawText("Nama: $nama", 396F, 410F, title)

        pdfDocument.finishPage(myPage)


        val contextWrapper = ContextWrapper(applicationContext)
        val documentDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        val file = File(documentDirectory, "LaporanGaji" + ".pdf")

        try {
            pdfDocument.writeTo(FileOutputStream(file))

            Toast.makeText(applicationContext, "Berhasil mencetak Pdf", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()

            Toast.makeText(applicationContext, "Gagal mencetak Pdf", Toast.LENGTH_SHORT)
                .show()
        }

        pdfDocument.close()
    }

    private fun getFilePath(): String? {
        val contextWrapper = ContextWrapper(applicationContext)
        val documentDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        val file = File(documentDirectory, "LaporanGaji" + ".pdf")
        return file.path
    }

    fun checkPermissions(): Boolean {
        val writeStoragePermission = ContextCompat.checkSelfPermission(
            applicationContext,
            WRITE_EXTERNAL_STORAGE
        )

        val readStoragePermission = ContextCompat.checkSelfPermission(
            applicationContext,
            READ_EXTERNAL_STORAGE
        )

        return writeStoragePermission == PackageManager.PERMISSION_GRANTED
                && readStoragePermission == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission() {

        ActivityCompat.requestPermissions(
            this,
            arrayOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE), PERMISSION_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_CODE) {

            if (grantResults.isNotEmpty()) {

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1]
                    == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show()

                } else {

                    Toast.makeText(this, "Permission Denied..", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _activityDetailBinding = null
    }
}