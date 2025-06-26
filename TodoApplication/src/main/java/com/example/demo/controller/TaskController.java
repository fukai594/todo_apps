package com.example.demo.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.auth.AuthUserDetails;
import com.example.demo.entity.Task;
import com.example.demo.form.CheckForm;
import com.example.demo.form.TaskForm;
import com.example.demo.service.TaskService;


/**
 * Webアプリケーションのタスク関連機能を担当するControllerクラスです。
 * タスクの一覧表示、登録、変更などの機能が含まれています。
 *
 */
@Controller
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    //ログイン中のユーザーのloginIdを取得
	 private String getLoginId(Authentication loginUser) {
		 AuthUserDetails userDetails = (AuthUserDetails)loginUser.getPrincipal();
		 return userDetails.getUser().getLoginId();	 
	 }
    /**
     * タスクの一覧を表示するメソッドです。
     * 
     * @param model タスク一覧をViewに渡すためのSpringのModelオブジェクト
     * @return "task/index" - タスク一覧表示用のHTMLテンプレートのパス
     */
	@RequestMapping(value = "/task/list", method = RequestMethod.GET)
	public String showTask(Authentication loginUser, Model model) {
		
		//ログインしているユーザーのloginIdを取得
		AuthUserDetails userDetails = (AuthUserDetails)loginUser.getPrincipal();
		String loginId = userDetails.getUser().getLoginId();
		
		//タスクの一覧を取得
	 	CheckForm checkForm = new CheckForm();
	 	model.addAttribute("checkForm", checkForm);
	 	
		List<Task> taskList = taskService.findAll(loginId);		
		model.addAttribute("taskList", taskList);
		
		//loginユーザーを表示する
		model.addAttribute("loginId", loginId);
		
		
		return "task/index";
	}
	
	/**
	 * タスクの新規登録画面を表示するメソッドです。
	 * 
	 * @param model タスク一覧をViewに渡すためのSpringのModelオブジェクト
	 * @return "task/edit" - タスク新規登録画面のHTMLテンプレートのパス
	 */
	@GetMapping(value = "/task/add")
	public String showForm(Model model) {
	    // タスクフォームを作成
	    TaskForm taskForm = new TaskForm();
	    
	    model.addAttribute("taskForm", taskForm);
	    return "task/edit";
	}
	
	/**
	 * タスクの変更画面を表示するメソッドです。
	 * 
	 * @param taskId タスクのID
	 * @param model タスク一覧をViewに渡すためのSpringのModelオブジェクト
	 * @return "task/edit" - タスク変更画面のHTMLテンプレートのパス
	 */
	@GetMapping(value = "/task/edit")
	public String showEditForm(@RequestParam("taskId") int taskId,Model model) {
		
	    // タスクIDに基づいてタスクを取得
		TaskForm taskForm = taskService.getTask(taskId);
		
		model.addAttribute("taskForm", taskForm);
		return "task/edit";
	}
	
	
	/**
	 * タスクの確認画面を表示するメソッドです。
	 * 
	 * @param taskForm タスクのフォームデータ
	 * @param bindingResult バリデーション結果を保持するオブジェクト
	 * @param model タスク一覧をViewに渡すためのSpringのModelオブジェクト
	 * @return "task/confirm" - タスク確認画面のHTMLテンプレートのパス
	 */
	@GetMapping(value = "/task/confirm")
	public String showConfirmForm(@Validated TaskForm taskForm, BindingResult bindingResult, Model model) {
		
		// バリデーションチェックでエラーがある場合は変更画面に戻る
		if (bindingResult.hasErrors()) {
			return "task/edit";
		}
		
		model.addAttribute("taskForm", taskForm);
		return "task/confirm";
	}
	
	/**
	 * タスクを保存するメソッドです。
	 * 
	 * @param taskForm タスクのフォームデータ
	 * @param bindingResult バリデーション結果を保持するオブジェクト
	 * @param redirectAttributes リダイレクト時に属性を渡すためのSpringのRedirectAttributesオブジェクト
	 * @param model タスク一覧をViewに渡すためのSpringのModelオブジェクト
	 * @return "redirect:/task/complete" - タスク確認画面へのリダイレクト
	 */
	@PostMapping(value = "/task/save")
	public String saveTask(
			@Validated TaskForm taskForm,
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes,
			Model model,
			Authentication loginUser
		) {
		
		//バリデーションチェック
		if (bindingResult.hasErrors()) {
			// バリデーションエラーがある場合は変更画面に遷移
			return "task/edit";
		}

		String loginId = getLoginId(loginUser);
		//ログインIDをtaskFormにセットする
		taskForm.setLoginId(loginId);
		//保存処理
		String completeMessage =taskService.save(taskForm);
		
		//redirect先に値を渡す
		redirectAttributes.addFlashAttribute("completeMessage", completeMessage);
		
		return "redirect:/task/complete";
	}
	
	/**
	 * タスク完了画面を表示するメソッドです。
	 * 
	 * @return "task/complete" - タスク完了画面のHTMLテンプレートのパス
	 */
    @GetMapping("/task/complete")
    public String showCompletePage() {
        return "task/complete";
    }
    
	/**
	 * タスクの削除確認画面を表示するメソッドです。
	 * 
	 * @param taskForm タスクのフォームデータ
	 * @param model タスク一覧をViewに渡すためのSpringのModelオブジェクト
	 * @return "task/confirm" - タスク確認画面のHTMLテンプレートのパス
	 */
	@GetMapping(value = "/task/delete")
	public String showDeleteForm(@RequestParam("taskId") int taskId, Model model) {
		
	    // タスクIDに基づいてタスクを取得
		TaskForm taskForm = taskService.getTask(taskId);
				
		model.addAttribute("taskForm", taskForm);
		return "task/deleteConfirm";
	}
	
	/**
	 * タスクを削除するメソッドです。
	 * 
	 * @param taskForm タスクのフォームデータ
	 * @param bindingResult バリデーション結果を保持するオブジェクト
	 * @param redirectAttributes リダイレクト時に属性を渡すためのSpringのRedirectAttributesオブジェクト
	 * @param model タスク一覧をViewに渡すためのSpringのModelオブジェクト
	 * @return "redirect:/task/complete" - タスク確認画面へのリダイレクト
	 */
	@PostMapping(value = "/task/delete")
	public String deleteTask(@RequestParam("taskId") int taskId, RedirectAttributes redirectAttributes,Model model) {
		
		//保存処理
		String completeMessage =taskService.delete(taskId);
		
		//redirect先に値を渡す
		redirectAttributes.addFlashAttribute("completeMessage", completeMessage);
		
		
		return "redirect:/task/complete";
	}
	
//	戻る機能
	 @GetMapping("/task/back")
	    public String backToEditPage(TaskForm taskForm,Model model) {
	    	model.addAttribute("taskForm", taskForm);
	    	return "task/edit";
	    }
	
//	 フィルター機能	
	 @GetMapping(value = "/task/filter")
		public String showFilter(@Validated CheckForm checkForm, BindingResult bindingResult, Model model, Authentication loginUser) {
			
		 	if (bindingResult.hasErrors()) {
				return "task/index";
		 	}
		 	
		 	//ログインしているユーザーのloginIdを取得
			AuthUserDetails userDetails = (AuthUserDetails)loginUser.getPrincipal();
			String loginId = userDetails.getUser().getLoginId();
			
		 	List<Task> taskList = taskService.filterTask(checkForm, loginId);

		 	model.addAttribute("taskList", taskList);
			
			return "task/index";
		}
}
