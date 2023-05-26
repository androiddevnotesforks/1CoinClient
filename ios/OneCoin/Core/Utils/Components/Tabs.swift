//
//  Tabs.swift
//  OneCoin
//
//  Created by Эльдар Попов on 26.05.2023.
//

import SwiftUI

protocol TabsProtocol: Identifiable, Equatable {
    var title: String { get }
}

struct Tabs<Data>: View where Data: TabsProtocol {
    let tabs: [Data]
    @Binding var selected: Data
    @Namespace var nameSpace
    private let animationEffectID = "animationEffectID"
    
    var body: some View {
        HStack(spacing: UI.Components.dedaultHStackSpace) {
            ForEach(tabs, id: \.id) { tab in
                createTitle(for: tab)
                    .onTapGesture {
                        selected = tab
                    }
            }
        }
    }
}

private extension Tabs {
    func createTitle(for tab: Data) -> some View {
        let isSelected = tab == selected
        
        return Text(tab.title)
            .fontSubtitle2MediumStyle(color: isSelected ? CoinTheme.shared.colors.primary : CoinTheme.shared.colors.secondary)
            .padding(6)
            .background {
                if isSelected {
                    Capsule()
                        .fill(CoinTheme.shared.colors.secondary)
                        .matchedGeometryEffect(id: animationEffectID, in: nameSpace, properties: .frame)
                }
            }
            .animation(.spring(response: 0.5), value: self.selected)
    }
}
