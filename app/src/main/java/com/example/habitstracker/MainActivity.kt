package com.example.habitstracker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

const val EXTRA_MESSAGE = "com.example.habitstracker.MESSAGE"

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        val layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = RecyclerViewAdapter(AppHabits.getHabits())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(HABITS, AppHabits.getHabits())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.getParcelableArrayList<Habit>(HABITS).let {
            if (it != null) {
                AppHabits.setHabits(it)
            }
        }
    }

    fun goCreateHabit(view: View) {
        val message = "create"
        val intent = Intent(this, HabitForm::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }

    fun goEditHabit(view: View) {
        val name = view.findViewById<TextView>(R.id.name).text.toString()
        val description = view.findViewById<TextView>(R.id.description).text.toString()
        val priority = view.findViewById<TextView>(R.id.priority).text.toString().toInt()
        val period = view.findViewById<TextView>(R.id.period_view).text.toString()
        val type = view.findViewById<TextView>(R.id.habit_type).text.toString()
        val editingHabit = Habit(name, description, priority, period, type)
        AppHabits.setEditingHabit(editingHabit)

        val message = "edit"
        val intent = Intent(this, HabitForm::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }

    companion object {
        const val HABITS = "HABITS"
    }
}