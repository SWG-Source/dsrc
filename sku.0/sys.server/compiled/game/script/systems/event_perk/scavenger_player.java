package script.systems.event_perk;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class scavenger_player extends script.base_script
{
    public scavenger_player()
    {
    }
    public static final float TIMELIMIT = 60 * 60 * 8;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        float rightNow = getGameTime();
        setObjVar(self, "event_perk.scavenger.startTime", rightNow);
        setObjVar(self, "event_perk.scavenger.my_score", 0);
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        checkTimeLimit(self);
        return SCRIPT_CONTINUE;
    }
    public int checkForPossibleScore(obj_id self, dictionary params) throws InterruptedException
    {
        int myScore = getIntObjVar(self, "event_perk.scavenger.my_score");
        int itemIndex = params.getInt("itemIndex");
        int[] itemsFound = getIntArrayObjVar(self, "event_perk.scavenger.items_found");
        obj_id droid = params.getObjId("droid");
        if (itemsFound[itemIndex] == 0)
        {
            itemsFound[itemIndex] = 1;
            myScore++;
            setObjVar(self, "event_perk.scavenger.my_score", myScore);
            setObjVar(self, "event_perk.scavenger.items_found", itemsFound);
            params.put("player", self);
            messageTo(droid, "updateMasterScoreCard", params, 1, false);
            return SCRIPT_CONTINUE;
        }
        if (itemsFound[itemIndex] == 1)
        {
            sendSystemMessage(self, new string_id("event_perk", "scavenger_already_turned_in"));
        }
        return SCRIPT_CONTINUE;
    }
    public int handleScavengerCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "event_perk.scavenger"))
        {
            removeObjVar(self, "event_perk.scavenger");
        }
        if (hasScript(self, "systems.event_perk.scavenger_player"))
        {
            detachScript(self, "systems.event_perk.scavenger_player");
        }
        return SCRIPT_CONTINUE;
    }
    public void checkTimeLimit(obj_id self) throws InterruptedException
    {
        float myCreationTime = getFloatObjVar(self, "event_perk.scavenger.startTime");
        float rightNow = getGameTime();
        float delta = rightNow - myCreationTime;
        String myName = getName(self);
        if (delta > TIMELIMIT)
        {
            messageTo(self, "handleScavengerCleanup", null, 1, false);
        }
        return;
    }
}
