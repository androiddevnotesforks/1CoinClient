//
//  TimelineTypes.swift
//  OneCoin
//
//  Created by Эльдар Попов on 24.05.2023.
//

enum TimelineTypes: String, Identifiable {
    var id: Self { return self }
    
    case Week
    case Month
    case Year
}
