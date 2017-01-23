package com.swin.utils;

import edu.princeton.cs.algs4.StdIn;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Created by Swin on 2016/9/2.
 */
public class Utils {
    public static Scanner getScanner(String fn) {
        Scanner in = null;
        try {
            in = new Scanner(new FileReader(fn));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return in;
    }



    @NotNull
    public static String[] getStringArray(String fileName) {
        String fn = Constant.DATA_DIR + fileName;
        StdIn.setScanner(Utils.getScanner(fn));
        return StdIn.readAllStrings();
    }

    public static int[] getIntArray(String fileName){
        String fn = Constant.DATA_DIR + fileName;
        StdIn.setScanner(Utils.getScanner(fn));
        return StdIn.readAllInts();
    }


    public static long[] getLongArray(String fileName){
        String fn = Constant.DATA_DIR + fileName;
        StdIn.setScanner(Utils.getScanner(fn));
        return StdIn.readAllLongs();
    }

    public static double[] getDoubleArray(String fileName){
        String fn = Constant.DATA_DIR + fileName;
        StdIn.setScanner(Utils.getScanner(fn));
        return StdIn.readAllDoubles();
    }


}
