#!/bin/bash
sed -i 's/Vertex.*{a}/Vertex[x=4.8,y=5  ]{a}/' *.tex
sed -i 's/Vertex.*{b}/Vertex[x=3.2,y=8.2]{b}/' *.tex
sed -i 's/Vertex.*{c}/Vertex[x=4.8,y=7  ]{c}/' *.tex
sed -i 's/Vertex.*{d}/Vertex[x=6.4,y=8.2]{d}/' *.tex
sed -i 's/Vertex.*{e}/Vertex[x=0  ,y=0  ]{e}/' *.tex
sed -i 's/Vertex.*{f}/Vertex[x=3.2,y=0  ]{f}/' *.tex
sed -i 's/Vertex.*{g}/Vertex[x=1.6,y=3.2]{g}/' *.tex
sed -i 's/Vertex.*{h}/Vertex[x=1.6,y=1.2]{h}/' *.tex
sed -i 's/Vertex.*{i}/Vertex[x=6.4,y=0  ]{i}/' *.tex
sed -i 's/Vertex.*{j}/Vertex[x=8  ,y=3.2]{j}/' *.tex
sed -i 's/Vertex.*{k}/Vertex[x=9.6,y=0  ]{k}/' *.tex
sed -i 's/Vertex.*{l}/Vertex[x=8  ,y=1.2]{l}/' *.tex
