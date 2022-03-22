package com.example.habitstracker

object AppHabits {
    private var habits: ArrayList<Habit> = arrayListOf()
    private var editingHabit: Habit? = null

    fun addHabit(habit: Habit) {
        habits.add(habit)
        habits.sortWith(compareBy { it.priority })
    }

    fun getHabits(): ArrayList<Habit> {
        return habits
    }

    fun getEditingHabit(): Habit {
        val habit = editingHabit!!
        editingHabit = null

        habits = ArrayList(habits.filter { it.name != habit.name
                || it.description != habit.description
                || it.type != habit.type
                || it.priority != habit.priority
                || "Периодичность: ${it.period}" != habit.period
        })
        habits.sortWith(compareBy { it.priority })

        return habit
    }

    fun setHabits(savedHabits: ArrayList<Habit>) {
        habits = savedHabits
    }

    fun setEditingHabit(habit: Habit) {
        editingHabit = habit
    }
}