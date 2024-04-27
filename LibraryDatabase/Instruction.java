package LibraryDatabase;

import java.io.Serializable;

public class Instruction implements Serializable {
    public String instruction;
    public LibraryItem libraryItem;
    public LibraryUsers thisUser;

    public Instruction(){}

    public Instruction(String instruction, LibraryItem libraryItem, LibraryUsers thisUser){
        this.instruction = instruction;
        this.libraryItem = libraryItem;
        this.thisUser = thisUser;
    }

    public String getInstruction(){
        return this.instruction;
    }
    public LibraryItem getLibraryItem(){
        return this.libraryItem;
    }
    public LibraryUsers getLibraryUser(){
        return this.thisUser;
    }
    public void setInstruction(String instruction){
        this.instruction = instruction;
    }

    @Override
    public String toString(){
        return this.getInstruction();
    }
}
