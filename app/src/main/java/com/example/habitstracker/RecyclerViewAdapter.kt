package com.example.habitstracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter (
    private val habits: List<Habit>?
) : RecyclerView.Adapter<RecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RecyclerViewHolder(inflater.inflate(R.layout.habit_card, parent, false))
    }

    override fun getItemCount(): Int = habits?.size ?: 0

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(habits?.get(position) ?: Habit("never", "never", 0,  "never", "never"))
    }
}