package script.ai;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.beast_lib;
import script.library.colors;
import script.library.factions;

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
        obj_id target = getTarget(self);
        if (!isIdValid(target) || !exists(target))
        {
            return false;
        }
        String type = getStringObjVar(self, "creature_type");
        dictionary aiData = dataTableGetRow("datatables/mob/creatures.iff", type);
        float assistRange = aiData.getFloat("assist");
        String socialGroup = aiData.getString("socialGroup");
        String pvpFaction = aiData.getString("pvpFaction");
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
        for (int i = 0; i < creatures.length; i++)
        {
            if (creatures[i] == self)
            {
                continue;
            }
            if (!isIdValid(creatures[i]) || !exists(creatures[i]))
            {
                continue;
            }
            if (isInvulnerable(creatures[i]))
            {
                continue;
            }
            if (isPlayer(target) && factions.ignorePlayer(target, creatures[i]))
            {
                continue;
            }
            if (isDead(creatures[i]))
            {
                continue;
            }
            if (isIdValid(getMaster(creatures[i])))
            {
                continue;
            }
            if (!canSee(self, creatures[i]))
            {
                continue;
            }
            if (!factions.shareSocialGroup(self, creatures[i]) && !factions.areCreaturesAllied(self, creatures[i]))
            {
                continue;
            }
            if (Math.abs(getLocation(self).y - getLocation(creatures[i]).y) > 6.0f)
            {
                continue;
            }
            callToList.add(creatures[i]);
        }
        if (callToList == null || callToList.size() == 0)
        {
            return false;
        }
        for (int q = 0; q < callToList.size(); q++)
        {
            startAssistedCombat(((obj_id)callToList.get(q)), target);
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
