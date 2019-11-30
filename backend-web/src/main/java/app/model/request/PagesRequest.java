package app.model.request;


public class PagesRequest {
	private String id;
	private String domain;
	private String subdomain;
	private String subsubdomain;
	private String permalink;
	private String content;
	private Boolean isPublish;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
}
