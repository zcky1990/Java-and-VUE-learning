package app.serializer;

import com.google.gson.*;
import app.mongo.model.HeroBanner;
import app.util.TimeUtility;

import java.lang.reflect.Type;
import java.util.Date;

public class HeroBannerSerializer implements JsonSerializer<HeroBanner> {

	@Override
	public JsonElement serialize(HeroBanner src, Type typeOfSrc, JsonSerializationContext context) {
		TimeUtility util = new TimeUtility();
		JsonObject jsonObj = (JsonObject)new GsonBuilder().create().toJsonTree(src);
		String id = src.getStringId();
		if(jsonObj.has("_id")) {
			jsonObj.remove("_id");
		}
		jsonObj.addProperty("id", id);
		Date time = src.get_id().getDate();
		String createDate = util.getFormatedDate(time, "dd/MM/yyyy");
		jsonObj.addProperty("created_date", createDate);
		return jsonObj;
	}
}
