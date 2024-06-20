package com.example.winestoreapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.winestoreapp.model.ProfileViewModel

@Composable
fun UserProfileScreen(
    profileViewModel: ProfileViewModel = viewModel(),
    userId: String,
    navController: NavHostController
) {
    val profile by profileViewModel.profile.collectAsState()
    val updateMessage by profileViewModel.updateMessage.collectAsState()

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(userId) {
        profileViewModel.loadUserProfile(userId)
    }

    LaunchedEffect(updateMessage) {
        updateMessage?.let {
            scaffoldState.snackbarHostState.showSnackbar(it)
            profileViewModel.clearUpdateMessage()
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        content = { innerPadding ->
            profile?.let { userProfile ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(16.dp)
                ) {
                    Image(
                        painter = rememberImagePainter(userProfile.pictureImg),
                        contentDescription = null,
                        modifier = Modifier.size(128.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = userProfile.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = userProfile.phoneNumber, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = userProfile.address, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    // Update profile button
                    Button(onClick = {
                        profileViewModel.updateUserProfile(userProfile)
                    }) {
                        Text(text = "Update Profile")
                    }
                }
            } ?: run {
                Text(
                    text = "Loading...",
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize()
                )
            }
        }
    )
}
