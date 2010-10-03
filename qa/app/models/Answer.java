package models;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class Answer extends Model {
 
    public String author;
    public Date postedAt;
     
    @Lob
    public String content;
    
    @ManyToOne
    public Question question;
    
    public Answer(Question question, String author, String content) {
        this.question = question;
        this.author = author;
        this.content = content;
        this.postedAt = new Date();
    }
 
}
