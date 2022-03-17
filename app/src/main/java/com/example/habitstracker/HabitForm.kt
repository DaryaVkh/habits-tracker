package com.example.habitstracker

import android.app.Activity
import android.content.Intent
import android.os.Bundle

import android.view.View
import android.widget.*


class HabitForm : Activity() {
    private var priority: Int = 1
    private var type: String = "Полезная"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.habit_form)

        val message = intent.getStringExtra(EXTRA_MESSAGE)
        val mode = message.toString()

        val spinner: Spinner = findViewById(R.id.priority_spinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.priority_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View?, selectedItemPosition: Int, selectedId: Long
            ) {
                val choose = resources.getStringArray(R.array.priority_array)
                priority = choose[selectedItemPosition].toInt()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

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
            type = habit.type!!
            radioGroup.check(typeId)
        } else {
            findViewById<TextView>(R.id.habit_edition_header).text = "Создание привычки"
        }
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked

            when (view.getId()) {
                R.id.radio_useful ->
                    if (checked) {
                        type = "Полезная"
                    }
                R.id.radio_harmful ->
                    if (checked) {
                        type = "Вредная"
                    }
            }
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

        val period = "$timesCount в $timePeriod"
        val newHabit = Habit(name, description, priority, period, type)

        AppHabits.addHabit(newHabit)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}