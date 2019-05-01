package kr.ac.chungbuk.computer.gyeongpyo;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class DataManger {
	public void saveData(PressDataType data, String filename) throws IOException {
		PrintWriter printWriter = new PrintWriter(new FileWriter(filename, true));
		printWriter.print(data.date + "\t" + data.title + "\t" + data.contents + "\n");
		printWriter.close();
	}
}
