#!/usr/bin/env sh

# Steps documentation in Notion: https://regal-bone-e41.notion.site/iOS-1Coin-93a26d9be65e491c86b390f0f2448aae

# Shell colors variables
red=`tput setaf 1`
green=`tput setaf 2`
reset=`tput sgr0`

# Check to see if Homebrew is installed, and install it if it is not
command -v brew >/dev/null 2>&1 && echo "${green}[✓]${reset} HomewBrew Installed\n" || { echo "${red}[✖] ${reset}Installing Homebrew Now...${reset}"; /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"; }

# Check Java Version
if type -p java >/dev/null 2>&1; then
    _java=java
elif [[ -n "$JAVA_HOME" ]] && [[ -x "$JAVA_HOME/bin/java" ]];  then
    _java="$JAVA_HOME/bin/java"
else
    echo "${red}[✖] ${reset}Java not found\n"
    brew install java11
fi

if [[ "$_java" ]]; then
    version=$("$_java" -version 2>&1 | awk -F '"' '/version/ {print $2}')
    echo "Java Version is $version"
    if [[ "$version" > "11.0.0" ]]; then
        echo "${green}[✓]${reset} Its more or equal 11.0.0\n"
    else
        echo "${red}[✖] ${reset}Installing Java\n"
        brew install java11
    fi
fi

# Kdoctor 
command -v kdoctor >/dev/null 2>&1 && kdoctor || { echo "${red}[✖] ${reset}Installing kdoctor Now...${reset}"; brew install kdoctor; kdoctor; }

# Generate DummyFramework
./gradlew generateDummyFramework

# Pod
cd ./ios/ && pod install

# Open
open ./*.xcworkspace
