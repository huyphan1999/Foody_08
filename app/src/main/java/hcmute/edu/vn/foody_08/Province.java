package hcmute.edu.vn.foody_08;

import java.io.Serializable;

public class Province implements Serializable {
    private String Name;
    private int Id;

    public Province(String name, int id) {
        Name = name;
        Id = id;
    }

    public Province() {

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
