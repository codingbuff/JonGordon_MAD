//
//  SearchResultsTableViewController.swift
//  lab3
//
//  Created by Jon Gordon on 2/7/22.
//

import UIKit

class SearchResultsTableViewController: UITableViewController, UISearchResultsUpdating {
    var allwords = [String]()
    var filteredWords = [String]()

    func updateSearchResults(for searchController: UISearchController) {
        let searchString = searchController.searchBar.text //search string
                filteredWords.removeAll(keepingCapacity: true) //removes all elements
                if searchString?.isEmpty == false {
                    //closure that returns true if the search string is present in a String
                    let searchfilter: (String) -> Bool = { name in
                        //look for the search string as a substring of the word
                        let range = name.range(of: searchString!, options: .caseInsensitive)
                        return range != nil //returns true if the value matches and false if there’s no match
                    } //end closure
                    //filter returns an array with the words that match the search string
                    let matches = allwords.filter(searchfilter)
                    filteredWords.append(contentsOf: matches)
                }
                tableView.reloadData() //reload table data with search results
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        tableView.register(UITableViewCell.self, forCellReuseIdentifier: "movieTitleCell")
        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        // self.navigationItem.rightBarButtonItem = self.editButtonItem
    }

    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return filteredWords.count
    }

    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "movieTitleCell", for: indexPath)
        var cellConfig = cell.defaultContentConfiguration()
        cellConfig.text = filteredWords[indexPath.row]
        cell.contentConfiguration = cellConfig
        return cell
    }
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
      let alert = UIAlertController(title: "Row selected", message: "You selected \(filteredWords[indexPath.row])", preferredStyle: .alert)
      let okAction = UIAlertAction(title: "OK", style: .default, handler: nil)
      alert.addAction(okAction)
      present(alert, animated: true, completion: nil)
      tableView.deselectRow(at: indexPath, animated: true)
    }

    /*
    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    */

    /*
    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            // Delete the row from the data source
            tableView.deleteRows(at: [indexPath], with: .fade)
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    */

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
