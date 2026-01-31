package br.edu.ifpb.unipass.models

data class StudentCard(
    val id: String,
    val studentName: String,
    val institution: String,
    val course: String,
    val shift: String,
    val cardNumber: String,
    val isActive: Boolean,
    val validUntil: String,
    val photoUrl: String? = null
)
