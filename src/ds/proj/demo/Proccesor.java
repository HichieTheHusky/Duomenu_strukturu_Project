package ds.proj.demo;

import ds.proj.util.Ks;
import ds.proj.util.Parsable;

import java.time.LocalDate;
import java.util.*;

/**
 *
 * @author NZXT-PC
 */
public class Proccesor implements Parsable<Proccesor> {

    // bendri duomenys visiems Procesoriams (visai klasei)
    private static final int minYear = 2000;
    private static final int currentYear = LocalDate.now().getYear();
    private static final double minPrice = 10.0;
    private static final double maxPrice = 210_000.0;

    // kiekvieno Procesoriaus individualūs duomenys
    private String type = "";
    private String brand = "";
    private String model = "";
    private int year = -1;
    private double price = -1.0;

    public Proccesor() {

    }

    public Proccesor(String type, String brand, String model, int year, double price) {
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;

        validate();
    }

    public Proccesor(String dataString) {
        this.parse(dataString);
        validate();
    }

    public Proccesor(Builder builder) {
        this.type = builder.type;
        this.brand = builder.brand;
        this.model = builder.model;
        this.year = builder.year;
        this.price = builder.price;

        validate();
    }

    private void validate() {
        String errorType = "";
        if (year < minYear || year > currentYear) {
            errorType = "Netinkami gamybos metai, turi būti ["
                    + minYear + ":" + currentYear + "]";
        }
        if (price < minPrice || price > maxPrice) {
            errorType += " Kaina už leistinų ribų [" + minPrice
                    + ":" + maxPrice + "]";
        }

        if (!errorType.isEmpty()) {
            Ks.ern("Procesorius yra blogai sugeneruotas: " + errorType);
        }
    }

    @Override
    public final void parse(String data) {
        try {   // duomenys, atskirti tarpais
            Scanner scanner = new Scanner(data);
            type = scanner.next();
            brand = scanner.next();
            model = scanner.next();
            year = scanner.nextInt();
            setPrice(scanner.nextDouble());

        } catch (InputMismatchException e) {
            Ks.ern("Blogas duomenų formatas -> " + data);
        } catch (NoSuchElementException e) {
            Ks.ern("Trūksta duomenų -> " + data);
        }
    }

    @Override
    public String toString() {  // papildyta su carRegNr
        return type + "_" + brand + ":" + model + " " + year + " " + String.format("%4.1f", price);
    }

    public String ToString_data() {  // papildyta su carRegNr
        return type + " " + brand + " " + model + " " + year + " " + String.format("%4.1f", price);
    }

    public String getType() {
        return type;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public double getPrice() {
        return price;
    }

    // keisti bus galima tik kainą - kiti parametrai pastovūs
    public void setPrice(double price) {
        this.price = price;
    }
    
        @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.type);
        hash = 29 * hash + Objects.hashCode(this.brand);
        hash = 29 * hash + Objects.hashCode(this.model);
        hash = 29 * hash + this.year;
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.price) ^ (Double.doubleToLongBits(this.price) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Proccesor other = (Proccesor) obj;
        if (this.year != other.year) {
            return false;
        }
        if (Double.doubleToLongBits(this.price) != Double.doubleToLongBits(other.price)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.brand, other.brand)) {
            return false;
        }
        if (!Objects.equals(this.model, other.model)) {
            return false;
        }
        return true;
    }

    // Car klases objektų gamintojas (builder'is)
    public static class Builder {

        private final static Random RANDOM = new Random(2017);  // Atsitiktinių generatorius
        private final static String[][] MODELS = { // galimų type/brand/model masyvas
            {"GPU", "Nvidia", "gtx_770", "rtx_2070_super", "rtx_2080", "gtx_1080TI"},
            {"GPU", "AMD", "rx570", "rx470", "rx580", "Vega_64"},
            {"CPU", "Intel", "i9-9900k", "i7-9700k", "i5-6600k"},
            {"CPU", "AMD", "Ryzen_3900x", "Ryzen_3700x", "ryzen_3600"}
        };

        private String type = "";
        private String brand = "";
        private String model = "";
        private int year = -1;
        private double price = -1.0;

        public Proccesor build() {
            return new Proccesor(this);
        }

        public Proccesor buildRandom() {
            int tIndex = RANDOM.nextInt(MODELS.length);        // markės indeksas  0..
            int mIndex = RANDOM.nextInt(MODELS[tIndex].length - 2) + 2;// modelio indeksas 1..
            return new Proccesor(
                    MODELS[tIndex][0],
                    MODELS[tIndex][1],
                    MODELS[tIndex][mIndex],
                    2000 + RANDOM.nextInt(20), // metai tarp 1999 ir 2019
                    10 + RANDOM.nextDouble() * 200_0); // kaina tarp 10 ir 201_0
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder brand(String brand) {
            this.brand = brand;
            return this;
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder year(int year) {
            this.year = year;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }
    }
}
