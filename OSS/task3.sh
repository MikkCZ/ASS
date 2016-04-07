#!/bin/bash
if [ -z "$1" ]; then
    echo "CHYBA1" >&2; exit 1
fi

if ([ ! -f "$1" ] || [ ! -w "$1" ]); then
    echo "CHYBA2" >&2; exit 2
fi

echo $(cat "$1" | wc -l)

if [ -z "$2" ]; then
    exit 0
    elif !(pgrep "$2"); then
        echo "CHYBA3" >&2; exit 0
    file
    else
        kill -15 $(pgrep "$2")
fi
