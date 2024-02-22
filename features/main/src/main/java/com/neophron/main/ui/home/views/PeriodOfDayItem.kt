package com.neophron.main.ui.home.views

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.neophron.main.R
import com.neophron.main.databinding.HomePeriodOfDayItemBinding


class PeriodOfDayItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding: HomePeriodOfDayItemBinding

    init {
        binding = HomePeriodOfDayItemBinding.inflate(LayoutInflater.from(context), this)
        setupLayout()
        setupAttributes(attrs, defStyleAttr, defStyleRes)
    }

    private fun setupLayout() {
        gravity = Gravity.CENTER_VERTICAL
        orientation = HORIZONTAL
    }

    private fun setupAttributes(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.PeriodOfDayItem, defStyleAttr, defStyleRes
        )
        val name = typedArray.getString(R.styleable.PeriodOfDayItem_name)
        binding.periodName.text = name
        typedArray.recycle()
    }


    var periodName: String
        get() = binding.periodName.text.toString()
        set(value) {
            binding.periodName.text = value
        }

    var iconUrl: String = ""
        get() = field
        set(value) {
            field = value
            Glide.with(context)
                .load(value)
                .into(binding.icon)
        }

    var temperature: String
        get() = binding.temperature.text.toString()
        set(value) {
            binding.temperature.text = value
        }

    var windSpeed: String
        get() = binding.wind.text.toString()
        set(value) {
            binding.wind.text = value
        }
}
