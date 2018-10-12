package com.seavus.mockito.presentation;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BusinessImplMockWithAnnotationsTest {

    @Mock
    DataService dataServiceMock;

    @Mock
    Transformer transformerMock;

    @InjectMocks
    BusinessImpl businessImpl;

    @Before
    public void preTestSetup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindTheGreatestFromAllData() {
        //given
        int[] array = new int[] { 100, 500, 300 };
        when(dataServiceMock.retrieveDataFromSomewhere()).thenReturn(array);
        //when
        int result = businessImpl.findTheGreatestFromAllData();
        //then
        assertEquals(500, result);
    }

    @Test
    public void testFindTheGreatestFromAllData_ForOneValue() {
        //given
        int[] array = new int[] { 15 };
        when(dataServiceMock.retrieveDataFromSomewhere()).thenReturn(array);
        //when
        int result = businessImpl.findTheGreatestFromAllData();
        //then
        assertEquals(15, result);
    }

    @Test
    public void testFindTheGreatestFromAllData_NoValues() {
        //given
        int[] array = new int[] { };
        when(dataServiceMock.retrieveDataFromSomewhere()).thenReturn(array);
        //when
        int result = businessImpl.findTheGreatestFromAllData();
        //then
        assertEquals(Integer.MIN_VALUE, result);
    }

    @Test
    public void testFindTheGreatestFromAllData_withMethodCallVerification() {
        //given
        int[] array = new int[] { 100, 500, 300 };
        when(dataServiceMock.retrieveDataFromSomewhere()).thenReturn(array);
        //when
        int result = businessImpl.findTheGreatestFromAllData();
        //then
        assertEquals(500, result);

        verify(dataServiceMock).retrieveDataFromSomewhere(); //or
        verify(dataServiceMock, times(1)).retrieveDataFromSomewhere(); //or
        verify(dataServiceMock, atLeastOnce()).retrieveDataFromSomewhere();
        verify(dataServiceMock, atLeastOnce()).doSomethingElse(anyInt());
    }

    @Test
    public void testFindTheGreatestFromAllData_withMethodCallVerification_neverCalled() {
        //given
        int[] array = new int[] { 100, 200, 300 };
        when(dataServiceMock.retrieveDataFromSomewhere()).thenReturn(array);
        //when
        int result = businessImpl.findTheGreatestFromAllData();
        //then
        assertEquals(300, result);

        verify(dataServiceMock).retrieveDataFromSomewhere(); //or
        verify(dataServiceMock, times(1)).retrieveDataFromSomewhere(); //or
        verify(dataServiceMock, atLeastOnce()).retrieveDataFromSomewhere();

        verify(dataServiceMock, never()).doSomethingElse(anyInt()); //or
        verify(dataServiceMock, times(0)).doSomethingElse(anyInt());

        /* when none method from mock object has been called */
        // verifyZeroInteractions(mockObject)
    }

    /* Mock void method */
    @Test/*(expected = Exception.class)*/ //for option 3.
    public void testFindTheGreatestFromAllData_with_Void_MethodCallVerification() {
        //given
        int[] array = new int[] { 500, 600, 700 };
        when(dataServiceMock.retrieveDataFromSomewhere()).thenReturn(array);

        /* for void method following 3 options can be used: */

        /* 1. */
        //doNothing().when(dataServiceMock).doSomethingElse(anyInt()); //by default
        /* 2. */
        doAnswer(invocation -> {
            Object arg0 = invocation.getArgument(0);
            assertEquals(700, arg0);
            return null;
        }).when(dataServiceMock).doSomethingElse(anyInt());
        /* 3. */
        //doThrow().when(dataServiceMock).doSomethingElse(anyInt());

        /* only one can be used in method else UnnecessaryStubbingException will be thrown! */

        //when
        int result = businessImpl.findTheGreatestFromAllData();
        //then
        assertEquals(700, result);
        verify(dataServiceMock).retrieveDataFromSomewhere(); //or
        verify(dataServiceMock, times(1)).retrieveDataFromSomewhere(); //or
        verify(dataServiceMock, atLeastOnce()).retrieveDataFromSomewhere();
        verify(dataServiceMock, times(1)).doSomethingElse(700);

    }

    //this test doesn't work in isolation and it is example of bad test !!!
    //it calls Transformer#transform method and this should not happen
    //Mock it by using PowerMock
    @Test
    public void testTransform_withStaticMethodCall() {
        //given
        int number = 600;
        //when
        int result = businessImpl.transformWithStaticMethodCall(number);
        //then
        assertEquals(300, result);

        verify(dataServiceMock, never()).doSomethingElse(anyInt()); //or
        verify(dataServiceMock, times(0)).doSomethingElse(anyInt());
    }

}
