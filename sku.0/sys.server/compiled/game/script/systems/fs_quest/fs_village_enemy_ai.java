package script.systems.fs_quest;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.fs_dyn_village;
import script.library.ai_lib;
import script.library.attrib;
import script.ai.ai_combat;
import script.library.factions;
import script.library.chat;
import script.library.utils;

public class fs_village_enemy_ai extends script.base_script
{
    public fs_village_enemy_ai()
    {
    }
    public void setPatrolPaths() throws InterruptedException
    {
        obj_id self = getSelf();
        if (ai_lib.isInCombat(self))
        {
            if (!utils.hasScriptVar(self, "fs_quest.patrol_set"))
            {
                utils.setScriptVar(self, "fs_quest.patrol_set", 1);
                messageTo(self, "tryPathAgain", null, 30.0f, false);
            }
            return;
        }
        location[] entries = fs_dyn_village.getVillageLocs("village_entry");
        int winner = 0;
        location here = getLocation(self);
        for (int i = 0; i < entries.length; i++)
        {
            if (here.distance(entries[i]) < here.distance(entries[winner]))
            {
                winner = i;
            }
        }
        location[] insides = fs_dyn_village.getVillageLocs("village_victims");
        location targetLocation = insides[rand(0, insides.length - 1)];
        setHomeLocation(self, targetLocation);
        setMovementRun(self);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_WANDER);
        ai_lib.aiPathTo(self, targetLocation);
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        LOG("questAI", "fs_village_enemy_ai.OnAttach(" + self + ") - setting up new patrol paths");
        setObjVar(self, fs_dyn_village.OBJVAR_TURRET_TARGET, 1);
        messageTo(self, "msgSelfDestruct", null, (float)fs_dyn_village.VILLAGE_ENEMY_MAX_LIFE, false);
        messageTo(self, "fs_enemy_combat_tick", null, 10.0f, false);
        setPatrolPaths();
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathNotFound(obj_id self) throws InterruptedException
    {
        LOG("questAI", "fs_village_enemy_ai.OnMovePathNotFound(" + self + ") - setting up new patrol paths");
        setPatrolPaths();
        return SCRIPT_CONTINUE;
    }
    public int tryPathAgain(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "fs_quest.patrol_set"))
        {
            utils.removeScriptVar(self, "fs_quest.patrol_set");
        }
        setPatrolPaths();
        return SCRIPT_CONTINUE;
    }
    public int resumeDefaultCalmBehavior(obj_id self, dictionary params) throws InterruptedException
    {
        setPatrolPaths();
        setAttributeInterested(self, attrib.ALL);
        return SCRIPT_CONTINUE;
    }
    public int msgSelfDestruct(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_OVERRIDE;
    }
    public int fs_enemy_combat_tick(obj_id self, dictionary params) throws InterruptedException
    {
        if (!ai_lib.isInCombat(self))
        {
            obj_id[] objects = getObjectsInRange(getSelf(), 32.0f);
            if (objects != null)
            {
                int iter;
                for (iter = 0; iter < objects.length; ++iter)
                {
                    if (isIdValid(objects[iter]) && hasObjVar(objects[iter], "faction"))
                    {
                        if (ai_combat.isGoodTarget(self, objects[iter]))
                        {
                            startCombat(self, objects[iter]);
                            break;
                        }
                    }
                }
            }
        }
        messageTo(self, "fs_enemy_combat_tick", null, 10.0f, false);
        return SCRIPT_CONTINUE;
    }
}
