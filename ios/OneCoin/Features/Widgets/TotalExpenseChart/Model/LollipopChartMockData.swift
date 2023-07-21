//
//  LollipopChartMockData.swift
//  OneCoin
//
//  Created by Эльдар Попов on 25.05.2023.
//

import SwiftUI
import Foundation

struct LollipopChartData: Identifiable, Hashable {
    let id: String
    let day: Date?
    let expences: Int
    var color: Color
    
    init(
        id: String = UUID().uuidString, 
        day: Date?, 
        expences: Int, 
        color: Color = CoinTheme.shared.colors.dividers
    ) {
        self.id = id
        self.day = day
        self.expences = expences
        self.color = color
    }
}

enum LollipopChartMockData {
    /// expences by day for the last 30 days.
    static let last30DaysStruct = [
        LollipopChartData(day: Date().dateComponent(year: 2023, month: 5, day: 8), expences: 268),
        LollipopChartData(day: Date().dateComponent(year: 2023, month: 5, day: 9), expences: 117),
        LollipopChartData(day: Date().dateComponent(year: 2023, month: 5, day: 10), expences: 106),
        LollipopChartData(day: Date().dateComponent(year: 2023, month: 5, day: 11), expences: 119),
        LollipopChartData(day: Date().dateComponent(year: 2023, month: 5, day: 12), expences: 109),
        LollipopChartData(day: Date().dateComponent(year: 2023, month: 5, day: 13), expences: 104),
        LollipopChartData(day: Date().dateComponent(year: 2023, month: 5, day: 14), expences: 196),
        LollipopChartData(day: Date().dateComponent(year: 2023, month: 5, day: 15), expences: 172),
        LollipopChartData(day: Date().dateComponent(year: 2023, month: 5, day: 16), expences: 122),
        LollipopChartData(day: Date().dateComponent(year: 2023, month: 5, day: 17), expences: 115),
        LollipopChartData(day: Date().dateComponent(year: 2023, month: 5, day: 18), expences: 138),
        LollipopChartData(day: Date().dateComponent(year: 2023, month: 5, day: 19), expences: 110),
        LollipopChartData(day: Date().dateComponent(year: 2023, month: 5, day: 20), expences: 106),
        LollipopChartData(day: Date().dateComponent(year: 2023, month: 5, day: 21), expences: 187),
        LollipopChartData(day: Date().dateComponent(year: 2023, month: 5, day: 22), expences: 187),
        LollipopChartData(day: Date().dateComponent(year: 2023, month: 5, day: 23), expences: 119),
        LollipopChartData(day: Date().dateComponent(year: 2023, month: 5, day: 24), expences: 160),
        LollipopChartData(day: Date().dateComponent(year: 2023, month: 5, day: 25), expences: 144),
        LollipopChartData(day: Date().dateComponent(year: 2023, month: 5, day: 26), expences: 152),
        LollipopChartData(day: Date().dateComponent(year: 2023, month: 5, day: 27), expences: 148),
        LollipopChartData(day: Date().dateComponent(year: 2023, month: 5, day: 28), expences: 240),
        LollipopChartData(day: Date().dateComponent(year: 2023, month: 5, day: 29), expences: 242),
        LollipopChartData(day: Date().dateComponent(year: 2023, month: 5, day: 30), expences: 173),
        LollipopChartData(day: Date().dateComponent(year: 2023, month: 5, day: 31), expences: 143),
        LollipopChartData(day: Date().dateComponent(year: 2023, month: 6, day: 1), expences: 137),
        LollipopChartData(day: Date().dateComponent(year: 2023, month: 6, day: 2), expences: 123),
        LollipopChartData(day: Date().dateComponent(year: 2023, month: 6, day: 3), expences: 146),
        LollipopChartData(day: Date().dateComponent(year: 2023, month: 6, day: 4), expences: 214),
        LollipopChartData(day: Date().dateComponent(year: 2023, month: 6, day: 5), expences: 250),
        LollipopChartData(day: Date().dateComponent(year: 2023, month: 6, day: 6), expences: 56)
    ]
}
