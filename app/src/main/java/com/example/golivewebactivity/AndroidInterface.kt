package com.example.golivewebactivity

import android.content.Context
import android.util.Log
import android.webkit.JavascriptInterface

class AndroidInterface (context: Context){

    private  val TAG = "AndroidInterface"
    private var mListener:SelectImageButtonClickListener = context as SelectImageButtonClickListener




    @JavascriptInterface
    fun onButtonsClicked()
    {
        Log.d(TAG, "onButtonsClicked: ")
        mListener.onStartMeetingButtonClicked()
    }

    interface SelectImageButtonClickListener{

        fun onStartMeetingButtonClicked()

    }

}