package app.model.request;

public class SejarahRequest {
	private String id;
	private String sejarah_content;
	private String modified_date;
	private Boolean sejarahPublishStatus;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSejarah_content() {
		return sejarah_content;
	}
	public void setSejarah_content(String sejarah_content) {
		this.sejarah_content = sejarah_content;
	}
	public String getModified_date() {
		return modified_date;
	}
	public void setModified_date(String modified_date) {
		this.modified_date = modified_date;
	}
	public Boolean getSejarahPublishStatus() {
		return sejarahPublishStatus;
	}
	public void setSejarahPublishStatus(Boolean sejarahPublishStatus) {
		this.sejarahPublishStatus = sejarahPublishStatus;
	}	
}
