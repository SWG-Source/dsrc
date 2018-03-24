package script.ai;

import script.deltadictionary;
import script.dictionary;
import script.library.ai_lib;
import script.library.beast_lib;
import script.library.factions;
import script.obj_id;

import java.util.Vector;

public class ai_combat_assist extends script.base_script
{
    public ai_combat_assist()
    {
    }
    public static void clearAssist() throws InterruptedException
    {
        final obj_id self = getSelf();
        deltadictionary dict = self.getScriptVars();
        dict.remove("ai.combat.assist");
    }
    public static void clearNoAssistCall() throws InterruptedException
    {
        final obj_id self = getSelf();
        deltadictionary dict = self.getScriptVars();
        dict.remove("ai.combat.assist.noAssistCall");
    }
    public static boolean isWaiting() throws InterruptedException
    {
        final obj_id self = getSelf();
        deltadictionary dict = self.getScriptVars();
        return dict.getBoolean("ai.combat.assist");
    }
    public static int getWaitTime() throws InterruptedException
    {
        final obj_id self = getSelf();
        deltadictionary dict = self.getScriptVars();
        return dict.getInt("ai.combat.assist.waitTime");
    }
    public static void assist(obj_id defender, obj_id target) throws InterruptedException
    {
    }
    public static boolean canCallForAssist() throws InterruptedException
    {
        final obj_id self = getSelf();
        deltadictionary dict = self.getScriptVars();
        return !dict.getBoolean("ai.combat.assist.noAssistCall");
    }
    public static boolean callForAssist() throws InterruptedException
    {
        obj_id self = getSelf();
        if (beast_lib.isBeast(self))
        {
            return false;
        }
        if (hasObjVar(self, "hologram_performer"))
        {
            return false;
        }
        obj_id target = getHateTarget(self);
        if (!isIdValid(target) || !exists(target))
        {
            return false;
        }
        String type = getStringObjVar(self, "creature_type");
        dictionary aiData = dataTableGetRow("datatables/mob/creatures.iff", type);
        if(aiData == null){
            LOG("DESIGNER_FATAL", "Creature Type (" + type + ") not found in creatures table (datatables/mob/creatures.iff)!!");
            return false;
        }
        float assistRange = aiData.getFloat("assist");
        if (assistRange == 0.0f)
        {
            return false;
        }
        obj_id[] creatures = getNPCsInRange(self, assistRange);
        if (creatures == null || creatures.length == 0)
        {
            return false;
        }
        Vector callToList = new Vector();
        callToList.setSize(0);
        for (obj_id creature : creatures) {
            if (creature.equals(self) || creature.equals(target)) {
                continue;
            }
            if (!isIdValid(creature) || !exists(creature)) {
                continue;
            }
            if (isInvulnerable(creature)) {
                continue;
            }
            if (isPlayer(target) && factions.ignorePlayer(target, creature)) {
                continue;
            }
            if (isDead(creature)) {
                continue;
            }
            if (isIdValid(getMaster(creature))) {
                continue;
            }
            if (!canSee(self, creature)) {
                continue;
            }
            if (!factions.shareSocialGroup(self, creature) && !factions.areCreaturesAllied(self, creature)) {
                continue;
            }
            if (Math.abs(getLocation(self).y - getLocation(creature).y) > 6.0f) {
                continue;
            }
            if(creature.equals(target)){
                continue;
            }
            callToList.add(creature);
        }
        if (callToList.size() == 0)
        {
            return false;
        }
        for (Object creature : callToList) {
            startAssistedCombat((obj_id) creature, target);
        }
        return true;
    }
    public static void startAssistedCombat(obj_id creature, obj_id target) throws InterruptedException
    {
        deltadictionary dict = creature.getScriptVars();
        if (!ai_lib.isInCombat(creature))
        {
            faceTo(creature, target);
            dict.put("ai.combat.assist.waitTime", isPlayer(target) ? rand(1.0f, 2.0f) : rand(3.0f, 4.0f));
            dict.put("ai.combat.assist", true);
            dict.put("ai.combat.assist.noAssistCall", true);
        }
        startCombat(creature, target);
    }
}
