package com.example.studentgoals

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class GoalDetailActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var goalTitleEditText: EditText
    private lateinit var goalDescriptionEditText: EditText
    private lateinit var completedCheckBox: CheckBox
    private lateinit var saveGoalButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_detail)

        databaseHelper = DatabaseHelper(this)

        goalTitleEditText = findViewById(R.id.titleEditText)
        goalDescriptionEditText = findViewById(R.id.descriptionEditText)
        completedCheckBox = findViewById(R.id.completedCheckBox)
        saveGoalButton = findViewById(R.id.saveGoalButton)

        val intent = intent
        if (intent.hasExtra("goal_title")) {
            val goalTitle = intent.getStringExtra("goal_title")
            displayGoalDetails(goalTitle)
        }

        saveGoalButton.setOnClickListener {
            val goalTitle = goalTitleEditText.text.toString()
            val goalDescription = goalDescriptionEditText.text.toString()
            val isCompleted = completedCheckBox.isChecked

            if (goalTitle.isNotEmpty()) {
                // Save the goal to the database
                databaseHelper.insertGoal(goalTitle, goalDescription)
                if (intent.hasExtra("goal_title")) {
                    // If editing an existing goal, delete the old goal
                    val oldGoalTitle = intent.getStringExtra("goal_title")
                    if (oldGoalTitle != null) {
                        databaseHelper.deleteGoal(oldGoalTitle)
                    }
                }

                // Optionally, you can display a success message or navigate back to the main activity.
                Toast.makeText(this, "Goal saved successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                // Handle case where the title is empty
                Toast.makeText(this, "Please enter a goal title", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displayGoalDetails(title: String?) {
        if (title != null) {
            val goal = databaseHelper.getGoalByTitle(title)
            goalTitleEditText.setText(goal?.title)
            goalDescriptionEditText.setText(goal?.description)
            completedCheckBox.isChecked = goal?.isCompleted == true
        }
    }
}



