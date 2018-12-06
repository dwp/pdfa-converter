package uk.gov.gsi.childmaintenance.futurescheme;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.text.DocumentException;

import uk.gov.gsi.childmaintenance.futurescheme.logger.LALogging;
import uk.gov.gsi.childmaintenance.futurescheme.logger.SASMILogging;
import uk.gov.gsi.childmaintenance.futurescheme.models.FileNames;
import uk.gov.gsi.childmaintenance.futurescheme.models.SystemArchiving;

/**
 * The Class HtmlToPdf implements a runnable to convert HTML files to PDF/A.
 */
class HtmlToPdf implements Runnable {

	private String source;

	public HtmlToPdf(String source) {
		this.source = source;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		FileNames fileNames = new FileNames(source);
		SystemArchiving systemArchiving = new SystemArchiving();
		try (FileReader fr = new FileReader(PdfConversion.filePaths.getMetaSource().concat(fileNames.getMetaName()));) {
			JAXBContext jaxbContext = JAXBContext.newInstance(SystemArchiving.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			systemArchiving = (SystemArchiving) jaxbUnmarshaller.unmarshal(fr);
		} catch (FileNotFoundException e) {
			SASMILogging.info(Utils.getCurrentDateTime() + "," + fileNames.getMetaName() + "," + fileNames.getExtName()
					+ "," + systemArchiving.getArchiveFor() + "," + systemArchiving.getPersonid()
					+ ",PDF Conversion,20,META File not found");
			Utils.failFileMovement(fileNames);
			throw new LAUtilityException("META File not found " + fileNames.getMetaName());
		} catch (IOException e) {
			SASMILogging.info(Utils.getCurrentDateTime() + "," + fileNames.getMetaName() + "," + fileNames.getExtName()
					+ "," + systemArchiving.getArchiveFor() + "," + systemArchiving.getPersonid()
					+ ",PDF Conversion,20,IO Exception reading META file");
			Utils.failFileMovement(fileNames);
			throw new LAUtilityException("META File not found " + fileNames.getMetaName());
		} catch (JAXBException e) {
			SASMILogging.info(Utils.getCurrentDateTime() + "," + fileNames.getMetaName() + "," + fileNames.getExtName()
					+ "," + systemArchiving.getArchiveFor() + "," + systemArchiving.getPersonid()
					+ ",PDF Conversion,20,META File can not be unmarshalled");
			Utils.failFileMovement(fileNames);
			throw new LAUtilityException("IO Exception reading META file " + fileNames.getMetaName());
		}
		LALogging.info("CorrelationId=" + systemArchiving.getPersonid()
				+ ", Source=ESB, Target=ESB, Direction=source_request, Interface=PDF Conversion");
		File htmlSourceFile = new File(PdfConversion.filePaths.getExtSource().concat(fileNames.getExtName()));

		FontProvider fontProvider = new FontProvider();
		fontProvider.addFont(PdfConversion.filePaths.getHtmlFont());

		File pdfDest = new File(PdfConversion.filePaths.getPdfOutput().concat(fileNames.getTmpName()));
		ConverterProperties converterProperties = new ConverterProperties();
		converterProperties.setFontProvider(fontProvider);
		converterProperties.setCharset("ISO-8859-1");
		try (FileInputStream fiS = new FileInputStream(htmlSourceFile);
				FileOutputStream foS = new FileOutputStream(pdfDest);) {
			HtmlConverter.convertToPdf(fiS, foS, converterProperties);
		} catch (IOException e) {
			SASMILogging.info(Utils.getCurrentDateTime() + "," + fileNames.getMetaName() + "," + fileNames.getExtName()
					+ "," + systemArchiving.getArchiveFor() + "," + systemArchiving.getPersonid() + ",PDF Conversion,24,Conversion to pdf file failed");
			Utils.failFileMovement(fileNames);
			throw new LAUtilityException("Conversion to pdf file failed for " + fileNames.getExtName());
		}
		try {
			Utils.pdfToPdfa(PdfConversion.filePaths.getPdfOutput().concat(fileNames.getTmpName()),
					PdfConversion.filePaths.getExtSource().concat(fileNames.getPdfName()), false);
		} catch (DocumentException | IOException e) {
			SASMILogging.info(Utils.getCurrentDateTime() + "," + fileNames.getMetaName() + "," + fileNames.getExtName()
					+ "," + systemArchiving.getArchiveFor() + "," + systemArchiving.getPersonid() + ",PDF Conversion,24,Conversion from Pdf to Pdfa failed");
			Utils.failFileMovement(fileNames);
			throw new LAUtilityException("Conversion from Pdf to Pdfa failed " + fileNames.getExtName());
		}
		try {
			Utils.successFileMovement(fileNames);
		} catch (IOException e) {
			SASMILogging.info(Utils.getCurrentDateTime() + "," + fileNames.getMetaName() + "," + fileNames.getExtName()
					+ "," + systemArchiving.getArchiveFor() + "," + systemArchiving.getPersonid() + ",PDF Conversion,20,Success scenario file movement failed");
			Utils.failFileMovement(fileNames);
			throw new LAUtilityException("Success scenario file movement failed " + fileNames.getExtName());
		}
		LALogging.info("CorrelationId=" + systemArchiving.getPersonid()
				+ ", Source=ESB, Target=ESB, Direction=final_response, Interface=PDF Conversion");
	}

}
