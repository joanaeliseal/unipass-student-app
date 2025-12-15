package br.edu.ifpb.unipass

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import br.edu.ifpb.unipass.navigation.AppNavHost
import br.edu.ifpb.unipass.ui.theme.UnipassTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            UnipassTheme {
                AppNavHost()
                }
            }
        }
    }

