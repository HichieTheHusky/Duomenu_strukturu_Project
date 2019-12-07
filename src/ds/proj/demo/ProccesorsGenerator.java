package ds.proj.demo;

import ds.proj.gui.ValidationException;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 * @author NZXT-PC
 */
public class ProccesorsGenerator {

    private Proccesor[] proccesors;
    
    private int currentProcIndex = 0;

    public static Proccesor[] generateShuffleProccesors(int setSize) throws ValidationException {

                Proccesor[] proccesors = IntStream.range(0, setSize)
                .mapToObj(i -> new Proccesor.Builder().buildRandom())
                .toArray(Proccesor[]::new);
        Collections.shuffle(Arrays.asList(proccesors));
        return proccesors;
    }

    public Proccesor[] generateShuffleProccesorsAndIds(int setSize,
            int setTakeSize) throws ValidationException {

        if (setTakeSize > setSize) {
            setTakeSize = setSize;
        }
        proccesors = generateShuffleProccesors(setSize);
        this.currentProcIndex = setTakeSize;
        return Arrays.copyOf(proccesors, setTakeSize);
    }

    // Imamas po vienas elementas iš sugeneruoto masyvo. Kai elementai baigiasi sugeneruojama
    // nuosava situacija ir išmetamas pranešimas.
    public Proccesor getProccesor() {
        if (proccesors == null) {
            throw new ValidationException("carsNotGenerated");
        }
        if (currentProcIndex < proccesors.length) {
            return proccesors[currentProcIndex++];
        } else {
            throw new ValidationException("allSetStoredToMap");
        }
    }
}
