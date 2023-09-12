//
//  YAxisDashedLine.swift
//  OneCoin
//
//  Created by Эльдар Попов on 26.05.2023.
//

import SwiftUI

struct YAxisDashedLine: Shape {
    func path(in rect: CGRect) -> Path {
        var path = Path()
        path.move(to: CGPoint(x: 0, y: 0))
        path.addLine(to: CGPoint(x: 0, y: rect.height))
        return path
    }
}
