package com.example.comparefastfood

data class CalorieResults(var calorieCount: String="", var url: String="") {

    fun showResource(restaurant: Int,meal: String){
        setUrl(restaurant,meal)
    }

    fun setCalCount(count: String){
        calorieCount = count
    }

    private fun setUrl(restaurant: Int, meal: String){
        //mcdonalds
        if (restaurant == 0) {
            when(meal) {
                "Burger" -> url = "https://www.mcdonalds.com/us/en-us/product/big-mac.html"
                "Crispy Chicken" -> url = "https://www.mcdonalds.com/us/en-us/product/crispy-chicken-sandwich.html"
                "Nuggets" -> url = "https://www.mcdonalds.com/us/en-us/product/chicken-mcnuggets-6-piece.html"
            }
        }
        //burger king
        else if (restaurant == 1) {
            when(meal) {
                "Burger" -> url = "https://www.bk.com/menu/picker-picker_5520"
                "Crispy Chicken" -> url = "https://www.bk.com/menu/picker-edfb52db-081b-45bf-a33b-4b21d0885ef8"
                "Nuggets" -> url = "https://www.bk.com/menu/item-item_162"
            }
        }
        //wendy's
        else if (restaurant == 2) {
            when(meal) {
                "Burger" -> url = "https://order.wendys.com/product/30000/daves-single"
                "Crispy Chicken" -> url = "https://order.wendys.com/product/30014/classic-chicken-sandwich"
                "Nuggets" -> url = "https://order.wendys.com/product/30634/6-pc-crispy-chicken-nuggets"
            }
        }
    }
}