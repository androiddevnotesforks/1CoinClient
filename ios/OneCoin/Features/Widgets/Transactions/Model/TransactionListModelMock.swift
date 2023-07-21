//
//  TransactionListModelMock.swift
//  OneCoin
//
//  Created by Эльдар Попов on 13.07.2023.
//

import Foundation
import OneCoinShared

//extension TransactionListModelData {
//    static var getModelMock: TransactionListModelData = {
//        let currency = Currency(code: "CurrencyCode", symbol: "CurrencySymbol")
//        let transaction = TransactionModel(
//            id: nil, 
//            type: .init(), 
//            primaryAccount: .init(), 
//            secondaryAccount: nil, 
//            primaryAmount: .init(currency: currency, amountValue: 100), 
//            secondaryAmount: nil, 
//            _category: nil, 
//            dateTime: Kotlinx_datetimeLocalDateTime(date: .init(year: 2023, month: .january, dayOfMonth: 30), time: .init(hour: 16, minute: 60, second: 300, nanosecond: 0)), 
//            insertionDateTime: nil
//        )
//        return TransactionListModelData(transaction: transaction, isSelected: .constant(true))
//    }()
//}

struct SwiftTransactionListModelData: Identifiable {
    var id: String { UUID().uuidString }
    let categoryTitle: String
    let account: AccountCardModel
    let total: String
    let icon: TransactionType
    let date: Date
}

extension SwiftTransactionListModelData: Hashable {
    public func hash(into hasher: inout Hasher) {
        return hasher.combine(id)
    }
    
    public static func == (lhs: SwiftTransactionListModelData, rhs: SwiftTransactionListModelData) -> Bool {
        return lhs.id == rhs.id
    }
}

extension SwiftTransactionListModelData {
    static let mockModels = [SwiftTransactionListModelData](repeating: SwiftTransactionListModelData.getTransactionModelMock(), count: 5)
    
    static func getTransactionModelMock() -> SwiftTransactionListModelData {
        SwiftTransactionListModelData(
            categoryTitle: "Public Transport", 
            account: AccountCardModel.getAccountCardModelMock(), 
            total: "-$25.52", 
            icon: .transport, 
            date: Date().dateComponent(year: 2023, month: 5, day: 8) ?? Date()
        )
    }
}
