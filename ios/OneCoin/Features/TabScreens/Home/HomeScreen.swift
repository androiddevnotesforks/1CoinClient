//
//  ContentView.swift
//  OneCoin
//
//  Created by Виталий Перятин on 07.02.2023.
//

import SwiftUI
import OneCoinShared

struct HomeScreen: View {

    var body: some View {
        VStack {
            HomeTopBar(
                //totalBalance: OneCoinShared.Amount(currency: OneCoinShared.Currency(code: "", symbol: ""), amountValue: 1),
                onSettingsClick: {}
            )
            ScrollView(.vertical, showsIndicators: false) {
                VStack {
                    CoinWidget(
                        title: "My Accounts",
                        withBorder: false
                    ) {
                        CardAccountsScreen()
                    }
                    
                    CoinWidget(
                        title: "Last Transactions",
                        withBorder: true
                    ) {
                        Text("Transactions' list")
                    }
                }
            }
        }
    }
}

struct HomeScreen_Previews: PreviewProvider {
    static var previews: some View {
        HomeScreen()
            .environmentObject(CoinTheme.light)
    }
}
