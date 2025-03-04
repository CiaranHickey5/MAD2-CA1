package ie.setu.ca1_mad2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ie.setu.ca1_mad2.ui.screens.AddExerciseScreen
import ie.setu.ca1_mad2.ui.screens.ListExerciseScreen
import ie.setu.ca1_mad2.ui.theme.CA1MAD2Theme
import androidx.lifecycle.viewmodel.compose.viewModel



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CA1MAD2Theme {
                val gymViewModel: GymTrackerViewModel = viewModel()
                GymTrackApp(gymViewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GymTrackApp(viewModel: GymTrackerViewModel) {
    var currentScreen by remember { mutableStateOf("add") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gym Tracker") }
            )
        },
        bottomBar = {
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(
                    onClick = { currentScreen = "add" },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Add Exercise")
                }
                Button(
                    onClick = { currentScreen = "list" },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("List Exercises")
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (currentScreen) {
                "add" -> AddExerciseScreen(viewModel)
                "list" -> ListExerciseScreen(viewModel)
            }
        }
    }
}