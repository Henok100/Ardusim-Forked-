package com.protocols.topography.gui;

import com.api.ArduSimTools;
import com.setup.Text;
import com.setup.sim.logic.SimParam;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class TopographyDialogApp extends Application{

    @Override
    public void start(Stage stage){
        TopographySimProperties properties = new TopographySimProperties();
        ResourceBundle resources = null;
        try{
            FileInputStream fis = new FileInputStream(SimParam.protocolParamFile);
            resources = new PropertyResourceBundle(fis);
            fis.close();
        }catch (IOException e){
            ArduSimTools.warnGlobal(Text.LOADING_ERROR, Text.PROTOCOL_PARAMETERS_FILE_NOT_FOUND);
        }
        //load the fxml and the controller
        FXMLLoader loader = null;
        try {
            URL url = new File("src/main/resources/protocols/topography/mission.fxml").toURI().toURL();
            loader = new FXMLLoader(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        TopographyDialogController controller = new TopographyDialogController(resources,properties,stage);
        loader.setController(controller);
        loader.setResources(resources);

        //set the scene
        stage.setTitle("Mission Dialog");
        try{
            stage.setScene(new Scene(loader.load()));
        }catch(IOException e){
            e.printStackTrace();
            ArduSimTools.warnGlobal(Text.LOADING_ERROR, Text.ERROR_LOADING_FXML);
        }
        stage.setOnCloseRequest(event-> System.exit(0));
        stage.show();
    }
}