//
//  ViewController.swift
//  ChordProgresser
//
//  Created by Jon Gordon on 10/9/21.
//

import UIKit

class ViewController: UIViewController, UIPickerViewDataSource, UIPickerViewDelegate {

    //outlets
    @IBOutlet weak var chordPicker: UIPickerView!
    @IBOutlet weak var addBtn: UIButton!
    @IBOutlet weak var clearBtn: UIButton!
    @IBOutlet weak var spot1: UIImageView!
    @IBOutlet weak var spot2: UIImageView!
    @IBOutlet weak var spot3: UIImageView!
    @IBOutlet weak var spot4: UIImageView!
    @IBOutlet weak var emptySelected: UIImageView!
    @IBOutlet weak var clearSlot1: UIButton!
    @IBOutlet weak var clearSlot2: UIButton!
    @IBOutlet weak var clearSlot3: UIButton!
    @IBOutlet weak var clearSlot4: UIButton!
    @IBOutlet weak var emptySlot: UIButton!
    
    //chord datatypes
    var spotIdx = -1
    let chords = ["A","A#/Bb","B","C","C#/Db","D","D#/Eb","E","F","F#/Gb","G","G#/Ab"]
    let chordType = ["maj","min"]
    //dictionary maps picker selection to image file prefix name
    var pickerToImg:[String:String] = [
        "A":"A",
        "A#/Bb":"Asharp-Bflat",
        "B":"B",
        "C":"C",
        "C#/Db":"Csharp-Dflat",
        "D":"D",
        "D#/Eb":"Dsharp-Eflat",
        "E":"E",
        "F":"F",
        "F#/Gb":"Fsharp-Gflat",
        "G":"G",
        "G#/Ab":"Gsharp-Aflat"
    ]
    //boolean values determine which slots are filled
    var areFilled = [false, false, false, false]
    
    var chordSelection:String? = Optional.some("A")
    var typeSelection:String? = Optional.some("maj")
    override func viewDidLoad() {
        super.viewDidLoad()
        chordPicker.dataSource = self
        chordPicker.delegate = self
        clearSlot1.isEnabled = false
        clearSlot2.isEnabled = false
        clearSlot3.isEnabled = false
        clearSlot4.isEnabled = false
        clearSlot1.isHidden = true
        clearSlot2.isHidden = true
        clearSlot3.isHidden = true
        clearSlot4.isHidden = true
        spot1.isUserInteractionEnabled = true
        spot2.isUserInteractionEnabled = true
        spot3.isUserInteractionEnabled = true
        spot4.isUserInteractionEnabled = true
        
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        let touch = touches.first
        if (emptySelected != nil && emptySelected.image == UIImage(named: "emptySlot-highlighted")){
            emptySelected.image = UIImage(named: "emptySlot")
        }
        if (touch?.view == spot1 && areFilled[0] == false){
            spot1.image = UIImage(named: "emptySlot-highlighted")
            emptySelected = spot1
            emptySlot = clearSlot1
            spotIdx = 0
        }
        else if (touch?.view == spot2 && areFilled[1] == false){
            spot2.image = UIImage(named: "emptySlot-highlighted")
            emptySelected = spot2
            emptySlot = clearSlot2
            spotIdx = 1
        }
        else if (touch?.view == spot3 && areFilled[2] == false){
            spot3.image = UIImage(named: "emptySlot-highlighted")
            emptySelected = spot3
            emptySlot = clearSlot3
            spotIdx = 2
        }
        else if (touch?.view == spot4 && areFilled[3] == false){
            spot4.image = UIImage(named: "emptySlot-highlighted")
            emptySelected = spot4
            emptySlot = clearSlot4
            spotIdx = 3
        }
        
    }
    
    //"Add" is pressed
    @IBAction func addChord(_ sender: UIButton) {
        if (chordSelection != nil && typeSelection != nil){
            var chordToFill:String = pickerToImg[chordSelection!]! + typeSelection!
            //print("this is chordToFill: \(chordToFill)")
            
            if spotIdx > -1{
                emptySelected.image = UIImage(named: chordToFill)
                emptySlot.isEnabled = true
                emptySlot.isHidden = false
                areFilled[spotIdx] = true
                spotIdx = -1
            }
            
            else if areFilled[0] == false{
                spot1.image = UIImage(named: chordToFill);
                //spotIdx += 1;
                clearSlot1.isEnabled = true
                clearSlot1.isHidden = false
                areFilled[0] = true
            }
            else if areFilled[1] == false{
                spot2.image = UIImage(named: chordToFill);
                //spotIdx += 1;
                clearSlot2.isEnabled = true
                clearSlot2.isHidden = false
                areFilled[1] = true
            }
            else if  areFilled[2] == false{
                spot3.image = UIImage(named: chordToFill);
                //spotIdx += 1;
                clearSlot3.isEnabled = true
                clearSlot3.isHidden = false
                areFilled[2] = true
            }
            else if  areFilled[3] == false{
                spot4.image = UIImage(named: chordToFill);
                //spotIdx += 1;
                clearSlot4.isEnabled = true
                clearSlot4.isHidden = false
                areFilled[3] = true
            }
            else if !areFilled.contains(false){
                //put in an alert right here
                let alert = UIAlertController(title: "Slots Full", message: "There are no more available slots to fill. Delete a chord or clear all to add more.", preferredStyle: UIAlertController.Style.alert)
                let okAction = UIAlertAction(title: "OK", style: UIAlertAction.Style.default, handler: nil)
                alert.addAction(okAction)
                present(alert, animated: true, completion: nil)
            }
        }
        else{
            print("No selection made!")
        }
    }
    
    //redX button is pressed ()
    @IBAction func clearSingleSlot(_ sender: UIButton) {
        switch sender.tag {
        case 1:
            spot1.image = UIImage(named: "emptySlot")
            //spotIdx = 0
            areFilled[0] = false
            clearSlot1.isEnabled = false
            clearSlot1.isHidden = true
        case 2:
            spot2.image = UIImage(named: "emptySlot")
            //spotIdx = 1
            areFilled[1] = false
            clearSlot2.isEnabled = false
            clearSlot2.isHidden = true
        case 3:
            spot3.image = UIImage(named: "emptySlot")
            //spotIdx = 2
            areFilled[2] = false
            clearSlot3.isEnabled = false
            clearSlot3.isHidden = true
        case 4:
            spot4.image = UIImage(named: "emptySlot")
            //spotIdx = 3
            areFilled[3] = false
            clearSlot4.isEnabled = false
            clearSlot4.isHidden = true
        default:
            //spotIdx = 0
            print("Slot does not exist to delete chord")
        }
    }
    
    //"Clear" is pressed
    @IBAction func clearAllSlots(_ sender: UIButton) {
        spot1.image = UIImage(named: "emptySlot")
        spot2.image = UIImage(named: "emptySlot")
        spot3.image = UIImage(named: "emptySlot")
        spot4.image = UIImage(named: "emptySlot")
        //spotIdx = 0
        areFilled = [false, false, false, false]
        clearSlot1.isEnabled = false
        clearSlot2.isEnabled = false
        clearSlot3.isEnabled = false
        clearSlot4.isEnabled = false
        clearSlot1.isHidden = true
        clearSlot2.isHidden = true
        clearSlot3.isHidden = true
        clearSlot4.isHidden = true
    }
    
    
    //BEGINNING OF ADOPTED METHODS
    //Picker view methods adopted from https://www.youtube.com/watch?v=EsheQe6U_WE

    func numberOfComponents(in pickerView: UIPickerView) -> Int {
            return 2
        }
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        if component == 0 {
            return chords.count
        }
        else {
            return chordType.count
        }
    }
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        if component == 0 {
            chordSelection = chords[row]
        }
        else {
            typeSelection = chordType[row]
        }
    }
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        if component == 0 {
            return chords[row]
        }
        else {
            return chordType[row]
        }
    }
// END OF ADOPTED METHODS
    
}

