%.pdf: %.tex
	pdflatex $< && pdflatex $< && pdflatex $<

REPORT_SOURCES=$(wildcard report/*.tex)

PRESENTATION_SOURCES=$(wildcard presentation/*.tex)

TIKZ_SOURCES=$(wildcard graphTikZ/*.tex)
FAILEDK2_SOURCES=$(wildcard graphTikZ/failedK2/*.tex)

.PHONY: all clean           

all: report.pdf presentation.pdf

report.pdf: report.tex bibliography.bib ${REPORT_SOURCES} ${TIKZ_SOURCES} ${FAILEDK2_SOURCES}
	pdflatex $< && bibtex report.aux && pdflatex $< && pdflatex $<

presentation.pdf: presentation.tex bibliography.bib ${PRESENTATION_SOURCES} ${TIKZ_SOURCES}
	pdflatex $< && bibtex presentation.aux && pdflatex $< && pdflatex $<

clean:
	rm *.aux *.snm *.toc *.log *.pdf *.out *.blg *.bbl *.nav
