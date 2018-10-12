package com.seavus.mockito.presentation;

public class BusinessImpl {

    private DataService dataService;

    private Transformer transformer;

    private TransformerFactory transformerFactory = new TransformerFactory();

    public BusinessImpl(DataService dataService) {
        this.dataService = dataService;
    }

    public void setTransformer(Transformer transformer) {
        this.transformer = transformer;
    }

    public int findTheGreatestFromAllData() {
        //int[] data = getResultFromAnotherSource();
        int[] data = dataService.retrieveDataFromSomewhere();
        int min = Integer.MIN_VALUE;

        for (int value : data) {
            if (value > min) {
                min = value;
            }
        }
        if(min > 400) {
            dataService.doSomethingElse(min);
        }
        return min;
    }

    private int[] getResultFromAnotherSource() {
        return new int[] {500};
    }

    public Integer transformWithStaticMethodCall(int number) {
        /* do something before ... */
        int transformed = Transformer.transformStatic(number);
        if(transformed > 400) {
            dataService.doSomethingElse(transformed);
        }
        /* do something after ... */
        return transformed;
    }

    public Integer transformWithFinalMethodCall(int number) {
        int transformed = transformer.transformFinal(number);
        if(transformed > 400) {
            dataService.doSomethingElse(transformed);
        }
        return transformed;
    }

    public Integer transformWithPrivateMethodCall(int number) {
        int transformed = transformer.callPrivateMethod(number);
        return transformed;
    }

    public Integer transformWithMockConstructor(int number) {
        Transformer transformer = transformerFactory.getInstance();
        int transformed = transformer.transform(number);
        if(transformed > 400) {
            dataService.doSomethingElse(transformed);
        }
        return transformed;
    }

    public Integer transformWithPrivateMethodCallInsideTestedClass(int number) {
        int transformed = this.privateInsideTestedClass(number);
        return transformed;
    }

    private Integer privateInsideTestedClass(int number) {
        return number/2;
    }

}
