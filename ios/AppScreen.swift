//
//  AppScreen.swift
//  OneCoin
//
//  Created by Виталий Перятин on 04.03.2023.
//

import SwiftUI

struct AppScreen: View {
    @Environment(\.colorScheme) var colorScheme: ColorScheme
    
    @State private var safeAreaInsets: (top: CGFloat, bottom: CGFloat) = (0, 0)
    
    var body: some View {
        GeometryReader { proxy in
            ZStack {
                TabsNavigationScreen()
                    .ignoresSafeArea()
                    .environment(\.safeAreaInsets, safeAreaInsets)
                    .onAppear {
                        safeAreaInsets = (proxy.safeAreaInsets.top, proxy.safeAreaInsets.bottom)
                    }
            }
        }
    }
}

struct AppScreen_Previews: PreviewProvider {
    static var previews: some View {
        AppScreen()
    }
}
