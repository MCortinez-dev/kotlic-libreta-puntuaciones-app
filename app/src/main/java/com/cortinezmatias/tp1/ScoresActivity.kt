package com.cortinezmatias.tp1

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cortinezmatias.tp1.databinding.ActivityScoresBinding

class ScoresActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScoresBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoresBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferences = getSharedPreferences("puntuaciones", Context.MODE_PRIVATE)

        val score1 = preferences.getInt("score1", 0)
        val score2 = preferences.getInt("score2", 0)
        val score3 = preferences.getInt("score3", 0)
        val promedio = preferences.getFloat("promedio", 0f)
        val gameName = preferences.getString("gameName", "Juego desconocido")

        binding.textScores.text = """
            Juego: $gameName
            Puntuación 1: $score1
            Puntuación 2: $score2
            Puntuación 3: $score3
            Promedio: $promedio
        """.trimIndent()
    }
}