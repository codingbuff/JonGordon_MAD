package com.example.lab7.sample

import com.example.lab7.R
import com.example.lab7.model.Cat

object SampleData {
    val catList = ArrayList<Cat>()

    init {
        catList.add(Cat("Naked Cat", R.drawable.bald))
        catList.add(Cat("Dark Cat", R.drawable.dark))
        catList.add(Cat("Fast Cat", R.drawable.fast))
        catList.add(Cat("Fluffy Cat", R.drawable.fluffy))
        catList.add(Cat("Wild Cat", R.drawable.wild))
    }
}