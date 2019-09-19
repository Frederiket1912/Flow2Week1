package facade;

import entity.Address;
import entity.Customer;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class CustomerFacade {

    private static EntityManagerFactory emf;
    private static CustomerFacade instance;

    public CustomerFacade() {
    }

    public static CustomerFacade getCustomerFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CustomerFacade();
        }
        return instance;
    }

    public Customer getCustomer(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            Customer c = em.find(Customer.class, id);
            return c;
        } finally {
            em.close();
        }
    }

    public List<Customer> getCustomers() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery q1 = em.createQuery("Select c from Customer c", Customer.class);
            return q1.getResultList();
        }finally{
            em.close();
        }
    }

    public Customer addCustomer(Customer cust) {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(cust);
            em.getTransaction().commit();
            return cust;
        }finally{
            em.close();
        }
    }

    public Customer deleteCustomer(int id) {
        EntityManager em = emf.createEntityManager();
        try{
            Customer c = getCustomer(id);
            Query q1 = em.createQuery("delete from Customer c where c.id = :id").setParameter("id", id);
            em.getTransaction().begin();
            q1.executeUpdate();
            em.getTransaction().commit();
            return c;
        }finally{
            em.close();
        }
    }

    public Customer editCustomer(Customer cust) {
        EntityManager em = emf.createEntityManager();
        try{
            Query q1 = em.createQuery("UPDATE Customer c SET c.firstName = :firstName, c.lastName = :lastName, c.addresses = :addresses WHERE c.id = :id")
                    .setParameter("firstName", cust.getFirstName())
                    .setParameter("lastName", cust.getLastName())
                    .setParameter("addresses", cust.getAddresses())
                    .setParameter("id", cust.getId());
            em.getTransaction().begin();
            q1.executeUpdate();
            em.getTransaction().commit();
            return getCustomer(cust.getId());
        }finally{
            em.close();
        }
    }
    
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        CustomerFacade cf = CustomerFacade.getCustomerFacade(emf);
        Customer c1 = new Customer("Hans", "Hansen");
        c1.addAddress(new Address("vej1", "by1"));
        c1.addAddress(new Address("vej2", "by2"));
        System.out.println(cf.addCustomer(c1));
        System.out.println(cf.getCustomer(1));
    }
}

