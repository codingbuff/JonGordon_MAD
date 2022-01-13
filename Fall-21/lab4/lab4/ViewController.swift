//
//  ViewController.swift
//  lab4
//
//  Created by Jon Gordon on 9/29/21.
//

import UIKit

class ViewController: UIViewController, UITextFieldDelegate {
    @IBOutlet weak var heightLabel: UILabel!
    @IBOutlet weak var sexLabel: UILabel!
    @IBOutlet weak var heightStepper: UIStepper!
    @IBOutlet weak var ageLabel: UILabel!
    @IBOutlet weak var ageField: UITextField!
    @IBOutlet weak var sexPicker: UIPickerView!
    @IBOutlet weak var weightLabel: UILabel!
    @IBOutlet weak var weightField: UITextField!
    @IBOutlet weak var mainLabel: UILabel!
    
    @IBOutlet weak var submitBtn: UIButton!
    
    let sexChoices = ["Select Sex", "Male", "Female"]
    var sexSelection:String?
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return true
    }
    
    override func viewDidLoad() {
        ageField.delegate = self
        sexPicker.dataSource = self
        sexPicker.delegate = self
        super.viewDidLoad()
        self.dismissKeyboard()
    }
    
    @IBAction func updateHeight(_ sender: UIStepper) {
        heightLabel.text = ("Height: " + String(Int(heightStepper.value)) + " in")
    }
    
    func calculateMaintenance(){

        if (sexSelection == nil || sexSelection == "Select Sex"){
            //error
            let alert = UIAlertController(title: "Warning", message: "Sex must be selected from the options",
                                          preferredStyle: UIAlertController.Style.alert)
            let okAction = UIAlertAction(title: "OK", style: UIAlertAction.Style.cancel, handler: nil)
            alert.addAction(okAction)
            present(alert, animated: true, completion: nil)
        }
        else {
            var maintenance:Float //return val
            var age = Float(ageField.text!)!
            var weight = Float(weightField.text!)!
            var height = Float(heightStepper.value)
            var weightToKilos = (weight/2.2046)
            var heightToCm = (height * 2.54)
            
            //male calculations
            if (sexSelection == "Male"){
                //Male: (88.4 + 13.4 x weight) + (4.8 x height) – (5.68 x age)
                maintenance = (88.4 + 13.4 * weight)
                maintenance += (4.8 * height)
                maintenance -= (5.68 * age)
                print("maintenance calories is: \(maintenance)")
            }
            //female calculations
            else {
                //(447.6 + 9.25 x weight) + (3.10 x height) – (4.33 x age)
                maintenance = (447.6 + 9.25 * weight)
                maintenance += (3.10 * height)
                maintenance -= (4.33 * age)
            }
            mainLabel.text = "Daily Calorie Maintenance: " + String(format:"%.0f", maintenance)
        }
    }

    @IBAction func calcMain(_ sender: UIButton) {
        calculateMaintenance()
    }
}

    
extension ViewController: UIPickerViewDataSource{

    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return sexChoices.count
    }
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        sexSelection = sexChoices[row]
    }
}
extension ViewController: UIPickerViewDelegate{
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        return sexChoices[row]
    }
}
    

extension UIViewController {
func dismissKeyboard() {
       let tap: UITapGestureRecognizer = UITapGestureRecognizer( target:     self, action:    #selector(UIViewController.dismissKeyboardTouchOutside))
       tap.cancelsTouchesInView = false
       view.addGestureRecognizer(tap)
    }
    
    @objc private func dismissKeyboardTouchOutside() {
       view.endEditing(true)
    }
}
