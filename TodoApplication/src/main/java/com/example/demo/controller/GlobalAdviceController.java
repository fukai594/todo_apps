package com.example.demo.controller;

import org.springframework.dao.DataAccessException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalAdviceController {
	
    /**
     * データベースアクセスエラーが発生した場合のハンドラーメソッドです。
     * @param ex データアクセス例外
     * @param model SpringのModelオブジェクト
     * @return エラーページのパス
     */
	@ExceptionHandler(DataAccessException.class)
	public String handleDatabaseError(DataAccessException ex, Model model) {
	    model.addAttribute("errorMessage", "データベースアクセスエラーが発生しました。");
	    return "task/systemError";
	}
	
    /**
     * 不正な引数が検出された場合のハンドラーメソッドです。
     * @param ex 不正な引数例外
     * @param model SpringのModelオブジェクト
     * @return エラーページのパス
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "task/systemError";
    }

}