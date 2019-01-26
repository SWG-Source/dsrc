package script.theme_park.heroic.axkva_min;

import script.dictionary;
import script.library.trial;
import script.location;
import script.obj_id;

public class warden extends script.base_script
{
    public warden()
    {
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        stopClientEffectObjByLabel(trial.getPlayersInDungeon(trial.getTop(self)), self, "burn");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        detachScript(self, "ai.ai");
        detachScript(self, "ai.creature_combat");
        attachScript(self, "player.yavin_e3");
        obj_id[] players = trial.getPlayersInDungeon(trial.getTop(self));
        setCreatureCoverVisibility(self, false);
        if (hasObjVar(self, "doBurn"))
        {
            messageTo(self, "start_burn", null, 2.0f, false);
        }
        if (players == null || players.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (obj_id player : players) {
            addPassiveReveal(self, player, 1);
        }
        return SCRIPT_CONTINUE;
    }
    public int start_burn(obj_id self, dictionary params) throws InterruptedException
    {
        playClientEffectObj(self, "appearance/pt_green_fire_base.prt", self, "", null, "burn");
        messageTo(self, "burn", null, 0.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int burn(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] players = trial.getValidTargetsInRadius(self, 5.0f);
        if (players == null || players.length == 0)
        {
            messageTo(self, "burn", null, 1.0f, false);
            return SCRIPT_CONTINUE;
        }
        for (obj_id player : players) {
            location loc = getLocation(player);
            String locationData = "" + loc.x + " " + loc.y + " " + loc.z + " " + loc.cell + " " + loc.x + " " + loc.y + " " + loc.z;
            queueCommand(self, (-1450748792), player, locationData, COMMAND_PRIORITY_DEFAULT);
        }
        messageTo(self, "burn", null, 1.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int fall_down(obj_id self, dictionary params) throws InterruptedException
    {
        setPosture(self, POSTURE_KNOCKED_DOWN);
        return SCRIPT_CONTINUE;
    }
}
