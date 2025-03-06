package ie.setu.ca1_mad2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ie.setu.ca1_mad2.ui.screens.AddExerciseScreen
import ie.setu.ca1_mad2.ui.screens.ListExerciseScreen
import ie.setu.ca1_mad2.ui.theme.CA1MAD2Theme
import androidx.lifecycle.viewmodel.compose.viewModel
import ie.setu.ca1_mad2.model.Workout
import ie.setu.ca1_mad2.ui.screens.AddWorkoutScreen
import ie.setu.ca1_mad2.ui.screens.ListWorkoutsScreen
import ie.setu.ca1_mad2.ui.screens.WorkoutDetailScreen


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
    var selectedWorkout by remember { mutableStateOf<Workout?>(null) }

    Scaffold(
        // Top bar
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Gym Tracker",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        // Bottom bar
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { currentScreen = "addWorkout" },
                        modifier = Modifier.size(width = 120.dp, height = 50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Text("Add Workout")
                    }
                    Button(
                        onClick = { currentScreen = "listWorkouts" },
                        modifier = Modifier.size(width = 120.dp, height = 50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Text("List Workouts")
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (currentScreen) {
                "addWorkout" -> {
                    AddWorkoutScreen(viewModel)
                }
                "listWorkouts" -> {
                    ListWorkoutsScreen(
                        viewModel = viewModel,
                        onWorkoutSelected = { workout ->
                            selectedWorkout = workout
                            currentScreen = "workoutDetail"
                        }
                    )
                }
                "workoutDetail" -> {
                    selectedWorkout?.let { workout ->
                        WorkoutDetailScreen(viewModel, workout)
                    }
                }
            }
        }
    }
}