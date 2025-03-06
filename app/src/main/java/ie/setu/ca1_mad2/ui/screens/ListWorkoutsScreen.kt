package ie.setu.ca1_mad2.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ie.setu.ca1_mad2.GymTrackerViewModel
import ie.setu.ca1_mad2.model.Workout

@Composable
fun ListWorkoutsScreen(
    viewModel: GymTrackerViewModel,
    onWorkoutSelected: (Workout) -> Unit
) {
    val workouts = viewModel.workouts

    LazyColumn(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp) // gap between items
    ) {
        items(workouts) { workout ->
            // Clickable column for each workout
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onWorkoutSelected(workout) }
            ) {
                Text(
                    text = "Workout Title:",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = workout.name,
                    style = MaterialTheme.typography.headlineSmall // bigger text
                )
                Text(
                    text = "Description: ${workout.description}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Exercises in this workout: ${workout.exercises.size}",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}
