//
//  Typography.swift
//  OneCoin
//
//  Created by Виталий Перятин on 02.03.2023.
//

import SwiftUI
import OneCoinShared

public struct Typography {
    let h1: Font
    let h2: Font
    let h3: Font
    let h4: Font
    let h5: Font
    let body1: Font
    let body1_medium: Font
    let subtitle1: Font
    let subtitle2: Font
    let subtitle2_medium: Font
    let subtitle3: Font
    let subtitle4: Font
}

public extension Typography {
    static let main = Typography(
        h1: Font.rubikMedium(withSize: 42),
        h2: Font.rubikMedium(withSize: 32),
        h3: Font.rubikRegular(withSize: 22),
        h4: Font.rubikMedium(withSize: 20),
        h5: Font.rubikMedium(withSize: 18),
        body1: Font.rubikRegular(withSize: 14),
        body1_medium: Font.rubikMedium(withSize: 14),
        subtitle1: Font.rubikMedium(withSize: 13),
        subtitle2: Font.rubikRegular(withSize: 12),
        subtitle2_medium: Font.rubikMedium(withSize: 12),
        subtitle3: Font.rubikMedium(withSize: 11),
        subtitle4: Font.rubikMedium(withSize: 10)
    )
}

fileprivate struct DefaultTextStyle:  ViewModifier {
    let fontColor: SwiftUI.Color?
    
    init(fontColor: SwiftUI.Color? = nil) {
        self.fontColor = fontColor
    }
    
    func body(content: Content) -> some View {
        return content
            .foregroundColor(fontColor ?? CoinTheme.shared.colors.content)
            .multilineTextAlignment(.leading)
    }
}


fileprivate struct H1Style: ViewModifier {
    let fontColor: SwiftUI.Color?
    
    init(fontColor: SwiftUI.Color? = nil) {
        self.fontColor = fontColor
    }
    
    func body(content: Content) -> some View {
        return content
            .modifier(DefaultTextStyle(fontColor: fontColor))
            .font(CoinTheme.shared.typography.h1)
    }
}

fileprivate struct H2Style: ViewModifier {
    let fontColor: SwiftUI.Color?
    
    init(fontColor: SwiftUI.Color? = nil) {
        self.fontColor = fontColor
    }
    
    func body(content: Content) -> some View {
        return content
            .modifier(DefaultTextStyle(fontColor: fontColor))
            .font(CoinTheme.shared.typography.h2)
    }
}

fileprivate struct H3Style: ViewModifier {
    let fontColor: SwiftUI.Color?
    
    init(fontColor: SwiftUI.Color? = nil) {
        self.fontColor = fontColor
    }
    
    func body(content: Content) -> some View {
        return content
            .modifier(DefaultTextStyle(fontColor: fontColor))
            .font(CoinTheme.shared.typography.h3)
    }
}

fileprivate struct H4Style: ViewModifier {
    let fontColor: SwiftUI.Color?
    
    init(fontColor: SwiftUI.Color? = nil) {
        self.fontColor = fontColor
    }
    
    func body(content: Content) -> some View {
        return content
            .modifier(DefaultTextStyle(fontColor: fontColor))
            .font(CoinTheme.shared.typography.h4)
    }
}

fileprivate struct H5Style: ViewModifier {
    let fontColor: SwiftUI.Color?
    
    init(fontColor: SwiftUI.Color? = nil) {
        self.fontColor = fontColor
    }
    
    func body(content: Content) -> some View {
        return content
            .modifier(DefaultTextStyle(fontColor: fontColor))
            .font(CoinTheme.shared.typography.h5)
    }
}

fileprivate struct Body1Style: ViewModifier {
    let fontColor: SwiftUI.Color?
    
    init(fontColor: SwiftUI.Color? = nil) {
        self.fontColor = fontColor
    }
    
    func body(content: Content) -> some View {
        return content
            .modifier(DefaultTextStyle(fontColor: fontColor))
            .font(CoinTheme.shared.typography.body1)
    }
}

fileprivate struct Body1MediumStyle: ViewModifier {
    let fontColor: SwiftUI.Color?
    
    init(fontColor: SwiftUI.Color? = nil) {
        self.fontColor = fontColor
    }
    
    func body(content: Content) -> some View {
        return content
            .modifier(DefaultTextStyle(fontColor: fontColor))
            .font(CoinTheme.shared.typography.body1_medium)
    }
}

fileprivate struct Subtitle1Style: ViewModifier {
    let fontColor: SwiftUI.Color?
    
    init(fontColor: SwiftUI.Color? = nil) {
        self.fontColor = fontColor
    }
    
    func body(content: Content) -> some View {
        return content
            .modifier(DefaultTextStyle(fontColor: fontColor))
            .font(CoinTheme.shared.typography.subtitle1)
    }
}

fileprivate struct Subtitle2Style: ViewModifier {
    let fontColor: SwiftUI.Color?
    
    init(fontColor: SwiftUI.Color? = nil) {
        self.fontColor = fontColor
    }
    
    func body(content: Content) -> some View {
        return content
            .modifier(DefaultTextStyle(fontColor: fontColor))
            .font(CoinTheme.shared.typography.subtitle2)
    }
}

fileprivate struct Subtitle2MediumStyle: ViewModifier {
    let fontColor: SwiftUI.Color?
    
    init(fontColor: SwiftUI.Color? = nil) {
        self.fontColor = fontColor
    }
    
    func body(content: Content) -> some View {
        return content
            .modifier(DefaultTextStyle(fontColor: fontColor))
            .font(CoinTheme.shared.typography.subtitle2_medium)
    }
}

fileprivate struct Subtitle3Style: ViewModifier {
    let fontColor: SwiftUI.Color?
    
    init(fontColor: SwiftUI.Color? = nil) {
        self.fontColor = fontColor
    }
    
    func body(content: Content) -> some View {
        return content
            .modifier(DefaultTextStyle(fontColor: fontColor))
            .font(CoinTheme.shared.typography.subtitle3)
    }
}

fileprivate struct Subtitle4Style: ViewModifier {
    let fontColor: SwiftUI.Color?
    
    init(fontColor: SwiftUI.Color? = nil) {
        self.fontColor = fontColor
    }
    
    func body(content: Content) -> some View {
        return content
            .modifier(DefaultTextStyle(fontColor: fontColor))
            .font(CoinTheme.shared.typography.subtitle4)
    }
}

extension View {
    func fontH1Style(color: SwiftUI.Color? = nil) -> some View {
        modifier(H1Style(fontColor: color))
    }
    
    func fontH2Style(color: SwiftUI.Color? = nil) -> some View {
        modifier(H2Style(fontColor: color))
    }
    
    func fontH3Style(color: SwiftUI.Color? = nil) -> some View {
        modifier(H3Style(fontColor: color))
    }
    
    func fontH4Style(color: SwiftUI.Color? = nil) -> some View {
        modifier(H4Style(fontColor: color))
    }
    
    func fontH5Style(color: SwiftUI.Color? = nil) -> some View {
        modifier(H5Style(fontColor: color))
    }
    
    func fontBody1Style(color: SwiftUI.Color? = nil) -> some View {
        modifier(Body1Style(fontColor: color))
    }
    
    func fontBody1MediumStyle(color: SwiftUI.Color? = nil) -> some View {
        modifier(Body1MediumStyle(fontColor: color))
    }
    
    func fontSubtitle1Style(color: SwiftUI.Color? = nil) -> some View {
        modifier(Subtitle1Style(fontColor: color))
    }
    
    func fontSubtitle2Style(color: SwiftUI.Color? = nil) -> some View {
        modifier(Subtitle2Style(fontColor: color))
    }
    
    func fontSubtitle2MediumStyle(color: SwiftUI.Color? = nil) -> some View {
        modifier(Subtitle2MediumStyle(fontColor: color))
    }
    
    func fontSubtitle3Style(color: SwiftUI.Color? = nil) -> some View {
        modifier(Subtitle3Style(fontColor: color))
    }
    
    func fontSubtitle4Style(color: SwiftUI.Color? = nil) -> some View {
        modifier(Subtitle4Style(fontColor: color))
    }
}

extension Font {
    static func rubikMedium(withSize: Double) -> Font {
        MR.fontsRubik().medium.toSwiftUIFont(withSize: withSize)
    }
    
    static func rubikRegular(withSize: Double) -> Font {
        MR.fontsRubik().regular.toSwiftUIFont(withSize: withSize)
    }
}

extension FontResource {
    func toSwiftUIFont(withSize: Double) -> Font {
        return Font(uiFont(withSize: withSize))
    }
}
