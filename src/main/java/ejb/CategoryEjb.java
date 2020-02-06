package ejb;

import entity.Category;
import entity.SubCategory;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/*
    There are three types of Enterprise Java Bean (EJB)
    - Stateless
    - Stateful
    - Singleton

    To make a class a EJB, just need to tag with the
    corresponding annotation
*/

@Stateless
public class CategoryEjb {
    /*
        EJB are "managed" by the container (eg, Wildfly of GlassFish).

       this mainly implies:
       - dependency injection
       - special handling (eg automated transactions)

       this class is trivial, and needs neither.

       a stateless EJB should not keep aby internal state, eg fields, as the
       JEE container might create thousands of instances, each time serving
       potentially a different one.

        A persistence context handles a set of entities which hold data to be persisted in some persistence source ( e.g. a database)
        in particular, the context is aware of the different states an entity can have (e.g. managed, detached) in relation to
        both the context and the underlying persistence store.

        Dependency injection: the container will add it.
    */
    @PersistenceContext
   private EntityManager em;

    public Long createCategory(String name){

        Category category = new Category();
        category.setName(name);

        //Insert the new Category to the DB. em.persist to det sql injection for you.(magic)
        em.persist(category);

        return category.getId();
    }

    public Long createSubCategory(long parentId, String name){

        // EntityManager need to find the parent of the SubCategory by using em.find(Category.class, parentId)
        // again the some magic happens, "we dont need to type anything directly to det database
        Category category = em.find(Category.class, parentId);
        if (category == null) {
            throw new IllegalArgumentException("Category you trying to find, don't exist");
        } else{

            SubCategory subCategory = new SubCategory();
            subCategory.setName(name);
            subCategory.setParent(category);
            // insert the new subCategory to the database
            em.persist(subCategory);

            category.getSubCategories().add(subCategory);

            return subCategory.getId();
        }
    }

    public List<Category> getAllCategories(boolean withSub){
        /*
            the typedQuery interface extends the query interface
            it's easier to run queries and process the query result in a type safe manner when using TypedQuery interface
        */
        TypedQuery<Category> query = em.createQuery("select category from Category category",Category.class);
        List<Category> categories = query.getResultList();

        if(withSub){
            /*
                this is force loading
                force the fetching of the list by calling one of its methods.

                However, note that this approach is not very efficient, as it might create
                one or more SQL queries.
                Another approach is to use a specific JPQL query instead of "em.find", as done in
                "jee/jpa/fetch", which will be translated in one single SQL query.
                However, it all depends on the JPA provider (eg Hibernate or EclipseLink)
                does the conversion to SQL.
                When performance is critical, you need to double-check all the created SQL
                queries.

             */
            categories.forEach(category -> category.getSubCategories().size());
        }
            return categories;
        }

        public Category getCategory(long id, boolean withSub){

        // some magic happens
        Category category = em.find(Category.class, id);

        if(category != null && withSub){
            category.getSubCategories().size();
        }
        return category;
        }

        public SubCategory getSubCategory(long id){

        SubCategory subCategory = em.find(SubCategory.class, id);

            if (subCategory == null) {
                throw new IllegalArgumentException("There are no SubCategory by that id");
            }
            return subCategory;
        }


}
