//
//  View+Extension.swift
//  OneCoin
//
//  Created by Виталий Перятин on 22.04.2023.
//

import SwiftUI

// Gestures
extension View {
    public func onTapGestureIf(_ condition: Bool, closure: @escaping () -> ()) -> some View {
        self.allowsHitTesting(condition)
            .onTapGesture {
                closure()
            }
    }
}

// Conditions
extension View {
    @ViewBuilder
    public func `if`<Transform: View>(
        _ value: Bool,
        transform: (Self) -> Transform
    ) -> some View {
        if value {
            transform(self)
        } else {
            self
        }
    }
    
    @ViewBuilder
    public func `if`<TrueContent: View, FalseContent: View>(
        _ value: Bool,
        if ifTransform: (Self) -> TrueContent,
        else elseTransform: (Self) -> FalseContent
    ) -> some View {
        if value {
            ifTransform(self)
        } else {
            elseTransform(self)
        }
    }
}
