package com.izamaralv.swipethebeat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.izamaralv.swipethebeat.screens.LoginScreen
import com.izamaralv.swipethebeat.screens.MainScreen
import com.izamaralv.swipethebeat.screens.SignInScreen
import com.izamaralv.swipethebeat.ui.theme.SwipeTheBeatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SwipeTheBeatTheme {
                // A surface container using the 'background' color from the theme
                Surface {
                    SignInScreen()
                }
            }
        }
    }
}

