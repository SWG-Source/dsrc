package script.quest.hero_of_tatooine;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.badge;

public class altruism_player extends script.base_script
{
    public altruism_player()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "quest.hero_of_tatooine.altruism") && !badge.hasBadge(self, "poi_factoryliberation"))
        {
            removeObjVar(self, "quest.hero_of_tatooine.altruism");
        }
        detachScript(self, "quest.hero_of_tatooine.altruism_player");
        return SCRIPT_CONTINUE;
    }
    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        sendFailMessage(self);
        return SCRIPT_CONTINUE;
    }
    public int OnRemovingFromWorld(obj_id self) throws InterruptedException
    {
        sendFailMessage(self);
        return SCRIPT_CONTINUE;
    }
    public void sendFailMessage(obj_id self) throws InterruptedException
    {
        obj_id control = getTopMostContainer(self);
        dictionary d = new dictionary();
        d.put("player", self);
        messageTo(control, "handleQuestFail", d, 0.0f, false);
    }
}
