package script.systems.storyteller;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.prose;
import script.library.static_item;
import script.library.storyteller;
import script.library.create;
import script.library.trial;

public class npc_difficulty_token extends script.base_script
{
    public npc_difficulty_token()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        string_id eliteConversionPrompt = new string_id("storyteller", "deploy_" + getTokenDifficultyString(self) + "_maker");
        mi.addRootMenu(menu_info_types.ITEM_USE, eliteConversionPrompt);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            sendSystemMessage(player, new string_id("storyteller", "placement_from_inventory_only"));
            return SCRIPT_CONTINUE;
        }
        if (isFreeTrialAccount(player))
        {
            sendSystemMessage(player, new string_id("storyteller", "placement_no_trial_accounts"));
            return SCRIPT_CONTINUE;
        }
        obj_id target = getStorytellerTokenTarget(player);
        if (item == menu_info_types.ITEM_USE)
        {
            if (isIdValid(target))
            {
                if (isTargetMyStorytellerObject(player, target))
                {
                    String creatureName = getCreatureName(target);
                    dictionary creatureDict = utils.dataTableGetRow(create.CREATURE_TABLE, creatureName);
                    if (creatureDict != null)
                    {
                        creatureDict.put("difficultyClass", getTokenDifficulty(self));
                        int level = getLevel(target);
                        create.initializeCreature(target, creatureName, creatureDict, level);
                        setNpcDifficulty(target, getTokenDifficulty(self));
                        string_id message = new string_id("storyteller", "npc_difficulty_made_" + getTokenDifficultyString(self));
                        prose_package pp = prose.getPackage(message, player, player);
                        prose.setTO(pp, utils.unpackString(getName(target)));
                        sendSystemMessageProse(player, pp);
                        storyteller.handleTokenUsage(self);
                        String logMsg = "(" + player + ")" + getName(player) + " is converting a storyteller npc into a(an)" + getTokenDifficultyString(self) + " npc: " + getStaticItemName(self) + " on " + target;
                        CustomerServiceLog("storyteller", logMsg);
                    }
                }
            }
            else 
            {
                sendSystemMessage(player, new string_id("storyteller", "invalid_target"));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public obj_id getStorytellerTokenTarget(obj_id player) throws InterruptedException
    {
        obj_id target = null;
        obj_id intendedTarget = getIntendedTarget(player);
        if (isIdValid(intendedTarget))
        {
            target = intendedTarget;
        }
        else 
        {
            target = getLookAtTarget(player);
        }
        return target;
    }
    public int getTokenDifficulty(obj_id token) throws InterruptedException
    {
        String staticName = getStaticItemName(token);
        int difficulty = 0;
        if (staticName.equals("st_o_elite_maker"))
        {
            difficulty = 1;
        }
        else if (staticName.equals("st_o_boss_maker"))
        {
            difficulty = 2;
        }
        return difficulty;
    }
    public String getTokenDifficultyString(obj_id token) throws InterruptedException
    {
        String staticName = getStaticItemName(token);
        String difficulty = "normal";
        if (staticName != null && staticName.equals("st_o_elite_maker"))
        {
            difficulty = "elite";
        }
        else if (staticName.equals("st_o_boss_maker"))
        {
            difficulty = "boss";
        }
        return difficulty;
    }
    public boolean isTargetMyStorytellerObject(obj_id player, obj_id target) throws InterruptedException
    {
        if (storyteller.isAnyStorytellerItem(target))
        {
            if (hasObjVar(target, "storytellerid"))
            {
                obj_id storytellerId = getObjIdObjVar(target, "storytellerid");
                if (storytellerId == player || storytellerId == utils.getObjIdScriptVar(player, "storytellerAssistant"))
                {
                    if (isMob(target) && !isPlayer(target) && !isInvulnerable(target))
                    {
                        return true;
                    }
                    else 
                    {
                        sendSystemMessage(player, new string_id("storyteller", "npc_difficulty_on_npc_only"));
                        return false;
                    }
                }
            }
        }
        sendSystemMessage(player, new string_id("storyteller", "converter_cannot_use"));
        return false;
    }
}
