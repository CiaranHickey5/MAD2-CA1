package ie.setu.ca1_mad2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import ie.setu.ca1_mad2.ui.theme.CA1MAD2Theme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import ie.setu.ca1_mad2.navigation.AppNavGraph
import ie.setu.ca1_mad2.ui.general.BottomAppBarProvider
import ie.setu.ca1_mad2.ui.general.TopAppBarProvider


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CA1MAD2Theme {
                val gymViewModel: GymTrackerViewModel = viewModel()
                val navController = rememberNavController()
                Scaffold(
                    topBar = {
                        TopAppBarProvider(navController)
                    },
                    bottomBar = {
                        BottomAppBarProvider(navController)
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