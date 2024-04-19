package org.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.jetbrains.annotations.NotNull;
import org.model.Cat;
import org.model.Owner;

import java.util.List;


public class OwnerDao {
    private final SessionFactory sessionFactory;

    public OwnerDao() {
        Configuration configuration = new Configuration().configure();
        this.sessionFactory = configuration.buildSessionFactory();
    }

    public OwnerDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public void saveOwner(Owner owner) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(owner);
            transaction.commit();
        }
    }

    public Owner getOwnerById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Owner.class, id);
        }
    }
    public void updateOwner(@NotNull Owner owner) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(owner);
            transaction.commit();
        }
    }

    public void deleteOwner(Owner owner) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(owner);
            transaction.commit();
        }
    }
    public List<Cat> getCatsByOwnerId(int ownerId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Cat> query = session.createNativeQuery("SELECT * FROM Cats c WHERE c.owner_id = :ownerId", Cat.class);
            query.setParameter("ownerId", ownerId);
            return query.list();
        }
    }
}
