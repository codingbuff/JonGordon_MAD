//
//  ViewController.swift
//  lab1
//
//  Created by Jon Gordon on 9/8/21.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var labelText: UILabel!
    @IBOutlet weak var hybridImg: UIImageView!
    @IBAction func makeHybrid(_ sender: UIButton) {
        if sender.tag == 1 {
            hybridImg.image=UIImage(named: "seaRex")
            labelText.text = "You made a Sea Rex!"
        }
        else if sender.tag == 2 {
            hybridImg.image=UIImage(named: "catPanda")
            labelText.text = "You made a Pitty!"
        }
    }
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }


}

