//
//  TimelineTypes.swift
//  OneCoin
//
//  Created by Эльдар Попов on 24.05.2023.
//

enum TimelineType: String {
    case Week
    case Month
    case Year
}

// add collection of all values of TimelineTypes
extension TimelineType: CaseIterable { }

// ID
extension TimelineType: Identifiable { 
    var id: Self { return self }
}

// for creatind Tabs From Timeline
extension TimelineType: TabsProtocol {
    var title: String {
        self.rawValue
    }
}
