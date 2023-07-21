//
//  TransactionType.swift
//  OneCoin
//
//  Created by Эльдар Попов on 13.07.2023.
//

import Foundation
import OneCoinShared

enum TransactionType {
    case transport
    case health
    
    var imageMRUrl: URL {
        switch self {
        case .health:
            return MR.files().ic_category_2.url
        case .transport:
            return MR.files().ic_category_8.url
        }
    }
}
