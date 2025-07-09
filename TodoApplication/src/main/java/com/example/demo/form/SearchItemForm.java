package com.example.demo.form;

import java.time.LocalDateTime;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Size;

public class SearchItemForm {
	@Size(max = 200)
	private String searchWords;

	private LocalDateTime startDate;

	private LocalDateTime endDate;
	
//	開始日と終了日の比較。同日でもよい
	@AssertTrue(message="開始日は終了日より前または同日にしてください")
	public boolean isDateValid() {
		if(startDate == null && endDate != null) return false;
		if (startDate != null && endDate != null) {
		    if (startDate.isAfter(endDate)) {
		        // エラー処理など
		    }
		}
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
