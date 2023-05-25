//
//  LollipopChart.swift
//  OneCoin
//
//  Created by Эльдар Попов on 25.05.2023.
//

import SwiftUI
import Charts

struct LollipopChart: View {
    var body: some View {
        Chart(LollipopChartMockData.last30Days, id: \.day) {
            BarMark(
                x: .value($0.day.description, $0.day, unit: .day),
                y: .value($0.expences.description, $0.expences)
            )
        }
    }
}

// TODO: export into extensions
func date(year: Int, month: Int, day: Int = 1) -> Date {
    Calendar.current.date(from: DateComponents(year: year, month: month, day: day)) ?? Date()
}
