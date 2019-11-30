package app.model.request;

import java.util.List;

public class MenuRequest {
	private String id;
	private String name;
	private PagesRequest page;
	private List<SubMenuRequest> submenu;
	private String menuType;
	
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
	public List<SubMenuRequest> getSubmenu() {
		return submenu;
	}
	public void setSubmenu(List<SubMenuRequest> submenu) {
		this.submenu = submenu;
	}
	public String getMenuType() {
		return menuType;
	}
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}	
	
}
