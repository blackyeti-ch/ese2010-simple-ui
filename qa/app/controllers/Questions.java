package controllers;
 
import play.*;
import play.mvc.*;
 
@Check("admin")
@With(Secure.class)
public class Questions extends CRUD {    
}
