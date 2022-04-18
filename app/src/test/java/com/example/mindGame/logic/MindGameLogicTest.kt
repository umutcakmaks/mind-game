package com.example.mindGame.logic

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.mindGame.data.GameSettings
import com.example.mindGame.data.MindCard
import com.example.mindGame.ui.MindGameFragment
import com.example.mindGame.util.JSONReader
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MindGameLogicTest{
    private val context: Context = ApplicationProvider.getApplicationContext<Context>()
    private var samePairOfCards = arrayListOf<MindCard>()
    private lateinit var differentCard:MindCard
    private var secondClickPosition = 0

    @Mock
    lateinit var mindGameLogic: MindGameLogic

    @Before
    fun initGameVariables(){
        val mindGameFragment = MindGameFragment()
        val jsonReader = JSONReader(context, 10)
        val gameSettings = GameSettings(200.0, 10)
        mindGameLogic = MindGameLogic(jsonReader.mindCardList, gameSettings, mindGameFragment)
        getSamePairOfCards()
    }

    @Test
    fun game_firstClickOnInvisibleCard(){
        mindGameLogic.FirstClick(samePairOfCards[0])
        val mindCardClicked = mindGameLogic.mindCardList[0]
        assertTrue(mindCardClicked.isVisible)
    }

    fun game_firstClickOnPairedCard(){
        mindGameLogic.FirstClick(samePairOfCards[0]) //first click
        mindGameLogic.SecondClick(secondClickPosition, samePairOfCards[1]) // second click

        mindGameLogic.FirstClick(samePairOfCards[0]) // first click again on the paired card
        val mindCardClicked = mindGameLogic.mindCardList[0]
        assertTrue(mindCardClicked.isVisible)
    }

    @Test
    fun game_secondClickOnInvisibleCard(){
        mindGameLogic.FirstClick(samePairOfCards[0]) //first click
        mindGameLogic.cardsPositions.add(0)
        mindGameLogic.SecondClick(secondClickPosition, samePairOfCards[1]) // second click
        val mindCardClicked = mindGameLogic.mindCardList[secondClickPosition]
        assertTrue(mindCardClicked.isVisible)
    }

    @Test
    fun game_secondClickOnFirstClickedCard(){
        mindGameLogic.FirstClick(samePairOfCards[0]) //first click
        mindGameLogic.cardsPositions.add(0)
        mindGameLogic.SecondClick(0, samePairOfCards[0]) // second click
        val mindCardClicked = mindGameLogic.mindCardList[0]
        assertFalse(mindCardClicked.isVisible)
    }

    @Test
    fun game_secondClickOnPairedCard(){
        mindGameLogic.FirstClick(samePairOfCards[0]) //first click
        mindGameLogic.cardsPositions.add(0)
        mindGameLogic.SecondClick(secondClickPosition, samePairOfCards[1]) // second click

        val thirdMindCard = if (secondClickPosition == 1)
            mindGameLogic.mindCardList[secondClickPosition+1]
            else
                mindGameLogic.mindCardList[secondClickPosition-1]

        mindGameLogic.FirstClick(thirdMindCard) // first click on a different card
        mindGameLogic.cardsPositions.add(secondClickPosition + 1)
        mindGameLogic.SecondClick(secondClickPosition, samePairOfCards[1])

        val mindCardClicked = mindGameLogic.mindCardList[secondClickPosition]
        assertTrue(mindCardClicked.isVisible)
    }

    private fun getSamePairOfCards(){
        samePairOfCards.add(mindGameLogic.mindCardList[0])

        val sameCardTitle = samePairOfCards[0].title
        var secondCard:MindCard
        secondClickPosition = 0
        do{
            secondClickPosition ++
            secondCard = mindGameLogic.mindCardList[secondClickPosition]

        } while (!secondCard.title.equals(sameCardTitle))

        samePairOfCards.add(secondCard)
        differentCard = mindGameLogic.mindCardList.first { it.title.equals(samePairOfCards[0].title) }
    }

    @After
    fun clearVariables(){
        samePairOfCards = arrayListOf()
    }
}