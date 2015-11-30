package script.corpse;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.permissions;
import script.library.corpse;
import script.library.spam;
import script.library.money;

public class player_corpse extends script.base_script
{
    public player_corpse()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.LOOT);
        if (mid != null)
        {
            mid.setServerNotify(true);
        }
        mid = mi.getMenuItemByType(menu_info_types.LOOT_ALL);
        if (mid != null)
        {
            mid.setServerNotify(true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.LOOT)
        {
            if (utils.isOwner(self, player))
            {
                utils.requestContainerOpen(player, self);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                sendSystemMessage(player, permissions.SID_NO_CORPSE_PERMISSION);
            }
        }
        else if (item == menu_info_types.LOOT_ALL)
        {
            if (utils.isOwner(self, player))
            {
                utils.requestContainerOpen(player, self);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                sendSystemMessage(player, permissions.SID_NO_CORPSE_PERMISSION);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        corpse.cleanUpPlayerCorpse(self, false);
        String corpseName = getAssignedName(self);
        location here = getLocation(self);
        CustomerServiceLog("Death", "PCorpse(" + self + " - " + corpseName + ") is being deleted at " + here.toString());
        CustomerServiceLog("Death", "PCorpse(" + self + " - " + corpseName + ") owner = " + getOwner(self));
        return SCRIPT_CONTINUE;
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
        if (isPlayer(transferer))
        {
            if (!utils.isOwner(self, transferer) && !isGod(transferer))
            {
                sendSystemMessage(transferer, permissions.SID_INSUFFICIENT_PERMISSIONS);
                return SCRIPT_OVERRIDE;
            }
            else 
            {
                CustomerServiceLog("Death", "(" + transferer + ") " + getName(transferer) + " is removing (" + item + ") " + getName(item) + " from (" + self + ")" + getName(self));
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
            CustomerServiceLog("Death", "(" + transferer + ") " + getName(transferer) + " has transferred (" + item + ") " + getName(item) + " from (" + self + ")" + getName(self) + " to " + destContainer);
            CustomerServiceLog("Loot", "(" + transferer + ") " + getName(transferer) + " has transferred (" + item + ") " + getName(item) + " from (" + self + ")" + getName(self) + " to " + destContainer);
            spam.lootItem(transferer, item, self, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnOpenedContainer(obj_id self, obj_id whoOpenedMe) throws InterruptedException
    {
        if (utils.isOwner(self, whoOpenedMe))
        {
            if (corpse.openPlayerCorpse(whoOpenedMe, self))
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_OVERRIDE;
    }
    public int OnClosedContainer(obj_id self, obj_id whoClosedMe) throws InterruptedException
    {
        obj_id[] itemsLeft = getContents(self);
        if ((itemsLeft == null) || (itemsLeft.length == 0))
        {
            if (getCashBalance(self) > 0)
            {
                corpse.lootCorpseCoins(whoClosedMe, self);
            }
            else 
            {
                corpse.cleanUpPlayerCorpse(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        obj_id[] itemsLeft = getContents(self);
        if ((itemsLeft == null) || (itemsLeft.length == 0))
        {
            if (getTotalMoney(self) == 0)
            {
                destroyObject(self);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleCorpseExpire(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, corpse.VAR_TIME_CREATED))
        {
            int create_time = getIntObjVar(self, corpse.VAR_TIME_CREATED);
            int dt = getGameTime() - create_time;
            if (dt < corpse.PLAYER_CORPSE_EXPIRATION_TIME)
            {
                int msgTime = corpse.PLAYER_CORPSE_EXPIRATION_TIME - dt;
                messageTo(self, corpse.HANDLER_CORPSE_EXPIRE, params, msgTime, true);
                return SCRIPT_CONTINUE;
            }
        }
        obj_id owner = getObjIdObjVar(self, utils.VAR_OWNER);
        if (owner != null)
        {
            dictionary d = new dictionary();
            d.put(corpse.DICT_CORPSE_ID, self);
            messageTo(owner, corpse.HANDLER_CORPSE_EXPIRE, d, 0, true);
        }
        corpse.cleanUpPlayerCorpse(self);
        return SCRIPT_CONTINUE;
    }
    public int handleAddConsentedUser(obj_id self, dictionary params) throws InterruptedException
    {
        if (params != null)
        {
            obj_id player = params.getObjId(corpse.DICT_PLAYER_ID);
            if (player != null)
            {
                if (permissions.addUserToPermissionsGroup(self, player, corpse.GROUP_CONSENTED))
                {
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                }
            }
        }
        return SCRIPT_OVERRIDE;
    }
    public int handleRemoveConsentedUser(obj_id self, dictionary params) throws InterruptedException
    {
        if (params != null)
        {
            obj_id player = params.getObjId(corpse.DICT_PLAYER_ID);
            if (player != null)
            {
                if (permissions.deleteUserFromPermissionsGroup(self, player, corpse.GROUP_CONSENTED))
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_OVERRIDE;
    }
    public int handleCorpseDepositSuccess(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_OVERRIDE;
        }
        int cash = params.getInt(corpse.DICT_COINS);
        transferBankCreditsToNamedAccount(self, money.ACCT_CORPSE_EXPIRATION, cash, corpse.HANDLER_DESTROY_SELF, corpse.HANDLER_DESTROY_SELF, params);
        utils.moneyOutMetric(self, money.ACCT_CORPSE_EXPIRATION, cash);
        return SCRIPT_CONTINUE;
    }
    public int handleCorpseDepositFail(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int handleAttemptCorpseCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] stuff = getContents(self);
        if ((stuff == null) || (stuff.length == 0) && (getCashBalance(self) == 0))
        {
            corpse.cleanUpPlayerCorpse(self);
        }
        else 
        {
            int stamp = 0;
            if (hasObjVar(self, "cleanupStamp"))
            {
                stamp = getIntObjVar(self, "cleanupStamp");
            }
            int now = getGameTime();
            if (now - stamp >= 300)
            {
                messageTo(self, "handleAttemptCorpseCleanup", null, 300, true);
                setObjVar(self, "cleanupStamp", now);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleDestroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int handleRequestCorpseMove(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id sender = params.getObjId("sender");
        if (!isIdValid(sender))
        {
            return SCRIPT_CONTINUE;
        }
        location spot = getBuildingEjectLocation(sender);
        if (spot == null)
        {
            spot = params.getLocation("senderLoc");
            location locLowerLeft = spot;
            locLowerLeft.x -= 20f;
            locLowerLeft.z -= 20f;
            location locUpperRight = spot;
            locUpperRight.x += 20f;
            locUpperRight.z += 20f;
            spot = getGoodLocation(2f, 2f, locLowerLeft, locUpperRight, false, true);
            if (spot == null)
            {
                return SCRIPT_CONTINUE;
            }
            spot.y = getHeightAtLocation(spot.x, spot.z);
        }
        setLocation(self, spot);
        corpse.updateCorpseOwnerWaypoint(getOwner(self), self);
        return SCRIPT_CONTINUE;
    }
    public int handleRequestLocation(obj_id self, dictionary params) throws InterruptedException
    {
        corpse.updateCorpseOwnerWaypoint(getOwner(self), self);
        return SCRIPT_CONTINUE;
    }
}
