package uk.gov.gsi.childmaintenance.futurescheme;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Document;

/**
 * The Class Rotate is used for rotating pages in a PDF file.
 */
class Rotate extends PdfPageEventHelper{
	
	/* (non-Javadoc)
	 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onStartPage(com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
	 */
	@Override
    public void onStartPage(PdfWriter writer, Document document) {
        writer.addPageDictEntry(PdfName.ROTATE, PdfPage.LANDSCAPE);
    }
}