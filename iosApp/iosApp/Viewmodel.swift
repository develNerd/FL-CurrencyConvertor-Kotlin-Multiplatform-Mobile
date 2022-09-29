//
//  Viewmodel.swift
//  iosApp
//
//  Created by Isaac Akakpo on 23/09/2022.
//  Copyright © 2022 orgName. All rights reserved.
//


import shared
import AsyncAlgorithms
import KMPNativeCoroutinesAsync
import Foundation

@MainActor
class ViewModel: ObservableObject {
    
    enum State {
            case idle
            case loading
            case failed(Error)
            case loaded([Currency])
        }
    

    
 
    
    let repository:GetRatesRepository
    
    @Published var currencyRate: CurrencyRates? = nil

    @Published var currencies = [Currency]()
    @Published var bottomFilterCurrencies = [Currency]()

    @Published var baseFactor:Double = 1.00
    @Published var baseCurrency:Currency = Currency(code: "USD", usdRate: 1.00, name: "United States Dollar", conversion: 1.00)

    
    init() {
        self.repository = RepositoryHelper().getRepo()
    }
   
    
    func searchFilter(searchText: String) {
        if (!searchText.isEmpty) {
                self.bottomFilterCurrencies = self.bottomFilterCurrencies.filter({cur in
                    cur.name.lowercased().contains(searchText.lowercased()) || cur.code.lowercased()
                        .contains(searchText.lowercased())
                })
            } else {
                self.bottomFilterCurrencies = currencies
            }
        }
    
    
   func loadInit() async {
       repository.loadData()
            do {
                let currencyRates = asyncStream(for: repository.currencyRatesNative())
                for try await currencyRate in currencyRates {
                    if(currencyRate.result != nil){
                        self.currencyRate = currencyRate.result
                        parseResults(rates: currencyRate.result?.rates ?? [Currency]())
                        self.baseCurrency =  (currencyRate.result?.rates.filter({ cur in
                            cur.code.contains(self.baseCurrency.code)
                        }).first) ?? self.baseCurrency
                    
                        print("Got random letters: \(String(describing: currencyRate.result?.rates))")
                        print("Base : \(self.baseCurrency)")
                    }
                }
            } catch {
                print("Failed with error: \(error)")
            }
        }
    
    func parseResults(rates:[Currency]){
       var rrates =  rates.map({cur in
            cur.conversion = baseFactor * cur.usdRate
            return cur
        })
        self.currencies = rrates
        self.bottomFilterCurrencies = rrates
    }
    
    func setBaseCurrency(currency: Currency) {
        self.baseCurrency = currency
        self.baseFactor = 1.00 / currency.usdRate
        }
    
    
    func calculateConversion(amount: Double = 0.0) {
        let cur =
        self.currencies.map({cur in
            if(cur.conversion == 0.0){
                cur.conversion = amount * baseFactor * cur.usdRate
            }else {
                cur.conversion = cur.usdRate * amount * baseFactor
            }
            return cur
        })
        
        self.currencies = cur
        print("\(cur.first) ")
      }

    
}
