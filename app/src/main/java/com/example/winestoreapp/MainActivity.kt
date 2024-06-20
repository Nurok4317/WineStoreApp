package com.example.winestoreapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem

import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.winestoreapp.model.CartViewModel
import com.example.winestoreapp.model.OrderViewModel
import com.example.winestoreapp.model.ProfileViewModel
import com.example.winestoreapp.model.checkIfAdmin
import com.example.winestoreapp.screens.AdminScreen
import com.example.winestoreapp.screens.CartScreen
import com.example.winestoreapp.screens.CatalogueScreen
import com.example.winestoreapp.screens.CheckoutScreen
import com.example.winestoreapp.screens.HomeScreen
import com.example.winestoreapp.screens.LoginScreen
import com.example.winestoreapp.screens.ProfileScreen
import com.example.winestoreapp.screens.PromotionDetailScreen
import com.example.winestoreapp.screens.PromotionsScreen
import com.example.winestoreapp.screens.UserScreen
import com.example.winestoreapp.screens.WineDetailScreen
import com.example.winestoreapp.ui.theme.WineStoreAppTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        setContent {
            WineStoreAppTheme {
                val cartViewModel: CartViewModel by viewModels()
                val orderViewModel: OrderViewModel by viewModels()
                val profileViewModel: ProfileViewModel by viewModels()

                val navController = rememberNavController()
                val currentUser = auth.currentUser

                MainApp(navController, cartViewModel, orderViewModel, profileViewModel, currentUser)
            }
        }
    }
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    var isAdmin by remember { mutableStateOf(false) }

    LaunchedEffect(user) {
        if (user != null) {
            checkIfAdmin(user.uid) { isAdminResult ->
                isAdmin = isAdminResult
            }
        }
    }

    Scaffold(
        bottomBar = { BottomNavigationBar(rememberNavController()) }
    ) {
        if (isAdmin) {
            // Show admin screen
            AdminScreen()
        } else {
            // Show regular user screen
            UserScreen()
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp(
    navController: NavHostController,
    cartViewModel: CartViewModel,
    orderViewModel: OrderViewModel,
    profileViewModel: ProfileViewModel,
    currentUser: FirebaseUser?
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Wine Store") },
                actions = {
                    IconButton(onClick = { navController.navigate("login") }) {
                        Icon(imageVector = Icons.Default.Person, contentDescription = "Log In")
                    }
                    IconButton(onClick = { navController.navigate("cart") }) {
                        Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Cart")
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { paddingValues ->
        NavHost(navController, startDestination = "home", Modifier.padding(paddingValues)) {
            composable("home") { HomeScreen(navController) }
            composable("catalogue") { CatalogueScreen(navController = rememberNavController(), cartViewModel = cartViewModel )}
            composable("discounts") {}
            composable("orders") { OrdersScreen(orderViewModel, navController) }
            composable("profile") { ProfileScreen(navController, currentUser!!, profileViewModel) }
            composable("wineDetails/{wineId}") { backStackEntry ->
                WineDetailScreen(navController, backStackEntry.arguments?.getString("wineId") ?: "")
            }
            composable("promotionDetails/{promotionId}") { backStackEntry ->
                PromotionDetailScreen(navController, backStackEntry.arguments?.getString("promotionId") ?: "")
            }
            composable("promotions") { PromotionsScreen(navController) }
            composable("login") { LoginScreen(navController) }
            composable("cart") { CartScreen(navController, orderViewModel) }
            composable("checkout") { CheckoutScreen(navController, cartViewModel, orderViewModel, currentUser!!) }
        }
    }
}

@Composable
fun OrdersScreen(orderViewModel: OrderViewModel, navController: NavHostController) {

}


data class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val title: String
)
@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem ("home", Icons.Default.Home, "Home"),
        BottomNavItem("catalogue", Icons.Default.List, "Catalogue"),
        BottomNavItem("discounts", Icons.Default.Lock, /*LocalOffer*/ "Discounts"),
        BottomNavItem("orders", Icons.Default.Notifications, "Orders"),
        BottomNavItem("profile", Icons.Default.Person, "Profile")
    )

    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to avoid creating a new instance of the destination
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple instances of the same destination when navigating
                        launchSingleTop = true
                        // Restore state when navigating back to a previously visited destination
                        restoreState = true
                    }
                }
            )
        }
    }

data class BottomNavItem(val route: String, val icon: ImageVector, val title: String)


@Composable
fun DefaultPreview() {
    WineStoreAppTheme {
        val navController = rememberNavController()
        MainApp(navController, CartViewModel(), OrderViewModel(), ProfileViewModel(), null)
    }
}
    }

