package com.cortinezmatias.tp1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.cortinezmatias.tp1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("CiclosDeVida", "onCreate()")

        // Botón LOGIN
        binding.butonLogin.setOnClickListener {
            validateData()
        }

        // Botón REGISTRO
        binding.butonRegistrer.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("CiclosDeVida", "onResume()")

        val preferences = getSharedPreferences(RegisterActivity.CREDENCIALES, MODE_PRIVATE)
        val autoLogin = preferences.getBoolean("autoLogin", false)

        if (autoLogin) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateData() {
        val nameBinding = binding.editUserName.text.toString()
        val passwordBinding = binding.editPassword.text.toString()

        val preferences = getSharedPreferences(RegisterActivity.CREDENCIALES, MODE_PRIVATE)
        val userInJsonFormat = preferences.getString("userData", null)
        val gson = Gson()

        val user = gson.fromJson(userInJsonFormat, User::class.java)

        try {
            if (nameBinding == user.name && passwordBinding == user.password) {
                val checkbox: Boolean = binding.checkboxAutoLogin.isChecked
                val preferences = getSharedPreferences(RegisterActivity.CREDENCIALES, MODE_PRIVATE)
                val edit = preferences.edit()

                edit.putBoolean("autoLogin", checkbox)
                edit.apply()

                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)

                Toast.makeText(this, "CORRECTO!", Toast.LENGTH_LONG).show()

            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos!", Toast.LENGTH_LONG).show()
            }

        } catch (e: NullPointerException) {
            Toast.makeText(this, "No estás registrado aún!", Toast.LENGTH_LONG).show()
        }
    }
}