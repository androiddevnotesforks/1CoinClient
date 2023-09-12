//
//  Router.swift
//  OneCoin
//
//  Created by Эльдар Попов on 14.07.2023.
//

import SwiftUI

final class Router: ObservableObject {
    typealias Destination = OneCoinTabs
    
    @Published var navPath = NavigationPath()
    
    func navigate(to destination: Destination) {
        navPath.append(destination)
    }
    
    func navigateBack() {
        navPath.removeLast()
    }
    
    func navigateToRoot(destination: Destination = .home) {
        navPath.removeLast(navPath.count)
        navPath.append(destination)
    }
}
