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
### Boolean
* getBooleanValue
* setBooleanValue
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

## Advanced
There is a persistance mode designed to persist items partly, persisting them by their content. This was created aiming to persist and load part of a Redux Store, and such, it is not designed to save multiple items/instances of the same class.

On class definition, persistable parameters will be tagged with the @Persist tag, giving them a name or identificator with it. Then, the class instance can be saved with `save(context)`, and loaded again from the generic class `load(context)` method.
