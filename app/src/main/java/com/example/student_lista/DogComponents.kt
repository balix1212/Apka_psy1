package com.example.student_lista

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.filled.ArrowBack
import androidx.navigation.NavController

@Composable
fun DogInputRow(
    textFieldValue: String,
    onTextChange: (String) -> Unit,
    onAddDog: () -> Unit,
    onToggleSearch: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = onTextChange,
            label = { Text("Imię Psa") },
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(onClick = onAddDog) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Dodaj Psa")
        }

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(onClick = onToggleSearch) {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Szukaj Pieska")
        }
    }
}

@Composable
fun SearchField(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Szukaj Pieska") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    )
}

@Composable
fun DuplicateError() {
    Text(
        text = "Pies już istnieje!",
        color = Color.Red,
        modifier = Modifier.padding(top = 4.dp)
    )
}

@Composable
fun DogList(
    favoriteDogs: List<String>,
    nonFavoriteDogs: List<String>,
    onDelete: (String) -> Unit,
    onFavoriteToggle: (String) -> Unit,
    navController: NavController
) {
    LazyColumn {
        items(favoriteDogs) { dogName ->
            DogListItem(
                dogName = dogName,
                isFavorite = true,
                onDelete = { onDelete(dogName) },
                onFavorite = { onFavoriteToggle(dogName) },
                navController = navController
            )
        }

        items(nonFavoriteDogs) { dogName ->
            DogListItem(
                dogName = dogName,
                isFavorite = false,
                onDelete = { onDelete(dogName) },
                onFavorite = { onFavoriteToggle(dogName) },
                navController = navController
            )
        }
    }
}

@Composable
fun DogCountInfo(totalDogs: Int, favoriteDogs: Int) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "\uD83D\uDC15", fontSize = 20.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "$totalDogs", fontWeight = FontWeight.Bold)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "\u2764", fontSize = 20.sp, color = Color.Red)
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "$favoriteDogs", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun DogListItem(
    dogName: String,
    isFavorite: Boolean,
    onDelete: () -> Unit,
    onFavorite: () -> Unit,
    navController: NavController
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color(0xFFECEFF1))
            .padding(8.dp)
            .clickable {
                navController.navigate("dog_details/$dogName")
            }
    ) {
        Text(
            text = "\uD83D\uDC15 $dogName",
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )

        IconButton(onClick = onFavorite) {
            Icon(
                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = null,
                tint = if (isFavorite) Color.Red else Color.Gray
            )
        }

        IconButton(onClick = onDelete) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = null, tint = Color.Red)
        }
    }
}

@Composable
fun DogDetailsScreen(dogName: String, onDelete: () -> Unit, navController: NavController) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {

                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Powrót")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Szczegóły",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                IconButton(onClick = onDelete) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Usuń Psa")
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = dogName,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "\uD83D\uDC15",
                    fontSize = 100.sp
                )
            }
        }
    }
}


