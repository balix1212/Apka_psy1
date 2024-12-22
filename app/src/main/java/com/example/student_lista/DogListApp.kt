package com.example.student_lista

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.*

@Composable
fun DogListApp(navController: NavController) {
    var textFieldValue by remember { mutableStateOf("") }
    var dogs by remember { mutableStateOf(setOf<String>()) }
    var favoriteDogs by remember { mutableStateOf(setOf<String>()) }
    var isDuplicate by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var isSearchVisible by remember { mutableStateOf(false) }

    val filteredDogs = dogs.filter { it.contains(searchQuery, ignoreCase = true) }
    val favoriteDogList = filteredDogs.filter { it in favoriteDogs }
    val nonFavoriteDogList = filteredDogs.filter { it !in favoriteDogs }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            DogInputRow(
                textFieldValue = textFieldValue,
                onTextChange = {
                    textFieldValue = it
                    isDuplicate = false
                },
                onAddDog = {
                    if (textFieldValue.isNotEmpty() && !dogs.contains(textFieldValue)) {
                        dogs = setOf(textFieldValue) + dogs
                        textFieldValue = ""
                        isDuplicate = false
                    } else {
                        isDuplicate = true
                    }
                },
                onToggleSearch = { isSearchVisible = !isSearchVisible }
            )

            if (isSearchVisible) {
                SearchField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it }
                )
            }

            if (isDuplicate) {
                DuplicateError()
            }

            Spacer(modifier = Modifier.height(16.dp))

            DogCountInfo(
                totalDogs = dogs.size,
                favoriteDogs = favoriteDogs.size
            )

            DogList(
                favoriteDogs = favoriteDogList,
                nonFavoriteDogs = nonFavoriteDogList,
                onDelete = { dogName ->
                    dogs = dogs.minus(dogName)
                    favoriteDogs = favoriteDogs.minus(dogName)
                },
                onFavoriteToggle = { dogName ->
                    favoriteDogs = if (dogName in favoriteDogs) {
                        favoriteDogs.minus(dogName)
                    } else {
                        favoriteDogs + dogName
                    }
                },
                navController = navController
            )
        }
    }
}



