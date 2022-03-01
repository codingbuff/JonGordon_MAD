//
//  Quote.swift
//  lab5
//
//  Created by Jon Gordon on 2/28/22.
//

import Foundation

struct Quote: Decodable  {
    let text: String
    let author: String
}

struct QuoteData: Decodable {
    var quotes = [Quote]()
}
