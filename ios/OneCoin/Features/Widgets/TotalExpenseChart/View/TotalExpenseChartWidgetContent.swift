//
//  TotalExpenseChartWidgetContent.swift
//  OneCoin
//
//  Created by Эльдар Попов on 25.05.2023.
//

import SwiftUI

struct TotalExpenseChartWidgetContent: View {
    var body: some View {
        VStack(alignment: .center, spacing: 7) {
            Text("Total expense")
                .fontSubtitle2MediumStyle(color: Color.secondary)
                .padding(.top, 15)
            
            Text("$12776")
                .fontH2Style(color: Color.primary)
            
            HStack {
                ForEach([TimelineTypes.Week, TimelineTypes.Month, TimelineTypes.Year]) { timeline in 
                    Text(timeline.rawValue)
                        .fontSubtitle2MediumStyle(color: Color.secondary)
                }
            }
            
            InteractiveLollipop(data: LollipopChartMockData.last30DaysStruct)
        }
        .frame(maxWidth: .infinity)
        .frame(height: UI.Card.largeHeight)
    }
}
