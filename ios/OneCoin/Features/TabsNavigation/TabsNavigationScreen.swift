//
//  TabsNavigationScreen.swift
//  OneCoin
//
//  Created by Виталий Перятин on 24.02.2023.
//

import SwiftUI

struct TabsNavigationScreen: View {
    @State private var selectedTab = OneCoinTabs.home
    @Environment(\.safeAreaInsets) private var safeAreaInsets: (top: CGFloat, bottom: CGFloat)
    
    var body: some View {
        VStack {
            ZStack {
                CoinTheme.shared.colors.background
                
                switch selectedTab {
                case .home:
                    HomeScreen()
                case .transactions:
                    TransactionsScreen()
                case .add:
                    AddTransactionScreen()
                case .plans:
                    PlansScreen()
                case .analytics:
                    AnalyticsScreen()
                }
            }
            CoinTabsView(selectedTab: $selectedTab)
                .safeAreaInset(edge: .bottom) {
                    Spacer().frame(height: safeAreaInsets.bottom)
                }
        }
    }
}

struct TabsNavigationScreen_Previews: PreviewProvider {
    static var previews: some View {
        TabsNavigationScreen()
            .environment(\.safeAreaInsets, (0, 0))
    }
}
