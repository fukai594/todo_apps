
package com.example.demo.entity;

import java.time.LocalDateTime;

/**
 * タスクエンティティクラス
 */
public class Task {
    /**
     * タスクID（自動インクリメントされる一意の識別子）。
     */
    private int taskId;

    /**
     * タスクのタイトル。
     */
    private String title;

    /**
     * タスクの説明。
     */
    private String description;

    /**
     * タスクの締め切り日時。
     */
    private LocalDateTime deadline;

    /**
     * タスクのステータス（例: 1 - 未着手, 2 - 作業中, 3 - 完了）。
     */
    private int status;
    
//    進捗率(0-100)
    private int progress;
    
//    優先度(1-4)
    private int priority;

	/**
     * ユーザーID（タスクを所有するユーザーの識別子）。
     */
    private String loginId;
    /**
     * タスクの作成日時（デフォルトは現在の日時）。
     */
    private LocalDateTime createdAt;

    /**
     * タスクの更新日時（更新時に現在の日時に自動設定）。
     */
    private LocalDateTime updatedAt;

    /**
     * 削除フラグ（タスクが削除されたかどうかを示す）。
     */
    private int deleteFlg;
    
    
    /**
     * メッセージのステータス（例: 1 - 期限切れです, 2 - 期日が迫っています, 0 - なし）
     */
    private int message;

    
    
	public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}
	
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getDeleteFlg() {
        return deleteFlg;
    }

    public void setDeleteFlg(int deleteFlg) {
        this.deleteFlg = deleteFlg;
    }
    
    public int getMessage() {
		return message;
	}

	public void setMessage(int message) {
		this.message = message;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	
}