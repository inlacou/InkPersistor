package com.inlacou.inkpersistor

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.inlacou.inkkotlinextensions.toHashMap
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class PersistorInstTest {

	private lateinit var appContext: Context
	private val keys: MutableList<String> = mutableListOf()

	@Before
	fun setup() {
		appContext = ApplicationProvider.getApplicationContext()
		Persistor.additionalGenericLoader = { context, key, field, sharedPreferences ->
			when(field.genericType) {
				GenericHolders::class.java.getDeclaredField("countryList").genericType -> GenericSharedPrefMngr.getList<Country>(appContext, key, sharedPreferences = sharedPreferences)
				GenericHolders::class.java.getDeclaredField("countryVisitsHashMap").genericType -> GenericSharedPrefMngr.getList<Pair<Country, Int>>(appContext, key, sharedPreferences = sharedPreferences).toHashMap()
				else -> null
			}
		}
		Persistor.additionalLoader = { context, key, field, sharedPreferences ->
			when (field.type) {
				ExampleEnum::class.java -> GenericSharedPrefMngr.getEnumValueByName<ExampleEnum>(appContext, key, sharedPreferences = sharedPreferences)
				else -> null
			}
		}
	}

	private class GenericHolders {
		private val countryList = listOf<Country>()
		private val countryVisitsHashMap = hashMapOf<Country, Int>()
	}

	@After
	fun cleanUp() {
		keys.forEach { GenericSharedPrefMngr.erase(appContext, it) }
	}

	@Test
	fun item_content_by_annotations_primitives() {
		val value = ExampleItemPrimitiveValuesOnly("Dalinar", "Kholin", 52)
		value.save(appContext)
		Assert.assertEquals(value, ExampleItemPrimitiveValuesOnly.load<ExampleItemPrimitiveValuesOnly>(appContext))
	}

	@Test
	fun item_content_by_annotations_simple() {
		val value = ExampleItemPrimitivesAndSimpleGenerics("Dalinar", "Kholin", 52, countriesVisited = listOf("Spain", "France", "Italy", "Bali", "Mexico", "India"))
		value.save(appContext)
		Assert.assertEquals(value, ExampleItemPrimitivesAndSimpleGenerics.load<ExampleItemPrimitivesAndSimpleGenerics>(appContext))
	}

	@Test
	fun item_content_by_annotations_complex() {
		val value = ExampleItemPrimitivesAndComplexGenerics("Dalinar", "Kholin", 52, option = ExampleEnum.OPTION_9,
			countriesVisited = listOf(Country("Spain", 100), Country("France", 160), Country("Italy", 130), Country("Bali", 30), Country("Mexico", 400), Country("India", 500)),
			countriesVisitedTimes = hashMapOf(Pair(Country("Spain", 100), 12436721), Pair(Country("France", 100), 2)),
		)
		value.save(appContext)
		Assert.assertEquals(value, ExampleItemPrimitivesAndComplexGenerics.load<ExampleItemPrimitivesAndComplexGenerics>(appContext))
	}

	enum class ExampleEnum { OPTION_1, OPTION_2, OPTION_3, OPTION_4, OPTION_5, OPTION_6, OPTION_7, OPTION_8, OPTION_9, OPTION_10  }
	data class ExampleItemPrimitiveValuesOnly(
		@Persist("TEST_EXAMPLE_PERSIST_WITH_ANNOTATIONS_PRIMITIVES_ONLY_STRING")
		val name: String = "",
		@Persist("TEST_EXAMPLE_PERSIST_WITH_ANNOTATIONS_PRIMITIVES_ONLY_STRING_2")
		val surname: String = "",
		@Persist("TEST_EXAMPLE_PERSIST_WITH_ANNOTATIONS_PRIMITIVES_ONLY_INT")
		val age: Int = 0,
	): PersistSaveable {
		companion object: PersistLoadable
	}
	data class ExampleItemPrimitivesAndSimpleGenerics(
		@Persist("TEST_EXAMPLE_PERSIST_WITH_ANNOTATIONS_SIMPLE_GENERICS_STRING")
		val name: String = "",
		@Persist("TEST_EXAMPLE_PERSIST_WITH_ANNOTATIONS_SIMPLE_GENERICS_STRING_2")
		val surname: String = "",
		@Persist("TEST_EXAMPLE_PERSIST_WITH_ANNOTATIONS_SIMPLE_GENERICS_INT")
		val age: Int = 0,
		@Persist("TEST_EXAMPLE_PERSIST_WITH_ANNOTATIONS_SIMPLE_GENERICS_LIST")
		val countriesVisited: List<String> = listOf()
	): PersistSaveable {
		companion object: PersistLoadable
	}
	data class ExampleItemPrimitivesAndComplexGenerics(
		@Persist("TEST_EXAMPLE_PERSIST_WITH_ANNOTATIONS_COMPLEX_GENERICS_STRING")
		val name: String = "",
		@Persist("TEST_EXAMPLE_PERSIST_WITH_ANNOTATIONS_COMPLEX_GENERICS_STRING_2")
		val surname: String = "",
		@Persist("TEST_EXAMPLE_PERSIST_WITH_ANNOTATIONS_COMPLEX_GENERICS_INT")
		val age: Int = 0,
		@Persist("TEST_EXAMPLE_PERSIST_WITH_ANNOTATIONS_COMPLEX_GENERICS_ENUM")
		val option: ExampleEnum = ExampleEnum.OPTION_1,
		@Persist("TEST_EXAMPLE_PERSIST_WITH_ANNOTATIONS_COMPLEX_GENERICS_LIST")
		val countriesVisited: List<Country> = listOf(),
		@Persist("TEST_EXAMPLE_PERSIST_WITH_ANNOTATIONS_COMPLEX_GENERICS_HASHMAP")
		val countriesVisitedTimes: HashMap<Country, Int> = hashMapOf(),
	): PersistSaveable {
		companion object: PersistLoadable
	}
	data class Country(val name: String, val population: Long)
}
