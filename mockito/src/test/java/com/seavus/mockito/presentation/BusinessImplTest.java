package com.seavus.mockito.presentation;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class BusinessImplTest {
    @Test
    public void testFindTheGreatestFromAllData() {
        BusinessImpl businessImpl = new BusinessImpl(new DataServiceStub());
        int result = businessImpl.findTheGreatestFromAllData();
        assertEquals(500, result);
    }
}



