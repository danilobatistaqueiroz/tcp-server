package com.labs.prova;

import java.io.IOException;
import java.io.PrintWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.labs.prova.interfaces.Commander;

public class ImdbCommander implements Commander {
    public void printMovies(PrintWriter out, String criteria) throws IOException {
        String titles = searchMoviesByTitle(criteria);
        String[] movies = titles.split("\n");
        for(String movie : movies) {
            out.println(movie);
        }
    }
    
    String searchMoviesByTitle(String search) throws IOException {
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
