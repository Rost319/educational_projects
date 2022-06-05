package hibernate_one_to_one;

import hibernate_one_to_one.entity.Detail;
import hibernate_one_to_one.entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class Test1 {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Employee.class)
                .addAnnotatedClass(Detail.class)
                .buildSessionFactory();

        Session session = null;

        try {
//            Session session = factory.getCurrentSession();  //Создаем сессию
//            session.beginTransaction();          //Открываем транзакцию
//            Employee employee = new Employee("Zaur", "Tregulov", "IT", 500);
//            Detail detail = new Detail("Baku", "123456789", "zaurtregulov@gmail.ru");
//            employee.setEmpDetail(detail);
//
//            session.save(employee);
//
//            session.getTransaction().commit();   //commit закрывает транзакцию
//            System.out.println("Good!");

//---------------------------------------------------------------------------------

//            Session session = factory.getCurrentSession();  //Создаем сессию
//            session.beginTransaction();          //Открываем транзакцию
//            Employee employee = new Employee("Oleg", "Smirnov", "Sales", 700);
//            Detail detail = new Detail("Moscow", "987654321", "olejka@gmail.ru");
//            employee.setEmpDetail(detail);
//
//            session.save(employee);
//
//            session.getTransaction().commit();   //commit закрывает транзакцию
//            System.out.println("Good!");


//            ------------------------------------------------------------------------



            session = factory.getCurrentSession();  //Создаем сессию
            session.beginTransaction();          //Открываем транзакцию

            Employee emp = session.get(Employee.class, 2);
            session.delete(emp);

            session.getTransaction().commit();   //commit закрывает транзакцию
            System.out.println("Good!");


        }finally {
            session.close();
            factory.close();
        }
    }
}
