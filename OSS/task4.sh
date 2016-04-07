#!/bin/bash
typsouboru() {
    if [ -d "$1" ]; then
        echo "DIR $1"; return 0
        elif [ -f "$1" ]; then
            echo "FILE $1"; return 0
        file
        else
            echo "INVALID $1" >&2; return 1
    fi
}

COUNT="0"
while read line; do
#    [ -z "$line" ] && break
    typsouboru "$line"
    COUNT=$(($COUNT + $?))
done

if [ ! "$COUNT" -eq "0" ]; then
    echo "$COUNT ERRORS" >&2
fi
