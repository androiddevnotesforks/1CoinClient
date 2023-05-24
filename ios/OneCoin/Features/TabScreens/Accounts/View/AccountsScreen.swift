//
//  AccountsScreen.swift
//  OneCoin
//
//  Created by Виталий Перятин on 24.02.2023.
//

import SwiftUI

struct CardAccountsScreen: View {
    var body: some View {
        ScrollView(.horizontal, showsIndicators: false) {
            LazyHStack(spacing: UI.Padding.Horizontal.small) {
                ForEach(AccountCardModel.getModelsMock(), id: \.id) { card in
                    AccountCard(with: card)
                }
            }
        }
    }
}

struct AccountCard: View {
    private let card: AccountCardModel
    
    init(with card: AccountCardModel) {
        self.card = card
    }
    
    var body: some View {
        RoundedRectangle(cornerRadius: 12)
            .fill(card.color)
            .frame(height: 128)
            .frame(minWidth: 160)
            .overlay(alignment: .top) {
                VStack(alignment: .leading) {
                    Image("DebitCard")
                    
                    Spacer()
                        .frame(maxWidth: .infinity)
                    
                    Text("$" + String(card.expences))
                        .fixedSize(horizontal: false, vertical: true)
                        .fontH5Style(color: CoinTheme.shared.colors.white)
                    
                    Text(card.name)
                        .opacity(0.5)
                        .fontSubtitle2Style(color: CoinTheme.shared.colors.secondaryBackground)
                }
                .padding(UI.Padding.Horizontal.default)
            }
    }
}

struct AccountsScreen_Previews: PreviewProvider {
    static var previews: some View {
        CardAccountsScreen()
    }
}
