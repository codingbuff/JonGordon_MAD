//
//  ViewController.swift
//  lab3
//
//  Created by Jon Gordon on 9/22/21.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var emuImg: UIImageView!
    @IBOutlet weak var capitalSwitch: UISwitch!
    @IBOutlet weak var imgControl: UISegmentedControl!
    @IBOutlet weak var fontSizeLabel: UILabel!
    @IBOutlet weak var colorSwitch: UISwitch!
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }


    func updateImage(){
        if imgControl.selectedSegmentIndex == 0 {
            titleLabel.text = "Tall Bird"
            emuImg.image = UIImage(named: "tallBird")
        }
        else if imgControl.selectedSegmentIndex == 1 {
            titleLabel.text = "Small Bird"
            emuImg.image = UIImage(named: "smallBird")
        }
    }
    func updateCaps(){
        if capitalSwitch.isOn {
            titleLabel.text = titleLabel.text?.uppercased()
        }
        else {
            titleLabel.text = titleLabel.text?.lowercased()
        }
    }
    
    func updateColor(){
        if colorSwitch.isOn{
            titleLabel.textColor = UIColor.red
        }
        else {
            titleLabel.textColor = UIColor.black
        }
    }
    
        @IBAction func changeInfo(_ sender: UISegmentedControl) {
            updateImage()
            updateCaps()
        }
        
        @IBAction func updateFont(_ sender: UISwitch) {
            updateCaps()
        }
    
    @IBAction func changeFontSize(_ sender: UISlider) {
        let fontSize=sender.value
        fontSizeLabel.text = String(format: "%.0f",fontSize)
        let fontSizeCGFloat = CGFloat(fontSize)
        titleLabel.font = UIFont.systemFont(ofSize: fontSizeCGFloat)
    }
    @IBAction func changeColor(_ sender: UISwitch) {
        updateColor()
    }
    
}

