package javaproject;

import java.io.IOException;
import java.util.Scanner;

public class InfoView {
	private static Scanner scan = new Scanner(System.in);
	static InfoController control = new InfoController();

	static Scanner getScanner() {
		return scan;
	}

	void mainLoop(List list) throws IOException {
		while (true) {
			showIntro(); // 메인메뉴 출력
			String choice = getSelect(); // 메인메뉴 선택
			switch (choice) {
			case "1":
				accountLogin(list); // 로그인
				break;
			case "2":
				accountJoin(list); // 회원가입
				break;
			case "3":
				accountQnA(); // QnA 챗봇
				break;
			case "4":
				System.out.println("===============프로그램을 종료합니다★===============");
				System.exit(0);
			default:
				System.out.println();
				System.out.println("잘못된 입력입니다.");
				break;
			}
		}

	}

	static void afterLogin(List list, Info ifo) throws IOException {
		FileSave fs = new FileSave();
		while (true) {
			InfoView.afterLoginView();
			String choice = getSelect();
			switch (choice) {
			case "1":
				control.moneyCheck(ifo); // 내정보 조회
				break;
			case "2":
				accountUpdate(list, ifo);	// 내정보 수정
				fs.listWriter(list);
				break;
			case "3":
				control.input(ifo); // 입금
				fs.listWriter(list); // 입금저장
				break;
			case "4":
				control.output(ifo); // 출금
				fs.listWriter(list); // 출금 저장
				break;
			case "5":
				control.transfer(list, ifo); // 계좌이체
				fs.listWriter(list); // 계좌이체 저장
				break;
			case "6":
				withdraw(list, ifo); // 회원탈퇴
				break;
			case "7":
				System.out.println();
				System.out.println("로그아웃 되었습니다.");
				System.out.println();
				return;
			default:
				System.out.println();
				System.out.println("잘못된 입력입니다.");
				break;
			}
		}
	}

	static void afterLoginAdmin(List list, Info ifo) throws IOException {
		FileSave fs = new FileSave();
		while (true) {
			afterLoginAdminView(); // 관리자 모드 메뉴 출력
			String choice = getSelect(); // 메뉴 선택
			switch (choice) {
			case "1":
				control.moneyCheck(ifo); // 내정보조회
				break;
			case "2":
				control.input(ifo); // 입금
				fs.listWriter(list); // 입금된 정보 저장
				break;
			case "3":
				control.output(ifo); // 출금
				fs.listWriter(list); // 출금된 정보 저장
				break;
			case "4":
				control.transferAdmin(list, ifo); // 계좌이체
				fs.listWriter(list); // 계좌이체된 정보 저장
				break;
			case "5":
				control.printAll(list); // 관리자용에만 있는 고객정보 전체 출력
				break;
			case "6":
				System.out.println();
				System.out.println("로그아웃 되었습니다.");
				System.out.println();
				return;
			default:
				System.out.println();
				System.out.println("잘못된 입력입니다.");
				break;
			}
		}
	}

	void accountLogin(List list) throws IOException {
		System.out.println();
		System.out.println("=============로그인 화면입니다★=============");
		control.login(list); // 하나 더 들어가는 메소드 (로그인)
		System.out.println("====================================");
		System.out.println();
	}

	void accountJoin(List list) throws IOException {
		System.out.println();
		System.out.println("============회원가입을 시작하겠습니다★============");
		control.makeAccount(list); // 하나 더 들어가는 메소드 (회원가입)
		System.out.println("==========================================");
		System.out.println();
	}

	static void accountUpdate(List list, Info ifo) throws IOException {
		System.out.println();
		System.out.println("=========회원정보 수정을 시작하겠습니다★=========");
		control.update(list, ifo); // 하나 더 들어가는 메소드 (회원가입)
		System.out.println("=========================================");
		System.out.println();
	}

	static void withdraw(List list, Info ifo) throws IOException {
		FileSave fs = new FileSave();
		System.out.println();
		System.out.println("============회원탈퇴을 시작하겠습니다★============");
		System.out.println("회원탈퇴를 하시면 회원님의 모든 정보와 잔액이 소멸됩니다.");
		while (true) {
			System.out.print("정말 회원탈퇴를 하시겠습니까? (예 / 아니오) : ");
			String choice = getScanner().next();
			if (choice.equals("예")) {
				control.goWithdraw(list, ifo);
				fs.listWriter(list); // 회원탈퇴 저장
				System.out.println("회원탈퇴가 완료되었습니다. 프로그램을 종료합니다.");
				System.exit(0);
			} else if (choice.equals("아니오")) {
				System.out.println("회원탈퇴가 취소되었습니다. 이전 화면으로 이동합니다.");
				return;
			} else {
				System.out.println("잘못 입력하셨습니다.");
				System.out.println();
			}
		}
	}

	void accountQnAView() {
		System.out.println("=================");
		System.out.println("문의사항을 선택해주세요.");
		System.out.println("1. 회원가입 문의");
		System.out.println("2. 로그인 문의");
		System.out.println("3. 입금 문의");
		System.out.println("4. 출금 문의");
		System.out.println("5. 계좌이체 문의");
		System.out.println("6. 비밀번호 문의");
		System.out.println("7. 메인 화면으로 이동");
		System.out.println("=================");
	}

	void accountQnA() {
		while (true) {
			accountQnAView();

			String input = getSelect();

			switch (input) {
			case "1":
				System.out.println();
				System.out.println("Q. 회원가입은 어떻게 하나요?");
				System.out.println("A. 메인메뉴에서 2번을 선택하여 사용하실 아이디와 비밀번호,");
				System.out.println("      그리고 이름을 입력하시면 자동으로 계정이 생성됩니다.");
				System.out.println("      계정이 생성되면 회원님의 아이디, 이름, 계좌번호가 화면에 출력됩니다.");
				System.out.println();
				System.out.println("Q. 계좌번호는 어떻게 알 수 있나요?");
				System.out.println("A. 고객님의 계정이 생성됨과 동시에 자동으로 5자리의 계좌번호가 생성됩니다.");
				System.out.println("      계좌번호 재확인은 로그인 후 1번을 눌러 내정보 조회에서 가능합니다.");
				System.out.println();
				break;
			case "2":
				System.out.println();
				System.out.println("Q. 로그인은 어떻게 하나요?");
				System.out.println("A. 메인메뉴에서 1번을 선택하여 회원가입하신 아이디와 비밀번호를 입력하세요.");
				System.out.println();
				break;
			case "3":
				System.out.println();
				System.out.println("Q. 입금은 어떻게 하나요?");
				System.out.println("A. 로그인 후, 2번 입금을 선택하세요.");
				System.out.println("      입금 금액을 입력하면 고객님의 계좌에 입금됩니다.");
				System.out.println();
				break;
			case "4":
				System.out.println();
				System.out.println("Q. 출금은 어떻게 하나요?");
				System.out.println("A. 로그인 후, 3번 출금을 선택하세요.");
				System.out.println("      출금 금액을 입력하면 고객님의 계좌에서 출금됩니다.");
				System.out.println();
				break;
			case "5":
				System.out.println();
				System.out.println("Q. 계좌이체 어떻게 하나요?");
				System.out.println("A. 로그인 후, 4번 계좌이체를 선택하세요.");
				System.out.println("      송금 금액을 입력하고 보내실 분의 계좌번호를 입력하면 송금됩니다.");
				System.out.println();
				break;
			case "6":
				System.out.println();
				System.out.println("Q. 비밀번호를 잊어버렸어요.");
				System.out.println("A. 고객센터로 연락주시면 비밀번호를 은밀하게 보내드립니다.");
				System.out.println("      ㅣ고객센터 정보");
				System.out.println("      ㅣ메일 : bitcamp2@gmail.com");
				System.out.println("      ㅣ연락처 : 070-9999-1111");
				System.out.println();
				break;
			case "7":
				System.out.println("메인 화면으로 이동합니다.");
				return;
			default:
				System.out.println("다시 입력해주세요");
				break;
			}
		}
	}

	static String getSelect() {
		System.out.println();
		System.out.print("원하시는 메뉴의 숫자를 입력해주세요 >>>>>>>>>> ");
		String choice = scan.next();
		return choice;
	}

	void showIntro() {
		System.out.println();
		System.out.println("============써티스뱅크에 오신것을 환영합니다★============");
		System.out.println("----------------------------------------------");
		System.out.println("     1. 로그인   2. 회원가입   3. QnA 궁금이   4. 종료");
		System.out.println("==============================================");
		System.out.println();
	}

	static void afterLoginAdminView() {
		System.out.println();
		System.out.println("============관리자 화면입니당★============");
		System.out.println("     1. 내정보 조회");
		System.out.println("     2. 입금");
		System.out.println("     3. 출금");
		System.out.println("     4. 계좌이체");
		System.out.println("     5. 고객계정 정보 출력");
		System.out.println("     6. 로그아웃");
		System.out.println("====================================");
		System.out.println();
	}

	static void afterLoginView() {
		System.out.println();
		System.out.println("============고객님의 화면입니당★============");
		System.out.println("     1. 내정보 조회");
		System.out.println("     2. 내정보 수정");
		System.out.println("     3. 입금");
		System.out.println("     4. 출금");
		System.out.println("     5. 계좌이체");
		System.out.println("     6. 회원탈퇴");
		System.out.println("     7. 로그아웃");
		System.out.println("====================================");
		System.out.println();
	}

}
