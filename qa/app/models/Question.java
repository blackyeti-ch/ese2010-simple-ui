package models;
 
import java.util.*;
import java.util.Map.Entry;

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
    
//  @ManyToMany
//	private Map<User,Vote> votes;

    
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
/*    
    public void vote (Vote vote, User user) {
    	votes.put(user,vote);
    	this.save();
    	}
    
	public int getRating() {
		int rating = 0;
		if (votes != null){
			for (Entry<User, Vote> entry : votes.entrySet()){
				rating = (entry.getValue() == Vote.UP) ? rating + 1 : rating - 1;
			}
		}
		return rating;
	}
*/
}
