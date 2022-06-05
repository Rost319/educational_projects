package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

class InfoCommand implements Command {

    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH +"info_en");

  @Override
  public void execute() {
   Locale.setDefault(Locale.ENGLISH);
   boolean isCounMany = false;

   ConsoleHelper.writeMessage(res.getString("before"));
    for (CurrencyManipulator currencyManipulator : CurrencyManipulatorFactory.getAllCurrencyManipulators()){
        if(currencyManipulator.hasMoney())
        ConsoleHelper.writeMessage(currencyManipulator.getCurrencyCode() + " - " + currencyManipulator.getTotalAmount());
        isCounMany = currencyManipulator.hasMoney();
    }
    if (!isCounMany){
        ConsoleHelper.writeMessage(res.getString("no.money"));
    }
  }

 }
