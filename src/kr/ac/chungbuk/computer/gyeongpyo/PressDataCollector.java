package kr.ac.chungbuk.computer.gyeongpyo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PressDataCollector {
	private List<AbsCrawler> absCrawler = new ArrayList<AbsCrawler>();
	private DataManger dataManger = new DataManger();
	String filename = "d:\\webdata_∞Ì∞Ê«•.txt";
	public void collectPressData() {
		try {
		absCrawler.add(new Hankyoreh());
		absCrawler.add(new SBS());
		absCrawler.add(new JTBC());
		absCrawler.add(new Joongang());
		absCrawler.add(new Donga());
		for(AbsCrawler data : absCrawler) {
			dataManger.saveData(data.doCrawling(), filename);
		}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
