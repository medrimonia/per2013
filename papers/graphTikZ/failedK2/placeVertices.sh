#!/bin/bash
sed -i 's/Vertex.*{a}/Vertex[x= 2,y=0]{a}/' *.tex
sed -i 's/Vertex.*{b}/Vertex[x= 2,y=4]{b}/' *.tex
sed -i 's/Vertex.*{c}/Vertex[x= 4,y=2]{c}/' *.tex
sed -i 's/Vertex.*{d}/Vertex[x= 0,y=2]{d}/' *.tex
sed -i 's/Vertex.*{e}/Vertex[x= 6,y=2]{e}/' *.tex
sed -i 's/Vertex.*{f}/Vertex[x= 8,y=0]{f}/' *.tex
sed -i 's/Vertex.*{g}/Vertex[x= 8,y=4]{g}/' *.tex
sed -i 's/Vertex.*{h}/Vertex[x=10,y=2]{h}/' *.tex
sed -i 's/Vertex.*{i}/Vertex[x=14,y=0]{i}/' *.tex
sed -i 's/Vertex.*{j}/Vertex[x=14,y=4]{j}/' *.tex
sed -i 's/Vertex.*{k}/Vertex[x=12,y=2]{k}/' *.tex
sed -i 's/Vertex.*{l}/Vertex[x=16,y=2]{l}/' *.tex
