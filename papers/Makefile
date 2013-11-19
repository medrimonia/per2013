%.pdf: %.tex
	pdflatex $< && pdflatex $< && pdflatex $<

REPORT_SOURCES=HeadPage.tex     \
               Introduction.tex \
               StateOfArt.tex   \
               Problematic.tex  \
               Work.tex         \
               Conclusion.tex

all: report.pdf presentation.pdf

report.pdf: ${REPORT_SOURCES}

presentation.pdf: presentationFirstPage.tex presentationForm.tex

clean:
	rm *.aux *.snm *.toc *.log