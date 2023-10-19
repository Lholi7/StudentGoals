package com.example.studentgoals

import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.After
import android.content.Context
import androidx.test.core.app.ApplicationProvider

class ExampleUnitTest {

    private lateinit var databaseHelper: DatabaseHelper

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        databaseHelper = DatabaseHelper(context)
    }

    @After
    fun tearDown() {
        databaseHelper.close()
    }

    @Test
    fun testInsertAndRetrieveGoal() {
        // Insert a goal into the database
        databaseHelper.insertGoal("Test Goal", "Test Description")

        // Retrieve the goal from the database by title
        val goal = databaseHelper.getGoalByTitle("Test Goal")

        // Check if the retrieved goal is not null and has the correct title and description
        assertNotNull(goal)
        assertEquals("Test Goal", goal?.title)
        assertEquals("Test Description", goal?.description)
    }

    @Test
    fun testUpdateGoalCompletion() {
        // Insert a goal into the database
        databaseHelper.insertGoal("Test Goal", "Test Description")

        // Update the completion status of the goal
        databaseHelper.updateGoalCompletion("Test Goal", true)

        // Retrieve the goal from the database
        val goal = databaseHelper.getGoalByTitle("Test Goal")

        // Check if the goal's completion status is updated
        assertNotNull(goal)
        assertTrue(goal?.isCompleted ?: false)
    }

    @Test
    fun testDeleteGoal() {
        // Insert a goal into the database
        databaseHelper.insertGoal("Test Goal", "Test Description")

        // Delete the goal from the database
        databaseHelper.deleteGoal("Test Goal")

        // Attempt to retrieve the goal from the database
        val goal = databaseHelper.getGoalByTitle("Test Goal")

        // Check if the goal is null after deletion
        assertNull(goal)
    }
}
