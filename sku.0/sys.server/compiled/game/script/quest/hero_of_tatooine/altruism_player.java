package script.quest.hero_of_tatooine;

import script.dictionary;
import script.library.badge;
import script.obj_id;

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
