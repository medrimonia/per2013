%.pdf: %.tex
	pdflatex $< && pdflatex $< && pdflatex $<

REPORT_SOURCES=HeadPage.tex     \
               Introduction.tex \
               StateOfArt.tex   \
               Problematic.tex  \
               Work.tex         \
               Conclusion.tex

PRESENTATION_SOURCES=presentation.tex            \
                     presentationFirstPage.tex   \
                     presentationForm.tex

TIKZ_SOURCES=k-partition.tex      \
             k-connected.tex      \
             undirected-graph.tex

all: report.pdf presentation.pdf

report.pdf: report.tex bibliography.bib ${REPORT_SOURCES}
	pdflatex $< && bibtex report.aux && pdflatex $< && pdflatex $<

presentation.pdf: ${PRESENTATION_SOURCES} bibliography.bib ${TIKZ_SOURCES}
	pdflatex $< && bibtex presentation.aux && pdflatex $< && pdflatex $<

clean:
	rm *.aux *.snm *.toc *.log