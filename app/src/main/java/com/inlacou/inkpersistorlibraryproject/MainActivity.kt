package com.inlacou.inkpersistorlibraryproject

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.inlacou.inkkotlinextensions.toJson
import com.inlacou.inkpersistor.GenericSharedPrefMngr
import com.inlacou.inkpersistor.Persistor
import com.inlacou.inkpersistor.load
import com.inlacou.inkpersistorlibraryproject.business.ExampleEnum
import com.inlacou.inkpersistorlibraryproject.business.FakeSimplifiedStore
import com.inlacou.inkpersistorlibraryproject.databinding.ActivityMainBinding
import timber.log.Timber
import java.util.HashMap
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.javaField

class MainActivity : AppCompatActivity() {
	
	private lateinit var binding: ActivityMainBinding
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		
		setSupportActionBar(binding.toolbar)

		binding.fab.setOnClickListener { view ->
			Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show()
		}

		Timber.plant(Timber.DebugTree())

		Persistor.additionalLoader = { context, key, field, isNullable, default, sharedPreferences ->
			Log.e("persistor", "key:                  $key")
			Log.e("persistor", "type:                 ${field.type}")
			Log.e("persistor", "contemplated types 0: ${ExampleEnum::class.java}")
			when (field.type) {
				ExampleEnum::class.java ->
					if(isNullable) GenericSharedPrefMngr.getNullableEnumValueByName(this, key, default as ExampleEnum, sharedPreferences = sharedPreferences)
					else GenericSharedPrefMngr.getEnumValueByName(this, key, default as ExampleEnum, sharedPreferences = sharedPreferences)
				else -> null
			}
		}

		Timber.d("FakeSimplifiedStore.load<FakeSimplifiedStore>(this): ${FakeSimplifiedStore.load<FakeSimplifiedStore>(this).toJson()}")

		FakeSimplifiedStore::class.declaredMemberProperties.forEachIndexed { index, kProperty1 ->
			Timber.d("declaredMemberProperties $index: ${kProperty1.name} " +
			  "| returnType: ${kProperty1.returnType} " +
			  "| annotations2: ${kProperty1.javaField?.annotations?.map { it.annotationClass.simpleName }} " +
			  "| isMarkedNullable: ${kProperty1.returnType.isMarkedNullable}")
		}
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		// Inflate the menu; this adds items to the action bar if it is present.
		menuInflater.inflate(R.menu.menu_main, menu)
		return true
	}
	
	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		return when (item.itemId) {
			R.id.action_settings -> true
			else -> super.onOptionsItemSelected(item)
		}
	}

}