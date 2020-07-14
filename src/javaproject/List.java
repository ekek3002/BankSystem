package javaproject;

import java.io.Serializable;
import java.util.ArrayList;

public class List implements Serializable {
	private ArrayList<Info> list;

	public List(ArrayList<Info> list) { // 리스트 생성자
		this.list = list;
	}

	public int getSize() { // 리스트 사이즈 메소드
		return list.size();
	}

	public void insert(Info ifo) { // 리스트(배열)에 객체 추가 메소드
		list.add(ifo);
	}

	public void remove(int index) { // 인덱스번호에 맞는 해당 객체 삭제
		list.remove(index);
	}

	public int getIndex(Info ifo) { // 객체를 매개변수로 넣으면 해당 객체의 배열 인덱스 번호 리턴
		return list.indexOf(ifo);
	}

	public void goRemove(Info ifo) { // remove메소드와 getIndex메소드를 합쳐 객체를 넣으면 해당 객체를 배열에서 삭제.
		remove(getIndex(ifo));
		// list.remove(indexOf(ifo)); // 이것과 같음
	}

	public Info getLocate(int i) { // 리스트에 인덱스 값을 주었을 때, 해당하는 객체를 꺼내게 하는 메소드
		return list.get(i);
	}
}
