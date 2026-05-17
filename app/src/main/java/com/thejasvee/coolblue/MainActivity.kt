package com.thejasvee.coolblue

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.thejasvee.coolblue.ui.search.SearchRoute
import com.thejasvee.coolblue.ui.theme.CoolblueTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoolblueTheme {
                SearchRoute()
            }
        }
    }
}
