package com.example.demo.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.common.Constants;
import com.example.demo.entity.Check;
import com.example.demo.entity.SearchItem;
import com.example.demo.entity.Task;
import com.example.demo.form.CheckForm;
import com.example.demo.form.SearchItemForm;
import com.example.demo.form.TaskForm;
import com.example.demo.repository.TaskRepository;

/**
 * タスク関連のビジネスロジックを担当するサービスクラスです。
 * タスクの検索、保存、更新などの機能を提供します。
 */
@Service
public class TaskServiceImpl implements TaskService{

	@Autowired
	TaskRepository taskRepository;
	
	/**
	 * タスク一覧を取得するメソッドです。
	 *
	 * @return List<Task> タスク一覧。
	 */
	@Override
	public List<Task> findAll(String loginId, Pageable pageable) {
		int limit = pageable.getPageSize();
		int offset = (int)pageable.getOffset();
		return taskRepository.findAll(loginId, limit, offset);
		}
	
	
	/**
	 * タスクを保存するメソッドです。
	 *
	 * @param task タスクエンティティ
	 * @return String 完了メッセージ
	 * @throws OptimisticLockingFailureException 楽観ロックエラーが発生した場合
	 */
	@Override
	@Transactional
	public String save(TaskForm taskForm) {
		
		//変換処理
		Task task = convertToTask(taskForm);

		if(task.getTaskId() != 0) {
			//変更処理の場合
			
			//楽観ロック
			int updateCount =taskRepository.update(task);
			if (updateCount == 0) {
				throw new OptimisticLockingFailureException("楽観ロックエラー");
			}
			//完了メッセージをセット
			return Constants.EDIT_COMPLETE;
			
		}else {
			//登録処理の場合
			taskRepository.save(task);
			//完了メッセージをセット
			return Constants.REGISTER_COMPLETE;
		}
	}
	
	/**
	 * タスクIDに基づいて1件のタスクを取得し、対応するタスクフォームに変換するメソッドです。
	 *
	 * @param taskId タスクID
	 * @return 対応するタスクフォーム
	 */
	@Override
	public TaskForm getTask(int taskId, String loginId) {
		
		//タスクを取得
		Task task =taskRepository.getTask(taskId, loginId);
		
		//変換処理
		TaskForm taskForm =convertToTaskForm(task);
		
		return taskForm;
	}
	
	/**
	 * タスクを削除するメソッドです。
	 *
	 * @param task タスクエンティティ
	 * @return String 完了メッセージ
	 * @throws OptimisticLockingFailureException 楽観ロックエラーが発生した場合
	 */
	@Override
	@Transactional
	public String delete(int taskId) {        
        //削除処理
      	taskRepository.delete(taskId);
		
		//完了メッセージをセット
		return Constants.DELETE_COMPLETE;
		
	}
	
	public List<Task> filterTask(
			CheckForm checkForm,
			String loginId,
			Pageable pageable,
			SearchItemForm searchItemForm
		){
		
		//変換処理
		SearchItem searchItem = new SearchItem();
		if (searchItemForm != null && searchItemForm.getSearchWords() != null){
			searchItem = convertToSearchItem(
					searchItemForm,
					splitWordsToList(searchItemForm.getSearchWords())
				);//複数検索を考慮し、searchWordsListを配列にする			
		}
		Check check = convertToCheck(checkForm);
		int limit = pageable.getPageSize();
		int offset = (int)pageable.getOffset();
		
		return taskRepository.filterTask(check, loginId, limit, offset, searchItem);
	}
	
	public void updateLoginId(String loginId, String newLoginId) {
		taskRepository.updateLoginId(loginId, newLoginId);
	}
	
	 //サービスクラスで複数検索に対するスペース区切りの文字分割の処理を行う
	public List<Task> searchTasks(
			SearchItemForm searchItemForm
			,String loginId
			,Pageable pageable){
		//変換処理
		SearchItem searchItem = convertToSearchItem(
				searchItemForm,
				splitWordsToList(searchItemForm.getSearchWords())
			);
		int limit = pageable.getPageSize();
		int offset = (int)pageable.getOffset();
		return taskRepository.searchTasks(searchItem,loginId, limit, offset);
	}
	//複数の検索をスペース区切りで配列にする
	private String[] splitWordsToList(String searchWords) {
		return searchWords.split("\\s|　+");
	}
	
//	検索履歴を登録
	public List<String[]> registerSearchHistory(
			String searchWords
			,List<String[]> searchHistory
			){
		 String[] newSearchWords = splitWordsToList(searchWords);

		 searchHistory.add(newSearchWords);//最新履歴を追加する

		//重複削除
		 List<String[]> duplicatedSearchHistory=  deleteDuplicateHistory(searchHistory);
		 //検索履歴の管理
		 if(duplicatedSearchHistory.size() > Constants.SEARCH_HISTORY_COUNT) {//sizeは0始まり
			 int range = duplicatedSearchHistory.size() - Constants.SEARCH_HISTORY_COUNT;//検索履歴が複数ある場合に対応
			 duplicatedSearchHistory.subList(0,range).clear();//古い履歴を削除
		 }
		 return duplicatedSearchHistory;
	}
	
	//表示用検索履歴を取得する
		public List<String[]> getHistoryForDisplay(List<String[]> history){
			List<String[]> historyForDisplay = new ArrayList<>();
			 for(String[] item: history) {
				 historyForDisplay.add(item.clone());
			 }
			 
			 for(String[] item:historyForDisplay) {
				 for(int i=0; i < item.length; i++) {
					 if(item[i].length() > Constants.SEARCH_HISTORY_LENGTH_FOR_DESPLAY) {
						 StringBuilder sb = new StringBuilder(item[i]);
						 sb.replace(Constants.SEARCH_HISTORY_LENGTH_FOR_DESPLAY, item[i].length(),"...");
						item[i] = sb.toString();
					 }
				 }
			 }
			 return historyForDisplay;
		}
	
	public List<String[]> deleteDuplicateHistory(List<String[]> searchHistory){
		LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>();

		//重複削除のためにセットに検索履歴を追加する
		for(String[] item: searchHistory) {
			for(String word: item) {
				linkedHashSet.add(word);
			}
		}
		//セットで格納した値を配列にして、リストに追加する
		List<String[]> duplicatedSearchHistory = new ArrayList<>();
		for(String item: linkedHashSet) {
			duplicatedSearchHistory.add(new String[] {item});
		}

		return duplicatedSearchHistory;

		}

	/**
	 * タスクフォームをタスクエンティティに変換するメソッドです。
	 *
	 * @param taskForm タスクフォーム
	 * @return 変換されたタスクエンティティ
	 */
	@Override
	public Task convertToTask(TaskForm taskForm) {
	    Task task = new Task();
	    task.setTaskId(taskForm.getTaskId());
	    task.setTitle(taskForm.getTitle());
	    task.setDescription(taskForm.getDescription());
	    task.setDeadline(taskForm.getDeadline());
	    task.setStatus(taskForm.getStatus());
	    task.setProgress(taskForm.getProgress());
	    task.setLoginId(taskForm.getLoginId());
	    task.setPriority(taskForm.getPriority());
	    task.setUpdatedAt(taskForm.getUpdatedAt());
	    return task;
	    }
	
	/**
	 * タスクエンティティをタスクフォームに変換するメソッドです。
	 *
	 * @param task タスクエンティティ
	 * @return 変換されたタスクフォーム
	 */
	@Override
	public TaskForm convertToTaskForm(Task task) {
		
	    TaskForm taskForm = new TaskForm();
	    taskForm.setTaskId(task.getTaskId());
	    taskForm.setTitle(task.getTitle());
	    taskForm.setMessage(task.getMessage());
	    taskForm.setDescription(task.getDescription());
	    taskForm.setDeadline(task.getDeadline());
	    taskForm.setStatus(task.getStatus());
	    taskForm.setProgress(task.getProgress());
	    taskForm.setLoginId(task.getLoginId());
	    taskForm.setPriority(task.getPriority());
	    taskForm.setUpdatedAt(task.getUpdatedAt());
	    return taskForm;
	    }
	
	public Check convertToCheck(CheckForm checkForm) {
		Check check = new Check();
		check.setCheckStatus1(checkForm.getCheckStatus1());
		check.setCheckStatus2(checkForm.getCheckStatus2());
		check.setCheckStatus3(checkForm.getCheckStatus3());
		check.setCheckPriority1(checkForm.getCheckPriority1());
		check.setCheckPriority2(checkForm.getCheckPriority2());
		check.setCheckPriority3(checkForm.getCheckPriority3());
		check.setCheckPriority4(checkForm.getCheckPriority4());
		return check;
	}
	
	public SearchItem convertToSearchItem(
			SearchItemForm searchItemForm,
			String[] searchWordsList
		) {
		SearchItem searchItem = new SearchItem();
		searchItem.setSearchWordsList(searchWordsList);
		searchItem.setStartDate(searchItemForm.getStartDate());
		searchItem.setEndDate(searchItemForm.getEndDate());
		return searchItem;
	}

}