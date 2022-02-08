package com.audax.projetoseries.activities

import android.accounts.AccountManager
import android.content.*
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toFile
import androidx.lifecycle.lifecycleScope
import com.audax.projetoseries.R
import com.audax.projetoseries.interfaces.RetrofitInstance
import com.audax.projetoseries.managers.UserManagement
import com.audax.projetoseries.model.PostSerieResponse
import com.audax.projetoseries.model.Serie
import com.google.gson.Gson
import java.io.ByteArrayOutputStream

class CreateActivity : AppCompatActivity() {

    var imageEncoded = ""
    lateinit var imageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        val title = findViewById<EditText>(R.id.titleText)
        val sinopse = findViewById<EditText>(R.id.sinopseText)
        val serie = Serie()

        val addImage = findViewById<ImageView>(R.id.addImage)
        addImage.setOnClickListener{openGallery()}

        val createButton = findViewById<ImageView>(R.id.cadastrarButton)
        createButton.setOnClickListener{createSerie(title.text.toString(), sinopse.text.toString(), imageEncoded)}

        val cancelButton = findViewById<ImageView>(R.id.cancel_button)
        cancelButton.setOnClickListener{this.finish()}

        val returnButton = findViewById<ImageView>(R.id.returnFromCreateButton)
        returnButton.setOnClickListener{this.finish()}
    }

    private fun openGallery() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, 100)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            imageUri = data?.data!!
            val imageBitmap = readFile(contentResolver, imageUri)
            imageEncoded = Base64.encodeToString(imageBitmap, Base64.NO_WRAP)
        }
    }

    private fun createSerie(nome: String, sinopse: String, capa: String){
        if (nome.isBlank()) {
            Toast.makeText(this, "Insira um título", Toast.LENGTH_SHORT).show()
            return
        }
        if(sinopse.isBlank()) {
            Toast.makeText(this, "Insira uma sinopse", Toast.LENGTH_SHORT).show()
            return
        }
        if(capa.isBlank()) {
            Toast.makeText(this, "Insira uma imagem", Toast.LENGTH_SHORT).show()
            return
        }
        val accessToken = getSharedPreferences("accessToken", Context.MODE_PRIVATE).getString("accessToken", null)
        val serie = Serie()
        serie.descricao = sinopse
        serie.nome = nome
        serie.capa = capa
        val gson = Gson()
        val userJson = gson.toJson(serie)
        Log.d("TAG", serie.toString())
        lifecycleScope.launchWhenCreated {
            val response = RetrofitInstance.api.postSerie("Bearer $accessToken", serie)
            if (response.isSuccessful && response.body() != null) {
                Toast.makeText(this@CreateActivity, "Série adicionada", Toast.LENGTH_SHORT).show()
                Log.d("TAG", response.toString())
            }
        }

    }

    private fun readFile(cr: ContentResolver, uri: Uri): ByteArray {
        val inStream = cr.openInputStream(uri) ?: return ByteArray(0)
        val outStream = ByteArrayOutputStream()
        val buffer = ByteArray(32)
        while (inStream.read(buffer) > 0) {
            outStream.write(buffer)
        }
        return outStream.toByteArray()
    }
}