package com.example.demo.form;

import java.time.LocalDateTime;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TaskForm {
	// タスクID
	private int taskId;
	
	// タイトルは1文字以上100文字以下
	@NotBlank
	@Size(min = 1, max = 100)
    private String title;
	
	// メッセージは0から2の範囲
	@Min(value = 0)
	@Max(value = 2)
	private int message;
	
	// 説明は最大200文字
	@Size(max = 200)
    private String description;
    
	// デッドラインは必須項目
	@NotNull
    private LocalDateTime deadline; 
    
	// ステータスは1から3の範囲
	@Min(value = 0)
	@Max(value = 3)
    private int status;
	
//	進捗率0-100
//	statusが1の時:0
//	statusが3の時:100
	@Min(value=0)
	@Max(value=100)
	private int progress;
	
//エラー定義作成予定
	private int priority;
	
//	作業中の時の条件
	@AssertTrue(message="status:未着手は0、完了は100、作業中は:0、100以外")
	public boolean isProgressdValid() {
		if (status == 1 && progress != 0) return false;
		else if(status == 3 && progress != 100) return false;
		else if(status == 2 && progress ==  0 || status == 2 && progress == 100) return false;//statusが作業中の時
		return true;
	}
	

	
	// 更新日時
	private LocalDateTime updatedAt;
	
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

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public int getMessage() {
		return message;
	}

	public void setMessage(int message) {
		this.message = message;
	}
}
