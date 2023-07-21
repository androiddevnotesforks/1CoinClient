//
//  InteractionWithLollipopChart.swift
//  OneCoin
//
//  Created by Эльдар Попов on 25.05.2023.
//

import SwiftUI
import Charts

struct InteractiveLollipop: View {
    let data: [LollipopChartData] = LollipopChartMockData.last30DaysStruct
    @State private var selectedElement: LollipopChartData? = nil
    @Environment(\.layoutDirection) var layoutDirection

    var body: some View {
        LollipopChart(data: data, selectedElement: $selectedElement)
            // Overlay Rectangle with additional data of chart 
            .chartOverlay { proxy in
                ZStack(alignment: .topLeading) {
                    GeometryReader { nthGeoItem in
                        if let selectedElement = selectedElement {
                            let dateInterval = Calendar.current.dateInterval(of: .day, for: selectedElement.day ?? Date()) ?? .init()
                            let startPositionX1 = proxy.position(forX: dateInterval.start) ?? 0
                            let startPositionX2 = proxy.position(forX: dateInterval.end) ?? 0
                            let midStartPositionX = (startPositionX1 + startPositionX2) / 2 + nthGeoItem[proxy.plotAreaFrame].origin.x
                            
                            let lineHeight = nthGeoItem[proxy.plotAreaFrame].maxY
                            // xAxis Point of tapped gesture
                            let lineX = layoutDirection == .rightToLeft ? nthGeoItem.size.width - midStartPositionX : midStartPositionX
                            // xAxis Point of beginning box
                            let boxOffset = max(0, min(nthGeoItem.size.width - UI.Card.Rectangle.width, lineX - UI.Card.Rectangle.width / 2))
                            
                            YAxisDashedLine()
                                .stroke(style: StrokeStyle(lineWidth: 1, dash: [5]))
                                .fill(CoinTheme.shared.colors.primary)
                                .frame(height: lineHeight)
                                .offset(x: lineX)
                            
                            VStack(alignment: .center) {
                                Text("\(selectedElement.day ?? Date(), format: .dateTime.year().month().day())")
                                    .fontSubtitle2Style(color: CoinTheme.shared.colors.backgroundSurface)                                
                                Text("-$\(selectedElement.expences, format: .number)")
                                    .fontBody1MediumStyle(color: CoinTheme.shared.colors.backgroundSurface)
                            }
                            .frame(width: UI.Card.Rectangle.width, height: UI.Card.Rectangle.height)
                            .background {
                                RoundedRectangle(cornerRadius: UI.Components.cornerRadius)
                                    .fill(CoinTheme.shared.colors.primary)
                            }
                            .offset(x: boxOffset)
                        }
                    }
                }
            }
    }
}
