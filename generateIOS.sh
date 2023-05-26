#!/usr/bin/env sh

## !!!
## Steps documentation in Notion: https://www.notion.so/1coin/Guide-for-iOS-developer-for-first-cold-run-80aaa5af802a4e359ab98e46e9f14782
## !!!

# Shell colors variables
red=`tput setaf 1`
green=`tput setaf 2`
reset=`tput sgr0`

# 1) Check to see if Homebrew is installed, and install it if it is not
if command -v brew >/dev/null 2>&1; then
    echo "${green}[✓]${reset} HomewBrew Installed\n"
else
    echo "${red}[✖] ${reset}Installing Homebrew Now...${reset}"; /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)";
fi

# 2) Check Java Version
JavaInstallation() {
    echo "Installing Java...\n"
    brew install java11 &&
    sudo ln -sfn $HOMEBREW_PREFIX/opt/openjdk@11/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-11.jdk &&
    echo 'export JAVA_HOME="$HOMEBREW_PREFIX/opt/openjdk@11/libexec/openjdk.jdk/Contents/Home"' >> ~/.zshrc
}

if type -p java >/dev/null 2>&1; then
    _java=java
elif [[ -n "$JAVA_HOME" ]] && [[ -x "$JAVA_HOME/bin/java" ]];  then
    _java="$JAVA_HOME/bin/java"
else
    echo "${red}[✖] ${reset}Java not found\n"
    JavaInstallation
fi

if [[ "$_java" ]]; then
    version=$("$_java" -version 2>&1 | awk -F '"' '/version/ {print $2}')
    echo "Java Version is $version"
    if [[ "$version" > "11.0.0" ]]; then
        echo "${green}[✓]${reset} Its more or equal 11.0.0\n"
    else
        echo "${red}[✖]${reset} Its less version than 11.0.0\n"
       JavaInstallation
    fi
fi

# 3) Kdoctor
RunKDoctor() {
    (kdoctor | tee /dev/tty | grep "[✖]" >/dev/null) && 
    (echo "${red}\nPlease resolve all error then start again${reset}"; exit -1)
}

if command -v kdoctor >/dev/null 2>&1; then
    RunKDoctor
else 
    echo "${red}[✖] ${reset}Installing kdoctor Now...${reset}"
    brew install kdoctor && RunKDoctor
fi

# 4) Generate DummyFramework
echo "\n${green}Generating KMM binary for project...${reset}\n"
./gradlew generateDummyFramework &&

if [[ $? == 1 ]]; then exit $?; fi

# 5) Pods and Opening Workspace
echo "\n${green}Pod install...${reset}\n"
cd ./ios/ && pod install && open ./*.xcworkspace