package com.javarush.task.task30.task3008.client;

import com.javarush.task.task30.task3008.ConsoleHelper;
import com.javarush.task.task30.task3008.Message;
import com.javarush.task.task30.task3008.MessageType;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class BotClient extends Client {

    public static void main(String[] args) {
        new BotClient().run();
    }

    @Override
    protected SocketThread getSocketThread() {
        return new BotSocketThread();
    }

    @Override
    protected boolean shouldSendTextFromConsole() {
        return false;
    }

    @Override
    protected String getUserName() {
        int x = (int) (Math.random() * 100);
        String name = "date_bot_" + x;
        return name;
    }

    public class BotSocketThread extends SocketThread {

        @Override
        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            sendTextMessage("Привет чатику. Я бот. Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды.");
            super.clientMainLoop();
        }

        @Override
        protected void processIncomingMessage(String message) {
            ConsoleHelper.writeMessage(message);
            if(message == null) return;

            String[] mas = message.split(":");
            if(mas.length != 2) return;

            String name = mas[0];
            String text = mas[1].trim();

            SimpleDateFormat sp;

                switch (text) {
                    case "дата" :
                        sp = new SimpleDateFormat("d.MM.YYYY");
                        break;
                    case "день" :
                        sp = new SimpleDateFormat("d");
                        break;
                    case "месяц" :
                        sp = new SimpleDateFormat("MMMM");
                        break;
                    case "год" :
                        sp = new SimpleDateFormat("YYYY");
                        break;
                    case "время" :
                        sp = new SimpleDateFormat("H:mm:ss");
                        break;
                    case "час" :
                        sp = new SimpleDateFormat("H");
                        break;
                    case "минуты" :
                        sp = new SimpleDateFormat("m");
                        break;
                    case "секунды" :
                        sp = new SimpleDateFormat("s");
                        break;
                    default:
                        return;
                }

            Date date = Calendar.getInstance().getTime();
            String ms = "Информация для " + name + ": " + sp.format(date);
            sendTextMessage(ms);
        }
    }
}
