package hibernate_test;

import hibernate_test.entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Test1 {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration()    //Создаем фабрику сессий
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Employee.class)
                .buildSessionFactory();

        try {
            Session session = factory.getCurrentSession();  //Создаем сессию
            Employee emp = new Employee("Dog", "OR", "IT", 100);
            session.beginTransaction();          //Открываем транзакцию
            session.save(emp);                   //Сохраняем объект в БД
            session.getTransaction().commit();   //commit закрывает транзакцию
            System.out.println("Good!");
            System.out.println(emp);
        }finally {
            factory.close();
        }


    }
}
