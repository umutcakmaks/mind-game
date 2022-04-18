package com.example.mindGame.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.example.mindGame.R
import com.example.mindGame.data.MindCard
import com.example.mindGame.logic.MindGameLogic
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.mind_card_hidden_item.view.*


class MindCardAdapter(var listCards: ArrayList<MindCard>, var mcontext: Context?, private var mindGame: MindGameLogic) :
    BaseAdapter() {
    private var listCard = ArrayList<ImageView>()

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val thisCard = listCards[position]
        val inflater = mcontext!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val cardview = inflater.inflate(R.layout.mind_card_hidden_item, null)
        listCard.add(cardview.card_item)

        cardview.setOnClickListener{
            cardview.card_item.background = null
            Picasso.get()
                .load(thisCard.image)
                .placeholder(R.drawable.diamond)
                .into(cardview.card_item)
            mindGame.Click(position)
        }

        if (thisCard.isVisible){
            val url = thisCard.image
            Picasso.get()
                .load(url)
                .placeholder(R.drawable.diamond)
                .into(cardview.card_item)
        }
        else{
            Picasso.get().load(R.drawable.diamond)
                .placeholder(R.drawable.diamond)
                .into(cardview.card_item);
        }

        return cardview
    }

    override fun getItem(position: Int): Any {
        return listCards[position]
    }

    override fun getItemId(position: Int): Long {
        return listCards[position].id
    }

    override fun getCount(): Int {
        return listCards.size
    }



}