package models;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
import play.data.validation.*;
 
@Entity
public class Question extends Model {
 
    @Required
    public String title;
    
    @Required
    public Date postedAt;
    
    @Lob
    @Required
    @MaxSize(10000)
    public String content;
    
    @Required
    @ManyToOne
    public User author;
    
    @OneToMany(mappedBy="question", cascade=CascadeType.ALL)
    public List<Answer> answers;

    
    public Question(User author, String title, String content) { 
        this.answers = new ArrayList<Answer>();
        this.author = author;
        this.title = title;
        this.content = content;
        this.postedAt = new Date();
    }

    public Question addAnswer(String author, String content) {
        Answer newAnswer = new Answer(this, author, content).save();
        this.answers.add(newAnswer);
        this.save();
        return this;
    }
    
    public Question previous() {
        return Question.find("postedAt < ? order by postedAt desc", postedAt).first();
    }
     
    public Question next() {
        return Question.find("postedAt > ? order by postedAt asc", postedAt).first();
    }
    
    public String toString() {
        return title + " (" + postedAt + ")";
    }

}
