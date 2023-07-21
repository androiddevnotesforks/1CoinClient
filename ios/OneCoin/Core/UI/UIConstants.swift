//
//  UIConstants.swift
//  OneCoin
//
//  Created by Эльдар Попов on 22.05.2023.
//

import Foundation

public enum UI { }

public extension UI {
    enum Padding {
        enum Vertical {
            static let small: CGFloat = 8
            static let `default`: CGFloat = 16
        }
        
        enum Horizontal {
            static let small: CGFloat = 8
            static let `default`: CGFloat = 16
        }
        
        enum Scroll {
            static let bottom: CGFloat = 30
        }
    }
    
    enum Spacing {
        static let `default`: CGFloat = 12
        static let large: CGFloat = 24
    }
    
    enum Card {
        static let height: CGFloat = 128
        static let largeHeight: CGFloat = 329
        
        static let width: CGFloat = 160
        
        enum Rectangle {
            static let height: CGFloat = 58
            static let width: CGFloat = 94
        }
    }
    
    enum Components {
        static let cornerRadius: CGFloat = 12
        static let dedaultHStackSpace: CGFloat = 8
    }
    
    enum SVGIcon {
        static let small: CGFloat = 18
        static let `default`: CGFloat = 24
        static let large: CGFloat = 44
    }
}
