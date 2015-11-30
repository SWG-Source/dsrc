package script.theme_park.dungeon.death_watch_bunker;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;
import script.library.utils;
import script.library.ai_lib;
import script.library.utils;
import script.library.chat;
import script.library.buff;
import script.library.anims;
import script.ai.ai_combat;

public class crazed_miner extends script.base_script
{
    public crazed_miner()
    {
    }
    public static final String FACETO_VOLUME_NAME = "faceToTriggerVolume";
    public static final string_id HELP_ME = new string_id("dungeon/death_watch", "help_me");
    public static final string_id RECOVERED_BATTERY = new string_id("dungeon/death_watch", "recovered_battery");
    public static final string_id HALDO_FAILED = new string_id("dungeon/death_watch", "haldo_failed");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setAnimationMood(self, "nervous");
        setName(self, "Haldo (a crazed miner)");
        messageTo(self, "handleHaldoTimeOut", null, 1800f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id wpn, int[] damage) throws InterruptedException
    {
        if (hasObjVar(self, "death_watch.already_spoke"))
        {
            return SCRIPT_CONTINUE;
        }
        setInvulnerable(self, true);
        messageTo(self, "makeCrazedMinerInvulnerable", null, 1, false);
        createTriggerVolume(FACETO_VOLUME_NAME, 8.0f, true);
        chat.chat(self, HELP_ME);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id attacker) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        obj_id player = getObjIdObjVar(self, "death_watch.noHelp");
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id playerInv = getObjectInSlot(player, "inventory");
        if (isIdValid(playerInv))
        {
            obj_id item = createObject("object/tangible/dungeon/death_watch_bunker/drill_battery.iff", playerInv, "");
            if (isIdValid(item))
            {
                sendSystemMessage(player, RECOVERED_BATTERY);
                setObjVar(player, "death_watch.medicine_kill", 1);
                removeObjVar(player, "death_watch.medicine");
                removeObjVar(structure, "death_watch.haldo");
                removeObjVar(structure, "death_watch.haldo_player");
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleCrazedCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        obj_id player = getObjIdObjVar(structure, "death_watch.haldo_player");
        removeObjVar(structure, "death_watch.haldo");
        removeObjVar(structure, "death_watch.haldo_player");
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int handleCrazedAttack(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        detachScript(self, "conversation.death_watch_insane_miner");
        clearCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, false);
        setObjVar(self, "death_watch.already_spoke", 1);
        startCombat(self, player);
        return SCRIPT_CONTINUE;
    }
    public int handleHaldoTimeOut(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        obj_id player = getObjIdObjVar(structure, "death_watch.haldo_player");
        if (isIdValid(player))
        {
            sendSystemMessage(player, HALDO_FAILED);
        }
        removeObjVar(structure, "death_watch.haldo");
        removeObjVar(structure, "death_watch.haldo_player");
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int makeCrazedMinerInvulnerable(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, "ai.combat.isInCombat");
        setCombatTarget(self, null);
        stopCombat(self);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        buff.removeAllBuffs(self);
        clearHateList(self);
        attachScript(self, "conversation.death_watch_insane_miner");
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
}
