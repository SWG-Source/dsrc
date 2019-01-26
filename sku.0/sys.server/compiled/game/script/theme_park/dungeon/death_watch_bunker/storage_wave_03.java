package script.theme_park.dungeon.death_watch_bunker;

import script.dictionary;
import script.library.ai_lib;
import script.location;
import script.obj_id;
import script.string_id;

public class storage_wave_03 extends script.base_script
{
    public storage_wave_03()
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
    public int handleInvestigate(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        obj_id top = getTopMostContainer(self);
        obj_id room = getCellId(top, "observationroom17");
        location here = getLocation(self);
        String planet = here.area;
        location attack = new location(-22.1f, 0, -85.2f, planet, room);
        ai_lib.aiPathTo(self, attack);
        addLocationTarget("checkPoint", attack, 1);
        return SCRIPT_CONTINUE;
    }
}
