package script.theme_park.dungeon.death_watch_bunker;

import script.dictionary;
import script.library.ai_lib;
import script.library.chat;
import script.location;
import script.obj_id;
import script.string_id;

public class storage_wave_02 extends script.base_script
{
    public storage_wave_02()
    {
    }
    public static final String TBL_STORAGE_WAVE = "datatables/dungeon/death_watch/storage_wave.iff";
    public static final string_id CALL_BACK_UP = new string_id("dungeon/death_watch", "call_back_up");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleAttackerCleanUp", null, 300.0f, false);
        messageTo(self, "handleInvestigate", null, 5.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleAttackerCleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        if (ai_lib.isInCombat(self))
        {
            messageTo(self, "handleAttackerCleanUp", null, 300.0f, false);
            return SCRIPT_CONTINUE;
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id wpn, int[] damage) throws InterruptedException
    {
        if (!hasObjVar(self, "death_watch.call_backup"))
        {
            obj_id structure = getTopMostContainer(self);
            obj_id storageTerminal = getObjIdObjVar(structure, "death_watch.storageTerminal");
            chat.chat(self, CALL_BACK_UP);
            setObjVar(self, "death_watch.call_backup", 1);
            messageTo(storageTerminal, "handleCallSupport2", null, 30.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleInvestigate(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        obj_id top = getTopMostContainer(self);
        obj_id room = getCellId(top, "observationroom17");
        location here = getLocation(self);
        String planet = here.area;
        location attack = new location(-20.5f, 0, -74.5f, planet, room);
        ai_lib.aiPathTo(self, attack);
        addLocationTarget("checkPoint", attack, 1);
        return SCRIPT_CONTINUE;
    }
}
