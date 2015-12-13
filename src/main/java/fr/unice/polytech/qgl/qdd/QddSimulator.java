package fr.unice.polytech.qgl.qdd;

import fr.unice.polytech.qgl.qdd.ai.ExplorerAI;
import fr.unice.polytech.qgl.qdd.enums.ResourceEnum;
import org.json.JSONObject;

/**
 * Created by danial on 04/12/15.
 */
public class QddSimulator {
    private QddExplorer explorer;
    private ExplorerAI explorerAI;
    private Action action;
    private int phase;

    public QddSimulator(String context) {
        explorer = new QddExplorer(context);
        explorerAI = new ExplorerAI(explorer);
        action = null;
        phase = 1;
    }

    public String nextDecision() {
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
                    case FLY: explorer.fly(); break;
                    case HEADING: explorer.turn(action.getStringParam("direction")); break;
                    case LAND: explorer.land(action.getStringParam("creek"), action.getIntParam("people"));
                        phase = 2; break;
                    case ECHO: explorer.echo(action.getStringParam("direction"), result); break;
                    case SCAN: explorer.scan(result); break;
                }
            }

            else {
                switch (action.getAction()) {
                    case LAND: explorer.land(action.getStringParam("creek"), action.getIntParam("people")); break;
                    case MOVE_TO: explorer.move(action.getStringParam("direction")); break;
                    case EXPLORE: explorer.explore(result.getJSONObject("extras").getJSONArray("resources")); break;
                    case EXPLOIT: explorer.exploit(ResourceEnum.valueOf(action.getStringParam("resource")),
                            result.getJSONObject("extras").getInt("amount"));
                }
            }

            explorerAI.logExplorer(action);
        }
    }
}
