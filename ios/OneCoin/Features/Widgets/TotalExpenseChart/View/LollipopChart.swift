//
//  LollipopChart.swift
//  OneCoin
//
//  Created by Эльдар Попов on 25.05.2023.
//

import SwiftUI
import Charts

struct LollipopChart: View {
    let data: [LollipopChartData]
    @Binding var selectedElement: LollipopChartData?
    
    var body: some View {
        Chart(data, id: \.day) {
            BarMark(
                x: PlottableValue.value($0.id, $0.day, unit: .day),
                y: PlottableValue.value($0.expences.description, $0.expences)
            )
        }
        .chartOverlay { proxy in
            GeometryReader { geometry in
                Rectangle()
                    .fill(.clear)
                    .contentShape(Rectangle())
                    .gesture(
                        SpatialTapGesture()
                            .onEnded { value in
                                // Convert the gesture location to the coordinate space of the plot area.
                                let selectedItem = getIndexOfDataFromGesture(location: value.location, proxy: proxy, geometry: geometry)
                                
                                if selectedElement?.day == selectedItem?.day {
                                    // If tapping the same element, clear the selection.
                                    selectedElement = nil
                                } else {
                                    selectedElement = selectedItem
                                }
                            }
                            .exclusively(
                                before: DragGesture()
                                    .onChanged { value in
                                        selectedElement = getIndexOfDataFromGesture(location: value.location, proxy: proxy, geometry: geometry)
                                    }
                            )
                    )
                }
        }
    }
    
    private func getIndexOfDataFromGesture(location: CGPoint, proxy: ChartProxy, geometry: GeometryProxy) -> LollipopChartData? {
        let relativeXPosition = location.x - geometry[proxy.plotAreaFrame].origin.x
        if let date = proxy.value(atX: relativeXPosition) as Date? {
            // Find the closest date element.
            var minDistance: TimeInterval = .infinity
            var index: Int? = nil
            for salesDataIndex in data.indices {
                let nthSalesDataDistance = data[salesDataIndex].day.distance(to: date)
                if abs(nthSalesDataDistance) < minDistance {
                    minDistance = abs(nthSalesDataDistance)
                    index = salesDataIndex
                }
            }
            if let index = index {
                return data[safe: index]
            }
        }
        return nil
    }
}
