package com.kaymanov.myapplication

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class CardsAdapter : RecyclerView.Adapter<CardsAdapter.CardViewHolder>() {

    private var numbers = ArrayList<Int>()

    fun setNumbers(numbers : ArrayList<Int>) {
        this.numbers = numbers
        notifyItemInserted(numbers.size-1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)

        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.tvCount.text = numbers.get(position).toString()
        if(numbers[position]%2==0){
            holder.card.setCardBackgroundColor(Color.RED)
        } else {
            holder.card.setCardBackgroundColor(Color.CYAN)
        }
    }

    override fun getItemCount(): Int {
        return numbers.size
    }


    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvCount = itemView.findViewById<TextView>(R.id.tvCount)
        var card = itemView.findViewById<CardView>(R.id.card)
    }
}