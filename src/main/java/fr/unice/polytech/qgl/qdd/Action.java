package fr.unice.polytech.qgl.qdd;

import fr.unice.polytech.qgl.qdd.enums.ActionEnum;
import org.json.JSONObject;

/**
 * Created by danial on 11/12/15.
 */
public class Action {
    private ActionEnum action;
    private JSONObject parameters;

    public Action(ActionEnum action) {
        this.action = action;
        parameters = new JSONObject();
    }

    public ActionEnum getAction() {
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
        action.put("action", this.action.getAction());
        if(parameters.length() != 0) {
            action.put("parameters", this.parameters);
        }

        return action.toString();
    }
}
