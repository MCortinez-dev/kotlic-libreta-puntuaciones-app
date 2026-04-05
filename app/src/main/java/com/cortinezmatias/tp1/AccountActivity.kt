package com.cortinezmatias.tp1

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cortinezmatias.tp1.databinding.ActivityAccountBinding
import com.google.gson.Gson

class AccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferences = getSharedPreferences(RegisterActivity.CREDENCIALES, Context.MODE_PRIVATE)
        val userJson = preferences.getString("userData", null)
        val gson = Gson()

        if (userJson != null) {
            val user = gson.fromJson(userJson, User::class.java)
            binding.textAccountData.text = """
                Nombre: ${user.name}
                Email: ${user.email}
                Contraseña: ${user.password}
            """.trimIndent()
        } else {
            binding.textAccountData.text = "No hay datos del usuario"
        }
    }
}