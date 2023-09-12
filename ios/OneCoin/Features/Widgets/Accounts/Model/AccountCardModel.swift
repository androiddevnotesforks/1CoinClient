//
//  AccountCardModel.swift
//  OneCoin
//
//  Created by Эльдар Попов on 10.05.2023.
//

import Foundation
import SwiftUI

struct AccountCardModel: Identifiable {
    let id = UUID().uuidString
    let name: String
    let expences: Int
    let color: Color
}

extension AccountCardModel {
    static func getAccountCardModelsMock() -> [AccountCardModel] { 
        [
            AccountCardModel(
                name: "Debit card (*5841)", 
                expences: 35000, 
                color: Color(red: 75 / 255, green: 63 / 255, blue: 114 / 255)
            ),
            AccountCardModel(
                name: "Debit card (*5841)", 
                expences: 35000, 
                color: Color(red: 7 / 255, green: 190 / 255, blue: 184 / 255)
            )
        ]
    }
    
    static func getAccountCardModelMock() -> AccountCardModel { 
        AccountCardModel(
            name: "Debit card (*5841)", 
            expences: 35000, 
            color: Color(red: 7 / 255, green: 190 / 255, blue: 184 / 255)
        )
    }
}
