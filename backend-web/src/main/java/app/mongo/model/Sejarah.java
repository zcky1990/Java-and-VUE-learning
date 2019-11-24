package app.mongo.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import app.model.request.SejarahRequest;

public class Sejarah {
	@Id
	private ObjectId _id;
	
	@DBRef
	private Users author;
	
	private String sejarah_content;
	private String modified_date;
	private Boolean sejarahPublishStatus;
	
	public String getStringId() {
		return _id.toHexString();
	}
	public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	public Users getAuthor() {
		return author;
	}
	public void setAuthor(Users author) {
		this.author = author;
	}
	public String getSejarah_content() {
		return sejarah_content;
	}
	public void setSejarah_content(String sejarah_content) {
		this.sejarah_content = sejarah_content;
	}
	public Boolean getSejarahPublishStatus() {
		return sejarahPublishStatus;
	}
	public void setSejarahPublishStatus(Boolean sejarahPublishStatus) {
		this.sejarahPublishStatus = sejarahPublishStatus;
	}
	public String getModified_date() {
		return modified_date;
	}
	public void setModified_date(String modified_date) {
		this.modified_date = modified_date;
	}
	
	public void fromObject(SejarahRequest request) {
		if(request.getId() != null) {
			this.set_id(new ObjectId(request.getId()));
		}
		this.setSejarah_content(request.getSejarah_content());
		this.setSejarahPublishStatus(request.getSejarahPublishStatus());
	}
}
