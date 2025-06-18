package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.Check;
import com.example.demo.entity.Task;

/**
 * タスクエンティティにアクセスするための MyBatis マッパーインターフェースです。
 */
@Mapper
public interface TaskMapper {
	
    /**
     * 全てのタスクを取得します。
     *
     * @return タスクのリスト
     */
    List<Task> findAll();
    
    /**
     * タスクを保存します。
     *
     * @param task 保存するタスク
     */
    void save(Task task);
    
    /**
     * 指定されたタスクIDに対応するタスクを取得します。
     *
     * @param taskId タスクID
     * @return タスク
     */
    Task getTask(int taskId);

    /**
     * タスクを更新します。
     *
     * @param task 更新するタスク
     * @return 更新された行数
     */
    int update(Task task);
    
    /**
     * タスクを削除します。
     *
     * @param task 削除するタスク
     * @return 削除された行数
     */
    int delete(int taskId);
    
    List<Task> filterTask(Check check);
    
}