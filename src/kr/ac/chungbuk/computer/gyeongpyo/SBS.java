package kr.ac.chungbuk.computer.gyeongpyo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SBS extends AbsCrawler{
	URL url;
	BufferedReader bufferedReader;
	public SBS() throws Exception {
		url = new URL("http://news.sbs.co.kr/news/endPage.do?news_id=N1002950068&plink=ORI&cooper=NAVER");
	}
	@Override
	protected String crawlingContents() throws IOException {
		bufferedReader = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
		
		String contents = "";
		String line = "";
		boolean isContents = false;
		while((line=bufferedReader.readLine())!=null) {
			if(line.contains("main_text")) {
				isContents = true;
			}
			if(isContents == true && line.contains("기사 본문")) {
				isContents = false;
				break;
			}
			if(isContents == true) {
				//태그 제거
				line = line.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
				line = line.replaceAll("<!--//([가-힣]*)([ ]*)([가-힣]*)-->","");
				line = line.replaceAll("&#39;", "\"");
				line = line.replaceAll("&#34;", "\"");
				line = line.replaceAll("&nbsp;", " ");
				line = line.replaceAll("<!--.*-->","");
				//문자 양옆 공백 제거
				line = line.trim();
				if(line.length() !=0) {
					//문장간의 간격 조절
					if(contents.length() != 0) {
						contents = contents.concat(" ");
					}
					contents = contents.concat(line);
				}
			}
		}
		bufferedReader.close();
		return contents;
	}

	@Override
	protected String crawlingTitle() throws Exception {
		bufferedReader = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
		
		String title = "";
		String line = "";
		while((line=bufferedReader.readLine())!=null) {
			if(line.contains("<title>")) {
				line = line.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
				line = line.replaceAll("&#39;", "\"");
				line = line.replaceAll("&#34;", "\"");
				line = line.replaceAll("&nbsp;", " ");
				line = line.replaceAll("<!--.*-->","");
				line = line.trim();
				title = line;
				break;
			}
		}
		bufferedReader.close();
		return title;
	}

	@Override
	protected Date crawlingDate() throws Exception{
		bufferedReader = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
		Date date = null;
		String line = "";
		while((line=bufferedReader.readLine())!=null) {
			if(line.contains("dateModified")) {
				line = line.replaceAll("[^0-9:|-]", " ");
				line = line.trim();
				SimpleDateFormat simpleDataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				date = simpleDataFormat.parse(line);
				break;
			}
		}
		bufferedReader.close();
		return date;
	}
}

