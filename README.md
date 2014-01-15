documentMaster
==============

Code to generate quiz, worksheet, reports etc.

Java Web Service:

To generate java files
>/opt/axis2-1.6.1/bin/wsdl2java.sh  -uri documentMaster.wsdl -s -ss -sd -ssi -or -o .

(-or 'overwrite flag' to be used only when adding a new operation)

To build/compile java src
>ant jar.server
