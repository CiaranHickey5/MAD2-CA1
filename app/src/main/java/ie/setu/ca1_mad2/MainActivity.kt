package ie.setu.ca1_mad2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ie.setu.ca1_mad2.navigation.AppNavGraph
import ie.setu.ca1_mad2.ui.components.navigation.BottomNavigationBar
import ie.setu.ca1_mad2.ui.components.navigation.TopAppBarComponent
import ie.setu.ca1_mad2.ui.theme.CA1MAD2Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CA1MAD2Theme {
                val gymViewModel: GymTrackerViewModel = hiltViewModel()
                val navController = rememberNavController()

                Scaffold(
                    topBar = {
                        TopAppBarComponent()
                    },
                    bottomBar = {
                        BottomNavigationBar(navController)
                    }
                ) { paddingValues ->
                    AppNavGraph(
                        navController = navController,
                        viewModel = gymViewModel,
                        innerPadding = paddingValues
                    )
                }
            }
        }
    }
}