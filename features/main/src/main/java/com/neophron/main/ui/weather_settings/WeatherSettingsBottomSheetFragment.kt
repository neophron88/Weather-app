package com.neophron.main.ui.weather_settings

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import com.neophron.contract.viewModelFactory.extract
import com.neophron.contract.viewModelFactory.viewModelProvider
import com.neophron.domain.models.TempUnit
import com.neophron.main.R
import com.neophron.main.databinding.WeatherSettingsScreenBinding
import com.neophron.main.di.SettingsAssistedFactoryProvider
import com.neophron.ui.utils.EdgeToEdgeBottomSheetFragment
import com.neophron88.library.ktx.InsetType
import com.neophron88.library.ktx.fitSystemUi
import com.neophron88.library.ktx.fragment.viewBindings
import com.neophron88.library.ktx.takeAs

class WeatherSettingsBottomSheetFragment :
    EdgeToEdgeBottomSheetFragment(R.layout.weather_settings_screen) {

    private val viewModel: SettingsViewModel by viewModelProvider {
        extract<SettingsAssistedFactoryProvider>().getSettingsFactory().create()
    }
    private val binding: WeatherSettingsScreenBinding by viewBindings()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupInsets()
        setupExit()
        setupRadioButton()
        observeUi()
    }

    private fun setupExit() = binding.apply {
        exit.setOnClickListener { dismiss() }
    }

    private fun setupInsets() = binding.apply {
        root.fitSystemUi(InsetType.NavigationBar)
    }

    private fun setupRadioButton() = binding.apply {
        unitRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.celsius -> viewModel.setTempUnit(TempUnit.Celsius)
                R.id.fahrenheit -> viewModel.setTempUnit(TempUnit.Fahrenheit)
                R.id.kelvin -> viewModel.setTempUnit(TempUnit.Kelvin)
            }
        }
    }

    private fun observeUi() = viewModel.uiState.observe(viewLifecycleOwner) {
        val unit: TempUnit = it.unit ?: return@observe
        binding.apply {
            unitRadioGroup
                .getChildAt(unit.ordinal)
                .takeAs<RadioButton>()
                .isChecked = true
        }
    }

}