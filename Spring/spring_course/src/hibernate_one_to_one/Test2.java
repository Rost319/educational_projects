package hibernate_one_to_one;

import hibernate_one_to_one.entity.Detail;
import hibernate_one_to_one.entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class Test2 {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Employee.class)
                .addAnnotatedClass(Detail.class)
                .buildSessionFactory();

        Session session = null;

        try {
//            session = factory.getCurrentSession();  //Создаем сессию
//            session.beginTransaction();          //Открываем транзакцию
//            Employee employee = new Employee("Nikolay", "Ivanov", "HR", 850);
//            Detail detail = new Detail("New-York", "654987321", "Nikola@gmail.ru");
//
//            employee.setEmpDetail(detail);
//            detail.setEmployee(employee);
//            session.save(detail);
//
//            session.getTransaction().commit();   //commit закрывает транзакцию
//            System.out.println("Good!");

//-------------------------------------------------------------------------------------------

//            session = factory.getCurrentSession();  //Создаем сессию
//            session.beginTransaction();          //Открываем транзакцию
//            Detail detail = session.get(Detail.class, 4);
//            System.out.println(detail.getEmployee());
//
//
//            session.getTransaction().commit();   //commit закрывает транзакцию
//            System.out.println("Done!");

//------------------------------------------------------------------------------------------

            session = factory.getCurrentSession();  //Создаем сессию
            session.beginTransaction();          //Открываем транзакцию

            Detail detail = session.get(Detail.class, 1);
            detail.getEmployee().setEmpDetail(null);
            session.delete(detail);

            session.getTransaction().commit();   //commit закрывает транзакцию
            System.out.println("Done!");


        }finally {
            session.close();
            factory.close();
        }
    }
}
