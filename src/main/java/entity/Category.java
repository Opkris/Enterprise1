package entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {


    @Id // Create unique number for each object
    @GeneratedValue// generate the id automatically for uss
    private Long id;

    @Column(unique = true) // ensure that the name is unique throw an exception when you create a duplicate
    @NotBlank // Cant have a name with just "Spaces" eks : name = "     ";
    @Size(max=128) // The name can have a name above 128 bit
    private String name;

    /**Cascade**/
    /*
        Entity relationships often depend on the existence of another entity - for example, the entity.Category-SubCategories relationship.
        Without the entity.Category, the entity.SubCategory entity doesn't have any meaning of its own. when we delete the entity.Category entity.
        our entity.SubCategory should also get deleted.
        Cascading is a way to achieve this. When we perform some action on the target entity, the same action will be applied to the
        associated entity

        Cascade.ALL propagates all operations - including Hibernate-specific ones - from a parent to child entity.
    */
        @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<SubCategory> subCategories;



     /****************************************************************************************
     *****************************************************************************************
     ****************************************************************************************/

    public Category() {
        subCategories = new ArrayList<>();
    }

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
