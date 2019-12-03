package app.mongo.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import app.model.request.FacultyRequest;

public class Faculty {
	@Indexed
	@Id
	private ObjectId _id;
	private String facultyName;
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
	public String getFacultyName() {
		return facultyName;
	}
	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}
	public Boolean getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(Boolean isPublished) {
		this.isPublished = isPublished;
	}

	public void fromObject(FacultyRequest request) {
		this.set_id(new ObjectId(request.getId()));
		this.setFacultyName(request.getFacultyName());
		this.setIsPublished(request.getIsPublished());
	}
}
