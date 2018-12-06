package uk.gov.gsi.childmaintenance.futurescheme.models;

import java.io.File;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;

public class FilePaths {

	private String textFont;
	private String htmlFont;
	private String pdfOutput;
	private String extSource;
	private String metaSource;
	private String metaOutput;
	private String pdfaOutput;
	private String logFile;
	private String sasLog;
	private String failed;

	private File file;
	private Path metaSourcePath;
	private Path extSourcePath;
	private Path metaOutputPath;
	private Path pdfaOutputPath;
	private Path failedPath;
	private Path pdfOutputPath;
	private File metaSourceFolder;
	private ExecutorService executor;

	public FilePaths() {
		super();
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Path getMetaSourcePath() {
		return metaSourcePath;
	}

	public void setMetaSourcePath(Path metaSourcePath) {
		this.metaSourcePath = metaSourcePath;
	}

	public Path getExtSourcePath() {
		return extSourcePath;
	}

	public void setExtSourcePath(Path extSourcePath) {
		this.extSourcePath = extSourcePath;
	}

	public Path getMetaOutputPath() {
		return metaOutputPath;
	}

	public void setMetaOutputPath(Path metaOutputPath) {
		this.metaOutputPath = metaOutputPath;
	}

	public Path getPdfaOutputPath() {
		return pdfaOutputPath;
	}

	public void setPdfaOutputPath(Path pdfaOutputPath) {
		this.pdfaOutputPath = pdfaOutputPath;
	}

	public Path getFailedPath() {
		return failedPath;
	}

	public void setFailedPath(Path failedPath) {
		this.failedPath = failedPath;
	}

	public Path getPdfOutputPath() {
		return pdfOutputPath;
	}

	public void setPdfOutputPath(Path pdfOutputPath) {
		this.pdfOutputPath = pdfOutputPath;
	}

	public File getMetaSourceFolder() {
		return metaSourceFolder;
	}

	public void setMetaSourceFolder(File metaSourceFolder) {
		this.metaSourceFolder = metaSourceFolder;
	}

	public ExecutorService getExecutor() {
		return executor;
	}

	public void setExecutor(ExecutorService executor) {
		this.executor = executor;
	}

	public String getTextFont() {
		return textFont;
	}

	public void setTextFont(String textFont) {
		this.textFont = textFont;
	}

	public String getHtmlFont() {
		return htmlFont;
	}

	public void setHtmlFont(String htmlFont) {
		this.htmlFont = htmlFont;
	}

	public String getPdfOutput() {
		return pdfOutput;
	}

	public void setPdfOutput(String pdfOutput) {
		this.pdfOutput = pdfOutput;
	}

	public String getExtSource() {
		return extSource;
	}

	public void setExtSource(String extSource) {
		this.extSource = extSource;
	}

	public String getMetaSource() {
		return metaSource;
	}

	public void setMetaSource(String metaSource) {
		this.metaSource = metaSource;
	}

	public String getMetaOutput() {
		return metaOutput;
	}

	public void setMetaOutput(String metaOutput) {
		this.metaOutput = metaOutput;
	}

	public String getPdfaOutput() {
		return pdfaOutput;
	}

	public void setPdfaOutput(String pdfaOutput) {
		this.pdfaOutput = pdfaOutput;
	}

	public String getLogFile() {
		return logFile;
	}

	public void setLogFile(String logFile) {
		this.logFile = logFile;
	}

	public String getSasLog() {
		return sasLog;
	}

	public void setSasLog(String sasLog) {
		this.sasLog = sasLog;
	}

	public String getFailed() {
		return failed;
	}

	public void setFailed(String failed) {
		this.failed = failed;
	}

}
