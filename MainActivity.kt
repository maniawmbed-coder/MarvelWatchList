package com.example.marvelwatchlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight

data class MarvelItem(
    val title: String,
    val year: Int,
    val phase: String
)

val marvelItems = listOf(
    MarvelItem("Iron Man", 2008, "فاز ۱"),
    MarvelItem("The Incredible Hulk", 2008, "فاز ۱"),
    MarvelItem("Iron Man 2", 2010, "فاز ۱"),
    MarvelItem("Thor", 2011, "فاز ۱"),
    MarvelItem("Captain America: The First Avenger", 2011, "فاز ۱"),
    MarvelItem("The Avengers", 2012, "فاز ۱"),
    MarvelItem("Iron Man 3", 2013, "فاز ۲"),
    MarvelItem("Thor: The Dark World", 2013, "فاز ۲"),
    MarvelItem("Captain America: The Winter Soldier", 2014, "فاز ۲"),
    MarvelItem("Guardians of the Galaxy", 2014, "فاز ۲"),
    MarvelItem("Avengers: Age of Ultron", 2015, "فاز ۲"),
    MarvelItem("Ant-Man", 2015, "فاز ۲"),
    MarvelItem("Captain America: Civil War", 2016, "فاز ۳"),
    MarvelItem("Doctor Strange", 2016, "فاز ۳"),
    MarvelItem("Guardians of the Galaxy Vol. 2", 2017, "فاز ۳"),
    MarvelItem("Spider-Man: Homecoming", 2017, "فاز ۳"),
    MarvelItem("Thor: Ragnarok", 2017, "فاز ۳"),
    MarvelItem("Black Panther", 2018, "فاز ۳"),
    MarvelItem("Avengers: Infinity War", 2018, "فاز ۳"),
    MarvelItem("Ant-Man and the Wasp", 2018, "فاز ۳"),
    MarvelItem("Captain Marvel", 2019, "فاز ۳"),
    MarvelItem("Avengers: Endgame", 2019, "فاز ۳")
)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MarvelWatchListApp()
        }
    }
}

@Composable
fun MarvelWatchListApp() {

    var searchText by remember {
        mutableStateOf("")
    }

    var watched by remember {
        mutableStateOf(setOf<String>())
    }

    val filteredItems = marvelItems.filter {
        it.title.contains(searchText, ignoreCase = true) ||
        it.phase.contains(searchText)
    }

    val progress =
        (watched.size * 100) / marvelItems.size

    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Color(0xFFE53935),
            background = Color(0xFF0B0B0F),
            surface = Color(0xFF15151C)
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0B0B0F))
                .padding(16.dp)
        ) {

            Text(
                text = "MARVEL",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Watch List",
                color = Color.LightGray
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "پیشرفت تماشا: $progress%",
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(7.dp))

            LinearProgressIndicator(
                progress = { progress / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )

            Spacer(modifier = Modifier.height(15.dp))

            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = {
                    Text("جست‌وجوی فیلم")
                }
            )

            Spacer(modifier = Modifier.height(15.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                items(
                    filteredItems,
                    key = { it.title }
                ) { item ->

                    val isWatched =
                        watched.contains(item.title)

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                                watched =
                                    if (isWatched) {
                                        watched - item.title
                                    } else {
                                        watched + item.title
                                    }
                            },

                        shape = RoundedCornerShape(15.dp),

                        colors = CardDefaults.cardColors(
                            containerColor =
                                if (isWatched)
                                    Color(0xFF20252A)
                                else
                                    Color(0xFF15151C)
                        )
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp),

                            verticalAlignment =
                                Alignment.CenterVertically
                        ) {

                            Text(
                                text =
                                    if (isWatched)
                                        "✓"
                                    else
                                        "○",

                                fontSize = 25.sp,

                                color =
                                    if (isWatched)
                                        Color(0xFF4CAF50)
                                    else
                                        Color(0xFFE53935)
                            )

                            Spacer(
                                modifier =
                                    Modifier.width(12.dp)
                            )

                            Column(
                                modifier =
                                    Modifier.weight(1f)
                            ) {

                                Text(
                                    text = item.title,
                                    fontWeight =
                                        FontWeight.Bold
                                )

                                Spacer(
                                    modifier =
                                        Modifier.height(4.dp)
                                )

                                Text(
                                    text =
                                        "${item.year} • ${item.phase}",

                                    color =
                                        Color.LightGray,

                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
