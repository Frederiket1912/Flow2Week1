

package tester;

import entity.Customer;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class Tester {
    
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        
        
        Customer c1 = new Customer("Hans", "Hansen");
        c1.addHobby("Fiskeri");
        c1.addHobby("Madlavning");
        c1.addHobby("Cyckling");
        c1.addPhone("1234", "Home");
        c1.addPhone("4321", "Mobile");
        
        Customer c2 = new Customer("Peter", "Petersen");
        c2.addHobby("Læsning");
        c2.addHobby("Frimærker");
        c2.addPhone("5678", "Home");
        c2.addPhone("8765", "Work");
        
        try {
            em.getTransaction().begin();
            em.persist(c1);
            em.persist(c2);
            em.getTransaction().commit();
            //Verify that books are managed and has been given a database id
            System.out.println(c1.getId());
            System.out.println(c2.getId());

        } finally {
            em.close();
        }
    }
    
}
