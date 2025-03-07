package ie.setu.ca1_mad2.ui.general

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import ie.setu.ca1_mad2.navigation.AppRoutes

data class NavBarItem(
    val route: String,
    val label: String
)

// Function that returns main nav items
fun navBarItems() = listOf(
    NavBarItem(AppRoutes.HOME, "Home"),
    NavBarItem(AppRoutes.ADD_WORKOUT, "Add Workout"),
    NavBarItem(AppRoutes.LIST_WORKOUTS, "List Workout")
)

@Composable
fun BottomAppBarProvider(navController: NavController) {
    NavigationBar {
        val backStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry.value?.destination?.route

        navBarItems().forEach { item ->
            NavigationBarItem(
                selected = (item.route == currentRoute),
                onClick = {
                    // Pop up to avoid stacking multiple copies
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                label = { Text(item.label) },
                icon = {}
            )
        }
    }
}