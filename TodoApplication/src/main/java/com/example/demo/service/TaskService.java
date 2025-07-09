package com.example.demo.service;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.example.demo.entity.Check;
import com.example.demo.entity.Task;
import com.example.demo.form.CheckForm;
import com.example.demo.form.SearchItemForm;
import com.example.demo.form.TaskForm;

/**
 * タスク関連のサービスを提供するインターフェースです。
 */
public interface TaskService {
	
    /**
     * すべてのタスクを取得します。
     *
     * @return タスクのリスト
     */
	List<Task> findAll(String loginId, Pageable pageable);
	
    /**
     * タスクを保存します。
     *
     * @param taskForm タスクのフォームデータ
     * @return 保存完了メッセージ
     */
	String save(TaskForm taskForm);
	
    /**
     * 指定されたタスクIDに対応するタスクを取得します。
     *
     * @param taskId タスクID
     * @return タスクのフォームデータ
     */
	TaskForm getTask(int taskId, String loginId);
	
    /**
     * タスクを削除します。
     *
     * @param taskForm タスクのフォームデータ
     * @return 削除完了メッセージ
     */
	String delete(int taskId);
	
	void updateLoginId(String loginId, String newLoginId);
	
	List<Task> filterTask(CheckForm checkForm, String loginId, Pageable pageable);
	
	List<Task> searchTasks(SearchItemForm searchItemForm, String loginId, Pageable pageable);
	
	//検索履歴の管理
	List<String[]> addSearchHistory(String searchWords, List<String[]> searchHistory);
    /**
     * タスクのフォームデータをタスクエンティティに変換します。
     *
     * @param taskForm タスクのフォームデータ
     * @return タスクエンティティ
     */
    Task convertToTask(TaskForm taskForm);
    
    /**
     * タスクエンティティをタスクのフォームデータに変換します。
     *
     * @param task タスクエンティティ
     * @return タスクのフォームデータ
     */
    TaskForm convertToTaskForm(Task task);
    Check convertToCheck(CheckForm checkForm);
}