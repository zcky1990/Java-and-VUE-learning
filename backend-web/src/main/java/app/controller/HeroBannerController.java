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
import app.model.request.HeroBannerRequest;
import app.mongo.model.HeroBanner;
import app.repository.HeroBannerRepository;
import app.serializer.HeroBannerSerializer;

@RestController
@RequestMapping("/api")
public class HeroBannerController extends BaseController {
	@Autowired
	private HeroBannerRepository repository;
	
	@RequestMapping(value = "/hero/show-hero", method = RequestMethod.GET)
	public ResponseEntity<String> getAllHeroPublish(HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			List<HeroBanner> herolist = repository.findAllByIsPublished(true);
			response = getSuccessResponse();
			response.add(Constant.RESPONSE, this.toJSONArrayWithSerializer(HeroBanner.class, new HeroBannerSerializer(), herolist)  );
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/hero/get-all-hero", method = RequestMethod.GET)
	public ResponseEntity<String> getAllHeroPublish(@RequestParam(value="page", required=false) String page, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			response = getSuccessResponse();
			List<HeroBanner> herolist = new ArrayList<HeroBanner> ();
			if(page!= null) {
				Pageable pageableRequest = PageRequest.of(Integer.parseInt(page), 10, Sort.by("_id").descending());
				Page<HeroBanner> pageCount = repository.findAll(pageableRequest);
				response.addProperty("totalPage", pageCount.getTotalPages());
				herolist = pageCount.getContent();
			}else {
				herolist = repository.findAll();
			}
			response.add(Constant.RESPONSE, this.toJSONArrayWithSerializer(HeroBanner.class, new HeroBannerSerializer(), herolist)  );
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}

	@RequestMapping(value = "/hero/create", method = RequestMethod.POST)
	public ResponseEntity<String> create(@Valid @RequestBody HeroBannerRequest heroRequest, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			HeroBanner hero = new HeroBanner();
			hero.fromObject(heroRequest);
			repository.save(hero);
			response = getSuccessResponse();
			response.add(Constant.RESPONSE, toJSONObjectWithSerializer(HeroBanner.class, new HeroBannerSerializer(), hero)  );
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}

	@RequestMapping(value = "/hero/edit", method = RequestMethod.PUT)
	public ResponseEntity<String> edit(@Valid @RequestBody HeroBannerRequest heroRequest, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			HeroBanner updateHero = new HeroBanner();
			updateHero.fromObject(heroRequest);
			repository.save(updateHero);
			response = getSuccessResponse();
			response.addProperty(Constant.RESPONSE,Constant.UPDATE_HERO_SUCCESS_MESSAGE);
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/hero/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> get(@PathVariable String id, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			Optional<HeroBanner> responseRoles = repository.findById(id);
			HeroBanner hero = responseRoles.get();
			response = getSuccessResponse();
			response.add(Constant.RESPONSE, toJSONObjectWithSerializer(HeroBanner.class, new HeroBannerSerializer(), hero)  );
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/hero/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable String id, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			repository.delete(repository.findById(id).get());
			response = getSuccessResponse();
			response.addProperty(Constant.RESPONSE, Constant.DELETE_ROLES_SUCCESS_MESSAGE);
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}

}
