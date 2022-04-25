package com.example.chordjam

import android.os.Parcel
import java.io.Serializable


class ProgItemList(progressionList: MutableList<ProgItem>):Serializable {
    private var itemList: MutableList<ProgItem> = progressionList

    fun getItems(): MutableList<ProgItem> {
        return itemList
    }

    fun getItemAt(index: Int): ProgItem{
        return itemList[index]
    }

}

