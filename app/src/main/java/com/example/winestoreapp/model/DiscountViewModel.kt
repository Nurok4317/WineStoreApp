package com.example.winestoreapp.model
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.winestoreapp.data.Wine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DiscountViewModel : ViewModel() {

    // Mutable state flow to hold the list of wines with discounts
    private val _winesWithDiscount = MutableStateFlow<List<Wine>>(emptyList())
    val winesWithDiscount: StateFlow<List<Wine>> = _winesWithDiscount

    init {
        // Example: Fetch wines with discounts when ViewModel is initialized
        fetchWinesWithDiscount()
    }

    private fun fetchWinesWithDiscount() {
        // Simulated data for demonstration
        viewModelScope.launch {
            val wines = listOf(
                Wine("1", "Wine A", "https://example.com/wine_a.jpg", 25.0, true),
                Wine("2", "Wine B", "https://example.com/wine_b.jpg", 30.0, true),
                Wine("3", "Wine C", "https://example.com/wine_c.jpg", 20.0, true),
            )
            _winesWithDiscount.value = wines
        }
    }
}
