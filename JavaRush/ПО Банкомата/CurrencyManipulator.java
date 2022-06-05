package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class CurrencyManipulator {

    private String currencyCode;
    private Map<Integer, Integer> denominations;

    public CurrencyManipulator(String currencyCode) {
        this.currencyCode = currencyCode;
        denominations = new TreeMap<>();
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void addAmount(int denomination, int count) {
        if(denominations.containsKey(denomination)){
            denominations.put(denomination, denominations.get(denomination) + count);
        }else
            denominations.put(denomination, count);
    }

    public int getTotalAmount() {
        int sum = 0;
        for(Map.Entry<Integer, Integer> entry : denominations.entrySet()){
            sum += (entry.getValue() * entry.getKey());
        }
        return sum;
    }

    public boolean hasMoney(){
        if(getTotalAmount() == 0 && denominations.size() == 0){
            return false;
        }else
            return true;
    }

    public boolean isAmountAvailable(int expectedAmount) {
        return getTotalAmount() >= expectedAmount;
    }


    public Map<Integer, Integer> withdrawAmount(int expectedAmount) throws NotEnoughMoneyException {
        Map<Integer, Integer> map = new TreeMap<>(Collections.reverseOrder());
        Map<Integer, Integer> result = new TreeMap<>(Collections.reverseOrder());
        map.putAll(denominations);
        int sum = 0;
        int remainder = expectedAmount;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()){
            int key = entry.getKey();
            while ((remainder - key) >= 0 && entry.getValue() > 0){
                sum += key;
                remainder = remainder - key;
                entry.setValue(entry.getValue() - 1);
                if(result.containsKey(key)){
                    result.put(key, result.get(key) + 1);
                }else {
                    result.put(key, 1);
                }
            }
        }

        if(sum == expectedAmount && remainder == 0){
            map.entrySet().removeIf(entry -> entry.getValue() == 0);
            denominations = map;
            return result;
        }else throw new NotEnoughMoneyException();
    }
}
