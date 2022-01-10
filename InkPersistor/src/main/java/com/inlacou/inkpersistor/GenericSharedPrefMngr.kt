package com.inlacou.inkpersistor

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.inlacou.inkkotlinextensions.fromJson
import com.inlacou.inkkotlinextensions.toJson

/**
 * Created by inlacou on 18/08/17.
 */
object GenericSharedPrefMngr {
	
	fun eraseAll(context: Context, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)) {
		sharedPreferences.edit().clear().commit()
	}
	
	fun erase(context: Context, key: String, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)) {
		sharedPreferences.edit().remove(key).commit()
	}
	
	fun getStringValue(context: Context, key: String, default: String = "", sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)): String {
		Logger.d("getStringValue | saved$key... ${sharedPreferences.getString(key, default)}")
		return sharedPreferences.getString(key, default) ?: default
	}
	
	fun setStringValue(context: Context, key: String, value: String, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)) {
		Logger.d("setStringValue | set$key... $value")
		val editor = sharedPreferences.edit()
		editor.putString(key, value)
		editor.commit()
	}
	
	fun getNullableStringValue(context: Context, key: String, default: String? = null, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)): String? {
		Logger.d("getNullableStringValue | saved$key... ${sharedPreferences.getString(key, default)}")
		return sharedPreferences.getString(key, default)
	}
	
	fun setNullableStringValue(context: Context, key: String, value: String?, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)) {
		Logger.d("setNullableStringValue | set$key... $value")
		val editor = sharedPreferences.edit()
		editor.putString(key, value)
		editor.commit()
	}
	
	fun getLongValue(context: Context, key: String, default: Long = 0, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)): Long {
		Logger.d("saved$key... ${sharedPreferences.getLong(key, default)}")
		return sharedPreferences.getLong(key, default)
	}
	
	fun setLongValue(context: Context, key: String, value: Long, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)) {
		Logger.d("set$key... $value")
		val editor = sharedPreferences.edit()
		editor.putLong(key, value)
		editor.commit()
	}
	
	fun getNullableIntValue(context: Context, key: String, default: Int?, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)): Int? {
		Logger.d("getNullableIntValue | saved$key... ${sharedPreferences.getInt(key, Int.MIN_VALUE)}")
		return sharedPreferences.getInt(key, Int.MIN_VALUE).let {
			if(it==Int.MIN_VALUE){
				default
			}else{
				it
			}.also {
				Logger.d("getNullableIntValue | saved$key... $it")
			}
		}
	}
	
	fun setNullableIntValue(context: Context, key: String, value: Int?, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)) {
		Logger.d("setNullableIntValue | set$key... $value")
		val editor = sharedPreferences.edit()
		editor.putInt(key, value ?: Int.MIN_VALUE)
		editor.commit()
	}
	
	fun getNullableLongValue(context: Context, key: String, default: Long? = null, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)): Long? {
		Logger.d("saved$key... ${sharedPreferences.getLong(key, Long.MIN_VALUE)}")
		return sharedPreferences.getLong(key, Long.MIN_VALUE).let {
			if(it==Long.MIN_VALUE){
				default
			}else{
				it
			}
		}
	}
	
	fun setNullableLongValue(context: Context, key: String, value: Long?, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)) {
		Logger.d("set$key... $value")
		val editor = sharedPreferences.edit()
		editor.putLong(key, value ?: Long.MIN_VALUE)
		editor.commit()
	}
	
	fun getIntValue(context: Context, key: String, default: Int = 0, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)): Int {
		Logger.d("getIntValue | saved$key... ${sharedPreferences.getInt(key, default)}")
		return sharedPreferences.getInt(key, default)
	}
	
	fun setIntValue(context: Context, key: String, value: Int, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)) {
		Logger.d("setIntValue | set$key... $value")
		val editor = sharedPreferences.edit()
		editor.putInt(key, value)
		editor.commit()
	}
	
	fun getFloatValue(context: Context, key: String, default: Float = 0f, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)): Float {
		Logger.d("saved$key... ${sharedPreferences.getFloat(key, default)}")
		return sharedPreferences.getFloat(key, default)
	}
	
	fun setFloatValue(context: Context, key: String, value: Float, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)) {
		Logger.d("set$key... $value")
		val editor = sharedPreferences.edit()
		editor.putFloat(key, value)
		editor.commit()
	}
	
	fun getNullableFloatValue(context: Context, key: String, default: Float?, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)): Float? {
		Logger.d("saved$key... ${sharedPreferences.getFloat(key, Float.MIN_VALUE)}")
		return sharedPreferences.getFloat(key, Float.MIN_VALUE).let {
			if(it==Float.MIN_VALUE) {
				default
			}else{
				it
			}
		}
	}
	
	fun setNullableFloatValue(context: Context, key: String, value: Float?, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)) {
		Logger.d("set$key... $value")
		val editor = sharedPreferences.edit()
		editor.putFloat(key, value ?: Float.MIN_VALUE)
		editor.commit()
	}

	fun getNullableDoubleValue(context: Context, key: String, default: Double?, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)): Double? {
		return (sharedPreferences.getString(key, default?.toString() ?: "") ?: (default?.toString() ?: "")).toDoubleOrNull().apply {
			Logger.d("saved$key... $this")
		}
	}
	
	fun setNullableDoubleValue(context: Context, key: String, value: Double?, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)) {
		Logger.d("set$key... $value")
		val editor = sharedPreferences.edit()
		editor.putString(key, value?.toString() ?: "")
		editor.commit()
	}

	fun getDoubleValue(context: Context, key: String, default: Double = 0.0, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)): Double {
		return (sharedPreferences.getString(key, default.toString()) ?: default.toString()).toDouble().apply {
			Logger.d("saved$key... $this")
		}
	}

	fun setDoubleValue(context: Context, key: String, value: Double, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)) {
		Logger.d("set$key... $value")
		val editor = sharedPreferences.edit()
		editor.putString(key, value.toString())
		editor.commit()
	}

	fun getBooleanValue(context: Context, key: String, default: Boolean = false, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)): Boolean {
		return (sharedPreferences.getInt(key, if(default) 1 else 0)==1).also {
			Logger.d("getBooleanValue | saved$key... $it")
		}

	}
	
	fun setBooleanValue(context: Context, key: String, value: Boolean, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)) {
		Logger.d("setBooleanValue | set$key... $value")
		val editor = sharedPreferences.edit()
		editor.putInt(key, if(value) 1 else 0)
		editor.commit()
	}

	fun getNullableBooleanValue(context: Context, key: String, default: Boolean?, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)): Boolean? {
		return sharedPreferences.getInt(key, if(default==null) -1 else if(default) 1 else 0).let {
			(if(it==1) true else if(it==0) false else null).also {
				Logger.d("getNullableBooleanValue | saved$key... $it")
			}
		}
	}

	fun setNullableBooleanValue(context: Context, key: String, value: Boolean?, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)) {
		Logger.d("setNullableBooleanValue | set$key... $value")
		val editor = sharedPreferences.edit()
		editor.putInt(key, if(value==null) Int.MIN_VALUE else if(value) 1 else 0)
		editor.commit()
	}

	/**
	 * Warning, the GET part does not work
	 */
	fun <T: Enum<T>> setEnumValueByPosition(context: Context, key: String, value: Enum<T>, sharedPreferences: SharedPreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context)) {
		Logger.d("set enum by position $key... ${value.ordinal}(${value.name})")
		val editor = sharedPreferences.edit()
		editor.putInt(key, value.ordinal)
		editor.commit()
	}

	/**
	 * WARNING, not tested
	 */
	fun <T: Enum<T>> setNullableEnumValueByPosition(context: Context, key: String, value: Enum<T>?, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)) {
		Logger.d("set enum by position $key... ${value?.ordinal}(${value?.name})")
		val editor = sharedPreferences.edit()
		if(value==null) editor.remove(key)
		else editor.putInt(key, value.ordinal)
		editor.commit()
	}

	fun <T: Enum<T>> setEnumValueByName(context: Context, key: String, value: Enum<T>, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)) {
		val editor = sharedPreferences.edit()
		editor.putString(key, value.name)
		editor.commit()
	}

	fun <T: Enum<T>> setNullableEnumValueByName(context: Context, key: String, value: Enum<T>?, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)) {
		val editor = sharedPreferences.edit()
		if(value==null) editor.remove(key)
		else editor.putString(key, value.name)
		editor.commit()
	}

	//Look at alternative to maybe avoid reified ("not working, try with this: https://stackoverflow.com/questions/53563449/how-to-get-the-value-of-an-enum-using-generics")
	inline fun <reified T: Enum<T>> getEnumValueByPosition(context: Context, key: String, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)): T? {
		Logger.d("saved $key... ${sharedPreferences.getInt(key, -1)}")
		return sharedPreferences.getInt(key, -1).let {
			if(it==-1) null
			else enumValues<T>()[it]
		}
	}
	
	inline fun <reified T: Enum<T>> getEnumValueByName(context: Context, key: String, default: T, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)): T {
		val aux = sharedPreferences.getString(key, "")
		Logger.d("getEnumValueByName | saved $key... $aux")
		return enumValues<T>().find {
			Logger.d("getEnumValueByName | find $it==$aux")
			it.name==aux ?: ""
		} ?: default
	}

	inline fun <reified T: Enum<T>> getNullableEnumValueByName(context: Context, key: String, default: T? = null, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)): T? {
		val aux = sharedPreferences.getString(key, "")
		Logger.d("getNullableEnumValueByName | saved $key... $aux")
		return enumValues<T>().find {
			Logger.d("getNullableEnumValueByName | find $it==$aux")
			it.name==aux ?: ""
		} ?: default
	}

	inline fun <reified T: Any> getItem(context: Context, key: String, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)): T? {
		Logger.d("saved$key... ${sharedPreferences.getString(key, "{}")}")
		return sharedPreferences.getString(key, "{}")?.fromJson()
	}
	inline fun <reified T: Any> getItem(context: Context, key: String, clazz: Class<out T>, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)): T? {
		Logger.d("saved$key... ${sharedPreferences.getString(key, "{}")}")
		return sharedPreferences.getString(key, "{}")?.fromJson(clazz)
	}

	fun <T> setItem(context: Context, key: String, value: T, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)) {
		Logger.d("set$key... $value")
		val editor = sharedPreferences.edit()
		editor.putString(key, value?.toJson() ?: "{}")
		editor.commit()
	}
	fun <T> setItem(context: Context, key: String, value: T, clazz: Class<out T>, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context))
		= GenericSharedPrefMngr.setItem(context, key, value, sharedPreferences)

	fun setList(context: Context, key: String, value: List<Any?>, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)) {
		Logger.d("set list $key... $value")
		val editor = sharedPreferences.edit()
		//Save the number of items to retrieve later
		editor.putString(key, value.size.toString())
		value.forEachIndexed { index, item ->
			editor.putString(key+index, item?.toJson() ?: "{}")
		}
		editor.commit()
	}

	/**
	 * WARNING! if T is nullable it will not add to the result the null items that should :S
	 */
	inline fun <reified T: Any> getList(context: Context, key: String, default: List<T>, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)): List<T> {
		Logger.d("get list $key")
		val results = mutableListOf<T>()
		val size = sharedPreferences.getString(key, null)?.toInt() ?: return default
		repeat(size) {
			sharedPreferences.getString(key+it, "{}")?.fromJson<T>().also {
				if(it!=null) results.add(it)
			}
		}
		return results
	}

	/**
	 * WARNING! if T is nullable it will not add to the result the null items that should :S
	 */
	inline fun <reified T: Any> getNullableList(context: Context, key: String, default: List<T>? = null, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)): List<T>? {
		Logger.d("get list $key")
		val results = mutableListOf<T>()
		val size = sharedPreferences.getString(key, null)?.toInt() ?: return default
		repeat(size) {
			sharedPreferences.getString(key+it, "{}")?.fromJson<T>().also {
				if(it!=null) results.add(it)
			}
		}
		return results
	}

}