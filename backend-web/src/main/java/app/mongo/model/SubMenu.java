package app.mongo.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import app.model.request.SubMenuRequest;

public class SubMenu {
	@Id
	private ObjectId _id;
	@Indexed
	private String name;
	private Pages page;
	
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
	public Pages getPage() {
		return page;
	}
	public void setPage(Pages page) {
		this.page = page;
	}	
	
	public void fromObject(SubMenuRequest request) {
		if(request.getId() != null) {
			this.set_id(new ObjectId(request.getId()));
		}
		this.setName(request.getName());
		if(request.getPage() != null) {
			Pages page= new Pages();
			page.fromObject(request.getPage());
			this.setPage(page);
		}		
	}
}
