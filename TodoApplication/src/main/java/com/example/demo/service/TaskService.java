package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Check;
import com.example.demo.entity.Task;
import com.example.demo.form.CheckForm;
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
	List<Task> findAll(String loginId);
	
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
	TaskForm getTask(int taskId);
	
    /**
     * タスクを削除します。
     *
     * @param taskForm タスクのフォームデータ
     * @return 削除完了メッセージ
     */
	String delete(int taskId);
	
	
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
    
    List<Task> filterTask(CheckForm checkForm);
    
    Check convertToCheck(CheckForm checkForm);

}