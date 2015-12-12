package fr.unice.polytech.qgl.qdd;

/**
 * Created by danial on 20/11/15.
 */


public class Navigator {

    private String front;

    public Navigator(String facing){
        this.front = facing;
    }

    public String back(){
        return Direction.valueOf(front).back;
    }

    public String right(){
        return Direction.valueOf(front).right;
    }

    public String left(){
        return Direction.valueOf(front).left;
    }

    private enum Direction {
        N("S","W","E"),
        S("N","E","W"),
        W("E","S","N"),
        E("W","N","S"),;
        public String back;
        public String left;
        public String right;

        Direction(String back, String left, String right){
            this.back = back;
            this.left = left;
            this.right = right;
        }
    }

    public String front(){
        return front;
    }

    public void setFront(String front)
    {
        this.front = front;
    }
}

