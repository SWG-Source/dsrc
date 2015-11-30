package script.theme_park.heroic.axkva_min;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.buff;
import script.library.combat;
import script.library.create;
import script.library.trial;
import script.library.utils;

public class suin_chalo extends script.base_script
{
    public suin_chalo()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.setHp(self, trial.HP_AXKVA_SUIN);
        return SCRIPT_CONTINUE;
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        if (ai_lib.isDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        int max = getMaxHealth(self);
        int current = getHealth(self);
        int toHeal = max - current;
        addToHealth(self, toHeal);
        String[] lists = 
        {
            "spawn90",
            "spawn75",
            "spawn50",
            "spawn35",
            "spawn20"
        };
        utils.removeObjVarList(self, lists);
        return SCRIPT_CONTINUE;
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id weapon, int[] damage) throws InterruptedException
    {
        float max = (float)getMaxHealth(self);
        float current = (float)getHealth(self);
        float ratio = current / max;
        if (ratio <= 0.9f)
        {
            if (ratio <= 0.75f)
            {
                if (ratio <= 0.6f)
                {
                    if (ratio <= 0.45f)
                    {
                        if (ratio <= 0.35f)
                        {
                            if (ratio <= 0.3f)
                            {
                                if (!buff.hasBuff(self, "bm_enrage"))
                                {
                                    buff.applyBuff(self, "bm_enrage", -1.0f);
                                }
                            }
                            spawnAspect(self, 30);
                            return SCRIPT_CONTINUE;
                        }
                        spawnAspect(self, 45);
                        return SCRIPT_CONTINUE;
                    }
                    spawnAspect(self, 60);
                    return SCRIPT_CONTINUE;
                }
                spawnAspect(self, 75);
                return SCRIPT_CONTINUE;
            }
            spawnAspect(self, 90);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void spawnAspect(obj_id self, int value) throws InterruptedException
    {
        if (hasObjVar(self, "spawn" + value))
        {
            return;
        }
        setObjVar(self, "spawn" + value, 1);
        String[] aspects = 
        {
            "heat_aspect",
            "acid_aspect",
            "elec_aspect",
            "cold_aspect"
        };
        int idx = rand(0, aspects.length - 1);
        obj_id creature = create.object("heroic_axkva_" + aspects[idx], getLocation(self));
        String name = "@" + new string_id("mob/creature_names", aspects[idx]);
        setName(creature, name);
        buff.applyBuff(creature, aspects[idx]);
        utils.setScriptVar(creature, "immunity.dot.all", true);
        trial.markAsTempObject(creature, true);
        attachScript(creature, "theme_park.heroic.axkva_min.suin_guard");
        utils.setScriptVar(creature, "aspect", aspects[idx]);
        trial.setParent(trial.getTop(self), creature, false);
        obj_id[] hateList = getHateList(self);
        for (int i = 0; i < hateList.length; i++)
        {
            setHate(creature, hateList[i], 1.0f);
        }
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        obj_id[] children = trial.getChildrenInRange(self, self, 200.0f);
        if (children == null || children.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        utils.messageTo(children, "suin_died", null, 0.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        obj_id[] players = trial.getPlayersInDungeon(trial.getTop(self));
        if (players == null || players.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < players.length; i++)
        {
            addHate(self, players[i], 1);
        }
        return SCRIPT_CONTINUE;
    }
}
