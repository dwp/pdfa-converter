package uk.gov.gsi.childmaintenance.futurescheme;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;

import uk.gov.gsi.childmaintenance.futurescheme.logger.LALogging;
import uk.gov.gsi.childmaintenance.futurescheme.models.FileNames;
import uk.gov.gsi.childmaintenance.futurescheme.Rotate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

class Utils {

	private Utils() {
	}

	/**
	 * Register watch service.
	 *
	 * @return the watch service
	 */
	public static WatchService registerWatchService() {
		WatchService watchService = null;
		try {
			watchService = FileSystems.getDefault().newWatchService();
			PdfConversion.filePaths.getMetaSourcePath().register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
			LALogging.info("Watcher service registered for " + PdfConversion.filePaths.getMetaSource());
		} catch (IOException e) {
			LALogging.error("Watch Service Registration failed for path " + PdfConversion.filePaths.getMetaSource());
			LALogging.error("Exiting utility with code 1");
			Runtime.getRuntime().halt(1);
		}
		return watchService;

	}

	/**
	 * Run watch service.
	 *
	 * @param watchService
	 *            the watch service
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public static void runWatchService(final WatchService watchService) throws InterruptedException {
		WatchKey key;

		// Main process for file watcher events
		while ((key = watchService.take()) != null) {
			for (WatchEvent<?> event : key.pollEvents()) {

				if (event.context().toString().contains("CSCS")) {
					Runnable worker = new TextToPdf(event.context().toString());
					PdfConversion.filePaths.getExecutor().execute(worker);
				} else if (event.context().toString().contains("CS2")) {
					Runnable worker = new HtmlToPdf(event.context().toString());
					PdfConversion.filePaths.getExecutor().execute(worker);
				} else {
					LALogging.error(" Unrecognized system for META File " + event.context().toString());
				}
			}
			key.reset();
		}
	}

	/**
	 * Adds the shut down hook.
	 *
	 * @param watchService
	 *            the watch service
	 */
	public static void addShutDownHook(final WatchService watchService) {

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				try {
					if (watchService != null) {
						LALogging.info("Shutting down watcher service ");
						watchService.close();
					}

				} catch (IOException e) {
					LALogging.error("Error Shutting down watcher service");
				}
				PdfConversion.filePaths.getExecutor().shutdown();
				try {
					if (!PdfConversion.filePaths.getExecutor().awaitTermination(15, TimeUnit.MINUTES)) {
						LALogging.info("Hard Shutdown Initiated");
						Runtime.getRuntime().halt(0);
					} else {
						LALogging.info("Executor Shutting Down completed");
					}
				} catch (InterruptedException e) {
					LALogging.error("Shutdown thread interrupted");
					Thread.currentThread().interrupt();
					Runtime.getRuntime().halt(1);
				}

			}
		}, "Shutdown-thread"));
	}

	/**
	 * Process stock files.
	 */
	public static void processStockFiles() {
		String backLogFileName;
		LALogging.info(" Starting to process stock META files present in " + PdfConversion.filePaths.getMetaSource());
		for (File fileEntry : PdfConversion.filePaths.getMetaSourceFolder().listFiles()) {
			backLogFileName = fileEntry.getName();
			if (backLogFileName.contains("CSCS")) {
				Runnable worker = new TextToPdf(backLogFileName);
				PdfConversion.filePaths.getExecutor().execute(worker);
			} else if (backLogFileName.contains("CS2")) {
				Runnable worker = new HtmlToPdf(backLogFileName);
				PdfConversion.filePaths.getExecutor().execute(worker);
			} else {
				LALogging.error(" Unrecognized system for META File " + backLogFileName);

			}
		}

	}

	/**
	 * Gets the current date time.
	 *
	 * @return the current date time
	 */
	public static String getCurrentDateTime() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.sssZ");
		return sdf.format(cal.getTime());
	}

	/**
	 * Convert Pdf to pdfa.
	 *
	 * @param pdfFile
	 *            the pdf file
	 * @param dest
	 *            the dest
	 * @param isLandscape
	 *            the is landscape
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws DocumentException
	 *             the document exception
	 */
	public static void pdfToPdfa(String pdfFile, String dest, Boolean isLandscape) throws DocumentException, IOException {
		Document document = new Document(); 
		try(OutputStream pdfAOutputFile = new FileOutputStream(new File(dest));) {
			PdfAWriter writerPdfA = PdfAWriter.getInstance(document, pdfAOutputFile, PdfAConformanceLevel.PDF_A_1A);
			writerPdfA.setPdfVersion(PdfAWriter.VERSION_1_7);
			// writerPdfA.setLanguage(PdfAWriter.)
			writerPdfA.setFullCompression();
			writerPdfA.createXmpMetadata();
			writerPdfA.setTagged();
			if (isLandscape) {
				writerPdfA.setPageEvent(new Rotate());
			}
			document.open();
			PdfContentByte cb = writerPdfA.getDirectContent();
			PdfReader reader = new PdfReader(pdfFile);
			PdfImportedPage page;
			int pdfPageCount = reader.getNumberOfPages();
			int pdfLastPage;
			if (pdfPageCount < 8191) {
				pdfLastPage = pdfPageCount;
			} else {
				pdfLastPage = 8191;
			}

			for (int i = 0; i < pdfLastPage; i++) {
				document.newPage();
				page = writerPdfA.getImportedPage(reader, i + 1);
				page.setAccessibleAttribute(PdfName.ALT, new PdfString("Page" + i));

				cb.addTemplate(page, 0d, 0d);
			} 
			document.close();
		}finally {
			if (document.isOpen()) {
				document.close();
			}
		}
		
	}

	/**
	 * Success file movement.
	 *
	 * @param fileNames
	 *            the file names
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void successFileMovement(FileNames fileNames) throws IOException {
		Files.move(PdfConversion.filePaths.getExtSourcePath().resolve(fileNames.getPdfName()),
				PdfConversion.filePaths.getPdfaOutputPath().resolve(fileNames.getPdfName()), StandardCopyOption.REPLACE_EXISTING);
		Files.move(PdfConversion.filePaths.getMetaSourcePath().resolve(fileNames.getMetaName()),
				PdfConversion.filePaths.getMetaOutputPath().resolve(fileNames.getMetaName()), StandardCopyOption.REPLACE_EXISTING);
		Files.deleteIfExists(PdfConversion.filePaths.getExtSourcePath().resolve(fileNames.getExtName()));
		Files.deleteIfExists(PdfConversion.filePaths.getMetaSourcePath().resolve(fileNames.getTmpName()));
	}

	/**
	 * Fail file movement.
	 *
	 * @param fileNames
	 *            the file names
	 */
	public static void failFileMovement(FileNames fileNames) {
		try {
			Files.move(PdfConversion.filePaths.getMetaSourcePath().resolve(fileNames.getMetaName()),
					PdfConversion.filePaths.getFailedPath().resolve(fileNames.getMetaName()));
			Files.deleteIfExists(PdfConversion.filePaths.getPdfOutputPath().resolve(fileNames.getTmpName()));
			Files.deleteIfExists(PdfConversion.filePaths.getExtSourcePath().resolve(fileNames.getPdfName()));
		} catch (Exception e) {
			LALogging.error("Failure scenario file movement failed " + fileNames.getMetaName());
		}
	}

	/**
	 * Check number.
	 *
	 * @param arg1
	 *            the arg 1
	 */
	public static boolean checkNumber(String arg1) {
		try {
			Integer.parseInt(arg1);
			return true;
		} catch (NumberFormatException nfe) {
			LALogging.error("Thread count is not a number " + arg1);
			return false;
		}
	}


	public static void setProperties(Properties props) {
		PdfConversion.filePaths.setTextFont(props.getProperty("TEXT_FONT"));
		PdfConversion.filePaths.setHtmlFont(props.getProperty("HTML_FONT"));
		PdfConversion.filePaths.setPdfOutput(props.getProperty("PDF_OUTPUT"));
		PdfConversion.filePaths.setExtSource(props.getProperty("EXT_INPUT"));
		PdfConversion.filePaths.setMetaSource(props.getProperty("META_INPUT"));
		PdfConversion.filePaths.setMetaOutput(props.getProperty("META_OUTPUT"));
		PdfConversion.filePaths.setPdfaOutput(props.getProperty("PDFA_OUTPUT"));
		PdfConversion.filePaths.setLogFile(props.getProperty("LOG_FILE"));
		PdfConversion.filePaths.setSasLog(props.getProperty("SAS_LOG_FILE"));
		PdfConversion.filePaths.setFailed(props.getProperty("META_EXT_FAILED"));
		PdfConversion.filePaths.setExtSourcePath(Paths.get(PdfConversion.filePaths.getExtSource()));
		PdfConversion.filePaths.setFailedPath(Paths.get(PdfConversion.filePaths.getFailed()));
		PdfConversion.filePaths.setMetaOutputPath(Paths.get(PdfConversion.filePaths.getMetaOutput()));
		PdfConversion.filePaths.setMetaSourcePath(Paths.get(PdfConversion.filePaths.getMetaSource()));
		PdfConversion.filePaths.setPdfaOutputPath(Paths.get(PdfConversion.filePaths.getPdfaOutput()));
		PdfConversion.filePaths.setPdfOutputPath(Paths.get(PdfConversion.filePaths.getPdfOutput()));
		PdfConversion.filePaths.setMetaSourceFolder(new File(PdfConversion.filePaths.getMetaSource()));
	}
}
