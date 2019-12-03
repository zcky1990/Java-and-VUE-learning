package app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

import app.constants.Constant;
import app.model.request.ProdiRequest;
import app.mongo.model.Prodi;
import app.repository.ProdiRepository;
import app.serializer.ProdiSerializer;

@RestController
@RequestMapping("/api")
public class ProdiController extends BaseController {
	@Autowired
	private ProdiRepository repository;

	@RequestMapping(value = "/prodi/create", method = RequestMethod.POST)
	public ResponseEntity<String> create(@Valid @RequestBody ProdiRequest prodiRequest, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			Prodi prodi = new Prodi();
			prodi.fromObject(prodiRequest);
			repository.save(prodi);
			response = getSuccessResponse();
			response.add(Constant.RESPONSE, toJSONObjectWithSerializer(Prodi.class, new ProdiSerializer(), prodi)  );
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}

	@RequestMapping(value = "/prodi/edit", method = RequestMethod.PUT)
	public ResponseEntity<String> edit(@Valid @RequestBody ProdiRequest prodiRequest, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			Prodi prodi = new Prodi();
			prodi.fromObject(prodiRequest);
			repository.save(prodi);
			response = getSuccessResponse();
			response.addProperty(Constant.RESPONSE,Constant.UPDATE_CATEGORY_SUCCESS_MESSAGE);
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/prodi/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> edit(@PathVariable String id, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			Optional<Prodi> responseFaculty = repository.findById(id);
			Prodi faculty = responseFaculty.get();
			response = getSuccessResponse();
			response.add(Constant.RESPONSE, toJSONObjectWithSerializer(Prodi.class, new ProdiSerializer(), faculty)  );
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/prodi/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable String id, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			repository.delete(repository.findById(id).get());
			response = getSuccessResponse();
			response.addProperty(Constant.RESPONSE, Constant.DELETE_SUCCESS_MESSAGE);
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}

	
	@RequestMapping(value = "/prodi/get_all_prodi_list", method = RequestMethod.GET)
	public ResponseEntity<String> getAllFacultyList(@RequestParam(value="page", required=false) String page, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			List<Prodi> faculty  = new ArrayList<>();
			response = getSuccessResponse();
			if(page!= null) {
				Pageable pageableRequest = PageRequest.of(Integer.parseInt(page), 10, Sort.by("_id").descending());
				Page<Prodi> pageFaculty = repository.findAll(pageableRequest);
				response.addProperty("total_page", pageFaculty.getTotalPages());
				faculty = pageFaculty.getContent();
			}else {
				faculty = repository.findAll();
			}
			response.add(Constant.RESPONSE, toJSONArrayWithSerializer(Prodi.class, new ProdiSerializer(), faculty)  );
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/prodi/get_all_published_prodi_list", method = RequestMethod.GET)
	public ResponseEntity<String> getAllPublishedFacultyList(@RequestParam(value="page", required=false) String page, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			List<Prodi> faculty  = new ArrayList<>();
			response = getSuccessResponse();
			if(page!= null) {
				Pageable pageableRequest = PageRequest.of(Integer.parseInt(page), 10, Sort.by("_id").descending());
				Page<Prodi> pageFaculty = repository.findAllByIsPublished(true, pageableRequest);
				response.addProperty("total_page", pageFaculty.getTotalPages());
				faculty = pageFaculty.getContent();
			}else {
				faculty = repository.findAllByIsPublished(true);
			}
			response.add(Constant.RESPONSE, toJSONArrayWithSerializer(Prodi.class, new ProdiSerializer(), faculty)  );
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}

}
