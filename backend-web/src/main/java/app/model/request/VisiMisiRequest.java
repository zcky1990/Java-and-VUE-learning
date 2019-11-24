package app.model.request;

public class VisiMisiRequest {
	private String id;
	private String visi;
	private String misi;
	private String modified_date;
	private Boolean visiMisiPublishStatus;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getModified_date() {
		return modified_date;
	}
	public void setModified_date(String modified_date) {
		this.modified_date = modified_date;
	}
	public String getVisi() {
		return visi;
	}
	public void setVisi(String visi) {
		this.visi = visi;
	}
	public String getMisi() {
		return misi;
	}
	public void setMisi(String misi) {
		this.misi = misi;
	}
	public Boolean getVisiMisiPublishStatus() {
		return visiMisiPublishStatus;
	}
	public void setVisiMisiPublishStatus(Boolean visiMisiPublishStatus) {
		this.visiMisiPublishStatus = visiMisiPublishStatus;
	}
}
