package ie.setu.ca1_mad2.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import ie.setu.ca1_mad2.GymTrackerViewModel

@Composable
fun AddExerciseScreen(viewModel: GymTrackerViewModel) {
    var name by remember { mutableStateOf("") }
    var muscleGroup by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Exercise Name") }
        )
        OutlinedTextField(
            value = muscleGroup,
            onValueChange = { muscleGroup = it },
            label = { Text("Exercise Muscle Group") }
        )
        Button(onClick = {
            viewModel.addExercise(name, muscleGroup)

            // Clear fields
            name = ""
            muscleGroup = ""
        }) {
            Text("Add Exercise")
        }
    }
}