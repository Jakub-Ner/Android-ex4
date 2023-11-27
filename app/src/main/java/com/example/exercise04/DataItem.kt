package com.example.exercise04

import java.io.Serializable
import kotlin.random.Random


class DataItem: Serializable {
    var text_main : String = "Default text"
    var text_2 : String = "value= "
    var item_value : Int = Random.nextInt(0, 5)
    var item_value2: Int = 0
    var item_type : Boolean = Random.nextBoolean()
    var item_checked : Boolean = Random.nextBoolean()
    constructor()
    constructor(num: Int) : this() {
        text_main = "Item name "+num
    }

    constructor(name: String, value: Comparable<*>, checked: Boolean, type: String) : this() {
        text_main = name
        text_2 = "value= "
        item_value = value as Int
        item_checked = checked
        item_type = type == "Plane"
    }
}