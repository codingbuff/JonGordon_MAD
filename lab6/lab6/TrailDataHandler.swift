//
//  TrailDataHandler.swift
//  lab6
//
//  Created by Jon Gordon on 3/28/22.
//

import Foundation
class TrailDataHandler{

    var trailData = [String]()

    func dataFileURL(_ filename:String)-> URL?{
        let urls = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask)
        let url = urls.first?.appendingPathComponent(filename)
        return url
    }

    func loadData(filename: String){
        let fileURL = dataFileURL(filename)
        if FileManager.default.fileExists(atPath: (fileURL?.path)!){
            do{
                let data = try Data(contentsOf: fileURL!)
                let decoder = PropertyListDecoder()
                trailData = try decoder.decode([String].self, from:data)
            } catch {
                print("no file")
            }
        }
        else {
            print("file does not exist")
        }
    }
    func saveData(fileName: String){
         //url of data file
         let fileURL = dataFileURL(fileName)
         do {
             //create an instance of PropertyListEncoder
             let encoder = PropertyListEncoder()
             //set format type to xml
             encoder.outputFormat = .xml
             let encodedData = try encoder.encode(trailData)
             //write encoded data to the file
             try encodedData.write(to: fileURL!)
             } catch {
             print("write error")
             }
     }

    func getTrails() -> [String]{
        return trailData
    }

    func addItem(newItem: String){
        trailData.append(newItem)
    }

    func deleteItem(index: Int){
        trailData.remove(at: index)
    }
}
