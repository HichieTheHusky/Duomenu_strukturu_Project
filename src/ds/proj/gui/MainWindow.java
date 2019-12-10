package ds.proj.gui;

import ds.proj.demo.Proccesor;
import ds.proj.demo.ProccesorsGenerator;
import ds.proj.util.ParsableUnrolledLinkedList;
import ds.proj.util.UnrolledLinkedList;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 * @author husky
 */
public class MainWindow extends BorderPane implements EventHandler<ActionEvent> {

    private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("ds.proj.gui.messages");
    private static final int TF_WIDTH = 200;
    private static final int TF_WIDTH_SMALLER = 80;
    private static final double SPACING = 5.0;
    private static final Insets INSETS = new Insets(SPACING);
    private static final double SPACING_SMALLER = 2.0;
    private static final Insets INSETS_SMALLER = new Insets(SPACING_SMALLER);

    private final ComboBox cmbFilterType = new ComboBox();
    private final ComboBox cmbFilterBrand = new ComboBox();
    private final TextArea taInput = new TextArea();
    private final GridPane paneParam12Events = new GridPane();
    private final GridPane paneRight = new GridPane();
    private final GridPane paneCenter = new GridPane();
    private final TextArea taEvents = new TextArea();
    private Panels paneParam01, paneParam02, paneParam1, paneButtons, paneButton;
    private MapTable<String[], String> table, myTable;
    private MainWindowMenu mainWindowMenu;
    private final Stage stage;

    private ParsableUnrolledLinkedList<Proccesor> list;
    private ParsableUnrolledLinkedList<Proccesor> mylist;
    private int sizeOfInitialSubSet, sizeOfGenSet, colWidth, initialCapacity;
    private final ProccesorsGenerator proccesorsGenerator = new ProccesorsGenerator();

    public MainWindow(Stage stage) {
        this.stage = stage;
        initComponents();
    }

    private void initComponents() {
        // Formuojamas mėlynos spalvos skydelis (dešinėje pusėje)
        paneParam01 = new Panels(
                new String[]{
                    MESSAGES.getString("lblParam00"),
                    MESSAGES.getString("lblParam01")},
                new String[]{
                    MESSAGES.getString("tfParam00"),
                    MESSAGES.getString("tfParam01")}, TF_WIDTH_SMALLER);

        paneParam02 = new Panels(
                new String[]{
                    MESSAGES.getString("lblParam02"),
                    MESSAGES.getString("lblParam03")},
                new String[]{
                    MESSAGES.getString("tfParam02"),
                    MESSAGES.getString("tfParam03")}, TF_WIDTH_SMALLER);

        cmbFilterType.setItems(FXCollections.observableArrayList(
                MESSAGES.getString("cmbType1"),
                MESSAGES.getString("cmbType2"),
                MESSAGES.getString("cmbType3")));
        cmbFilterType.setOnAction(this);
        cmbFilterType.getSelectionModel().select(0);
        cmbFilterBrand.setItems(FXCollections.observableArrayList(
                MESSAGES.getString("cmbBrand1"),
                MESSAGES.getString("cmbBrand2"),
                MESSAGES.getString("cmbBrand3"),
                MESSAGES.getString("cmbBrand4")));
        cmbFilterBrand.setOnAction(this);
        cmbFilterBrand.getSelectionModel().select(0);

        // Formuojamas mygtukų tinklelis (pilkas). Naudojama klasė Panels.
        paneButtons = new Panels(
                new String[]{
                    MESSAGES.getString("button1"),
                    MESSAGES.getString("button2"),
                    MESSAGES.getString("button3"),
                    MESSAGES.getString("button4"),
                    MESSAGES.getString("button6")}, 1, 5);
        paneButtons.getButtons().forEach((btn) -> btn.setOnAction(this));
        IntStream.of(2, 3, 4).forEach(p -> paneButtons.getButtons().get(p).setDisable(true));

        paneButton = new Panels(
                new String[]{
                    MESSAGES.getString("button5")}, 1, 1);
        paneButton.getButtons().forEach((btn) -> btn.setOnAction(this));
        IntStream.of(0).forEach(p -> paneButton.getButtons().get(p).setDisable(true));

        // Viskas sudedama į mėlynos spalvos skydelį 
        GridPane.setVgrow(taInput, Priority.ALWAYS);
        taInput.setPrefWidth(TF_WIDTH);
        cmbFilterType.setPrefWidth(TF_WIDTH);
        cmbFilterBrand.setPrefWidth(TF_WIDTH);
        Stream.of(
                paneParam01,
                paneParam02,
                new Label(MESSAGES.getString("border0")),
                cmbFilterType,
                cmbFilterBrand,
                paneButton,
                new Label(MESSAGES.getString("border1")),
                taInput,
                paneButtons
        ).forEach(n -> paneRight.addColumn(0, n));
        paneRight.setHgap(SPACING);
        paneRight.setVgap(SPACING);
        paneRight.setPadding(INSETS);

        // Formuojama pirmoji parametrų lentelė (šviesiai žalia). Naudojama klasė Panels.
        paneParam1 = new Panels(
                new String[]{
                    MESSAGES.getString("lblParam11"),
                    MESSAGES.getString("lblParam12"),
                    MESSAGES.getString("lblParam13"),
                    MESSAGES.getString("lblParam15"),
                    MESSAGES.getString("lblParam21")},
                new String[]{
                    MESSAGES.getString("tfParam11"),
                    MESSAGES.getString("tfParam12"),
                    MESSAGES.getString("tfParam13"),
                    MESSAGES.getString("tfParam15"),
                    MESSAGES.getString("tfParam21")}, TF_WIDTH_SMALLER);

        // Dviejų lentelių skydeliai sudedami į šviesiai pilką skydelį
        paneParam12Events.setPadding(new Insets(SPACING_SMALLER, SPACING, SPACING, SPACING));
        paneParam12Events.setVgap(SPACING_SMALLER);
        paneParam12Events.setHgap(SPACING);
        paneParam12Events.add(new Label(MESSAGES.getString("border2")), 0, 0);
        GridPane.setHgrow(taEvents, Priority.ALWAYS);
        paneParam12Events.addRow(1, paneParam1, taEvents);
        paneParam12Events.add(new Label(MESSAGES.getString("border4")), 1, 0);

        // Sukuriama lentelė, sukuriamas trūkstamas metodas
        table = new MapTable<String[], String>() {
            @Override
            public ObservableValue<String> returnValue(TableColumn.CellDataFeatures<String[], String> p) {
                int index = Integer.parseInt(p.getTableColumn().getId());
                return new SimpleStringProperty(index < p.getValue().length ? p.getValue()[index] : "");
            }
        };

        myTable = new MapTable<String[], String>() {
            @Override
            public ObservableValue<String> returnValue(TableColumn.CellDataFeatures<String[], String> p) {
                int index = Integer.parseInt(p.getTableColumn().getId());
                return new SimpleStringProperty(index < p.getValue().length ? p.getValue()[index] : "");
            }
        };

        // Sukuriamas meniu
        mainWindowMenu = new MainWindowMenu() {
            @Override
            public void handle(ActionEvent ae) {
                Region region = (Region) taEvents.lookup(".content");
                region.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

                try {
                    Object source = ae.getSource();
                    KsGui.setFormatStartOfLine(true);
                    if (source.equals(mainWindowMenu.getMenus().get(0).getItems().get(0))) {
                        fileChooseMenu();
                    } else if (source.equals(mainWindowMenu.getMenus().get(0).getItems().get(1))) {
                        fileSaveMenu();
                    } else if (source.equals(mainWindowMenu.getMenus().get(0).getItems().get(3))) {
                        System.exit(0);
                    } else if (source.equals(mainWindowMenu.getMenus().get(1).getItems().get(0))) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.initStyle(StageStyle.UTILITY);
                        alert.setTitle(MESSAGES.getString("menuItem21"));
                        alert.setHeaderText(MESSAGES.getString("author"));
                        alert.showAndWait();
                    } else if (source.equals(mainWindowMenu.getMenus().get(2).getItems().get(2))) {
                        mapGeneration(null);
                    } else if (source.equals(mainWindowMenu.getMenus().get(2).getItems().get(4))) {
                        clearListtable();
                    }
                } catch (ValidationException e) {
                    KsGui.ounerr(taEvents, e.getMessage());
                } catch (Exception e) {
                    KsGui.ounerr(taEvents, MESSAGES.getString("systemError"));
                    e.printStackTrace(System.out);
                }
                KsGui.setFormatStartOfLine(false);
            }
        };

        // Formuojamas Lab3 langas
        // ..viršuje, dešinėje, centre ir apačioje talpiname objektus..
        setTop(mainWindowMenu);

        VBox vboxTable = new VBox();
        vboxTable.setPadding(INSETS_SMALLER);
        VBox.setVgrow(table, Priority.ALWAYS);
        vboxTable.getChildren().addAll(new Label(MESSAGES.getString("border3")), table);

        VBox myVboxTable = new VBox();
        myVboxTable.setPadding(INSETS_SMALLER);
        VBox.setVgrow(myTable, Priority.ALWAYS);
        myVboxTable.getChildren().addAll(new Label(MESSAGES.getString("border5")), myTable);

        Stream.of(
                vboxTable,
                myVboxTable
        ).forEach(n -> paneCenter.addColumn(0, n));
        paneCenter.setHgap(SPACING);
        paneCenter.setVgap(SPACING);
        paneCenter.setPadding(INSETS);

        setRight(paneRight);
        setCenter(paneCenter);
        setBottom(paneParam12Events);
        appearance();
    }

    private void appearance() {
        paneParam1.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        paneRight.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        paneButtons.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
        taInput.setFont(Font.font("Monospaced", 12));
        taEvents.setEditable(false);
        taEvents.setFont(Font.font("Monospaced", 12));

        TextField c = paneParam1.getTfOfTable().get(paneParam1.getTfOfTable().size() - 1);
        c.setEditable(false);
        c.setMouseTransparent(true);
        paneParam01.getTfOfTable().forEach(t -> {
            t.setEditable(false);
            t.setMouseTransparent(true);
        });

    }

    @Override
    public void handle(ActionEvent event) {
        KsGui.setFormatStartOfLine(true);
        Platform.runLater(() -> {
            try {
                System.gc();
                System.gc();
                System.gc();
                Region region = (Region) taEvents.lookup(".content");
                region.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

                Object source = event.getSource();
                if (source instanceof Button) {
                    handleButtons(source);
                }/* else if (source instanceof ComboBox && (source.equals(cmbCollisionTypes) || source.equals(cmbHashFunctions))) {
                IntStream.of(1, 3).forEach(p -> paneButtons.getButtons().get(p).setDisable(true));
                }*/
            } catch (ValidationException e) {
                KsGui.ounerr(taEvents, MESSAGES.getString(e.getMessage()), e.getValue());
            } catch (UnsupportedOperationException e) {
                KsGui.ounerr(taEvents, e.getMessage());
            } catch (Exception e) {
                KsGui.ounerr(taEvents, MESSAGES.getString("systemError"));
                e.printStackTrace(System.out);
            }
        });
    }

    public static void createAndShowGui(Stage stage) {
        Platform.runLater(() -> {
            Locale.setDefault(Locale.US); // Suvienodiname skaičių formatus
            MainWindow window = new MainWindow(stage);
            stage.setScene(new Scene(window, 1300, 700));
//            stage.setMaximized(true);
            stage.setTitle(MESSAGES.getString("title"));
            stage.getIcons().add(new Image("file:" + MESSAGES.getString("icon")));
            stage.setOnCloseRequest(e -> System.exit(0));
            stage.show();
        });
    }

    private void handleButtons(Object source) {
        if (source.equals(paneButton.getButtons().get(0))) {
            filter();
        } else if (source.equals(paneButtons.getButtons().get(0))) {
            setCash(-1);
        } else if (source.equals(paneButtons.getButtons().get(1))) {
            addCash();
        } else if (source.equals(paneButtons.getButtons().get(2))) {
            buy();
        } else if (source.equals(paneButtons.getButtons().get(3))) {
            sell();
        } else if (source.equals(paneButtons.getButtons().get(4))) {
            addForSelling();
        }
    }

    private void filter() {

        ParsableUnrolledLinkedList<Proccesor> filtered;
        filtered = new ParsableUnrolledLinkedList<>(initialCapacity, Proccesor::new);

        Iterator<Proccesor> it = list.iterator();

        int test1 = cmbFilterType.getSelectionModel().getSelectedIndex();
        int test2 = cmbFilterBrand.getSelectionModel().getSelectedIndex();
        String testType;
        String testBrand;

        switch (test1) {
            case 0:
                testType = "CPU";
                break;
            case 1:
                testType = "GPU";
                break;
            default:
                testType = "none";
        }
        switch (test2) {
            case 0:
                testBrand = "Nvidia";
                break;
            case 1:
                testBrand = "Intel";
                break;
            case 2:
                testBrand = "AMD";
                break;
            default:
                testBrand = "none";
        }

        double test3 = Double.parseDouble((String.valueOf(paneParam02.getTfOfTable().get(0).getText())));
        double test4 = Double.parseDouble((String.valueOf(paneParam02.getTfOfTable().get(1).getText())));

        while (it.hasNext()) {
            Proccesor proc = it.next();
            if (testType == "none" || proc.getType() == testType) {
                if (testBrand == "none" || proc.getBrand() == testBrand) {
                    if (test3 == 0 || proc.getPrice() >= test3) {
                        if (test4 == 0 || proc.getPrice() <= test4) {
                            filtered.add(proc);
                        }
                    }
                }
            }
        }

        table.formTable(initialCapacity * 2 + 1, colWidth);
        String[][] modelList = filtered.getModelList();
        table.getItems().clear();
        table.setItems(FXCollections.observableArrayList(modelList));
    }

    private void setCash(double k) {
        String price = "";
        if (k != -1) {
            price = String.valueOf(k);
        } else {
            price = taInput.getText();
        }
        paneParam01.getTfOfTable().get(0).setText(String.valueOf(price));
    }

    private void setSpentCash(double k) {
        String price = "";
        if (k != -1) {
            price = String.valueOf(k);
        } else {
            price = taInput.getText();
        }
        paneParam01.getTfOfTable().get(1).setText(String.valueOf(price));
    }

    private void addCash() {
        String price = taInput.getText();
        double oldCash = Double.parseDouble((String.valueOf(paneParam01.getTfOfTable().get(0).getText())));
        double newCash = Double.parseDouble(price);
        double cash = oldCash + newCash;
        paneParam01.getTfOfTable().get(0).setText(String.valueOf(cash));
    }

    private void buy() {
        String data = taInput.getText();
        Proccesor test = new Proccesor(data);
        double cash = Double.parseDouble((String.valueOf(paneParam01.getTfOfTable().get(0).getText())));
        if (test.getPrice() < cash && list.contains(test)) {
            list.remove(test);
            mylist.add(test);

            table.formTable(initialCapacity * 2 + 1, colWidth);
            String[][] modelList = list.getModelList();
            table.getItems().clear();
            table.setItems(FXCollections.observableArrayList(modelList));

            myTable.formTable(initialCapacity * 2 + 1, colWidth);
            modelList = mylist.getModelList();
            myTable.getItems().clear();
            myTable.setItems(FXCollections.observableArrayList(modelList));
            // Atnaujinamai maišos lentelės parametrai (geltona lentelė)
            paneParam1.getTfOfTable().get(4).setText(String.valueOf(list.size()));

            cash -= test.getPrice();
            setCash(cash);

            double spentCash = Double.parseDouble((String.valueOf(paneParam01.getTfOfTable().get(1).getText())));
            spentCash += test.getPrice();
            setSpentCash(spentCash);

            KsGui.oun(taEvents, test, MESSAGES.getString("buy"));

        } else {
            KsGui.oun(taEvents, test, MESSAGES.getString("errbuy"));
        }

    }

    private void sell() {
        String data = taInput.getText();
        Proccesor test = new Proccesor(data);
        double cash = Double.parseDouble((String.valueOf(paneParam01.getTfOfTable().get(0).getText())));
        if (test.getPrice() < cash && mylist.contains(test)) {
            mylist.remove(test);
            list.add(test);

            table.formTable(initialCapacity * 2 + 1, colWidth);
            String[][] modelList = list.getModelList();
            table.getItems().clear();
            table.setItems(FXCollections.observableArrayList(modelList));

            myTable.formTable(initialCapacity * 2 + 1, colWidth);
            modelList = mylist.getModelList();
            myTable.getItems().clear();
            myTable.setItems(FXCollections.observableArrayList(modelList));
            // Atnaujinamai maišos lentelės parametrai (geltona lentelė)
            paneParam1.getTfOfTable().get(4).setText(String.valueOf(list.size()));

            cash += test.getPrice();
            setCash(cash);

            double spentCash = Double.parseDouble((String.valueOf(paneParam01.getTfOfTable().get(1).getText())));
            spentCash -= test.getPrice();
            setSpentCash(spentCash);

            KsGui.oun(taEvents, test, MESSAGES.getString("sale"));

        } else {
            KsGui.oun(taEvents, test, MESSAGES.getString("errsale"));
        }
    }

    private void addForSelling() {
        String data = taInput.getText();
        Proccesor test = new Proccesor(data);

        mylist.add(test);

        myTable.formTable(initialCapacity * 2 + 1, colWidth);
        String[][] modelList = mylist.getModelList();
        myTable.getItems().clear();
        myTable.setItems(FXCollections.observableArrayList(modelList));
    }

    private void mapGeneration(String filePath) {
        // Išjungiami 2 ir 4 mygtukai
        IntStream.of(2, 3, 4).forEach(p -> paneButtons.getButtons().get(p).setDisable(true));
        IntStream.of(0).forEach(p -> paneButton.getButtons().get(p).setDisable(true));
        // Duomenų nuskaitymas iš parametrų lentelės (žalios)    
        readMapParameters();
        // Sukuriamas tuščias atvaizdis priklausomai nuo kolizijų tipo
        createList();
        // Jei failas nenurodytas - generuojami automobiliai ir talpinami atvaizdyje
        if (filePath == null) {
            Proccesor[] proccesorsArray = proccesorsGenerator.generateShuffleProccesorsAndIds(sizeOfGenSet, sizeOfInitialSubSet);
            for (Proccesor c : proccesorsArray) {
                list.add(c);
            }
            KsGui.ounArgs(taEvents, MESSAGES.getString("mapPuts"), list.size());
        } else { // Jei failas nurodytas skaitoma iš failo
            list.load(filePath);
            if (list.isEmpty()) {
                KsGui.ounerr(taEvents, MESSAGES.getString("fileWasNotReadOrEmpty"), filePath);
            } else {
                KsGui.ou(taEvents, MESSAGES.getString("fileWasRead"), filePath);

            }
        }
        // Atvaizdis rodomas lentelėje
        table.formTable(initialCapacity * 2 + 1, colWidth);
        String[][] modelList = list.getModelList();
        table.getItems().clear();
        table.setItems(FXCollections.observableArrayList(modelList));
        // Atnaujinamai maišos lentelės parametrai (geltona lentelė)
        paneParam1.getTfOfTable().get(4).setText(String.valueOf(list.size()));
        // Įjungiami 2 ir 4 mygtukai
        IntStream.of(2, 3, 4).forEach(p -> paneButtons.getButtons().get(p).setDisable(false));
        IntStream.of(0).forEach(p -> paneButton.getButtons().get(p).setDisable(false));

    }

    private void clearListtable() {
        
        list.clear();
        // Atvaizdis rodomas lentelėje
        table.formTable(initialCapacity * 2 + 1, colWidth);
        String[][] modelList = list.getModelList();
        table.getItems().clear();
        table.setItems(FXCollections.observableArrayList(modelList));

        IntStream.of(2, 3, 4).forEach(p -> paneButtons.getButtons().get(p).setDisable(false));

        KsGui.ou(taEvents, MESSAGES.getString("clearedList"));

    }

    private void createList() {

        this.list = new ParsableUnrolledLinkedList<>(initialCapacity, Proccesor::new);
        this.mylist = new ParsableUnrolledLinkedList<>(initialCapacity, Proccesor::new);

    }

    private void readMapParameters() {
        int i = 0;
        List<TextField> tfs = paneParam1.getTfOfTable();

        sizeOfInitialSubSet = notNegativeNumberVerifier(tfs.get(i++), "badSizeOfInitialSubSet");
        sizeOfGenSet = notNegativeNumberVerifier(tfs.get(i++), "badSizeOfGenSet");
        initialCapacity = notNegativeNumberVerifier(tfs.get(i++), "badInitialCapacity");
        colWidth = notNegativeNumberVerifier(tfs.get(i), "badColumnWidth");
    }

    private int notNegativeNumberVerifier(TextField tf, String errorMessage) {
        int result;
        try {
            result = Integer.parseInt(tf.getText());
            tf.setStyle(result < 0 ? Panels.STYLE_ERROR : Panels.STYLE_COMMON);
            if (result < 0) {
                throw new ValidationException(errorMessage, tf.getText());
            }
        } catch (NumberFormatException e) {
            tf.setStyle(Panels.STYLE_ERROR);
            throw new ValidationException(errorMessage, tf.getText());
        }

        return result;
    }

    private void fileChooseMenu() {

        FileChooser fc = new FileChooser();
        // Papildoma mūsų sukurtais filtrais
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("txt", "*.txt")
        );
        fc.setTitle((MESSAGES.getString("menuItem11")));
        fc.setInitialDirectory(new File(System.getProperty("user.dir")));
        File file = fc.showOpenDialog(stage);
        if (file != null) {
            mapGeneration(file.getAbsolutePath());
        }

    }

    private void fileSaveMenu() throws ValidationException, IOException {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("txt", "*.txt")
        );
        fc.setTitle((MESSAGES.getString("menuItem12")));
        fc.setInitialDirectory(new File(System.getProperty("user.dir")));

        File file = fc.showSaveDialog(stage);
        if (file != null) {
            filesaver(file.getAbsolutePath());
        }

    }

    private void filesaver(String filePath) throws ValidationException, IOException {
//            Files.write(Paths.get(filePath), (Iterable)proccesorSet);
        PrintWriter pw = new PrintWriter(filePath);

        Iterator<Proccesor> it = list.iterator();
        while (it.hasNext()) {
            pw.println(it.next().ToString_data());
        }
        pw.close();

    }

}
