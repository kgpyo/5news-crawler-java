package kr.ac.chungbuk.computer.gyeongpyo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JTBC extends AbsCrawler{

	URL url;
	BufferedReader bufferedReader;
	public JTBC() throws Exception {
		url = new URL("http://news.jtbc.co.kr/html/222/NB11258222.html");
	}
	@Override
	protected String crawlingContents() throws IOException {
		bufferedReader = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
		
		String contents = "";
		String line = "";
		boolean isContents = false;
		while((line=bufferedReader.readLine())!=null) {
			if(line.contains("article_content")) {
				isContents = !isContents;
			}
			if(isContents == true) {
				//ÅÂ±× Á¦°Å
				line = line.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
				line = line.replaceAll("<!--//([°¡-ÆR]*)([ ]*)([°¡-ÆR]*)-->","");
				line = line.replaceAll("&#39;", "\"");
				line = line.replaceAll("&#34;", "\"");
				line = line.replaceAll("&nbsp;", " ");
				line = line.replaceAll("<!--.*-->","");
				//¹®ÀÚ ¾ç¿· °ø¹é Á¦°Å
				line = line.trim();
				if(line.length() !=0) {
					//¹®Àå°£ÀÇ °£°Ý Á¶Àý
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
				line = line.substring(line.indexOf("content")+new String("content=\"").length(), 
						line.indexOf("\" />"));
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
