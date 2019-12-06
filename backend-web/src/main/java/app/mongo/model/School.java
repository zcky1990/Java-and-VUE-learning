package app.mongo.model;

public class School {
	private String schoolName;
	private String schoolAddress;
	private String schoolType;
	private String schoolMajor;
	
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
}
