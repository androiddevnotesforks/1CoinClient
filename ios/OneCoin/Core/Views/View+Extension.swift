//
//  View+Extension.swift
//  OneCoin
//
//  Created by Виталий Перятин on 22.04.2023.
//

import SwiftUI

extension View {
    func onTapGestureIf(_ condition: Bool, closure: @escaping () -> ()) -> some View {
        self.allowsHitTesting(condition)
            .onTapGesture {
                closure()
            }
    }
}
