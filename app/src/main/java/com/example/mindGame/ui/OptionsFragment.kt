package com.example.mindGame.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mindGame.R
import com.example.mindGame.data.GameSettings
import com.example.mindGame.util.JSONReader
import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap

class OptionsFragment : Fragment(){
    lateinit var spinner: Spinner
    lateinit var radioGroup: RadioGroup
    lateinit var startGameButton:Button

    companion object{
        var gridSizes: BiMap<String, Int> = HashBiMap.create()
        const val smallGrid = "small"
        const val midGrid = "middle"
        const val bigGrid = "big"

        var speedValues : BiMap<String, Double> = HashBiMap.create()
        const val low_speed = "low"
        const val mid_speed = "middle"
        const val fast_speed = "fast"
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_options, container, false)
        spinner = rootView.findViewById(R.id.grid_size_spinner)
        radioGroup = rootView.findViewById(R.id.difficulty_radiogroup)
        startGameButton = rootView.findViewById(R.id.start_game_button)

        initializeHashMaps()
        return rootView
    }

    private fun initializeHashMaps() {
        gridSizes[smallGrid] = 8
        gridSizes[midGrid] = 12
        gridSizes[bigGrid] = 16

        speedValues[low_speed] = 2000.0
        speedValues[mid_speed] = 1000.0
        speedValues[fast_speed] = 500.0
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startGameButton.setOnClickListener{
            val selectedItem = spinner.selectedItemId
            val checkedRadioButtonId = radioGroup.checkedRadioButtonId
            val gridSize = getGridSizeFromSelectedId(selectedItem)!!
            val jsonReader = JSONReader(context!!, gridSize)
            val speed = getSpeedFromSelectedRadioButtonId(checkedRadioButtonId)!!

            val mindCardList = jsonReader.mindCardList
            if (mindCardList.isNullOrEmpty()){
                val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context!!)
                    .setPositiveButton("ok", DialogInterface.OnClickListener { dialog, _ ->
                        dialog.dismiss()
                        findNavController().navigate(R.id.mainScreen)
                    })
                    .setTitle("The file " + jsonReader.FILE_NAME + " could not be read due to an exception")
                alertDialogBuilder.create().show()
            }
            else {
                val bundle = Bundle()
                val settings = GameSettings(speed, gridSize)
                bundle.putParcelable(getString(R.string.bundle_settings), settings)
                bundle.putParcelableArrayList(getString(R.string.bundle_card_array), jsonReader.mindCardList)

                findNavController().navigate(R.id.mindGame, bundle)
            }
        }
    }

    private fun getGridSizeFromSelectedId(selectedItem:Long) = when (selectedItem){
        0L -> gridSizes[smallGrid]
       1L -> gridSizes[midGrid]
        2L -> gridSizes[bigGrid]
        else -> gridSizes[smallGrid]
    }

    private fun getSpeedFromSelectedRadioButtonId(selectedDifficulty:Int) = when (selectedDifficulty){
        R.id.easy_radiobutton -> speedValues[low_speed]
        R.id.normal_radiobutton -> speedValues[mid_speed]
        R.id.hard_radiobutton -> speedValues[fast_speed]
        else -> speedValues[low_speed]
    }
}