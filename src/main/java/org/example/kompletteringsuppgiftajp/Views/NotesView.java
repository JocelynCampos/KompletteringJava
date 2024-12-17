package org.example.kompletteringsuppgiftajp.Views;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.kompletteringsuppgiftajp.Entities.Notes;

public class NotesView {

    Notes notes = new Notes();

    public void ShowStage(Stage stage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        //Sökfält, TOP
        TextField searchField = new TextField("Sök: ");
        root.setTop(searchField);

        //Anteckningarna, VÄNSTER sida
        ListView<String> notesList = new ListView<>();
        notesList.getItems().addAll("Anteckning", "En till anteckning", "Prova på javaFX");
        VBox leftPanel = new VBox(10, new Label("Notes"), notesList);
        //root.setLeft();


        //Bottom. Där knapparna ska placeras
        Button newNoteButton = new Button("New note");
        Button updateButton = new Button("Update notes");
        Button removeButton = new Button("Remove note");
        HBox buttonPanel = new HBox(10, newNoteButton, updateButton, removeButton);
        root.setBottom(buttonPanel);


        Button SaveNote = new Button("Save");
        Button saveTag = new Button("Save tag");
        Button removeTag = new Button("Remove tagg");
        Button newTag = new Button("New tag");


        //root.setRight();
        VBox rightPanel = new VBox();

        //root.setCenter();


    }
}
