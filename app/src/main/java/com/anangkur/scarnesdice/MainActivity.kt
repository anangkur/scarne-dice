package com.anangkur.scarnesdice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.anangkur.scarnesdice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var userOverallScore = 0
    private var userTurnScore = 0
    private var comOverallScore = 0
    private var comTurnScore = 0
    private var randomNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateScore(0, true)
        binding.btnRoll.setOnClickListener { onRollClick(true) }
        binding.btnReset.setOnClickListener { resetScore() }
        binding.btnHold.setOnClickListener { holdScore(userTurnScore, true) }
    }

    private fun onRollClick(isUser: Boolean) {
        randomNumber = (1..6).random()
        updateIvDice(randomNumber, isUser)
    }

    private fun isComRolledAgain(): Boolean {
        return ((1..2).random() % 2 ==0)
    }

    private fun updateIvDice(diceNumber: Int, isUser: Boolean) {
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
        updateScore(diceNumber, isUser)
    }

    private fun updateScore(score: Int, isUser: Boolean) {
        if (score != 1) {
            if (isUser) userTurnScore += score else comTurnScore += score
        } else {
            changeUser(isUser)
            if (isUser) userTurnScore = 0 else comTurnScore = 0
        }
        updateTvScore(userOverallScore, userTurnScore, comOverallScore, comTurnScore, isUser)
    }

    private fun updateTvScore(
        userOverallScore: Int,
        userTurnScore: Int,
        comOverallScore: Int,
        comTurnScore: Int,
        isUser: Boolean
    ) {
        binding.tvScore.text = if (isUser)
            getString(R.string.template_score_user, userOverallScore, comOverallScore, userTurnScore)
        else
            getString(R.string.template_score_com, userOverallScore, comOverallScore, comTurnScore)
    }

    private fun resetScore() {
        userTurnScore = 0
        userOverallScore = 0
        comTurnScore = 0
        comOverallScore = 0
        updateTvScore(userOverallScore, userTurnScore, comOverallScore, comTurnScore, true)
    }

    private fun holdScore(turnScore: Int, isUser: Boolean) {
        if (isUser) {
            userOverallScore += turnScore
            userTurnScore = 0
        } else {
            comOverallScore += turnScore
            comTurnScore = 0
        }
        updateTvScore(userOverallScore, userTurnScore, comOverallScore, comTurnScore, isUser)
        changeUser(isUser)
    }

    private fun computerTurn() {
        binding.btnHold.isEnabled = false
        binding.btnRoll.isEnabled = false
        do {
            onRollClick(false)
        } while (randomNumber != 1 && isComRolledAgain())
        holdScore(comTurnScore, false)
    }

    private fun changeUser(isUser: Boolean) {
        if (isUser) {
            computerTurn()
        } else {
            binding.btnHold.isEnabled = true
            binding.btnRoll.isEnabled = true
        }
    }
}