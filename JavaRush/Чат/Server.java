package com.javarush.task.task30.task3008;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(ConsoleHelper.readInt())) {
            System.out.println("Сервер запущен!");
            while (true){
                Handler handler = new Handler(serverSocket.accept());
                handler.start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private static class Handler extends Thread {
        private Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        //Метод в качестве параметра принимает соединение connection, а возвращает имя нового клиента (рукопожатие)
        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException {
            while (true){
                connection.send(new Message(MessageType.NAME_REQUEST));
                Message message = connection.receive();
                String name = message.getData();
                if(message.getType() == MessageType.USER_NAME && !message.getData().isEmpty() && !name.equals("") && !connectionMap.containsKey(name)){
                    connectionMap.put(name, connection);
                    connection.send(new Message(MessageType.NAME_ACCEPTED));
                    return name;
                }
            }

        }

        //Метод отправки клиенту (новому участнику) информации об остальных клиентах (участниках) чата.
        private void notifyUsers(Connection connection, String userName) throws IOException {
            for(Map.Entry<String, Connection> entry : connectionMap.entrySet()){
                if(!entry.getKey().equals(userName)){
                    connection.send(new Message(MessageType.USER_ADDED, entry.getKey()));
                }
            }
        }

        //Метод обработки сообщений сервером.
        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException {
            while (true){
                Message message = connection.receive();
                if(message.getType() == MessageType.TEXT){
                    sendBroadcastMessage(new Message(message.getType(), userName + ": " + message.getData()));
                }else ConsoleHelper.writeMessage("Ошибка ввода");
            }
        }

        @Override
        public void run() {
            ConsoleHelper.writeMessage("Установлено новое соединение с удаленным адресом: " + socket.getRemoteSocketAddress());
            try (Connection connection = new Connection(socket)) {
                    String name = serverHandshake(connection);   //Получаем имя нового пользователя
                    sendBroadcastMessage(new Message(MessageType.USER_ADDED, name));  //Рассылаем всем участникам чата инфо о новом пользователе
                    notifyUsers(connection, name);      //Информируем нового пользователя о других участниках
                    serverMainLoop(connection, name);   //Обмен сообщениями
                    connectionMap.remove(name);         //Удаление пользователя
                    sendBroadcastMessage(new Message(MessageType.USER_REMOVED, name));  //Информирования участников об удалении пользователя
            } catch (IOException | ClassNotFoundException e) {
                ConsoleHelper.writeMessage("Произошла ошибка при обмене данными с удаленным адресом!");
            }
            ConsoleHelper.writeMessage("Cоединение с удаленным адресом закрыто!");
        }
    }

    //Метод который должен отправлять сообщение message всем соединениям из connectionMap
    public static void sendBroadcastMessage(Message message) {
        for (Map.Entry<String, Connection> connectionEntry : connectionMap.entrySet()){
            try {
                connectionEntry.getValue().send(message);
            } catch (IOException e) {
                System.out.println("Не смогли отправить сообщение!");
            }
        }
    }
}
