package fr.unice.polytech.qgl.qdd;

import org.json.JSONObject;

/**
 * Created by danial on 11/12/15.
 */
public class ActionBuilder {
    private JSONObject actionMessage = new JSONObject();

    public ActionBuilder action (String actionString) {
        JSONObject action = new JSONObject();
        actionMessage.put("action", actionString);
        return this;
    }

    public ActionBuilder echo() {
        return action("echo");
    }

    public ActionBuilder heading() {
        return action("heading");
    }

    public ActionBuilder direction(String direction) {
        param("direction", direction);
        return this;
    }

    public ActionBuilder param(String key, String value) {
        if(!actionMessage.has("parameters")) {
            JSONObject parameters = new JSONObject();
            parameters.put(key, value);
            actionMessage.put("parameters", parameters);
        }

        else {
            actionMessage.getJSONObject("parameters").append(key, value);
        }

        return this;
    }

    public String toJSON(){
        return actionMessage.toString();
    }

    public class Heading extends ActionBuilder{
        public Heading direction(String direction) {
            param("direction", direction);
            return this;
        }
    }
}
