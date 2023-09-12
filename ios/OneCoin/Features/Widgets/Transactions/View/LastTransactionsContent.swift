//
//  LastTransactionsContent.swift
//  OneCoin
//
//  Created by Эльдар Попов on 13.07.2023.
//

import SwiftUI
import OneCoinShared

struct LastTransactionsContent: View {
    let lastTransactions: [TransactionListModelData]
    let onTransactionClick: (_ transaction: TransactionModel) -> ()
    
    let mockModels: [SwiftTransactionListModelData]
    
    var body: some View {
//        if (lastTransactions.isEmpty) {
//            LastTransactionsEmptyStub
//        } else {
//            Text("Last transactions: \(lastTransactions.count)")
        VStack(spacing: UI.Spacing.default) {
            ForEach(mockModels.prefix(3), id: \.id) {
                TransactionCell(model: $0)
            }
        }
        .padding(UI.Padding.Horizontal.default)
//        }
    }
    
    private var LastTransactionsEmptyStub: some View {
        HStack(spacing: 8) {
            SVGImageView(url: MR.files().ic_error.url)
                .tintColor(CoinTheme.shared.colors.secondary)
                .frameSvg(width: UI.SVGIcon.small, height: UI.SVGIcon.small)
            
            Text("No transactions yet")
                .fontSubtitle2Style(color: CoinTheme.shared.colors.secondary)
        }
    }
}
