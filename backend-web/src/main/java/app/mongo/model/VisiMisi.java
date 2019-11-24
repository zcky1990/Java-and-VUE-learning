package app.mongo.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import app.model.request.VisiMisiRequest;

public class VisiMisi {
	@Id
	private ObjectId _id;
	
	@DBRef
	private Users author;
	
	private String visi;
	private String misi;
	private String modified_date;
	private Boolean visiMisiPublishStatus;
	
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
	public String getModified_date() {
		return modified_date;
	}
	public void setModified_date(String modified_date) {
		this.modified_date = modified_date;
	}
	
	public void fromObject(VisiMisiRequest request) {
		if(request.getId() != null) {
			this.set_id(new ObjectId(request.getId()));
		}
		this.setVisi(request.getVisi());
		this.setMisi(request.getMisi());
		this.setVisiMisiPublishStatus(request.getVisiMisiPublishStatus());
	}
}
