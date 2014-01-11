#!/bin/bash
REGEXP_1="\[.*\]\(.*\)%E1"
REGEXP_2="\[.*\]\(.*\)%E2"
REGEXP_F="\[.*\]\(.*\)%Efree"

V_SIZE="MinSize=25pt"

C_1="color=orange"
C_2="color=violet"
C_F="color=black"

USED_SIZE="lw=3pt"
FREE_SIZE="lw=1pt"

sed -i "s|$REGEXP_1$|\[$USED_SIZE,$C_1\]\1%E1|" *.tex
sed -i "s|$REGEXP_2$|\[$USED_SIZE,$C_2\]\1%E2|" *.tex
sed -i "s|$REGEXP_F$|\[$FREE_SIZE,$C_F\]\1%Efree|" *.tex