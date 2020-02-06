package entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CategoryEntityTest extends EntityTestBase{

    @Test
    public void testTooLongName(){

        String name = new String(new char[200]);

        Category category = new Category();
        category.setName(name);

        assertFalse(persistInTransaction(category));

        category.setId(null);
        category.setName("foo");

        assertTrue(persistInTransaction(category));
    }
    @Test
    public void testUniqueName(){

        String name =  "Unique entity.Category";

        Category uniqueCategory = new Category();
        uniqueCategory.setName(name);

        assertTrue(persistInTransaction(uniqueCategory));

        Category notUniqueCategory = new Category();
        notUniqueCategory.setName(name);

        assertFalse(persistInTransaction(notUniqueCategory));
    }
}
