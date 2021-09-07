package com.inlacou.inkpersistor

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.inlacou.inkpersistor.GenericSharedPrefMngr.erase
import com.inlacou.inkpersistor.GenericSharedPrefMngr.getBooleanValue
import com.inlacou.inkpersistor.GenericSharedPrefMngr.getDoubleValue
import com.inlacou.inkpersistor.GenericSharedPrefMngr.getFloatValue
import com.inlacou.inkpersistor.GenericSharedPrefMngr.getIntValue
import com.inlacou.inkpersistor.GenericSharedPrefMngr.getList
import com.inlacou.inkpersistor.GenericSharedPrefMngr.getLongValue
import com.inlacou.inkpersistor.GenericSharedPrefMngr.getStringValue
import com.inlacou.inkpersistor.GenericSharedPrefMngr.setBooleanValue
import com.inlacou.inkpersistor.GenericSharedPrefMngr.setDoubleValue
import com.inlacou.inkpersistor.GenericSharedPrefMngr.setEnumValueByName
import com.inlacou.inkpersistor.GenericSharedPrefMngr.setFloatValue
import com.inlacou.inkpersistor.GenericSharedPrefMngr.setIntValue
import com.inlacou.inkpersistor.GenericSharedPrefMngr.setItem
import com.inlacou.inkpersistor.GenericSharedPrefMngr.setList
import com.inlacou.inkpersistor.GenericSharedPrefMngr.setLongValue
import com.inlacou.inkpersistor.GenericSharedPrefMngr.setStringValue
import java.lang.reflect.Field

object Persistor {
	
	
	fun setField(context: Context, key: String, field: Field?, target: Any?, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)) {
		Logger.d("persisssstor | $key | ${field?.type?.simpleName} | $target")
		if(field==null || target==null) {
			erase(context = context, key = key, sharedPreferences = sharedPreferences); return }
		when(field.type) {
			Int::class.java -> setIntValue(context, key, field.getInt(target), sharedPreferences = sharedPreferences)
			String::class.java -> setStringValue(context, key, field.get(target) as String, sharedPreferences = sharedPreferences)
			Double::class.java -> setDoubleValue(context, key, field.getDouble(target), sharedPreferences = sharedPreferences)
			Boolean::class.java -> setBooleanValue(context, key, field.getBoolean(target), sharedPreferences = sharedPreferences)
			Long::class.java -> setLongValue(context, key, field.getLong(target), sharedPreferences = sharedPreferences)
			Float::class.java -> setFloatValue(context, key, field.getFloat(target), sharedPreferences = sharedPreferences)
			//Special types - easy here
			HashMap::class.java -> setList(context, key, (field.get(target) as HashMap<*, *>).toList(), sharedPreferences = sharedPreferences)
			List::class.java -> setList(context, key, (field.get(target) as List<*>), sharedPreferences = sharedPreferences)
			Enum::class.java -> {
				Logger.d("persisssstor | Enum | $key | ${field.type.simpleName} | $target")
				setEnumValueByName(context, key, field.get(target) as Enum<*>, sharedPreferences = sharedPreferences)
			}
			else -> {
				when(field.type.superclass) { //Check supertypes
					Enum::class.java -> setEnumValueByName(context, key, field.get(target) as Enum<*>, sharedPreferences = sharedPreferences)
					else -> setItem(context, key, field.get(target), sharedPreferences = sharedPreferences)
				}
			}
		}
	}
	
	fun getField(context: Context, key: String, field: Field?, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)): Any? {
		if(field==null) return null
		Logger.d("getField | field: ${field.name} | ${field.type} | ${field.type.simpleName}");
		return when(field.type) {
			Boolean::class.java -> getBooleanValue(context, key, sharedPreferences = sharedPreferences)
			Float::class.java -> getFloatValue(context, key, sharedPreferences = sharedPreferences)
			Double::class.java -> getDoubleValue(context, key, sharedPreferences = sharedPreferences)
			Long::class.java -> getLongValue(context, key, sharedPreferences = sharedPreferences)
			Int::class.java -> getIntValue(context, key, sharedPreferences = sharedPreferences)
			String::class.java -> getStringValue(context, key, sharedPreferences = sharedPreferences)
			//Lists and Maps
			HashMap::class.java, List::class.java -> {
				when(field.genericType) {
					GenericHolders::class.java.getDeclaredField("stringList").genericType -> getList<String>(context, key, sharedPreferences = sharedPreferences)
					else -> { additionalGenericLoader?.invoke(context, key, field, sharedPreferences) ?: throw ClassCastException("${field.name} type ${field.type} not supported. Add it on additionalGenericLoader function if you can.") }
				}
			}
			//Complex classes
			else -> additionalLoader?.invoke(context, key, field, sharedPreferences) ?: throw ClassCastException("${field.name} type ${field.type} not supported. Add it on additionalLoader function if you can.")
		}
	}
	
	var additionalLoader: ((context: Context, key: String, field: Field, sharedPreferences: SharedPreferences) -> Any?)? = null
	var additionalGenericLoader: ((context: Context, key: String, field: Field, sharedPreferences: SharedPreferences) -> Any?)? = null
	
	private class GenericHolders {
		private val stringList = listOf<String>()
	}
	
}