package com.example.skimap;

public class BitPacker {


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
