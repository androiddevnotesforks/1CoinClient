//
//  TransactionsScreen.swift
//  OneCoin
//
//  Created by Виталий Перятин on 24.02.2023.
//

import SwiftUI

struct TransactionsScreen: View {
    let mockModels: [SwiftTransactionListModelData] = SwiftTransactionListModelData.mockModels + [SwiftTransactionListModelData(
            categoryTitle: "Public Transport", 
            account: AccountCardModel.getAccountCardModelMock(), 
            total: "-$20.52", 
            icon: .transport, 
            date: Date().dateComponent(year: 2023, month: 6, day: 8) ?? Date()
        )] + SwiftTransactionListModelData.mockModels
    
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            TransactionsAppBar()
            
            ScrollView(.vertical, showsIndicators: false) {
                VStack(alignment: .leading, spacing: UI.Spacing.large) {
                    ForEach(mockModels.chunchedByDates(), id: \.self) { chunk in
                        VStack(alignment: .leading, spacing: UI.Spacing.default) {
                            Text("\(chunk.first?.date ?? Date())")
                                .fontSubtitle2Style(color: CoinTheme.shared.colors.secondary)
                            
                            ForEach(chunk) { model in
                                TransactionCell(model: model)
                            }
                        }
                    }
                }
                .padding(UI.Padding.Horizontal.default)
                .padding(.bottom, UI.Padding.Scroll.bottom)
            }
        }
    }
}

struct TransactionsScreen_Previews: PreviewProvider {
    static var previews: some View {
        TransactionsScreen()
    }
}
