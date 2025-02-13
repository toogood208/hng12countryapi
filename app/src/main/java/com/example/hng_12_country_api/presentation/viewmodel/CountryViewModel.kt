package com.example.hng_12_country_api.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hng_12_country_api.data.api.ResponseResult
import com.example.hng_12_country_api.data.model.CountryItem
import com.example.hng_12_country_api.data.repository.CountryRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CountryViewModel(private val countryRepository: CountryRepository) : ViewModel() {
    private val _countries = MutableStateFlow<List<CountryItem>>(emptyList())
    val countries = _countries.asStateFlow()
    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            countryRepository.getCountryList().collectLatest { result ->
                when (result) {
                    is ResponseResult.Error -> {
                        _isLoading.value = false
                        _showErrorToastChannel.send(true)
                    }

                    is ResponseResult.Success -> {

                        result.data?.let { country ->
                            _countries.update { country }
                            _isLoading.value = false

                        }
                    }
                }
            }
        }
    }

}