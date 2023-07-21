//
//  ContentView.swift
//  OneCoin
//
//  Created by Виталий Перятин on 07.02.2023.
//

import SwiftUI
import OneCoinShared

struct HomeScreen: View {
    @EnvironmentObject var router: Router
    
    var body: some View {
        VStack {
            HomeTopBar(
                //totalBalance: OneCoinShared.Amount(currency: OneCoinShared.Currency(code: "", symbol: ""), amountValue: 1),
                onSettingsClick: {}
            )
            ScrollView(.vertical, showsIndicators: false) {
                VStack(spacing: 16) {
                    CoinWidget(
                        // My accounts
                        title: MR.strings().widget_settings_item_my_accounts.desc().localized(),
                        withBorder: false,
                        onClick: { print("ahah") }
                    ) {
                        CardAccountsScreen()
                    }
                    
                    TotalExpenseChartWidget()
                    
                    LastTransactionsWidget(
                        lastTransactions: [], 
                        widgetClick: { router.navigate(to: OneCoinTabs.transactions) }, 
                        onTransactionClick: { _ in }
                    )
                }
                .padding(.bottom, UI.Padding.Scroll.bottom)
            }
        }
    }
}

struct HomeScreen_Previews: PreviewProvider {
    static var previews: some View {
        HomeScreen()
    }
}
