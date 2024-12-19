package org.example.kompletteringsuppgiftajp.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.kompletteringsuppgiftajp.Entities.Tags;

import java.util.List;

public class TagsDAO {


    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("komplettering-unit");

    //Create
    public void saveTags(Tags tags) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(tags);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive())  {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    //ReadOne
    public Tags getTagsId(int id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Tags tags = null;

        try {
            tags = em.find(Tags.class, id);
        } catch (Exception e) {
           e.printStackTrace();
        }
        finally {
            em.close();
        }
        return tags;
    }

    //ReadAll
    public List<Tags> getAllTags () {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Tags> tagsList = null;
        try {
            tagsList = em.createQuery("Select t FROM Tags t", Tags.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return tagsList;
    }

    //Update
    public boolean updateTags(Tags updatedTags) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            Tags existingTags = em.find(Tags.class, updatedTags.getId());
            if (existingTags != null) {
                existingTags.setTagContent(updatedTags.getTagContent());
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
    public boolean deleteTags(int id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();
            Tags tags = em.find(Tags.class, id);

            if (tags != null) {
                em.remove(tags);
                transaction.commit();
                return true;
            } else {
                System.out.println("Tag with ID " + id + " not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return false;
    }
}
