//
//  TrailsTableViewController.swift
//  lab6
//
//  Created by Jon Gordon on 3/28/22.
//

import UIKit

class TrailsTableViewController: UITableViewController {
    var trails = [String]()
    var trailData = TrailDataHandler()
    let dataFile = "trails.plist"
    override func viewDidLoad() {
        super.viewDidLoad()
        trailData.loadData(filename: dataFile)
        trails = trailData.getTrails()
        NotificationCenter.default.addObserver(self, selector: #selector(self.applicationWillResignActive(_:)), name:
        UIApplication.willResignActiveNotification, object: nil)
        
        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        self.navigationItem.leftBarButtonItem = self.editButtonItem
    }
    
    @objc func applicationWillResignActive(_ notification: Notification){
     trailData.saveData(fileName: dataFile)
     }
    
    @IBAction func addTrail(_ sender: UIBarButtonItem) {
        let addalert = UIAlertController(title: "New Trail", message:"Add a new favorite trail to your list",preferredStyle: .alert)
        addalert.addTextField(configurationHandler: {(UITextField) in
        })
        let cancelAction = UIAlertAction(title:"Cancel", style: .cancel,handler: nil)
        addalert.addAction(cancelAction)
        let addItemAction = UIAlertAction(title:"Add",style:.default,handler: {(UIAlertAction) in
            let newitem = addalert.textFields![0]
            if newitem.text?.isEmpty == false {
                let newTrailItem = newitem.text!
                self.trails.append(newTrailItem)
                self.trailData.addItem(newItem: newTrailItem)
                self.tableView.reloadData()
            }
        })
        addalert.addAction(addItemAction)
        present(addalert,animated: true, completion: nil)
    }
    
    
    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return trails.count
    }

    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath)
        var cellConfig = cell.defaultContentConfiguration()
        cellConfig.text = trails[indexPath.row]
        cell.contentConfiguration = cellConfig
        return cell
    }
    

    
    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    

    
    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            trails.remove(at: indexPath.row)
            trailData.deleteItem(index: indexPath.row)
            tableView.deleteRows(at: [indexPath], with: .fade)
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    

    /*
    // Override to support rearranging the table view.
    override func tableView(_ tableView: UITableView, moveRowAt fromIndexPath: IndexPath, to: IndexPath) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(_ tableView: UITableView, canMoveRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    */

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
