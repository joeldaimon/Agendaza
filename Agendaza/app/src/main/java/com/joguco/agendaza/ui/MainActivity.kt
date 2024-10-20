package com.joguco.agendaza.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.joguco.agendaza.ViewModel.EntryViewModel
import com.joguco.agendaza.databinding.ActivityMainBinding
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var entryVM: EntryViewModel

    companion object {
        val IMAGE_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        entryVM = ViewModelProvider(this)[EntryViewModel::class.java]
        initListeners()
        initUI()
    }

    private fun initUI() {
        var entryList = entryVM.getAllEntries()
        if(entryList.size > 0 ) {
            binding.ivImage.setImageBitmap(convertByteArrayToBitmap(entryList[0].image))
        }
    }

    private fun initListeners() {
        binding.btnUpload.setOnClickListener{
            pickImageGallery()
        }
    }

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val imageUri: Uri? = data.data
            binding.ivImage.setImageURI(imageUri)
            if (imageUri != null) {
                val inputStream = contentResolver.openInputStream(imageUri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                var imagen: ByteArray = convertImageToByteArray(bitmap)

                inputStream?.close()

                //Crear
                var entry = entryVM.add(
                    "test",
                    image = imagen,
                    "test text",
                    tags = "test1-test2"
                )
            }
        }
    }

    fun convertImageToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    fun convertByteArrayToBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}