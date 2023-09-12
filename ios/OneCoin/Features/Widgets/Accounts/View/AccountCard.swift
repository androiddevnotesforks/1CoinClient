//
//  AccountCard.swift
//  OneCoin
//
//  Created by Эльдар Попов on 23.05.2023.
//

import SwiftUI

struct AccountCardView<Content>: View where Content: View {
    private let color: Color
    private let content: () -> Content
    private let sizeOfContainer: CGSize
    
    @inlinable
    public init(
        color: Color,
        sizeOfContainer: CGSize,
        content: @escaping () -> Content
    ) {
        self.color = color
        self.sizeOfContainer = sizeOfContainer
        self.content = content
    }
    
    var body: some View {
        RoundedRectangle(cornerRadius: UI.Components.cornerRadius)
            .fill(color)
            .frame(height: sizeOfContainer.height)
            .frame(width: sizeOfContainer.width)
            .overlay(alignment: .top) {
                content()
            }
    }
}
