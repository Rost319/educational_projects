package com.javarush.task.task28.task2810;

import com.javarush.task.task28.task2810.model.HHStrategy;
import com.javarush.task.task28.task2810.model.HabrCareerStrategy;
import com.javarush.task.task28.task2810.model.Model;
import com.javarush.task.task28.task2810.model.Provider;
import com.javarush.task.task28.task2810.view.HtmlView;
import com.javarush.task.task28.task2810.view.View;
import com.javarush.task.task28.task2810.vo.Vacancy;

import java.util.List;

public class Aggregator {

    public static void main(String[] args) {
        Provider provider = new Provider(new HHStrategy());
        Provider providerSecond = new Provider(new HabrCareerStrategy());
        HtmlView view = new HtmlView();
        Model model = new Model(view, provider, providerSecond);
        Controller controller = new Controller(model);
        view.setController(controller);
        view.userCitySelectEmulationMethod();

    }
}
