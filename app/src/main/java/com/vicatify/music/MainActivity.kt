
package com.vicatify.music

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val DarkBg = Color(0xFF101010)
private val CardBg = Color(0xFF202020)
private val Accent = Color(0xFF32D583)

data class Song(
    val title: String,
    val artist: String,
    val language: String,
    val genre: String,
    val url: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VicatifyApp()
        }
    }
}

@Composable
fun VicatifyApp() {
    val songs = remember {
        listOf(
            Song(
                "Contoh Lagu Indonesia",
                "Artis Indonesia",
                "Indonesia",
                "Pop",
                "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
            ),
            Song(
                "Example English Song",
                "Example Artist",
                "English",
                "Pop",
                "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3"
            ),
            Song(
                "Contoh Lagu DJ",
                "DJ Example",
                "Instrumental",
                "DJ",
                "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3"
            )
        )
    }

    var currentIndex by remember { mutableIntStateOf(0) }
    var smartQueue by remember { mutableStateOf(true) }
    var status by remember {
        mutableStateOf("Siap memutar")
    }

    val currentSong = songs[currentIndex]

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = DarkBg
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(22.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                Text(
                    "vicatify",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Accent
                )

                Text(
                    "Your music, your flow.",
                    color = Color.LightGray
                )

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = CardBg
                    ),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement =
                            Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(210.dp)
                                .background(
                                    Color(0xFF30352F),
                                    RoundedCornerShape(18.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "♫",
                                fontSize = 100.sp,
                                color = Accent
                            )
                        }

                        Text(
                            currentSong.title,
                            fontSize = 23.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Text(
                            currentSong.artist,
                            color = Color.LightGray
                        )

                        Text(
                            "${currentSong.language} • ${currentSong.genre}",
                            color = Accent
                        )

                        Text(
                            status,
                            color = Color.LightGray,
                            fontSize = 13.sp
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement =
                                Arrangement.SpaceEvenly,
                            verticalAlignment =
                                Alignment.CenterVertically
                        ) {
                            TextButton(
                                onClick = {
                                    currentIndex =
                                        (currentIndex - 1 + songs.size) %
                                            songs.size
                                    status = "Lagu dipilih"
                                }
                            ) {
                                Text("⏮", fontSize = 25.sp)
                            }

                            Button(
                                onClick = {
                                    status = "Pemutar audio belum dipasang"
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Accent
                                )
                            ) {
                                Text(
                                    "▶",
                                    color = Color.Black,
                                    fontSize = 24.sp
                                )
                            }

                            TextButton(
                                onClick = {
                                    currentIndex =
                                        (currentIndex + 1) % songs.size
                                    status = "Lagu berikutnya"
                                }
                            ) {
                                Text("⏭", fontSize = 25.sp)
                            }
                        }
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "Smart Queue",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "Bahasa sama, genre serupa",
                            color = Color.LightGray,
                            fontSize = 12.sp
                        )
                    }

                    Switch(
                        checked = smartQueue,
                        onCheckedChange = {
                            smartQueue = it
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Accent
                        )
                    )
                }

                Button(
                    onClick = {
                        val next = if (smartQueue) {
                            songs.indices.firstOrNull {
                                it != currentIndex &&
                                songs[it].language ==
                                    currentSong.language &&
                                songs[it].genre == currentSong.genre
                            }
                        } else {
                            null
                        }

                        currentIndex = next
                            ?: ((currentIndex + 1) % songs.size)

                        status = "Lagu berikutnya dipilih"
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CardBg
                    )
                ) {
                    Text(
                        "Cari lagu berikutnya ♫",
                        color = Color.White
                    )
                }

                Text(
                    "Vicatify • Prototype",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }
    }
}
