package com.example.studentgoals

data class Goal(
    val id: Int,
    val title: String,
    val description: String,
    val isCompleted: Boolean // Use Int to represent 0 or 1 for completion status
)
