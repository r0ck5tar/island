package fr.unice.polytech.qgl.qdd.navigation;

import fr.unice.polytech.qgl.qdd.enums.ResourceEnum;

/**
 * Created by danial on 10/12/15.
 */
public class Resource {
    private int amount;
    private ResourceEnum type;

    public Resource(){

    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


}
