package com.example.demo.entity;

import java.time.LocalDate;

public class SearchItem {
	private String[] searchWordsList;

	private LocalDate startDate;

	private LocalDate endDate;

	public String[] getSearchWordsList() {
		return searchWordsList;
	}

	public void setSearchWordsList(String[] searchWordsList) {
		this.searchWordsList = searchWordsList;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
}
