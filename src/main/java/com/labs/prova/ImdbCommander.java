package com.labs.prova;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.labs.prova.interfaces.Commander;

public class ImdbCommander implements Commander {
    
    private String encodeValue(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    }
    
    public String searchMoviesByTitle(String search) throws IOException {
        search = encodeValue(search);
        search = StringEscapeUtils.escapeHtml4(search);
        String url = String.format("https://www.imdb.com/find?q=%s&s=tt&ref_=fn_tt", search);
        Document doc = Jsoup.connect(url).get();
        Elements findList = doc.getElementsByClass("findList");
        Elements filter = findList.select(".result_text");
        StringBuilder titles = new StringBuilder();
        for(int i = 0; i < filter.size(); i++) {
            titles.append(filter.get(i).text()+"\n");
        }
        int size = titles.length();
        titles.insert(0,String.format("%d:",size));
        return titles.toString();
    }
}
