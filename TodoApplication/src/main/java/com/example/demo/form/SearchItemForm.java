package com.example.demo.form;

import java.time.LocalDateTime;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Size;

public class SearchItemForm {
	@Size(max = 200)
	private String searchWords;

	private LocalDateTime startDate;

	private LocalDateTime endDate;

	@AssertTrue(message="開始日・終了日の指定が間違っています")
	public boolean isDateValid() {
		if(startDate == null && endDate != null) return false;
		if (startDate != null && endDate != null) {
		    if (startDate.isAfter(endDate)) {
		        return false;
		    }
		}
		return true;
	}
	//日付を入力した際は必ず、検索文字を入力する
	@AssertTrue(message="検索したい文字を入力してください")
	public boolean isInputFieldsValid() {
		if(startDate != null && searchWords == "")return false;
		return true;
	}
	
	
	public String getSearchWords() {
		return searchWords;
	}

	public void setSearchWords(String searchWords) {
		this.searchWords = searchWords;
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
