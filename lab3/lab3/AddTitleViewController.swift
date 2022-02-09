//
//  AddTitleViewController.swift
//  lab3
//
//  Created by Jon Gordon on 2/7/22.
//

import UIKit

class AddTitleViewController: UIViewController {

    var addTitle = String()
    
    @IBOutlet weak var titleTextfield: UITextField!
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "doneSegue"{
            //only add a country if there is text in the textfield
            if titleTextfield.text?.isEmpty == false{
                addTitle=titleTextfield.text!
            }
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    



}
