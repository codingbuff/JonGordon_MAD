//
//  QuoteDataHandler.swift
//  lab5
//
//  Created by Jon Gordon on 2/28/22.
//
import UIKit
import Foundation
class QuoteDataHandler {
    
    var quoteData = QuoteData()
    func loadjson() async {
        guard let urlPath = URL(string: "https://type.fit/api/quotes")
            else {
                print("url error")
                return
            }

        do {
            let (data, response) = try await URLSession.shared.data(from: urlPath, delegate: nil)
            guard (response as? HTTPURLResponse)?.statusCode == 200
                else {
                    print("file download error")
                    return
                }
            //download successful
            print("download complete")
            parsejson(data)
        }
        catch {
            print(error.localizedDescription)
        }
    }

    func parsejson (_ data: Data) {
        
        //reference code: https://johncodeos.com/how-to-parse-json-in-ios-using-swift/
        if let json = try! JSONSerialization.jsonObject(with: data, options: .allowFragments) as? [Any] {
            
            for item in json {
                if let object = item as? [String: Any] {
                    // text
                    let text = object["text"] as? String ?? "N/A"
                    // author
                    let author = object["author"] as? String ?? "N/A"
                    let newQuote = Quote(text: text,author: author)
                    quoteData.quotes.append(newQuote)
                }
            }
        }
        else{
            print("json failed to populate")
            return
        }
    }
    func getQuotes() -> [Quote] {
        return quoteData.quotes
    }

}
