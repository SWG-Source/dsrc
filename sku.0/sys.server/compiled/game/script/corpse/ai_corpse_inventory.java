package script.corpse;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.prose;
import script.library.spam;
import script.library.utils;
import script.library.group;
import script.library.corpse;
import script.library.permissions;
import script.library.loot;

public class ai_corpse_inventory extends script.base_script
{
    public ai_corpse_inventory()
    {
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (isPlayer(transferer))
        {
            sendSystemMessage(transferer, corpse.SID_REMOVE_ONLY_CORPSE);
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToLoseItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        obj_id lootingPlayer = transferer;
        if (!isIdValid(lootingPlayer) || !isPlayer(lootingPlayer))
        {
            lootingPlayer = getContainedBy(destContainer);
        }
        if (isPlayer(lootingPlayer))
        {
            obj_id corpseId = getContainedBy(self);
            if (corpseId == null)
            {
                return SCRIPT_CONTINUE;
            }
            if (hasObjVar(item, "pickupable"))
            {
                obj_id winningPlayer = getObjIdObjVar(item, "pickupable");
                if (winningPlayer == lootingPlayer)
                {
                    removeObjVar(item, "unpickable");
                    return SCRIPT_CONTINUE;
                }
                string_id noLoot = new string_id(group.GROUP_STF, "no_loot_permission");
                sendSystemMessage(lootingPlayer, noLoot);
                return SCRIPT_OVERRIDE;
            }
            if (corpse.hasLootPermissions(corpseId, lootingPlayer))
            {
                if (loot.isCashLootItem(item))
                {
                    int cash = getIntObjVar(item, "loot.cashAmount");
                    dictionary data = new dictionary();
                    data.put(corpse.DICT_COINS, cash);
                    data.put(corpse.DICT_CORPSE_ID, corpseId);
                    messageTo(lootingPlayer, "handleRequestCoinLoot", data, 0, false);
                    destroyObject(item);
                    return SCRIPT_OVERRIDE;
                }
                if (group.isGrouped(lootingPlayer))
                {
                    obj_id team = getGroupObject(lootingPlayer);
                    int lootRule = getGroupLootRule(team);
                    if (lootRule == 3)
                    {
                        lootingPlayer = loot.chooseRandomLootPlayerFromGroup(self, lootingPlayer);
                    }
                    else if (lootRule == 1)
                    {
                        if (!loot.isMasterLooter(lootingPlayer, true))
                        {
                            return SCRIPT_OVERRIDE;
                        }
                    }
                    if (loot.doGroupLooting(corpseId, lootingPlayer, item))
                    {
                        if (canPutIn(item, utils.getInventoryContainer(lootingPlayer)) != 0)
                        {
                            loot.sendGroupLootSystemMessage(item, lootingPlayer, group.GROUP_STF, "full_inventory");
                            return SCRIPT_OVERRIDE;
                        }
                    }
                    else 
                    {
                        return SCRIPT_OVERRIDE;
                    }
                }
                CustomerServiceLog("Loot", "(" + lootingPlayer + ") " + getName(lootingPlayer) + " is attempting to remove (" + item + ") " + getName(item) + " from (" + self + ")" + getName(self) + " to " + destContainer);
            }
            else 
            {
                sendSystemMessage(lootingPlayer, permissions.SID_INSUFFICIENT_PERMISSIONS);
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnLostItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (!isIdValid(transferer))
        {
            return SCRIPT_CONTINUE;
        }
        if (isPlayer(transferer))
        {
            CustomerServiceLog("Loot", "(" + transferer + ") " + getName(transferer) + " has transferred (" + item + ") " + getName(item) + " from (" + self + ")" + getName(self) + " to " + destContainer);
            obj_id gid = getGroupObject(transferer);
            if (isIdValid(gid))
            {
                group.notifyItemLoot(gid, transferer, getContainedBy(self), item);
                return SCRIPT_CONTINUE;
            }
            prose_package pp = new prose_package();
            string_id lootMsg = new string_id("loot_n", "solo_looted");
            pp = prose.setStringId(pp, lootMsg);
            pp = prose.setTO(pp, item);
            sendSystemMessageProse(transferer, pp);
        }
        obj_id[] itemsLeft = getContents(self);
        if ((itemsLeft == null) || (itemsLeft.length == 0))
        {
            obj_id corpseId = getContainedBy(self);
            if (isIdValid(corpseId))
            {
                corpse.clearLootMeParticle(corpseId);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnOpenedContainer(obj_id self, obj_id whoOpenedMe) throws InterruptedException
    {
        obj_id corpseId = getContainedBy(self);
        if (corpseId == null)
        {
            return SCRIPT_OVERRIDE;
        }
        if (corpse.hasLootPermissions(corpseId, whoOpenedMe))
        {
            corpse.openAICorpse(whoOpenedMe, corpseId);
            int cnt = utils.getIntScriptVar(self, "openCount");
            cnt++;
            utils.setScriptVar(self, "openCount", cnt);
            utils.setScriptVar(corpseId, "lootTimestamp", getGameTime());
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_OVERRIDE;
    }
    public int OnClosedContainer(obj_id self, obj_id whoClosedMe) throws InterruptedException
    {
        int cnt = utils.getIntScriptVar(self, "openCount");
        cnt--;
        if (cnt > 0)
        {
            utils.setScriptVar(self, "openCount", cnt);
            return SCRIPT_CONTINUE;
        }
        obj_id[] itemsLeft = getContents(self);
        if ((itemsLeft == null) || (itemsLeft.length == 0))
        {
            obj_id corpseId = getContainedBy(self);
            if (corpseId == null)
            {
                return SCRIPT_CONTINUE;
            }
            if (!hasObjVar(self, corpse.VAR_HAS_RESOURCE))
            {
                messageTo(corpseId, corpse.HANDLER_CORPSE_EMPTY, null, 0, true);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
