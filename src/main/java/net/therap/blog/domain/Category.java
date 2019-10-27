package net.therap.blog.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sajjad.ahmed
 * @since 9/5/19.
 */

@Entity
@Table(name = "category")
@NamedQueries({
        @NamedQuery(name = "Category.findAll", query = "FROM Category"),
})
public class Category extends BaseDomain {

    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "post_category",
            joinColumns = {@JoinColumn(name = "category_id")},
            inverseJoinColumns = @JoinColumn(name = "post_id"))
    private List<Post> posts;

    public Category() {
        this.posts = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void checkNull() {
        super.validate(this);
    }

    @Override
    public String toString() {
        return "id=" + getId() + ", name=" + this.name;

    }
}
