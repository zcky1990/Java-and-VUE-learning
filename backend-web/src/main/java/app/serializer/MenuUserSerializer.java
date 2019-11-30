package app.serializer;

import com.google.gson.*;

import app.mongo.model.Menu;
import app.mongo.model.SubMenu;

import java.lang.reflect.Type;
import java.util.List;

public class MenuUserSerializer implements JsonSerializer<Menu> {

	@Override
	public JsonElement serialize(Menu src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject jsonObj = (JsonObject)new GsonBuilder().create().toJsonTree(src);
		if(jsonObj.has("_id")) {
			jsonObj.remove("_id");
		}
		if(jsonObj.has("page")) {
			jsonObj.addProperty("slug", src.getPage().getPermalink());
			jsonObj.remove("page");
		}
		
		if(jsonObj.has("menuType")) {
			jsonObj.remove("menuType");
			if(src.getMenuType().equalsIgnoreCase("menu")) {
				jsonObj.addProperty("isMenu", true);
			}else {
				jsonObj.addProperty("isMenu", false);
			}
		}
		
		if(jsonObj.has("submenu")) {
			JsonArray newSubMenu = new JsonArray();
			List<SubMenu> menus = src.getSubmenu();
			for(int i = 0 ; i < menus.size(); i++) {
				SubMenu submenu = menus.get(i);
				JsonObject submenuObj = new JsonObject();
				submenuObj.addProperty("name", submenu.getName());
				submenuObj.addProperty("slug", submenu.getPage().getPermalink());
				newSubMenu.add(submenuObj);
			}
			jsonObj.remove("submenu");
			jsonObj.add("submenu", newSubMenu);
		}
		return jsonObj;
	}
}
