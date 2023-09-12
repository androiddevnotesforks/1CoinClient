//
//  AppScreen.swift
//  OneCoin
//
//  Created by Виталий Перятин on 04.03.2023.
//

import SwiftUI

struct AppScreen: View {
    @StateObject var router = Router()
    
    init() {
        UINavigationBar.setAnimationsEnabled(false)
    }
    
    var body: some View {
        NavigationStack(path: $router.navPath) {
                VStack {
                    HomeScreen()
                    CoinTabsView(selectedTab: OneCoinTabs.home)
                }
                .navigationDestination(for: Router.Destination.self) { selectedTab in
                    VStack {
                        switch selectedTab {
                        case .home:
                            HomeScreen()
                                .navigationBarBackButtonHidden()
                        case .transactions:
                            TransactionsScreen()
                                .navigationBarBackButtonHidden()
                        case .add:
                            AddTransactionScreen()
                                .navigationBarBackButtonHidden()
                        case .plans:
                            PlansScreen()
                                .navigationBarBackButtonHidden()
                        case .analytics:
                            AnalyticsScreen()
                                .navigationBarBackButtonHidden()
                        }
                        
                        CoinTabsView(selectedTab: selectedTab)
                    }
                }
            }
            .environmentObject(router)
    }
}

struct AppScreen_Previews: PreviewProvider {
    static var previews: some View {
        AppScreen()
    }
}
