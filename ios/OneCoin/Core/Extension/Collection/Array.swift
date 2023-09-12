//
//  Array.swift
//  OneCoin
//
//  Created by Эльдар Попов on 20.07.2023.
//

extension Array where Element == SwiftTransactionListModelData {
    func chunchedByDates() -> [[Element]] {
        let empty: [[Element]] = []
        return reduce(into: empty) { resultingArray, inputTransactionModel in
            let index: Int? = resultingArray.firstIndex(where: { resultDate in resultDate.first?.date.compare(inputTransactionModel.date) == .orderedSame })
            
            let existing = index.map { i in resultingArray[i] + [inputTransactionModel] } ?? [inputTransactionModel]
            
            if let index {
                resultingArray[index] = existing
            } else {
                resultingArray.append(existing)
            }
        }
    }
}
