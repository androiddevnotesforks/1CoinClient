//
//  Date.swift
//  OneCoin
//
//  Created by Эльдар Попов on 25.05.2023.
//

import Foundation

public extension Date {
    // TODO: export into extensions
    func dateComponent(year: Int, month: Int, day: Int = 1) -> Date {
        Calendar.current.date(from: DateComponents(year: year, month: month, day: day)) ?? Date()
    }
}
