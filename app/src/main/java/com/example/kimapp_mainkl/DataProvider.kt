package com.example.kimapp_mainkl

object DataProvider {
    val genderData: MutableMap<String, Any> = mutableMapOf()
    fun saveData(key: String, value: Any) {
        genderData[key] = value
    }

    fun getData(key: String): Any? {
        return genderData[key]
    }
}
