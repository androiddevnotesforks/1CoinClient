//
//  TotalExpenseChartWidget.swift
//  OneCoin
//
//  Created by Эльдар Попов on 24.05.2023.
//

import SwiftUI
import OneCoinShared

struct TotalExpenseChartWidget: View {
    var body: some View {
        CoinWidget(
            title: MR.strings().expense_analytics_trend.desc().localized(),
            withBorder: true,
            onClick: nil
        ) {
            TotalExpenseChartWidgetContent()
        }
    }
}
