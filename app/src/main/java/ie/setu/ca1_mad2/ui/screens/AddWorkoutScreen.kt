package ie.setu.ca1_mad2.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ie.setu.ca1_mad2.GymTrackerViewModel

@Composable
fun AddWorkoutScreen(viewModel: GymTrackerViewModel) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Workout Name") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") }
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = {
            viewModel.addWorkout(name, description)
            name = ""
            description = ""
        }) {
            Text("Add Workout")
        }
    }
}