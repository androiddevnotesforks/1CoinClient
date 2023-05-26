//
//  TotalExpenseChartWidgetContent.swift
//  OneCoin
//
//  Created by Эльдар Попов on 25.05.2023.
//

import SwiftUI

struct TotalExpenseChartWidgetContent: View {
    @State private var selectedTimeline = TimelineType.Month
    
    var body: some View {
        VStack(alignment: .center, spacing: 0) {
            Text("Total expense")
                .fontSubtitle2MediumStyle(color: CoinTheme.shared.colors.secondary)
                .padding(.top, 15)
            
            Text("$12776")
                .fontH2Style(color: CoinTheme.shared.colors.primary)
            
            Tabs(tabs: TimelineType.allCases, selected: $selectedTimeline)
            
            InteractiveLollipop()
                .padding(EdgeInsets(top: 0, leading: 16, bottom: 4, trailing: 8))
        }
        .frame(maxWidth: .infinity)
        .frame(height: UI.Card.largeHeight)
    }
}
