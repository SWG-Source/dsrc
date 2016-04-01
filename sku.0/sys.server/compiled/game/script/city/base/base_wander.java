package script.city.base;

import script.dictionary;
import script.library.ai_lib;
import script.location;
import script.obj_id;

public class base_wander extends script.base_script
{
	public base_wander()
	{
	}
	public String[] patrolPoints =
			{
					"city1",
					"city2",
					"city3",
					"city4",
					"city5",
					"city6"
			};
	public int OnAttach(obj_id self) throws InterruptedException
	{
		ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
		messageTo(self, "pathRandom", null, 1, false);
		return SCRIPT_CONTINUE;
	}
	public int OnInitialize(obj_id self) throws InterruptedException
	{
		messageTo(self, "pathRandom", null, 1, false);
		return SCRIPT_CONTINUE;
	}
	public int pathRandom(obj_id self, dictionary params) throws InterruptedException
	{
		ai_lib.setPatrolRandomNamedPath(self, patrolPoints);
		return SCRIPT_CONTINUE;
	}
	public int OnLoiterMoving(obj_id self) throws InterruptedException
	{
		stop(self);
		messageTo(self, "pathRandom", null, 2, false);
		return SCRIPT_OVERRIDE;
	}
	public int OnMovePathComplete(obj_id self) throws InterruptedException
	{
		messageTo(self, "pathRandom", null, rand(30, 60), false);
		return SCRIPT_CONTINUE;
	}
	public int OnMovePathNotFound(obj_id self) throws InterruptedException
	{
		if (hasObjVar(self, "checkingFeet"))
		{
			if (getIntObjVar(self, "checkingFeet") > 10)
			{
				return SCRIPT_CONTINUE;
			}
		}
		messageTo(self, "pathRandom", null, rand(30, 60), false);
		checkForFeetGluedToFloor(self);
		return SCRIPT_CONTINUE;
	}
	public void checkForFeetGluedToFloor(obj_id self) throws InterruptedException
	{
		if (!hasObjVar(self, "checkingFeet"))
		{
			setObjVar(self, "checkingFeet", 1);
			setObjVar(self, "mightBeStuck", getLocation(self));
			messageTo(self, "amIStuck", null, 180, false);
		}
		else
		{
			int checked = getIntObjVar(self, "checkingFeet") + 1;
			setObjVar(self, "checkingFeet", checked);
			if (checked > 10)
			{
				return;
			}
			setObjVar(self, "mightBeStuck", getLocation(self));
			messageTo(self, "amIStuck", null, 180, false);
		}
	}
	public int amIStuck(obj_id self, dictionary params) throws InterruptedException
	{
		location now = getLocation(self);
		if (now == null || now.equals(getLocationObjVar(self, "mightBeStuck")))
		{
			messageTo(self, "killIt", null, 3, false);
		}
		else
		{
			messageTo(self, "cleanUpChecks", null, 10, false);
		}
		return SCRIPT_CONTINUE;
	}
	public int killIt(obj_id self, dictionary params) throws InterruptedException
	{
		destroyObject(self);
		return SCRIPT_CONTINUE;
	}
	public int cleanUpChecks(obj_id self, dictionary params) throws InterruptedException
	{
		int checks = getIntObjVar(self, "checkingFeet");
		if (checks > 0)
		{
			setObjVar(self, "checkingFeet", --checks);
		}
		return SCRIPT_CONTINUE;
	}
}
