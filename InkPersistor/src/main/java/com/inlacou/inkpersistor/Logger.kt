package com.inlacou.inkpersistor

import android.util.Log
import timber.log.Timber

@PublishedApi internal object Logger {
	fun d(s: String) {
		try {
			Timber.d(s)
		}catch (e: NoClassDefFoundError){
			Log.d("InkPersistor", s)
		}
	}
}