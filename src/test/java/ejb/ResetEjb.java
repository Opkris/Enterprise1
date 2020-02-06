package ejb;

import entity.Category;
import entity.Quiz;
import entity.SubCategory;

import javax.ejb.Stateless;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/*
    Dependency Injection by Reflection:

    @Stateless
    public class ....{

    @PersistenceContext
    private EntityManager em;

    For "em", no input for constructor, and no setter
    JEE container will automatically inject the current active "em"
    EJB just need to declare the dependency as a field...
    how it is created and injected is a job for the container

*/


@Stateless
public class ResetEjb {

    @PersistenceContext
    private EntityManager em;

    public void ResetDB(){

        delete(Quiz.class);
        delete(SubCategory.class);
        delete(Category.class);
    }

    private void delete(Class<?> entity){

        if (entity == null || entity.getAnnotation(Entity.class) == null) {
            throw new IllegalArgumentException("Invalid/non entity class");
        }
        String name = entity.getSimpleName();

        Query query = em.createQuery("delete from " + name);
        query.executeUpdate();
    }
}
