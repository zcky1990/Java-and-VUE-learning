package app.mongo.model;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

import app.model.request.MenuRequest;

public class Menu {
	@Id
	private ObjectId _id;
	@Indexed
	private String name;
	@DBRef
	private List<SubMenu> submenu;
	private Pages page;
	private String menuType;
	
	public String getStringId() {
		return _id.toHexString();
	}
	public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<SubMenu> getSubmenu() {
		return submenu;
	}
	public void setSubmenu(List<SubMenu> submenu) {
		this.submenu = submenu;
	}	
	public Pages getPage() {
		return page;
	}
	public void setPage(Pages page) {
		this.page = page;
	}
	public String getMenuType() {
		return menuType;
	}
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
	public void fromObject(MenuRequest request) {
		if(request.getId() != null) {
			this.set_id(new ObjectId(request.getId()));
		}
		this.setName(request.getName());
		if(request.getPage() != null) {
			Pages page= new Pages();
			page.fromObject(request.getPage());
			this.setPage(page);
		}
		this.setMenuType(request.getMenuType());
	}
}
