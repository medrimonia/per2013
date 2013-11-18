SUBFILES=HeadPage.tex     \
         Introduction.tex \
         StateOfArt.tex   \
         Problematic.tex  \
         Work.tex         \
         Conclusion.tex

%.pdf: %.tex
	pdflatex $< && pdflatex $< && pdflatex $<

report.pdf: ${SUBFILES}

clean:
	rm *.aux *.snm *.toc *.log