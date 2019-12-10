/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.proj.benchmarkdemo;


import java.util.List;
import java.util.Random;
/**
 *
 * @author husky
 */
public class util {
    
    
    static Random generator = new Random();

    static void generateList(List<Float> list, int size) {
        for (int i = 0; i < size; i++) {
            list.add(generator.nextFloat());
        }
    }
    
    static void generateIndexes(int[] indexes, int listSize) {
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = generator.nextInt(listSize);
        }
    }
    
}
