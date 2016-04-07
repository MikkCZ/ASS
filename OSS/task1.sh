#!/bin/bash
cd /tmp
mkdir osdtask
cd osdtask
ls / > rootdir.txt
chmod 660 rootdir.txt
ln -s rootdir.txt odkaz.txt
cd ..
rm -r smazat
echo DONE >&2
