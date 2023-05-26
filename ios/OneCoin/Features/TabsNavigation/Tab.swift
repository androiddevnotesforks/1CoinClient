//
//  Tab.swift
//  OneCoin
//
//  Created by Эльдар Попов on 10.05.2023.
//

import OneCoinShared

enum OneCoinTabs {
    case home
    case transactions
    case add
    case plans
    case analytics
    
    var tabName: String {
        switch self {
        case .home:
            return MR.strings().tab_home.desc().localized()
        case .transactions:
            return MR.strings().tab_transactions.desc().localized()
        case .add:
            return ""
        case .plans:
            return MR.strings().tab_accounts.desc().localized()
        case .analytics:
            return MR.strings().tab_analytics.desc().localized()
        }
    }
    
    var tabURL: URL {
        switch self {
        case .home:
            return MR.files().ic_home_inactive.url
        case .transactions:
            return MR.files().ic_transactions_inactive.url
        case .add:
            return MR.files().ic_plus.url
        case .plans:
            return MR.files().ic_wallet_inactive.url
        case .analytics:
            return MR.files().ic_analytics_inactive.url
        }
    }
}
