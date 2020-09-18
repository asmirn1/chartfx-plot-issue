import de.gsi.chart.XYChart;
import de.gsi.chart.renderer.spi.ErrorDataSetRenderer;
import de.gsi.dataset.spi.DefaultErrorDataSet;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class App extends Application {

    private static final LinkedHashMap<String, double[]> dataMap = new LinkedHashMap<>();

    static {
        dataMap.put("Set 3", new double[]{0, 0, 1, 2, 3});
        dataMap.put("Set 2", new double[]{0, 1, 2, 3, 4});
        dataMap.put("Set 1", new double[]{1, 2, 3, 4, 5});
        dataMap.put("Set 5", new double[]{0, 0, 0, 0, 1});
        dataMap.put("Set 4", new double[]{0, 0, 0, 1, 2});
    }

    private ListView<String> listView;
    private ErrorDataSetRenderer renderer;


    @Override
    public void start(Stage stage) throws IOException {

        listView = new ListView<>();
        listView.getItems().setAll(dataMap.keySet());
        listView.getSelectionModel().selectedItemProperty().addListener((o, s, t) -> updateData(t));

        XYChart chart = new XYChart();
        HBox.setHgrow(chart, Priority.ALWAYS);
        chart.setAnimated(false);
        chart.getRenderers().clear();

        renderer = new ErrorDataSetRenderer();
        chart.getRenderers().add(renderer);


        HBox hBox = new HBox();
        hBox.getChildren().setAll(listView, chart);

        Scene scene = new Scene(hBox, 1200, 800);

        stage.setScene(scene);
        stage.setOnCloseRequest(windowEvent -> {
            Platform.exit();
            System.exit(0);
        });

        stage.show();
    }

    private void updateData(String key) {
        renderer.getDatasets().clear();
        DefaultErrorDataSet dataSet = new DefaultErrorDataSet(key);
        double[] data = dataMap.get(key);
        for (int i = 0; i < data.length; ++i)
            dataSet.add(i, data[i]);
        renderer.getDatasets().add(dataSet);
    }
}
