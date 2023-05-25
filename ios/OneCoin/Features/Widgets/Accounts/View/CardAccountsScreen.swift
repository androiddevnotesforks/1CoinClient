//
//  CardAccountsScreen.swift
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
                    AccountCardView(color: card.color, sizeOfContainer: CGSize(width: UI.Card.width, height: UI.Card.height)) {
                            accountCardView(with: card)
                                .padding(UI.Padding.Horizontal.default)
                    }
                }
                
                addNewAccountCardView
            }
        }
    }
    
    private func accountCardView(with card: AccountCardModel) -> some View {
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
    }
    
    private var addNewAccountCardView: some View {
        Button(action: { }) {
            AccountCardView(color: .white, sizeOfContainer: CGSize(width: UI.Card.width / 2, height: UI.Card.height - 1)) {
                    VStack {
                        Spacer()
                        Image(systemName: "plus")
                            .frame(width: 10, height: 10)
                        Spacer()
                    }
                    
            }
            .shadow(radius: 1)
        }
    }
}

struct AccountsScreen_Previews: PreviewProvider {
    static var previews: some View {
        CardAccountsScreen()
    }
}
