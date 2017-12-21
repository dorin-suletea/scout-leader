package core;

import api.BittrexManager;

/**
 * Created by next on 12/20/17.
 */
public class Main {
    public static void main(String args[]) {
        BittrexManager bittrexManager = RuntimeModule.getInjectedObject(BittrexManager.class);
//        ApiFactoryImpl apiFact = RuntimeModule.getInjectedObject(ApiFactoryImpl.class);


        System.out.println(bittrexManager.getPairs());
    }
}
