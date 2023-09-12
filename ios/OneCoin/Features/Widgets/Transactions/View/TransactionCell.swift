//
//  TransactionCell.swift
//  OneCoin
//
//  Created by Эльдар Попов on 13.07.2023.
//

import SwiftUI
import OneCoinShared

struct TransactionCell: View {
//    let transaction: TransactionListModelData
//    
//    init(for transaction: TransactionListModelData) {
//        self.transaction = transaction
//    }
//    
    let model: SwiftTransactionListModelData
    
    var body: some View {
        HStack(spacing: 18) {
            transactionIcon
            
            VStack(alignment: .leading, spacing: 2) {
                Text(model.categoryTitle)
                    .fontBody1Style(color: CoinTheme.shared.colors.content)
                
                Text(model.account.name)
                    .fontSubtitle2Style(color: CoinTheme.shared.colors.secondary)
            }
            Spacer()
            Text(model.total)
                .fontBody1Style(color: CoinTheme.shared.colors.content)
        }
    }
    
    private var transactionIcon: some View {
        ZStack {
            Circle()
                .fill(CoinTheme.shared.colors.dividers)
                .frame(width: UI.SVGIcon.large, height: UI.SVGIcon.large)
            
            SVGImageView(url: model.icon.imageMRUrl)
                .tintColor(CoinTheme.shared.colors.content)
                .frameSvg(width: UI.SVGIcon.default, height: UI.SVGIcon.default)
        }
    }
}
