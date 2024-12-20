package org.example.kompletteringsuppgiftajp;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.kompletteringsuppgiftajp.Views.NotesView;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
    NotesView notesView = new NotesView(stage);
    notesView.ShowStage(stage);
    }

    public static void main(String[] args) {
        //Starta JAVAFX
        launch(args);
    }
}
