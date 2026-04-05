package com.cortinezmatias.tp1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.cortinezmatias.tp1.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    companion object {
        const val CREDENCIALES = "credenciales_usuario"
    }

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            saveUserData()
        }
    }

    private fun saveUserData() {
        val username = binding.editTextUser.text.toString()
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()

        // Validaciones básicas
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        // Crear el objeto User
        val user = User(username, email, password)

        // Guardarlo como JSON en SharedPreferences
        val gson = Gson()
        val jsonUser = gson.toJson(user)

        val preferences = getSharedPreferences(CREDENCIALES, MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString("userData", jsonUser)
        editor.putString("userName", username)
        editor.putBoolean("autoLogin", false)
        editor.apply()

        Toast.makeText(this, "Usuario registrado correctamente!", Toast.LENGTH_SHORT).show()

        // Volver al login automáticamente
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
