//
//  MoviesDataLoader.swift
//  lab3
//
//  Created by Jon Gordon on 2/6/22.
//

import Foundation

class MoviesDataLoader{
    var allData = [MoviesData]()
    
    func loadData(filename: String){
        if let pathURL = Bundle.main.url(forResource: filename, withExtension: "plist"){
            //creates a property list decoder object
            let plistdecoder = PropertyListDecoder()
            do {
                let data = try Data(contentsOf: pathURL)
                //decodes the property list
                allData = try plistdecoder.decode([MoviesData].self, from: data)
            } catch {
                // handle error
                print(error)
            }
        }
    }
    
    func getMovieGenres() -> [String]{
        var genres = [String]()
        for item in allData{
            genres.append(item.genre)
        }
        return genres
    }
    
    func getTitles(index:Int) -> [String] {
        return allData[index].titles
    }
    
    func addTitle(index:Int, newTitle:String, newIndex: Int){
        allData[index].titles.insert(newTitle, at: newIndex)
    }

    func deleteTitle(index:Int, titleIndex: Int){
        allData[index].titles.remove(at: titleIndex)
    }
}
