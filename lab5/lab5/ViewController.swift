//
//  ViewController.swift
//  lab5
//
//  Created by Jon Gordon on 2/28/22.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var quoteTextView: UITextView!
    
    @IBOutlet weak var authorTextView: UITextView!
    var quoteDataHandler = QuoteDataHandler()
    @IBOutlet weak var generateNewQuote: UIButton!
    var quotes = [Quote]()
    
    
    @IBAction func generateNewQuote(_ sender: Any) {
        getAPIdata()
    }
    override func viewDidLoad() {
        super.viewDidLoad()
        getAPIdata()

    }
    
    func getRandomQuote() -> Quote {
        let k = quotes.count - 1
        var randomIndex = Int.random(in: 0...k)
        return quotes[randomIndex]
    }
    
    func updateQuote(text:String, author:String){
        
        print(text)
        print(author)
        authorTextView.text = author
        quoteTextView.text = text
    }
    
    func getAPIdata() {
        Task {
            await quoteDataHandler.loadjson()
            quotes = quoteDataHandler.getQuotes()
            var newQuote = getRandomQuote()
            updateQuote(text: newQuote.text,author: newQuote.author)
        }

    }
    
}

