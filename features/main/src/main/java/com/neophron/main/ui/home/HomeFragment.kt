package com.neophron.main.ui.home

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.view.doOnLayout
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.neophron.contract.viewModelFactory.extract
import com.neophron.contract.viewModelFactory.viewModelProvider
import com.neophron.domain.errors.ErrorType
import com.neophron.main.R
import com.neophron.main.databinding.HomeScreenBinding
import com.neophron.main.di.HomeAssistedFactoryProvider
import com.neophron.main.ui.weather_settings.WeatherSettingsBottomSheetFragment
import com.neophron.main.utils.parseToStringRes
import com.neophron.main.utils.showKeyboard
import com.neophron.main.utils.showToast
import com.neophron.ui.utils.VerticalLinearLayoutManager
import com.neophron88.library.ktx.InsetType
import com.neophron88.library.ktx.UpdateParam
import com.neophron88.library.ktx.dp
import com.neophron88.library.ktx.fitSystemUi
import com.neophron88.library.ktx.fragment.interceptOnBackPressed
import com.neophron88.library.ktx.fragment.onBackPressedCallback
import com.neophron88.library.ktx.fragment.viewBindings
import com.neophron88.library.ktx.fragment.viewLifeCycle

class HomeFragment : Fragment(R.layout.home_screen) {

    private val viewModel: HomeViewModel by viewModelProvider {
        extract<HomeAssistedFactoryProvider>().getHomeFactory().create()
    }
    private val onBackPressedCallback by viewLifeCycle {
        onBackPressedCallback { hideInputSearch() }
    }
    private val binding: HomeScreenBinding by viewBindings()
    private val weatherAdapter by viewLifeCycle { weatherAdapter(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupInsets()
        setupWeatherList()
        setupRefreshContent()
        setupSettings()
        setupSearch()
        observeUiState()
        observeUiEvent()
        interceptOnBackPressed(viewLifecycleOwner, onBackPressedCallback)
    }

    private fun setupInsets() = binding.apply {
        root.fitSystemUi(InsetType.StatusBar)
        weatherList.fitSystemUi(InsetType.NavigationBar)
        inputSearch.fitSystemUi(InsetType.StatusBar, UpdateParam.Margin)
    }

    private fun setupWeatherList() = binding.apply {
        weatherList.adapter = weatherAdapter
        weatherList.layoutManager = VerticalLinearLayoutManager(requireContext())
    }

    private fun setupRefreshContent() = binding.apply {
        refreshLayout.setOnRefreshListener { viewModel.requestWeatherData() }
    }

    private fun setupSettings() = binding.apply {
        settings.setOnClickListener {
            WeatherSettingsBottomSheetFragment().show(parentFragmentManager, null)
        }
    }

    private fun setupSearch() = binding.apply {
        inputSearch.doOnLayout { hideInputSearch() }
        inputSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val city = inputSearch.text.toString()
                if (city.isNotBlank()) {
                    viewModel.setCity(city)
                    hideInputSearch()
                }
            }
            false
        }
        search.setOnClickListener { showInputSearch() }
    }

    private fun showInputSearch() = binding.inputSearch.apply {
        isEnabled = true
        onBackPressedCallback.isEnabled = true
        val y = 8.dp(requireContext()).toFloat()
        animate().translationY(y).setDuration(300).start()
        animate().alpha(1f).setDuration(300)
            .withStartAction { isVisible = true }
            .withEndAction {
                requestFocus()
                requireContext().showKeyboard(this)
            }.start()
    }

    private fun hideInputSearch() {
        binding.inputSearch.apply {
            onBackPressedCallback.isEnabled = false
            isEnabled = false
            val y = (height + marginTop).toFloat()
            animate().translationY(-y).setDuration(300).start()
            animate().alpha(0f).setDuration(300).withEndAction {
                isVisible = false
                setText("")
            }.start()
        }
    }


    private fun observeUiState() = viewModel.uiState.observe(viewLifecycleOwner) {
        binding.apply {

            todayWeatherContainer.isInvisible = it.isEmpty
            todayWeatherAdditionalInfoContainer.isInvisible = it.isEmpty
            refreshLayout.isRefreshing = it.isInProgress
            weatherAdapter.submitList(it.list)

            it.current ?: return@apply
            cityName.text = it.current.cityName
            weatherValue.text = it.current.temperature
            weatherState.text = getString(
                R.string.weather_state_placeholder,
                it.current.description,
                it.current.tempMax,
                it.current.tempMin
            )
            weatherUnit.text = getString(it.current.unit.parseToStringRes())
            pressure.text = it.current.pressure
            humadity.text = it.current.humidity
            windSpeed.text = it.current.windSpeed
            Glide.with(requireContext())
                .load(it.current.iconUrl)
                .into(weatherIcon)
        }
    }

    private fun observeUiEvent() = viewModel.uiEvent.observe(viewLifecycleOwner) {
        if (it is HomeUiEvent.Error) handleError(it.type)

    }

    private fun handleError(type: ErrorType) {
        when (type) {
            ErrorType.NoConnection -> showToast(R.string.no_connection)
            ErrorType.NoSuchCity -> showToast(R.string.no_such_city)
            ErrorType.BackendFailed -> showToast(R.string.backend_failed)
            ErrorType.EmptyList -> showInputSearch()
            is ErrorType.Unknown -> showToast(R.string.backend_failed)
            else -> Unit
        }
    }

}