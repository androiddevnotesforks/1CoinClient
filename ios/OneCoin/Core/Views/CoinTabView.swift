//
//  CoinTabView.swift
//  OneCoin
//
//  Created by Виталий Перятин on 25.02.2023.
//

import SwiftUI
import OneCoinShared

struct CoinTabsView: View {
    @Binding var selectedTab: OneCoinTabs
    
    var body: some View {
        HStack {
            // MARK: - Home Tab
            Spacer()
            showSelectedTabScreen(with: OneCoinTabs.home)
            
            // MARK: - Transactions Tab
            Spacer()
            showSelectedTabScreen(with: .transactions)

            // MARK: - Plus Button
            addTransactionButton

            // MARK: - Plans Tab
            showSelectedTabScreen(with: .plans)
            Spacer()
            
            // MARK: - Analytics Tab
            showSelectedTabScreen(with: .analytics)
            Spacer()
        }
        .background {
            Divider()
                .padding(.bottom, 55)
        }
        .background(CoinTheme.shared.colors.backgroundSurface)
    }
    
    private var addTransactionButton: some View {
        Button {
            selectedTab = OneCoinTabs.add
        } label: {
            ZStack {
                Circle()
                    .foregroundColor(CoinTheme.shared.colors.primary)
                    .frame(width: 56, height: 56)
                    .shadow(radius: 4)
                
                SVGImageView(url: OneCoinTabs.add.tabURL)
                    .tintColor(CoinTheme.shared.colors.white)
                    .frameSvg(width: 30, height: 30)
            }
            .padding(.bottom, 32)
        }
    }
    
    private func showSelectedTabScreen(with tab: OneCoinTabs) -> some View {
        CoinTab(
            text: tab.tabName,
            url: tab.tabURL,
            isActive: selectedTab == tab
        ) { selectedTab = tab }
    }
}

fileprivate struct CoinTab: View {
    private let text: String
    private let url: URL
    private var isActive: Bool
    private let action: () -> ()
    
    init(text: String, url: URL, isActive: Bool, action: @escaping () -> Void) {
        self.text = text
        self.url = url
        self.isActive = isActive
        self.action = action
    }
    
    var body: some View {
        Button {
            action()
        } label: {
            VStack {
                SVGImageView(url: url)
                    .tintColor(isActive ? CoinTheme.shared.colors.primary : CoinTheme.shared.colors.secondary)
                    .frameSvg(width: 25, height: 25)
                Text(text)
                    .fontSubtitle4Style(color: isActive ? CoinTheme.shared.colors.primary : CoinTheme.shared.colors.secondary)
            }
        }
    }
}

struct CoinTabView_Previews: PreviewProvider {
    static var previews: some View {
        CoinTabsView(selectedTab: .constant(OneCoinTabs.home))
    }
}
