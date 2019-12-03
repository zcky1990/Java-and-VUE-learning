package app.model.request;

public class ProdiRequest {
	private String id;
	private FacultyRequest fakultas;
	private String prodiName;
	private String strata;
	private Boolean isPublished;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public FacultyRequest getFakultas() {
		return fakultas;
	}
	public void setFakultas(FacultyRequest fakultas) {
		this.fakultas = fakultas;
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
	public Boolean getIsPublished() {
		return isPublished;
	}
	public void setIsPublished(Boolean isPublished) {
		this.isPublished = isPublished;
	}
}
