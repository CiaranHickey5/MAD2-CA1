package ie.setu.ca1_mad2.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ie.setu.ca1_mad2.GymTrackerViewModel
import ie.setu.ca1_mad2.model.Exercise


@Composable
fun ListExerciseScreen(viewModel: GymTrackerViewModel) {
    val exercises: List<Exercise> = viewModel.exercises
    LazyColumn {
        items(exercises) { exercise ->
            Text("• ${exercise.name} - ${exercise.muscleGroup}")
        }
    }
}