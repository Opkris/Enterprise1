package entity;

import org.junit.jupiter.api.Test;

import javax.persistence.TypedQuery;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuizEntityTest extends EntityTestBase{

// after det update form part 03 the testQuiz will fail because
// we added a link to entity.SubCategory, and set that to : Cannot be null.

    @Test
    public void testQuiz(){

        Quiz quiz = new Quiz();
        quiz.setQuestion("will there be a tomorrow?");
        quiz.setAnswer1("yes");
        quiz.setAnswer2("no");
        quiz.setAnswer3("maybe");
        quiz.setAnswer4("what the hell");
        quiz.setCorrectAnswer(0);

        boolean persisted = persistInTransaction(quiz);
        // Missing sub-category after update from part 03 so we need ether to add a
        // subcategory or set assertTrue to assertFalse  ;)
        assertFalse(persisted);
    }

    @Test
    public SubCategory testQuizWithSubcategory(){
        // if this test fail's first time you try to run it, check if you have
        // created a Category constructor with subcategory = new ArrayList <>()


        // Creating a entity.Category with the name "Enterprise 1"
        Category category = new Category();
        category.setName("Enterprise 1");

        // Creating a entity.SubCategory with the name "JPA"
        SubCategory subCategory = new SubCategory();
        subCategory.setName("JPA");

        // Adding entity.SubCategory "JPA" to "Enterprise 1" entity.Category's entity.SubCategory List
        category.getSubCategories().add(subCategory);
        // Setting "Enterprise 1" as the parent in the entity.SubCategory "JPA" class
        subCategory.setParent(category);

        Quiz quiz = new Quiz();
        quiz.setQuestion("Will this test pass?");
        quiz.setAnswer1("Yes");
        quiz.setAnswer2("No");
        quiz.setAnswer3("Maybe");
        quiz.setAnswer4("No idea");
        quiz.setCorrectAnswer(0);

        quiz.setSubCategory(subCategory);

        // Verify that it can persist these 3 entities into the H2 database.
        assertTrue(persistInTransaction(category,subCategory,quiz));

        return subCategory;
    }
    private Quiz createQuiz(SubCategory subCategory, String question){

        Quiz quiz = new Quiz();
        quiz.setQuestion(question);
        quiz.setAnswer1("a");
        quiz.setAnswer2("b");
        quiz.setAnswer3("c");
        quiz.setAnswer4("d");
        quiz.setCorrectAnswer(0);

        quiz.setSubCategory(subCategory);

        return quiz;
    }

    // when we are adding a entity.SubCategory to eny entity.Category, its impotent that we also
    // are setting/adding a parent to the subCategory ( as a link back )
    public SubCategory addSubcategory(Category category, String subcategoryName){
        SubCategory subCategory = new SubCategory();
        subCategory.setName(subcategoryName);

        category.getSubCategories().add(subCategory);
        subCategory.setParent(category);

        return subCategory;
    }

    @Test
    public void testQueries(){

        // Creating a new entity.Category and setting the name to "JEE"
        Category jee = new Category();
        jee.setName("JEE");

        // Creating 3 subCategories "JPA", "EJB", "JSF"
        SubCategory jpa = addSubcategory(jee, "JPA");
        SubCategory ejb = addSubcategory(jee, "EJB");
        SubCategory jsf = addSubcategory(jee, "JSF");

        // confirm that the entity.Category, and the 3 SubCategories is inserted to H2 Database
        assertTrue(persistInTransaction(jee, jpa, ejb, jsf));

        // creating 4 different quizzes: 2 in "JPA" and 1 each for the remaining
        Quiz a = createQuiz(jpa,"a");
        Quiz b = createQuiz(jpa,"b");
        Quiz c = createQuiz(ejb,"c");
        Quiz d = createQuiz(jsf,"d");

        // confirming that they are inserted in to the database
        assertTrue(persistInTransaction(a,b,c,d));

        // Create a TypedQuery in which, using JPQL..
        TypedQuery<Quiz> queryJPA = em.createQuery(
        // SQL statement inside a String => ""
                "select q from Quiz q where q.subCategory.name='JPA'",Quiz.class);
        // creating a List to store all information inside quizJPA
        List<Quiz> quizJPA = queryJPA.getResultList();

        // confirming that there are ony 2 elements inside "quizJPA" and the result
        assertEquals(2, quizJPA.size());
        assertTrue(quizJPA.stream().anyMatch(q -> q.getQuestion().equals("a")));
        assertTrue(quizJPA.stream().anyMatch(q -> q.getQuestion().equals("b")));

        TypedQuery<Quiz> queryJEE = em.createQuery(
                "select q from Quiz q where q.subCategory.parent.name='JEE'",Quiz.class);
        List<Quiz> all = queryJEE.getResultList();
        assertEquals(4, all.size());
    }
}
