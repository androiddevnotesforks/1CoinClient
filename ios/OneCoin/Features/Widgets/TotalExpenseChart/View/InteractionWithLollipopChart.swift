//
//  InteractionWithLollipopChart.swift
//  OneCoin
//
//  Created by Эльдар Попов on 25.05.2023.
//

import SwiftUI
import Charts

struct InteractiveLollipop: View {
    let data: [LollipopChartData]
    @State private var selectedElement: LollipopChartData? = nil
    @Environment(\.layoutDirection) var layoutDirection

    var body: some View {
        LollipopChart(data: data, selectedElement: $selectedElement)
            .chartBackground { proxy in
                ZStack(alignment: .topLeading) {
                    GeometryReader { nthGeoItem in
                        if let selectedElement = selectedElement {
                            let dateInterval = Calendar.current.dateInterval(of: .day, for: selectedElement.day)!
                            let startPositionX1 = proxy.position(forX: dateInterval.start) ?? 0
                            let startPositionX2 = proxy.position(forX: dateInterval.end) ?? 0
                            let midStartPositionX = (startPositionX1 + startPositionX2) / 2 + nthGeoItem[proxy.plotAreaFrame].origin.x

                            let lineX = layoutDirection == .rightToLeft ? nthGeoItem.size.width - midStartPositionX : midStartPositionX
                            let lineHeight = nthGeoItem[proxy.plotAreaFrame].maxY
                            let boxOffset = max(0, min(nthGeoItem.size.width - 94, lineX - 94 / 2))
                            
                            Rectangle()
                                .fill(Color.blue)
                                .frame(width: 2, height: lineHeight)
                                .position(x: lineX, y: lineHeight / 2)
                            
                            VStack(alignment: .center) {
                                Text("\(selectedElement.day, format: .dateTime.year().month().day())")
                                    .fontSubtitle2Style(color: Color.secondary)                                
                                Text("-$\(selectedElement.expences, format: .number)")
                                    .fontBody1MediumStyle(color: CoinTheme.shared.colors.backgroundSurface)
                            }
                            .frame(width: 94, height: 58)
                            .background {
                                ZStack {
                                    RoundedRectangle(cornerRadius: 8)
                                        .fill(Color.blue)
                                    RoundedRectangle(cornerRadius: 8)
                                        .fill(Color.blue)
                                }
                            }
                            .offset(x: boxOffset)
                        }
                    }
                }
            }
    }
}
