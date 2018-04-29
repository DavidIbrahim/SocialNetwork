package Rameez;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SuggestedFriend {
    private String name;
    private Integer probability;

    public SuggestedFriend( String name , Integer probability) {
        this.name = name;
        this.probability = probability;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProbability() {
        return probability;
    }

    public void setProbability(Integer probability) {
        this.probability = probability;
    }


}


