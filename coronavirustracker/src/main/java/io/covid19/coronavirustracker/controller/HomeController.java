package io.covid19.coronavirustracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



import io.covid19.coronavirustracker.model.LocationStats;
import io.covid19.coronavirustracker.services.CoronaVirusDataServices;

@Controller
public class HomeController {
	
	@Autowired
	CoronaVirusDataServices coronaVirusDataServices;
	
	@GetMapping("/")
	public String Home(Model model) {
		 java.util.List<LocationStats> allState = coronaVirusDataServices.getAllStats();
		int totalReportedCase= allState.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
		int totalNewReportedCase= allState.stream().mapToInt(stat -> stat.getDiffPrevDay()).sum();
		
		model.addAttribute("locationStats" ,allState);
		model.addAttribute("totalReportedCase" ,totalReportedCase);
		model.addAttribute("totalNewReportedCase" ,totalNewReportedCase);
		
		return "home";
	}

}
