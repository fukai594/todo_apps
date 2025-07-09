package com.example.demo.entity;

import java.time.LocalDateTime;

public class SearchItem {
	private String[] searchWordsList;

	private LocalDateTime startDate;

	private LocalDateTime endDate;

	public String[] getSearchWordsList() {
		return searchWordsList;
	}

	public void setSearchWordsList(String[] searchWordsList) {
		this.searchWordsList = searchWordsList;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
	
}
