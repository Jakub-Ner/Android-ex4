package com.example.exercise04

import java.io.Serializable
import kotlin.random.Random


class DataItem : Serializable {
    var text2: String = "power= "
    var itemValue: Float = Random.nextFloat() * 5
    var item_checked: Boolean = Random.nextBoolean()


    var itemType: Int = Random.nextInt(0,3)

    constructor()

    constructor(value: Comparable<*>, checked: Boolean, type: Int) : this() {
        text2 = "power= "
        itemValue = value as Float
        itemType = type
        item_checked = checked
    }
}