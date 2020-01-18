package app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RobotTxtController {
	@RequestMapping(value={"/robots.txt", "/robot.txt"})
	@ResponseBody
	public String getRobotsTxt() {
	    return "User-agent: *\n" +
	            "Disallow: /admin\n";
	}

}
