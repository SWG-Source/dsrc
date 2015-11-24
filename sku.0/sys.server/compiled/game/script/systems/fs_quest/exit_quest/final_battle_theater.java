package script.systems.fs_quest.exit_quest;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.theater;
import script.library.chat;
import script.library.quests;
import script.library.utils;

public class final_battle_theater extends script.base_script
{
    public final_battle_theater()
    {
    }
    public int OnTheaterCreated(obj_id self, obj_id[] objects, obj_id player, obj_id creator) throws InterruptedException
    {
        setObjVar(self, "fs_quest.final_battle.player", player);
        if (objects != null)
        {
            Vector red_stones = new Vector();
            red_stones.setSize(0);
            Vector green_stones = new Vector();
            green_stones.setSize(0);
            for (int i = 0; i < objects.length; i++)
            {
                setObjVar(objects[i], theater.VAR_PARENT, self);
                setObjVar(objects[i], "questOwner", player);
                if (hasScript(objects[i], "systems.fs_quest.exit_quest.power_shrine"))
                {
                    if (hasObjVar(objects[i], "fs_quest.red_shrine"))
                    {
                        red_stones.add(objects[i]);
                    }
                    else 
                    {
                        green_stones.add(objects[i]);
                    }
                }
            }
            if (red_stones.size() > 0)
            {
                setObjVar(self, "fs_quest.final_battle.red_stones", red_stones);
            }
            if (green_stones.size() > 0)
            {
                setObjVar(self, "fs_quest.final_battle.green_stones", green_stones);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int msgQuestGreenStonesDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("quest", "final_battle_theater.msgQuestGreenStonesDestroyed -- " + self);
        quests.createSpawner("thug_help", getLocation(self), "datatables/quest/force_sensitive/final_battle_spawn.iff", self);
        return SCRIPT_CONTINUE;
    }
    public int msgQuestRedStonesDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("quest", "final_battle_theater.msgQuestRedStonesDestroyed -- " + self);
        quests.createSpawner("merc_help", getLocation(self), "datatables/quest/force_sensitive/final_battle_spawn.iff", self);
        quests.createSpawner("thug_help", getLocation(self), "datatables/quest/force_sensitive/final_battle_spawn.iff", self);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id leader = getObjIdObjVar(self, "fs_quest.final_battle.leader");
        if ((isIdValid(leader)) && (exists(leader)))
        {
            destroyObject(leader);
        }
        obj_id second = getObjIdObjVar(self, "fs_quest.final_battle.second");
        if ((isIdValid(second)) && (exists(second)))
        {
            destroyObject(second);
        }
        Vector existingNPCs = new Vector();
        if (hasObjVar(self, "fs_quest.final_battle_npcs"))
        {
            existingNPCs = utils.getResizeableObjIdBatchObjVar(self, "fs_quest.final_battle_npcs");
            obj_id NPC = null;
            for (int x = 0; x < existingNPCs.size(); x++)
            {
                NPC = (obj_id)existingNPCs.get(x);
                if ((isIdValid(NPC)) && (exists(NPC)))
                {
                    destroyObject(NPC);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
