package com.example.demo.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.Check;
import com.example.demo.entity.Task;
import com.example.demo.mapper.TaskMapper;


/**
 * タスク情報にアクセスするためのリポジトリクラスです。
 */
@Repository
public class TaskRepository {
	
	private final TaskMapper taskMapper;
	
    /**
     * コンストラクタ
     *
     * @param taskMapper タスクデータへのマッパー
     */
    public TaskRepository(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    /**
     * 全てのタスクを取得します。
     *
     * @return タスクのリスト
     */
    public List<Task> findAll(String loginId, int limit, int offset) {
        return taskMapper.findAll(loginId, limit, offset);
    }
    
    /**
     * タスクを保存します。
     *
     * @param task 保存するタスク
     */
    public void save(Task task) {
        taskMapper.save(task);
    }
    
    /**
     * 指定されたタスクIDに対応するタスクを取得します。
     *
     * @param taskId タスクID
     * @return タスク
     */
    public Task getTask(int taskId, String loginId) {
        return taskMapper.getTask(taskId, loginId);
    }

    /**
     * タスクを更新します。
     *
     * @param task 更新するタスク
     * @return 更新された行数
     */
    public int update(Task task) {
        return taskMapper.update(task);
    }
    
    /**
     * タスクを削除します。
     *
     * @param task 削除するタスク
     * @return 削除された行数
     */
    public int delete(int taskId) {
        return taskMapper.delete(taskId);
    }

    public List<Task> filterTask(Check check, String loginId, int limit, int offset){
    	return taskMapper.filterTask(check, loginId, limit, offset);
    }

}
