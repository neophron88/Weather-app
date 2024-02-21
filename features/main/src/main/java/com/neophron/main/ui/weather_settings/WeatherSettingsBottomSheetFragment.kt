package com.neophron.main.ui.weather_settings

import android.os.Bundle
import android.view.View
import com.neophron.contract.viewModelFactory.extract
import com.neophron.contract.viewModelFactory.viewModelProvider
import com.neophron.main.R
import com.neophron.main.databinding.WeatherSettingsScreenBinding
import com.neophron.main.di.SettingsAssistedFactoryProvider
import com.neophron.ui.utils.EdgeToEdgeBottomSheetFragment
import com.neophron88.library.ktx.InsetType
import com.neophron88.library.ktx.fitSystemUi
import com.neophron88.library.ktx.fragment.viewBindings

class WeatherSettingsBottomSheetFragment : EdgeToEdgeBottomSheetFragment(R.layout.weather_settings_screen) {

    private val viewModel: SettingsViewModel by viewModelProvider {
        extract<SettingsAssistedFactoryProvider>().getSettingsFactory().create()
    }
    private val binding:WeatherSettingsScreenBinding by viewBindings()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.fitSystemUi(InsetType.NavigationBar)
    }

}