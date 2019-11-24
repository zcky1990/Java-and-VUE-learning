package app.serializer;

import com.google.gson.*;

import app.mongo.model.Users;
import app.mongo.model.VisiMisi;
import app.util.TimeUtility;
import java.lang.reflect.Type;
import java.util.Date;

public class VisiMisiSerializer implements JsonSerializer<VisiMisi> {
	
	@Override
	public JsonElement serialize(VisiMisi src, Type typeOfSrc, JsonSerializationContext context) {
		TimeUtility util = new TimeUtility();
		JsonObject jsonObj = (JsonObject)new GsonBuilder().create().toJsonTree(src);
		String id = src.getStringId();
		if(jsonObj.has("_id")) {
			jsonObj.remove("_id");
		}
		jsonObj.addProperty("id", id);
		if(jsonObj.has("author")) {
			jsonObj.remove("author");
			Users user = src.getAuthor();
			JsonObject userJson = new JsonObject();
			userJson.addProperty("id", user.getStringId());
			userJson.addProperty("firstname", user.getFirstname());
			userJson.addProperty("lastname", user.getLastname());
			userJson.addProperty("email", user.getEmail());
			userJson.addProperty("username", user.getUsername());
			userJson.addProperty("name", user.getName());
			userJson.addProperty("image_profile_url", user.getImageUrl());
			jsonObj.add("author", userJson);
		}
						
		Date time = src.get_id().getDate();
		String createDate = util.getFormatedDate(time, "dd/MM/yyyy");
		jsonObj.addProperty("created_date", createDate);
		
		return jsonObj;
	}
}
