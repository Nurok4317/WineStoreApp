package com.example.winestoreapp.screens
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.winestoreapp.model.ProfileViewModel
import com.google.firebase.auth.FirebaseUser

@Composable
fun ProfileScreen(navController: NavHostController, user: FirebaseUser, profileViewModel: ProfileViewModel) {
    val profile by profileViewModel.profile.collectAsState()

    LaunchedEffect(user.uid) {
        profileViewModel.loadUserProfile(user.uid)
    }

    profile?.let {
        var name by remember { mutableStateOf(it.name) }
        var address by remember { mutableStateOf(it.address) }

        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text("Profile", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Address") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val updatedProfile = it.copy(name = name, address = address)
                    profileViewModel.updateUserProfile(updatedProfile)
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Update Profile")
            }
        }
    }
}
