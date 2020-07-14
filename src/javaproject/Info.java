package javaproject;

import java.io.Serializable;

public class Info implements Serializable {
	String name; // 이름
	String id; // 아이디
	String pw; // 패스워드
	long money; // 잔액
	String account; // 계좌번호

	// 생성자
	Info() {
		id = null;
		pw = null;
		name = null;
		money = 0l;
		account = null;
	}

	Info(String id, String pw) {
		this.id = id;
		this.pw = pw;
		name = null;
		money = 0l;
		account = null;
	}

	void setInfo(String id, String pw, String name) {
		this.id = id;
		this.pw = pw;
		this.name = name;
		money = 0l;
		account = Integer.toString((int) (Math.random() * 90000 + 10000)); // 계정생성시 자동으로 5자리 계좌번호랜덤생성
//		account = "0"; // 관리자용 계좌번호
	}

	void setMoney(long money) {
		this.money = money;
	}

	void printInfo() {
		System.out.println("고객님의 계정과 계좌가 생성되었습니다.");
		System.out.println("---아이디 		: " + id);
		System.out.println("---이름 		: " + name);
		System.out.println("---계좌번호 	: " + account);
		System.out.println("---잔액 		: " + money);
		System.out.println("--------------------------");
	}
}
