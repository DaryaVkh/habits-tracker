package com.example.habitstracker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView


class HabitForm : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.habit_form)

        val message = intent.getStringExtra(EXTRA_MESSAGE)
        val mode = message.toString()

        val spinner: Spinner = findViewById(R.id.priority_spinner)

        if (mode == "edit") {
            val habit = AppHabits.getEditingHabit()
            findViewById<TextView>(R.id.habit_edition_header).text = "Редактирование привычки"

            val period = habit.period?.split(' ')
            findViewById<EditText>(R.id.name_edit).setText(habit.name)
            findViewById<EditText>(R.id.description_edit).setText(habit.description)
            spinner.setSelection(habit.priority - 1)
            findViewById<EditText>(R.id.times_count_edit).setText(period?.elementAt(1))
            findViewById<EditText>(R.id.time_period_edit).setText(period?.elementAt(3))
            val radioGroup = findViewById<RadioGroup>(R.id.radio_group)
            val typeId = if (habit.type == "Полезная") R.id.radio_useful else R.id.radio_harmful
            radioGroup.check(typeId)
        } else {
            findViewById<TextView>(R.id.habit_edition_header).text = "Создание привычки"
        }
    }

    fun createHabit(view: View) {
        val habits = AppHabits.getHabits()

        var name: String = findViewById<EditText>(R.id.name_edit).text.toString()
        if (name == "") {
            name = "Привычка № ${habits.size + 1}"
        }

        var description = findViewById<EditText>(R.id.description_edit).text.toString()
        if (description == "") {
            description = "—"
        }

        var timesCount = findViewById<EditText>(R.id.times_count_edit).text.toString()
        if (timesCount == "") {
            timesCount = "1"
        }

        var timePeriod = findViewById<EditText>(R.id.time_period_edit).text.toString()
        if (timePeriod == "") {
            timePeriod = "месяц"
        }

        val priority = findViewById<Spinner>(R.id.priority_spinner).selectedItem.toString().toInt()
        val typeId = findViewById<RadioGroup>(R.id.radio_group).checkedRadioButtonId
        val type = if (typeId == R.id.radio_harmful) "Вредная" else "Полезная"

        val period = "$timesCount в $timePeriod"
        val newHabit = Habit(name, description, priority, period, type)

        AppHabits.addHabit(newHabit)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}