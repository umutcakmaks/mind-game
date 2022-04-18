package com.example.mindGame.util

import android.content.Context
import android.util.Log
import com.example.mindGame.data.MindCard
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream

class JSONReader (val context: Context, val gridSize:Int) {
    val FILE_NAME = "products.json"
    private val PRODUCTS_ARRAY = "products"
    private val PRODUCT_ID = "id"
    private val PRODUCT_TITLE = "title"
    private val PRODUCT_IMAGE = "image"
    private val PRODUCT_IMAGE_SRC = "src"
    var mindCardList = arrayListOf<MindCard>()

    init {
        readJSONFile()
    }

    private fun readJSONFile(){
        try {
            val inputStream: InputStream = context.assets.open(FILE_NAME)
            val inputString = inputStream.bufferedReader().use{it.readText()}
            val jsonObject = JSONObject(inputString)
            val jsonArray: JSONArray = jsonObject.getJSONArray(PRODUCTS_ARRAY)
            for (i in 0 until gridSize){
                val mindCardObject = jsonArray.getJSONObject(i)
                val id = mindCardObject.getLong(PRODUCT_ID)
                val title = mindCardObject.getString(PRODUCT_TITLE)
                val imageArray = mindCardObject.getJSONObject(PRODUCT_IMAGE)
                val imageSrc = imageArray.getString(PRODUCT_IMAGE_SRC)
                val mindCard = MindCard(id, title, imageSrc)
                mindCardList.add(mindCard)
                mindCardList.add(mindCard.copy())
            }
            mindCardList.shuffle()
        } catch (e:Exception){
            Log.d("JSON Exception", e.toString())

        }

    }

}