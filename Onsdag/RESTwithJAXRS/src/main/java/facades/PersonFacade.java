package facades;

import entities.Person;
import interfaces.IPersonFacade;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class PersonFacade implements IPersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PersonFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //TODO Remove/Change this before use
    public long getRenameMeCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long renameMeCount = (long) em.createQuery("SELECT COUNT(r) FROM RenameMe r").getSingleResult();
            return renameMeCount;
        } finally {
            em.close();
        }

    }

    @Override
    public Person addPerson(String fName, String lName, String phone) {
        Person p = new Person(fName, lName, phone);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
            return p;
        } finally {
            em.close();
        }
    }

    @Override
    public Person deletePerson(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            Query q1 = em.createQuery("DELETE FROM Person p where p.id = :id")
                    .setParameter("id", id);
            em.getTransaction().begin();
            Person p = getPerson(id);
            q1.executeUpdate();
            em.getTransaction().commit();
            return p;
        } finally {
            em.close();
        }
    }
    
    
    @Override
    public Person getPerson(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            Person c = em.find(Person.class, id);
            return c;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Person> getAllPersons() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery q1 = em.createQuery("Select p from Person p", Person.class);
            return q1.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Person editPerson(Person person) {
        EntityManager em = emf.createEntityManager();
        try {
            Query q1 = em.createQuery("UPDATE Person p SET p.firstName = :firstName, p.lastName = :lastName, p.phone = :phone WHERE p.id = :id")
                    .setParameter("firstName", person.getFirstName())
                    .setParameter("lastName", person.getLastName())
                    .setParameter("phone", person.getPhone())
                    .setParameter("id", person.getId());
            em.getTransaction().begin();
            q1.executeUpdate();
            em.getTransaction().commit();
            return getPerson(person.getId());
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        PersonFacade pf = PersonFacade.getPersonFacade(emf);
        System.out.println(pf.deletePerson(1));
        //pf.addPerson("Added", "Added", "123");
//        Person p = pf.getPerson(1);
//        
//        System.out.println(p.getFirstName());
//        
//        p.setFirstName("Edited2");
//        p.setLastName("Edited2");
//        
//        
//        Person pEdited = pf.editPerson(p);
//        
//        
//        System.out.println(pEdited.getFirstName());
    }
}
