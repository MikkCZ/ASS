#!/bin/bash
basename $0
pwd
echo
echo "$1"
VAR1=$(ls "$2" -1 | grep "$1")
echo "$VAR1" >&2
VAR2=$(/bin/uname -a)
sh /tmp/osdwrite "$VAR2"
