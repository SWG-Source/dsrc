package script.theme_park.heroic.axkva_min;

import script.dictionary;
import script.library.ai_lib;
import script.library.trial;
import script.obj_id;

public class nandina extends script.base_script
{
    public nandina()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "setGorvoAsPet", null, 1.0f, false);
        trial.setHp(self, trial.HP_AKXVA_NANDINA);
        return SCRIPT_CONTINUE;
    }
    public int setGorvoAsPet(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] spawn_id = trial.getObjectsInDungeonWithObjVar(trial.getTop(self), "spawn_id");
        if (spawn_id == null || spawn_id.length == 0)
        {
            debugSpeakMsg(self, "found no spawn_id");
            return SCRIPT_CONTINUE;
        }
        obj_id gorvo = null;
        for (obj_id obj_id : spawn_id) {
            if ((getStringObjVar(obj_id, "spawn_id")).equals("gorvo")) {
                gorvo = obj_id;
            }
        }
        setMaster(gorvo, self);
        obj_id[] allies = new obj_id[2];
        allies[0] = gorvo;
        allies[1] = self;
        ai_lib.establishAgroLink(self, allies);
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        obj_id[] players = trial.getPlayersInDungeon(trial.getTop(self));
        if (players == null || players.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (obj_id player : players) {
            addHate(self, player, 1);
        }
        return SCRIPT_CONTINUE;
    }
}
