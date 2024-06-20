package com.example.winestoreapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.winestoreapp.OrdersScreen
import com.example.winestoreapp.screens.*
import com.example.winestoreapp.model.CartViewModel
import com.example.winestoreapp.model.OrderViewModel
import com.example.winestoreapp.model.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

@Composable
fun NavGraph(
    navController: NavHostController,
    cartViewModel: CartViewModel,
    orderViewModel: OrderViewModel,
    profileViewModel: ProfileViewModel,  // Add profileViewModel parameter
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier

    ) {
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable("catalogue") {
            CatalogueScreen(navController = navController, cartViewModel = cartViewModel)
        }
        composable("promotions") {
            PromotionsScreen(navController = navController)
        }
        composable("orders") {
            OrdersScreen(navController = navController, orderViewModel = orderViewModel)
        }

        }
    }
