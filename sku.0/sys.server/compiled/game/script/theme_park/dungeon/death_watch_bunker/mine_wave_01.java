package script.theme_park.dungeon.death_watch_bunker;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.chat;
import script.library.create;

public class mine_wave_01 extends script.base_script
{
    public mine_wave_01()
    {
    }
    public static final String TBL_MINE_WAVE = "datatables/dungeon/death_watch/mine_wave.iff";
    public static final string_id CALL_BACK_UP = new string_id("dungeon/death_watch", "call_back_up");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleAttackerCleanUp", null, 300f, false);
        messageTo(self, "handleInvestigate", null, 5f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id wpn, int[] damage) throws InterruptedException
    {
        if (!hasObjVar(self, "death_watch.call_backup"))
        {
            obj_id structure = getTopMostContainer(self);
            obj_id mineTerminal = getObjIdObjVar(structure, "death_watch.mineTerminal");
            chat.chat(self, CALL_BACK_UP);
            setObjVar(self, "death_watch.call_backup", 1);
            messageTo(mineTerminal, "handleCallSupport1", null, 30f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleAttackerCleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        if (ai_lib.isInCombat(self))
        {
            messageTo(self, "handleAttackerCleanUp", null, 300f, false);
            return SCRIPT_CONTINUE;
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int handleInvestigate(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        obj_id top = getTopMostContainer(self);
        obj_id room = getCellId(top, "hall33");
        location here = getLocation(self);
        String planet = here.area;
        location attack = new location(25.5f, 0, -103.4f, planet, room);
        ai_lib.aiPathTo(self, attack);
        addLocationTarget("checkPoint", attack, 1);
        return SCRIPT_CONTINUE;
    }
}
