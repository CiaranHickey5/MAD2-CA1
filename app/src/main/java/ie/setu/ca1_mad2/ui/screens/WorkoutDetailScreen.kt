package ie.setu.ca1_mad2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ie.setu.ca1_mad2.GymTrackerViewModel
import ie.setu.ca1_mad2.model.Exercise
import ie.setu.ca1_mad2.model.Workout
import kotlinx.coroutines.delay

@Composable
fun WorkoutDetailScreen(
    viewModel: GymTrackerViewModel,
    workout: Workout
) {
    var exerciseName by remember { mutableStateOf("") }
    var exerciseMuscleGroup by remember { mutableStateOf("") }
    var exerciseAdded by remember { mutableStateOf(false) }

    // States for edit dialog
    var showEditDialog by remember { mutableStateOf(false) }
    var exerciseToEdit by remember { mutableStateOf<Exercise?>(null) }
    var editedName by remember { mutableStateOf("") }
    var editedMuscleGroup by remember { mutableStateOf("") }

    // States for delete dialog
    var showDeleteDialog by remember { mutableStateOf(false) }
    var exerciseToDelete by remember { mutableStateOf<Exercise?>(null) }

    // Edit Exercise Dialog
    if (showEditDialog && exerciseToEdit != null) {
        AlertDialog(
            onDismissRequest = {
                showEditDialog = false
                exerciseToEdit = null
            },
            title = { Text("Edit Exercise") },
            text = {
                Column {
                    OutlinedTextField(
                        value = editedName,
                        onValueChange = { editedName = it },
                        label = { Text("Exercise Name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = editedMuscleGroup,
                        onValueChange = { editedMuscleGroup = it },
                        label = { Text("Muscle Group") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        exerciseToEdit?.let { exercise ->
                            viewModel.updateWorkoutExercise(
                                workoutId = workout.id,
                                exerciseId = exercise.id,
                                newName = editedName,
                                newMuscleGroup = editedMuscleGroup
                            )
                        }
                        showEditDialog = false
                        exerciseToEdit = null
                    },
                    enabled = editedName.isNotBlank()
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = {
                    showEditDialog = false
                    exerciseToEdit = null
                }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Delete Exercise Dialog
    if (showDeleteDialog && exerciseToDelete != null) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
                exerciseToDelete = null
            },
            title = { Text("Delete Exercise") },
            text = { Text("Are you sure you want to remove '${exerciseToDelete?.name}' from this workout?") },
            confirmButton = {
                Button(
                    onClick = {
                        exerciseToDelete?.let { exercise ->
                            viewModel.removeExerciseFromWorkout(
                                workoutId = workout.id,
                                exerciseId = exercise.id
                            )
                        }
                        showDeleteDialog = false
                        exerciseToDelete = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = {
                    showDeleteDialog = false
                    exerciseToDelete = null
                }) {
                    Text("Cancel")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Workout Header
        Text(
            text = workout.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        if (workout.description.isNotBlank()) {
            Text(
                text = workout.description,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
            )
        } else {
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Exercises Section
        Text(
            text = "Exercises",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
        )

        if (workout.exercises.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant,
                        RoundedCornerShape(8.dp)
                    )
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No exercises added to this workout yet",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(workout.exercises) { exercise ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            // Exercise header with edit and delete buttons
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = exercise.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(1f)
                                )

                                // Edit button
                                IconButton(
                                    onClick = {
                                        exerciseToEdit = exercise
                                        editedName = exercise.name
                                        editedMuscleGroup = exercise.muscleGroup
                                        showEditDialog = true
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Edit Exercise",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }

                                // Delete button
                                IconButton(
                                    onClick = {
                                        exerciseToDelete = exercise
                                        showDeleteDialog = true
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete Exercise",
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            }

                            Text(
                                text = "Muscle Group: ${exercise.muscleGroup}",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            }
        }

        // Add Exercise Form
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Add New Exercise",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = exerciseName,
            onValueChange = { exerciseName = it },
            label = { Text("Exercise Name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = exerciseMuscleGroup,
            onValueChange = { exerciseMuscleGroup = it },
            label = { Text("Muscle Group") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (exerciseName.isNotBlank()) {
                    viewModel.addExerciseToWorkout(workout.id, exerciseName, exerciseMuscleGroup)
                    exerciseName = ""
                    exerciseMuscleGroup = ""
                    exerciseAdded = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Exercise to Workout")
        }

        if (exerciseAdded) {
            Text(
                "Exercise added successfully!",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )

            // Reset the success message after a delay
            LaunchedEffect(exerciseAdded) {
                delay(2000)
                exerciseAdded = false
            }
        }
    }
}