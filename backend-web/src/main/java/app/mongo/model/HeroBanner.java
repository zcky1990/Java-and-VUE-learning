package app.mongo.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import app.model.request.HeroBannerRequest;

public class HeroBanner {
	@Id
	private ObjectId _id;
	
	private String title;
	private String subtitle;
	private String url_image;
	private Boolean isPublished;
	
	public String getStringId() {
		return _id.toHexString();
	}
	public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
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
	public void fromObject(HeroBannerRequest request) {
		if(request.getId() != null) {
			this.set_id(new ObjectId(request.getId()));
		}
		this.setTitle(request.getTitle());
		this.setSubtitle(request.getSubtitle());
		this.setUrl_image(request.getUrl_image());
		this.setIsPublished(request.getIsPublished());
	}
}
