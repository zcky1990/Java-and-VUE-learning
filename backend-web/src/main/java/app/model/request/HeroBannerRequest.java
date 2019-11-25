package app.model.request;

public class HeroBannerRequest {
	private String id;
	private String title;
	private String subtitle;
	private String url_image;
	private Boolean isPublished;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public String getUrl_image() {
		return url_image;
	}
	public void setUrl_image(String url_image) {
		this.url_image = url_image;
	}
	public Boolean getIsPublished() {
		return isPublished;
	}
	public void setIsPublished(Boolean isPublished) {
		this.isPublished = isPublished;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
