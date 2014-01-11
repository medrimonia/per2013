#!/bin/bash
REGEXP_1="Normal.*V1"
REGEXP_2="Normal.*V2"
REGEXP_F="Normal.*Vfree"

V_SIZE="MinSize=25pt"

C_1="LineColor=orange"
C_2="LineColor=violet"
C_F="LineColor=black"

USED_SIZE="LineWidth=3pt"
FREE_SIZE="LineWidth=1pt"

sed -i "s/$REGEXP_1/Normal[$V_SIZE,$C_1,$USED_SIZE]%V1/"    *.tex
sed -i "s/$REGEXP_2/Normal[$V_SIZE,$C_2,$USED_SIZE]%V2/"    *.tex
sed -i "s/$REGEXP_F/Normal[$V_SIZE,$C_F,$FREE_SIZE]%Vfree/" *.tex
