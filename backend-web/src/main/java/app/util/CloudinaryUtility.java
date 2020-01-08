package app.util;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import com.google.gson.JsonObject;

import app.constants.Constant;
import app.environment.EnvironmentBuild;

public class CloudinaryUtility {
	private EnvironmentBuild env;
	private Cloudinary cloudinary;
	
	public CloudinaryUtility(EnvironmentBuild env) {
		this.env = env;
		this.setProperties();
	}
		
	public void setProperties() {
		cloudinary = new Cloudinary(ObjectUtils.asMap(
				  "cloud_name", env.getCloudinaryCloudName(),
				  "api_key", env.getCloudinaryApiKey(),
				  "api_secret", env.getCloudinaryApiSecret()));
	}
	
	public JsonObject uploadImage(String path) {
		File toUpload = new File(path);
		JsonObject response = new JsonObject();
		try {
			Map uploadResult = cloudinary.uploader().upload(toUpload, ObjectUtils.emptyMap());
			response.addProperty("url", uploadResult.get("secure_url").toString());
			response.addProperty("status", "success");
		} catch (IOException e) {
			response.addProperty("error_message", e.getMessage().toString());
			response.addProperty("status", "error");
		}
		return response;
	}
	
	public JsonObject uploadBase64ImageString(String data) {
		JsonObject response = new JsonObject();
		try {
			Map uploadResult = cloudinary.uploader().upload(data, ObjectUtils.emptyMap());
			response.addProperty("url", uploadResult.get("secure_url").toString());
			response.addProperty("status", "success");
		} catch (IOException e) {
			response.addProperty("error_message", e.getMessage().toString());
			response.addProperty("status", "error");
		}
		return response;
	}
	
	public JsonObject uploadBase64ImageString(String data, String folder) {
		JsonObject response = new JsonObject();
		try {
			Map options = ObjectUtils.asMap(
				    "folder", folder);
			Map uploadResult = cloudinary.uploader().upload(data, options);
			response.addProperty("url", uploadResult.get("secure_url").toString());
			response.addProperty("status", "success");
		} catch (IOException e) {
			response.addProperty("error_message", e.getMessage().toString());
			response.addProperty("status", "error");
		}
		return response;
	}
		
	public JsonObject uploadImage(MultipartFile file) {
		JsonObject response = new JsonObject();
		try {
			Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
			response.addProperty("url", uploadResult.get("secure_url").toString());
			response.addProperty("status", "success");
		} catch (IOException e) {
			response.addProperty("error_message", e.getMessage().toString());
			response.addProperty("status", "error");
		}
		return response;
	}
	
	public JsonObject uploadImage(MultipartFile file, String folder) {
		JsonObject response = new JsonObject();
		Map options;
		if(env.getEnvirontment().equals(Constant.DEV_ENV)) {
			options = ObjectUtils.asMap("folder", folder + "dev");
		}else {
			options = ObjectUtils.asMap("folder", folder);
		}
		try {
			Map uploadResult = cloudinary.uploader().upload(file.getBytes(), options);
			response.addProperty("url", uploadResult.get("secure_url").toString());
			response.addProperty("status", "success");
		} catch (IOException e) {
			response.addProperty("error_message", e.getMessage().toString());
			response.addProperty("status", "error");
		}
		return response;
	}
	
	public JsonObject uploadImage(MultipartFile file, String folder, String name) {
		JsonObject response = new JsonObject();
		Map options;
		if(env.getEnvirontment().equals(Constant.DEV_ENV)) {
			options = ObjectUtils.asMap("folder", folder + "dev", "public_id", name);
		}else {
			options = ObjectUtils.asMap("folder", folder, "public_id", name);
		}
		try {
			Map uploadResult = cloudinary.uploader().upload(file.getBytes(), options);
			response.addProperty("url", uploadResult.get("secure_url").toString());
			response.addProperty("status", "success");
		} catch (IOException e) {
			response.addProperty("error_message", e.getMessage().toString());
			response.addProperty("status", "error");
		}
		return response;
	}

}
