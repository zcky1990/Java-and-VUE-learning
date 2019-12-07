package app.serializer;

import com.google.gson.*;

import app.mongo.model.Faculty;
import app.mongo.model.Mahasiswa;
import app.mongo.model.Prodi;
import app.util.TimeUtility;
import java.lang.reflect.Type;
import java.util.Date;

public class MahasiswaDetailsSerializer implements JsonSerializer<Mahasiswa> {

	@Override
	public JsonElement serialize(Mahasiswa src, Type typeOfSrc, JsonSerializationContext context) {
		TimeUtility util = new TimeUtility();
		JsonObject jsonObj = (JsonObject)new GsonBuilder().create().toJsonTree(src);
		String id = src.getStringId();
		if(jsonObj.has("_id")) {
			jsonObj.remove("_id");
		}
		jsonObj.addProperty("id", id);
		
		if(jsonObj.has("prodi")) {
			Prodi prodi = src.getProdi();
			Faculty faculty = prodi.getFakultas();
			JsonObject prodiJsonObj = new JsonObject();
			prodiJsonObj.addProperty("id",prodi.getStringId());
			prodiJsonObj.addProperty("isPublished",prodi.getIsPublished());
			prodiJsonObj.addProperty("degree",prodi.getDegree());
			prodiJsonObj.addProperty("prodiName",prodi.getProdiName());
			
			JsonObject facultyJsonObj = new JsonObject();
			facultyJsonObj.addProperty("id", faculty.getStringId());
			facultyJsonObj.addProperty("facultyName", faculty.getFacultyName());
			facultyJsonObj.addProperty("isPublished", faculty.getIsPublished());
			
			prodiJsonObj.add("fakultas", facultyJsonObj);
			jsonObj.add("prodi", prodiJsonObj);
		}
		Date time = src.get_id().getDate();
		String createDate = util.getFormatedDate(time, "dd/MM/yyyy HH:mm:ss");
		jsonObj.addProperty("created_date", createDate);
		return jsonObj;
	}
}
