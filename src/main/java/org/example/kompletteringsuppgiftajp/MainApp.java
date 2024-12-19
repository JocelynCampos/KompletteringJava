package org.example.kompletteringsuppgiftajp;


import javafx.application.Application;
import javafx.stage.Stage;
import org.example.kompletteringsuppgiftajp.Views.NotesView;

public class MainApp extends Application {

    public void start(Stage primaryStage) {
        NotesView notesView = new NotesView();
        notesView.ShowStage(primaryStage);
    }
    public static void main (String [] args) {
        launch(args);
    }

}
