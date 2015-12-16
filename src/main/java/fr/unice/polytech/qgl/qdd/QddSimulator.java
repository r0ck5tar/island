package fr.unice.polytech.qgl.qdd;

import fr.unice.polytech.qgl.qdd.ai.ExplorerAI;
import fr.unice.polytech.qgl.qdd.enums.Resource;
import org.json.JSONObject;

/**
 * Created by danial on 04/12/15.
 */
public class QddSimulator {
    private QddExplorer explorer;
    private ExplorerAI explorerAI;
    private int phase;
    Action action;
    int actionCounter;

    public QddSimulator(String context) {
        explorer = new QddExplorer(context);
        explorerAI = new ExplorerAI(explorer);
        ExplorerLogger.init(explorer, this);
        actionCounter = 0;
        action = null;
        phase = 1;
    }

    public String nextDecision() {
        actionCounter++;
        if(phase == 1) {
            action = explorerAI.computeAerialStrategy();
            return action.toJSON();
        }
        else {
            action = explorerAI.computeTerrestrialStrategy();
            return action.toJSON();
        }
    }

    public void analyseAnswer(JSONObject result) {
        if (result.getString("status").equals("OK"));{
            explorer.decreaseBudget(result.getInt("cost"));

            if(phase == 1) {
                switch (action.getAction()) {
                    case Action.FLY: explorer.fly(); break;
                    case Action.HEADING: explorer.turn(action.getStringParam("direction")); break;
                    case Action.LAND: explorer.land(action.getStringParam("creek"), action.getIntParam("people"));
                        phase = 2; break;
                    case Action.ECHO: explorer.echo(action.getStringParam("direction"), result); break;
                    case Action.SCAN: explorer.scan(result); break;
                }
            }

            else {
                switch (action.getAction()) {
                    case Action.LAND: explorer.land(action.getStringParam("creek"), action.getIntParam("people")); break;
                    case Action.MOVE_TO: explorer.move(action.getStringParam("direction")); break;
                    case Action.EXPLORE: explorer.explore(result.getJSONObject("extras").getJSONArray("resources")); break;
                    case Action.EXPLOIT: explorer.exploit(Resource.valueOf(action.getStringParam("resource")),
                            result.getJSONObject("extras").getInt("amount"));
                }
            }

            explorerAI.logExplorer(action);
        }
    }
}
