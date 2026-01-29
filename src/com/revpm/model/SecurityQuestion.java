package com.revpm.model;

public class SecurityQuestion {

	private int id;
	private String question;
	private String answer;
	private int userId;

	public SecurityQuestion(int id, String question, String answer, int userId) {

		this.id = id;
		this.question = question;
		this.answer = answer;
		this.userId = userId;
	}

	public int getId() {
		return id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getUserId() {
		return userId;
	}
}
