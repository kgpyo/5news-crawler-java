package kr.ac.chungbuk.computer.gyeongpyo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Hankyoreh extends AbsCrawler{
	URL url;
	BufferedReader bufferedReader;
	public Hankyoreh() throws Exception {
		url = new URL("http://www.hani.co.kr/arti/economy/economy_general/770364.html");
	}
	@Override
	protected String crawlingContents() throws IOException {
		bufferedReader = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
		
		String contents = "";
		String line = "";
		boolean isContents = false;
		while((line=bufferedReader.readLine())!=null) {
			if(line.contains("<div class=\"article-text\">")) {
				isContents = true;
			}
			if(isContents == true && line.contains("콘텐츠 유료화 영역")) {
				isContents = false;
				break;
			}
			if(isContents == true) {
				//태그 제거
				line = line.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
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
			if(line.contains("og:title")) {
				line = line.substring(line.indexOf("content=\"")+9, line.indexOf("\" />"));
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
			if(line.contains("article:published_time")) {
				line = line.replaceAll("article:", "");
				line = line.replaceAll("[^0-9:|-]", " ");
				line = line.replace('T', ' ');
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
