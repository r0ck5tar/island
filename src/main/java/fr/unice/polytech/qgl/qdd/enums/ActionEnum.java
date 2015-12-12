package fr.unice.polytech.qgl.qdd.enums;

/**
 * Created by danial on 11/12/15.
 */
public enum ActionEnum {
    //Phase 1
    HEADING("heading"), FLY("fly"), ECHO("echo"), SCAN("scan"),

    //Phase 1 & 2
    STOP("stop"), LAND("land"),

    //Phase 2
    MOVE_TO("move_to"), SCOUT("scout"), GLIMPSE("glimpse"), EXPLORE("explore"), EXPLOIT("exploit"), TRANSFORM("transform");

    private String action;

    ActionEnum(String action) {
        this.action = action;
    }

    public String getAction() {
        return  action;
    }
}
