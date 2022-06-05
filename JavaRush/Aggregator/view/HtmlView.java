package com.javarush.task.task28.task2810.view;

import com.javarush.task.task28.task2810.Controller;
import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class HtmlView implements View {

    private Controller controller;
    private final String filePath = "./4.JavaCollections/src/" + this.getClass().getPackage().getName().replace(".", "/") + "/vacancies.html";

    @Override
    public void update(List<Vacancy> vacancies) {
        try {
            String result = getUpdatedFileContent(vacancies);
            updateFile(result);
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void userCitySelectEmulationMethod() {
        controller.onCitySelect("Odessa");
    }

    private String getUpdatedFileContent(List<Vacancy> list) {
        Document document = null;
        try {
            document = getDocument();
            Element element = document.getElementsByClass("template").first();


            Element elementClone = element.clone();
            elementClone.removeAttr("style");
            elementClone.removeClass("template");

            Elements prevVacancies = document.getElementsByClass("vacancy");

            for (Element redundant : prevVacancies) {
                if (!redundant.hasClass("template")) {
                    redundant.remove();
                }
            }

            for (Vacancy vacancy : list){
                Element elementRes = elementClone.clone();
                elementRes.getElementsByClass("city").first().text(vacancy.getCity());
                elementRes.getElementsByClass("companyName").first().text(vacancy.getCompanyName());
                elementRes.getElementsByClass("salary").first().text(vacancy.getSalary());
                elementRes.getElementsByTag("a").first().text(vacancy.getTitle());
                elementRes.getElementsByTag("a").first().attr("href", vacancy.getUrl());

                element.before(elementRes.outerHtml());

            }

            return document.html();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Some exception occurred";
    }

    private void updateFile(String content) {
        File file = new File(filePath);
        try(FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(content.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Document getDocument() throws IOException {
        Document document = Jsoup.parse(new File(filePath), "UTF-8");
        return document;
    }

}
