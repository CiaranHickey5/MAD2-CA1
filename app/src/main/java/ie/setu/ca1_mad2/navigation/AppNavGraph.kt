package ie.setu.ca1_mad2.navigation

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
        startDestination = AppRoutes.HOME
    ) {
        composable(AppRoutes.HOME) {
            // Suppose "home" is actually listing workouts
            ListWorkoutsScreen(
                viewModel = viewModel,
                onWorkoutSelected = { workout ->
                    navController.navigate(AppRoutes.WORKOUT_DETAIL + "/${workout.id}")
                }
            )
        }
        composable(AppRoutes.ADD_WORKOUT) { AddWorkoutScreen(viewModel) }
        composable(AppRoutes.LIST_WORKOUTS) {
            ListWorkoutsScreen(
                viewModel = viewModel,
                onWorkoutSelected = { workout ->
                    navController.navigate(AppRoutes.WORKOUT_DETAIL + "/${workout.id}")
                }
            )
        }
    }
}