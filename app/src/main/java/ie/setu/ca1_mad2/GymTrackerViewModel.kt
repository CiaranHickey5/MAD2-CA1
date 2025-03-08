package ie.setu.ca1_mad2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.ca1_mad2.data.room.GymRepository
import ie.setu.ca1_mad2.model.Exercise
import ie.setu.ca1_mad2.model.Workout
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GymTrackerViewModel @Inject constructor(
    private val repository: GymRepository
) : ViewModel() {

    // Exercise state
    private val _exercises = MutableStateFlow<List<Exercise>>(emptyList())
    val exercises: StateFlow<List<Exercise>> = _exercises.asStateFlow()

    // Workout state
    private val _workouts = MutableStateFlow<List<Workout>>(emptyList())
    val workouts: StateFlow<List<Workout>> = _workouts.asStateFlow()

    init {
        // Collect exercises
        viewModelScope.launch {
            repository.exercises.collect { exerciseList ->
                _exercises.value = exerciseList
            }
        }

        // Collect workouts
        viewModelScope.launch {
            repository.workouts.collect { workoutList ->
                _workouts.value = workoutList
            }
        }
    }

    // Create global exercise
    fun addExercise(name: String, muscleGroup: String) {
        if (name.isNotBlank()) {
            viewModelScope.launch {
                repository.insertExercise(
                    Exercise(name = name, muscleGroup = muscleGroup)
                )
            }
        }
    }

    // Create a new workout
    fun addWorkout(name: String, description: String) {
        if (name.isNotBlank()) {
            viewModelScope.launch {
                repository.insertWorkout(
                    Workout(name = name, description = description)
                )
            }
        }
    }

    // Update an existing workout
    fun updateWorkout(workoutId: String, newName: String, newDescription: String) {
        if (newName.isNotBlank()) {
            viewModelScope.launch {
                // Find the workout
                val workout = _workouts.value.find { it.id == workoutId } ?: return@launch

                // Update the workout
                val updatedWorkout = workout.copy(
                    name = newName,
                    description = newDescription
                )

                repository.updateWorkout(updatedWorkout)
            }
        }
    }

    // Delete a workout by ID
    fun deleteWorkout(workoutId: String) {
        viewModelScope.launch {
            val workout = _workouts.value.find { it.id == workoutId } ?: return@launch
            repository.deleteWorkout(workout)
        }
    }

    // Add an exercise to a specific workout
    fun addExerciseToWorkout(workoutId: String, exerciseName: String, exerciseMuscleGroup: String) {
        if (exerciseName.isNotBlank()) {
            viewModelScope.launch {
                val exercise = Exercise(
                    name = exerciseName,
                    muscleGroup = exerciseMuscleGroup
                )

                repository.addExerciseToWorkout(workoutId, exercise)
            }
        }
    }

    // Update an exercise within a workout
    fun updateWorkoutExercise(workoutId: String, exerciseId: String, newName: String, newMuscleGroup: String) {
        if (newName.isNotBlank()) {
            viewModelScope.launch {
                val exercise = Exercise(
                    id = exerciseId,
                    name = newName,
                    muscleGroup = newMuscleGroup
                )

                repository.updateExercise(exercise)
            }
        }
    }

    // Remove an exercise from a specific workout
    fun removeExerciseFromWorkout(workoutId: String, exerciseId: String) {
        viewModelScope.launch {
            repository.removeExerciseFromWorkout(workoutId, exerciseId)
        }
    }

    // Convert list of muscle groups to string
    private fun convertMuscleGroupsToString(muscleGroups: List<String>): String {
        return muscleGroups.joinToString(", ")
    }

    // Convert string of muscle groups to list
    private fun convertStringToMuscleGroups(muscleGroupString: String): List<String> {
        return muscleGroupString.split(", ").filter { it.isNotBlank() }
    }

    // Update the exercise with a list of muscle groups
    fun updateWorkoutExerciseWithMuscleGroups(
        workoutId: String,
        exerciseId: String,
        newName: String,
        newMuscleGroups: List<String>
    ) {
        val muscleGroupString = convertMuscleGroupsToString(newMuscleGroups)
        updateWorkoutExercise(workoutId, exerciseId, newName, muscleGroupString)
    }

    // Add an exercise to workout with list of muscle groups
    fun addExerciseToWorkoutWithMuscleGroups(
        workoutId: String,
        exerciseName: String,
        muscleGroups: List<String>
    ) {
        val muscleGroupString = convertMuscleGroupsToString(muscleGroups)
        addExerciseToWorkout(workoutId, exerciseName, muscleGroupString)
    }

    // Add a global exercise with list of muscle groups
    fun addExerciseWithMuscleGroups(name: String, muscleGroups: List<String>) {
        val muscleGroupString = convertMuscleGroupsToString(muscleGroups)
        addExercise(name, muscleGroupString)
    }
}