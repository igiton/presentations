package com.seavus.mockito.presentation;

public class DataServiceImpl implements DataService {

    @Override
    public int[] retrieveDataFromSomewhere() {
        System.out.println("Really retrieveDataFromSomewhere ");
        return new int[]{1000, 5000, 2000};
    }

    @Override
    public void doSomethingElse(int number){
        System.out.println("Really doing Something Else With "+number);
    }
}
