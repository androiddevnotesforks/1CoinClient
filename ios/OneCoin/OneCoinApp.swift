//
//  OneCoinApp.swift
//  OneCoin
//
//  Created by Виталий Перятин on 07.02.2023.
//

import SwiftUI
import OneCoinShared
import OneCoinSharedSwift
import Combine
import KMPNativeCoroutinesCombine

@main
struct OneCoinApp: App {
    private let diMediator = DiMediator()
    private var cancellableCounter: AnyCancellable?
    private var cancellableTextField: AnyCancellable?
    private var cancellableAction: AnyCancellable?
    
    init() {
        initKMMLogger()
        initKMMDi()
        initApp()
        
        sampleViewModelUsage()
        //  homeViewModelUsage()
    }
 
    var body: some Scene {
        WindowGroup {
            AppScreen()
        }
    }
}

extension OneCoinApp {
    // Initialize logger first
    private func initKMMLogger() {
        LoggerInitializer().doInit()
    }
    
    // Inject all Koin denendencies graph
    private func initKMMDi() {
        diMediator.configure()
    }

    // Initialize application on startup
    private func initApp() {
        diMediator.core.appInitializer.configure()
    }
    
    // Sample ViewModel simulating the real one for isolated testing of comsuming Flows in Swift
    private mutating func sampleViewModelUsage() {
        let sampleViewModel = diMediator.sample.sampleViewModel
        // onScreenComposed called after screen rendered
        sampleViewModel.onScreenComposed()
        
        // Create publisher from StateFlow with primitive value in SampleViewModel
        // https://github.com/rickclephas/KMP-NativeCoroutines/tree/master#combine
        // https://github.com/rickclephas/KMP-NativeCoroutines/issues/94
        // https://github.com/rickclephas/KMP-NativeCoroutines/blob/bb6e5ea0504db706ef52577a66861c015daf2265/sample/Combine/ClockCombineViewModel.swift#L30
        // https://github.com/maltsev-gorskij/MultiplatformSandbox_ios/blob/develop/iosApp/Presentation/Screens/Launches/ViewModel/LaunchesViewModel.swift#L41
        cancellableCounter = createPublisher(for: sampleViewModel.counterNative)
            .receive(on: RunLoop.main)
            .sink { completion in
                print("Received counter completion: \(completion)")
            } receiveValue: { value in
                print("Received counter value: \(value)")
            }
        
        // This call will emit new increased value of counter through the publisher
        sampleViewModel.increaseCounter()
        
        // Cancel publisher if necessary
//        cancellableCounter?.cancel()
//        cancellableCounter = nil
        
        // Create publisher from StateFlow with data class value in SampleViewModel
        cancellableTextField = createPublisher(for: sampleViewModel.textFieldParamsNative)
            .receive(on: RunLoop.main)
            .sink(receiveCompletion: { completion in
                print("Received textField completion: \(completion)")
            }, receiveValue: { value in
                print("Received textField value: \(value.text) \(value.colorName)")
            })
        
        // This call will emit new value through the publisher
        sampleViewModel.setTextFieldValue(textFieldParams: TextFieldParams(text: "SomeText", colorName: "Red"))
        
        // Create publisher from StateFlow with sealed class value in SampleViewModel to listen UI MVI like Actions
        // https://github.com/icerockdev/moko-kswift
        // https://github.com/maltsev-gorskij/MultiplatformSandbox_ios/blob/develop/iosApp/Presentation/Screens/Launches/ViewModel/LaunchesViewModel.swift#L41
        // https://github.com/icerockdev/moko-kswift/tree/master/sample/ios-app/Tests
        cancellableAction = createPublisher(for: sampleViewModel.viewActionChannelNative)
            .receive(on: RunLoop.main)
            .sink(receiveCompletion: { completion in
                print("Received action completion: \(completion)")
            }, receiveValue: { sampleAction in
                guard let sampleAction = sampleAction else { return }
                let sampleActionKs = SampleActionKs(sampleAction)
                switch sampleActionKs {
                case .navigateBack:
                    print("Action: PopBackStack")
                case .openDetailsScreen(let result):
                    print("Action: Open details screen on item #\(result.navScreenParams.id)")
                }
            })
        
        // This call will emit new action through the publisher
        sampleViewModel.emitNavigateBackAction()
        sampleViewModel.emitOpenDetailsScreenAction(navScreenParams: NavScreenParams(id: 194))
        
        // Clear ViewModel to stop all async jobs if ViewModel do not needed anymore
        // sampleViewModel.clear()
    }
    
    // Example of what can be comsumed from real one production ViewModel already used in Android build
    private func homeViewModelUsage() {
        // let homeViewModel = diMediator.home.homeViewModel
        // homeViewModel.onScreenComposed()
        
    
        // https://github.com/adeo-opensource/kviewmodel--mpp#ios
        // https://github.com/icerockdev/moko-kswift
        // homeViewModel.onScreenComposed() - called after screen rendered
        // createPublisher(for: homeViewModel.totalBalanceNative) - example of listening state changes
        // createPublisher(for: homeViewModel.viewActionChannelNative) - for listening actions
    }
}
