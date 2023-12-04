package com.example.exercise04.DataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.exercise04.R
import java.io.Serializable
import kotlin.random.Random

    @Entity(tableName = "item_table")
    class DBItem : Serializable {
        @PrimaryKey(autoGenerate = true)
        var id = 0

        @ColumnInfo(name = "name")
        var item_name: String = "D"
        @ColumnInfo(name = "date")
        var item_date: String = ""
        @ColumnInfo(name = "rating")
        var item_rating : Float = 0.0f
        @ColumnInfo(name = "image")
        var item_image : Int = R.drawable.energy_drink
        @ColumnInfo(name = "type")
        var item_type : String= ""
        @ColumnInfo(name = "checked")
        var item_checked : Boolean= Random.nextBoolean()

//        constructor()

//        constructor(num: Int) {
//            item_name = "Item name " + num
//            item_date = "Brak daty"
//            val travelCompanions = when (Random.nextInt(0, 3)) {
//                0 -> "Coffee Mug"
//                1 -> "Cup of Tea"
//                2 -> "Energy drink"
//                else -> throw IllegalStateException("Unexpected value")
//            }
//            item_rating = Random.nextDouble(0.0, 5.0).toFloat()
//            item_type = "Żaden z wymienionych"
//            item_image = R.drawable.energy_drink
//            item_checked = Random.nextBoolean()
//        }

        constructor(
            item_name: String,
            item_date: String,
            item_rating : Float,
            item_type : String,
            item_checked : Boolean
        ) {
            this.item_name = item_name
            this.item_date = item_date
            this.item_rating = item_rating
            this.item_type = item_type
            this.item_checked = item_checked

            when(item_type){
                "Coffee Mug" -> item_image = R.drawable.coffee_mug
                "Cup of Tea" -> item_image = R.drawable.cup_of_tea
                "Energy drink" -> item_image = R.drawable.energy_drink
            }
        }

    }
