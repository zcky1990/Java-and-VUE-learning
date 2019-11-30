package app.mongo.model;


import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import app.model.request.PagesRequest;

public class Pages {
	@Id
	private ObjectId _id;
	private String domain;
	private String subdomain;
	private String subsubdomain;
	@Indexed
	private String permalink;
	private String content;
	private Boolean isPublish;
	
	public String getStringId() {
		return _id.toHexString();
	}
	public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getSubdomain() {
		return subdomain;
	}
	public void setSubdomain(String subdomain) {
		this.subdomain = subdomain;
	}
	public String getSubsubdomain() {
		return subsubdomain;
	}
	public void setSubsubdomain(String subsubdomain) {
		this.subsubdomain = subsubdomain;
	}
	public String getPermalink() {
		return permalink;
	}
	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Boolean getIsPublish() {
		return isPublish;
	}
	public void setIsPublish(Boolean isPublish) {
		this.isPublish = isPublish;
	}
	public void fromObject(PagesRequest request) {
		if(request.getId() != null) {
			this.set_id(new ObjectId(request.getId()));
		}
		this.setDomain(request.getDomain());
		this.setSubdomain(request.getSubdomain());
		this.setSubsubdomain(request.getSubsubdomain());
		this.setPermalink(request.getPermalink());
		this.setContent(request.getContent());
		this.setIsPublish(request.getIsPublish());
	}
}
