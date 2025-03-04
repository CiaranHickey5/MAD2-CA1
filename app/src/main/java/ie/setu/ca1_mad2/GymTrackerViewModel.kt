package ie.setu.ca1_mad2

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import ie.setu.ca1_mad2.model.Exercise

class GymTrackerViewModel : ViewModel() {

    private val _exercises = mutableStateListOf<Exercise>()

    val exercises: List<Exercise> get() = _exercises

    fun addExercise(name: String, muscleGroup: String) {
        if (name.isNotBlank()) {
            _exercises.add(
                Exercise(name = name, muscleGroup = muscleGroup)
            )
        }
    }
}