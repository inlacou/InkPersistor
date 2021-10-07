package com.inlacou.inkpersistor

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.inlacou.inkkotlinextensions.toJson
import com.inlacou.inkpersistor.GenericSharedPrefMngr.erase
import com.inlacou.inkpersistor.GenericSharedPrefMngr.getBooleanValue
import com.inlacou.inkpersistor.GenericSharedPrefMngr.getDoubleValue
import com.inlacou.inkpersistor.GenericSharedPrefMngr.getFloatValue
import com.inlacou.inkpersistor.GenericSharedPrefMngr.getIntValue
import com.inlacou.inkpersistor.GenericSharedPrefMngr.getList
import com.inlacou.inkpersistor.GenericSharedPrefMngr.getLongValue
import com.inlacou.inkpersistor.GenericSharedPrefMngr.getNullableBooleanValue
import com.inlacou.inkpersistor.GenericSharedPrefMngr.getNullableDoubleValue
import com.inlacou.inkpersistor.GenericSharedPrefMngr.getNullableFloatValue
import com.inlacou.inkpersistor.GenericSharedPrefMngr.getNullableIntValue
import com.inlacou.inkpersistor.GenericSharedPrefMngr.getNullableLongValue
import com.inlacou.inkpersistor.GenericSharedPrefMngr.getNullableStringValue
import com.inlacou.inkpersistor.GenericSharedPrefMngr.getStringValue
import com.inlacou.inkpersistor.GenericSharedPrefMngr.setBooleanValue
import com.inlacou.inkpersistor.GenericSharedPrefMngr.setDoubleValue
import com.inlacou.inkpersistor.GenericSharedPrefMngr.setEnumValueByName
import com.inlacou.inkpersistor.GenericSharedPrefMngr.setFloatValue
import com.inlacou.inkpersistor.GenericSharedPrefMngr.setIntValue
import com.inlacou.inkpersistor.GenericSharedPrefMngr.setItem
import com.inlacou.inkpersistor.GenericSharedPrefMngr.setList
import com.inlacou.inkpersistor.GenericSharedPrefMngr.setLongValue
import com.inlacou.inkpersistor.GenericSharedPrefMngr.setNullableBooleanValue
import com.inlacou.inkpersistor.GenericSharedPrefMngr.setNullableDoubleValue
import com.inlacou.inkpersistor.GenericSharedPrefMngr.setNullableFloatValue
import com.inlacou.inkpersistor.GenericSharedPrefMngr.setNullableIntValue
import com.inlacou.inkpersistor.GenericSharedPrefMngr.setNullableLongValue
import com.inlacou.inkpersistor.GenericSharedPrefMngr.setNullableStringValue
import com.inlacou.inkpersistor.GenericSharedPrefMngr.setStringValue
import java.lang.reflect.Field
import kotlin.reflect.full.declaredMemberProperties

object Persistor {
	
	fun setField(context: Context, key: String, field: Field?, isNullable: Boolean, target: Any?, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)) {
		Logger.d("persisssstor | $key | ${field?.type?.simpleName} | $target")
		if(field==null || target==null) { erase(context = context, key = key, sharedPreferences = sharedPreferences); return }
		when(field.type) {
			Int::class.java, GenericHolders::class.java.getDeclaredField("nullableInt").type ->
				if(isNullable) setNullableIntValue(context, key, field.getInt(target), sharedPreferences = sharedPreferences)
				else setNullableIntValue(context, key, field.getInt(target), sharedPreferences = sharedPreferences)

			String::class.java, GenericHolders::class.java.getDeclaredField("nullableString").type ->
				if(isNullable) setNullableStringValue(context, key, field.get(target) as String, sharedPreferences = sharedPreferences)
			    else setStringValue(context, key, field.get(target) as String, sharedPreferences = sharedPreferences)

			Double::class.java, GenericHolders::class.java.getDeclaredField("nullableDouble").type ->
				if(isNullable) setNullableDoubleValue(context, key, field.getDouble(target), sharedPreferences = sharedPreferences)
				else setDoubleValue(context, key, field.getDouble(target), sharedPreferences = sharedPreferences)

			Boolean::class.java, GenericHolders::class.java.getDeclaredField("nullableBoolean").type ->
				if(isNullable) setNullableBooleanValue(context, key, field.getBoolean(target), sharedPreferences = sharedPreferences)
			    else setBooleanValue(context, key, field.getBoolean(target), sharedPreferences = sharedPreferences)

			Long::class.java, GenericHolders::class.java.getDeclaredField("nullableLong").type ->
				if(isNullable) setNullableLongValue(context, key, field.getLong(target), sharedPreferences = sharedPreferences)
			    else setLongValue(context, key, field.getLong(target), sharedPreferences = sharedPreferences)

			Float::class.java, GenericHolders::class.java.getDeclaredField("nullableFloat").type ->
				if(isNullable) setNullableFloatValue(context, key, field.getFloat(target), sharedPreferences = sharedPreferences)
			    else setFloatValue(context, key, field.getFloat(target), sharedPreferences = sharedPreferences)


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
	
	fun getField(
			context: Context,
			key: String,
			field: Field?,
			isNullable: Boolean,
			defaultValue: Any?,
			sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)): Any? {
		if(field==null) return null
		Logger.d("getField | key: $key | field: ${field.name} | ${field.type} | ${field.type.simpleName} | isNullable: $isNullable | default $defaultValue")
		field.javaClass.kotlin.typeParameters.forEachIndexed { index, item ->
			Logger.d("typeParameter $index: ${item.name}")
		}
		Logger.d("typeParameter: ${field.javaClass.kotlin}")
		return when(field.type) {
			Boolean::class.java, GenericHolders::class.java.getDeclaredField("nullableBoolean").type ->
				if(isNullable) getNullableBooleanValue(context, key, default = defaultValue as Boolean?, sharedPreferences = sharedPreferences)
				else getBooleanValue(context, key, default = defaultValue as Boolean, sharedPreferences = sharedPreferences)

			Float::class.java, GenericHolders::class.java.getDeclaredField("nullableFloat").type ->
				if(isNullable) getNullableFloatValue(context, key, default = defaultValue as Float?, sharedPreferences = sharedPreferences)
				else getFloatValue(context, key, default = defaultValue as Float, sharedPreferences = sharedPreferences)

			Double::class.java, GenericHolders::class.java.getDeclaredField("nullableDouble").type ->
				if(isNullable) getNullableDoubleValue(context, key, default = defaultValue as Double?, sharedPreferences = sharedPreferences)
				else getDoubleValue(context, key, default = defaultValue as Double, sharedPreferences = sharedPreferences)

			Long::class.java, GenericHolders::class.java.getDeclaredField("nullableLong").type ->
				if(isNullable) getNullableLongValue(context, key, default = defaultValue as Long?, sharedPreferences = sharedPreferences)
				else getLongValue(context, key, default = defaultValue as Long, sharedPreferences = sharedPreferences)

			Int::class.java, GenericHolders::class.java.getDeclaredField("nullableInt").type ->
				if(isNullable) getNullableIntValue(context, key, default = defaultValue as Int?, sharedPreferences = sharedPreferences)
				else getIntValue(context, key, default = defaultValue as Int, sharedPreferences = sharedPreferences)

			String::class.java, GenericHolders::class.java.getDeclaredField("nullableString").type ->
				if(isNullable) getNullableStringValue(context, key, default = defaultValue as String?, sharedPreferences = sharedPreferences)
				else getStringValue(context, key, default = defaultValue as String, sharedPreferences = sharedPreferences)

			//Lists and Maps
			HashMap::class.java, List::class.java -> {
				when(field.genericType) {
					GenericHolders::class.java.getDeclaredField("stringList").genericType -> getList<String>(context, key, default = defaultValue as List<String>, sharedPreferences = sharedPreferences)
					else -> if(additionalGenericLoader==null) throw FieldGenericTypeNotHandledException("${field.name} fields type '${field.type}' of generic type '${field.genericType}' not supported. Add an 'additionalGenericLoader' function with this class on it.")
							else additionalGenericLoader?.invoke(context, key, field, isNullable, defaultValue, sharedPreferences)
				}
			}
			//Complex classes
			else -> if(additionalLoader==null) throw FieldTypeNotHandledException("${field.name} fields type '${field.type}' not supported. Add an 'additionalLoader' function with this class on it.")
					else additionalLoader?.invoke(context, key, field, isNullable, defaultValue, sharedPreferences)
		}
	}
	
	var additionalLoader:        ((context: Context, key: String, field: Field, isNullable: Boolean, defaultValue: Any?, sharedPreferences: SharedPreferences) -> Any?)? = null
	var additionalGenericLoader: ((context: Context, key: String, field: Field, isNullable: Boolean, defaultValue: Any?, sharedPreferences: SharedPreferences) -> Any?)? = null
	
	private class GenericHolders {
		private val nullableBoolean: Boolean? = null
		private val nullableDouble: Double? = null
		private val nullableFloat: Float? = null
		private val nullableLong: Long? = null
		private val nullableInt: Int? = null
		private val nullableString: String? = null
		private val stringList = listOf<String>()
	}
	
}