package script.theme_park.dungeon.kashyyyk_the_arena;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.trial;
import script.library.space_dungeon;

public class arena_controller extends script.base_script
{
    public arena_controller()
    {
    }
    public static final String ARENA_CHAMPION = "object/mobile/dressed_arena_champion.iff";
    public int beginSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        clearArena(self);
        setupArenaChampion(self);
        return SCRIPT_CONTINUE;
    }
    public void setupArenaChampion(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "arena_champion"))
        {
            obj_id arenaChampion = getObjIdObjVar(self, "arena_champion");
            if (isIdValid(arenaChampion))
            {
                destroyObject(arenaChampion);
            }
        }
        obj_id creature = create.object(ARENA_CHAMPION, getLocation(self));
        if (isIdValid(creature))
        {
            setObjVar(self, "arena_champion", creature);
            attachScript(creature, "conversation.ep3_forest_wirartu_arena");
            setObjVar(creature, "controller_object", self);
            setYaw(creature, 171);
        }
    }
    public void clearArena(obj_id self) throws InterruptedException
    {
        obj_id[] creatureList = getObjectsInRange(self, 150);
        if (creatureList != null && creatureList.length > 0)
        {
            for (int i = 0; i < creatureList.length; i++)
            {
                if (isMob(creatureList[i]) && !hasScript(creatureList[i], "conversation.ep3_forest_arena_guard_interior") && !hasScript(creatureList[i], "conversation.ep3_forest_wirartu_arena"))
                {
                    trial.cleanupNpc(creatureList[i]);
                }
                else if (isPlayer(creatureList[i]))
                {
                    space_dungeon.ejectPlayerFromDungeon(creatureList[i]);
                }
            }
        }
    }
}
