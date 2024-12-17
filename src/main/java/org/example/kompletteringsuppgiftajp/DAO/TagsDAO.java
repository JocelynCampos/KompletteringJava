package org.example.kompletteringsuppgiftajp.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.kompletteringsuppgiftajp.entity.Tags;

import java.util.List;

public class TagsDAO {


    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("komplettering.unit");

    //Create
    private void saveTags(Tags tags) {
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
    }


    //Update
    //Delete

}
