package symbolTable;

import java.util.Hashtable;

public class Class extends Module{

    private Unit currentUnit;
    public Class(String name){
        this.name = name;
    }

    public void setCurrentUnit(Unit unit){
        this.currentUnit = unit;
    }

    public Unit getCurrentUnit(){
        return currentUnit;
    }

}
