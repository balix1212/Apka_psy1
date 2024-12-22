package com.example.student_lista

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import com.example.student_lista.ui.theme.Student_listaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Student_listaTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "dog_list") {
                    composable("dog_list") {
                        DogListApp(navController)
                    }
                    composable("dog_details/{dogName}") { backStackEntry ->
                        val dogName = backStackEntry.arguments?.getString("dogName")
                        dogName?.let {
                            DogDetailsScreen(
                                dogName = it,
                                onDelete = {
                                    println("Usuwam psa: $dogName")
                                },
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}




