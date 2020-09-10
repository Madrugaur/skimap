package com.bradenlittle.skimap;
/**
 * Description: Utility Class for readability and modularity
 * @author Braden Little (https://github.com/Madrugaur)
 * @version 1.0
 * @since May, 2020
 */
public class BitPacker {

    /**
     * Description: Packs an array of booleans into a byte
     * @param flags An array of boolean flags
     * @return A packed byte
     * @author Braden Little (https://github.com/Madrugaur)
     */
    public static byte bitPack(boolean[] flags){
        int numFlags = flags.length;
        byte word = 0x0;
        for (int i = 0; i < numFlags; i++){
            if(flags[i]) {
                word = (byte) (word | oneAt(i));
            }
        }
        return word;
    }
    private static byte oneAt(int i){
        return (byte)(0x1 << i);
    }
}
