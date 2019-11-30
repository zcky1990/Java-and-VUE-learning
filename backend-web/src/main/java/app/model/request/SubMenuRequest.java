package app.model.request;

public class SubMenuRequest {
	private String id;
	private String name;
	private PagesRequest page;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public PagesRequest getPage() {
		return page;
	}
	public void setPage(PagesRequest page) {
		this.page = page;
	}
}
