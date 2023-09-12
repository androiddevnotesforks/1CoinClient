//
//  CoinTheme.swift
//  OneCoin
//
//  Created by Виталий Перятин on 02.03.2023.
//

import SwiftUI

public final class CoinTheme: ObservableObject {
    public var colors: CoinColors
    public var typography: Typography = .main
    
    public static var shared: CoinTheme {
         CoinTheme.isCurrentColorSchemeDark ? CoinTheme.dark : CoinTheme.light
    }
    
    private init(colors: CoinColors, typography: Typography = .main) {
        self.colors = colors
        self.typography = typography
    }
    
    private static var isCurrentColorSchemeDark: Bool {
        let currentColorScheme = UITraitCollection.current.userInterfaceStyle
        return currentColorScheme == .dark ? true : false
    }
}

public extension CoinTheme {
    fileprivate static let dark = CoinTheme(
        colors: CoinColors.dark
    )
    
    fileprivate static let light = CoinTheme(
        colors: CoinColors.light
    )
}
