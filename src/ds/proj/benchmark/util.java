/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.proj.benchmark;

import ds.proj.util.UnrolledLinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author husky
 */
public class util {

    static Random generator = new Random();

    static void generateList(List<Float> list, int size, float[] test) {

        for (int i = 0; i < size; i++) {
            float obj = generator.nextFloat();
            list.add(obj);
            test[i] = obj;
        }

        shuffleProc(test);

    }

    static void generateList(UnrolledLinkedList<Float> list, int size, float[] test) {

        for (int i = 0; i < size; i++) {
            float obj = generator.nextFloat();
            list.add(obj);
            test[i] = obj;
        }

        shuffleProc(test);
    }

    static void generateIndexes(int[] indexes, int listSize) {
        int k = listSize - 2_000;
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = generator.nextInt(k);
//            k++;
        }

    }

    static void shuffleProc(float[] ar) {

        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            float a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

}
