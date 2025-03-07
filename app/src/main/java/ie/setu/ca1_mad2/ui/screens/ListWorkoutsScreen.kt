package ie.setu.ca1_mad2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ie.setu.ca1_mad2.GymTrackerViewModel
import ie.setu.ca1_mad2.model.Workout

@Composable
fun ListWorkoutsScreen(
    viewModel: GymTrackerViewModel,
    onWorkoutSelected: (Workout) -> Unit
) {
    val workouts = viewModel.workouts

    // State for deletion confirmation
    var workoutToDelete by remember { mutableStateOf<Workout?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    // Delete confirmation dialog
    if (showDeleteDialog && workoutToDelete != null) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
                workoutToDelete = null
            },
            title = { Text("Delete Workout") },
            text = { Text("Are you sure you want to delete '${workoutToDelete?.name}'? This action cannot be undone.") },
            confirmButton = {
                Button(
                    onClick = {
                        workoutToDelete?.let { workout ->
                            viewModel.deleteWorkout(workout.id)
                        }
                        showDeleteDialog = false
                        workoutToDelete = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = {
                    showDeleteDialog = false
                    workoutToDelete = null
                }) {
                    Text("Cancel")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Your Workouts",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (workouts.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No workouts yet. Add your first workout!",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(workouts) { workout ->
                    WorkoutCard(
                        workout = workout,
                        onWorkoutSelected = onWorkoutSelected,
                        onDeleteClicked = {
                            workoutToDelete = workout
                            showDeleteDialog = true
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun WorkoutCard(
    workout: Workout,
    onWorkoutSelected: (Workout) -> Unit,
    onDeleteClicked: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Workout title and delete button in a row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = workout.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )

                // Delete icon button
                IconButton(onClick = onDeleteClicked) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Workout",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

            if (workout.description.isNotBlank()) {
                Text(
                    text = workout.description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp)
            ) {
                Text(
                    text = "Exercises: ${workout.exercises.size}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            // View details button
            Button(
                onClick = { onWorkoutSelected(workout) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                Text("View Details")
            }
        }
    }
}