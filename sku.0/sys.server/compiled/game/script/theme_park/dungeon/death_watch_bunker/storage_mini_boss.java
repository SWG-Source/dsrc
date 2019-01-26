package script.theme_park.dungeon.death_watch_bunker;

import script.dictionary;
import script.library.ai_lib;
import script.library.utils;
import script.location;
import script.obj_id;

public class storage_mini_boss extends script.base_script
{
    public storage_mini_boss()
    {
    }
    public static final String TBL_HALL_WAVE = "datatables/dungeon/death_watch/hall_wave.iff";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setName(self, "Klin Nif");
        messageTo(self, "handleAttackerCleanUp", null, 300.0f, false);
        messageTo(self, "handleInvestigate", null, 1.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int aiCorpsePrepared(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id corpseInventory = utils.getInventoryContainer(self);
        if (corpseInventory == null)
        {
            return SCRIPT_CONTINUE;
        }
        createObject("object/tangible/dungeon/death_watch_bunker/passkey_storage.iff", corpseInventory, "");
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
        location attack = new location(-20.5f, 0, -74.5f, planet, room);
        ai_lib.aiPathTo(self, attack);
        addLocationTarget("checkPoint", attack, 1);
        return SCRIPT_CONTINUE;
    }
}
