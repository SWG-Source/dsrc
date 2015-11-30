package script.systems.fs_quest.exit_quest;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.quests;
import script.library.theater;
import script.library.chat;
import script.library.prose;
import script.ai.ai_combat;

public class final_battle_second extends script.base_script
{
    public final_battle_second()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id spawner = getObjIdObjVar(self, "quest_spawner.spawned_by");
        if (isIdValid(spawner))
        {
            obj_id theater_object = getObjIdObjVar(spawner, theater.VAR_PARENT);
            LOG("fs_quest", "!!!!!!!!!!!! theater_object ->" + theater_object);
            if (isIdValid(theater_object))
            {
                obj_id player = getObjIdObjVar(theater_object, "fs_quest.final_battle.player");
                setObjVar(self, "questOwner", player);
                setHomeLocation(self);
                ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
                setObjVar(theater_object, "fs_quest.final_battle.second", self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int msgAidGreen(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id spawner = getObjIdObjVar(self, "quest_spawner.spawned_by");
        obj_id questPlayer = getObjIdObjVar(self, "questOwner");
        prose_package pp;
        if (isIdValid(spawner))
        {
            obj_id theater_object = getObjIdObjVar(spawner, theater.VAR_PARENT);
            if (hasObjVar(theater_object, "lastKiller"))
            {
                obj_id killer = getObjIdObjVar(theater_object, "lastKiller");
                if (isIdValid(killer))
                {
                    pp = prose.getPackage(new string_id("quest/force_sensitive/exit", "taunt1"), killer);
                    chat.publicChat(self, killer, null, null, pp);
                    startCombat(self, killer);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        if (isIdValid(questPlayer))
        {
            pp = prose.getPackage(new string_id("quest/force_sensitive/exit", "taunt1"), questPlayer);
            chat.publicChat(self, questPlayer, null, null, pp);
            startCombat(self, questPlayer);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgAidRed(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id spawner = getObjIdObjVar(self, "quest_spawner.spawned_by");
        obj_id questPlayer = getObjIdObjVar(self, "questOwner");
        prose_package pp;
        if (isIdValid(spawner))
        {
            obj_id theater_object = getObjIdObjVar(spawner, theater.VAR_PARENT);
            if (hasObjVar(theater_object, "lastKiller"))
            {
                obj_id killer = getObjIdObjVar(theater_object, "lastKiller");
                if (isIdValid(killer))
                {
                    pp = prose.getPackage(new string_id("quest/force_sensitive/exit", "taunt2"), killer);
                    chat.publicChat(self, killer, null, null, pp);
                    startCombat(self, killer);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        if (isIdValid(questPlayer))
        {
            pp = prose.getPackage(new string_id("quest/force_sensitive/exit", "taunt2"), questPlayer);
            chat.publicChat(self, questPlayer, null, null, pp);
            startCombat(self, questPlayer);
        }
        return SCRIPT_CONTINUE;
    }
}
