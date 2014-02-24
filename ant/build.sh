#!/bin/sh
cd ant
ant -Didea.home=/Applications/IntelliJ\ IDEA\ 13.app -Dplugin.home=/Users/guy/Library/Application\ Support/IntelliJIdea13 $* install
