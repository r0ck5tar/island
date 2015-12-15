package fr.unice.polytech.qgl.qdd;

import org.json.JSONObject;

/**
 * Created by danial on 11/12/15.
 */
public class Action {
    public static final String
            //Phase 1
            HEADING = "heading", FLY = "fly", ECHO = "echo", SCAN = "scan",
            //Phase 1 & 2
            STOP = "stop", LAND = "land",
            //Phase 2
            MOVE_TO = "move_to", SCOUT = "scout", GLIMPSE = "glimpse", EXPLORE = "explore", EXPLOIT = "exploit", TRANSFORM = "transform";

    private String action;
    private JSONObject parameters;

    public Action(String action) {
        this.action = action;
        parameters = new JSONObject();
    }

    public String getAction() {
        return action;
    }

    public String getStringParam(String param) {
        return parameters.getString(param);
    }

    public int getIntParam(String param) {
        return parameters.getInt(param);
    }

    public Action addParameter(String key, String value){
        parameters.put(key, value);
        return this;
    }

    public String toJSON() {
        JSONObject action = new JSONObject();
        action.put("action", this.action);
        if(parameters.length() != 0) {
            action.put("parameters", this.parameters);
        }

        return action.toString();
    }
}
