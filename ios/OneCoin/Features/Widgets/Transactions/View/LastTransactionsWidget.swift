//
//  LastTransactionsWidget.swift
//  OneCoin
//
//  Created by Виталий Перятин on 22.04.2023.
//

import SwiftUI
import OneCoinShared

struct LastTransactionsWidget: View {
    let lastTransactions: [TransactionListModelData]
    let widgetClick: (() -> ())?
    let onTransactionClick: (_ transaction: TransactionModel) -> ()
    
    let mockModels: [SwiftTransactionListModelData] = SwiftTransactionListModelData.mockModels
    
    var body: some View {
        CoinWidget(
            // Last Transactions
            title: MR.strings().home_last_transactions.desc().localized() + ": \(mockModels.count)",
            withBorder: true,
            onClick: widgetClick
        ) {
            LastTransactionsContent(
                lastTransactions: lastTransactions,
                onTransactionClick: onTransactionClick,
                mockModels: mockModels
            )
            .frame(minHeight: UI.Card.Rectangle.height)
        }
    }
}

struct LastTransactionsWidget_Previews: PreviewProvider {
    static var previews: some View {
        LastTransactionsWidget(
            lastTransactions: [],
            widgetClick: {
                
            },
            onTransactionClick: { (transaction) in
                print("123")
            }
        )
    }
}
