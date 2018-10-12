package com.seavus.mockito.presentation;

public class Transformer {

    public static int transformStatic(int basic) {
        int transformed = 0;
        if(basic > 500) {
            transformed = basic/2;
        }
        return transformed;
    }

    public final int transformFinal(int basic) {
        int transformed = 0;
        if(basic > 500) {
            transformed = basic/2;
        }
        return transformed;
    }

    public int transform(int basic) {
        int transformed = 0;
        if(basic > 500) {
            transformed = basic/2;
        }
        return transformed;
    }

    public int callPrivateMethod(int basic) {
        int result = 0;
        if(basic > 100) {
            result = this.privateMethodInTransformer(basic);
        }
        return result;
    }

    private int privateMethodInTransformer(int basic) {
        int transformed = 0;
        if(basic > 500) {
            transformed = basic/2;
        }
        return transformed;
    }

}
