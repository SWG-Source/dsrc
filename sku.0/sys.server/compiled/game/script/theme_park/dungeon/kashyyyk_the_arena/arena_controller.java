package script.theme_park.dungeon.kashyyyk_the_arena;

import script.dictionary;
import script.library.create;
import script.library.space_dungeon;
import script.library.trial;
import script.obj_id;

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
            for (obj_id obj_id : creatureList) {
                if (isMob(obj_id) && !hasScript(obj_id, "conversation.ep3_forest_arena_guard_interior") && !hasScript(obj_id, "conversation.ep3_forest_wirartu_arena")) {
                    trial.cleanupNpc(obj_id);
                } else if (isPlayer(obj_id)) {
                    space_dungeon.ejectPlayerFromDungeon(obj_id);
                }
            }
        }
    }
}
