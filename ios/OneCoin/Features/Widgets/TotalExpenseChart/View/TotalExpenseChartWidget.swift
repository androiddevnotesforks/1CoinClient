//
//  TotalExpenseChartWidget.swift
//  OneCoin
//
//  Created by Эльдар Попов on 24.05.2023.
//

import SwiftUI
import OneCoinShared

struct TotalExpenseChartWidget: View {
    @EnvironmentObject var router: Router
    
    var body: some View {
        // Expense trend
        CoinWidget(
            title: MR.strings().expense_analytics_trend.desc().localized(),
            withBorder: true,
            onClick: { router.navigate(to: OneCoinTabs.analytics) }
        ) {
            TotalExpenseChartWidgetContent()
        }
    }
}
