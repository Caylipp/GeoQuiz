package com.hoang.msu.geoquiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val ANSWER_IS_TRUE_KEY = "answer_is_true_key"
private const val BTN_CLICKED = "btn_clicked"



class CheatViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {


    var answerIsTrue: Boolean
        get() = savedStateHandle.get(ANSWER_IS_TRUE_KEY) ?: false
        set(value) = savedStateHandle.set(ANSWER_IS_TRUE_KEY, value)

    var btnClicked: Boolean
        get() = savedStateHandle.get(BTN_CLICKED) ?: false
        set(value) = savedStateHandle.set(BTN_CLICKED, value)


}