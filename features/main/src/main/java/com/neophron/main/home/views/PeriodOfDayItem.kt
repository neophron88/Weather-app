package com.neophron.main.home.views

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
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


}
