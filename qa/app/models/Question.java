package models;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class Question extends Model {
 
    public String title;
    public Date postedAt;
    
    @Lob
    public String content;
    
    @ManyToOne
    public User author;
    
    public Question(User author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.postedAt = new Date();
    }
 
}
