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

    private final TextArea taInput = new TextArea();
    private final GridPane paneParam12Events = new GridPane();
    private final GridPane paneRight = new GridPane();
    private final TextArea taEvents = new TextArea();
    private Panels paneParam1, paneButtons;
    private MapTable<String[], String> table;
    private MainWindowMenu mainWindowMenu;
    private final Stage stage;

    private ParsableUnrolledLinkedList<Proccesor> list;
    private int sizeOfInitialSubSet, sizeOfGenSet, colWidth, initialCapacity;
    private final ProccesorsGenerator proccesorsGenerator = new ProccesorsGenerator();

    public MainWindow(Stage stage) {
        this.stage = stage;
        initComponents();
    }

    private void initComponents() {
        // Formuojamas mėlynos spalvos skydelis (dešinėje pusėje)
        // Formuojamas mygtukų tinklelis (pilkas). Naudojama klasė Panels.
        paneButtons = new Panels(
                new String[]{
                    MESSAGES.getString("button1"),
                    MESSAGES.getString("button2"),
                    MESSAGES.getString("button3"),
                    MESSAGES.getString("button4")}, 1, 4);
        paneButtons.getButtons().forEach((btn) -> btn.setOnAction(this));
        IntStream.of(1, 3).forEach(p -> paneButtons.getButtons().get(p).setDisable(true));

        // Viskas sudedama į mėlynos spalvos skydelį 
        GridPane.setVgrow(taInput, Priority.ALWAYS);
        taInput.setPrefWidth(TF_WIDTH);
        Stream.of(new Label(MESSAGES.getString("border1")),
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
//                        fileChooseMenu();
                    } else if (source.equals(mainWindowMenu.getMenus().get(0).getItems().get(1))) {
                        KsGui.ounerr(taEvents, MESSAGES.getString("notImplemented"));
                    } else if (source.equals(mainWindowMenu.getMenus().get(0).getItems().get(3))) {
                        System.exit(0);
                    } else if (source.equals(mainWindowMenu.getMenus().get(1).getItems().get(0))) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.initStyle(StageStyle.UTILITY);
                        alert.setTitle(MESSAGES.getString("menuItem21"));
                        alert.setHeaderText(MESSAGES.getString("author"));
                        alert.showAndWait();
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
        setRight(paneRight);
        setCenter(vboxTable);
        setBottom(paneParam12Events);
        appearance();
    }

    private void appearance() {
        paneParam1.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        paneRight.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        paneButtons.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
        taInput.setFont(Font.font("Monospaced", 12));
        taInput.setDisable(true);
        taEvents.setEditable(false);
        taEvents.setFont(Font.font("Monospaced", 12));

        TextField c = paneParam1.getTfOfTable().get(paneParam1.getTfOfTable().size() - 1);
        c.setEditable(false);
        c.setMouseTransparent(true);
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

    private void handleButtons(Object source) {
        if (source.equals(paneButtons.getButtons().get(0))) {
            mapGeneration(null);
            taInput.setDisable(false);
        } else if (source.equals(paneButtons.getButtons().get(1))) {
//            mapPut();
        } else if (source.equals(paneButtons.getButtons().get(2))) {
//            mapEfficiency();
        } else if (source.equals(paneButtons.getButtons().get(3))) {
            KsGui.ounerr(taEvents, MESSAGES.getString("notImplemented"));
        }
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

    private void mapGeneration(String filePath) {
        // Išjungiami 2 ir 4 mygtukai
        IntStream.of(1, 3).forEach(p -> paneButtons.getButtons().get(p).setDisable(true));
        // Duomenų nuskaitymas iš parametrų lentelės (žalios)    
        readMapParameters();
        // Sukuriamas tuščias atvaizdis priklausomai nuo kolizijų tipo
        createList();

        // Sukuriamas tuščias atvaizdis priklausomai nuo kolizijų tipo
//        createMap();
        // Jei failas nenurodytas - generuojami automobiliai ir talpinami atvaizdyje
        if (filePath == null) {
            Proccesor[] proccesorsArray = proccesorsGenerator.generateShuffleProccesorsAndIds(sizeOfGenSet, sizeOfInitialSubSet);
            for (Proccesor c : proccesorsArray) {
                list.add(c);
            }
            KsGui.ounArgs(taEvents, MESSAGES.getString("mapPuts"), list.size());

//else { // Jei failas nurodytas skaitoma iš failo
//            map.load(filePath);
//            if (map.isEmpty()) {
//                KsGui.ounerr(taEvents, MESSAGES.getString("fileWasNotReadOrEmpty"), filePath);
//            } else {
//                KsGui.ou(taEvents, MESSAGES.getString("fileWasRead"), filePath);
//
//             }
        }
        // Atvaizdis rodomas lentelėje
        table.formTable(initialCapacity * 2 + 1, colWidth);
        String[][] modelList = list.getModelList();
        table.getItems().clear();
        table.setItems(FXCollections.observableArrayList(modelList));
        // Atnaujinamai maišos lentelės parametrai (geltona lentelė)
        paneParam1.getTfOfTable().get(4).setText(String.valueOf(list.size()));
        // Įjungiami 2 ir 4 mygtukai
        IntStream.of(1, 3).forEach(p -> paneButtons.getButtons().get(p).setDisable(false));
    }

    private void createList() {

        this.list = new ParsableUnrolledLinkedList<>(initialCapacity);

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

}
