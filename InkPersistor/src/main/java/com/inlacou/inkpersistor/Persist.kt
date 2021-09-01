package com.inlacou.inkpersistor

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class Persist(val key: String)
