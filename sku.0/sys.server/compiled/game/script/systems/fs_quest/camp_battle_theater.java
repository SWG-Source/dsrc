package script.systems.fs_quest;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.quests;

public class camp_battle_theater extends script.base_script
{
    public camp_battle_theater()
    {
    }
    public int OnTheaterCreated(obj_id self, obj_id[] objects, obj_id player, obj_id creator) throws InterruptedException
    {
        location here = getLocation(self);
        location spawn = new location(here.x + 5, here.y, here.z + 3);
        obj_id pawn = create.createCreature("shadow_punk", spawn, true);
        if (isIdValid(pawn))
        {
            attachScript(pawn, "quest.force_sensitive.fs_theater_enemy");
        }
        spawn = new location(here.x + 2, here.y, here.z + 7);
        pawn = create.createCreature("shadow_punk", here, true);
        if (isIdValid(pawn))
        {
            attachScript(pawn, "quest.force_sensitive.fs_theater_enemy");
        }
        obj_id boss = create.createCreature("shadow_outlaw", here, true);
        if (isIdValid(boss))
        {
            attachScript(boss, "quest.force_sensitive.fs_theater_enemy");
            setObjVar(boss, "isBoss", 1);
            setObjVar(boss, "quest.owner", player);
        }
        quests.activate("loot_datapad_2", player, null);
        return SCRIPT_CONTINUE;
    }
}
