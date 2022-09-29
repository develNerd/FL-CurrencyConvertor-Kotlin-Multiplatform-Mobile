//
//  NumberFormatUtil.swift
//  iosApp
//
//  Created by Isaac Akakpo on 26/09/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

func formatAmount(stringAmount:Double) -> String{
    let currencyFormatter = NumberFormatter()
    currencyFormatter.usesGroupingSeparator = true
    currencyFormatter.numberStyle = .decimal
    // localize to your grouping and decimal separator
    currencyFormatter.locale = Locale.current

    // We'll force unwrap with the !, if you've got defined data you may need more error checking

    return currencyFormatter.string(from: NSNumber(value: stringAmount))!
}



func meo(newValue:String){
    var parseDouble = ""
    let filtered = newValue.filter { "0123456789".contains($0) }
                   if filtered != newValue {
                       parseDouble = filtered
                   }
}


extension String {
    var stripChars: String {
        return self.filter { "0123456789".contains($0) }
    }
}


func formatConversion(stringAmount:String) -> String{
    let currencyFormatter = NumberFormatter()
    currencyFormatter.maximumFractionDigits = 5
    currencyFormatter.minimumFractionDigits = 0
    currencyFormatter.numberStyle = .decimal
    // localize to your grouping and decimal separator
    currencyFormatter.locale = Locale.current

    // We'll force unwrap with the !, if you've got defined data you may need more error checking

    return currencyFormatter.string(from: NSNumber(value: Double(stringAmount) ?? 0.0))!
}

func parseEnteredAmount(in text: String) -> String {
    let pattern = "[0-9]"
    do {
        let regex = try NSRegularExpression(pattern: pattern)
        let results = regex.matches(in: text,
                                    range: NSRange(text.startIndex..., in: text))
        return results.map {
            String(text[Range($0.range, in: text)!])
        }.joined(separator: ", ")
    } catch let error {
        print("invalid regex: \(error.localizedDescription)")
        return "0"
    }
}


