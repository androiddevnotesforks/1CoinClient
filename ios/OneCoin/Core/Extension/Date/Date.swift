//
//  Date.swift
//  OneCoin
//
//  Created by Эльдар Попов on 25.05.2023.
//

import Foundation

public extension Date {
    // MARK: - Создание 08.05.2023
    // Date().dateComponent(year: 2023, month: 5, day: 8)
    func dateComponent(year: Int, month: Int = 1, day: Int = 1) -> Date? {
        Calendar.current.date(from: DateComponents(year: year, month: month, day: day))
    }
}
