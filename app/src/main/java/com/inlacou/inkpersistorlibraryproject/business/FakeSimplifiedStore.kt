package com.inlacou.inkpersistorlibraryproject.business

import com.inlacou.inkpersistor.Persist
import com.inlacou.inkpersistor.PersistLoadable
import com.inlacou.inkpersistor.PersistSaveable

data class FakeSimplifiedStore(
	@Persist("STRING_NAME")
	val name: String? = null,
	@Persist("INT_AGE")
	val age: Int = 18,
	@Persist("LIST_STRING_SURNAMES")
	val surnames: List<String> = listOf("Montoya", "Pendragon", "Kholin"),
	@Persist("ENUM_EXAMPLE_ENUM_FAVORITE_LETTER")
	val favoriteLetter: ExampleEnum = ExampleEnum.B,
): PersistSaveable {
	companion object: PersistLoadable
}