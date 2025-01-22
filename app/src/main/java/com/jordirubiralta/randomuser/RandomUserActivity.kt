package com.jordirubiralta.randomuser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jordirubiralta.randomuser.ui.RandomUserApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RandomUserActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RandomUserApp()
        }
    }
}
