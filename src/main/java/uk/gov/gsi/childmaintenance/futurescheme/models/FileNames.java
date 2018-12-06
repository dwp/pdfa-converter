package uk.gov.gsi.childmaintenance.futurescheme.models;

public class FileNames {
	private static final String META_EXTN = ".META";
	private String metaName;
	private String extName;
	private String pdfName;
	private String tmpName;
	public String getMetaName() {
		return metaName;
	}
	public void setMetaName(String metaName) {
		this.metaName = metaName;
	}
	public String getExtName() {
		return extName;
	}
	public void setExtName(String extName) {
		this.extName = extName;
	}
	public String getPdfName() {
		return pdfName;
	}
	public void setPdfName(String pdfName) {
		this.pdfName = pdfName;
	}
	public String getTmpName() {
		return tmpName;
	}
	public void setTmpName(String tmpName) {
		this.tmpName = tmpName;
	}
	public FileNames(String source) {
		this.metaName = source;
		this.extName = source.replace(META_EXTN, ".EXT");
		this.pdfName = source.replace(META_EXTN, ".PDF");
		this.tmpName = source.replace(META_EXTN, ".TMP");
	}
	
}
