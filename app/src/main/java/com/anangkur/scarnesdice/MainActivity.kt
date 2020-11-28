package com.anangkur.scarnesdice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.anangkur.scarnesdice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var userOverallScore = 0
    private var userTurnScore = 0
    private var comOverallScore = 0
    private var comTurnScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateScore(0)
        binding.btnRoll.setOnClickListener { onRollClick() }
    }

    private fun onRollClick() {
        val randomNumber = (1..6).random()
        updateIvDice(randomNumber)
    }

    private fun updateIvDice(diceNumber: Int) {
        binding.ivDice.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                when (diceNumber) {
                    1 -> R.drawable.dice1
                    2 -> R.drawable.dice2
                    3 -> R.drawable.dice3
                    4 -> R.drawable.dice4
                    5 -> R.drawable.dice5
                    6 -> R.drawable.dice6
                    else -> 0
                }
            )
        )
        updateScore(diceNumber)
    }

    private fun updateScore(score: Int) {
        userTurnScore = if (score != 1) score else 0
        updateTvScore(userOverallScore, userTurnScore, comOverallScore)
    }

    private fun updateTvScore(
        userOverallScore: Int,
        userTurnScore: Int,
        comOverallScore: Int
    ) {
        binding.tvScore.text = getString(R.string.template_score, userOverallScore, comOverallScore, userTurnScore)
    }
}