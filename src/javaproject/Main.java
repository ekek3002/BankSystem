package javaproject;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		InfoView view = new InfoView(); // 메인루프 메소드 가져오기 위해 InfoView 객체 선언
		FileSave fs = new FileSave(); // 파일을 저장하고 불러오기 위해서 FileSave 클래스 객체 선언
		List arrlist = fs.listReader(); // list.list를 읽어와서 List클래스에 있는 배열객체(list)를 불러온다. 읽고 덮어쓰기 반복됨.
		if (arrlist == null) {
			ArrayList<Info> list = new ArrayList<Info>(); // ArrayList인 list 선언
			arrlist = new List(list); // 읽어들일 파일이 없을 때, 데이터 쌓기 시작하는 용. 덮어씌우기만 됨.(읽기x)
		}
		view.mainLoop(arrlist); // 메인루프 반복
	}
}
