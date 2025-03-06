package ie.setu.ca1_mad2.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
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
    var exerciseDesc by remember { mutableStateOf("") }

    Column {
        Text("Workout: ${workout.name}")
        Text("Description: ${workout.description}")

        Spacer(modifier = Modifier.height(12.dp))

        Text("Exercises:")
        LazyColumn {
            items(workout.exercises) { exercise ->
                Text("• ${exercise.name} : ${exercise.muscleGroup}")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        // Add exercise to the workout
        OutlinedTextField(
            value = exerciseName,
            onValueChange = { exerciseName = it },
            label = { Text("Exercise Name") }
        )
        OutlinedTextField(
            value = exerciseDesc,
            onValueChange = { exerciseDesc = it },
            label = { Text("Exercise Description") }
        )
        Button(onClick = {
            viewModel.addExerciseToWorkout(workout.id, exerciseName, exerciseDesc)
            exerciseName = ""
            exerciseDesc = ""
        }) {
            Text("Add Exercise to Workout")
        }
    }
}