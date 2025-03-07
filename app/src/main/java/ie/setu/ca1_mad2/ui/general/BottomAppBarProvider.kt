package ie.setu.ca1_mad2.ui.general

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import ie.setu.ca1_mad2.navigation.AppRoutes

data class NavBarItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

// Function that returns main nav items
fun navBarItems() = listOf(
    NavBarItem(AppRoutes.HOME, "Home", Icons.Default.Home),
    NavBarItem(AppRoutes.ADD_WORKOUT, "Add Workout", Icons.Default.Add),
    NavBarItem(AppRoutes.LIST_WORKOUTS, "Workouts", Icons.Default.List)
)

@Composable
fun BottomAppBarProvider(navController: NavController) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        navBarItems().forEach { item ->
            NavigationBarItem(
                selected = (currentRoute == item.route),
                onClick = {
                    // Pop up to avoid stacking multiple copies
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                label = { Text(item.label) },
                icon = { Icon(item.icon, contentDescription = item.label) }
            )
        }
    }
}