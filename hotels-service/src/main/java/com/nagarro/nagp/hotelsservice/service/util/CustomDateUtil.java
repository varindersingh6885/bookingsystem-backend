package com.nagarro.nagp.hotelsservice.service.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomDateUtil {
	public static List<String> getIndividualDates(String startDate, String endDate) {
		List<String> res = new ArrayList<>();
		LocalDate startDateLocal = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		LocalDate endDateLocal = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		
		List<LocalDate> dates = startDateLocal.datesUntil(endDateLocal).collect(Collectors.toList());
		for(LocalDate date : dates) {
		    res.add(date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		}
		return res;
	}
}
