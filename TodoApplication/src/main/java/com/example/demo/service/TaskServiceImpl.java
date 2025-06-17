package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.common.Constants;
import com.example.demo.entity.Task;
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
	public List<Task> findAll() {
		return taskRepository.findAll();
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
		
		//完了メッセージを宣言
		String completeMessage = null;
		
		if(task.getTaskId() != 0) {
			//変更処理の場合
			
			//楽観ロック
			int updateCount =taskRepository.update(task);
			if (updateCount == 0) {
				throw new OptimisticLockingFailureException("楽観ロックエラー");
			}
			//完了メッセージをセット
			completeMessage = Constants.EDIT_COMPLETE;
			return completeMessage;
			
		}else {
			//登録処理の場合
			taskRepository.save(task);
			//完了メッセージをセット
			completeMessage = Constants.REGISTER_COMPLETE;
			return completeMessage;
		}
	}
	
	/**
	 * タスクIDに基づいて1件のタスクを取得し、対応するタスクフォームに変換するメソッドです。
	 *
	 * @param taskId タスクID
	 * @return 対応するタスクフォーム
	 */
	@Override
	public TaskForm getTask(int taskId) {
		
		//タスクを取得
		Task task =taskRepository.getTask(taskId);
		
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
		String completeMessage = Constants.DELETE_COMPLETE;
		return completeMessage;
		
	}
	
	public List<Task> filterTask(boolean checkStatus){
		return taskRepository.filterTask(checkStatus);
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
	    taskForm.setPriority(task.getPriority());
	    taskForm.setUpdatedAt(task.getUpdatedAt());
	    return taskForm;
	    }
	
}