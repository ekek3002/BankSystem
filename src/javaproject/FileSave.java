package javaproject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class FileSave {

//	String fileSave(Info a) {
//		return a.account;
//	}

//	void fileWriter(Info ifo) throws IOException {
//		FileSave fs = new FileSave();
//		OutputStream os = new FileOutputStream(fs.fileSave(ifo) + ".xxs");
//		ObjectOutputStream out = new ObjectOutputStream(os);
//
//		out.writeObject(ifo);
//
//		out.close();
//	}
//
//	Info fileReader(String account) throws IOException, ClassNotFoundException {
//		InputStream is = new FileInputStream(account + ".xxs");
//		ObjectInputStream in = new ObjectInputStream(is);
//
//		Info ifo = (Info) in.readObject();
//		
//		in.close();
//
//		return ifo;
//	}

	void listWriter(List list) throws IOException {
		OutputStream os = new FileOutputStream("list.list"); // bridge
		ObjectOutputStream out = new ObjectOutputStream(os); // 창고

		out.writeObject(list);

		out.close();
	}

	List listReader() throws IOException, ClassNotFoundException {

		File file = new File("list.list");
		if (file.isFile()) {
			InputStream is = new FileInputStream("list.list"); // bridge
			ObjectInputStream in = new ObjectInputStream(is); // 창고

			List list = (List) in.readObject();
			in.close();

			return list;
		} else {
			return null;
		}
	}

}
