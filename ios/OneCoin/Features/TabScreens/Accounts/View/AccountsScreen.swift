//
//  AccountsScreen.swift
//  OneCoin
//
//  Created by Виталий Перятин on 24.02.2023.
//

import SwiftUI

struct CardAccountsScreen: View {
    var body: some View {
        ForEach(accountCardModelsMock, id: \.id) { card in
            AccountCard(with: card)
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
            .frame(height: 145)
            .overlay(alignment: .top) {
                VStack(alignment: .leading) {
                    Spacer()
                        .frame(maxWidth: .infinity)
                    
                    Text(card.name)
                        .fontH5Style(color: .white)
                    
                    Text(String(card.expences))
                        .fontSubtitle2Style(color: .white)
                }
                .padding(16)
            }
    }
}

struct AccountsScreen_Previews: PreviewProvider {
    static var previews: some View {
        CardAccountsScreen()
    }
}
