package com.neophron88.library.ktx.view

import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputLayout


fun TextInputLayout.disableErrorWhenTyping() {
    editText?.doAfterTextChanged {
        if (error != null) {
            error = null
            isErrorEnabled = false
        }
    }
}