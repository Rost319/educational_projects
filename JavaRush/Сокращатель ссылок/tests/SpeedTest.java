package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Helper;
import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.HashBiMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.HashMapStorageStrategy;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SpeedTest {

    @Test
    public void testHashMapStorage() {
        Shortener shortener1 = new Shortener(new HashMapStorageStrategy());
        Shortener shortener2 = new Shortener(new HashBiMapStorageStrategy());

        Set<String> origStrings = new HashSet<>();


        for (long i = 0; i < 10_000; i++) {
            origStrings.add(Helper.generateRandomString());
        }

        Set<Long> Ids1 = new HashSet<>();
        Set<String> String1 = new HashSet<>();

        Long timeShortener1 = getTimeToGetIds(shortener1, origStrings, Ids1);
        Long timeShort1 = getTimeToGetStrings(shortener1, Ids1, String1);

        Set<Long> Ids2 = new HashSet<>();
        Set<String> String2 = new HashSet<>();

        Long timeShortener2 = getTimeToGetIds(shortener2, origStrings, Ids2);
        Long timeShort2 = getTimeToGetStrings(shortener2, Ids2, String2);


        Assert.assertTrue(timeShortener1 > timeShortener2);
        Assert.assertEquals(timeShort1, timeShort2, 150f);
    }

    public long getTimeToGetIds(Shortener shortener, Set<String> strings, Set<Long> ids) {
        Date startTime = new Date();
        for (String string : strings){
            ids.add(shortener.getId(string));
        }
        Date endTime = new Date();
        return endTime.getTime() - startTime.getTime();
    }

    public long getTimeToGetStrings(Shortener shortener, Set<Long> ids, Set<String> strings) {
        Date startTime = new Date();
        for (Long l : ids){
            strings.add(shortener.getString(l));
        }
        Date endTime = new Date();
        return endTime.getTime() - startTime.getTime();
    }
}
