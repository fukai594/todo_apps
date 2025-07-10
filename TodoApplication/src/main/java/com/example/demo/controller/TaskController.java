package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import com.example.demo.form.SearchItemForm;
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
    private HttpSession session;
    
    public TaskController(TaskService taskService, HttpSession session) {
        this.taskService = taskService;
        this.session = session;
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
	public String showTask(
			Authentication loginUser,
			Model model,
			@RequestParam(defaultValue="0") int page,
			@RequestParam(defaultValue="10") int size){
		
		//ログインしているユーザーのloginIdを取得
		String loginId = getLoginId(loginUser);
		//ページネーション
		Pageable pageable = PageRequest.of(page, size);
		List<Task>taskList = taskService.findAll(loginId, pageable);
	 	CheckForm checkForm = new CheckForm();
	 	SearchItemForm searchItemForm = new SearchItemForm();
//	 	session:page,sizeの値を設定
	 	this.session.setAttribute("page", page);
	 	this.session.setAttribute("size", size);
	 	
	 	model.addAttribute("loginId", loginId);
	 	model.addAttribute("page",this.session.getAttribute("page"));//ページングのために必要
	 	model.addAttribute("size",this.session.getAttribute("size"));//ページングのために必要
	 	model.addAttribute("pageSize", taskList.size());
	 	model.addAttribute("checkForm", checkForm);
	 	model.addAttribute("searchItemForm", searchItemForm);
		model.addAttribute("taskList", taskList);
		model.addAttribute("searchHistory", this.session.getAttribute("searchHistory"));

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
	public String showEditForm(
			@RequestParam("taskId") int taskId,
			Model model,
			Authentication loginUser) {
		
	    // タスクIDに基づいてタスクを取得
		String loginId = getLoginId(loginUser);
		try {
			TaskForm taskForm = taskService.getTask(taskId, loginId);
			model.addAttribute("taskForm", taskForm);
			model.addAttribute("page", this.session.getAttribute("page"));
			model.addAttribute("size", this.session.getAttribute("size"));
		}catch(NullPointerException | IllegalStateException ex) {
			model.addAttribute("errorMessage", "不正なアクセスです。");
		    return "task/systemError";
		}				
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
	public String showConfirmForm(
			@Validated TaskForm taskForm,
			BindingResult bindingResult,
			Model model
			) {
		
		// バリデーションチェックでエラーがある場合は変更画面に戻る
		if (bindingResult.hasErrors()) {
			return "task/edit";
		}
		//先頭と末尾の空白を取り除く
		taskForm.setTitle(taskForm.getTitle().strip());
		taskForm.setDescription(taskForm.getDescription().strip());
		model.addAttribute("page", this.session.getAttribute("page"));
		model.addAttribute("size", this.session.getAttribute("size"));
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
		//ログインIDをtaskFormにセットする
		taskForm.setLoginId(getLoginId(loginUser));
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
	public String showDeleteForm(
			@RequestParam("taskId") int taskId,
			Model model,
			Authentication loginUser
	 ) {
		
	    // タスクIDに基づいてタスクを取得
		String loginId = getLoginId(loginUser);
		try {
			TaskForm taskForm = taskService.getTask(taskId, loginId);
			model.addAttribute("taskForm", taskForm);
			model.addAttribute("page", this.session.getAttribute("page"));
			model.addAttribute("size", this.session.getAttribute("size"));
		}catch(NullPointerException | IllegalStateException ex) {
			model.addAttribute("errorMessage", "不正なアクセスです。");
		    return "task/systemError";
		}
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
	public String deleteTask(
			@RequestParam("taskId") int taskId,
			RedirectAttributes redirectAttributes,
			Model model
		) {
		
		//保存処理
		String completeMessage =taskService.delete(taskId);
		
		//redirect先に値を渡す
		redirectAttributes.addFlashAttribute("completeMessage", completeMessage);
		
		
		return "redirect:/task/complete";
	}
	
//	戻る機能
	 @GetMapping("/task/back")
	    public String backToEditPage(
	    		TaskForm taskForm
	    		,Model model
	    		) {
	    	model.addAttribute("taskForm", taskForm);
	    	model.addAttribute("page", this.session.getAttribute("page"));
	    	model.addAttribute("size", this.session.getAttribute("size"));
	    	return "task/edit";
	    }
	
//	 フィルター機能	
	 @GetMapping(value = "/task/filter")
		public String showFilter(
				@Validated CheckForm checkForm,
				BindingResult bindingResult,
				Model model,
				Authentication loginUser
				) {
			
		 	if (bindingResult.hasErrors()) {
				return "task/index";
		 	}
		 	//session:pageの値を再設定
		 	this.session.setAttribute("page",0);
//		 	PageRequest.ofの引数がint,intのため変換処理
			Pageable pageable = PageRequest.of(
					Integer.parseInt(this.session.getAttribute("page").toString()),
					Integer.parseInt(this.session.getAttribute("size").toString())
					);
			//searchで検索したのち、絞り込み検索する場合を考慮し、searchFormを引数にとる
			SearchItemForm searchItemForm = (SearchItemForm)this.session.getAttribute("searchItemForm");
		 	List<Task> taskList = taskService.filterTask(checkForm, getLoginId(loginUser), pageable,searchItemForm);
		 	

		 	model.addAttribute("searchHistory", this.session.getAttribute("searchHistory"));
		 	model.addAttribute("searchItemForm", searchItemForm);
		 	model.addAttribute("page",this.session.getAttribute("page"));
		 	model.addAttribute("size",this.session.getAttribute("size"));
		 	model.addAttribute("pageSize", taskList.size());
		 	model.addAttribute("taskList", taskList);
			
			return "task/index";
		}
//	検索機能
	 @GetMapping(value="task/search")
	 public String searchTasks(
		     @Validated SearchItemForm searchItemForm,
		     BindingResult bindingResult,
		     Model model,
		     Authentication loginUser	     
	     ) {

		 //これをshowTaskを呼び出すように変更が必要
		 if(bindingResult.hasErrors()) {
			 String loginId = getLoginId(loginUser);
				//ページネーション
			 Pageable pageable = PageRequest.of(
					 Integer.parseInt(this.session.getAttribute("page").toString()),
					 Integer.parseInt(this.session.getAttribute("size").toString())
					 );
			List<Task>taskList = taskService.findAll(loginId, pageable);
			CheckForm checkForm = new CheckForm();
			
		 	model.addAttribute("loginId", loginId);
		 	model.addAttribute("page",this.session.getAttribute("page"));//ページングのために必要
		 	model.addAttribute("size",this.session.getAttribute("size"));//ページングのために必要
		 	model.addAttribute("pageSize", taskList.size());
		 	model.addAttribute("checkForm", checkForm);
		 	model.addAttribute("searchItemForm", searchItemForm);
			model.addAttribute("taskList", taskList);
			model.addAttribute("searchHistory", this.session.getAttribute("searchHistory"));

			
			return "task/index";
		 }
		 
		//session:pageの値を再設定
		 this.session.setAttribute("page",0);
//		 	PageRequest.ofの引数がint,intのため変換処理
		 Pageable pageable = PageRequest.of(
				 Integer.parseInt(this.session.getAttribute("page").toString()),
				 Integer.parseInt(this.session.getAttribute("size").toString())
				 );
		 List<Task> taskList = taskService.searchTasks(searchItemForm, getLoginId(loginUser), pageable);
		 //session:searchHistoryの値を設定
		 if (this.session.getAttribute("searchHistory") == null){
			 List<String> searchHistory = new ArrayList<String>();
			 this.session.setAttribute("searchHistory", searchHistory);
		 }
		 //searchHistoryを更新
		 List<String[]> newSearchHistory = taskService.addSearchHistory(
				 searchItemForm.getSearchWords()
				 ,(List<String[]>)this.session.getAttribute("searchHistory")//List<String[]>にキャスト
			);
		 
		 this.session.setAttribute("searchHistory", newSearchHistory);
		 List<String[]> history = (List<String[]>) this.session.getAttribute("searchHistory");
		 
		//filter機能でもsearchItemFormを使用するためセッションとして保存する
			this.session.setAttribute("searchItemForm", searchItemForm);
			
		 CheckForm checkForm = new CheckForm();
		 model.addAttribute("checkForm", checkForm);
		 model.addAttribute("page", this.session.getAttribute("page"));
		 model.addAttribute("size", this.session.getAttribute("size"));
		 model.addAttribute("pageSize", taskList.size());
		 model.addAttribute("taskList", taskList);
		 model.addAttribute("searchHistory", history);
		 return "task/index";
	 }
	 
	 @GetMapping(value="/task/searchHisory")
	 public String searchTasksByHistory(
	       @RequestParam int historyNumber,
	       Model model,
		   Authentication loginUser,
		   RedirectAttributes redirectAttributes
	    ) {
		 String loginId = getLoginId(loginUser);
		//ページネーション
	    Pageable pageable = PageRequest.of(
			 Integer.parseInt(this.session.getAttribute("page").toString()),
			 Integer.parseInt(this.session.getAttribute("size").toString())
			 );
	    
	    List<String[]> history = (List<String[]>) this.session.getAttribute("searchHistory");
	    //historyは0番目から始まる

	    //String[]からStringへ変換、サービスクラスのsearchTasksの型と合わせるため
	    String searchFormWords = String.join(" ", history.get(historyNumber));
	    System.out.println(historyNumber);
	    System.out.println("検索文字"+ searchFormWords);
//	    System.out.println(history.get(historyNumber));
	    //task/indexページ表示のために初期化が必要
	    SearchItemForm searchItemForm = new SearchItemForm();
	    CheckForm checkForm = new CheckForm();

	    searchItemForm.setSearchWords(searchFormWords);
		List<Task>taskList = taskService.searchTasks(searchItemForm, loginId, pageable);
		//filter機能でもsearchItemFormを使用するためセッションとして保存する
		this.session.setAttribute("searchItemForm", searchItemForm);
		
		 model.addAttribute("loginId", loginId);
		 model.addAttribute("page",this.session.getAttribute("page"));//ページングのために必要
		 model.addAttribute("size",this.session.getAttribute("size"));//ページングのために必要
		 model.addAttribute("pageSize", taskList.size());
		 model.addAttribute("checkForm", checkForm);
		 model.addAttribute("searchItemForm", searchItemForm);
		 model.addAttribute("taskList", taskList);
		 model.addAttribute("searchHistory", history);
		 
		 
		 return "task/index";
	 }
}
