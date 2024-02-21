package com.neophron.main.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.neophron.contract.viewModelFactory.extract
import com.neophron.contract.viewModelFactory.viewModelProvider
import com.neophron.main.R
import com.neophron.main.databinding.HomeDayItemBinding
import com.neophron.main.databinding.HomeScreenBinding
import com.neophron.main.databinding.HomeTodayContainerItemBinding
import com.neophron.main.databinding.HomeTodayItemBinding
import com.neophron.main.di.HomeAssistedFactoryProvider
import com.neophron.main.ui.weather_settings.WeatherSettingsBottomSheetFragment
import com.neophron.ui.utils.HorizontalLinearLayoutManager
import com.neophron88.library.ktx.InsetType
import com.neophron88.library.ktx.fitSystemUi
import com.neophron88.library.ktx.fragment.viewBindings
import com.neophron88.library.ktx.fragment.viewLifeCycle
import com.neophron88.library.recyclerview.adapterdelegate.ItemsAdapter

class HomeFragment : Fragment(R.layout.home_screen) {


    private val viewModel: HomeViewModel by viewModelProvider {
        extract<HomeAssistedFactoryProvider>().getHomeFactory().create()
    }
    private val binding: HomeScreenBinding by viewBindings()
    private val weatherAdapter by viewLifeCycle { weatherAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            root.fitSystemUi(InsetType.StatusBar)
            weatherList.fitSystemUi(InsetType.NavigationBar)
            weatherList.adapter = weatherAdapter
            weatherList.layoutManager = LinearLayoutManager(requireContext())
            settings.setOnClickListener {
                WeatherSettingsBottomSheetFragment().show(parentFragmentManager, null)
            }
            weatherAdapter.submitList(listOf(1, 2f, 3f, 4f, 5f, 6f, 7f))

        }
    }

    private fun weatherAdapter() = ItemsAdapter {
        item<Int, HomeTodayContainerItemBinding> {
            layout { R.layout.home_today_container_item }
            diffUtil {
                areItemsTheSame { oldItem, newItem -> oldItem == newItem }
                areContentsTheSame { oldItem, newItem -> oldItem == newItem }
            }
            viewHolder {
                val todayAdapter = this@HomeFragment.todayAdapter()
                viewBinding(HomeTodayContainerItemBinding::bind)
                viewBindingCreated {
                    binding.apply {
                        hourList.adapter = todayAdapter
                        hourList.layoutManager = HorizontalLinearLayoutManager(requireContext())
                        todayAdapter.submitList(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
                    }
                }
                onBind { }
            }
        }

        item<Float, HomeDayItemBinding> {
            layout { R.layout.home_day_item }
            diffUtil {
                areItemsTheSame { oldItem, newItem -> oldItem == newItem }
                areContentsTheSame { oldItem, newItem -> oldItem == newItem }
            }
            viewHolder {
                viewBinding(HomeDayItemBinding::bind)
                viewBindingCreated {}
                onBind { }
            }
        }
    }

    private fun todayAdapter() = ItemsAdapter {
        item<Int, HomeTodayItemBinding> {
            layout { R.layout.home_today_item }
            diffUtil {
                areItemsTheSame { oldItem, newItem -> oldItem == newItem }
                areContentsTheSame { oldItem, newItem -> oldItem == newItem }
            }
            viewHolder {
                viewBinding(HomeTodayItemBinding::bind)
                viewBindingCreated {}
                onBind { }
            }
        }
    }

}