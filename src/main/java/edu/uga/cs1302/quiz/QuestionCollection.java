package edu.uga.cs1302.quiz;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.util.List;
import java.util.ArrayList;
import java.io.Reader;
import java.io.FileReader;
import java.io.IOException;

public class QuestionCollection {
    private List<Country> countries;

    //Constructor
    public QuestionCollection() {
	this.countries = new ArrayList<>();
        readCountries("/home/myid/kkochut/cs1302/country_continent.csv");
    }

    private void readCountries(String csvFilePath) {
	try (Reader reader = new FileReader(csvFilePath);
	     CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)){
	    for (CSVRecord csvRecord : csvParser) {
		String name = csvRecord.get(0);
		String continent = csvRecord.get(1);
		countries.add(new Country(name, continent));
	    }
        } catch (IOException e) {
	    //Diagnosing exception type
	    e.printStackTrace();
	}
    }

    public List<Country> getCountries() {
	return countries;
    }
}

    
	 
