package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.ResourceBundle;

public class LoginCommand implements Command {

    private ResourceBundle validCreditCards = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "verifiedCards");
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "login_en");

    String Card;
    String pin;

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("before"));
        ConsoleHelper.writeMessage(res.getString("specify.data"));
        while (true) {
            String numberCard = null;
            try {
                numberCard = ConsoleHelper.readString().trim();
                String numberPin = ConsoleHelper.readString().trim();

                if (!numberCard.matches("\\d*") && numberCard != null && numberCard.length() != 12 &&
                        !numberPin.matches("\\d*") && numberPin != null && numberPin.length() != 4)
                    throw new IllegalArgumentException();

                if (validCreditCards.containsKey(numberCard) && validCreditCards.getString(numberCard).equals(numberPin)) {
                    ConsoleHelper.writeMessage(String.format(res.getString("success.format"), numberCard));
                    break;
                }
            } catch (IllegalArgumentException e) {

            }
            ConsoleHelper.writeMessage(String.format(res.getString("not.verified.format"), numberCard));
            ConsoleHelper.writeMessage(res.getString("try.again.or.exit"));
            ConsoleHelper.writeMessage(res.getString("try.again.with.details"));
        }

    }
}
