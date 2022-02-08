package com.audax.projetoseries.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.audax.projetoseries.R
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.audax.projetoseries.managers.UserManagement
import com.audax.projetoseries.interfaces.RetrofitInstance
import com.google.gson.Gson
import com.audax.projetoseries.model.User
import retrofit2.HttpException

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivty"
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val login = findViewById<EditText>(R.id.loginTE)
        val password = findViewById<EditText>(R.id.passwordTE)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {tryLog(login.text.toString(), password.text.toString()) }
    }

    private fun tryLog(sendLogin: String, sendPassword: String) {
        val user = User()
        user.email = sendLogin
        user.senha = sendPassword
        val gson = Gson()
        val userJson = gson.toJson(user)
        Log.d(TAG, user.toString())
        lifecycleScope.launchWhenCreated {
            val response = try {
                RetrofitInstance.api.postLogin("Basic ${UserManagement.authorizaton}", user)
            } catch (e: HttpException) {
                Log.e(TAG, "http exception")
                return@launchWhenCreated
            }
            if (response.isSuccessful && response.body() != null) {
                this@MainActivity.getSharedPreferences("accessToken", Context.MODE_PRIVATE).edit().putString("accessToken", response.body()!!.access_token).apply()
                startActivity(Intent(this@MainActivity, MainScreen::class.java))
                return@launchWhenCreated
            }
            else {
                Toast.makeText(this@MainActivity, "Login ou senha inválidos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}