#!/bin/bash

# author: Michal Stanke <stankmic@fel.cvut.cz>, AE4B33OSS, 2013
# task:	Create script that finds all files in a given directory (and subdirectories) having the suffix provided as an argument and performs one of the following actions:
# 		Copies them and changes their suffix, or
# 		renames them to use another suffix, or
# 		deletes them.

# Defining argument constants.
TASKNUM=3
GETTASK="-v"
HELP="-h"
COPY="-c"
RENAME="-m"
REMOVE="-r"

echohelp () {
	echo "Usage: suffix_handle 'directory' [arguments]"
	echo "  -c ext1 ext2	Copy all ext1 files with ext2 extension for the result."
	echo "  -m ext1 ext2	Change (rename) all ext1 files to ext2."
	echo "  -r ext1		Delete all ext1 files."
	echo "  -v		Required for automatic evaluation - prints out the task variant number."
	return 0
}

# Task number or help
if [ "$1" == "$GETTASK" ]; then
	echo $TASKNUM
	exit 0
elif [ "$1" == "$HELP" ]; then
	echohelp
	exit 0
fi

# Load directory
if [ -z "$1" ]; then
	echo "No directory specified." >&2
	exit 1
fi
directory="$1"

# Check directory read permission
if ! [ -r "$directory" ]; then
	echo "$directory" >&2
	exit 2
fi

# Determine the desired operation
if [ -z "$2" ]; then
	echo "Missing arguments." >&2
	exit 3
fi
operation="$2"

# Check operation validity
case "$operation" in
	"$COPY") ;;
	"$RENAME") ;;
	"$REMOVE") ;;
	*) echo "Invalid $operation argument." >&2; exit 4;
esac

# Check number of arguments
if [ "$operation" == "$REMOVE" ] && [ $# -ne 3 ]; then
	echo "Expecting directory and two arguments." >&2
	exit 3
elif [ "$operation" != "$REMOVE" ] && [ $# -ne 4 ]; then
	echo "Expecting directory and 3 arguments." >&2
	exit 3
fi

# Load file extension(s)
ext1="$3"
if [ "$operation" != "$REMOVE" ]; then
	ext2="$4"
fi

# Process the files
OLDIFS=$IFS
IFS=$(echo -n -e "\n\b")
for oldfile in $(find "$directory" -name "*$ext1"); do
	newfile="${oldfile%$ext1}$ext2"
	if [ "$operation" == "$COPY" ]; then
		if ! [ -r "$oldfile" ]; then
			echo "$oldfile" >&2
			continue
		fi
		cp "$oldfile" "$newfile"
		echo "$oldfile => $newfile"
	elif [ "$operation" == "$RENAME" ]; then
		mv "$oldfile" "$newfile"
		echo "$oldfile => $newfile"
	elif [ "$operation" == "$REMOVE" ]; then
		rm -f "$oldfile"
		echo "$oldfile"
	fi
done
IFS=$OLDIFS

exit 0
