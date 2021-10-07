package com.inlacou.inkpersistor

import kotlin.Exception

class FieldTypeNotHandledException: Exception {
	constructor(message: String): super(message)
	constructor(message: String, throwable: Throwable): super(message, throwable)
	constructor(throwable: Throwable): super(throwable)
}