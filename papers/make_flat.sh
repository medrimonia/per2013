mkdir -p flat
cp report.tex flat
cp report/*.tex flat
cp resources/* flat
cp tkz-graph.sty flat
cp graphTikZ/*.tex flat
cp graphTikZ/inputOutput/*.tex flat
cp graphTikZ/failedK2/*.tex flat
cp bibliography.bib flat

sed -si "s|report/||g" flat/*.tex
sed -si "s|graphTikZ/||g" flat/*.tex
sed -si "s|inputOutput/||g" flat/*.tex
sed -si "s|failedK2/||g" flat/*.tex
