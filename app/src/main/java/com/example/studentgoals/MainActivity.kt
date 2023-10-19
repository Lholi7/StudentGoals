package com.example.studentgoals

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var goalListAdapter: GoalAdapter
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigateToDetailButton = findViewById<Button>(R.id.navigateToDetailButton)
        val navigateToSettingsButton = findViewById<Button>(R.id.navigateToSettingsButton)

        navigateToDetailButton.setOnClickListener {
            val intent = Intent(this, GoalDetailActivity::class.java)
            // Add any extras you want to pass to the GoalDetailActivity using intent.putExtra()
            startActivity(intent)
        }

        navigateToSettingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        databaseHelper = DatabaseHelper(this)

        val goalListView = findViewById<ListView>(R.id.goalListView)
        val addGoalButton = findViewById<View>(R.id.addGoalButton)

        // Create a new instance of GoalAdapter with an empty list initially
        goalListAdapter = GoalAdapter(this, ArrayList())

        goalListView.adapter = goalListAdapter

        goalListView.setOnItemClickListener { _, _, position, _ ->
            val goal = goalListAdapter.getItem(position)
            if (goal != null) {
                val intent = Intent(this, GoalDetailActivity::class.java)
                intent.putExtra("goal_title", goal.title)
                startActivity(intent)
            }
        }

        addGoalButton.setOnClickListener {
            val intent = Intent(this, GoalDetailActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Update the goal list when returning to this activity
        val goals = getGoals()
        goalListAdapter.clear()
        goalListAdapter.addAll(goals)
    }

    private fun getGoals(): List<Goal> {
        // Retrieve and return the list of goals from the database
        return databaseHelper.getAllGoals()
    }
}
