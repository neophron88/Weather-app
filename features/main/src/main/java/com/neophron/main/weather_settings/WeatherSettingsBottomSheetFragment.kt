package com.neophron.main.weather_settings

import android.os.Bundle
import android.view.View
import com.neophron.main.R
import com.neophron.main.databinding.WeatherSettingsScreenBinding
import com.neophron.ui.utils.EdgeToEdgeBottomSheetFragment
import com.neophron88.library.ktx.InsetType
import com.neophron88.library.ktx.fitSystemUi
import com.neophron88.library.ktx.fragment.viewBindings

class WeatherSettingsBottomSheetFragment : EdgeToEdgeBottomSheetFragment(R.layout.weather_settings_screen) {

    private val binding:WeatherSettingsScreenBinding by viewBindings()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.fitSystemUi(InsetType.NavigationBar)
    }

}