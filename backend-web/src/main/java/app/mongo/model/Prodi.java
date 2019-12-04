package app.mongo.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import app.model.request.ProdiRequest;

public class Prodi {
	@Id
	private ObjectId _id;
	@DBRef
	private Faculty fakultas;
	private String prodiName;
	private String strata;
	private Boolean isPublished;
	private String degree;
	
	public String getStringId() {
		return _id.toHexString();
	}
	public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	public Faculty getFakultas() {
		return fakultas;
	}
	public void setFakultas(Faculty fakultas) {
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
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public void fromObject(ProdiRequest request) {
		if(request.getId() != null) {
			this.set_id(new ObjectId(request.getId()));
		}
		if(request.getFakultas() != null) {
			Faculty fakultas = new Faculty();
			fakultas.fromObject(request.getFakultas());
			this.setFakultas(fakultas);
		}
		this.setDegree(request.getDegree());
		this.setProdiName(request.getProdiName());
		this.setStrata(request.getStrata());
		this.setIsPublished(request.getIsPublished());
	}
}
