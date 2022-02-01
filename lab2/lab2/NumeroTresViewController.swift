//
//  NumeroTresViewController.swift
//  lab2
//
//  Created by Jon Gordon on 1/31/22.
//

import UIKit

class NumeroTresViewController: UIViewController {

@IBAction func goToMaps(_ sender: UIButton) {
    if(UIApplication.shared.canOpenURL(URL(string: "comgooglemaps://")!)){
                //open the app with this URL scheme
                UIApplication.shared.open(URL(string: "comgooglemaps://")!, options: [:], completionHandler: nil)
            } else {
                if(UIApplication.shared.canOpenURL(URL(string: "maps://")!)){
                    //open the app with this URL scheme
                    UIApplication.shared.open(URL(string: "maps://")!, options: [:], completionHandler: nil)
                } else {
                    UIApplication.shared.open(URL(string: "maps://")!, options: [:], completionHandler: nil)
                }
            }
    }
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    

}
