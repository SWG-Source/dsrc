package script.event.housepackup;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.cts;
import script.library.utils;

public class cts_greeter extends script.base_script
{
    public cts_greeter()
    {
    }
    public static final boolean BLOGGING_ON = true;
    public static final String BLOG_CATEGORY = "CharacterTransfer";
    public static final String STF_FILE = "veteran_new";
    public static final string_id SID_TRANSFER_TO_SPONSOR = new string_id(STF_FILE, "transfer_to_sponsor");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!utils.isNestedWithinAPlayer(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, cts.CURRENT_PLAYER_OID))
        {
            cts.initializeCtsObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTransferred(obj_id self, obj_id sourceContainer, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if (!isPlayer(getContainedBy(destContainer)))
        {
            return SCRIPT_CONTINUE;
        }
        if (destContainer == sourceContainer)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id containingPlayer = utils.getContainingPlayer(self);
        if (!isValidId(containingPlayer) || !exists(containingPlayer))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, cts.CURRENT_PLAYER_OID))
        {
            cts.initializeCtsObject(self);
        }
        else if (hasObjVar(self, cts.CURRENT_PLAYER_OID) && !hasObjVar(self, cts.SPONSOR_PLAYER_OID))
        {
            if (!utils.isNestedWithinAPlayer(self))
            {
                return SCRIPT_CONTINUE;
            }
            obj_id originalOwner = getObjIdObjVar(self, cts.CURRENT_PLAYER_OID);
            if (!isValidId(originalOwner))
            {
                return SCRIPT_CONTINUE;
            }
            if (containingPlayer == originalOwner)
            {
                return SCRIPT_CONTINUE;
            }
            if (!hasObjVar(self, cts.PREVIOUS_PLAYER_CLUSTER))
            {
                return SCRIPT_CONTINUE;
            }
            if (hasObjVar(self, cts.SPONSOR_PLAYER_OID))
            {
                return SCRIPT_CONTINUE;
            }
            setObjVar(self, cts.SPONSOR_PLAYER_OID, containingPlayer);
            setObjVar(self, cts.SPONSOR_PLAYER_FULL_NAME, getPlayerFullName(containingPlayer));
            setObjVar(self, cts.SPONSOR_PLAYER_CLUSTER_NAME, getClusterName());
            setObjVar(self, cts.SPONSOR_PLAYER_TIME, getCalendarTime());
            CustomerServiceLog("CharacterTransfer", "Successful stamping of sponsor data onto RESTRICTED CTS Gift Box: " + self + " " + getName(self) + " to player: " + containingPlayer + " " + getName(containingPlayer) + " from CTS Player and original owner: " + getObjIdObjVar(self, cts.CURRENT_PLAYER_OID) + " " + getName(getObjIdObjVar(self, cts.CURRENT_PLAYER_OID)));
        }
        if (hasObjVar(self, cts.CURRENT_PLAYER_OID) && hasObjVar(self, cts.SPONSOR_PLAYER_OID))
        {
            if (!utils.isNestedWithinAPlayer(self))
            {
                return SCRIPT_CONTINUE;
            }
            obj_id sponsorOid = getObjIdObjVar(self, cts.SPONSOR_PLAYER_OID);
            if (!isValidId(sponsorOid))
            {
                return SCRIPT_CONTINUE;
            }
            if (!hasObjVar(self, cts.CTS_PAINTING_OBJVAR) || !hasObjVar(self, cts.CTS_UNRESTRICTED_GIFT_BOX_OBJVAR) || !hasObjVar(self, cts.DATETIME_GIFT_TRANSFER) || !hasObjVar(self, cts.CURRENT_PLAYER_NAME) || !hasObjVar(self, cts.DATETIME_GIFT_TRANSFER) || !hasObjVar(self, cts.PREVIOUS_PLAYER_CLUSTER) || !hasObjVar(self, cts.PREVIOUS_PLAYER_NAME) || !hasObjVar(self, cts.CURRENT_PLAYER_CLUSTER))
            {
                return SCRIPT_CONTINUE;
            }
            if (!hasObjVar(self, cts.SPONSOR_PLAYER_FULL_NAME) || !hasObjVar(self, cts.SPONSOR_PLAYER_CLUSTER_NAME) || !hasObjVar(self, cts.SPONSOR_PLAYER_TIME))
            {
                return SCRIPT_CONTINUE;
            }
            String unrestrictedTemplate = getStringObjVar(self, cts.CTS_UNRESTRICTED_GIFT_BOX_OBJVAR);
            if (unrestrictedTemplate == null || unrestrictedTemplate.equals(""))
            {
                return SCRIPT_CONTINUE;
            }
            String paintingTemplate = getStringObjVar(self, cts.CTS_PAINTING_OBJVAR);
            if (paintingTemplate == null || paintingTemplate.equals(""))
            {
                return SCRIPT_CONTINUE;
            }
            obj_id originalCtsPlayer = getObjIdObjVar(self, cts.CURRENT_PLAYER_OID);
            if (!isValidId(originalCtsPlayer))
            {
                return SCRIPT_CONTINUE;
            }
            int refugeeTimeTransfer = getIntObjVar(self, cts.DATETIME_GIFT_TRANSFER);
            if (refugeeTimeTransfer <= 0)
            {
                return SCRIPT_CONTINUE;
            }
            String refugeePrevGalaxy = getStringObjVar(self, cts.PREVIOUS_PLAYER_CLUSTER);
            if (refugeePrevGalaxy == null || refugeePrevGalaxy.equals(""))
            {
                return SCRIPT_CONTINUE;
            }
            String refugeePrevName = getStringObjVar(self, cts.PREVIOUS_PLAYER_NAME);
            if (refugeePrevName == null || refugeePrevName.equals(""))
            {
                return SCRIPT_CONTINUE;
            }
            String refugeeCurrentGalaxy = getStringObjVar(self, cts.CURRENT_PLAYER_CLUSTER);
            if (refugeeCurrentGalaxy == null || refugeeCurrentGalaxy.equals(""))
            {
                return SCRIPT_CONTINUE;
            }
            String refugeeName = getStringObjVar(self, cts.CURRENT_PLAYER_NAME);
            if (refugeeName == null || refugeeName.equals(""))
            {
                return SCRIPT_CONTINUE;
            }
            String playerFullName = getStringObjVar(self, cts.SPONSOR_PLAYER_FULL_NAME);
            if (playerFullName == null || playerFullName.equals(""))
            {
                return SCRIPT_CONTINUE;
            }
            String sponsorClusterName = getStringObjVar(self, cts.SPONSOR_PLAYER_CLUSTER_NAME);
            if (sponsorClusterName == null || sponsorClusterName.equals(""))
            {
                return SCRIPT_CONTINUE;
            }
            int giftTime = getIntObjVar(self, cts.SPONSOR_PLAYER_TIME);
            if (refugeeTimeTransfer <= 0)
            {
                return SCRIPT_CONTINUE;
            }
            obj_id unrestrictedGiftBox = createObjectInInventoryAllowOverload(unrestrictedTemplate, sponsorOid);
            if (!isValidId(unrestrictedGiftBox) || !exists(unrestrictedGiftBox))
            {
                return SCRIPT_CONTINUE;
            }
            setObjVar(unrestrictedGiftBox, cts.CTS_PAINTING_OBJVAR, paintingTemplate);
            setObjVar(unrestrictedGiftBox, cts.CURRENT_PLAYER_OID, originalCtsPlayer);
            setObjVar(unrestrictedGiftBox, cts.DATETIME_GIFT_TRANSFER, refugeeTimeTransfer);
            setObjVar(unrestrictedGiftBox, cts.PREVIOUS_PLAYER_CLUSTER, refugeePrevGalaxy);
            setObjVar(unrestrictedGiftBox, cts.PREVIOUS_PLAYER_NAME, refugeePrevName);
            setObjVar(unrestrictedGiftBox, cts.CURRENT_PLAYER_CLUSTER, refugeeCurrentGalaxy);
            setObjVar(unrestrictedGiftBox, cts.CURRENT_PLAYER_NAME, refugeeName);
            setObjVar(unrestrictedGiftBox, cts.SPONSOR_PLAYER_OID, sponsorOid);
            setObjVar(unrestrictedGiftBox, cts.SPONSOR_PLAYER_FULL_NAME, playerFullName);
            setObjVar(unrestrictedGiftBox, cts.SPONSOR_PLAYER_CLUSTER_NAME, sponsorClusterName);
            setObjVar(unrestrictedGiftBox, cts.SPONSOR_PLAYER_TIME, giftTime);
            CustomerServiceLog("CharacterTransfer", "Successful creation and transfer of sponsor data from RESTRICTED to UNRESTRICTED" + " Gift Box: " + unrestrictedGiftBox + " " + getName(unrestrictedGiftBox) + " to player: " + sponsorOid + " " + getName(sponsorOid) + " from CTS Player and original owner: " + originalCtsPlayer + " " + getName(originalCtsPlayer));
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if (!isPlayer(getContainedBy(destContainer)))
        {
            blog("OnAboutToBeTransferred destination container not a player");
            sendSystemMessage(transferer, SID_TRANSFER_TO_SPONSOR);
            return SCRIPT_OVERRIDE;
        }
        obj_id destPlayer = utils.getContainingPlayer(destContainer);
        obj_id destPlayerInv = utils.getInventoryContainer(destPlayer);
        if (destContainer != destPlayerInv)
        {
            blog("OnAboutToBeTransferred destination container not a player's top level inventory");
            sendSystemMessage(transferer, SID_TRANSFER_TO_SPONSOR);
            return SCRIPT_OVERRIDE;
        }
        blog("OnAboutToBeTransferred transfer success");
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (!isValidId(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        String inscriptionString = cts.getCtsInscription(self);
        if (inscriptionString != null && !inscriptionString.equals(""))
        {
            names[idx] = "inscription";
            attribs[idx] = inscriptionString;
            idx++;
        }
        return SCRIPT_CONTINUE;
    }
    public boolean blog(String msg) throws InterruptedException
    {
        if (BLOGGING_ON)
        {
            LOG(BLOG_CATEGORY, msg);
        }
        return true;
    }
}
