package com.example.exercise04.fragment2


import android.content.Context
import com.example.exercise04.DataBase.DBItem
import com.example.exercise04.DataBase.MyDB
import com.example.exercise04.DataBase.MyDao


class DataRepo2(context: Context) {
//    private var dataList: MutableList<DBItem>? = null
    private var myDao: MyDao
    private var db: MyDB

    companion object {
        private var R_INSTANCE: DataRepo2? = null

        fun getInstance(context: Context): DataRepo2 {
            if (R_INSTANCE == null) {
                R_INSTANCE = DataRepo2(context)
            }
            return R_INSTANCE as DataRepo2
        }
    }

    init {
        db = MyDB.getDatabase(context)!!
        myDao = db.myDao()!!
// addItem(DBItem(1))
// addItem(DBItem(2))
    }

    fun getData(): MutableList<DBItem>? {
        return myDao.getAllData()
    }
    fun addItem(item: DBItem) : Boolean {
        return myDao.insert(item) >= 0
    }
    fun deleteItem(item: DBItem) : Boolean {
        return myDao.delete(item) > 0
    }

    fun editItem(item: DBItem) {
        return myDao.update(item)
    }

}
