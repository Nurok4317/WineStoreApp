import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun AddWineScreen() {
    var wineName by remember { mutableStateOf("") }
    var wineDescription by remember { mutableStateOf("") }
    var winePrice by remember { mutableStateOf("") }
    val db = FirebaseFirestore.getInstance()

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = wineName,
            onValueChange = { wineName = it },
            label = { Text("Wine Name") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = wineDescription,
            onValueChange = { wineDescription = it },
            label = { Text("Description") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = winePrice,
            onValueChange = { winePrice = it },
            label = { Text("Price") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val wineData = hashMapOf(
                "name" to wineName,
                "description" to wineDescription,
                "price" to winePrice.toDouble()
            )
            db.collection("wines").add(wineData)
                .addOnSuccessListener {
                    // Wine added successfully
                }
                .addOnFailureListener {
                    // Handle failure
                }
        }) {
            Text("Add Wine")
        }
    }
}
