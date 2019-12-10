/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.proj.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 *
 * @author NZXT-PC
 */
public class ParsableUnrolledLinkedList<V extends Parsable<V>> extends UnrolledLinkedList<V> implements ParsableList<V> {

    private final Function<String, V> createFunction; // funkcija bazinio reikšmės objekto kūrimui

    public ParsableUnrolledLinkedList(Function<String, V> createFunction) {
        super();
        this.createFunction = createFunction;
    }

    public ParsableUnrolledLinkedList(int initialCapacity, Function<String, V> createFunction) {

        super(initialCapacity);
        this.createFunction = createFunction;

    }

    @Override
    public boolean add(String dataString) {
        return super.add(
                createElement(dataString));
    }

    @Override
    public void load(String filePath) {
        if (filePath == null || filePath.length() == 0) {
            return;
        }
        clear();
        try (BufferedReader fReader = Files.newBufferedReader(Paths.get(filePath), Charset.defaultCharset())) {
            fReader.lines()
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .forEach(this::add);
        } catch (FileNotFoundException e) {
            Ks.ern("Tinkamas duomenų failas nerastas: " + e.getLocalizedMessage());
        } catch (IOException | UncheckedIOException e) {
            Ks.ern("Failo skaitymo klaida: " + e.getLocalizedMessage());
        }
    }

    @Override
    public void save(String filePath) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String[][] getModelList() {
        int nodeAmmount = 0;
        for (Node node = firstNode; node != null; node = node.next) {
            nodeAmmount++;
        }
        String[][] result = new String[nodeAmmount][];
        int count = 0;
        for (Node node = firstNode; node != null; node = node.next) {
            List<String> list = new ArrayList<>();
            list.add("[ " + count + " ]");
            for (int i = 0; i < node.numElements; i++) {
                list.add("-->");
                list.add(node.elements[i].toString());
            }
            result[count] = list.toArray(new String[0]);
            count++;
        }
        return result;
    }

    protected V createElement(String data) {
        return Optional.ofNullable(createFunction)
                .map(f -> f.apply(data))
                .orElseThrow(() -> new IllegalStateException("Nenustatyta aibės elementų kūrimo funkcija"));
    }
}
