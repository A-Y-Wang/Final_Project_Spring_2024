package LibraryDatabase;
import org.bson.types.ObjectId;

import java.io.Serializable;

//gonna implement rating will figure out later

public class LibraryItem implements Serializable {

    //public String ID;
    public String type; //Book, AudioBook, Graphic Novel, Movie
    public String title;
    public String author;
    public String illustrator;
    //public LibraryUsers libusa;
    public int amount;
    //private double rating;

    public LibraryItem(){}

    public LibraryItem(String typee, String tit, String aut, String ill, int num){
        //this.ID = id;
        this.type= typee;
        this.title = tit;
        this.author = aut;
        this.illustrator = ill;
        //this.libusa = user;
        this.amount = num;

    }
    public String getType(){
        return this.type;
    }

    public String getTitle(){
        return this.title;
    }

    public String getAuthor(){
        return this.author;
    }

    public String getIllustrator(){
        return this.illustrator;
    }

    public void setAmount(int number){
        this.amount = number;
    }

    public int getAmount(){
        return this.amount;
    }

//    public void setUser(LibraryUsers liber){
//        this.libusa = liber;
//    }

    @Override
    public String toString(){
        return "Media: "+ this.type +" | Title: "+ this.title +" | Author: "+ this.author +" | Illustrator: "+ this.illustrator;
    }

}
