package uk.gov.gsi.childmaintenance.futurescheme;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import uk.gov.gsi.childmaintenance.futurescheme.logger.LALogging;
import uk.gov.gsi.childmaintenance.futurescheme.logger.SASMILogging;
import uk.gov.gsi.childmaintenance.futurescheme.models.FileNames;
import uk.gov.gsi.childmaintenance.futurescheme.models.SystemArchiving;

/**
 * The Class TextToPdf implements a runnable to convert Text files to PDF/A.
 */
class TextToPdf implements Runnable {
	
	private String source;
	public TextToPdf(String source) {
		this.source = source;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		FileNames fileNames = new FileNames(source);
		SystemArchiving systemArchiving = new SystemArchiving();
		try(FileReader fr = new FileReader(PdfConversion.filePaths.getMetaSource().concat(fileNames.getMetaName()));) {
			JAXBContext jaxbContext = JAXBContext.newInstance(SystemArchiving.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			
			systemArchiving = (SystemArchiving) jaxbUnmarshaller
					.unmarshal(fr);
		} catch (FileNotFoundException e) {
				SASMILogging.info(Utils.getCurrentDateTime()+","+fileNames.getMetaName()+","+fileNames.getExtName()+","+systemArchiving.getArchiveFor()+","+systemArchiving.getPersonid()+",PDF Conversion,20,META File not found");
				Utils.failFileMovement(fileNames);
				throw new LAUtilityException("META File not found " + fileNames.getMetaName());
		} catch (IOException e) {
			SASMILogging.info(Utils.getCurrentDateTime()+","+fileNames.getMetaName()+","+fileNames.getExtName()+","+systemArchiving.getArchiveFor()+","+systemArchiving.getPersonid()+",PDF Conversion,20,IO Exception reading META file");
			Utils.failFileMovement(fileNames);
			throw new LAUtilityException("META File not found " + fileNames.getMetaName());
		} catch (JAXBException e) {
			SASMILogging.info(Utils.getCurrentDateTime()+","+fileNames.getMetaName()+","+fileNames.getExtName()+","+systemArchiving.getArchiveFor()+","+systemArchiving.getPersonid()+",PDF Conversion,20,META File can not be unmarshalled");
			Utils.failFileMovement(fileNames);
			throw new LAUtilityException("IO Exception reading META file " + fileNames.getMetaName());
		} 
		LALogging.info("CorrelationId="+systemArchiving.getPersonid()+", Source=ESB, Target=ESB, Direction=source_request, Interface=PDF Conversion");
		Document document = new Document(PageSize.A4.rotate(), 10f, 10f, 10f, 10f);
		try (BufferedReader br = new BufferedReader(
				new FileReader(PdfConversion.filePaths.getExtSource().concat(fileNames.getExtName())))) {
			PdfWriter.getInstance(document, new FileOutputStream(PdfConversion.filePaths.getPdfOutput().concat(fileNames.getTmpName())));

			document.open();

			String strline;
			Font font = FontFactory.getFont(PdfConversion.filePaths.getTextFont(), BaseFont.WINANSI, BaseFont.EMBEDDED, 10);
			assert br != null;
			while ((strline = br.readLine()) != null) {
				Paragraph para = new Paragraph(strline + "\n", font);
				document.add(para);
			}
			document.close();
		} catch (IOException e) {
			SASMILogging.info(Utils.getCurrentDateTime()+","+fileNames.getMetaName()+","+fileNames.getExtName()+","+systemArchiving.getArchiveFor()+","+systemArchiving.getPersonid()+",PDF Conversion,20,IO Exception reading EXT file");
			Utils.failFileMovement(fileNames);
			throw new LAUtilityException("IO Exception reading EXT file " + fileNames.getMetaName());
		} catch (DocumentException e) {
			SASMILogging.info(Utils.getCurrentDateTime()+","+fileNames.getMetaName()+","+fileNames.getExtName()+","+systemArchiving.getArchiveFor()+","+systemArchiving.getPersonid()+",PDF Conversion,24,Conversion to pdf file failed");
			Utils.failFileMovement(fileNames);
			throw new LAUtilityException("Conversion to pdf file failed for " + fileNames.getExtName());
		} finally {
			if (document.isOpen()) {
				document.close();
			}
		}

		try {
			Utils.pdfToPdfa(PdfConversion.filePaths.getPdfOutput().concat(fileNames.getTmpName()),
					PdfConversion.filePaths.getExtSource().concat(fileNames.getPdfName()),true);
		} catch (IOException | DocumentException e) {
			SASMILogging.info(Utils.getCurrentDateTime()+","+fileNames.getMetaName()+","+fileNames.getExtName()+","+systemArchiving.getArchiveFor()+","+systemArchiving.getPersonid()+",PDF Conversion,24,Conversion from Pdf to Pdfa failed");
			Utils.failFileMovement(fileNames);
			throw new LAUtilityException("Conversion from Pdf to Pdfa failed " + fileNames.getExtName());
		}
		try {
			Utils.successFileMovement(fileNames);
		} catch (IOException e) {
			SASMILogging.info(Utils.getCurrentDateTime()+","+fileNames.getMetaName()+","+fileNames.getExtName()+","+systemArchiving.getArchiveFor()+","+systemArchiving.getPersonid()+",PDF Conversion,20,Success scenario file movement failed");
			Utils.failFileMovement(fileNames);
			throw new LAUtilityException("Success scenario file movement failed " + fileNames.getExtName());
		}
		LALogging.info("CorrelationId="+systemArchiving.getPersonid()+", Source=ESB, Target=ESB, Direction=final_response, Interface=PDF Conversion");
	}

}
