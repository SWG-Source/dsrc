package script.ai;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.chat;
import script.library.buff;
import script.library.utils;
import script.library.combat;
import script.library.groundquests;
import script.library.factions;
import script.library.smuggler;

public class smuggler_spawn_enemy extends script.base_script
{
    public smuggler_spawn_enemy()
    {
    }
    public static final String STRING_FILE = "smuggler/enemy";
    public static final int FLAG_CLEAN_UP = 1;
    public static final int FLAG_ATTACK = 2;
    public static final int FLAG_FOLLOW = 3;
    public static final String VOL_PATROL_WATCH = "smugglerPatrolWatch";
    public static final float VOL_PATROL_RANGE = 12.0f;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "startContrabandCheck", null, 2.0f, false);
        return SCRIPT_CONTINUE;
    }
    public void doSnare(obj_id playerSmuggler, obj_id npc) throws InterruptedException
    {
        if (isIncapacitated(npc))
        {
            return;
        }
        if (isIncapacitated(playerSmuggler))
        {
            return;
        }
        if (utils.hasScriptVar(npc, "fastTalked"))
        {
            return;
        }
        int taunt = rand(1, 5);
        if (taunt == 1)
        {
            string_id barkString = new string_id(STRING_FILE, "bark_snare");
            chat.chat(npc, barkString);
        }
        buff.applyBuff(playerSmuggler, "caltropSnare");
        return;
    }
    public void broadcastCondition(obj_id playerSmuggler, obj_id npc, int flag) throws InterruptedException
    {
        location here = getLocation(npc);
        obj_id[] objects = getObjectsInRange(here, 32);
        for (int i = 0; i < objects.length; i++)
        {
            if (hasObjVar(objects[i], "quest.owner"))
            {
                if ((getObjIdObjVar(objects[i], "quest.owner") == playerSmuggler) && (npc != objects[i]))
                {
                    switch (flag)
                    {
                        case FLAG_CLEAN_UP:
                        messageTo(objects[i], "handleCleanUp", null, 1.0f, false);
                        break;
                        case FLAG_ATTACK:
                        if (isIncapacitated(playerSmuggler))
                        {
                            break;
                        }
                        if (!ai_lib.isInCombat(objects[i]))
                        {
                            if (!utils.hasScriptVar(objects[i], "fastTalked"))
                            {
                                startCombat(objects[i], playerSmuggler);
                            }
                        }
                        else 
                        {
                            obj_id enemyTarget = getHateTarget(objects[i]);
                            if (!isIdValid(enemyTarget))
                            {
                                break;
                            }
                            if (enemyTarget != playerSmuggler)
                            {
                                if (!utils.hasScriptVar(objects[i], "fastTalked"))
                                {
                                    float hate = getMaxHate(objects[i]);
                                    addHate(objects[i], playerSmuggler, (hate + 1000f));
                                }
                            }
                        }
                        messageTo(objects[i], "combatLoop", null, 11.0f, false);
                        break;
                        case FLAG_FOLLOW:
                        ai_lib.aiFollow(objects[i], npc, 3f, 6f);
                        messageTo(objects[i], "handleCleanUp", null, 9.0f, false);
                        break;
                        default:
                        break;
                    }
                }
            }
        }
        return;
    }
    public void enemyPickFight(obj_id npc, obj_id victimNpc, obj_id playerSmuggler) throws InterruptedException
    {
        clearHateList(npc);
        clearHateList(victimNpc);
        utils.setScriptVar(npc, "fastTalked", playerSmuggler);
        setMaster(npc, playerSmuggler);
        utils.setScriptVar(victimNpc, "fastTalked", playerSmuggler);
        startCombat(npc, victimNpc);
        startCombat(victimNpc, npc);
        addHate(npc, victimNpc, 9999.0f);
        addHate(victimNpc, npc, 9999.0f);
        string_id barkString = new string_id(STRING_FILE, "bark_myfriend");
        chat.chat(npc, barkString);
        barkString = new string_id(STRING_FILE, "bark_huh");
        chat.chat(victimNpc, barkString);
        dictionary params = new dictionary();
        params.put("playerSmuggler", playerSmuggler);
        messageTo(npc, "endConfusion", params, 15.0f, false);
        messageTo(victimNpc, "endConfusion", params, 15.0f, false);
        return;
    }
    public void doConfuse(obj_id playerSmuggler, obj_id npc) throws InterruptedException
    {
        removeHateTarget(npc, playerSmuggler);
        stopCombat(npc);
        string_id barkString = new string_id(STRING_FILE, "bark_confused");
        chat.chat(npc, barkString);
        utils.setScriptVar(npc, "fastTalked", playerSmuggler);
        ai_lib.aiStopFollowing(npc);
        ai_lib.pathAwayFrom(npc, playerSmuggler);
        dictionary params = new dictionary();
        params.put("playerSmuggler", playerSmuggler);
        messageTo(npc, "endConfusion", params, 15.0f, false);
        return;
    }
    public void doConfuseAttack(obj_id playerSmuggler, obj_id npc) throws InterruptedException
    {
        location here = getLocation(npc);
        obj_id[] objects = getObjectsInRange(here, 25);
        obj_id victimEnemy = null;
        obj_id enemyTarget = null;
        for (int i = 0; i < objects.length; i++)
        {
            if (hasScript(objects[i], "ai.smuggler_spawn_enemy"))
            {
                if (objects[i] != npc)
                {
                    victimEnemy = objects[i];
                    if (ai_lib.isInCombat(objects[i]))
                    {
                        enemyTarget = getHateTarget(objects[i]);
                        if (isIdValid(enemyTarget))
                        {
                            if (enemyTarget == playerSmuggler)
                            {
                                enemyPickFight(npc, objects[i], playerSmuggler);
                                return;
                            }
                        }
                    }
                }
            }
        }
        if (isIdValid(victimEnemy))
        {
            enemyPickFight(npc, victimEnemy, playerSmuggler);
        }
        else 
        {
            doConfuse(playerSmuggler, npc);
        }
        return;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (hasObjVar(breacher, "gm"))
        {
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (breacher == self)
        {
            return SCRIPT_CONTINUE;
        }
        if (isIncapacitated(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (isIncapacitated(breacher))
        {
            return SCRIPT_CONTINUE;
        }
        if (volumeName.equals(VOL_PATROL_WATCH))
        {
            if (utils.getPlayerProfession(breacher) == utils.SMUGGLER)
            {
                if (groundquests.isDoingSmugglerMission(breacher))
                {
                    int taunt = rand(1, 10);
                    if (taunt == 1)
                    {
                        string_id barkString = new string_id(STRING_FILE, "bark_smuggler");
                        chat.chat(self, barkString);
                        int smugglerLevel = combat.getLevel(breacher);
                        int myLevel = combat.getLevel(self);
                        if (smugglerLevel > myLevel)
                        {
                            int levelDifference = (smugglerLevel - myLevel);
                            if (levelDifference > 5)
                            {
                                barkString = new string_id(STRING_FILE, "bark_help");
                                chat.chat(self, barkString);
                                groundquests.sendSignal(breacher, "smugglerPatrolHelp");
                            }
                        }
                    }
                    doSnare(breacher, self);
                    startCombat(self, breacher);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int combatLoop(obj_id self, dictionary params) throws InterruptedException
    {
        if (isIncapacitated(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "fastTalked"))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "quest.owner"))
        {
            obj_id playerSmuggler = (getObjIdObjVar(self, "quest.owner"));
            if (!isIdValid(playerSmuggler))
            {
                return SCRIPT_CONTINUE;
            }
            if (isIncapacitated(playerSmuggler))
            {
                messageTo(self, "handleCleanUp", null, 1.0f, false);
            }
            float distanceRange = (getLocation(self)).distance(getLocation(playerSmuggler));
            if (distanceRange > 256.0f)
            {
                messageTo(self, "handleCleanUp", null, 1.0f, false);
                return SCRIPT_CONTINUE;
            }
            if (!ai_lib.isInCombat(self))
            {
                if (!utils.hasScriptVar(self, "fastTalked"))
                {
                    startCombat(self, playerSmuggler);
                }
            }
            if (!buff.hasBuff(playerSmuggler, "caltropSnare"))
            {
                doSnare(playerSmuggler, self);
            }
            messageTo(self, "combatLoop", null, 11.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int smugglerKilled(obj_id self, dictionary params) throws InterruptedException
    {
        if (isIncapacitated(self))
        {
            return SCRIPT_CONTINUE;
        }
        string_id barkString = new string_id(STRING_FILE, "bark_smack_talk");
        chat.chat(self, barkString);
        if (hasObjVar(self, "quest.owner"))
        {
            obj_id playerSmuggler = (getObjIdObjVar(self, "quest.owner"));
            if (!isIdValid(playerSmuggler))
            {
                return SCRIPT_CONTINUE;
            }
            messageTo(self, "handleCleanUp", null, 3.0f, false);
            broadcastCondition(playerSmuggler, self, FLAG_CLEAN_UP);
        }
        return SCRIPT_CONTINUE;
    }
    public int startContrabandCheck(obj_id self, dictionary params) throws InterruptedException
    {
        if (isIncapacitated(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "quest.owner"))
        {
            setBaseWalkSpeed(self, 6.0f);
            setBaseRunSpeed(self, 9.0f);
            setAILeashTime(self, 999999);
            obj_id playerSmuggler = (getObjIdObjVar(self, "quest.owner"));
            int randStr = rand(1, 3);
            string_id barkString = new string_id(STRING_FILE, "bark_s" + randStr);
            if (!ai_lib.isInCombat(self))
            {
                faceTo(self, playerSmuggler);
                chat.chat(self, barkString);
                ai_lib.aiFollow(self, playerSmuggler, 3f, 6f);
            }
            if (hasObjVar(self, "quest.count"))
            {
                int boss = getIntObjVar(self, "quest.count");
                if (boss == 0)
                {
                    if (!ai_lib.isInCombat(self))
                    {
                        messageTo(self, "contrabandCheckResult", null, 11.0f, false);
                        utils.setScriptVar(self, "contrabandCheck", 1);
                    }
                    else 
                    {
                        broadcastCondition(playerSmuggler, self, FLAG_ATTACK);
                    }
                }
            }
        }
        else 
        {
            createTriggerVolume(VOL_PATROL_WATCH, VOL_PATROL_RANGE, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int contrabandCheckResult(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, "contrabandCheck");
        if (isIncapacitated(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "quest.owner"))
        {
            obj_id playerSmuggler = (getObjIdObjVar(self, "quest.owner"));
            if (!isIdValid(playerSmuggler))
            {
                return SCRIPT_CONTINUE;
            }
            int expertiseIncrease = 0;
            int rankIncrease = 0;
            if (utils.hasScriptVar(self, "slyLie"))
            {
                float underworldFaction = factions.getFactionStanding(playerSmuggler, "underworld");
                int smugglerRank = smuggler.getSmugglerRank(underworldFaction);
                expertiseIncrease = 10 + (int)getSkillStatisticModifier(playerSmuggler, "expertise_sly_lie_bonus");
                rankIncrease = (int)getSkillStatisticModifier(self, "expertise_sly_lie_rank") * smugglerRank;
                utils.removeScriptVar(self, "slyLie");
            }
            int finalChance = expertiseIncrease + rankIncrease;
            if (rand(1, 100) > (finalChance + 5))
            {
                string_id barkString = new string_id(STRING_FILE, "bark_attack");
                if (finalChance > 0)
                {
                    barkString = new string_id(STRING_FILE, "bark_lies_failed");
                }
                chat.chat(self, barkString);
                doSnare(playerSmuggler, self);
                if (!ai_lib.isInCombat(self))
                {
                    startCombat(self, playerSmuggler);
                }
                else 
                {
                    obj_id enemyTarget = getHateTarget(self);
                    if (!isIdValid(enemyTarget))
                    {
                        return SCRIPT_CONTINUE;
                    }
                    if (enemyTarget != playerSmuggler)
                    {
                        float hate = getMaxHate(self);
                        addHate(self, playerSmuggler, (hate + 1000f));
                    }
                }
                messageTo(self, "combatLoop", null, 11.0f, false);
                if (!hasObjVar(self, "bossEnemy"))
                {
                    broadcastCondition(playerSmuggler, self, FLAG_ATTACK);
                }
            }
            else 
            {
                if (!ai_lib.isInCombat(self))
                {
                    string_id barkString = new string_id(STRING_FILE, "bark_sorry");
                    chat.chat(self, barkString);
                    ai_lib.pathAwayFrom(self, playerSmuggler);
                    messageTo(self, "handleCleanUp", null, 9.0f, false);
                    if (!hasObjVar(self, "bossEnemy"))
                    {
                        broadcastCondition(playerSmuggler, self, FLAG_FOLLOW);
                    }
                }
                else 
                {
                    if (!hasObjVar(self, "bossEnemy"))
                    {
                        obj_id enemyTarget = getHateTarget(self);
                        if (enemyTarget != playerSmuggler)
                        {
                            float hate = getMaxHate(self);
                            addHate(self, playerSmuggler, (hate + 1000f));
                        }
                        broadcastCondition(playerSmuggler, self, FLAG_ATTACK);
                    }
                }
            }
        }
        utils.removeScriptVar(self, "slyLie");
        return SCRIPT_CONTINUE;
    }
    public int fastTalkReaction(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id playerSmuggler = params.getObjId("playerSmuggler");
        if (!isIdValid(playerSmuggler))
        {
            return SCRIPT_CONTINUE;
        }
        float underworldFaction = factions.getFactionStanding(playerSmuggler, "underworld");
        int smugglerRank = smuggler.getSmugglerRank(underworldFaction);
        int expertiseIncrease = (int)getSkillStatisticModifier(playerSmuggler, "expertise_fast_talk_bonus");
        int rankIncrease = (int)getSkillStatisticModifier(self, "expertise_fast_talk_rank") * smugglerRank;
        int finalChance = expertiseIncrease + rankIncrease;
        int roll = rand(1, 100);
        if (roll > (finalChance + 25))
        {
            string_id barkString = new string_id(STRING_FILE, "bark_fast_talk_failed");
            chat.chat(self, barkString);
            return SCRIPT_CONTINUE;
        }
        if (roll < (finalChance - 10))
        {
            doConfuseAttack(playerSmuggler, self);
            return SCRIPT_CONTINUE;
        }
        doConfuse(playerSmuggler, self);
        return SCRIPT_CONTINUE;
    }
    public int endConfusion(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id playerSmuggler = params.getObjId("playerSmuggler");
        setMaster(self, null);
        utils.removeScriptVar(self, "fastTalked");
        if (!isIdValid(playerSmuggler))
        {
            return SCRIPT_CONTINUE;
        }
        if ((rand(1, 5)) == 1)
        {
            string_id barkString = new string_id(STRING_FILE, "bark_notfooled");
            chat.chat(self, barkString);
        }
        if (!ai_lib.isInCombat(self))
        {
            startCombat(self, playerSmuggler);
        }
        else 
        {
            obj_id enemyTarget = getHateTarget(self);
            if (enemyTarget != playerSmuggler)
            {
                float hate = getMaxHate(self);
                addHate(self, playerSmuggler, (hate + 1000f));
            }
        }
        messageTo(self, "combatLoop", null, 1.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleCleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "fastTalked"))
        {
            obj_id noAttack = utils.getObjIdScriptVar(self, "fastTalked");
            if (!isIdValid(noAttack))
            {
                return SCRIPT_CONTINUE;
            }
            string_id barkString = new string_id(STRING_FILE, "bark_confused");
            chat.chat(self, barkString);
            removeHateTarget(self, noAttack);
            ai_lib.aiStopFollowing(self);
            ai_lib.pathAwayFrom(self, noAttack);
        }
        return SCRIPT_CONTINUE;
    }
}
