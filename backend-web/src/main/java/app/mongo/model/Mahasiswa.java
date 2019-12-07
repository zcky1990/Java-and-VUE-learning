package app.mongo.model;

import java.time.LocalDate;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

import app.model.request.MahasiswaRequest;

public class Mahasiswa {
	@Id
	private ObjectId _id;
	private String idNumber;
	private String fullName;
	private String email;
	private String placeOfBirth;
	private String birthday;
	private String address;
	private String religion;
	private String status;
	private String phoneNumber;
	private String modifiedDate;
	private String gender;
	private String city;
	private String subDistrict;
	private String district;
	private String zipcode;
	private School sekolah;
	private boolean isValidated;
	@Indexed
	@CreatedDate
    private LocalDate createdDate;
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
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getPlaceOfBirth() {
		return placeOfBirth;
	}
	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}
	public String getReligion() {
		return religion;
	}
	public void setReligion(String religion) {
		this.religion = religion;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getSubDistrict() {
		return subDistrict;
	}
	public void setSubDistrict(String subDistrict) {
		this.subDistrict = subDistrict;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public boolean isValidated() {
		return isValidated;
	}
	public void setValidated(boolean isValidated) {
		this.isValidated = isValidated;
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Prodi getProdi() {
		return prodi;
	}
	public void setProdi(Prodi prodi) {
		this.prodi = prodi;
	}
	public void fromObject(MahasiswaRequest request) {
		if(request.getId() != null) {
			this.set_id(new ObjectId(request.getId()));
		}
		this.setIdNumber(request.getIdNumber());
		this.setEmail(request.getEmail());
		this.setFullName(request.getFullName());
		this.setAddress(request.getAddress());
		this.setPlaceOfBirth(request.getPlaceOfBirth());
		this.setBirthday(request.getBirthday());
		this.setGender(request.getGender());
		this.setCity(request.getCity());
		this.setDistrict(request.getDistrict());
		this.setSubDistrict(request.getSubDistrict());
		this.setReligion(request.getReligion());
		this.setPhoneNumber(request.getPhoneNumber());
		if(request.getProdi() != null) {
			Prodi newProdi = new Prodi();
			newProdi.fromObject(request.getProdi());
			this.setProdi(newProdi);
		}
		this.setStatus(request.getStatus());
		this.setZipcode(request.getZipcode());
		this.setSekolah(request.getSekolah());
	}
}
