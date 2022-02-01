//
//  DataLoader.swift
//  lab2
//
//  Created by Jon Gordon on 1/31/22.
//

import Foundation

class DataLoader {
    
    var allData = [LanguageNumbers]()

    
    func loadData(filename: String){
        if let pathURL = Bundle.main.url(forResource: filename, withExtension:  "plist") {
            //initialize a property list decoder object
            let plistdecoder = PropertyListDecoder()
            do {
                let data = try Data(contentsOf: pathURL)
                //decodes the property list
                allData = try plistdecoder.decode([LanguageNumbers].self, from: data)
            } catch {
                //handle error
                print("Cannot load data")
            }
        }
    }
    
    func getLanguages() -> [String]{
        var languages = [String]()
        for item in allData {
            languages.append(item.language)
        }
        return languages
    }
    
    func getNumbers(index: Int) -> [String] {
        return allData[index].numbers
    }
}
