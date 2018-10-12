package com.seavus.mockito.presentation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TransformerFactory.class)
public class BusinessImplTestWithPowerMock_MockConstructor {

    @InjectMocks
    BusinessImpl businessImpl;

    @Mock
    DataService dataServiceMock;

    /* Mock of constructor */
    @Test
    public void testTransform_withMockConstructor() throws Exception {
        //given
        int number = 100;
        int returnValue = 50;
        Transformer transformerMock = Mockito.mock(Transformer.class);
        PowerMockito.whenNew(Transformer.class).withNoArguments().thenReturn(transformerMock);
        doReturn(returnValue).when(transformerMock).transform(anyInt());
        //when
        int result = businessImpl.transformWithMockConstructor(number);
        //then
        assertEquals(returnValue, result);
        PowerMockito.verifyNew(Transformer.class).withNoArguments();
        verify(dataServiceMock, never()).doSomethingElse(anyInt());
    }
}
