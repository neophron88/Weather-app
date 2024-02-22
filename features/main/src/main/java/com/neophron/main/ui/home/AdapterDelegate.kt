package com.neophron.main.ui.home

import android.content.Context
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.neophron.main.R
import com.neophron.main.databinding.HomeDayItemBinding
import com.neophron.main.databinding.HomeTodayContainerItemBinding
import com.neophron.main.databinding.HomeTodayItemBinding
import com.neophron.main.domain.models.DayPeriodDisplay
import com.neophron.main.domain.models.WeatherByHourDisplay
import com.neophron.main.domain.models.WeatherByHourDisplayContainer
import com.neophron.main.domain.models.WeatherByPeriodDisplay
import com.neophron.main.ui.home.views.PeriodOfDayItem
import com.neophron.ui.utils.HorizontalLinearLayoutManager
import com.neophron88.library.recyclerview.adapterdelegate.ItemsAdapter
import com.neophron88.library.recyclerview.adapterdelegate.defaultDiffUtil

internal fun weatherAdapter(context: Context) = ItemsAdapter {

    item<WeatherByHourDisplayContainer, HomeTodayContainerItemBinding> {

        layout { R.layout.home_today_container_item }

        defaultDiffUtil()

        viewHolder {

            val todayAdapter = todayAdapter()
            viewBinding(HomeTodayContainerItemBinding::bind)

            viewBindingCreated {
                binding.hourList.apply {
                    adapter = todayAdapter
                    layoutManager = HorizontalLinearLayoutManager(context)
                }
            }

            onBind {
                binding.date.text = item.dayDate
                todayAdapter.submitList(item.display)
            }
        }

    }

    item<WeatherByPeriodDisplay, HomeDayItemBinding> {
        layout { R.layout.home_day_item }

        defaultDiffUtil()

        viewHolder {

            viewBinding(HomeDayItemBinding::bind)

            onBind {
                binding.apply {
                    date.text = item.time
                    midNight.bind(item.midnight)
                    morning.bind(item.morning)
                    afternoon.bind(item.afternoon)
                    night.bind(item.night)
                }
            }
        }
    }
}

private fun PeriodOfDayItem.bind(period: DayPeriodDisplay?) {
    this.isVisible = period != null
    period ?: return
    temperature = period.temperature
    iconUrl = period.iconUrl
    windSpeed = period.windSpeed
}

private fun todayAdapter() = ItemsAdapter {
    item<WeatherByHourDisplay, HomeTodayItemBinding> {

        layout { R.layout.home_today_item }

        defaultDiffUtil()

        viewHolder {

            viewBinding(HomeTodayItemBinding::bind)

            onBind {
                binding.apply {
                    temperature.text = item.temperature
                    time.text = item.time
                    Glide.with(root.context)
                        .load(item.iconUrl)
                        .into(icon)
                }
            }
        }
    }
}
