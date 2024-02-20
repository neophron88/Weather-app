package com.neophron.ui.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.core.view.updatePadding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class EdgeToEdgeBottomSheetFragment : BottomSheetDialogFragment {

    constructor() : super()

    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        CustomizableBottomSheetDialog(requireContext(), theme)


    private class CustomizableBottomSheetDialog(
        context: Context,
        @StyleRes theme: Int,
    ) : BottomSheetDialog(context, theme) {

        override fun onAttachedToWindow() {
            findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)?.apply {
                post { updatePadding(bottom = 0) }
                window!!.navigationBarColor = Color.TRANSPARENT
            }
            super.onAttachedToWindow()
        }

    }
}

