package ie.setu.ca1_mad2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ie.setu.ca1_mad2.GymTrackerViewModel
import ie.setu.ca1_mad2.model.Workout

@Composable
fun WorkoutDetailScreen(
    viewModel: GymTrackerViewModel,
    workout: Workout
) {
    var exerciseName by remember { mutableStateOf("") }
    var exerciseMuscleGroup by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Workout Title:",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = workout.name,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "Description: ${workout.description}",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Exercises in This Workout:",
            style = MaterialTheme.typography.titleMedium
        )
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(workout.exercises) { exercise ->
                Column {
                    Text(
                        text = "Name: ${exercise.name}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Description: ${exercise.muscleGroup}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Add new exercise
        OutlinedTextField(
            value = exerciseName,
            onValueChange = { exerciseName = it },
            label = { Text("Exercise Name") }
        )
        OutlinedTextField(
            value = exerciseMuscleGroup,
            onValueChange = { exerciseMuscleGroup = it },
            label = { Text("Exercise Muscle Group") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            viewModel.addExerciseToWorkout(workout.id, exerciseName, exerciseMuscleGroup)
            exerciseName = ""
            exerciseMuscleGroup = ""
        }) {
            Text("Add Exercise to This Workout")
        }
    }
}
