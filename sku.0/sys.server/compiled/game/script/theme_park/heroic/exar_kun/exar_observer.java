package script.theme_park.heroic.exar_kun;

import script.dictionary;
import script.library.ai_lib;
import script.library.factions;
import script.library.trial;
import script.obj_id;

import java.util.Vector;

public class exar_observer extends script.base_script
{
    public exar_observer()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCreatureCoverVisibility(self, false);
        setInvulnerable(self, true);
        factions.setIgnorePlayer(self);
        detachScript(self, "ai.creature_combat");
        messageTo(self, "hate_link", null, 1.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int start_observation(obj_id self, dictionary params) throws InterruptedException
    {
        trial.bumpSession(self, "reset");
        messageTo(self, "observe_loop", trial.getSessionDict(self, "reset"), 1.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int observe_loop(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params, "reset"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] players = trial.getValidTargetsInCell(trial.getTop(self), "r7");
        if (players == null || players.length == 0)
        {
            dictionary dict = trial.getSessionDict(trial.getTop(self));
            dict.put("triggerType", "triggerId");
            dict.put("triggerName", "exar_reset");
            messageTo(trial.getTop(self), "triggerFired", dict, 0.0f, false);
            return SCRIPT_CONTINUE;
        }
        for (obj_id player : players) {
            startCombat(self, player);
            addHate(self, player, 1.0f);
        }
        messageTo(self, "observe_loop", trial.getSessionDict(self, "reset"), 8.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int hate_link(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] allobj = trial.getObjectsInCellWithObjVar(trial.getTop(self), "r7", "spawn_id");
        Vector the_team = new Vector();
        the_team.setSize(0);
        for (obj_id obj_id : allobj) {
            String spawn_id = getStringObjVar(obj_id, "spawn_id");
            if (spawn_id.equals("harmony") || spawn_id.equals("chaos") || spawn_id.equals("veng") || spawn_id.equals("wrath")) {
                the_team.add(obj_id);
            }
        }
        the_team.add(self);
        if (the_team != null)
        {
            allobj = new obj_id[the_team.size()];
            the_team.toArray(allobj);

        }
        ai_lib.establishAgroLink(self, allobj);
        return SCRIPT_CONTINUE;
    }
}
