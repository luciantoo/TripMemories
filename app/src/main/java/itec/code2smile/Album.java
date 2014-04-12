package itec.code2smile;

import java.util.ArrayList;

/**
 * Created by Sined on 4/12/2014.
 */
public class Album {

    private int id;

    private String name;

    private ArrayList<String> strings;

    public Album(int id,String name, ArrayList<String> strings){
        this.id=id;
        this.name=name;
        this.strings=strings;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }

    public void setStrings(ArrayList<String> strings){
        this.strings=strings;
    }

    public ArrayList<String> getStrings(){
        return strings;
    }

    public void setId(int id){
        this.id=id;
    }

    public int getId(){
        return id;
    }
}
