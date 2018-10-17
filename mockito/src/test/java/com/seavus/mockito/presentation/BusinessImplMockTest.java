package com.seavus.mockito.presentation;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.hamcrest.collection.IsMapContaining.hasValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BusinessImplMockTest {

    @Test
    public void testFindTheGreatestFromAllData() {
        DataService dataServiceMock = mock(DataService.class); //creation of mock object
        when(dataServiceMock.retrieveDataFromSomewhere()).thenReturn(new int[] { 100, 200, 500 }); //if we do not create this stub, null will be returned
        BusinessImpl businessImpl = new BusinessImpl(dataServiceMock);//instance of tested class that work with mocks
        int result = businessImpl.findTheGreatestFromAllData();
        assertEquals(500, result);
        verify(dataServiceMock, times(1)).retrieveDataFromSomewhere();

        //or by using hamcrest
        assertThat(result, is(equalTo(500)));
    }

    @Test
    public void testFindTheGreatestFromAllData_ForOneValue() {
        DataService dataServiceMock = mock(DataService.class); //creation of mock object
        when(dataServiceMock.retrieveDataFromSomewhere()).thenReturn(new int[] { 15 });
        BusinessImpl businessImpl = new BusinessImpl(dataServiceMock);
        int result = businessImpl.findTheGreatestFromAllData();
        assertEquals(15, result);
        verify(dataServiceMock, times(1)).retrieveDataFromSomewhere();
    }

    @Test
    public void hamcrestMatchersExample() {
        //List
        String first = "first";
        String second = "second";
        String third = "third";

        List<String> list = new ArrayList();
        list.add(first);
        list.add(second);
        list.add(third);

        assertThat(list, hasSize(3));
        assertThat(list, hasSize(greaterThan(2)));
        assertThat(list, everyItem(is(instanceOf(String.class))));
        assertThat(list, contains(first, second, third));
        assertThat(list, containsInAnyOrder(second, third, first));

        //Map
        Integer first1 = 1;
        Integer second1 = 2;
        Integer third1 = 3;

        Map<String, Integer> map = new HashMap<>();
        map.put(first, first1);
        map.put(second, second1);
        map.put(third, third1);

        assertThat(map.size(), is(3));
        assertThat(map, hasKey(first));
        assertThat(map, hasValue(first1));
        assertThat(map, hasEntry(first, first1));

    }

}
