package com.javarush.task.task30.task3008.client;

import com.javarush.task.task30.task3008.Connection;
import com.javarush.task.task30.task3008.ConsoleHelper;
import com.javarush.task.task30.task3008.Message;
import com.javarush.task.task30.task3008.MessageType;

import java.io.IOException;
import java.net.Socket;

public class Client {
    protected Connection connection;
    private volatile boolean clientConnected = false;

    public static void main(String[] args) {
        new Client().run();
    }

    //Метод запрашивающий ввод адреса сервера у пользователя
    protected String getServerAddress() {
        return ConsoleHelper.readString();
    }

    //Метод запрашивающий ввод порта сервера
    protected int getServerPort() {
        return ConsoleHelper.readInt();
    }

    //Метод запрашивающий и возвращающий имя пользователя
    protected String getUserName() {
        return ConsoleHelper.readString();
    }

    //Метод всегда возвращает true
    protected boolean shouldSendTextFromConsole() {
        return true;
    }

    //Метод создает и возвращает новый объект класса SocketThread
    protected SocketThread getSocketThread() {
        return new SocketThread();
    }

    //Создает сообщение и отправляет его серверу
    protected void sendTextMessage(String text) {
        try {
            connection.send(new Message(MessageType.TEXT, text));
        } catch (IOException e) {
            ConsoleHelper.writeMessage("Ошибка отправки!");
            clientConnected = false;
        }
    }

    public void run() {
        SocketThread socketThread = getSocketThread();
        socketThread.setDaemon(true);
        socketThread.start();
        synchronized (this) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                ConsoleHelper.writeMessage("Ошибка подключения!");
                return;
            }
            if (clientConnected) {
                ConsoleHelper.writeMessage("Соединение установлено. Для выхода наберите команду 'exit'.");
            } else {
                ConsoleHelper.writeMessage("Произошла ошибка во время работы клиента.");
            }
            String text = ConsoleHelper.readString();
            while (clientConnected && !text.equals("exit")) {
                if (shouldSendTextFromConsole())
                    sendTextMessage(text);
                text = ConsoleHelper.readString();
            }
        }
    }

    public class SocketThread extends Thread {

        //должен выводить текст message в консоль
        protected void processIncomingMessage(String message) {
            ConsoleHelper.writeMessage(message);
        }

        //должен выводить в консоль информацию о присоединении участника к чату
        protected void informAboutAddingNewUser(String userName) {
            ConsoleHelper.writeMessage("Участник с именем " + userName + " присоединился к чату");
        }

        //должен выводить в консоль, что участник покинул чат
        protected void informAboutDeletingNewUser(String userName) {
            ConsoleHelper.writeMessage("Участник с именем " + userName + " покинул чат");
        }

        //устанавливает значение переменной clientConnected и пробуждает ожидающий основной поток класса Client
        protected void notifyConnectionStatusChanged(boolean clientConnected) {
            Client.this.clientConnected = clientConnected;
            synchronized (Client.this){
                Client.this.notify();
            }
        }

        //Этот метод будет представлять клиента серверу
        protected void clientHandshake() throws IOException, ClassNotFoundException {
            while (true) {
                Message message = connection.receive();
                if(message.getType() == MessageType.NAME_REQUEST){
                    connection.send(new Message(MessageType.USER_NAME, getUserName()));
                } else if(message.getType() == MessageType.NAME_ACCEPTED) {
                    notifyConnectionStatusChanged(true);
                    break;
                }else {
                    throw new IOException("Unexpected MessageType");
                }
            }
        }

        //Этот метод будет реализовывать главный цикл обработки сообщений сервера.
        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            while (true) {
                Message message = connection.receive();
                if(message.getType() == MessageType.TEXT) {
                    processIncomingMessage(message.getData());
                } else if(message.getType() == MessageType.USER_ADDED) {
                    informAboutAddingNewUser(message.getData());
                } else if(message.getType() == MessageType.USER_REMOVED) {
                    informAboutDeletingNewUser(message.getData());
                } else {
                    throw new IOException("Unexpected MessageType");
                }
            }
        }

        @Override
        public void run() {
            try {
                connection = new Connection(new Socket(getServerAddress(), getServerPort()));
                clientHandshake();
                clientMainLoop();
            } catch (ClassNotFoundException | IOException e) {
                notifyConnectionStatusChanged(false);
            }
        }
    }
}
