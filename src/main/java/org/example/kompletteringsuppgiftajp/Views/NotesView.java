package org.example.kompletteringsuppgiftajp.Views;

import jakarta.persistence.EntityManager;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.kompletteringsuppgiftajp.DAO.NotesDAO;
import org.example.kompletteringsuppgiftajp.DAO.TagsDAO;
import org.example.kompletteringsuppgiftajp.Entities.Notes;
import org.example.kompletteringsuppgiftajp.Entities.Tags;

import java.util.List;
import java.util.stream.Collectors;

public class NotesView {

    private final NotesDAO notesDAO = new NotesDAO();
    private final TagsDAO tagsDAO = new TagsDAO();
    private final Stage stage;
    private ListView<String> notesList;
    private ListView<String> tagsList;
    private TextField titleField;
    private TextArea contentField;
    //private TagField tagField;

    public NotesView(Stage stage) {
        this.stage = stage;
    }


    public void ShowStage() {
        BorderPane root = new BorderPane();

        root.setPadding(new Insets(10));

        //TOP Sökfält
        TextField searchField = new TextField();
        searchField.setPromptText("Sök: ");
        root.setTop(searchField);

        //VÄNSTER sida Anteckningarna
        notesList = new ListView<>();
        VBox leftPanel = new VBox(10, new Label("Notes"), notesList);
        leftPanel.setPadding(new Insets(10));
        root.setLeft(leftPanel);

        //Bottom, knappar till anteckningar
        Button newNoteButton = new Button("Save note");
        Button updateNoteButton = new Button("Update notes");
        Button removeNoteButton = new Button("Remove note");
        Button clearAllFieldsButton = new Button("Clear Fields");

        HBox buttonPanel = new HBox(10, newNoteButton, updateNoteButton, removeNoteButton, clearAllFieldsButton);
        root.setBottom(buttonPanel);

        //Center Innehåll
        titleField = new TextField();
        titleField.setPromptText("Title");
        contentField = new TextArea();
        contentField.setPromptText("Content:");

        VBox centerPanel = new VBox(10, new Label("Title"), titleField, new Label("Content"), contentField);
        centerPanel.setPadding(new Insets(10));
        root.setCenter(centerPanel);

        //Höger Taggar
        tagsList = new ListView<>();
        TextField addTagField = new TextField();
        addTagField.setPromptText("Add new tag");
        Button addTagButton = new Button("Add tag");
        Button removeTagButton = new Button("Remove tagg");
        VBox rightPanel = new VBox(10, new Label("Taggar"), tagsList, addTagField, addTagButton, removeTagButton);
        rightPanel.setPadding(new Insets(10));
        root.setRight(rightPanel);

        //Button funktionalitet
        newNoteButton.setOnAction(actionEvent -> {
            String title = titleField.getText();
            String content = contentField.getText();
            String tag = addTagField.getText();

            if (!title.isEmpty() && !content.isEmpty()) {
                Notes newNote = new Notes();
                newNote.setNoteTitle(title);
                newNote.setNoteContent(content);

                if (tag !=null && !tag.trim().isEmpty()) {
                    Tags existingTag = tagsDAO.getAllTags()
                            .stream()
                            .filter(tags -> tags.getTagContent().equalsIgnoreCase(tag))
                            .findFirst()
                            .orElse(null);

                    if (existingTag == null) {
                        existingTag = new Tags(tag);
                        tagsDAO.saveTags(existingTag);
                    }
                    newNote.getTags().add(existingTag);
                }

                notesDAO.saveNotes(newNote);//Save in database
                loadNotes(notesList); //Updatera listan
                titleField.clear();
                contentField.clear();
                addTagField.clear();
            } else {
                System.out.println("Empty in here. Waiting for content :) ");
            }
        });

        //Updatera anteckning
        updateNoteButton.setOnAction(actionEvent -> {
            String selectedNote = notesList.getSelectionModel().getSelectedItem();

            if (selectedNote != null) {
                Notes noteToUpdate = notesDAO.getAllNotes()
                        .stream()
                        .filter(notes -> notes.getNoteTitle().equals(selectedNote))
                        .findFirst()
                        .orElse(null);

                if (noteToUpdate != null) {
                    String newTitle = titleField.getText().trim();
                    String newContent = contentField.getText().trim();

                    if (!newTitle.isEmpty() && !newContent.isEmpty()) {
                        noteToUpdate.setNoteTitle(newTitle);
                        noteToUpdate.setNoteContent(newContent);
                        notesDAO.updateNotes(noteToUpdate);
                        loadNotes(notesList);
                        alertUser(Alert.AlertType.INFORMATION, "Success","Notes updated.");
                    } else {
                        alertUser(Alert.AlertType.WARNING,"Empty fields. ","Fill title and content.");
                    }
                }
            }
        });


        removeNoteButton.setOnAction(actionEvent -> {
            String selectedNote = notesList.getSelectionModel().getSelectedItem();
            if (selectedNote != null) {
                Notes noteToDelete = notesDAO.getAllNotes()
                        .stream()
                        .filter(notes -> notes.getNoteTitle().equals(selectedNote))
                        .findFirst()
                        .orElse(null);
                if (noteToDelete != null) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Sure you want to delete this note?", ButtonType.YES, ButtonType.NO);
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.YES) {
                        notesDAO.deleteNote(noteToDelete.getId()); // Ta bort från databasen
                        loadNotes(notesList);
                        alertUser(Alert.AlertType.INFORMATION, "Succsess.","Note successfully removed.");
                    }
                }
            } else {
                System.out.println("No note is selected.");
            }
        });

        //Rensa allt knapp
        clearAllFieldsButton.setOnAction(actionEvent -> clearAllFields());


        //AddTag knapp
        addTagButton.setOnAction(actionEvent -> {
            String selectedNoteTitle = notesList.getSelectionModel().getSelectedItem();
            String newTagContent = addTagField.getText();
            if (selectedNoteTitle != null && !newTagContent.isEmpty()) {
                Notes selectedNote = notesDAO.getAllNotes()
                        .stream()
                        .filter(notes -> notes.getNoteTitle().equals(selectedNoteTitle))
                        .findFirst()
                        .orElse(null);
                if (selectedNote != null) {
                    Tags existingTag = tagsDAO.getAllTags()
                            .stream()
                            .filter(tags -> tags.getTagContent().equals(newTagContent))
                            .findFirst()
                            .orElse(null);
                    if (existingTag == null) {
                        Tags createNewTag = new Tags(newTagContent);
                        tagsDAO.saveTags(createNewTag);
                        existingTag = createNewTag;
                    }
                    notesDAO.connectTagsToNotes(selectedNote.getId(), existingTag.getId());
                    Notes refreshedNote = notesDAO.getNotesID(selectedNote.getId());
                    showTagsForEachNote(refreshedNote);
                    addTagField.clear();
                }
            } else {
                alertUser(Alert.AlertType.WARNING, "Empty", "Enter a tag before adding");
            }
        });

        // RemoveTag knapp
        removeTagButton.setOnAction(actionEvent -> {
            String selectedTag = tagsList.getSelectionModel().getSelectedItem();
            if (selectedTag != null) {
                List<Tags> tagsListDB = tagsDAO.getAllTags();
                Tags deleteTag = tagsListDB.stream()
                        .filter(tags -> tags.getTagContent().equals(selectedTag))
                        .findFirst()
                        .orElse(null);
                if (deleteTag != null) {
                    boolean isDeleted = tagsDAO.deleteTags(deleteTag.getId());
                    if (isDeleted) {
                        tagsList.getItems().remove(selectedTag);
                    }
                }
            } else  {
                alertUser(Alert.AlertType.WARNING, "No selection","Select a tag to remove.");
            }
        });

        //Listener för sökning i notes
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            notesList.getItems().clear();
            tagsList.getItems().clear();

            if (newValue == null || newValue.trim().isEmpty()) {
                loadNotes(notesList);
                return;
            }

            List<Notes> filterNotes = notesDAO.getAllNotes()
                    .stream()
                    .filter(notes -> notes.getNoteTitle().toLowerCase().contains(newValue.toLowerCase()) ||
                            notes.getTags().stream()
                                    .anyMatch(tags -> tags.getTagContent().toLowerCase().contains(newValue.toLowerCase())))
                    .collect(Collectors.toList());

            filterNotes.forEach(notes -> {
                notesList.getItems().add(notes.getNoteTitle());

                notes.getTags().forEach(tags -> tagsList.getItems().add(tags.getTagContent()));
            });
        });

        //Lyssnare för att visa anteckningar
        notesList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue ) -> {
            if (newValue != null){
                Notes selectedNote = notesDAO.getAllNotes()
                        .stream()
                        .filter(notes -> notes.getNoteTitle().equals(newValue))
                        .findFirst()
                        .orElse(null);

                if (selectedNote != null) {
                    titleField.setText(selectedNote.getNoteTitle());
                    contentField.setText(selectedNote.getNoteContent());
                    showTagsForEachNote(selectedNote);
                }
            } else {
                titleField.clear();
                contentField.clear();
                tagsList.getItems().clear();
            }
        });

        loadNotes(notesList);

        //Visa scenen
        this.stage.setScene(new Scene(root, 800, 600));
        this.stage.setTitle("Notes app");
        this.stage.show();

    }

    private void loadNotes(ListView<String> notesList) {
        notesList.getItems().clear();
        List<Notes> allNotes = notesDAO.getAllNotes();
        if (allNotes != null && !allNotes.isEmpty()) {
            for (Notes note : allNotes)
                notesList.getItems().add(note.getNoteTitle());
        }
    }

    private void showTagsForEachNote(Notes selectedNote) {
        tagsList.getItems().clear();

        if (selectedNote != null && selectedNote.getTags() != null) {
            selectedNote.getTags().forEach(tags -> {
                tagsList.getItems().add(tags.getTagContent());
                System.out.println(tags.getTagContent());
            });
        } else {
            System.out.println("No tags here buddy! :) ");
        }
    }

    private void clearAllFields() {
        titleField.clear();
        contentField.clear();
        tagsList.getItems().clear();
        notesList.getSelectionModel().clearSelection();
    }

    private void alertUser(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();

    }

}
