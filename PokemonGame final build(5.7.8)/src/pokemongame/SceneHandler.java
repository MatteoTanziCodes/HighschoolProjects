/*
 * Programer: Zain
 * class: SceneHandler
 * Project: PokemonGame
 * Date: January 19 2018
 * Description: Changes fxml scenes
 */
package pokemongame;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.*;
import javafx.event.ActionEvent;

/**
 * 
 * @author Zain
 */
public class SceneHandler {

    private String page;

    /**
     * Constructor
     * @param s 
     */
    public SceneHandler(String s) {
        page = s;
    }

    /**
     * setScene - change the scene location
     * @param s 
     */
    public void setScene(String s) {
        page = s;
    }

    /**
     * ChangeScene - changes the fxml scene to desired page
     * @param event
     * @throws IOException 
     */
    public void changeScene(ActionEvent event) throws IOException {
        Parent main_page = FXMLLoader.load(getClass().getResource(page));
        Scene main_scene = new Scene(main_page);
        Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_stage.setScene(main_scene);
        main_stage.show();
    }

    /**
     * ChangeScene overloaded- changes scene to custom fxml page
     * @param event
     * @param s
     * @throws IOException 
     */
    public void changeScene(ActionEvent event, String s) throws IOException {
        page = s;
        Parent main_page = FXMLLoader.load(getClass().getResource(page));
        Scene main_scene = new Scene(main_page);
        Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_stage.setScene(main_scene);
        main_stage.show();
    }

}
