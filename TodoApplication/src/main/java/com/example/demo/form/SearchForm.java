package com.example.demo.form;

import java.time.LocalDate;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class SearchForm {
	//空白だけは許可せず、文字が含まれていれば許可する
	@Pattern(regexp = "^(?![\\s　]*$).+", message = "スペースのみは入力できません")
	@Size(max = 200)
	private String searchWords;

	private LocalDate startDate;

	private LocalDate endDate;

	public String getSearchWords() {
		return searchWords;
	}

	public void setSearchWords(String searchWords) {
		this.searchWords = searchWords;
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
