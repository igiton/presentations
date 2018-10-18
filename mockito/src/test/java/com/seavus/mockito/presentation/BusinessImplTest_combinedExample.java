package com.seavus.mockito.presentation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Transformer.class, TransformerFactory.class})
public class BusinessImplTest_combinedExample {

    DataService dataServiceMock = PowerMockito.mock(DataService.class);

    BusinessImpl businessImpl = new BusinessImpl(dataServiceMock);

    @Captor
    private ArgumentCaptor<Integer> intCaptor;

    @Captor
    private ArgumentCaptor<Integer> intCaptor2;

    @Test
    public void retrieveAndTransform_combinedExampleTest() throws Exception {
    //given
        int[] array = new int[] { 100, 200, 300 };
        //stub "regular" method
        when(dataServiceMock.retrieveDataFromSomewhere()).thenReturn(array);
        //mock and stub static method
        mockStatic(Transformer.class);
        when(Transformer.transformStatic(anyInt())).thenReturn(100);
        //mock and stub final method
        Transformer transformerMock = PowerMockito.mock(Transformer.class);//Be sure that mock method belongs to PowerMockito, not to Mockito!!!
        businessImpl.setTransformer(transformerMock);
        PowerMockito.doReturn(100).when(transformerMock).transformFinal(anyInt());
        //mock and stub constructor
        PowerMockito.whenNew(Transformer.class).withNoArguments().thenReturn(transformerMock);
        doReturn(10).when(transformerMock).transform(intCaptor2.capture());

     //when
        int result = businessImpl.retrieveAndTransform_combinedExample();

     //then
        assertEquals(10, result);
        //for "regular" methods
        verify(dataServiceMock).retrieveDataFromSomewhere();
        verify(dataServiceMock, never()).doSomethingElse(anyInt());
        //for static method call
        verifyStatic(Transformer.class);
        Transformer.transformStatic(intCaptor.capture());
        assertEquals(300, intCaptor.getValue().intValue());
        //for final method call
        verify(transformerMock).transformFinal(anyInt());//or
        verify(transformerMock).transformFinal(100);
        //for creating new instance
        PowerMockito.verifyNew(Transformer.class).withNoArguments();

        //when we are interested for exact argument value
        verify(transformerMock).transform(100);//or
        //when we are interested for exact argument type but not for value
        verify(transformerMock).transform(anyInt());//or
        //when we are interested for captured value
        verify(transformerMock).transform(intCaptor2.getValue().intValue());
    }
}
