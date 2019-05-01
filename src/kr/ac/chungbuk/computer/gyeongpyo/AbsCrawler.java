package kr.ac.chungbuk.computer.gyeongpyo;

import java.lang.Exception;
import java.util.Date;

public abstract class AbsCrawler{
	public PressDataType doCrawling() throws Exception {
		PressDataType pressData = new PressDataType();
		pressData.date = this.crawlingDate();
		pressData.title = this.crawlingTitle();
		pressData.contents = this.crawlingContents();
		return pressData;
	}
	protected abstract String crawlingContents() throws Exception;
	protected abstract String crawlingTitle() throws Exception;
	protected abstract Date crawlingDate() throws Exception;
}
