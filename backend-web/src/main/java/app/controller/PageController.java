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
import app.model.request.PagesRequest;
import app.mongo.model.Pages;
import app.repository.PagesRepository;
import app.serializer.PagesSerializer;

@RestController
@RequestMapping("/api")
public class PageController extends BaseController {
	@Autowired
	private PagesRepository repository;

	@RequestMapping(value = "/pages/create", method = RequestMethod.POST)
	public ResponseEntity<String> create(@Valid @RequestBody PagesRequest menuRequest, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			Pages menu = new Pages();
			menu.fromObject(menuRequest);
			repository.save(menu);
			response = getSuccessResponse();
			response.add(Constant.RESPONSE, toJSONObjectWithSerializer(Pages.class, new PagesSerializer(), menu)  );
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}

	@RequestMapping(value = "/pages/edit", method = RequestMethod.PUT)
	public ResponseEntity<String> edit(@Valid @RequestBody PagesRequest menuRequest, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			Pages menu = new Pages();
			menu.fromObject(menuRequest);
			repository.save(menu);
			response = getSuccessResponse();
			response.addProperty(Constant.RESPONSE,Constant.UPDATE_CATEGORY_SUCCESS_MESSAGE);
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/pages/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> edit(@PathVariable String id, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			Optional<Pages> responseMenu = repository.findById(id);
			Pages menu = responseMenu.get();
			response = getSuccessResponse();
			response.add(Constant.RESPONSE, toJSONObjectWithSerializer(Pages.class, new PagesSerializer(), menu)  );
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/pages/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable String id, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			repository.delete(repository.findById(id).get());
			response = getSuccessResponse();
			response.addProperty(Constant.RESPONSE, Constant.DELETE_CATEGORY_SUCCESS_MESSAGE);
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}

	
	@RequestMapping(value = "/pages/get_pages_list", method = RequestMethod.GET)
	public ResponseEntity<String> getPagesList( @RequestParam(value="page", required=false) String page, HttpServletRequest request) throws Exception {
		JsonObject response ;
		try {
			response = getSuccessResponse();
			List<Pages> pages  = new ArrayList<>();
			if(page!= null) {
				Pageable pageableRequest = PageRequest.of(Integer.parseInt(page), 10, Sort.by("_id").descending());
				Page<Pages> pageList = repository.findAll(pageableRequest);
				int pageCount = pageList.getTotalPages();
				pages = pageList.getContent();
				response.addProperty("total_page", pageCount);
			}else {
				pages = repository.findAll();
			}
			response.add(Constant.RESPONSE, toJSONArrayWithSerializer(Pages.class, new PagesSerializer(), pages) );
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/pages/get_published_pages_list", method = RequestMethod.GET)
	public ResponseEntity<String> getPublishedPagesList(HttpServletRequest request) throws Exception {
		JsonObject response ;
		try {
			response = getSuccessResponse();
			List<Pages> pages  = new ArrayList<>();
			pages = repository.findByIsPublish(true);
			response.add(Constant.RESPONSE, toJSONArrayWithSerializer(Pages.class, new PagesSerializer(), pages) );
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/pages/get_page/{permalink}", method = RequestMethod.GET)
	public ResponseEntity<String> getArticle(@PathVariable String permalink, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			Pages article = repository.findByPermalink(permalink);
			response = getSuccessResponse();
			response.add(Constant.RESPONSE, toJSONObjectWithSerializer(Pages.class, new PagesSerializer(), article)  );	
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString() , getResponseHeader(), HttpStatus.OK);
	}

}
