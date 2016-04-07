#!/bin/bash
downloadfile () {
    FILE_URL="$1"
    if [[ ! "$FILE_URL" == *\:* ]]; then
        if [[ "$FILE_URL" == \/* ]]; then
            FILE_URL="file://$FILE_URL"
            else
                SOURCE_URL="$2"
                SOURCE_URL="$(echo "$SOURCE_URL" | sed -n 's/\(.*[/]\)[^/]*/\1/pI')"
                FILE_URL="$SOURCE_URL$FILE_URL"
        fi
    fi
    FILE_NAME="$(echo "$FILE_URL" | sed -n 's/.*[/]\(.*\.pdf\)/\1/pI')"
    curl -s "$FILE_URL" > "$FILE_NAME"
    if [ ! "$?" -eq "0" ]; then
        echo "ERROR $FILE_NAME" >&2
        rm "$FILE_NAME"
        else
            echo "$FILE_NAME"
    fi
}

#1
DIR="$(mktemp -d)"
echo "$DIR"
cd "$DIR"
if [ -z "$1" ]; then
    echo "Adresa nezadana." >&2
    exit 1
fi
#2
SOURCE_URL="$1"
curl -s "$SOURCE_URL" > source.html
if [ ! "$?" -eq "0" ]; then
    echo "Chyba stahovani stranky." >&2
    exit 2
fi
#3
while read line; do
    PDF_URL="$(echo "$line" | sed -n 's/.*href="\([^\"]*\.pdf\)".*/\1/pI')"
    if [ ! -z "$PDF_URL" ]; then
        #4
        downloadfile "$PDF_URL" "$SOURCE_URL"
    fi
done < source.html

rm source.html
