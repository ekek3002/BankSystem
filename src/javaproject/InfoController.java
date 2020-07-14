package javaproject;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InfoController {

	void makeAccount(List list) throws IOException {
		FileSave fs = new FileSave(); // 저장을 위해서 FileSave 객체 선언
		Scanner scan = InfoView.getScanner(); // 스캐너 메소드 사용

		System.out.print("사용하실 아이디를 입력해주세요. : ");
		String id = makeAccountID(list); // 아이디 만들때 중복가입 방지용 메소드
		System.out.print("사용하실 비밀번호를 입력해주세요. : ");
		String pw = scan.next(); // 중복되어도 상관없음. (패스워드 보안용 자릿수 추가, 비밀번호 변경가능)
		System.out.print("고객님의 성함을 입력해주세요. : ");
		String name = scan.next(); // 중복되어도 상관없음.

		Info ifo = new Info(); // Info클래스 객체를 하나 선언해줌.
		ifo.setInfo(id, pw, name); // 위 객체에 아이디, 비밀번호, 이름을 저장해줌.
		for (int i = 0; i < list.getSize(); i++) { // 배열에 0번부터 끝번까지 돌면서
			Info a = list.getLocate(i); // Info 클래스 쓰고버릴 객체 하나를 불러서 배열 i번을 넣음.
										// Info 클래스 내부에서 객체가 생성되면 자동으로 5자리 계좌번호 받음.
			if (ifo.account.equals(a.account)) { // 만약에 이미 배열에 있는 계좌번호면
				i = -1;
				ifo.account = Integer.toString((int) (Math.random() * 90000 + 10000)); // 다시 받음.
				// 0.0~0.9999999999999 *100000 = 00000 ~ 89999 ( 10000~99999)
			}
		}
		list.insert(ifo); // 배열에 해당 객체 저장.
//		fs.fileWriter(ifo);
		fs.listWriter(list); // 배열을 파일에 저장.
		ifo.printInfo(); // 객체의 정보 한번 출력.
	}

	String makeAccountID(List list) {
		Scanner scan = InfoView.getScanner();
		scan.nextLine();
		String id = scan.nextLine(); // 아이디 입력
		for (int i = 0; i < list.getSize(); i++) { // 이미 배열에 저장된 객체들을 0번부터 끝번까지 순차적 검색
			Info ifo = list.getLocate(i); // Info 객체를 하나 선언해서 배열 i번에 저장된 애들 순차적으로 넣어줌.
			if (id.equals(ifo.id)) { // 만약에 입력한 id와 ifo.id(배열i번에 저장된 객체의 id)가 같으면 if에 걸림.
				System.out.println("이미 등록된 아이디입니다.");
				System.out.print("사용하실 아이디를 입력해주세요. : ");
				id = makeAccountID(list); // 중복된 경우 재귀를 통하여 해당 메소드를 다시 돌게 됨.
			}
		}
		return id; // 중복된 값이 없을경우 id를 리턴.
	}

	void update(List list, Info ifo) {
		Scanner scan = InfoView.getScanner();
		System.out.print("변경하실 비밀번호를 입력해주세요 : ");
		ifo.pw = scan.next();
		System.out.print("변경하실 이름을 입력해주세요 : ");
		ifo.name = scan.next();
	}

	void printAll(List list) {
		if (list.getSize() > 0) {
			for (int i = 0; i < list.getSize(); i++) {
				Info ifo = list.getLocate(i);

				System.out.println();
				System.out.println("----------" + i + "----------");
				System.out.println("---아이디 		: " + ifo.id);
				System.out.println("---이름 		: " + ifo.name);
				System.out.println("---계좌번호 	: " + ifo.account);
				System.out.println("---잔액 		: " + ifo.money);
				System.out.println("---비밀번호 	: " + ifo.pw);
				System.out.println("---------------------");
				System.out.println();
			}
		} else {
			System.out.println("계좌 정보가 하나도 없습니다.");
		}

	}

	public void login(List list) throws IOException {
		Scanner scan = InfoView.getScanner(); // 스캐너 받음
		System.out.print("아이디를 입력해주세요. : ");
		scan.nextLine();
		String id = scan.nextLine(); // 아이디 입력
		System.out.print("고객님의 비밀번호를 입력해주세요. : ");
		String pw = scan.next(); // 비밀번호 입력
		for (int i = 0; i < list.getSize(); i++) { // 배열의 0번부터 끝까지 검색
			Info ifo = list.getLocate(i); // Info클래스에서 넣고뺴고쓸 객체하나 생성
			if (id.equals(ifo.id) && pw.equals(ifo.pw)) { // 배열에 저장되어있는 객체중에 아이디랑 비밀번호값이 일치하는 경우에만 로그인.
				System.out.println(ifo.name + "님의 계정으로 로그인 되었습니다.");
				if (ifo.id.equals("관리자")) { // 일치하고 로그인 된 경우, 아이디가 관리자이면
					InfoView.afterLoginAdmin(list, ifo); // 관리자용 로그인 화면으로 이동
				} else {
					InfoView.afterLogin(list, ifo); // 관리자가 아닐경우 일반고객용 로그인 화면으로 이동
				}
				return; // 걸리면 나가기
			}
			if (i == list.getSize() - 1) { // 마지막까지 훑었는데 일치하는 경우가 아니면 출력
				System.out.println("입력하신 아이디, 비밀번호와 일치하는 계정이 없습니다.");
				return; // 안걸리면 다시 로그인 해야함.
			}
		}
	}

	void moneyCheck(Info ifo) { // 매개변수로 객체를 계속 받아서 로그인한 계정의 정보만 출력할수 있게 함.
		System.out.println();
		System.out.println(ifo.name + "님의 아이디");
		System.out.println(ifo.id);
		System.out.println(ifo.name + "님의 계좌번호");
		System.out.println(ifo.account);
		System.out.println(ifo.name + "님의 잔액");
		System.out.println(ifo.money + "원");
		System.out.println();
	}

	// 계좌이체
	void transferAdmin(List list, Info ifo) {
		if (ifo.money == 0) {
			System.out.println("고객님의 계좌 잔액이 0원입니다.");
			return;
		} else {

			Scanner sc = InfoView.getScanner();

			long withdrawal = ifo.money; // 보낼 사람의 돈
			long deposit = 0; // 받을 사람의 돈
			long inMoney = 0; // 이체금액

			// 출금계좌

			boolean flag = true;
			while (flag) {
				try {
					System.out.println();
					System.out.print("송금할 금액을 입력하세요 : ");
					inMoney = sc.nextLong(); // 송금금액 입력
					if (inMoney > withdrawal) { // 잔고보다 이체금이 더 클 때 출력
						System.out.println();
						System.out.println("잔고보다 이체금이 더 큽니다.");
					} else if (inMoney <= 0) { // 입금 및 출금 예외처리와 동일
						System.out.println();
						System.out.println("0원 및 -단위는 입금이 불가합니다");
					} else {
						flag = false;
					}
				} catch (InputMismatchException e) { // 문자열 예외처리
					sc.nextLine();
					System.out.println();
					System.out.println("정상적인 숫자를 입력하세요 ");
					continue;
				}
			}

			long result1 = withdrawal - inMoney; // 출금계좌원금 - 이체 금액


			// 입금계좌
			Info ifo2 = new Info(); // 새로운 Info 클래스 객체 선언

			System.out.println();
			System.out.print("송금 받을 계좌번호를 입력하세요 : ");
			String deAccount = sc.next(); // 송금할 계좌번호 입력

			for (int i = 0; i < list.getSize(); i++) { // 0번부터 끝번까지 검색해서
				ifo2 = list.getLocate(i); // 새로 부른 객체에 i번을 넣음
				if (deAccount.equals(ifo2.account)) { // 검색되는 객체의 계좌번호가 입력받은 계좌번호랑 일치하면
					deposit = ifo2.money; // 그 객체의 원금을 deposit에 불러옴.
					break;
				}
			}

			long result2 = deposit + inMoney; // 입금계좌원금 + 이체 금액
			if (result2 > 9220000000000000001l) {
				System.out.println("받으실 고객님의 잔액이 922경원을 초과하여 이체하실 수 없습니다.");
				return;
			}

			ifo.setMoney(result1); // 출금계좌 잔고 변경
			ifo2.setMoney(result2); // 입금계좌 잔고 변경

			// 계좌이체 내역 출력
			System.out.println();
			System.out.println("기존 잔액 : " + withdrawal);
			System.out.println("고객님의 계좌에서" + inMoney + "원이 출금되었습니다.");
			System.out.println("잔액  : " + result1);
			System.out.println();
			System.out.println("기존 잔액 : " + deposit); // 관리자용이라 출력됨
			System.out.println(ifo2.name + "님의 계좌에 " + inMoney + " 원이 송금되었습니다."); // 고객전용 계좌이체
			System.out.println("잔액  : " + result2); // 관리자용이라 출력됨
			System.out.println();
		}
	}

	// 일반고객용 계좌이체 메소드(관리자용과 동일)
	void transfer(List list, Info ifo) {
		if (ifo.money == 0) {
			System.out.println("고객님의 계좌 잔액이 0원입니다.");
			return;
		} else {
			Scanner sc = InfoView.getScanner();

			long withdrawal = ifo.money; // 출금 계좌에서 받아올 원금
			long deposit = 0; // 입금 계좌에서 받아올 원금
			long inMoney = 0; // 이체금액

			// 출금계좌

			boolean flag = true;
			while (flag) {
				try {
					System.out.println();
					System.out.print("송금할 금액을 입력하세요 : ");
					inMoney = sc.nextLong();
					if (inMoney > withdrawal) {
						System.out.println();
						System.out.println("잔고보다 이체금이 더 큽니다.");
					} else if (inMoney <= 0) {
						System.out.println();
						System.out.println("0원 및 -단위는 입금이 불가합니다");
					} else {
						flag = false;
					}
				} catch (InputMismatchException e) {
					sc.nextLine();
					System.out.println();
					System.out.println("정상적인 숫자를 입력하세요 ");
					continue;
				}
			}

			long result1 = withdrawal - inMoney; // 출금계좌원금 - 이체 금액

			// 계좌 보낼 사람이 금액까지 확인하고 계좌 받을 사람의 정보 입력

			// 입금계좌
			Info ifo2 = new Info();

			System.out.println();
			System.out.print("송금 받을 계좌번호를 입력하세요 : ");
			String deAccount = sc.next();

			for (int i = 0; i < list.getSize(); i++) {
				ifo2 = list.getLocate(i);
				if (deAccount.equals(ifo2.account)) {
					deposit = ifo2.money;
					break;
				}
			}

			long result2 = deposit + inMoney; // 입금계좌원금 + 이체 금액
			if (result2 > 9220000000000000001l) {
				System.out.println("받으실 고객님의 잔액이 922경원을 초과하여 이체하실 수 없습니다.");
				return;
			}

			ifo.setMoney(result1); // 출금계좌 잔고 변경
			ifo2.setMoney(result2); // 입금계좌 잔고 변경

			System.out.println();
			System.out.println("기존 잔액 : " + withdrawal);
			System.out.println("고객님의 계좌에서" + inMoney + "원이 출금되었습니다.");
			System.out.println("잔액  : " + result1);
			System.out.println();
			System.out.println(ifo2.name + "님의 계좌에 " + inMoney + " 원이 송금되었습니다.");
			System.out.println();
		}
	}

	long deposit() { // 입금 금액 예외 처리(문자 누를 경우 에러방지)
		Scanner sc = InfoView.getScanner();
		long moneyInt = 0;
		boolean rd = true;
		while (rd) {
			try {
				System.out.println();
				System.out.print("입금 금액을 입력하세요 : ");
				moneyInt = sc.nextLong(); // 입금금액 입력
				rd = false; // 정확한 금액 입력시 rd가 false가 되면서 while문 탈출
			} catch (InputMismatchException e) {
				sc.nextLine(); // 만약 String 입력할 경우 출력
				System.out.println();
				System.out.println("정상적인 숫자를 입력하세요 ");
				continue; // 다시 처음으로
			}
		}
		return moneyInt; // while문 나와서 입금금액 리턴.
	}

	// 입금
	void input(Info ifo) {

		boolean rd = true;
		while (rd) {
			long moneyInput = deposit(); // 입금 금액 설정
			if (moneyInput <= 0) { // 만약 0보다 작을 경우 출력
				System.out.println();
				System.out.println("0원 및 -단위는 입금이 불가합니다");
				continue; // 다시 while문 처음으로
			}
			// 원금에 넣을 돈 만큼 더함.
			long moneyCopy = ifo.money;
			moneyCopy += moneyInput;
			if (moneyCopy < 9220000000000000001l) {
				ifo.money = moneyCopy;
				rd = false; // 완료되어 while문 탈출
			} else {
				System.out.println("잔액이 922경원을 초과할 순 없습니다.");
				System.out.println("현재 잔액 : " + ifo.money);
				return;
			}
		}
	}

	// 출금 문자열 예외(문자만) // 입금이랑 설명 같음
	long withdraw() {
		Scanner sc = InfoView.getScanner();
		long moneyInt = 0;
		boolean rd = true;
		while (rd) {
			try {
				System.out.println();
				System.out.print("출금 금액을 입력하세요 : ");
				moneyInt = sc.nextLong();
				rd = false;
			} catch (InputMismatchException e) {
				sc.nextLine();
				System.out.println();
				System.out.println("정상적인 숫자를 입력하세요 ");
				continue;
			}
		}
		return moneyInt;
	}

	// 출금
	void output(Info ifo) {

		boolean rd = true;
		while (rd) {
			long moneyInput = withdraw(); // 출금금액입력
			if (moneyInput > ifo.money) { // 가진 금액보다 출금금액이 크면 출력
				System.out.println();
				System.out.println("잔액이 부족합니다");
				System.out.println("현재 잔액 : " + ifo.money); // 현재 잔액 확인
				System.out.println();
				continue;
			} else if (moneyInput <= 0) { // 출금금액이 0보다 작으면 출력
				System.out.println();
				System.out.println("0원 및 -단위는 출금이 불가합니다");
				continue;
			}
			ifo.money -= moneyInput;
			rd = false;
		}
	}

	void goWithdraw(List list, Info ifo) {
		list.goRemove(ifo);
	}
}
