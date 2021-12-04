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

    public boolean add(String dataString);

    void load(String filePath);

    void save(String filePath);

    /**
     * Grąžina maišos lentelės turinį, skirtą atvaizdavimui JavaFX lentelėse
     *
     * @return Grąžina maišos lentelės turinį dvimačiu masyvu
     */
    String[][] getModelList();
}
