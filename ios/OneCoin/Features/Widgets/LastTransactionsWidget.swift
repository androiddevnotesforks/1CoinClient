//
//  LastTransactionsWidget.swift
//  OneCoin
//
//  Created by Виталий Перятин on 22.04.2023.
//

import SwiftUI
import OneCoinShared

struct LastTransactionsWidget: View {
    
    var lastTransactions: [TransactionListModelData]
    var onClick: () -> ()
    var onTransactionClick: (_ transaction: TransactionModel) -> ()
    
    var body: some View {
        
        CoinWidget(
            title: MR.strings().home_last_transactions.desc().localized(),
            withBorder: true,
            onClick: onClick
        ) {
            LastTransactionsWidgetContent(
                lastTransactions: lastTransactions,
                onTransactionClick: onTransactionClick
            )
        }
    }
}

struct LastTransactionsWidgetContent: View {
    
    var lastTransactions: [TransactionListModelData]
    var onTransactionClick: (_ transaction: TransactionModel) -> ()
    
    var body: some View {
        if (lastTransactions.isEmpty) {
            Text("Empty last transactions")
            //LastTransactionsEmptyStub()
        } else {
            Text("Last transactions: \(lastTransactions.count)")
//            LastTransactionsColumn(
//                transactions = lastTransactions,
//                onTransactionClick = onTransactionClick
//            )
        }
    }
}

struct LastTransactionsWidget_Previews: PreviewProvider {
    static var previews: some View {
        LastTransactionsWidget(
            lastTransactions: [],
            onClick: {
                
            },
            onTransactionClick: { (transaction) in
                print("123")
            }
        )
    }
}
