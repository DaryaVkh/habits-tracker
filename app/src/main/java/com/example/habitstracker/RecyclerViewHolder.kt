package com.example.habitstracker

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val nameView: TextView = itemView.findViewById(R.id.name_view)
    private val descriptionView: TextView = itemView.findViewById(R.id.description_view)
    private val priorityView: TextView = itemView.findViewById(R.id.priority_view)
    private val periodView: TextView = itemView.findViewById(R.id.period_view)

    private val name: TextView = itemView.findViewById(R.id.name)
    private val description: TextView = itemView.findViewById(R.id.description)
    private val timesCount: TextView = itemView.findViewById(R.id.times_count)
    private val timePeriod: TextView = itemView.findViewById(R.id.time_period)
    private val priority: TextView = itemView.findViewById(R.id.priority)
    private val type: TextView = itemView.findViewById(R.id.habit_type)

    fun bind(habit: Habit) {
        nameView.text = "Название: ${habit.name}"
        descriptionView.text = habit.description
        priorityView.text = "[priority ${habit.priority}]"
        periodView.text = "Периодичность: ${habit.period}"

        name.text = habit.name
        description.text = habit.description
        priority.text = habit.priority.toString()
        val period = habit.period?.split(' ')
        timesCount.text = period?.get(0) ?: ""
        timePeriod.text = period?.get(2) ?: ""
        type.text = habit.type

        val card = itemView.findViewById<CardView>(R.id.cv)
        if (habit.type == "Полезная") {
            card.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.green))
        } else {
            card.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.red))
        }
    }
}