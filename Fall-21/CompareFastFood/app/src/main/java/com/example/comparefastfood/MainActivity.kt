package com.example.comparefastfood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.switchmaterial.SwitchMaterial

 class MainActivity : AppCompatActivity() {
     var message: String = ""
     lateinit var outputMsg: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        outputMsg = findViewById<TextView>(R.id.output)
    }

     fun calcCalories(view: android.view.View) {
         //UI Controls and Strings
         val resSpin = findViewById<Spinner>(R.id.spinner)
         val mealRadio = findViewById<RadioGroup>(R.id.mealRadio)
         val mealId = mealRadio.checkedRadioButtonId
         val layoutRoot = findViewById<ConstraintLayout>(R.id.root_layout)
         val drinkRadio = findViewById<RadioGroup>(R.id.drinkRadio)
         val drinkId = drinkRadio.checkedRadioButtonId
         val iceCreamCheck = findViewById<CheckBox>(R.id.iceCream)
         val cookieCheck = findViewById<CheckBox>(R.id.cookie)
         val largeSwitch = findViewById<Switch>(R.id.switch1)
         //val outputMsg = findViewById<TextView>(R.id.output)
         message = "The calorie count from the selected options and a side of fries is: "
         //Calorie Counters
         //McDonald's nutrition from https://www.mcdonalds.com/us/en-us.html
         val mcDonaldCal = LinkedHashMap<String,Int>()
         mcDonaldCal["Burger"] = 550 //big mac calories, keeping it simple here
         mcDonaldCal["Crispy Chicken"] = 470
         mcDonaldCal["Nuggets"] = 250 //assuming 6 piece
         mcDonaldCal["medFry"] = 320
         mcDonaldCal["largeFry"] = 490
         mcDonaldCal["Chocolate Cookie"] = 170
         mcDonaldCal["Vanilla Cone"] = 200

         //Burger King nutrition from https://www.bk.com/menu
         val burgerKingCal = LinkedHashMap<String,Int>()
         burgerKingCal["Burger"] = 657 //whopper
         burgerKingCal["Crispy Chicken"] = 800
         burgerKingCal["Nuggets"] = 260 //assuming 6 piece
         burgerKingCal["medFry"] = 508
         burgerKingCal["largeFry"] = 608
         burgerKingCal["Chocolate Cookie"] = 329
         burgerKingCal["Vanilla Cone"] = 142

         //Wendy's nutrtion from https://order.wendys.com/categories?site=menu
         val wendysCal = LinkedHashMap<String,Int>()
         wendysCal["Burger"] = 590 //Dave's single
         wendysCal["Crispy Chicken"] = 490
         wendysCal["Nuggets"] = 280 //assuming 6 piece
         wendysCal["medFry"] = 350
         wendysCal["largeFry"] = 470
         wendysCal["Chocolate Cookie"] = 330
         wendysCal["Vanilla Cone"] = 340 //obviously gotta do the frosty here (small)

         //drink calories could vary per restaurant but
         //we're gonna assume they're all the same
         //info taken from mcdonald's resource (see above)
         val cokeCal = LinkedHashMap<String,Int>()
         cokeCal["med"] = 210
         cokeCal["large"] = 290
         val spriteCal = LinkedHashMap<String,Int>()
         spriteCal["med"] = 200
         spriteCal["large"] = 230
         val drPepperCal = LinkedHashMap<String,Int>()
         drPepperCal["med"] = 200
         drPepperCal["large"] = 230


         if (mealId == -1) {
             Log.i("if statement", "$mealId")
             //Snackbar
             val fillingSnackbar =
                 Snackbar.make(layoutRoot, "Please select a meal", Snackbar.LENGTH_SHORT)
             fillingSnackbar.show()
         }else {

             var calorieCount = 0 //keeps track of calories based on user choices

             //userCalMap maps meal choices to calorie amount depending on restaurant picked
             val userCalMap: MutableMap<String, Int> = HashMap()
             if (resSpin.selectedItem == "McDonald's") {userCalMap.putAll(mcDonaldCal)}
             else if (resSpin.selectedItem == "Burger King") {userCalMap.putAll(burgerKingCal)}
             else if (resSpin.selectedItem == "Wendy's") {userCalMap.putAll(wendysCal)}

             //Debug map
             //Log.i("userCalMap ", "$userCalMap")

             val selectedMeal = findViewById<RadioButton>(mealId).text
             calorieCount += userCalMap[selectedMeal]!!

             //if user selects a drink (no default needed)
             if (drinkId != -1){
                 val selectedDrink = findViewById<RadioButton>(drinkId).text
                 val drinkChoiceMap: MutableMap<String, Int> = HashMap() //maps drink to calorie amounts based on sizes
                 if(selectedDrink == "Coke"){drinkChoiceMap.putAll(cokeCal)}
                 else if (selectedDrink == "Sprite"){drinkChoiceMap.putAll(spriteCal)}
                 else if (selectedDrink == "Dr. Pepper"){drinkChoiceMap.putAll(drPepperCal)}

                 //large switch is checked means large drink
                 if (largeSwitch.isChecked){
                     calorieCount += drinkChoiceMap["large"]!!
                 }
                 //regular-sized drink
                 else {
                     calorieCount += drinkChoiceMap["med"]!!
                 }
             }

             //if user added ice cream cone
             if (iceCreamCheck.isChecked){
                 calorieCount += userCalMap[iceCreamCheck.text]!!
             }

             //if user added chocolate cookie
             if (cookieCheck.isChecked){
                 calorieCount += userCalMap[cookieCheck.text]!!
             }

             //Add fry calories depending on meal size (regular or large)
             if (largeSwitch.isChecked){
                 calorieCount += userCalMap["largeFry"]!!
             }
             else {
                 calorieCount += userCalMap["medFry"]!!
             }

             //display computed calorie count
             val calorieString = calorieCount.toString()
            message = "The calorie count from the selected options and a side of fries is: " + calorieString
         //outputMsg.text = "The calorie count from the selected options and a side of fries is: " + calorieString
            updateUI()
         }
     }

     fun updateUI(){
        outputMsg.text = message
     }

     override fun onSaveInstanceState(outState: Bundle) {
         super.onSaveInstanceState(outState)
         outState.putString("message", message)
         super.onSaveInstanceState(outState)
     }

     override fun onRestoreInstanceState(savedInstanceState: Bundle) {
         super.onRestoreInstanceState(savedInstanceState)
         message = savedInstanceState.getString("message", "")
         updateUI()
     }

 }