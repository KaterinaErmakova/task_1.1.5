package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;

public class Main {
    public static void main(String[] args) {
        UserDao userDao = new UserDaoHibernateImpl();
        userDao.createUsersTable();
        userDao.saveUser("Vasiliy", "Livanov", (byte) 89);
        userDao.saveUser("Alla", "Pugacheva", (byte) 76);
        userDao.saveUser("Jason", "Statham", (byte) 57);
        userDao.saveUser("Johnny", "Sins", (byte) 46);
        System.out.println(userDao.getAllUsers());
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
