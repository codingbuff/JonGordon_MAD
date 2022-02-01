//
//  ViewController.swift
//  lab2
//
//  Created by Jon Gordon on 1/31/22.
//

import UIKit

class ViewController: UIViewController, UIPickerViewDelegate, UIPickerViewDataSource {

    @IBOutlet weak var choiceLabel: UILabel!
    @IBOutlet weak var numberPicker: UIPickerView!
    
    var languageData = DataLoader()
    let languages = ["English","Spanish","German"]
    let numbers = ["uno","dos","tres"]
    let languageComponent = 0
    let numberComponent = 1
    let filename = "numbers.plist"
    
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 2
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        if component == languageComponent {
            return languages.count
        } else {
            return numbers.count
        }
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        if component == languageComponent {
            return languages[row]
        } else {
            return numbers[row]
        }
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
         let languagerow = pickerView.selectedRow(inComponent: 0) //gets the selected row for the genre
         let numberrow = pickerView.selectedRow(inComponent: 1) //gets the selected row for the decade
         choiceLabel.text="You picked the \(languages[languagerow]) language and numero \(numbers[numberrow])"
     }
        
    

    override func viewDidLoad() {
        super.viewDidLoad()
//        languageData.loadData(filename: filename)
//        languages = languageData.getLanguages()
//        numbers = languageData.getNumbers(index: 1)
    } 


}

