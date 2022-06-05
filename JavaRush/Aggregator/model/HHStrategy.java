package com.javarush.task.task28.task2810.model;

import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HHStrategy implements Strategy {

    private static final String URL_FORMAT = "https://hh.ru/search/vacancy?text=java+%s&page=%d";

    @Override
    public List<Vacancy> getVacancies(String searchString) {
        List<Vacancy> list = new ArrayList<>();

        int i = 0;
        while (true) {
            try {
                Document document = getDocument(searchString, i);
                Elements elementList = document.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy");

                if(elementList.isEmpty()) break;

                for (Element element : elementList){
                    String title = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").text();
                    String salary = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-compensation").text();
                    String city = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-address").text();
                    String companyName = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-employer").text();
                    String siteName = "http://hh.ru";
                    String url = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").attr("href");

                    Vacancy vacancy = new Vacancy();
                    vacancy.setTitle(title);
                    vacancy.setSalary(salary.length() > 0 ? salary : "");
                    vacancy.setCity(city);
                    vacancy.setCompanyName(companyName);
                    vacancy.setSiteName(siteName);
                    vacancy.setUrl(url);

                    list.add(vacancy);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            i++;
        }

        return list;
    }

    protected Document getDocument(String searchString, int page) throws IOException {
        Document doc = Jsoup.connect(String.format(URL_FORMAT, searchString, page))
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36")
                    .referrer("https://hh.ru/search/vacancy?text=java+Kiev").get();

        return doc;
    }
}
