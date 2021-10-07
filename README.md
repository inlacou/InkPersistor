# InkPersistor
Android Library for data persistance in Android SharedPreferences

Import with `implementation 'com.github.inlacou:InkPersistor:'` [![](https://jitpack.io/v/inlacou/InkPersistor.svg)](https://jitpack.io/#inlacou/InkPersistor)

## Normal
### String
* getStringValue
* setStringValue
* getNullableStringValue
* setNullableStringValue
### Int
* getIntValue
* setIntValue
* getNullableIntValue
* setNullableIntValue
### Long
* getLongValue
* setLongValue
* getNullableLongValue
* setNullableLongValue
### Float
* getFloatValue
* setFloatValue
* setNullableFloatValue
* getNullableFloatValue
### Double
* getDoubleValue
* setDoubleValue
* getNullableDoubleValue
* setNullableDoubleValue
### Boolean
* getBooleanValue
* setBooleanValue
* getNullableBooleanValue
* setNullableBooleanValue
### Enum
* setEnumValueByPosition
* setEnumValueByName
* getEnumValueByPosition
* getEnumValueByName
### Item
* getItem
* setItem
### List
* setList
* getList
* setNullableList
* getNullableList

## Advanced
There is a persistance mode designed to persist items partly, persisting them by their content. This was created aiming to persist and load part of a Redux Store, and such, it is not designed to save multiple items/instances of the same class.

On class definition, persistable parameters will be tagged with the @Persist tag, giving them a name or identificator with it. Then, the class instance can be saved with `save(context)`, and loaded again from the generic class `load(context)` method.

### Example

Class definition:
```kt
data class FakeSimplifiedStore(
	@Persist("STRING_NAME")
	val name: String? = null,
	@Persist("INT_AGE")
	val age: Int = 18,
	@Persist("LIST_STRING_SURNAMES")
	val surnames: List<String> = listOf("Baley", "Stark", "Kholin"),
	@Persist("ENUM_EXAMPLE_ENUM_FAVORITE_LETTER")
	val favoriteLetter: ExampleEnum = ExampleEnum.B,
): PersistSaveable {
	companion object: PersistLoadable
}
```

Load it:
```kt
FakeSimplifiedStore.load<FakeSimplifiedStore>(context)
```

Save it:
```kt
val item: FakeSimplifiedStore = /*get the instance from anywhere in your code*/
item.save(context)
```

There are more examples in project or in library android tests.
