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
import app.model.request.MenuRequest;
import app.model.request.SubMenuRequest;
import app.mongo.model.Menu;
import app.mongo.model.SubMenu;
import app.repository.MenuRepository;
import app.repository.SubMenuRepository;
import app.serializer.MenuSerializer;
import app.serializer.MenuUserSerializer;

@RestController
@RequestMapping("/api")
public class MenuController extends BaseController {
	@Autowired
	private MenuRepository repository;

	@Autowired
	private SubMenuRepository subrepository;

	@RequestMapping(value = "/menu/create", method = RequestMethod.POST)
	public ResponseEntity<String> create(@Valid @RequestBody MenuRequest menuRequest, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			Menu menu = new Menu();
			List<SubMenuRequest> subMenuRequest = menuRequest.getSubmenu();
			List<SubMenu> subMenuList = new ArrayList<>();
			if(subMenuRequest != null) {
				for(int i = 0 ; i < subMenuRequest.size(); i++) {
					SubMenu submenu = new SubMenu();
					submenu.fromObject(subMenuRequest.get(i));
					subrepository.save(submenu);
					subMenuList.add(submenu);
				}
			}
			menu.fromObject(menuRequest);
			menu.setSubmenu(subMenuList);
			repository.save(menu);
			response = getSuccessResponse();
			response.add(Constant.RESPONSE, toJSONObjectWithSerializer(Menu.class, new MenuSerializer(), menu)  );
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}

	@RequestMapping(value = "/menu/edit", method = RequestMethod.PUT)
	public ResponseEntity<String> edit(@Valid @RequestBody MenuRequest menuRequest, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			Menu menu = new Menu();
			List<SubMenuRequest> subMenuRequest = menuRequest.getSubmenu();
			List<SubMenu> subMenuList = new ArrayList<>();
			if(subMenuRequest != null) {
				for(int i = 0 ; i < subMenuRequest.size(); i++) {
					SubMenu submenu = new SubMenu();
					submenu.fromObject(subMenuRequest.get(i));
					subrepository.save(submenu);
					subMenuList.add(submenu);
				}
			}
			menu.fromObject(menuRequest);
			menu.setSubmenu(subMenuList);
			repository.save(menu);
			response = getSuccessResponse();
			response.addProperty(Constant.RESPONSE,Constant.UPDATE_CATEGORY_SUCCESS_MESSAGE);
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}

	@RequestMapping(value = "/menu/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> edit(@PathVariable String id, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			Optional<Menu> responseMenu = repository.findById(id);
			Menu menu = responseMenu.get();
			response = getSuccessResponse();
			response.add(Constant.RESPONSE, toJSONObjectWithSerializer(Menu.class, new MenuSerializer(), menu)  );
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}

	@RequestMapping(value = "/menu/delete/{id}", method = RequestMethod.DELETE)
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


	@RequestMapping(value = "/menu/get_menu_list", method = RequestMethod.GET)
	public ResponseEntity<String> getCategoryList( @RequestParam(value="page", required=false) String page, HttpServletRequest request) throws Exception {
		JsonObject response ;
		try {
			response = getSuccessResponse();
			List<Menu> menu  = new ArrayList<>();
			if(page!= null) {
				Pageable pageableRequest = PageRequest.of(Integer.parseInt(page), 10, Sort.by("_id").descending());
				Page<Menu> menuPage = repository.findAll(pageableRequest);
				int pageCount = menuPage.getTotalPages();
				response.addProperty("total_page", pageCount);
			}else {
				menu = repository.findAll();
			}
			response.add(Constant.RESPONSE, toJSONArrayWithSerializer(Menu.class, new MenuSerializer(), menu)  );
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}

	@RequestMapping(value = "/menu/get_menu", method = RequestMethod.GET)
	public ResponseEntity<String> getCategoryList(HttpServletRequest request) throws Exception {
		JsonObject response ;
		try {
			response = getSuccessResponse();
			List<Menu> menu  = new ArrayList<>();
			menu = repository.findAll();
			response.add(Constant.RESPONSE, toJSONArrayWithSerializer(Menu.class, new MenuUserSerializer(), menu)  );
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}

}
