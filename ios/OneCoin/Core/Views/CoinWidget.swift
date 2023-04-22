//
//  CoinWidget.swift
//  OneCoin
//
//  Created by Виталий Перятин on 22.04.2023.
//

import SwiftUI
import OneCoinShared

private let HorizontalPadding = 16.0

struct CoinWidget<Content: View>: View {
    
    @EnvironmentObject var theme: CoinTheme
    
    var title: String
    var withBorder: Bool = false
    var withHorizontalPadding: Bool = true
    var onClick: (() -> ())? = nil
    var content: () -> Content
    
    var body: some View {
        VStack(alignment: .leading) {
            CoinWidgetTitle(title: title, onClick: onClick)
            CoinWidgetContent<Content>(
                withBorder: withBorder,
                withHorizontalPadding: withHorizontalPadding,
                content: content
            )
        }
    }
}

private struct CoinWidgetTitle: View {
    
    @EnvironmentObject var theme: CoinTheme
    
    var title: String
    var onClick: (() -> ())? = nil
    
    var body: some View {
        let clickEnabled = onClick != nil
        HStack(alignment: .center) {
            Text(title)
                .fontH5Style(color: theme.colors.content)
            if (clickEnabled) {
                Spacer()
                // TODO: replace ic_arrow_down to ic_arrow_next_small
                SVGImageView(url: MR.files().ic_arrow_down.url)
                    .tintColor(theme.colors.content)
                    .frameSvg(width: 24, height: 24)
            }
        }
            .onTapGestureIf(clickEnabled) {
                onClick!()
            }
            .frame(maxWidth: .infinity, alignment: .leading)
            .padding(.vertical, 8)
            .padding(.horizontal, HorizontalPadding)
    }
}

private struct CoinWidgetContent<Content: View>: View {
    
    @EnvironmentObject var theme: CoinTheme
    
    var withBorder: Bool = false
    var withHorizontalPadding: Bool = true
    var content: () -> Content
    
    var body: some View {
        let contentHorizontalPadding = withHorizontalPadding ? HorizontalPadding : 0
        let borderWidth: CGFloat = withBorder ? 1 : 0
        let borderRadius: CGFloat = withBorder ? 12 : 0
        let shape = withBorder ? RoundedRectangle(cornerRadius: borderRadius) : RoundedRectangle(cornerRadius: 0)
        
        content()
            .frame(maxWidth: .infinity, alignment: .leading)
            .overlay(
                shape.stroke(theme.colors.dividers, lineWidth: borderWidth)
            )
            .background(theme.colors.background)
            .clipShape(shape)
            .padding(.horizontal, contentHorizontalPadding)
            .padding(.top, 4)
    }
}


struct CoinWidget_Previews: PreviewProvider {
    static var previews: some View {
        CoinWidget(
            title: "Title",
            withBorder: true,
            withHorizontalPadding: true
        ) {
            Text("Content")
        }
        .environmentObject(CoinTheme.light)
    }
}
