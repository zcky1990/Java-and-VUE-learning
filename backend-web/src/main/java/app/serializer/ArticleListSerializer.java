package app.serializer;

import com.google.gson.*;

import app.mongo.model.Article;
import app.mongo.model.Category;
import app.util.TimeUtility;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public class ArticleListSerializer implements JsonSerializer<Article> {

	@Override
	public JsonElement serialize(Article src, Type typeOfSrc, JsonSerializationContext context) {
		TimeUtility util = new TimeUtility();
		JsonObject jsonObj = (JsonObject)new GsonBuilder().create().toJsonTree(src);
		String id = src.getStringId();
		if(jsonObj.has("_id")) {
			jsonObj.remove("_id");
		}
		jsonObj.addProperty("id", id);

		if(jsonObj.has("author")) {
			jsonObj.remove("author");
		}
		jsonObj.remove("article_content");

		if(jsonObj.has("categoryArticle")) {
			jsonObj.remove("categoryArticle");
			List<Category> list = src.getCategoryArticle();
			JsonArray listCategory = new JsonArray();
			for(int i = 0 ; i < list.size(); i++) {
				Category categoryArticle = list.get(i);
				JsonObject category = new JsonObject();
				category.addProperty("id", categoryArticle.getStringId());
				category.addProperty("name", categoryArticle.getName());
				category.addProperty("status", categoryArticle.getStatus());
				category.addProperty("type", categoryArticle.getType());
				listCategory.add(category);
			}
			jsonObj.add("categoryArticle", listCategory);
		}

		Date time = src.get_id().getDate();
		String createDate = util.getFormatedDate(time, "dd/MM/yyyy");
		jsonObj.addProperty("created_date", createDate);

		return jsonObj;
	}
}
