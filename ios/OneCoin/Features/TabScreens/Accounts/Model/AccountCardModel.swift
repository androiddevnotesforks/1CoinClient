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

let accountCardModelsMock: [AccountCardModel] = [
    .init(name: "Дебетовая карта", expences: 1000, color: .green),
    .init(name: "Кредитная карта", expences: 10000, color: .red)
]
