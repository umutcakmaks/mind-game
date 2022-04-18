package com.example.mindGame.logic

import android.os.Bundle
import android.os.Handler
import com.example.mindGame.R
import com.example.mindGame.adapters.MindCardAdapter
import com.example.mindGame.data.GameRecord
import com.example.mindGame.data.GameSettings
import com.example.mindGame.data.MindCard
import com.example.mindGame.ui.MindGameFragment


class MindGameLogic (val mindCardList:ArrayList<MindCard>, private val gameSettings: GameSettings, private val mindGameView: MindGameFragment){

    var cardsPositions:ArrayList<Int> = arrayListOf()
    private var mAdapter : MindCardAdapter? = null
    private var timerIsStopped = false
    private var millis = 0L
    private var nClicks = 0
    private var nPoints = 0

    private var firstMindCard: MindCard? = null
    private var secondMindCard: MindCard? = null

    fun startGame(){
        mAdapter = mindGameView.mAdapter
        startTimer()
    }

    fun Click(position: Int) {
        val mindCard = mindCardList[position]
        cardsPositions.add(position)
        nClicks++
        if (nClicks == 1) {
            FirstClick(mindCard)
        } else if (nClicks == 2) {
            SecondClick(position, mindCard)
        }
        mindGameView.updatePointsView(nPoints)
    }

    fun SecondClick(position: Int, mindCard: MindCard) {
        if (position == cardsPositions[0]) {
            nClicks = 0
            mindCard.isVisible = false
            mAdapter?.notifyDataSetChanged()
            cardsPositions.clear()
        } else if (!mindCard.isVisible) { // we also check if we click on a card that is already visible
            secondMindCard = mindCard
            secondMindCard?.isVisible = true
            checkIfCardsMatch()
            cardsPositions.clear()
        } else {
            nClicks--
            cardsPositions.removeAt(cardsPositions.size - 1)
        }
    }


    fun FirstClick(mindCard: MindCard) {
        if (mindCard.isVisible) {
            nClicks--
            cardsPositions.remove(0)
        } else {
            firstMindCard = mindCard
            mindCard.isVisible = true
        }
    }

    private fun checkIfCardsMatch() {
        if (firstMindCard?.title.equals(secondMindCard?.title)) {
            firstMindCard = null
            secondMindCard = null
            mAdapter?.notifyDataSetChanged()
            nPoints++
            if (nPoints == gameSettings.gridSize) {
                setGameFinished()
            }
        } else {
            secondMindCard?.isVisible = true
            resetCards()
        }
        nClicks = 0
    }

    private fun resetCards() {
        firstMindCard?.isVisible = false
        secondMindCard?.isVisible = false
        firstMindCard = null
        secondMindCard = null
        mindGameView.setScreenNotTouchable()
    }

    private fun setGameFinished() {
        timerIsStopped = true
        val bundle = Bundle()
        val gameRecord = GameRecord(nPoints, millis, gameSettings)
        bundle.putParcelable(mindGameView.context?.getString(R.string.bundle_game_record), gameRecord)
        mindGameView.setGameFinished(bundle)
    }

    private fun startTimer() {
        val handler = Handler()
        val start = System.currentTimeMillis()
        val timerRunnable = object : Runnable {
            override fun run() {
                if (!timerIsStopped) {
                    millis = System.currentTimeMillis() - start
                    var seconds = (millis / 1000)
                    val minutes = seconds / 60
                    seconds %= 60
                    mindGameView.updateTimeView(minutes, seconds)

                    handler.postDelayed(this, 1000)
                }
            }
        }
        timerRunnable.run()
    }

    fun shuffleCards() {
        mindCardList.shuffle()
        mAdapter?.listCards = mindCardList
        mAdapter?.notifyDataSetChanged()
    }
}