package ie.setu.ca1_mad2.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ie.setu.ca1_mad2.GymTrackerViewModel
import ie.setu.ca1_mad2.model.Workout

@Composable
fun ListWorkoutsScreen(
    viewModel: GymTrackerViewModel,
    onWorkoutSelected: (Workout) -> Unit
) {
    val workouts = viewModel.workouts
    LazyColumn {
        items(workouts) { workout ->
            Column(
                modifier = androidx.compose.ui.Modifier.clickable {
                    onWorkoutSelected(workout)
                }
            ) {
                Text(workout.name)
                Text(workout.description)
                Text("Exercises: ${workout.exercises.size}")
            }
        }
    }
}