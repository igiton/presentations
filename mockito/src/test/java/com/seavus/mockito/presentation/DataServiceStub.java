package com.seavus.mockito.presentation;

public class DataServiceStub implements DataService {

    @Override
    public int[] retrieveDataFromSomewhere() {
        return new int[]{100, 500, 200};
    }

    @Override
    public void doSomethingElse(int number){
        System.out.println("doing Something Else With "+number);
    }

}