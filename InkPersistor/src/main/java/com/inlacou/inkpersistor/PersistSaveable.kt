package com.inlacou.inkpersistor

import android.content.Context
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.javaField

interface PersistSaveable {
	//Works flawlessly
	fun save(context: Context) {
		val declaredFields = this::class.declaredMemberProperties
		for (field in declaredFields) {
			for (annotation in field.javaField?.annotations!!) {
				when(annotation) {
					is Persist -> {
						field.javaField?.isAccessible = true
						Persistor.setField(context, annotation.key, field.javaField, field.returnType.isMarkedNullable, this)
					}
				}
			}
		}
	}
}

