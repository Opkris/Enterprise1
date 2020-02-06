# entity.Quiz Game - Part 02

Continue to work on the Maven project started as exercise 
in the first lesson.


Extend the 3 existing entity classes:

* *entity.Category*: should have 0 or more subcategories
* *entity.SubCategory*: should have a single parent category
* *entity.Quiz*: should have a single subcategory it belongs to.
          Do NOT add a link back from subcategory to its quizzes
          

In the `entity.QuizEntityTest` class, add the following tests:

* `testQuizWithSubcategory`: create one category with one subcategory.
   Then create a quiz for that subcategory.
   Verify that you can persist these 3 entities into the H2 database.

* `testQueries`: create 1 category (name *JEE*) with 3 subcategories
   (*JPA*, *EJB* and *JSF*).
   Then create 4 different quizzes: 2 in the same subcategory (*JPA*), and
   1 each for the remaining 2 categories (*EJB* and *JSF*).
   Note: the actual content of the quizzes does not matter.
   In the test, create a `TypedQuery` in which, using JPQL, you retrieve all the quizzes
   belonging to the subcategory *JPA*.
   Verify you get only 2 quizzes.
   Create a second `TypedQuery` in which, using JPQL, you retrieve all
   the quizzes whose subcategory is under the *JEE* category.
   Verify you get all the 4 quizzes.
   
   
As you now have 3 different tests in `entity.QuizEntityTest`, to avoid repeating
writing the same code, make sure that the creation/shutdown of 
the `EntityManager` is done in `@BeforeEach`/`@AfterEach` methods.   

Solutions to this exercise can be found in the 
`intro/exercise-solutions/quiz-game/part-02` module.            