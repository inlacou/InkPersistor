package com.inlacou.inkpersistor

import android.content.Context
import com.inlacou.inkkotlinextensions.toHashMap
import com.inlacou.inkkotlinextensions.toPairs
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.javaField

interface PersistLoadable
//Works, but new object types or objects with generics must be added explicitly
inline fun <reified T : PersistSaveable> PersistLoadable.load(context: Context): T {
	//Make new empty instance (this clears the data (what))
	val instance = T::class.java.newInstance()
	Logger.d("instance by reflect: $instance")
	//Get the data
	val map = T::class.declaredMemberProperties
		.flatMap { field -> field.javaField!!.annotations.asList().toPairs(field) }
		.mapNotNull { pair ->
			when(val annotation = pair.first) {
				is Persist -> Pair(pair.second, Persistor.getField(
						context = context, key = annotation.key, field = pair.second.javaField,
					isNullable = pair.second.returnType.isMarkedNullable, defaultValue = pair.second.getter.call(instance)))
				else -> null
			}
		}
		.toHashMap()
	//Set the data
	T::class.declaredMemberProperties.forEach {
		if(map.containsKey(it)) {
			it.javaField!!.isAccessible = true
			it.javaField!!.set(instance, map[it])
		}
	}
	return instance
}