package com.example.mindGame.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mindGame.R
import com.example.mindGame.adapters.RecordAdapter
import com.example.mindGame.data.GameRecord
import com.example.mindGame.data.database.DataBaseHelper

class FinishedFragment : Fragment(){
    private lateinit var laast_score_tv: TextView
    private lateinit var last_time_tv:TextView
    private lateinit var scores_recyclerView:RecyclerView
    private lateinit var grid_size_tv:TextView
    private lateinit var speed_tv:TextView
    private lateinit var restartButton: Button

    private lateinit var gameRecord: GameRecord

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_finished_game, container, false)
        laast_score_tv = rootView.findViewById(R.id.statistics_score)
        last_time_tv = rootView.findViewById(R.id.statistics_time)
        scores_recyclerView = rootView.findViewById(R.id.statistics_recyclerview)
        scores_recyclerView.layoutManager = LinearLayoutManager(context)
        grid_size_tv = rootView.findViewById(R.id.statistics_category)
        speed_tv = rootView.findViewById(R.id.statistics_speed)
        restartButton = rootView.findViewById(R.id.restart_button)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val databaseHelper = DataBaseHelper(context!!)

        val bundle = arguments
        if (bundle != null){
            bindViews(bundle)
            insertRecordOnDatabase(databaseHelper)
        }

        val gameRecords = getGameRecords(databaseHelper)
        setupAdapter(gameRecords)
        setListeners()
    }

    private fun setupAdapter(gameRecords: ArrayList<GameRecord>) {
        val madapter = RecordAdapter(context!!, gameRecords)
        scores_recyclerView.adapter = madapter
    }

    private fun getGameRecords(dataBaseHelper: DataBaseHelper): ArrayList<GameRecord> {
        val cursor = dataBaseHelper.getAllGameRecordsGivenSettings(gameRecord.gameSettings)
        val gameRecords = GameRecord.fillGameRecordsFromCursor(cursor)
        return gameRecords
    }

    private fun setListeners() {
        restartButton.setOnClickListener {
            findNavController().navigate(R.id.gameOptions)
        }
    }

    private fun bindViews(bundle: Bundle) {
        gameRecord = bundle.getParcelable(getString(R.string.bundle_game_record))!!
        laast_score_tv.text = gameRecord.points.toString()
        grid_size_tv.text = gameRecord.gameSettings.gridSize.toString() + " (" +OptionsFragment.gridSizes.inverse()[gameRecord.gameSettings.gridSize] + " grid)"
        speed_tv.text = OptionsFragment.speedValues.inverse()[gameRecord.gameSettings.speed]
        val millis = gameRecord.time
        var seconds = (millis / 1000)
        val minutes = seconds / 60
        seconds %= 60
        last_time_tv.text = String.format("%d:%02d", minutes, seconds)
    }

    private fun insertRecordOnDatabase(dataBaseHelper: DataBaseHelper) {
        dataBaseHelper.insertGameRecord(gameRecord)
    }
}