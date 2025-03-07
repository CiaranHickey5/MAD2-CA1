package ie.setu.ca1_mad2.navigation

import ListWorkoutsScreen
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ie.setu.ca1_mad2.GymTrackerViewModel
import ie.setu.ca1_mad2.ui.screens.*

@Composable
fun AppNavGraph(
    navController: NavHostController,
    viewModel: GymTrackerViewModel,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = AppRoutes.HOME,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(AppRoutes.HOME) {
            ListWorkoutsScreen(
                viewModel = viewModel,
                onWorkoutSelected = { workout ->
                    navController.navigate(AppRoutes.WORKOUT_DETAIL + "/${workout.id}")
                }
            )
        }

        composable(AppRoutes.ADD_WORKOUT) {
            AddWorkoutScreen(viewModel = viewModel)
        }

        composable(AppRoutes.LIST_WORKOUTS) {
            ListWorkoutsScreen(
                viewModel = viewModel,
                onWorkoutSelected = { workout ->
                    navController.navigate(AppRoutes.WORKOUT_DETAIL + "/${workout.id}")
                }
            )
        }

        composable(
            route = AppRoutes.WORKOUT_DETAIL + "/{workoutId}",
            arguments = listOf(navArgument("workoutId") { type = NavType.StringType })
        ) { backStackEntry ->
            val workoutId = backStackEntry.arguments?.getString("workoutId") ?: return@composable
            val workout = viewModel.workouts.find { it.id == workoutId } ?: return@composable

            WorkoutDetailScreen(
                viewModel = viewModel,
                workout = workout
            )
        }

        composable(AppRoutes.ADD_EXERCISE) {
            AddExerciseScreen(viewModel = viewModel)
        }

        composable(AppRoutes.LIST_EXERCISE) {
            ListExerciseScreen(viewModel = viewModel)
        }
    }
}