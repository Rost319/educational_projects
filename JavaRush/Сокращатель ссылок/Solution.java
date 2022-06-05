package com.javarush.task.task33.task3310;


import com.javarush.task.task33.task3310.strategy.*;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Solution {

    public static Set<Long> getIds(Shortener shortener, Set<String> strings) {
        Set<Long> set = new HashSet<>();
        for(String s : strings) {
           set.add(shortener.getId(s));
        }
        return set;
    }

    public static Set<String> getStrings(Shortener shortener, Set<Long> keys) {
        Set<String> set = new HashSet<>();
        for(Long l : keys){
            set.add(shortener.getString(l));
        }
        return set;
    }

    public static void testStrategy(StorageStrategy strategy, long elementsNumber) {
        String nameStrategy = strategy.getClass().getSimpleName().toString();
        Helper.printMessage(nameStrategy);

        Set<String> originalString = new HashSet<>();

        for (long i = 0; i < elementsNumber; i++) {
            originalString.add(Helper.generateRandomString());
        }

        Shortener shortener = new Shortener(strategy);

        Date startDate = new Date();
        Set<Long> testSetID = getIds(shortener, originalString);
        Date endDate = new Date();
        Long time = endDate.getTime() - startDate.getTime();
        Helper.printMessage("Время получения идентификаторов для " + elementsNumber + " строк: " + time.toString());

        Date startDate2 = new Date();
        Set<String> testSetString = getStrings(shortener, testSetID);
        Date endDate2 = new Date();
        Long time2 = endDate2.getTime() - startDate2.getTime();
        Helper.printMessage("Время получения строк для " + elementsNumber + " идентификаторов: " + time2.toString());

        if(testSetString.equals(originalString)){
            Helper.printMessage("Тест пройден.");
        }else {
            Helper.printMessage("Тест не пройден.");
        }

    }

    public static void main(String[] args) {
        testStrategy(new HashMapStorageStrategy(), 10000L);
        testStrategy(new OurHashMapStorageStrategy(), 10000L);
        testStrategy(new FileStorageStrategy(), 100L);
        testStrategy(new OurHashBiMapStorageStrategy(), 10000L);
        testStrategy(new HashBiMapStorageStrategy(), 10000L);
        testStrategy(new DualHashBidiMapStorageStrategy(), 10000L);
    }
}
