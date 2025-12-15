package br.edu.ifpb.unipass.models

data class User(
    val name: String,
    val initials: String,
    val notificationCount: Int = 0
)
