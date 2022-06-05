package hibernate_test;

import hibernate_test.entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Test3 {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration()    //Создаем фабрику сессий
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Employee.class)
                .buildSessionFactory();

        try {
            Session session = factory.getCurrentSession();  //Создаем сессию
            session.beginTransaction();          //Открываем транзакцию

//            List<Employee> emps = session.createQuery("from Employee")
//                    .getResultList();

            String query = "from Employee where name=:name and salary>:salary";
            List<Employee> emps = session.createQuery(query, Employee.class)
                    .setParameter("name", "Aleksandr")
                    .setParameter("salary", 650)
                    .list();

            for (Employee e : emps)
                System.out.println(e);


            session.getTransaction().commit();

            System.out.println("Done!");

        }finally {
            factory.close();
        }


    }
}
