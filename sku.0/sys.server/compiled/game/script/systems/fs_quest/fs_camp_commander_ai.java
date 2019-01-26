package script.systems.fs_quest;

import script.*;
import script.library.*;

public class fs_camp_commander_ai extends script.base_script
{
    public fs_camp_commander_ai()
    {
    }
    public static final int RUNAWAY_TIME_LIMIT = 180;
    public static final float MIN_DISTANCE_TO_RUN = 50.0f;
    public static final float MAX_DISTANCE_TO_RUN = 100.0f;
    public static final float RUN_PULSE_TIME = 10;
    public static final int MIN_RESCUERS = 1;
    public static final int MAX_RESCUERS = 2;
    public static final float HELP_SPAWN_TIME_MIN = 180;
    public static final float HELP_SPAWN_TIME_MAX = 300;
    public void lifeExpired() throws InterruptedException
    {
        obj_id self = getSelf();
        if (hasScript(self, "ai.ai"))
        {
            detachScript(self, "ai.ai");
        }
        if (hasScript(self, "ai.creature_combat"))
        {
            detachScript(self, "ai.creature_combat");
        }
        if (hasScript(self, "systems.combat.credit_for_kills"))
        {
            detachScript(self, "systems.combat.credit_for_kills");
        }
        if (hasScript(self, "systems.combat.combat_actions"))
        {
            detachScript(self, "systems.combat.combat_actions");
        }
        setPosture(self, POSTURE_KNOCKED_DOWN);
        utils.setScriptVar(self, "deathSequence", 1);
        messageTo(self, "msgDeathSequence", null, 5.0f, false);
        return;
    }
    public int msgSpawnRescuers(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "deathSequence"))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "fs_cs.iAmFree") || !hasObjVar(self, "fs_cs.primaryEscort"))
        {
            if (hasObjVar(self, "fs_cs.helpCallPending"))
            {
                removeObjVar(self, "fs_cs.helpCallPending");
            }
            return SCRIPT_CONTINUE;
        }
        obj_id target = getObjIdObjVar(self, "fs_cs.primaryEscort");
        int amount = rand(MIN_RESCUERS, MAX_RESCUERS);
        location targetLoc = getLocation(target);
        location here = getLocation(self);
        if (targetLoc != null && targetLoc.distance(here) < 300)
        {
            location spawnLoc = utils.getRandomAwayLocation(targetLoc, 75, 100);
            for (int i = 0; i < amount; i++)
            {
                obj_id mob = create.createCreature("shadow_pirate_nonaggro", spawnLoc, true);
                if (isIdValid(mob) && exists(mob))
                {
                    setObjVar(mob, "fs_cs.target", target);
                    attachScript(mob, "systems.fs_quest.fs_commander_rescue_ai");
                }
            }
        }
        messageTo(self, "msgSpawnRescuers", null, rand(HELP_SPAWN_TIME_MIN, HELP_SPAWN_TIME_MAX), false);
        return SCRIPT_CONTINUE;
    }
    public int msgDeathSequence(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "deathSequence"))
        {
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        int phase = utils.getIntScriptVar(self, "deathSequence");
        switch (phase)
        {
            case 1:
            chat.chat(self, new string_id("fs_quest_village", "commander_pain"));
            utils.setScriptVar(self, "deathSequence", 2);
            messageTo(self, "msgDeathSequence", null, rand(5, 10), false);
            break;
            case 2:
            chat.chat(self, new string_id("fs_quest_village", "commander_pain2"));
            utils.setScriptVar(self, "deathSequence", 3);
            messageTo(self, "msgDeathSequence", null, rand(5, 10), false);
            break;
            case 3:
            chat.chat(self, new string_id("fs_quest_village", "commander_pain3"));
            utils.setScriptVar(self, "deathSequence", 4);
            messageTo(self, "msgDeathSequence", null, rand(5, 10), false);
            break;
            default:
            obj_id primaryEscort = null;
            obj_id shieldKiller = null;
            dictionary dic = new dictionary();
            dic.put("sender", self);
            if (hasObjVar(self, fs_counterstrike.OBJVAR_CAMP_SHIELD_KILLER))
            {
                shieldKiller = getObjIdObjVar(self, fs_counterstrike.OBJVAR_CAMP_SHIELD_KILLER);
            }
            if (hasObjVar(self, "fs_cs.primaryEscort"))
            {
                primaryEscort = getObjIdObjVar(getSelf(), "fs_cs.primaryEscort");
            }
            if (isIdValid(primaryEscort))
            {
                messageTo(primaryEscort, "msgCommanderDied", dic, 0.0f, false);
            }
            if (isIdValid(shieldKiller) && (shieldKiller != primaryEscort) && !hasObjVar(self, "fs_cs.shieldKillerOusted"))
            {
                messageTo(shieldKiller, "msgCommanderDied", dic, 0.0f, false);
            }
            destroyObject(self);
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public int msgLifeExpired(obj_id self, dictionary params) throws InterruptedException
    {
        lifeExpired();
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "msgLifeExpired", null, fs_counterstrike.getCampBossDeathTime(), false);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int mnuUse = 0;
        if (!hasObjVar(self, "fs_cs.primaryEscort"))
        {
            mnuUse = mi.addRootMenu(menu_info_types.SERVER_MENU1, new string_id("fs_quest_village", "camp_commander_capture"));
        }
        if (hasObjVar(self, "fs_cs.allowShieldKillerRecap"))
        {
            obj_id shieldKiller = getObjIdObjVar(self, fs_counterstrike.OBJVAR_CAMP_SHIELD_KILLER);
            if (!isIdValid(shieldKiller))
            {
                return SCRIPT_CONTINUE;
            }
            if (shieldKiller != player)
            {
                return SCRIPT_CONTINUE;
            }
            mnuUse = mi.addRootMenu(menu_info_types.SERVER_MENU2, new string_id("fs_quest_village", "fs_cs_recapture"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        sendDirtyObjectMenuNotification(player);
        if (item == menu_info_types.SERVER_MENU1)
        {
            if (hasObjVar(self, "fs_cs.primaryEscort"))
            {
                return SCRIPT_CONTINUE;
            }
            obj_id shieldKiller = getObjIdObjVar(self, fs_counterstrike.OBJVAR_CAMP_SHIELD_KILLER);
            if (!isIdValid(shieldKiller))
            {
                sendSystemMessage(player, new string_id("fs_quest_village", "camp_commander_no_auth"));
                return SCRIPT_CONTINUE;
            }
            if (shieldKiller != player && (!fs_counterstrike.arePlayersInSameGroup(shieldKiller, player) || !quests.isActive("fs_cs_intro", player)))
            {
                sendSystemMessage(player, new string_id("fs_quest_village", "camp_commander_no_auth"));
                return SCRIPT_CONTINUE;
            }
            dictionary d = new dictionary();
            d.put("capturedBy", player);
            d.put("commander", self);
            messageTo(shieldKiller, "msgCommanderCaptured", d, 0.0f, false);
            if (shieldKiller != player)
            {
                messageTo(player, "msgCommanderCaptured", d, 0.0f, false);
            }
            if (!hasObjVar(self, "fs_cs.helpCallPending"))
            {
                setObjVar(self, "fs_cs.helpCallPending", 1);
                messageTo(self, "msgSpawnRescuers", null, rand(HELP_SPAWN_TIME_MIN, HELP_SPAWN_TIME_MAX), false);
            }
            chat.chat(self, new string_id("fs_quest_village", "commander_death_notify"));
        }
        else if (item == menu_info_types.SERVER_MENU2)
        {
            if (!hasObjVar(self, "fs_cs.iAmFree"))
            {
                return SCRIPT_CONTINUE;
            }
            location here = getLocation(self);
            location userLoc = getLocation(player);
            if (here.distance(userLoc) > 10.0f)
            {
                sendSystemMessage(self, new string_id("fs_quest_village", "commander_too_far"));
                return SCRIPT_CONTINUE;
            }
            if (hasObjVar(self, "fs_cs.allowShieldKillerRecap"))
            {
                obj_id shieldKiller = getObjIdObjVar(self, fs_counterstrike.OBJVAR_CAMP_SHIELD_KILLER);
                if (!isIdValid(shieldKiller))
                {
                    return SCRIPT_CONTINUE;
                }
                if (shieldKiller != player)
                {
                    return SCRIPT_CONTINUE;
                }
                removeObjVar(self, "fs_cs.allowShieldKillerRecap");
                removeObjVar(self, "fs_cs.iAmFree");
                removeObjVar(self, "quests.supressFollowMenu");
                dictionary d = new dictionary();
                d.put("capturedBy", player);
                d.put("commander", self);
                messageTo(shieldKiller, "msgCommanderCaptured", d, 0.0f, false);
                if (!hasObjVar(self, "fs_cs.helpCallPending"))
                {
                    setObjVar(self, "fs_cs.helpCallPending", 1);
                    messageTo(self, "msgSpawnRescuers", null, rand(HELP_SPAWN_TIME_MIN, HELP_SPAWN_TIME_MAX), false);
                }
            }
        }
        sendDirtyObjectMenuNotification(player);
        return SCRIPT_CONTINUE;
    }
    public int msgPrimaryEscortDied(obj_id self, dictionary params) throws InterruptedException
    {
        ai_lib.aiStopFollowing(self);
        obj_id shieldKiller = getObjIdObjVar(self, fs_counterstrike.OBJVAR_CAMP_SHIELD_KILLER);
        if (!isIdValid(shieldKiller))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "fs_cs.primaryEscort"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id primaryEscort = getObjIdObjVar(self, "fs_cs.primaryEscort");
        setObjVar(self, "fs_cs.iAmFree", 1);
        setObjVar(self, "quests.supressFollowMenu", 1);
        if (primaryEscort != shieldKiller)
        {
            if (hasObjVar(self, "fs_cs.shieldKillerOusted"))
            {
                messageTo(self, "msgLifeExpired", null, 0.0f, false);
                return SCRIPT_CONTINUE;
            }
            messageTo(self, "msgRun", null, 0.0f, false);
            messageTo(self, "msgRanAwaySuccessfully", null, RUNAWAY_TIME_LIMIT, false);
            dictionary dsk = new dictionary();
            dsk.put("questFailure", false);
            setObjVar(self, "fs_cs.allowShieldKillerRecap", 1);
            messageTo(shieldKiller, "msgCommanderFree", dsk, 0.0f, false);
            dictionary ped = new dictionary();
            ped.put("questFailure", true);
            messageTo(primaryEscort, "msgCommanderFree", ped, 0.0f, false);
        }
        else 
        {
            messageTo(self, "msgLifeExpired", null, 0.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgShieldKillerOuster(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "fs_cs.shieldKillerOusted", 1);
        return SCRIPT_CONTINUE;
    }
    public int msgPrimaryEscortAbandoned(obj_id self, dictionary params) throws InterruptedException
    {
        ai_lib.aiStopFollowing(self);
        obj_id shieldKiller = getObjIdObjVar(self, fs_counterstrike.OBJVAR_CAMP_SHIELD_KILLER);
        if (!isIdValid(shieldKiller))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "fs_cs.primaryEscort"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id primaryEscort = getObjIdObjVar(self, "fs_cs.primaryEscort");
        setObjVar(self, "fs_cs.iAmFree", 1);
        setObjVar(self, "quests.supressFollowMenu", 1);
        if (primaryEscort != shieldKiller)
        {
            messageTo(self, "msgRun", null, 0.0f, false);
            messageTo(self, "msgRanAwaySuccessfully", null, RUNAWAY_TIME_LIMIT, false);
            dictionary dsk = new dictionary();
            dsk.put("questFailure", false);
            setObjVar(self, "fs_cs.allowShieldKillerRecap", 1);
            messageTo(shieldKiller, "msgCommanderFree", dsk, 0.0f, false);
        }
        else 
        {
            messageTo(self, "msgLifeExpired", null, 0.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgRanAwaySuccessfully(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "fs_cs.iAmFree"))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "msgLifeExpired", null, 0.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int msgRun(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "fs_cs.iAmFree"))
        {
            return SCRIPT_CONTINUE;
        }
        run();
        messageTo(self, "msgRun", null, RUN_PULSE_TIME, false);
        return SCRIPT_CONTINUE;
    }
    public void run() throws InterruptedException
    {
        LOG("fs_quest", "COMMANDER RUNNING");
        obj_id self = getSelf();
        location target = utils.getRandomAwayLocation(getLocation(self), MIN_DISTANCE_TO_RUN, MAX_DISTANCE_TO_RUN);
        setMovementRun(self);
        ai_lib.aiPathTo(self, target);
        return;
    }
}
