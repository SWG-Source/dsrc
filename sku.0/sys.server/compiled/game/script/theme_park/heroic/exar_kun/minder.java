package script.theme_park.heroic.exar_kun;

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

public class minder extends script.base_script
{
    public minder()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "setupSquad", null, 1.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        messageTo(self, "buff_me", null, 1.0f, false);
        messageTo(self, "summon_adds", null, 20.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int setupSquad(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] bat = trial.getObjectsInDungeonWithObjVar(trial.getTop(self), "bat_id");
        obj_id[] monkey = trial.getObjectsInDungeonWithObjVar(trial.getTop(self), "skreeg_id");
        setMaster(bat[0], self);
        setMaster(monkey[0], self);
        obj_id[] allies = new obj_id[3];
        allies[0] = self;
        allies[1] = bat[0];
        allies[2] = monkey[0];
        ai_lib.establishAgroLink(self, allies);
        trial.setHp(self, trial.HP_EXAR_MINDER);
        trial.setHp(bat[0], trial.HP_EXAR_VINRITH);
        trial.setHp(monkey[0], trial.HP_EXAR_LURESH);
        return SCRIPT_CONTINUE;
    }
    public int buff_me(obj_id self, dictionary params) throws InterruptedException
    {
        if (isDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        buff.applyBuff(self, "kun_minder_heal_buff");
        messageTo(self, "buff_me", null, 20.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int summon_adds(obj_id self, dictionary params) throws InterruptedException
    {
        if (isDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] bat = trial.getObjectsInDungeonWithObjVar(trial.getTop(self), "bat_id");
        obj_id[] monkey = trial.getObjectsInDungeonWithObjVar(trial.getTop(self), "skreeg_id");
        obj_id vinrith = null;
        obj_id luresh = null;
        if (bat != null && bat.length > 0)
        {
            if (!isDead(bat[0]))
            {
                vinrith = bat[0];
            }
        }
        if (monkey != null && monkey.length > 0)
        {
            if (!isDead(monkey[0]))
            {
                luresh = monkey[0];
            }
        }
        if ((!isIdValid(vinrith) || !exists(vinrith) || isDead(vinrith)) && (!isIdValid(luresh) || !exists(luresh) || isDead(luresh)))
        {
            return SCRIPT_CONTINUE;
        }
        Vector randomAdd = new Vector();
        randomAdd.setSize(0);
        final int GB_BOSS = 1;
        final int SK_BOSS = 2;
        if (isIdValid(vinrith) && exists(vinrith) && !isDead(vinrith))
        {
            utils.addElement(randomAdd, GB_BOSS);
        }
        if (isIdValid(luresh) && exists(luresh) && !isDead(luresh))
        {
            utils.addElement(randomAdd, SK_BOSS);
        }
        int result = ((Integer)randomAdd.get(rand(0, randomAdd.size() - 1))).intValue();
        dictionary dict = trial.getSessionDict(trial.getTop(self));
        switch (result)
        {
            case GB_BOSS:
            dict.put("triggerType", "triggerId");
            dict.put("triggerName", "summon_bats");
            messageTo(trial.getTop(self), "triggerFired", dict, 0.0f, false);
            break;
            case SK_BOSS:
            dict.put("triggerType", "triggerId");
            dict.put("triggerName", "summon_monkey");
            messageTo(trial.getTop(self), "triggerFired", dict, 0.0f, false);
            break;
        }
        messageTo(self, "summon_adds", null, 30.0f, false);
        return SCRIPT_CONTINUE;
    }
}
