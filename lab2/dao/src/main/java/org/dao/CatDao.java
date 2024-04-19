package org.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.model.Cat;

public class CatDao {

    private final SessionFactory sessionFactory;

    public CatDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public CatDao() {
        Configuration configuration = new Configuration().configure();
        this.sessionFactory = configuration.buildSessionFactory();
    }

    public void saveCat(Cat cat) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(cat);
            tx.commit();
        }
    }
    public Cat getCatById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Cat.class, id);
        }
    }
    public Cat getCatByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Cat.class, name);
        }
    }

    public void updateCat(Cat cat) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(cat);
            tx.commit();
        }
    }

    public void deleteCat(Cat cat) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(cat);
            tx.commit();
        }
    }

    public void addFriendToCat(Cat cat, Cat friend) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Hibernate.initialize(cat.getFriends());
            Hibernate.initialize(friend.getFriends());
            cat.addFriend(friend);
            friend.addFriend(cat);
            session.merge(cat);
            tx.commit();
        }
    }
}
