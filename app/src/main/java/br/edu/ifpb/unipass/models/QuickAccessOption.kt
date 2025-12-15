package br.edu.ifpb.unipass.models

import androidx.compose.ui.graphics.vector.ImageVector

data class QuickAccessOption(
    val icon: ImageVector,
    val label: String,
    val onClick: () -> Unit
)
