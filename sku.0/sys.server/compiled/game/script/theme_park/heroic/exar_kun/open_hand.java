package script.theme_park.heroic.exar_kun;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.buff;
import script.library.chat;
import script.library.combat;
import script.library.healing;
import script.library.trial;
import script.library.utils;

public class open_hand extends script.base_script
{
    public open_hand()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.setHp(self, trial.HP_EXAR_OPEN);
        String[] cultists = 
        {
            "one",
            "two",
            "three",
            "four",
            "five",
            "six",
            "seven",
            "eight"
        };
        utils.setScriptVar(self, "cultist", cultists);
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if (getCellId(trial.getTop(self), "r2") != destContainer)
        {
            location selfLoc = getLocation(self);
            location warpLoc = selfLoc;
            warpLoc.cell = getCellId(trial.getTop(self), "r2");
            warpLoc.x = -8.0f;
            warpLoc.z = -68.0f;
            setLocation(self, warpLoc);
            setLocation(getHateTarget(self), warpLoc);
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        messageTo(self, "summonNextAdd", trial.getSessionDict(self, "add"), 5.0f, false);
        messageTo(self, "sharePain", null, 3.0f, false);
        messageTo(self, "teslaZap", null, 8.0f, false);
        buff.applyBuff(self, "open_balance_buff", 1200.0f);
        return SCRIPT_CONTINUE;
    }
    public int summonNextAdd(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] allSpawn = trial.getObjectsInDungeonWithObjVar(trial.getTop(self), "spawn_id");
        obj_id friend = getNextCultist(allSpawn);
        if (!isIdValid(friend))
        {
            messageTo(self, "finalBuff", null, 11.0f, false);
            return SCRIPT_CONTINUE;
        }
        dictionary dict = new dictionary();
        dict.put("sacrifice", friend);
        chat.chat(self, "Let your fervor rise");
        messageTo(friend, "activateSelf", null, 0.0f, false);
        messageTo(self, "summonNextAdd", null, 30.0f, false);
        messageTo(self, "sacrificeAdd", dict, 40.0f, false);
        return SCRIPT_CONTINUE;
    }
    public obj_id getNextCultist(obj_id[] allSpawn) throws InterruptedException
    {
        if (allSpawn == null || allSpawn.length == 0)
        {
            return null;
        }
        String cultist = getCultistString(getSelf());
        for (int i = 0; i < allSpawn.length; i++)
        {
            if ((getStringObjVar(allSpawn[i], "spawn_id")).equals(cultist))
            {
                return allSpawn[i];
            }
        }
        return null;
    }
    public String getCultistString(obj_id self) throws InterruptedException
    {
        String[] masterList = utils.getStringArrayScriptVar(self, "cultist");
        if (masterList == null || masterList.length == 0)
        {
            return "none";
        }
        Vector cultList = new Vector(Arrays.asList(masterList));
        String nextCultist = ((String)cultList.get(rand(0, cultList.size() - 1)));
        cultList.remove(nextCultist);
        if (cultList != null)
        {
            masterList = new String[cultList.size()];
            cultList.toArray(masterList);

        }
        utils.setScriptVar(self, "cultist", masterList);
        return nextCultist;
    }
    public int sacrificeAdd(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id add = params.getObjId("sacrifice");
        if (!isIdValid(add) || isDead(add))
        {
            return SCRIPT_CONTINUE;
        }
        incrementAddsKilled(self);
        String sacBuff = getSacrificeBuff(self);
        chat.chat(self, "Your God demands sacrifice!");
        kill(add);
        if (!sacBuff.equals("none"))
        {
            buff.applyBuff(self, sacBuff);
        }
        int healingReduction = getEnhancedSkillStatisticModifierUncapped(self, "expertise_healing_reduction");
        float redux = (float)healingReduction / ((float)healingReduction + 75.0f);
        int toHeal = (int)(125000.0f - (125000.0f * redux));
        toHeal = toHeal < 1 ? 1 : toHeal;
        healing.healDamage(self, HEALTH, toHeal);
        playClientEffectLoc(self, "clienteffect/bacta_bomb.cef", getLocation(self), 0);
        return SCRIPT_CONTINUE;
    }
    public String getSacrificeBuff(obj_id self) throws InterruptedException
    {
        int killed = getNumberAddsKilled(self);
        String sacrifice = "none";
        switch (killed)
        {
            case 1:
            sacrifice = "kun_one_sacrifice";
            break;
            case 2:
            sacrifice = "kun_two_sacrifice";
            break;
            case 3:
            sacrifice = "kun_three_sacrifice";
            break;
            case 4:
            sacrifice = "kun_four_sacrifice";
            break;
            case 5:
            sacrifice = "kun_five_sacrifice";
            break;
            case 6:
            sacrifice = "kun_six_sacrifice";
            break;
            case 7:
            sacrifice = "kun_seven_sacrifice";
            break;
            case 8:
            sacrifice = "kun_eight_sacrifice";
            break;
        }
        return sacrifice;
    }
    public void incrementAddsKilled(obj_id self) throws InterruptedException
    {
        int killed = getNumberAddsKilled(self);
        killed++;
        utils.setScriptVar(self, "killed", killed);
    }
    public int getNumberAddsKilled(obj_id self) throws InterruptedException
    {
        return utils.hasScriptVar(self, "killed") ? utils.getIntScriptVar(self, "killed") : 0;
    }
    public int sharePain(obj_id self, dictionary params) throws InterruptedException
    {
        if (isDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        utils.removeScriptVarTree(self, "pain");
        obj_id[] hateList = getHateList(self);
        int maxSize = hateList.length < 3 ? hateList.length : 3;
        Vector vectorList = new Vector(Arrays.asList(hateList));
        vectorList = utils.shuffleArray(vectorList);
        for (int i = 0; i < vectorList.size() && i < maxSize; i++)
        {
            utils.setScriptVar(self, "pain.list" + i, ((obj_id)vectorList.get(i)));
            buff.applyBuff(((obj_id)vectorList.get(i)), "kun_open_share_pain_debuff");
        }
        buff.applyBuff(self, "kun_open_share_pain");
        messageTo(self, "sharePain", null, 18.0f, false);
        chat.chat(self, "My pain runs deep. Let me share it with you.");
        return SCRIPT_CONTINUE;
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id weapon, int[] damage) throws InterruptedException
    {
        int total = 0;
        for (int i = 0; i < damage.length; i++)
        {
            total += damage[i];
        }
        int split = Math.round((float)total / 5.0f);
        prose_package pp = new prose_package();
        pp.stringId = new string_id("cbt_spam", "kun_open_share_pain");
        pp.actor.set(self);
        for (int k = 0; k < 3; k++)
        {
            if (!utils.hasScriptVar(self, "pain.list" + k))
            {
                break;
            }
            obj_id target = utils.getObjIdScriptVar(self, "pain.list" + k);
            if (!isIdValid(target) || isDead(target) || isIncapacitated(target))
            {
                continue;
            }
            pp.target.set(target);
            pp.digitInteger = split;
            combat.sendCombatSpamMessageProse(target, self, pp, true, true, false, COMBAT_RESULT_HIT);
            damage(target, DAMAGE_ELEMENTAL_HEAT, HIT_LOCATION_BODY, split);
        }
        return SCRIPT_CONTINUE;
    }
    public int finalBuff(obj_id self, dictionary params) throws InterruptedException
    {
        String lastBuff = getSacrificeBuff(self);
        if (!lastBuff.equals("none"))
        {
            buff.applyBuff(self, lastBuff, 1200.0f);
        }
        buff.removeBuff(self, "open_balance_buff");
        return SCRIPT_CONTINUE;
    }
    public int teslaZap(obj_id self, dictionary params) throws InterruptedException
    {
        if (isDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] hateList = getHateList(self);
        int maxSize = hateList.length < 3 ? hateList.length : 3;
        Vector vectorList = new Vector(Arrays.asList(hateList));
        vectorList = utils.shuffleArray(vectorList);
        for (int i = 0; i < vectorList.size() && i < maxSize; i++)
        {
            buff.applyBuff(((obj_id)vectorList.get(i)), "kun_open_spark");
        }
        messageTo(self, "teslaZap", null, 15.0f, false);
        return SCRIPT_CONTINUE;
    }
}
