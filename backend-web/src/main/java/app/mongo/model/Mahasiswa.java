package app.mongo.model;

import java.util.Date;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import app.model.request.MahasiswaRequest;

public class Mahasiswa {
	@Id
	private ObjectId _id;
	private String fullName;
	private String email;
	private String birthday;
	private String address;
	private String phonenumber;
	private String modified_date;
	private String sex;
	private String city;
	private String zipcode;
	private boolean status;
	private School sekolah;
	private String graduationYear;
	private Date submitAt;
	@DBRef
	private Prodi prodi;
	
	public String getStringId() {
		return _id.toHexString();
	}
	public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getModified_date() {
		return modified_date;
	}
	public void setModified_date(String modified_date) {
		this.modified_date = modified_date;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
		public School getSekolah() {
		return sekolah;
	}
	public void setSekolah(School sekolah) {
		this.sekolah = sekolah;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getGraduationYear() {
		return graduationYear;
	}
	public void setGraduationYear(String graduationYear) {
		this.graduationYear = graduationYear;
	}
	public Prodi getProdi() {
		return prodi;
	}
	public void setProdi(Prodi prodi) {
		this.prodi = prodi;
	}
	public Date getSubmitAt() {
		return submitAt;
	}
	public void setSubmitAt(Date submitAt) {
		this.submitAt = submitAt;
	}
	public void fromObject(MahasiswaRequest request) {
		if(request.getId() != null) {
			this.set_id(new ObjectId(request.getId()));
		}
		this.setEmail(request.getEmail());
		this.setFullName(request.getFullName());
		this.setAddress(request.getAddress());
		this.setBirthday(request.getBirthday());
		this.setSex(request.getSex());
		this.setGraduationYear(request.getGraduationYear());
		this.setCity(request.getCity());
		this.setPhonenumber(request.getPhonenumber());
		if(request.getProdi() != null) {
			Prodi newProdi = new Prodi();
			newProdi.fromObject(request.getProdi());
			this.setProdi(newProdi);
		}
		this.setZipcode(request.getZipcode());
		this.setSekolah(request.getSekolah());
	}
}
