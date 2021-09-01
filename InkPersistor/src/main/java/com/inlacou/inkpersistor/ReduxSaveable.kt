package com.inlacou.inkpersistor

import android.content.Context
import com.inlacou.inkkotlinextensions.toHashMap
import com.inlacou.inkkotlinextensions.toPairs

interface ReduxSaveable {
	//Works flawlessly
	fun save(context: Context) {
		val declaredFields = this::class.java.declaredFields
		for (field in declaredFields) {
			for (annotation in field.annotations) {
				when(annotation) {
					is Persist -> {
						field.isAccessible = true
						Persistor.setField(context, annotation.key, field, this)
					}
				}
			}
		}
	}
}

interface ReduxLoadable
//Works, but new object types or objects with generics must be added explicitly
inline fun <reified T : ReduxSaveable> ReduxLoadable.load(context: Context): T {
	//Get the data
	val map = T::class.java.declaredFields.flatMap { field -> field.annotations.asList().toPairs(field) }.mapNotNull { pair ->
		when(val annotation = pair.first) {
			is Persist -> Pair(pair.second,
				Persistor.getField(context, annotation.key, pair.second)
			)
			else -> null
		}
	}.toHashMap()
	//Make new empty instance (this clears the data)
	val target = T::class.java.newInstance()
	//Set the data
	T::class.java.declaredFields.forEach {
		if(map.containsKey(it)) {
			it.isAccessible = true
			it.set(target, map[it])
		}
	}
	return target
}
