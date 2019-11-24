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
import app.model.request.SejarahRequest;
import app.mongo.model.Sejarah;
import app.mongo.model.Users;
import app.repository.SejarahRepository;
import app.repository.UsersRepository;
import app.serializer.SejarahSerializer;
import app.util.TimeUtility;

@RestController
@RequestMapping("/api")
public class SejarahController extends BaseController {
	@Autowired
	private UsersRepository userRepository;
	
	@Autowired
	private SejarahRepository sejarahRepository;
	

	@RequestMapping(value = "/sejarah/add", method = RequestMethod.POST)
	public ResponseEntity<String> addSejarah(@Valid @RequestBody SejarahRequest sejarahRequest, HttpServletRequest request) throws Exception {
		String auth = request.getHeader("x-uid");
		Users user = userRepository.findBy_id(new ObjectId(auth));
		JsonObject response;
		if(user != null) {
			Sejarah sejarah = new Sejarah();
			sejarah.fromObject(sejarahRequest);
			sejarah.setAuthor(user);
			try {
				sejarahRepository.save(sejarah);
				System.out.println("masuk sini 3");
				response = getSuccessResponse();
				response.add(Constant.RESPONSE, toJSONObjectWithSerializer(Sejarah.class, new SejarahSerializer(), sejarah)  );
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
	
	@RequestMapping(value = "/sejarah/update", method = RequestMethod.PUT)
	public ResponseEntity<String> updateSejarah(@Valid @RequestBody SejarahRequest sejarahRequest, HttpServletRequest request) throws Exception {
		String auth = request.getHeader("x-uid");
		TimeUtility util = new TimeUtility();
		Users user = userRepository.findBy_id(new ObjectId(auth));
		JsonObject response;
		if(user != null) {
			Sejarah sejarah = new Sejarah();
			sejarah.fromObject(sejarahRequest);
			sejarah.setAuthor(user);
			sejarah.setModified_date(util.getCurrentDate("dd/MM/yyyy HH:mm:ss"));
			try {
				sejarahRepository.save(sejarah);
				response = getSuccessResponse();
				response.addProperty(Constant.RESPONSE,Constant.UPDATE_SEJARAH_SUCCESS_MESSAGE);
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
	
	@RequestMapping(value = "/sejarah/get", method = RequestMethod.GET)
	public ResponseEntity<String> getSejarah( HttpServletRequest request) throws Exception {
		JsonObject response;
//		try {
			List<Sejarah> sejarahList = sejarahRepository.findAll();
			response = getSuccessResponse();
			response.add(Constant.RESPONSE, this.toJSONArrayWithSerializer(Sejarah.class, new SejarahSerializer(), sejarahList)  );	
//		} catch(Exception e) {
//			response = getFailedResponse();
//			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
//		}
		return new ResponseEntity<String>( response.toString() , getResponseHeader(), HttpStatus.OK);
	}
	
}
