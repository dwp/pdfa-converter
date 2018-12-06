package uk.gov.gsi.childmaintenance.futurescheme.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)


@XmlRootElement(name="SystemArchiving", namespace="urn:Archive.Namespace")
public class SystemArchiving {

    @XmlElement(name = "System")
    protected String system;
    @XmlElement(name = "Title")
    protected String title;
    @XmlElement(name = "Forenames")
    protected String forenames;
    @XmlElement(name = "Surname")
    protected String surname;
    @XmlElement(name = "DateOfBirth")
    protected String dateOfBirth;
    @XmlElement(name = "ArchiveFor")
    protected String archiveFor;
    @XmlElement(name = "PayableFromNINO")
    protected String payableFromNINO;
    @XmlElement(name = "PayableToNINO")
    protected String payableToNINO;
    @XmlElement(name = "Address1")
    protected String address1;
    @XmlElement(name = "Address2")
    protected String address2;
    @XmlElement(name = "Address3")
    protected String address3;
    @XmlElement(name = "Address4")
    protected String address4;
    @XmlElement(name = "Address5")
    protected String address5;
    @XmlElement(name = "Postcode")
    protected String postcode;
    @XmlElement(name = "Personid")
    protected String personid;
    @XmlElement(name = "NatSens")
    protected String natSens;
    @XmlElement(name = "ClosureDate")
    protected String closureDate;
    @XmlElement(name = "SequenceNo")
    protected String sequenceNo;
    @XmlElement(name = "Sex")
    protected String sex;
    @XmlAttribute(name = "id")
    protected String id;

    /**
     * Gets the value of the system property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSystem() {
        return system;
    }

    /**
     * Sets the value of the system property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSystem(String value) {
        this.system = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the forenames property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getForenames() {
        return forenames;
    }

    /**
     * Sets the value of the forenames property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setForenames(String value) {
        this.forenames = value;
    }

    /**
     * Gets the value of the surname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the value of the surname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSurname(String value) {
        this.surname = value;
    }

    /**
     * Gets the value of the dateOfBirth property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the value of the dateOfBirth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateOfBirth(String value) {
        this.dateOfBirth = value;
    }

    /**
     * Gets the value of the archiveFor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArchiveFor() {
        return archiveFor;
    }

    /**
     * Sets the value of the archiveFor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArchiveFor(String value) {
        this.archiveFor = value;
    }

    /**
     * Gets the value of the payableFromNINO property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayableFromNINO() {
        return payableFromNINO;
    }

    /**
     * Sets the value of the payableFromNINO property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayableFromNINO(String value) {
        this.payableFromNINO = value;
    }

    /**
     * Gets the value of the payableToNINO property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayableToNINO() {
        return payableToNINO;
    }

    /**
     * Sets the value of the payableToNINO property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayableToNINO(String value) {
        this.payableToNINO = value;
    }

    /**
     * Gets the value of the address1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * Sets the value of the address1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddress1(String value) {
        this.address1 = value;
    }

    /**
     * Gets the value of the address2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * Sets the value of the address2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddress2(String value) {
        this.address2 = value;
    }

    /**
     * Gets the value of the address3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddress3() {
        return address3;
    }

    /**
     * Sets the value of the address3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddress3(String value) {
        this.address3 = value;
    }

    /**
     * Gets the value of the address4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddress4() {
        return address4;
    }

    /**
     * Sets the value of the address4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddress4(String value) {
        this.address4 = value;
    }

    /**
     * Gets the value of the address5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddress5() {
        return address5;
    }

    /**
     * Sets the value of the address5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddress5(String value) {
        this.address5 = value;
    }

    /**
     * Gets the value of the postcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * Sets the value of the postcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostcode(String value) {
        this.postcode = value;
    }

    /**
     * Gets the value of the personid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersonid() {
        return personid;
    }

    /**
     * Sets the value of the personid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersonid(String value) {
        this.personid = value;
    }

    /**
     * Gets the value of the natSens property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNatSens() {
        return natSens;
    }

    /**
     * Sets the value of the natSens property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNatSens(String value) {
        this.natSens = value;
    }

    /**
     * Gets the value of the closureDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClosureDate() {
        return closureDate;
    }

    /**
     * Sets the value of the closureDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClosureDate(String value) {
        this.closureDate = value;
    }

    /**
     * Gets the value of the sequenceNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSequenceNo() {
        return sequenceNo;
    }

    /**
     * Sets the value of the sequenceNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSequenceNo(String value) {
        this.sequenceNo = value;
    }

    /**
     * Gets the value of the sex property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSex() {
        return sex;
    }

    /**
     * Sets the value of the sex property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSex(String value) {
        this.sex = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

	@Override
	public String toString() {
		return "SystemArchiving [system=" + system + ", title=" + title + ", forenames=" + forenames + ", surname="
				+ surname + ", dateOfBirth=" + dateOfBirth + ", archiveFor=" + archiveFor + ", payableFromNINO="
				+ payableFromNINO + ", payableToNINO=" + payableToNINO + ", address1=" + address1 + ", address2="
				+ address2 + ", address3=" + address3 + ", address4=" + address4 + ", address5=" + address5
				+ ", postcode=" + postcode + ", personid=" + personid + ", natSens=" + natSens + ", closureDate="
				+ closureDate + ", sequenceNo=" + sequenceNo + ", sex=" + sex + ", id=" + id + "]";
	}

    
}
