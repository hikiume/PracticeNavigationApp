package com.example.practicenavigationapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.practicenavigationapp.ui.theme.PracticeNavigationAppTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticeNavigationAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavHost()
                }
            }
        }
    }
}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Home
    ) {
        composable<Home> {
            HomeScreen(
                onNext = { itemId ->
                    navController.navigate(Detail(itemId = "Coffee"))
                }
            )
        }
        composable<Detail> { backStackEntry ->
            val detail = backStackEntry.toRoute<Detail>()
            DetailScreen(
                itemId = detail.itemId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun HomeScreen(onNext: (String) -> Unit) {
    Button(onClick = { onNext("Coffee") }) {
        Text("次へ")
    }
}

@Composable
fun DetailScreen(itemId: String, onBack: () -> Unit) {
    Column {
        Text(itemId)
        Button(onClick = onBack) {
            Text("戻る")
        }
    }
}

@Serializable
object Home

@Serializable
data class Detail(val itemId: String)

