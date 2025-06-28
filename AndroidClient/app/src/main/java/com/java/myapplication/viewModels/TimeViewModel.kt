package com.java.myapplication.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.java.myapplication.data.AdBudgetDTO
import com.java.myapplication.data.PlayerReadyDTO
import com.java.myapplication.data.PlayerUiState
import com.java.myapplication.data.TimeDTO
import com.java.myapplication.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TimeViewModel : ViewModel() {

    private val _date = MutableStateFlow(TimeDTO(1, 2025))
    val date: StateFlow<TimeDTO> = _date.asStateFlow()

    fun getDate(): String {
        return "01.${_date.value.month}.${_date.value.year}"
    }

    fun setDate(date: TimeDTO) {
        _date.value = _date.value.copy(month = date.month, year = date.year)
    }

}