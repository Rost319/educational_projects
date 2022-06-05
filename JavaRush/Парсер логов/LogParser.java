package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LogParser implements IPQuery, UserQuery, DateQuery, EventQuery, QLQuery {

    private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private Path logDir;

    public LogParser(Path logDir) {
        this.logDir = logDir;
    }

    private List<User> readLogDir(){
        List<User> listUser = new ArrayList<>();
        List<String> listString = new ArrayList<>();
        try {
            for (Path path : Files.list(logDir).filter(x -> x.getFileName().toString().endsWith(".log")).collect(Collectors.toList())) {
                Files.lines(path).forEach(listString::add);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        listUser = listString.stream().map(x -> new User(x)).collect(Collectors.toList());

        return listUser;
    }

    private List<User> filtrationDate(Date after, Date before) {
        List<User> listUser = new ArrayList<>();
        if(after == null){
            after = new Date(Long.MIN_VALUE);
        }

        if(before == null){
            before = new Date(Long.MAX_VALUE);
        }

        for (User user : readLogDir()){
             if(user.getDate().after(after) && user.getDate().before(before)) {
                listUser.add(user);
            }
        }
        return listUser;
    }


    @Override
    public int getNumberOfUniqueIPs(Date after, Date before) {
        return getUniqueIPs(after,before).size();
    }

    @Override
    public Set<String> getUniqueIPs(Date after, Date before) {
        return filtrationDate(after,before).stream()
                .map(x -> x.getIp())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before) {
        return filtrationDate(after,before).stream()
                .filter(x -> x.getUserName().equals(user))
                .map(x -> x.getIp())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getIPsForEvent(Event event, Date after, Date before) {
        return filtrationDate(after,before).stream()
                .filter(x -> x.getEvent().equals(event))
                .map(x -> x.getIp())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getIPsForStatus(Status status, Date after, Date before) {
        return filtrationDate(after,before).stream()
                .filter(x -> x.getStatus().equals(status))
                .map(x -> x.getIp())
                .collect(Collectors.toSet());
    }


    @Override
    public Set<String> getAllUsers() {
        return readLogDir().stream()
                .map(x -> x.getUserName())
                .collect(Collectors.toSet());
    }

    @Override
    public int getNumberOfUsers(Date after, Date before) {
        return filtrationDate(after,before).stream()
                .map(x -> x.getUserName())
                .collect(Collectors.toSet()).size();
    }

    @Override
    public int getNumberOfUserEvents(String user, Date after, Date before) {
        return filtrationDate(after,before).stream()
                .filter(x -> x.getUserName().equals(user))
                .map(x -> x.getEvent())
                .collect(Collectors.toSet()).size();
    }

    @Override
    public Set<String> getUsersForIP(String ip, Date after, Date before) {
        return filtrationDate(after, before).stream()
                .filter(x -> x.getIp().equals(ip))
                .map(x -> x.getUserName())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getLoggedUsers(Date after, Date before) {
        return filtrationDate(after, before).stream()
                .filter(x -> x.getEvent().equals(Event.LOGIN))
                .map(x -> x.getUserName())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getDownloadedPluginUsers(Date after, Date before) {
        return filtrationDate(after, before).stream()
                .filter(x -> x.getEvent().equals(Event.DOWNLOAD_PLUGIN))
                .map(x -> x.getUserName())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getWroteMessageUsers(Date after, Date before) {
        return filtrationDate(after, before).stream()
                .filter(x -> x.getEvent().equals(Event.WRITE_MESSAGE))
                .map(x -> x.getUserName())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before) {
        return filtrationDate(after, before).stream()
                .filter(x -> x.getEvent().equals(Event.SOLVE_TASK))
                .map(x -> x.getUserName())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before, int task) {
        return filtrationDate(after, before).stream()
                .filter(x -> x.getEvent().equals(Event.SOLVE_TASK))
                .filter(x -> x.getNumberTask() == task)
                .map(x -> x.getUserName())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before) {
        return filtrationDate(after, before).stream()
                .filter(x -> x.getEvent().equals(Event.DONE_TASK))
                .map(x -> x.getUserName())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before, int task) {
        return filtrationDate(after, before).stream()
                .filter(x -> x.getEvent().equals(Event.DONE_TASK))
                .filter(x -> x.getNumberTask() == task)
                .map(x -> x.getUserName())
                .collect(Collectors.toSet());
    }


    @Override
    public Set<Date> getDatesForUserAndEvent(String user, Event event, Date after, Date before) {
        return filtrationDate(after, before).stream()
                .filter(x -> x.getUserName().equals(user))
                .filter(x -> x.getEvent().equals(event))
                .map(x -> x.getDate())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Date> getDatesWhenSomethingFailed(Date after, Date before) {
        return filtrationDate(after, before).stream()
                .filter(x -> x.getStatus().equals(Status.FAILED))
                .map(x -> x.getDate())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Date> getDatesWhenErrorHappened(Date after, Date before) {
        return filtrationDate(after, before).stream()
                .filter(x -> x.getStatus().equals(Status.ERROR))
                .map(x -> x.getDate())
                .collect(Collectors.toSet());
    }

    @Override
    public Date getDateWhenUserLoggedFirstTime(String user, Date after, Date before) {
        List<Date> list = filtrationDate(after, before).stream()
                .filter(x -> x.getUserName().equals(user))
                .filter(x -> x.getEvent().equals(Event.LOGIN))
                .map(x -> x.getDate())
                .collect(Collectors.toList());
        
        return list.stream().min(Date::compareTo).orElse(null);
    }

    @Override
    public Date getDateWhenUserSolvedTask(String user, int task, Date after, Date before) {
        List<Date> list = filtrationDate(after, before).stream()
                .filter(x -> x.getUserName().equals(user))
                .filter(x -> x.getEvent().equals(Event.SOLVE_TASK))
                .filter(x -> x.getNumberTask() == task)
                .map(x -> x.getDate())
                .collect(Collectors.toList());

        return list.stream().min(Date::compareTo).orElse(null);
    }

    @Override
    public Date getDateWhenUserDoneTask(String user, int task, Date after, Date before) {
        List<Date> list = filtrationDate(after, before).stream()
                .filter(x -> x.getUserName().equals(user))
                .filter(x -> x.getEvent().equals(Event.DONE_TASK))
                .filter(x -> x.getNumberTask() == task)
                .map(x -> x.getDate())
                .collect(Collectors.toList());

        return list.stream().min(Date::compareTo).orElse(null);
    }

    @Override
    public Set<Date> getDatesWhenUserWroteMessage(String user, Date after, Date before) {
        return filtrationDate(after, before).stream()
                .filter(x -> x.getUserName().equals(user))
                .filter(x -> x.getEvent().equals(Event.WRITE_MESSAGE))
                .map(x -> x.getDate())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Date> getDatesWhenUserDownloadedPlugin(String user, Date after, Date before) {
        return filtrationDate(after, before).stream()
                .filter(x -> x.getUserName().equals(user))
                .filter(x -> x.getEvent().equals(Event.DOWNLOAD_PLUGIN))
                .map(x -> x.getDate())
                .collect(Collectors.toSet());
    }


    @Override
    public int getNumberOfAllEvents(Date after, Date before) {
        return getAllEvents(after, before).size();
    }

    @Override
    public Set<Event> getAllEvents(Date after, Date before) {
        return filtrationDate(after, before).stream()
                .map(x -> x.getEvent())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getEventsForIP(String ip, Date after, Date before) {
        return filtrationDate(after, before).stream()
                .filter(x -> x.getIp().equals(ip))
                .map(x -> x.getEvent())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getEventsForUser(String user, Date after, Date before) {
        return filtrationDate(after, before).stream()
                .filter(x -> x.getUserName().equals(user))
                .map(x -> x.getEvent())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getFailedEvents(Date after, Date before) {
        return filtrationDate(after, before).stream()
                .filter(x -> x.getStatus().equals(Status.FAILED))
                .map(x -> x.getEvent())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getErrorEvents(Date after, Date before) {
        return filtrationDate(after, before).stream()
                .filter(x -> x.getStatus().equals(Status.ERROR))
                .map(x -> x.getEvent())
                .collect(Collectors.toSet());
    }

    @Override
    public int getNumberOfAttemptToSolveTask(int task, Date after, Date before) {
        return filtrationDate(after, before).stream()
                .filter(x -> x.getEvent().equals(Event.SOLVE_TASK))
                .filter(x -> x.getNumberTask() == task)
                .collect(Collectors.toList()).size();
    }

    @Override
    public int getNumberOfSuccessfulAttemptToSolveTask(int task, Date after, Date before) {
        return filtrationDate(after, before).stream()
                .filter(x -> x.getEvent().equals(Event.DONE_TASK))
                .filter(x -> x.getNumberTask() == task)
                .collect(Collectors.toList()).size();
    }

    @Override
    public Map<Integer, Integer> getAllSolvedTasksAndTheirNumber(Date after, Date before) {
        Map<Integer, Integer> map = new HashMap<>();
        for (User user : filtrationDate(after, before)){
            if(user.getEvent().equals(Event.SOLVE_TASK)){
                map.put(user.getNumberTask(), getNumberOfAttemptToSolveTask(user.getNumberTask(), after, before));
            }
        }
        return map;

    }

    @Override
    public Map<Integer, Integer> getAllDoneTasksAndTheirNumber(Date after, Date before) {
        Map<Integer, Integer> map = new HashMap<>();
        for (User user : filtrationDate(after, before)){
            if(user.getEvent().equals(Event.DONE_TASK)){
                map.put(user.getNumberTask(), getNumberOfSuccessfulAttemptToSolveTask(user.getNumberTask(), after, before));
            }
        }
        return map;
    }


    @Override
    public Set<Object> execute(String query) {
        Set<Object> set = null;
        String[] masString = query.split(" ");
        String field1 = masString[1];


        if(masString.length <= 2){
            set = executeMap(field1, readLogDir().stream().collect(Collectors.toSet()));
        } else if(masString.length > 2 && masString.length <= 10){
            String[] dopMasString = query.split("=");
            String field2 = masString[3];
            String value1 = dopMasString[1];
            String value = value1.substring(2, value1.length()-1);
            Set<User> setUser = executeFilter(field2, value, readLogDir().stream().collect(Collectors.toSet()));
            set = executeMap(field1, setUser);
        } else if (masString.length > 10){
            String[] dopMasString2 = query.split("\"");
            String field2 = masString[3];
            String value1 = dopMasString2[1];
            try {
                Date date1 = format.parse(dopMasString2[3]);
                Date date2 = format.parse(dopMasString2[5]);
                List<User> setCompareToDate = filtrationDate(date1, date2);
                Set<User> setUser = executeFilter(field2, value1, setCompareToDate.stream().collect(Collectors.toSet()));
                set = executeMap(field1, setUser);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return set;
    }

    private Set<User> executeFilter(String field2, String value1, Set<User> set) {
        Set<User> setResult = null;
        switch (field2){
            case "ip":
                setResult = set.stream().filter(x -> x.getIp().equals(value1)).collect(Collectors.toSet());
                break;
            case "user":
                setResult = set.stream().filter(x -> x.getUserName().equals(value1)).collect(Collectors.toSet());
                break;
            case "date":
                try {
                    Date date = format.parse(value1);
                    setResult = set.stream().filter(x -> x.getDate().equals(date)).collect(Collectors.toSet());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case "event":
                Event event = null;
                if(value1.startsWith("SOLVE_TASK") || value1.startsWith("DONE_TASK")){
                    String[] masDop = value1.split(" ");
                    event = Event.valueOf(masDop[0]);
                }else {
                    event = Event.valueOf(value1);
                }
                Event finalEvent = event;
                setResult = set.stream().filter(x -> x.getEvent().equals(finalEvent)).collect(Collectors.toSet());
                break;
            case "status":
                Status status = Status.valueOf(value1);
                setResult = set.stream().filter(x -> x.getStatus().equals(status)).collect(Collectors.toSet());
                break;
        }
        return setResult;
    }


    private Set<Object> executeMap(String field1, Set<User> set) {
        Set<Object> setResult = null;
        switch (field1){
            case "ip":
                setResult = set.stream().map(x -> x.getIp()).collect(Collectors.toSet());
                break;
            case "user":
                setResult = set.stream().map(x -> x.getUserName()).collect(Collectors.toSet());
                break;
            case "date":
                setResult = set.stream().map(x -> x.getDate()).collect(Collectors.toSet());
                break;
            case "event":
                setResult = set.stream().map(x -> x.getEvent()).collect(Collectors.toSet());
                break;
            case "status":
                setResult = set.stream().map(x -> x.getStatus()).collect(Collectors.toSet());
                break;
        }
        return setResult;
    }
}