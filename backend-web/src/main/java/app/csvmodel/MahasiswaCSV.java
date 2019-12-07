package app.csvmodel;

import app.mongo.model.Mahasiswa;

public class MahasiswaCSV {
	private String idNumber;
	private String fullName;
	private String email;
	private String placeOfBirth;
	private String birthday;
	private String address;
	private String religion;
	private String status;
	private String phoneNumber;
	private String gender;
	private String city;
	private String subDistrict;
	private String district;
	private String zipcode;
	private String schoolName;
	private String schoolAddress;
	private String schoolType;
	private String schoolMajor;
	private String graduationYear;
	private String facultyName;
	private String prodiName;
	private String strata;
	private String degree;
	
	public MahasiswaCSV(Mahasiswa mahasiswa) {
		this.idNumber = mahasiswa.getIdNumber();
		this.fullName = mahasiswa.getFullName();
		this.email = mahasiswa.getEmail();
		this.placeOfBirth = mahasiswa.getPlaceOfBirth();
		this.birthday = mahasiswa.getBirthday();
		this.address = mahasiswa.getAddress();
		this.religion = mahasiswa.getReligion();
		this.status = mahasiswa.getStatus();
		this.phoneNumber = mahasiswa.getPhoneNumber();
		this.gender = mahasiswa.getGender();
		this.city = mahasiswa.getCity();
		this.subDistrict = mahasiswa.getSubDistrict();
		this.district = mahasiswa.getDistrict();
		this.zipcode = mahasiswa.getZipcode();
		this.schoolName = mahasiswa.getSekolah().getSchoolName();
		this.schoolAddress = mahasiswa.getSekolah().getSchoolAddress();
		this.schoolType = mahasiswa.getSekolah().getSchoolType();
		this.schoolMajor = mahasiswa.getSekolah().getSchoolMajor();
		this.graduationYear = mahasiswa.getSekolah().getGraduationYear();
		this.facultyName = mahasiswa.getProdi().getFakultas().getFacultyName();
		this.prodiName = mahasiswa.getProdi().getProdiName();
		this.strata = mahasiswa.getProdi().getStrata();
		this.degree = mahasiswa.getProdi().getDegree();
	}
	
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
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
	public String getPlaceOfBirth() {
		return placeOfBirth;
	}
	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
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
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getSchoolAddress() {
		return schoolAddress;
	}
	public void setSchoolAddress(String schoolAddress) {
		this.schoolAddress = schoolAddress;
	}
	public String getSchoolType() {
		return schoolType;
	}
	public void setSchoolType(String schoolType) {
		this.schoolType = schoolType;
	}
	public String getSchoolMajor() {
		return schoolMajor;
	}
	public void setSchoolMajor(String schoolMajor) {
		this.schoolMajor = schoolMajor;
	}
	public String getGraduationYear() {
		return graduationYear;
	}
	public void setGraduationYear(String graduationYear) {
		this.graduationYear = graduationYear;
	}
	public String getFacultyName() {
		return facultyName;
	}
	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}
	public String getProdiName() {
		return prodiName;
	}
	public void setProdiName(String prodiName) {
		this.prodiName = prodiName;
	}
	public String getStrata() {
		return strata;
	}
	public void setStrata(String strata) {
		this.strata = strata;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	
	

}
