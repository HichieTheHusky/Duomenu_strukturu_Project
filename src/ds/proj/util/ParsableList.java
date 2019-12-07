/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.proj.util;

/**
 *
 * @author NZXT-PC
 */
public interface ParsableList<V> {

    void load(String filePath);

    void save(String filePath);

    void println();

    void println(String title);

    /**
     * Grąžina maišos lentelės turinį, skirtą atvaizdavimui JavaFX lentelėse
     *
     * @param delimiter Poros toString() eilutės kirtiklis
     * @return Grąžina maišos lentelės turinį dvimačiu masyvu
     */
    String[][] getModelList();
}
