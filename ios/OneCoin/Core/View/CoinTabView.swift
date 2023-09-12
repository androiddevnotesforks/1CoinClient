//
//  CoinTabView.swift
//  OneCoin
//
//  Created by Виталий Перятин on 25.02.2023.
//

import SwiftUI
import OneCoinShared

struct CoinTabsView: View {
    private enum Constant {
        static let plusIconFrame: CGFloat = 30
        static let plusButtonFrame: CGFloat = 56
        static let tabOffset: CGFloat = 5
    }
    let selectedTab: OneCoinTabs
    @EnvironmentObject var router: Router
    
    var body: some View {
        HStack {
            // MARK: - Home Tab
            showSelectedTabScreen(with: OneCoinTabs.home)
            
            // MARK: - Transactions Tab
            Spacer()
            showSelectedTabScreen(with: .transactions)
            
            // MARK: - Plus Button
            addTransactionButton
                .offset(y: -Constant.plusIconFrame)
            
            // MARK: - Plans Tab
            showSelectedTabScreen(with: .plans)
            Spacer()
            
            // MARK: - Analytics Tab
            showSelectedTabScreen(with: .analytics)
        }
        .navigationBarBackButtonHidden()
        .padding(.horizontal, Constant.plusIconFrame)
        .offset(y: -Constant.tabOffset)
        .frame(height: Constant.plusButtonFrame)
        .background {
            Divider()
                .frame(height: 2)
                .overlay(Color.black.opacity(0.15))
                .offset(y: -Constant.plusIconFrame - Constant.tabOffset)
        }
        .background(CoinTheme.shared.colors.backgroundSurface)
    }
    
    private var addTransactionButton: some View {
        Button {
            router.navigate(to: OneCoinTabs.add)
        } label: {
            ZStack {
                Circle()
                    .foregroundColor(CoinTheme.shared.colors.primary)
                    .frame(width: Constant.plusButtonFrame, height: Constant.plusButtonFrame)
                    .shadow(radius: 4)
                
                SVGImageView(url: OneCoinTabs.add.tabURL)
                    .tintColor(CoinTheme.shared.colors.white)
                    .frameSvg(width: Constant.plusIconFrame, height: Constant.plusIconFrame)
            }
        }
        .buttonStyle(PlainButtonStyle())
    }
    
    private func showSelectedTabScreen(with tab: OneCoinTabs) -> some View {
        CoinTab(
            text: tab.tabName,
            url: tab.tabURL,
            isActive: selectedTab == tab
        ) { router.navigate(to: tab) }
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
        .buttonStyle(PlainButtonStyle())
    }
}

struct CoinTabView_Previews: PreviewProvider {
    static var previews: some View {
        CoinTabsView(selectedTab: OneCoinTabs.home)
    }
}
