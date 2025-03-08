package ie.setu.ca1_mad2.ui.screens

import EmptyStateMessage
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ie.setu.ca1_mad2.GymTrackerViewModel
import ie.setu.ca1_mad2.model.Workout
import ie.setu.ca1_mad2.ui.components.cards.WorkoutCard
import ie.setu.ca1_mad2.ui.components.dialogs.DeleteConfirmationDialog
import ie.setu.ca1_mad2.ui.components.dialogs.EditWorkoutDialog

@Composable
fun ListWorkoutsScreen(
    workouts: List<Workout>,
    viewModel: GymTrackerViewModel,
    onWorkoutSelected: (Workout) -> Unit
) {
    // State for deletion confirmation
    var workoutToDelete by remember { mutableStateOf<Workout?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    // State for editing workout
    var workoutToEdit by remember { mutableStateOf<Workout?>(null) }
    var showEditDialog by remember { mutableStateOf(false) }
    var editedName by remember { mutableStateOf("") }
    var editedDescription by remember { mutableStateOf("") }

    // Edit Workout Dialog
    if (showEditDialog && workoutToEdit != null) {
        EditWorkoutDialog(
            workout = workoutToEdit!!,
            name = editedName,
            description = editedDescription,
            onNameChange = { editedName = it },
            onDescriptionChange = { editedDescription = it },
            onSave = {
                workoutToEdit?.let { workout ->
                    viewModel.updateWorkout(
                        workoutId = workout.id,
                        newName = editedName,
                        newDescription = editedDescription
                    )
                }
                showEditDialog = false
                workoutToEdit = null
            },
            onDismiss = {
                showEditDialog = false
                workoutToEdit = null
            }
        )
    }

    // Delete confirmation dialog
    if (showDeleteDialog && workoutToDelete != null) {
        DeleteConfirmationDialog(
            title = "Delete Workout",
            itemName = workoutToDelete?.name ?: "",
            onConfirm = {
                workoutToDelete?.let { workout ->
                    viewModel.deleteWorkout(workout.id)
                }
                showDeleteDialog = false
                workoutToDelete = null
            },
            onDismiss = {
                showDeleteDialog = false
                workoutToDelete = null
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
            EmptyStateMessage(message = "No workouts yet. Add your first workout!")
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
                        },
                        onEditClicked = {
                            workoutToEdit = workout
                            editedName = workout.name
                            editedDescription = workout.description
                            showEditDialog = true
                        }
                    )
                }
            }
        }
    }
}