%.pdf: %.tex
	pdflatex $< && pdflatex $< && pdflatex $<

REPORT_SOURCES=HeadPage.tex     \
               Introduction.tex \
               StateOfArt.tex   \
               Problematic.tex  \
               Work.tex         \
               Conclusion.tex

all: report.pdf presentation.pdf

report.pdf: report.tex bibliography.bib ${REPORT_SOURCES}
	pdflatex $< && bibtex report.aux && pdflatex $< && pdflatex $<

presentation.pdf: presentationFirstPage.tex presentationForm.tex

clean:
	rm *.aux *.snm *.toc *.log