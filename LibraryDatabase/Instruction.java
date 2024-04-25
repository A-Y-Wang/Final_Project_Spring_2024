package LibraryDatabase;

import java.io.Serializable;

public class Instruction implements Serializable {
    private String instruction;
    private LibraryItem libraryItem;
    private LibraryUsers thisUser;

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

    @Override
    public String toString(){
        return this.getInstruction();
    }
}
