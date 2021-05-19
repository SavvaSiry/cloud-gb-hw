package server;

import model.File;
import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class DataBaseModule {
    public static File findFileById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(File.class, id);
    }
    public static User findUserById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(User.class, id);
    }

    public static void save(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();
    }
    public static void save(File file) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(file);
        tx1.commit();
        session.close();
    }

    public static void update(File file) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.merge(file);
        tx1.commit();
        session.close();
    }

    public static void update(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.merge(user);
        tx1.commit();
        session.close();
    }

    public static void delete(User server) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(server);
        tx1.commit();
        session.close();
    }
    public static void delete(File file) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(file);
        tx1.commit();
        session.close();
    }

    public static void deleteFileById(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        File tokenServer = new File();
        tokenServer.setId(id);
        session.delete(tokenServer);
        tx1.commit();
        session.close();
    }

    public static void deleteUserById(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        User user = new User();
        user.setId(id);
        session.delete(user);
        tx1.commit();
        session.close();
    }

    public static File findFileByName(String name) {
        try {
            return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(File.class, name);
        }catch (Exception e){
            return null;
        }
    }
    public static User findUserByName(String name) {
        try {
            return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(User.class, name);
        }catch (Exception e){
            return null;
        }
    }

    public static boolean checkPass(String name, String pass) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(User.class, name).getPass().equals(pass);
    }

    public static List<User> findAllUsers() {
        List<User> users = (List<User>)  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From User").list();
        return users;
    }
    public static List<File> findAllFiles() {
        List<File> files = (List<File>)  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From User").list();
        return files;
    }
}