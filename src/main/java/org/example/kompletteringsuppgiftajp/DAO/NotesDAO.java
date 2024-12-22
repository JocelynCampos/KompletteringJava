package org.example.kompletteringsuppgiftajp.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.kompletteringsuppgiftajp.Entities.Notes;
import org.example.kompletteringsuppgiftajp.Entities.Tags;

import java.util.List;

public class NotesDAO {
    //CRUD operationer

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("komplettering-unit");

    //Create
    public void saveNotes(Notes notes){
        EntityManager eM = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = eM.getTransaction();
            transaction.begin();
            eM.persist(notes);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            eM.close();
        }
    }

    //Read one
    public Notes getNotesID (int id) {
        EntityManager eM = ENTITY_MANAGER_FACTORY.createEntityManager();
        Notes note = null;

        try {
            note = eM.find(Notes.class, id); // hämtar id från klassen Notes
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            eM.close();
        }
        return note;
    }

    //Read all
    public List<Notes> getAllNotes() {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Notes> notesList = null;

        try {
            notesList = em.createQuery("Select n FROM Notes n", Notes.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return notesList;
    }

    //Update
    public boolean updateNotes(Notes updatedNotes) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();
            Notes existingNote = em.find(Notes.class, updatedNotes.getId());
            if (existingNote != null) {
                existingNote.setNoteTitle(updatedNotes.getNoteTitle());
                existingNote.setNoteContent(updatedNotes.getNoteContent());
                em.merge(existingNote);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }


    //Delete
    public boolean deleteNote(int id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            Notes notes = em.find(Notes.class, id);

            if (notes != null) {
                em.remove(notes);
                transaction.commit();
                return true;
            } else {
                System.out.println("Note with ID " + id + " not found.");
            }
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
        return false;
    }

    public void connectTagsToNotes(int noteId, int tagId) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();

            Notes notes = em.find(Notes.class, noteId);
            Tags tags = em.find(Tags.class, tagId);

            if (notes != null && tags != null) {
                notes.getTags().add(tags);
                tags.getNotes().add(notes);

                em.merge(notes);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
