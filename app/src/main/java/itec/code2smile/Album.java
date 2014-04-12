package itec.code2smile;

import java.util.ArrayList;

/**
 * Created by Sined on 4/12/2014.
 */
public class Album {

    private int id;

    private String name;

    private ArrayList<String> photoNames;

    public Album(int id,String name, ArrayList<String> strings){
        this.id = id;
        this.name = name;
        this.photoNames = strings;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setPhotoNames(ArrayList<String> photoNames){
        this.photoNames = photoNames;
    }

    public ArrayList<String> getPhotoNames(){
        return photoNames;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }
}
