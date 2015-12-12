package fr.unice.polytech.qgl.qdd;

import org.json.*;
import eu.ace_design.island.bot.*;

/**
 * @author : Ulysse
 */

// A minimal bot, with minimal documentation
public class Explorer implements IExplorerRaid 
{
	private QddSimulator qdd;

	public void initialize(String context) 
	{
		qdd = new QddSimulator(context);
	}

	public String takeDecision() 
	{
		return qdd.nextDecision();
	}


	public void acknowledgeResults( String result )
	{
		JSONObject resultMessage = new JSONObject(result);
		qdd.analyseAnswer(resultMessage);
	}
}
