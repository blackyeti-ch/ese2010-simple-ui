package models;
 
import java.util.*;
import java.util.Map.Entry;

import javax.persistence.*;
 
import play.db.jpa.*;
import play.data.validation.*;
 
@Entity
public class Answer extends Model {
 
    @Required
    public String author;
    
    @Required
    public Date postedAt;
     
    @Lob
    @Required
    @MaxSize(10000)
    public String content;
    
    @ManyToOne
    @Required
    public Question question;
    
//  @ManyToMany
//	private Map<User,Vote> votes;
    
    public Answer(Question question, String author, String content) {
        this.question = question;
        this.author = author;
        this.content = content;
        this.postedAt = new Date();
    }
    
    public String toString() {
        return author + " (" + postedAt + ")";
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
