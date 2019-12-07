package app.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import com.google.gson.JsonObject;

import app.constants.Constant;
import app.csvmodel.MahasiswaCSV;
import app.model.request.MahasiswaRequest;
import app.mongo.model.Mahasiswa;
import app.repository.MahasiswaRepository;
import app.serializer.MahasiswaDetailsSerializer;
import app.serializer.MahasiswaSerializer;

@RestController
@RequestMapping("/api")
public class MahasiswaController extends BaseController {
	@Autowired
	private MahasiswaRepository repository;

	@RequestMapping(value = "/mahasiswa/create", method = RequestMethod.POST)
	public ResponseEntity<String> create(@Valid @RequestBody MahasiswaRequest prodiRequest, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			Mahasiswa mahasiswa = new Mahasiswa();
			mahasiswa.fromObject(prodiRequest);
			repository.save(mahasiswa);
			response = getSuccessResponse();
			response.add(Constant.RESPONSE, toJSONObjectWithSerializer(Mahasiswa.class, new MahasiswaSerializer(), mahasiswa)  );
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}

	@RequestMapping(value = "/mahasiswa/edit", method = RequestMethod.PUT)
	public ResponseEntity<String> edit(@Valid @RequestBody MahasiswaRequest prodiRequest, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			Mahasiswa mahasiswa = new Mahasiswa();
			mahasiswa.fromObject(prodiRequest);
			repository.save(mahasiswa);
			response = getSuccessResponse();
			response.addProperty(Constant.RESPONSE,Constant.UPDATE_CATEGORY_SUCCESS_MESSAGE);
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/mahasiswa/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> edit(@PathVariable String id, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			Optional<Mahasiswa> responseFaculty = repository.findById(id);
			Mahasiswa faculty = responseFaculty.get();
			response = getSuccessResponse();
			response.add(Constant.RESPONSE, toJSONObjectWithSerializer(Mahasiswa.class, new MahasiswaDetailsSerializer(), faculty)  );
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/mahasiswa/delete/{id}", method = RequestMethod.DELETE)
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

	
	@RequestMapping(value = "/mahasiswa/get_all_mahasiswa_list", method = RequestMethod.GET)
	public ResponseEntity<String> getAllFacultyList(@RequestParam(value="page", required=false) String page, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			List<Mahasiswa> faculty  = new ArrayList<>();
			response = getSuccessResponse();
			if(page!= null) {
				Pageable pageableRequest = PageRequest.of(Integer.parseInt(page), 10, Sort.by("_id").descending());
				Page<Mahasiswa> pageFaculty = repository.findAll(pageableRequest);
				response.addProperty("total_page", pageFaculty.getTotalPages());
				faculty = pageFaculty.getContent();
			}else {
				faculty = repository.findAll();
			}
			response.add(Constant.RESPONSE, toJSONArrayWithSerializer(Mahasiswa.class, new MahasiswaSerializer(), faculty)  );
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/mahasiswa/get_all_mahasiswa_list_filtered", method = RequestMethod.GET)
	public ResponseEntity<String> getAllFilteredMahasiswaList(@RequestParam(value="page", required=false) String page,@RequestParam(value="startDate", required=true) String startDate,@RequestParam(value="endDate", required=true) String endDate, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			List<Mahasiswa> faculty  = new ArrayList<>();
			response = getSuccessResponse();
			if(page!= null) {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date lsDate = formatter.parse(startDate);    
				LocalDate strdate = lsDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				
				Date leDate = formatter.parse(endDate);    
				LocalDate enddate = leDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				
				Date st = Date.from(strdate.atStartOfDay().toInstant(ZoneOffset.UTC));
				Date et = Date.from(enddate.atStartOfDay().toInstant(ZoneOffset.UTC));
				Pageable pageableRequest = PageRequest.of(Integer.parseInt(page), 10, Sort.by("_id").descending());
				Page<Mahasiswa> pageFaculty = repository.findByTimeCreatedBetween(st,et,pageableRequest);
				response.addProperty("total_page", pageFaculty.getTotalPages());
				faculty = pageFaculty.getContent();
			}else {
				faculty = repository.findAll();
			}
			response.add(Constant.RESPONSE, toJSONArrayWithSerializer(Mahasiswa.class, new MahasiswaSerializer(), faculty)  );
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/mahasiswa/get_all_mahasiswa_list_filtered_by_name_email", method = RequestMethod.GET)
	public ResponseEntity<String> getAllFilteredMahasiswaListByName(@RequestParam(value="page", required=false) String page,@RequestParam(value="name", required=true) String name, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			List<Mahasiswa> faculty  = new ArrayList<>();
			response = getSuccessResponse();
			if(page!= null) {
				Pageable pageableRequest = PageRequest.of(Integer.parseInt(page), 10, Sort.by("_id").descending());
				Page<Mahasiswa> pageFaculty = repository.findByNameOrEmail(name,pageableRequest);
				response.addProperty("total_page", pageFaculty.getTotalPages());
				faculty = pageFaculty.getContent();
			}else {
				faculty = repository.findAll();
			}
			response.add(Constant.RESPONSE, toJSONArrayWithSerializer(Mahasiswa.class, new MahasiswaSerializer(), faculty)  );
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/mahasiswa/exportCSV", method = RequestMethod.GET)
	public void exportCSV(@RequestParam(value="startDate", required=true) String startDate,@RequestParam(value="endDate", required=true) String endDate, HttpServletRequest request, HttpServletResponse response) throws Exception {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date lsDate = formatter.parse(startDate);    
				LocalDate strdate = lsDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				
				Date leDate = formatter.parse(endDate);    
				LocalDate enddate = leDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				
				Date st = Date.from(strdate.atStartOfDay().toInstant(ZoneOffset.UTC));
				Date et = Date.from(enddate.atStartOfDay().toInstant(ZoneOffset.UTC));
				List<Mahasiswa> pageFaculty = repository.findByTimeCreatedBetween(st,et);
		
		        String filename = "download_"+startDate+"_"+endDate+".csv";
		        response.setContentType("text/csv");
		        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
		                "attachment; filename=\"" + filename + "\"");

		        StatefulBeanToCsv<MahasiswaCSV> writer = new StatefulBeanToCsvBuilder<MahasiswaCSV>(response.getWriter())
		                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
		                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
		                .withOrderedResults(false)
		                .build();
		        List<MahasiswaCSV> list = new ArrayList<>();
		        for(int i = 0 ; i < pageFaculty.size(); i++) {
		        	MahasiswaCSV csv = new MahasiswaCSV(pageFaculty.get(i));
		        	list.add(csv);
		        }
		        writer.write(list);
		        
	}
}
