package script.systems.fs_quest.exit_quest;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.quests;
import script.library.theater;
import script.library.healing;
import script.ai.ai_combat;

public class power_shrine extends script.base_script
{
    public power_shrine()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "msgFSBattleHealPulse", null, 5.0f, false);
        setMaxHitpoints(self, 40000);
        setHitpoints(self, getMaxHitpoints(self));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDamaged(obj_id self, obj_id attacker, obj_id weapon, int damage) throws InterruptedException
    {
        int hitpoints = getHitpoints(self);
        int max_hitpoints = getMaxHitpoints(self);
        float percent_hp = (float)hitpoints / (float)max_hitpoints;
        int alert_range = 0;
        if (percent_hp > .75)
        {
            alert_range = 15;
        }
        else if (percent_hp > .50)
        {
            alert_range = 20;
        }
        else if (percent_hp > .25)
        {
            alert_range = 25;
        }
        else 
        {
            alert_range = 30;
        }
        if (alert_range > 0)
        {
            obj_id[] defenders = new obj_id[0];
            defenders = getNPCsInRange(self, alert_range);
            for (int i = 0; i < defenders.length; i++)
            {
                startCombat(defenders[i], attacker);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDisabled(obj_id self, obj_id killer) throws InterruptedException
    {
        obj_id theater_object = getObjIdObjVar(self, theater.VAR_PARENT);
        if (isIdValid(theater_object))
        {
            obj_id leader = getObjIdObjVar(theater_object, "fs_quest.final_battle.leader");
            obj_id second = getObjIdObjVar(theater_object, "fs_quest.final_battle.second");
            String obj_var_name;
            if (hasObjVar(self, "fs_quest.red_shrine"))
            {
                obj_var_name = "fs_quest.final_battle.red_stones";
            }
            else 
            {
                obj_var_name = "fs_quest.final_battle.green_stones";
            }
            Vector stones = getResizeableObjIdArrayObjVar(theater_object, obj_var_name);
            if (stones.size() < 2)
            {
                removeObjVar(theater_object, obj_var_name);
                if (obj_var_name.equals("fs_quest.final_battle.green_stones"))
                {
                    obj_id[] red_stones = getObjIdArrayObjVar(theater_object, "fs_quest.final_battle.red_stones");
                    if (red_stones != null)
                    {
                        for (int i = 0; i < red_stones.length; i++)
                        {
                            playClientEffectObj(killer, "clienteffect/combat_lightning_rifle_hit.cef", red_stones[i], "");
                        }
                    }
                }
                if (obj_var_name.equals("fs_quest.final_battle.green_stones"))
                {
                    setObjVar(theater_object, "lastKiller", killer);
                    messageTo(theater_object, "msgQuestGreenStonesDestroyed", null, 0.0f, false);
                    if (isIdValid(second))
                    {
                        messageTo(second, "msgAidGreen", null, 1, false);
                    }
                }
                else 
                {
                    messageTo(theater_object, "msgQuestRedStonesDestroyed", null, 0.0f, false);
                    if (isIdValid(leader))
                    {
                        messageTo(leader, "msgAidRed", null, 1, false);
                    }
                    if (isIdValid(second))
                    {
                        messageTo(second, "msgAidRed", null, 1, false);
                    }
                }
            }
            else 
            {
                stones.remove(self);
                setObjVar(theater_object, obj_var_name, stones);
            }
        }
        playClientEffectObj(killer, "clienteffect/combat_explosion_lair_large.cef", self, "");
        messageTo(self, "msgQuestDestroyShrine", null, 3.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int msgFSBattleHealPulse(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id theater_object = getObjIdObjVar(self, theater.VAR_PARENT);
        if (!isIdValid(theater_object))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = getObjIdObjVar(theater_object, "fs_quest.final_battle.player");
        if (hasObjVar(self, "fs_quest.red_shrine"))
        {
            obj_id leader = getObjIdObjVar(theater_object, "fs_quest.final_battle.leader");
            if (isIdValid(leader))
            {
                healing.fullHealEveryone(leader);
                playClientEffectObj(player, "clienteffect/healing_healdamage.cef", leader, "");
            }
        }
        else 
        {
            obj_id leader = getObjIdObjVar(theater_object, "fs_quest.final_battle.leader");
            boolean source_effect = false;
            if (isIdValid(leader))
            {
                healing.fullHealEveryone(leader);
                playClientEffectObj(player, "clienteffect/healing_healdamage.cef", leader, "");
                source_effect = true;
            }
            obj_id second = getObjIdObjVar(theater_object, "fs_quest.final_battle.second");
            if (isIdValid(second))
            {
                healing.fullHealEveryone(second);
                playClientEffectObj(player, "clienteffect/healing_healdamage.cef", second, "");
                if (!source_effect)
                {
                    source_effect = true;
                }
            }
            obj_id[] stones = getObjIdArrayObjVar(theater_object, "fs_quest.final_battle.red_stones");
            if (stones != null)
            {
                for (int i = 0; i < stones.length; i++)
                {
                    setHitpoints(stones[i], getMaxHitpoints(stones[i]));
                    playClientEffectObj(player, "clienteffect/healing_healdamage.cef", stones[i], "");
                }
            }
        }
        messageTo(self, "msgFSBattleHealPulse", null, 10.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int msgQuestDestroyShrine(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
