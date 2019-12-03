package app.serializer;

import com.google.gson.*;

import app.mongo.model.Faculty;
import app.mongo.model.Prodi;
import app.util.TimeUtility;
import java.lang.reflect.Type;
import java.util.Date;

public class ProdiSerializer implements JsonSerializer<Prodi> {

	@Override
	public JsonElement serialize(Prodi src, Type typeOfSrc, JsonSerializationContext context) {
		TimeUtility util = new TimeUtility();
		JsonObject jsonObj = (JsonObject)new GsonBuilder().create().toJsonTree(src);
		String id = src.getStringId();
		if(jsonObj.has("_id")) {
			jsonObj.remove("_id");
		}
		jsonObj.addProperty("id", id);
		
		if(jsonObj.has("fakultas")) {
			jsonObj.remove("fakultas");
			Faculty fak = src.getFakultas();
			JsonObject faculty = new JsonObject();
			faculty.addProperty("id", fak.getStringId());
			faculty.addProperty("facultyName", fak.getFacultyName());
			faculty.addProperty("status", fak.getIsPublished());
			jsonObj.add("fakultas", faculty);
		}
		Date time = src.get_id().getDate();
		String createDate = util.getFormatedDate(time, "dd/MM/yyyy HH:mm:ss");
		jsonObj.addProperty("created_date", createDate);
		return jsonObj;
	}
}
