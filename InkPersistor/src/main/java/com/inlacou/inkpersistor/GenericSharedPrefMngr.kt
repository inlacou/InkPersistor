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
		Logger.d("saved$key... ${sharedPreferences.getString(key, default)}")
		return sharedPreferences.getString(key, default) ?: default
	}
	
	fun setStringValue(context: Context, key: String, value: String, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)) {
		Logger.d("set$key... $value")
		val editor = sharedPreferences.edit()
		editor.putString(key, value)
		editor.commit()
	}
	
	fun getNullableStringValue(context: Context, key: String, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)): String? {
		Logger.d("saved$key... ${sharedPreferences.getString(key, null)}")
		return sharedPreferences.getString(key, null)
	}
	
	fun setNullableStringValue(context: Context, key: String, value: String?, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)) {
		Logger.d("set$key... $value")
		val editor = sharedPreferences.edit()
		editor.putString(key, value)
		editor.commit()
	}
	
	fun getLongValue(context: Context, key: String, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)): Long {
		Logger.d("saved$key... ${sharedPreferences.getLong(key, 0)}")
		return sharedPreferences.getLong(key, 0)
	}
	
	fun setLongValue(context: Context, key: String, value: Long, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)) {
		Logger.d("set$key... $value")
		val editor = sharedPreferences.edit()
		editor.putLong(key, value)
		editor.commit()
	}
	
	fun getNullableIntValue(context: Context, key: String): Int? {
		val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
		Logger.d("saved$key... ${sharedPreferences.getInt(key, Int.MIN_VALUE)}")
		return sharedPreferences.getInt(key, Int.MIN_VALUE).let {
			if(it==Int.MIN_VALUE){
				null
			}else{
				it
			}
		}
	}
	
	fun setNullableIntValue(context: Context, key: String, value: Int?) {
		Logger.d("set$key... $value")
		val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
		editor.putInt(key, value ?: Int.MIN_VALUE)
		editor.commit()
	}
	
	fun getNullableLongValue(context: Context, key: String): Long? {
		val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
		Logger.d("saved$key... ${sharedPreferences.getLong(key, Long.MIN_VALUE)}")
		return sharedPreferences.getLong(key, Long.MIN_VALUE).let {
			if(it==Long.MIN_VALUE){
				null
			}else{
				it
			}
		}
	}
	
	fun setNullableLongValue(context: Context, key: String, value: Long?) {
		Logger.d("set$key... $value")
		val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
		editor.putLong(key, value ?: Long.MIN_VALUE)
		editor.commit()
	}
	
	fun getIntValue(context: Context, key: String, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)): Int {
		Logger.d("saved$key... ${sharedPreferences.getInt(key, 0)}")
		return sharedPreferences.getInt(key, 0)
	}
	
	fun setIntValue(context: Context, key: String, value: Int, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)) {
		Logger.d("set$key... $value")
		val editor = sharedPreferences.edit()
		editor.putInt(key, value)
		editor.commit()
	}
	
	fun getFloatValue(context: Context, key: String, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)): Float {
		Logger.d("saved$key... ${sharedPreferences.getFloat(key, 0F)}")
		return sharedPreferences.getFloat(key, 0F)
	}
	
	fun setFloatValue(context: Context, key: String, value: Float, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)) {
		Logger.d("set$key... $value")
		val editor = sharedPreferences.edit()
		editor.putFloat(key, value)
		editor.commit()
	}
	
	fun getNullableFloatValue(context: Context, key: String): Float? {
		val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
		Logger.d("saved$key... ${sharedPreferences.getFloat(key, Float.MIN_VALUE)}")
		return sharedPreferences.getFloat(key, Float.MIN_VALUE).let {
			if(it==Float.MIN_VALUE) {
				null
			}else{
				it
			}
		}
	}
	
	fun setNullableFloatValue(context: Context, key: String, value: Float?) {
		Logger.d("set$key... $value")
		val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
		editor.putFloat(key, value ?: Float.MIN_VALUE)
		editor.commit()
	}
	
	fun getDoubleValue(context: Context, key: String, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)): Double {
		return (sharedPreferences.getString(key, "0.0") ?: "0.0").toDouble().apply {
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
		Logger.d("saved$key... ${sharedPreferences.getBoolean(key, default)}")
		return sharedPreferences.getBoolean(key, default)
	}
	
	fun setBooleanValue(context: Context, key: String, value: Boolean, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)) {
		Logger.d("set$key... $value")
		val editor = sharedPreferences.edit()
		editor.putBoolean(key, value)
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
	 * Warning, the GET part does not work
	 */
	fun <T: Enum<T>> setEnumValueByName(context: Context, key: String, value: Enum<T>, sharedPreferences: SharedPreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context)) {
		Logger.d("enumvaluebyname | set enum by name $key... ${value.name}(${value.ordinal})")
		val editor = sharedPreferences.edit()
		editor.putString(key, value.name)
		editor.commit()
	}
	
	//Look at alternative to maybe avoid reified ("not working, try with this: https://stackoverflow.com/questions/53563449/how-to-get-the-value-of-an-enum-using-generics")
	inline fun <reified T: Enum<T>> getEnumValueByPosition(context: Context, key: String, sharedPreferences: SharedPreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context)): T? {
		Logger.d("saved $key... ${sharedPreferences.getInt(key, -1)}")
		return sharedPreferences.getInt(key, -1).let {
			if(it==-1) null
			else enumValues<T>()[it]
		}
	}
	
	inline fun <reified T: Enum<T>> getEnumValueByName(context: Context, key: String, sharedPreferences: SharedPreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context)): T? {
		val aux = sharedPreferences.getString(key, "")
		Logger.d("enumvaluebyname | saved $key... $aux")
		return enumValues<T>().find {
			Logger.d("enumvaluebyname | $it == $aux")
			it.name==aux ?: "" }
	}
	
	inline fun <reified T: Any> getItem(context: Context, key: String, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)): T? {
		Logger.d("saved$key... ${sharedPreferences.getString(key, "{}")}")
		return sharedPreferences.getString(key, "{}")?.fromJson()
	}
	
	fun setItem(context: Context, key: String, value: Any?, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)) {
		Logger.d("set$key... $value")
		val editor = sharedPreferences.edit()
		editor.putString(key, value?.toJson() ?: "{}")
		editor.commit()
	}
	
	fun setList(context: Context, keyBase: String, value: List<Any?>, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)) {
		Logger.d("set list $keyBase... $value")
		val editor = sharedPreferences.edit()
		value.forEachIndexed { index, item ->
			editor.putString(keyBase+index, item?.toJson() ?: "{}")
		}
		val max = value.size
		(max .. (max+100)*2).forEach {
			editor.putString(keyBase+it, "{}")
		}
		editor.commit()
	}
	
	inline fun <reified T: Any> getList(context: Context, keyBase: String, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)): List<T> {
		Logger.d("get list $keyBase")
		val results = mutableListOf<T>()
		var index = 0
		var item: T?
		do {
			sharedPreferences.getString(keyBase+index, "{}")?.fromJson<T>().also {
				item = it
				index++
				if(it!=null) results.add(it)
			}
		}while (item!=null)
		return results
	}
	
}