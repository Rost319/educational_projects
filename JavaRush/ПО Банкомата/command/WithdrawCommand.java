package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;
import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.Map;
import java.util.ResourceBundle;

class WithdrawCommand implements Command {

    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "withdraw_en");

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("before"));
        String currencyCode = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator currencyManipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode);

        ConsoleHelper.writeMessage(res.getString("specify.amount"));
        String digit = ConsoleHelper.readString();
        while (true) {
            try {
                int sum = Integer.parseInt(digit);
                if (sum <= 0) {
                    ConsoleHelper.writeMessage(res.getString("Please enter a positive number!"));
                    digit = ConsoleHelper.readString();
                    continue;
                }
                if (currencyManipulator.isAmountAvailable(sum)) {
                    Map<Integer, Integer> map = currencyManipulator.withdrawAmount(sum);
                    ConsoleHelper.writeMessage("Issued by: ");
                    for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                        ConsoleHelper.writeMessage("\t" + entry.getKey() + " - " + entry.getValue());
                    }
                    ConsoleHelper.writeMessage(String.format(res.getString("success.format"), sum, currencyCode));
                    break;
                } else
                    ConsoleHelper.writeMessage(res.getString("not.enough.money"));
            } catch (NotEnoughMoneyException e) {
                ConsoleHelper.writeMessage(res.getString("exact.amount.not.available"));
            } catch (Exception e) {
                ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));            }
            digit = ConsoleHelper.readString();
        }
    }
}
