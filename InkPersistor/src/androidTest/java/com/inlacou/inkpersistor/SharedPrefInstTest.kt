package com.inlacou.inkpersistor

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class SharedPrefInstTest {

	private lateinit var appContext: Context
	private val keys: MutableList<String> = mutableListOf()

	@Before
	fun setup() {
		appContext = ApplicationProvider.getApplicationContext()
	}

	@After
	fun cleanUp() {
		keys.forEach { GenericSharedPrefMngr.erase(appContext, it) }
	}

	@Test
	fun nullableString() {
		var value: String? = "nullableString"
		val key = "test_nullable_string_key"
		keys.add(key)
		GenericSharedPrefMngr.setNullableStringValue(appContext, key, value)
		Assert.assertEquals(value, GenericSharedPrefMngr.getNullableStringValue(appContext, key))
		value = null
		keys.add(key)
		GenericSharedPrefMngr.setNullableStringValue(appContext, key, value)
		Assert.assertEquals(value, GenericSharedPrefMngr.getNullableStringValue(appContext, key))
	}

	@Test
	fun string() {
		val value = "string"
		val key = "test_string_key"
		keys.add(key)
		println("value $value")
		println("key $key")
		GenericSharedPrefMngr.setStringValue(appContext, key, value)
		Assert.assertEquals(value, GenericSharedPrefMngr.getStringValue(appContext, key))
	}

	@Test
	fun int() {
		val value = 15
		val key = "test_int_key"
		keys.add(key)
		GenericSharedPrefMngr.setIntValue(appContext, key, value)
		Assert.assertEquals(value, GenericSharedPrefMngr.getIntValue(appContext, key))
	}

	@Test
	fun long() {
		val value = 15L
		val key = "test_long_key"
		keys.add(key)
		GenericSharedPrefMngr.setLongValue(appContext, key, value)
		Assert.assertEquals(value, GenericSharedPrefMngr.getLongValue(appContext, key))
	}

	@Test
	fun float() {
		val value = 15.5f
		val key = "test_float_key"
		keys.add(key)
		GenericSharedPrefMngr.setFloatValue(appContext, key, value)
		Assert.assertEquals(value, GenericSharedPrefMngr.getFloatValue(appContext, key), 0.0f)
	}

	@Test
	fun double() {
		val value = 15.5
		val key = "test_double_key"
		keys.add(key)
		GenericSharedPrefMngr.setDoubleValue(appContext, key, value)
		Assert.assertEquals(value, GenericSharedPrefMngr.getDoubleValue(appContext, key), 0.0)
	}

	@Test
	fun enum_by_name() {
		val value = ExampleEnum.OPTION_5
		val key = "test_enum_name_key"
		keys.add(key)
		GenericSharedPrefMngr.setEnumValueByName(appContext, key, value)
		Assert.assertEquals(value, GenericSharedPrefMngr.getEnumValueByName<ExampleEnum>(appContext, key))
	}

	@Test
	fun enum_by_position() {
		val value = ExampleEnum.OPTION_9
		val key = "test_enum_position_key"
		keys.add(key)
		GenericSharedPrefMngr.setEnumValueByPosition(appContext, key, value)
		Assert.assertEquals(value, GenericSharedPrefMngr.getEnumValueByPosition<ExampleEnum>(appContext, key))
	}

	@Test
	fun item_directly() {
		val value = ExampleItem("Dalinar", "Kholin", 52)
		val key = "test_item_key"
		keys.add(key)
		//First we should have no value
		Assert.assertEquals(null, GenericSharedPrefMngr.getItem<ExampleItem>(appContext, key))
		//We set a value
		GenericSharedPrefMngr.setItem(appContext, key, value)
		//So we get that value
		Assert.assertEquals(value, GenericSharedPrefMngr.getItem<ExampleItem>(appContext, key))
		//We nullify it
		GenericSharedPrefMngr.setItem(appContext, key, null)
		//So we have no value again
		Assert.assertEquals(null, GenericSharedPrefMngr.getItem<ExampleItem>(appContext, key))
		//We add it again
		GenericSharedPrefMngr.setItem(appContext, key, value)
		//Erase all
		GenericSharedPrefMngr.eraseAll(appContext)
		//And we should have null again
		Assert.assertEquals(null, GenericSharedPrefMngr.getItem<ExampleItem>(appContext, key))
	}

	enum class ExampleEnum { OPTION_1, OPTION_2, OPTION_3, OPTION_4, OPTION_5, OPTION_6, OPTION_7, OPTION_8, OPTION_9, OPTION_10  }
	data class ExampleItem(val name: String, val surname: String, val age: Int)
}
