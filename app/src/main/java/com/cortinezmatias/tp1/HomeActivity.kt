package com.cortinezmatias.tp1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cortinezmatias.tp1.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menú hamburguesa
        binding.topAppBar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(android.view.Gravity.LEFT)
        }

        // Recuperamos el nombre del usuario guardado en SharedPreferences
        val preferences = getSharedPreferences(RegisterActivity.CREDENCIALES, Context.MODE_PRIVATE)
        val userName = preferences.getString("userName", "Jugador")

        // Mostramos el nombre del usuario en el TextView
        binding.textView.text = userName

        // Mostramos el nombre en el encabezado del drawer
        val headerView = binding.navigationView.getHeaderView(0)
        val headerUserName = headerView.findViewById<android.widget.TextView>(R.id.headerUserName)
        headerUserName.text = userName

        // Al hacer clic en el checkbox mostramos un mensaje
        binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "GANASTES 🏆", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Te ganó otro", Toast.LENGTH_SHORT).show()
            }
        }

        // Botón para guardar las puntuaciones
        binding.btnSaveScores.setOnClickListener {
            guardarPuntuaciones()
        }

        // Botón deslogueo
        binding.LogOut.setOnClickListener {
            val preferences = getSharedPreferences(RegisterActivity.CREDENCIALES, Context.MODE_PRIVATE)
            val editor = preferences.edit()

            // Desactivamos el autoLogin
            editor.putBoolean("autoLogin", false)
            editor.apply()

            Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()

            // Volvemos a la pantalla de inicio de sesión
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        // Clics del menú lateral
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    Toast.makeText(this, "Inicio", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_games -> {
                    // Puntuaciones
                    val intent = Intent(this, ScoresActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_account -> {
                    // Datos del usuario
                    val intent = Intent(this, AccountActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_logout -> {
                    cerrarSesion()
                }
            }
            binding.drawerLayout.closeDrawer(Gravity.START)
            true
        }
    }

    private fun cerrarSesion() {
        val preferences = getSharedPreferences(RegisterActivity.CREDENCIALES, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean("autoLogin", false)
        editor.apply()

        Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun guardarPuntuaciones() {
        val score1 = binding.editTextNumber.text.toString().toIntOrNull() ?: 0
        val score2 = binding.editTextNumber2.text.toString().toIntOrNull() ?: 0
        val score3 = binding.editTextNumber3.text.toString().toIntOrNull() ?: 0
        val ganador = binding.checkBox.isChecked
        val gameName = binding.editTextGameName.text.toString()

        val promedio = (score1 + score2 + score3) / 3.0

        val preferences = getSharedPreferences("puntuaciones", Context.MODE_PRIVATE)
        val editor = preferences.edit()

        editor.putInt("score1", score1)
        editor.putInt("score2", score2)
        editor.putInt("score3", score3)
        editor.putBoolean("ganador", ganador)
        editor.putFloat("promedio", promedio.toFloat())
        editor.putString("gameName", gameName)
        editor.apply()

        Toast.makeText(
            this,
            "Puntuaciones guardadas ✅\nPromedio: $promedio",
            Toast.LENGTH_LONG
        ).show()
    }
}