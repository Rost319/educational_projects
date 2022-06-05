package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ResourceBundle;

public class ConsoleHelper {

    private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));
    private static ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "common_en");


    public static void writeMessage (String message) {
        System.out.println(message);
    }

    public static String readString() throws InterruptOperationException {
        String line = null;
        try {
            line = bis.readLine();
        } catch (IOException e) {

        }
        if(line.toLowerCase().equals("exit")) throw new InterruptOperationException();
        return line;
    }

    public static String askCurrencyCode() throws InterruptOperationException {
        writeMessage(res.getString("choose.currency.code"));
        String currency = readString();
        while (currency.trim().length() != 3){
            writeMessage("The code must be 3 characters long. Try again!");
            currency = readString();
        }
        return currency.trim().toUpperCase();
    }

    public static String[] getValidTwoDigits(String currencyCode) throws InterruptOperationException {
        writeMessage(String.format(res.getString("choose.denomination.and.count.format"), currencyCode));
        String toDigit = readString();
        while (true){
            try {
                String[] masDigit = toDigit.split(" ");
                if(Integer.parseInt(masDigit[0]) > 0 && Integer.parseInt(masDigit[1]) > 0 && masDigit.length == 2)
                    return masDigit;


            }catch (Exception e){

            }
            System.out.println(res.getString("invalid.data"));
            toDigit = readString();
        }
    }

    public static Operation askOperation() throws InterruptOperationException {
        writeMessage(res.getString("choose.operation"));
        writeMessage(String.format("1 - %s, 2 - %s, 3 - %s, 4 - %s" ,
                res.getString("operation.INFO"), res.getString("operation.DEPOSIT"),
                res.getString("operation.WITHDRAW"), res.getString("operation.EXIT")));
        String number = readString();
        while (true){
            try {

               return Operation.getAllowableOperationByOrdinal(Integer.parseInt(number));
            }catch (Exception e){

            }
            System.out.println(res.getString("invalid.data"));
            number = readString();
        }
    }

    public static void printExitMessage(){
        writeMessage(res.getString("the.end"));
    }
}
