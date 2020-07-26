package io.covid19.coronavirustracker.services;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import io.covid19.coronavirustracker.model.LocationStats;
@Service
public class CoronaVirusDataServices {
	
	private static String VIRUS_DATA_URL= "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
	
	private List<LocationStats> allStats= new ArrayList<LocationStats>();
	
	
	
	public List<LocationStats> getAllStats() {
		return allStats;
	}


	@PostConstruct
	@Scheduled(cron = "* * 1 * * *")
	public void fetchCovidData() throws IOException, InterruptedException {
		  List<LocationStats> newStats= new ArrayList<LocationStats>();

		HttpClient client=HttpClient.newHttpClient();
		HttpRequest request=HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA_URL)).build();
		 HttpResponse<String> httpResponse= client.send(request,HttpResponse.BodyHandlers.ofString());
		 
		 StringReader csvReader = new StringReader(httpResponse.body());
		 
		 Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvReader);
		 for (CSVRecord record : records) {
		     //String state = record.get("Province/State");
		     //System.out.println(state);
		    // String Region = record.get("Country/Region");
			 //int lastRecord= record.size()-1;
			 LocationStats locationStats= new LocationStats();
			 locationStats.setCountry(record.get("Country/Region"));
			 locationStats.setState(record.get("Province/State"));
			 int totalLatestCases =Integer.parseInt(record.get(record.size()-1));
			 int totalPrevDayCases =Integer.parseInt(record.get(record.size()-2));
			 
			 locationStats.setLatestTotalCases(totalLatestCases);
			 locationStats.setDiffPrevDay(totalLatestCases - totalPrevDayCases);
			 //System.out.println(locationStats);
			 newStats.add(locationStats);
			 
			 
		 }
		 
		 this.allStats=newStats;
	}

}
