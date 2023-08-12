import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader
                (Main.class.getResource("view/loginScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 343);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        if(Locale.getDefault().getLanguage().equals("en")
                || Locale.getDefault().getLanguage().equals("fr")){
            Locale.getDefault();
        }
        else{
            Locale.setDefault(new Locale("en"));
        }
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }

}