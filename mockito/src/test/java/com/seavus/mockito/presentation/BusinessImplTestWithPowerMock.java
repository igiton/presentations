package com.seavus.mockito.presentation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Transformer.class, BusinessImpl.class})
public class BusinessImplTestWithPowerMock {

    DataService dataServiceMock = PowerMockito.mock(DataService.class);

    BusinessImpl businessImpl = new BusinessImpl(dataServiceMock);

    @Captor
    private ArgumentCaptor<Integer> intCaptor;

    /* Mock static method */
    @Test
    public void test_With_Static_MethodCall() {
        //given
        int number = 100;
        int returnValue = 50;
        mockStatic(Transformer.class);
        when(Transformer.transformStatic(number)).thenReturn(returnValue);
        //when
        int result = businessImpl.transformWithStaticMethodCall(number);
        //then
        verifyStatic(Transformer.class);
        Transformer.transformStatic(intCaptor.capture());
        assertEquals(number, intCaptor.getValue().intValue());
        assertEquals(returnValue, result);
        verify(dataServiceMock, never()).doSomethingElse(anyInt());
    }

    /* Mock final method */
    @Test
    public void test_With_Final_MethodCall() {
        //given
        int number = 100;
        int returnValue = 50;
        Transformer transformerMock = PowerMockito.mock(Transformer.class);//Be sure that mock method belongs to PowerMockito, not to Mockito!!!
        businessImpl.setTransformer(transformerMock);
        PowerMockito.doReturn(returnValue).when(transformerMock).transformFinal(number);
        //when
        int result = businessImpl.transformWithFinalMethodCall(number);
        //then
        assertEquals(returnValue, result);
        verify(dataServiceMock, never()).doSomethingElse(anyInt());
        verify(transformerMock).transformFinal(number);
    }

    /* Mock private method in dependency class */
    @Test
    public void test_With_Private_MethodCall_InExternalDependency() throws Exception {
        //given
        int number = 200;
        int returnValue = 50;
        Transformer transformer = new Transformer();
        Transformer transformerSpy = spy(transformer);
        businessImpl.setTransformer(transformerSpy);
        PowerMockito.doReturn(returnValue).when(transformerSpy, "privateMethodInTransformer", number);
        //when
        int result = businessImpl.transformWithPrivateMethodCall(number);
        //then
        assertEquals(returnValue, result);
        PowerMockito.verifyPrivate(transformerSpy, times(1)).invoke("privateMethodInTransformer", number);
        verify(dataServiceMock, never()).doSomethingElse(anyInt());
    }

    /* Mock private method in tested class */
    @Test
    public void testWith_with_Private_MethodCall_InsideTestedClass() throws Exception {
        //given
        int number = 200;
        int returnValue = 50;
        BusinessImpl businessImplSpy = PowerMockito.spy(businessImpl);//we are using "spy" because real method Transformer#callPrivateMethod needs to be called
        PowerMockito.doReturn(returnValue).when(businessImplSpy, "privateInsideTestedClass", number);
        //when
        int result = businessImplSpy.transformWithPrivateMethodCallInsideTestedClass(number);//here we are using spy object!
        //then
        assertEquals(returnValue, result);
        PowerMockito.verifyPrivate(businessImplSpy, times(1)).invoke("privateInsideTestedClass", number);
    }

}
