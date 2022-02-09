//
//  MovieTitleTableViewController.swift
//  lab3
//
//  Created by Jon Gordon on 2/6/22.
//

import UIKit

class MovieTitleTableViewController: UITableViewController {
    
    var movieData = MoviesDataLoader()
    var selectedGenre = 0
    var titleList = [String]()
    var searchController = UISearchController()
    override func viewDidLoad() {
        super.viewDidLoad()

        //search results
        let resultsController = SearchResultsTableViewController()
        resultsController.allwords = titleList //set the allwords property to our words array
        searchController = UISearchController(searchResultsController: resultsController) //initialize our search controller with the resultsController when it has search results to display

        //search bar configuration
        searchController.searchBar.placeholder = "Enter a search term"
        searchController.searchBar.sizeToFit()
        tableView.tableHeaderView=searchController.searchBar
        searchController.searchResultsUpdater = resultsController
        
        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
//        self.navigationItem.rightBarButtonItem = self.editButtonItem
        
    }

    override func viewWillAppear(_ animated: Bool) {
        titleList = movieData.getTitles(index: selectedGenre)
    }
    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return titleList.count
    }

    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "movieTitleCell", for: indexPath)
        var cellConfig = cell.defaultContentConfiguration()
        cellConfig.text = titleList[indexPath.row]
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
            titleList.remove(at: indexPath.row)
            // Delete the row from the data source
            tableView.deleteRows(at: [indexPath], with: .fade)
            movieData.deleteTitle(index: selectedGenre, titleIndex: indexPath.row)
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
    @IBAction func unwindSegue (_ segue:UIStoryboardSegue){
        if segue.identifier=="doneSegue"{
            if let source = segue.source as? AddTitleViewController {
                if source.addTitle.isEmpty == false{
                    movieData.addTitle(index:selectedGenre, newTitle:source.addTitle, newIndex: titleList.count)
                    titleList.append(source.addTitle)
                    tableView.reloadData()
                }
            }
        }
    }
}
