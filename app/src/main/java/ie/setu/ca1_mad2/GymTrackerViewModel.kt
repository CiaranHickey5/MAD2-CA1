package ie.setu.ca1_mad2

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import ie.setu.ca1_mad2.model.Exercise
import ie.setu.ca1_mad2.model.Workout

class GymTrackerViewModel : ViewModel() {

    private val _exercises = mutableStateListOf<Exercise>()
    val exercises: List<Exercise> get() = _exercises

    // Create global exercise
    fun addExercise(name: String, muscleGroup: String) {
        if (name.isNotBlank()) {
            _exercises.add(
                Exercise(name = name, muscleGroup = muscleGroup)
            )
        }
    }

    private val _workouts = mutableStateListOf<Workout>()
    val workouts: List<Workout> get() = _workouts

    // Create a new workout
    fun addWorkout(name: String, description: String) {
        if (name.isNotBlank()) {
            _workouts.add(Workout(name = name, description = description))
        }
    }

    // Delete a workout by ID
    fun deleteWorkout(workoutId: String) {
        _workouts.removeIf { it.id == workoutId }
    }

    // Add an exercise to a specific workout
    fun addExerciseToWorkout(workoutId: String, exerciseName: String, exerciseMuscleGroup: String) {
        val workout = _workouts.find { it.id == workoutId } ?: return
        if (exerciseName.isNotBlank()) {
            workout.exercises.add(Exercise(name = exerciseName, muscleGroup = exerciseMuscleGroup))
        }
    }

    // Remove an exercise from a specific workout
    fun removeExerciseFromWorkout(workoutId: String, exerciseId: String) {
        val workout = _workouts.find { it.id == workoutId } ?: return
        workout.exercises.removeIf { it.id == exerciseId }
    }
}