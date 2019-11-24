package app.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

import app.constants.Constant;
import app.model.request.VisiMisiRequest;
import app.mongo.model.Sejarah;
import app.mongo.model.Users;
import app.mongo.model.VisiMisi;
import app.repository.UsersRepository;
import app.repository.VisiMisiRepository;
import app.serializer.SejarahSerializer;
import app.serializer.VisiMisiSerializer;
import app.util.TimeUtility;

@RestController
@RequestMapping("/api")
public class VisiMisiController extends BaseController {
	@Autowired
	private UsersRepository userRepository;
	
	@Autowired
	private VisiMisiRepository visiMisiRepository;
	

	@RequestMapping(value = "/visi-misi/add", method = RequestMethod.POST)
	public ResponseEntity<String> addSejarah(@Valid @RequestBody VisiMisiRequest visiMisiRequest, HttpServletRequest request) throws Exception {
		String auth = request.getHeader("x-uid");
		Users user = userRepository.findBy_id(new ObjectId(auth));
		JsonObject response;
		if(user != null) {
			VisiMisi visiMisi = new VisiMisi();
			visiMisi.fromObject(visiMisiRequest);
			visiMisi.setAuthor(user);
			try {
				visiMisiRepository.save(visiMisi);
				response = getSuccessResponse();
				response.add(Constant.RESPONSE, toJSONObjectWithSerializer(VisiMisi.class, new VisiMisiSerializer(), visiMisi)  );
			} catch(Exception e) {
				response = getFailedResponse();
				response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
			}
		}else {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, Constant.USER_NOT_FOUND_ERROR_MESSAGE);
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/visi-misi/update", method = RequestMethod.PUT)
	public ResponseEntity<String> updateSejarah(@Valid @RequestBody VisiMisiRequest visiMisiRequest, HttpServletRequest request) throws Exception {
		String auth = request.getHeader("x-uid");
		TimeUtility util = new TimeUtility();
		Users user = userRepository.findBy_id(new ObjectId(auth));
		JsonObject response;
		if(user != null) {
			VisiMisi visiMisi = new VisiMisi();
			visiMisi.fromObject(visiMisiRequest);
			visiMisi.setAuthor(user);
			visiMisi.setModified_date(util.getCurrentDate("dd/MM/yyyy HH:mm:ss"));
			try {
				visiMisiRepository.save(visiMisi);
				response = getSuccessResponse();
				response.addProperty(Constant.RESPONSE,Constant.UPDATE_VISI_MISI_SUCCESS_MESSAGE);
			} catch(Exception e) {
				response = getFailedResponse();
				response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
			}
		}else {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, Constant.USER_NOT_FOUND_ERROR_MESSAGE);
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/visi-misi/get", method = RequestMethod.GET)
	public ResponseEntity<String> getSejarah( HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			 List<VisiMisi> visiMisiList = visiMisiRepository.findAll();
			response = getSuccessResponse();
			response.add(Constant.RESPONSE, this.toJSONArrayWithSerializer(VisiMisi.class, new VisiMisiSerializer(), visiMisiList)  );	
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString() , getResponseHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/visi-misi/get_visi_misi", method = RequestMethod.GET)
	public ResponseEntity<String> getVisiMisi( HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			VisiMisi visiMisi = visiMisiRepository.findByVisiMisiPublishStatus(true);
			response = getSuccessResponse();
			if(visiMisi != null) {
				response.add(Constant.RESPONSE, this.toJSONObjectWithSerializer(VisiMisi.class, new VisiMisiSerializer(), visiMisi)  );	
			}else {
				response.add(Constant.RESPONSE, new JsonObject());	
			}
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString() , getResponseHeader(), HttpStatus.OK);
	}
	
}
