package ie.setu.ca1_mad2.ui.screens

import EmptyStateMessage
import FormField
import FormSection
import SectionHeader
import SuccessMessage
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ie.setu.ca1_mad2.GymTrackerViewModel
import ie.setu.ca1_mad2.model.Exercise
import ie.setu.ca1_mad2.model.Workout
import ie.setu.ca1_mad2.ui.components.cards.ExerciseCard
import ie.setu.ca1_mad2.ui.components.dialogs.DeleteConfirmationDialog
import ie.setu.ca1_mad2.ui.components.dialogs.EditExerciseDialog
import ie.setu.ca1_mad2.ui.components.inputs.MultiMuscleGroupSelector
import kotlinx.coroutines.delay

@Composable
fun WorkoutDetailScreen(
    viewModel: GymTrackerViewModel,
    workout: Workout
) {
    var exerciseName by remember { mutableStateOf("") }
    var selectedMuscleGroups by remember { mutableStateOf<List<String>>(emptyList()) }
    var exerciseAdded by remember { mutableStateOf(false) }

    // States for edit dialog
    var showEditDialog by remember { mutableStateOf(false) }
    var exerciseToEdit by remember { mutableStateOf<Exercise?>(null) }
    var editedName by remember { mutableStateOf("") }
    var editedMuscleGroups by remember { mutableStateOf<List<String>>(emptyList()) }

    // States for delete dialog
    var showDeleteDialog by remember { mutableStateOf(false) }
    var exerciseToDelete by remember { mutableStateOf<Exercise?>(null) }

    // Edit Exercise Dialog
    if (showEditDialog && exerciseToEdit != null) {
        EditExerciseDialog(
            exercise = exerciseToEdit!!,
            name = editedName,
            muscleGroup = exerciseToEdit!!.muscleGroup,
            onNameChange = { editedName = it },
            onMuscleGroupChange = { newGroups ->
                editedMuscleGroups = newGroups
            },
            onSave = {
                exerciseToEdit?.let { exercise ->
                    viewModel.updateWorkoutExercise(
                        workoutId = workout.id,
                        exerciseId = exercise.id,
                        newName = editedName,
                        newMuscleGroup = editedMuscleGroups.joinToString(", ")
                    )
                }
                showEditDialog = false
                exerciseToEdit = null
            },
            onDismiss = {
                showEditDialog = false
                exerciseToEdit = null
            }
        )
    }

    // Delete Exercise Dialog
    if (showDeleteDialog && exerciseToDelete != null) {
        DeleteConfirmationDialog(
            title = "Delete Exercise",
            itemName = exerciseToDelete?.name ?: "",
            onConfirm = {
                exerciseToDelete?.let { exercise ->
                    viewModel.removeExerciseFromWorkout(
                        workoutId = workout.id,
                        exerciseId = exercise.id
                    )
                }
                showDeleteDialog = false
                exerciseToDelete = null
            },
            onDismiss = {
                showDeleteDialog = false
                exerciseToDelete = null
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
        SectionHeader(title = "Exercises")

        if (workout.exercises.isEmpty()) {
            EmptyStateMessage(message = "No exercises added to this workout yet")
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(workout.exercises) { exercise ->
                    ExerciseCard(
                        exercise = exercise,
                        onEditClicked = {
                            exerciseToEdit = exercise
                            editedName = exercise.name
                            editedMuscleGroups = exercise.muscleGroup.split(", ").filter { it.isNotBlank() }
                            showEditDialog = true
                        },
                        onDeleteClicked = {
                            exerciseToDelete = exercise
                            showDeleteDialog = true
                        }
                    )
                }
            }
        }

        // Add Exercise Form
        FormSection(title = "Add New Exercise") {
            FormField(
                value = exerciseName,
                onValueChange = { exerciseName = it },
                label = "Exercise Name"
            )

            Spacer(modifier = Modifier.height(12.dp))

            MultiMuscleGroupSelector(
                selectedMuscleGroups = selectedMuscleGroups,
                onSelectionChanged = { selectedMuscleGroups = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (exerciseName.isNotBlank() && selectedMuscleGroups.isNotEmpty()) {
                        // Join groups with comma
                        val muscleGroupString = selectedMuscleGroups.joinToString(", ")
                        viewModel.addExerciseToWorkout(workout.id, exerciseName, muscleGroupString)
                        exerciseName = ""
                        selectedMuscleGroups = emptyList()
                        exerciseAdded = true
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = exerciseName.isNotBlank() && selectedMuscleGroups.isNotEmpty()
            ) {
                Text("Add Exercise to Workout")
            }
        }

        if (exerciseAdded) {
            SuccessMessage(message = "Exercise added successfully!")

            // Reset message after delay
            LaunchedEffect(exerciseAdded) {
                delay(2000)
                exerciseAdded = false
            }
        }
    }
}