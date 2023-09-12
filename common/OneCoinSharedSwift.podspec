Pod::Spec.new do |spec|
    spec.name                     = 'OneCoinSharedSwift'
    spec.version                  = '1.0'
    spec.homepage                 = 'Link to a Kotlin/Native module homepage'
    spec.source                   = { :git => "Not Published", :tag => "Cocoapods/#{spec.name}/#{spec.version}" }
    spec.authors                  = ''
    spec.license                  = ''
    spec.summary                  = 'Some description for a Kotlin/Native module'
    spec.module_name              = "OneCoinSharedSwift"
    
    
    spec.static_framework         = false
    spec.dependency 'OneCoinShared'
    spec.source_files = "build/cocoapods/framework/OneCoinSharedSwift/**/*.{h,m,swift}"
end