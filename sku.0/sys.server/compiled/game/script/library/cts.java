package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class cts extends script.base_script
{
    public cts()
    {
    }
    public static final boolean BLOGGING_ON = true;
    public static final String BLOG_CATEGORY = "CharacterTransfer";
    public static final String CTS_GIFT_TEMPLATE = "object/tangible/furniture/house_cleanup/cts_";
    public static final String CTS_GIFT_BOX = "box.iff";
    public static final String REFUGEE_OBJVAR_PREFIX = "cts_reward";
    public static final String PREVIOUS_PLAYER_NAME = REFUGEE_OBJVAR_PREFIX + ".player_previous_name";
    public static final String PREVIOUS_PLAYER_CLUSTER = REFUGEE_OBJVAR_PREFIX + ".player_previous_cluster";
    public static final String DATETIME_GIFT_TRANSFER = REFUGEE_OBJVAR_PREFIX + ".player_date_time_claimed";
    public static final String CURRENT_PLAYER_NAME = REFUGEE_OBJVAR_PREFIX + ".player_current_name";
    public static final String CURRENT_PLAYER_CLUSTER = REFUGEE_OBJVAR_PREFIX + ".player_current_cluster";
    public static final String CURRENT_PLAYER_OID = REFUGEE_OBJVAR_PREFIX + ".player_current_oid";
    public static final String SPONSOR_PLAYER_OID = REFUGEE_OBJVAR_PREFIX + ".sponsor_current_oid";
    public static final String SPONSOR_PLAYER_FULL_NAME = REFUGEE_OBJVAR_PREFIX + ".sponsor_current_name";
    public static final String SPONSOR_PLAYER_CLUSTER_NAME = REFUGEE_OBJVAR_PREFIX + ".sponsor_current_cluster";
    public static final String SPONSOR_PLAYER_TIME = REFUGEE_OBJVAR_PREFIX + ".sponsor_current_time";
    public static final String CTS_PAINTING_OBJVAR = "cts.greeter.painting";
    public static final String CTS_UNRESTRICTED_GIFT_BOX_OBJVAR = "cts.greeter.unrestricted_box";
    public static final String SCRIPTVAR_CTS_ITEM_ID = "ctsItemId";
    public static boolean grantPackagedPainting(obj_id sponsorGiftBox, obj_id anyPlayer) throws InterruptedException
    {
        blog("grantPackagedPainting init");
        if (!isValidId(sponsorGiftBox) || !exists(sponsorGiftBox))
        {
            return false;
        }
        if (!isValidId(anyPlayer) || !exists(anyPlayer))
        {
            return false;
        }
        blog("grantPackagedPainting initial validation complete");
        if (!hasObjVar(sponsorGiftBox, CTS_PAINTING_OBJVAR))
        {
            CustomerServiceLog("CharacterTransfer", "No Painting ObjVar on Gift - CTS Sponsor Gift unpacked failed for player: " + anyPlayer + " " + getName(anyPlayer) + ". Offending object: " + sponsorGiftBox + " failed to spawn painting because no painting template found.");
            return false;
        }
        String paintingTemplate = getStringObjVar(sponsorGiftBox, CTS_PAINTING_OBJVAR);
        if (paintingTemplate == null || paintingTemplate.equals(""))
        {
            CustomerServiceLog("CharacterTransfer", "Painting ObjVar Null - CTS Sponsor Gift unpacked failed for player: " + anyPlayer + " " + getName(anyPlayer) + ". Offending object: " + sponsorGiftBox + " failed to spawn retrive painting string: " + paintingTemplate);
            return false;
        }
        if (!hasObjVar(sponsorGiftBox, cts.CURRENT_PLAYER_OID))
        {
            CustomerServiceLog("CharacterTransfer", "Original CTS Player OID not found - CTS Sponsor Gift unpacked failed for player: " + anyPlayer + " " + getName(anyPlayer) + ". Offending object: " + sponsorGiftBox + ". Did not have original owner refugee OID on box object");
            return false;
        }
        if (!hasObjVar(sponsorGiftBox, cts.SPONSOR_PLAYER_OID))
        {
            CustomerServiceLog("CharacterTransfer", "Sponsor's OID not found - CTS Sponsor Gift unpacked failed for player: " + anyPlayer + " " + getName(anyPlayer) + ". Offending object: " + sponsorGiftBox + ". Did not have sponsor OID on box object");
            return false;
        }
        blog("grantPackagedPainting has painting, CTS player OID and Sponsor OID.");
        int refugeeTimeTransfer = getIntObjVar(sponsorGiftBox, cts.DATETIME_GIFT_TRANSFER);
        if (refugeeTimeTransfer <= 0)
        {
            return false;
        }
        blog("grantPackagedPainting has refugeeTimeTransfer");
        String refugeePrevGalaxy = getStringObjVar(sponsorGiftBox, cts.PREVIOUS_PLAYER_CLUSTER);
        if (refugeePrevGalaxy == null || refugeePrevGalaxy.equals(""))
        {
            return false;
        }
        blog("grantPackagedPainting has refugeePrevGalaxy");
        String refugeePrevName = getStringObjVar(sponsorGiftBox, cts.PREVIOUS_PLAYER_NAME);
        if (refugeePrevName == null || refugeePrevName.equals(""))
        {
            return false;
        }
        blog("grantPackagedPainting has refugeePrevName");
        String refugeeCurrentGalaxy = getStringObjVar(sponsorGiftBox, cts.CURRENT_PLAYER_CLUSTER);
        if (refugeeCurrentGalaxy == null || refugeeCurrentGalaxy.equals(""))
        {
            return false;
        }
        blog("grantPackagedPainting has refugeeCurrentGalaxy");
        String refugeeName = getStringObjVar(sponsorGiftBox, cts.CURRENT_PLAYER_NAME);
        if (refugeeName == null || refugeeName.equals(""))
        {
            return false;
        }
        blog("grantPackagedPainting has refugeeName");
        obj_id sponsorOid = getObjIdObjVar(sponsorGiftBox, cts.SPONSOR_PLAYER_OID);
        if (!isValidId(sponsorOid))
        {
            return false;
        }
        blog("grantPackagedPainting has sponsorOid");
        String playerFullName = getStringObjVar(sponsorGiftBox, cts.SPONSOR_PLAYER_FULL_NAME);
        if (playerFullName == null || playerFullName.equals(""))
        {
            return false;
        }
        String sponsorClusterName = getStringObjVar(sponsorGiftBox, cts.SPONSOR_PLAYER_CLUSTER_NAME);
        if (sponsorClusterName == null || sponsorClusterName.equals(""))
        {
            return false;
        }
        int giftTime = getIntObjVar(sponsorGiftBox, cts.SPONSOR_PLAYER_TIME);
        if (refugeeTimeTransfer <= 0)
        {
            return false;
        }
        blog("grantPackagedPainting paintingTemplate: " + paintingTemplate);
        obj_id sponsorGiftPainting = createObjectInInventoryAllowOverload(paintingTemplate, anyPlayer);
        if (!isValidId(sponsorGiftPainting) || !exists(sponsorGiftPainting))
        {
            CustomerServiceLog("CharacterTransfer", "CTS Sponsor Gift unpacked failed for player: " + anyPlayer + " " + getName(anyPlayer) + ". Offending object: " + sponsorGiftPainting + " failed to spawn painting: " + paintingTemplate);
            return false;
        }
        setObjVar(sponsorGiftPainting, cts.DATETIME_GIFT_TRANSFER, refugeeTimeTransfer);
        setObjVar(sponsorGiftPainting, cts.PREVIOUS_PLAYER_CLUSTER, refugeePrevGalaxy);
        setObjVar(sponsorGiftPainting, cts.PREVIOUS_PLAYER_NAME, refugeePrevName);
        setObjVar(sponsorGiftPainting, cts.CURRENT_PLAYER_CLUSTER, refugeeCurrentGalaxy);
        setObjVar(sponsorGiftPainting, cts.CURRENT_PLAYER_NAME, refugeeName);
        setObjVar(sponsorGiftPainting, cts.SPONSOR_PLAYER_OID, sponsorOid);
        setObjVar(sponsorGiftPainting, cts.SPONSOR_PLAYER_FULL_NAME, playerFullName);
        setObjVar(sponsorGiftPainting, cts.SPONSOR_PLAYER_CLUSTER_NAME, sponsorClusterName);
        setObjVar(sponsorGiftPainting, cts.SPONSOR_PLAYER_TIME, giftTime);
        CustomerServiceLog("CharacterTransfer", "Packed item: " + sponsorGiftBox + " " + getName(sponsorGiftBox) + " unpacked: " + sponsorGiftPainting + " " + getName(sponsorGiftPainting) + " for player: " + anyPlayer + " " + getName(anyPlayer) + ".");
        return true;
    }
    public static String getCtsInscription(obj_id object) throws InterruptedException
    {
        if (!isValidId(object) || !exists(object))
        {
            return null;
        }
        if (!hasObjVar(object, cts.CURRENT_PLAYER_OID))
        {
            return null;
        }
        String refugeeTimeTransfer = getCalendarTimeStringLocal(getIntObjVar(object, cts.DATETIME_GIFT_TRANSFER));
        if (refugeeTimeTransfer == null || refugeeTimeTransfer.equals(""))
        {
            return null;
        }
        String refugeePrevGalaxy = getStringObjVar(object, cts.PREVIOUS_PLAYER_CLUSTER);
        if (refugeePrevGalaxy == null || refugeePrevGalaxy.equals(""))
        {
            return null;
        }
        String refugeePrevName = getStringObjVar(object, cts.PREVIOUS_PLAYER_NAME);
        if (refugeePrevName == null || refugeePrevName.equals(""))
        {
            return null;
        }
        String refugeeCurrentGalaxy = getStringObjVar(object, cts.CURRENT_PLAYER_CLUSTER);
        if (refugeeCurrentGalaxy == null || refugeeCurrentGalaxy.equals(""))
        {
            return null;
        }
        String refugeeName = getStringObjVar(object, cts.CURRENT_PLAYER_NAME);
        if (refugeeName == null || refugeeName.equals(""))
        {
            return null;
        }
        String inscriptionString = "";
        if (!hasObjVar(object, cts.SPONSOR_PLAYER_OID))
        {
            inscriptionString += "Given to " + refugeeName + ", " + refugeeCurrentGalaxy + " upon transfer from " + refugeePrevName;
            inscriptionString += ", " + refugeePrevGalaxy + " on " + refugeeTimeTransfer;
        }
        else 
        {
            obj_id sponsorOid = getObjIdObjVar(object, cts.SPONSOR_PLAYER_OID);
            if (!isValidId(sponsorOid))
            {
                return null;
            }
            String playerFullName = getStringObjVar(object, cts.SPONSOR_PLAYER_FULL_NAME);
            if (playerFullName == null || playerFullName.equals(""))
            {
                return null;
            }
            String sponsorClusterName = getStringObjVar(object, cts.SPONSOR_PLAYER_CLUSTER_NAME);
            if (sponsorClusterName == null || sponsorClusterName.equals(""))
            {
                return null;
            }
            String giftTime = getCalendarTimeStringLocal(getIntObjVar(object, cts.SPONSOR_PLAYER_TIME));
            if (giftTime == null || giftTime.equals(""))
            {
                return null;
            }
            inscriptionString += "Given to " + playerFullName + ", " + sponsorClusterName + " on " + giftTime + " from " + refugeeName + ", ";
            inscriptionString += refugeeCurrentGalaxy + ", who received it upon transfer from " + refugeePrevName + ", " + refugeePrevGalaxy;
            inscriptionString += " on " + refugeeTimeTransfer;
        }
        if (inscriptionString == null || inscriptionString.equals(""))
        {
            CustomerServiceLog("CharacterTransfer", "Unable to build inscription for CTS reward: " + object + " " + getName(object));
        }
        return inscriptionString;
    }
    public static boolean initializeCtsObject(obj_id object) throws InterruptedException
    {
        blog("initializeCtsPainting init");
        if (hasObjVar(object, cts.CURRENT_PLAYER_OID))
        {
            return true;
        }
        if (!utils.isNestedWithinAPlayer(object))
        {
            return false;
        }
        obj_id containingPlayer = utils.getContainingPlayer(object);
        if (!isValidId(containingPlayer) || !exists(containingPlayer))
        {
            return false;
        }
        if (!hasObjVar(object, CURRENT_PLAYER_OID))
        {
            setObjVar(object, CURRENT_PLAYER_OID, containingPlayer);
        }
        dictionary playerCTSData = getCharacterCtsHistory(containingPlayer);
        if (playerCTSData == null || playerCTSData.isEmpty())
        {
            CustomerServiceLog("veteran", "- CTS REWARD - " + object + " created but could not get the player " + containingPlayer + " CTS Data. Location: " + getLocation(object));
            return false;
        }
        String[] playerPreviousName = playerCTSData.getStringArray("character_name");
        if (playerPreviousName == null || playerPreviousName.length <= 0)
        {
            CustomerServiceLog("veteran", "- CTS REWARD - " + object + " created but could not get containing player OID. Location: " + getLocation(object));
            return false;
        }
        String[] playerPreviousGalaxy = playerCTSData.getStringArray("cluster_name");
        if (playerPreviousGalaxy == null || playerPreviousGalaxy.length <= 0)
        {
            CustomerServiceLog("veteran", "- CTS REWARD - " + object + " created but could not get containing player OID. Location: " + getLocation(object));
            return false;
        }
        int[] timeDate = playerCTSData.getIntArray("transfer_time");
        if (timeDate == null || timeDate.length <= 0)
        {
            CustomerServiceLog("veteran", "- CTS REWARD - " + object + " created but could not get the current Cal Time for this message. Location: " + getLocation(object));
            return false;
        }
        if (playerPreviousName.length != playerPreviousGalaxy.length || playerPreviousName.length != timeDate.length)
        {
            CustomerServiceLog("veteran", "- CTS REWARD - " + object + " created but player CTS Data not consistent. Player: " + getName(containingPlayer) + " " + containingPlayer);
            return false;
        }
        setObjVar(object, CURRENT_PLAYER_NAME, getPlayerFullName(containingPlayer));
        setObjVar(object, CURRENT_PLAYER_CLUSTER, getClusterName());
        setObjVar(object, DATETIME_GIFT_TRANSFER, timeDate[timeDate.length - 1]);
        setObjVar(object, PREVIOUS_PLAYER_NAME, playerPreviousName[playerPreviousName.length - 1]);
        setObjVar(object, PREVIOUS_PLAYER_CLUSTER, playerPreviousGalaxy[playerPreviousGalaxy.length - 1]);
        if (hasObjVar(object, CTS_PAINTING_OBJVAR))
        {
            CustomerServiceLog("veteran", "- CTS SPONSOR'S GIFT BOX stamped with data from Player: " + containingPlayer + " " + getName(containingPlayer) + " claimed the Galaxy Painting CTS Reward: " + object + " " + getName(object) + " at " + getCalendarTimeStringLocal(timeDate[timeDate.length - 1]) + " showing a previous galaxy of: " + playerPreviousGalaxy[playerPreviousGalaxy.length - 1] + " where the player's name was: " + playerPreviousName[playerPreviousName.length - 1]);
        }
        else 
        {
            CustomerServiceLog("veteran", "- CTS REFUGEE PAINTING stamped with data from Player: " + containingPlayer + " " + getName(containingPlayer) + " claimed the Galaxy Painting CTS Reward: " + object + " " + getName(object) + " at " + getCalendarTimeStringLocal(timeDate[timeDate.length - 1]) + " showing a previous galaxy of: " + playerPreviousGalaxy[playerPreviousGalaxy.length - 1] + " where the player's name was: " + playerPreviousName[playerPreviousName.length - 1]);
        }
        return true;
    }
    public static boolean blog(String msg) throws InterruptedException
    {
        if (BLOGGING_ON)
        {
            LOG(BLOG_CATEGORY, msg);
        }
        return true;
    }
    public static void initiateCtsFromItem(obj_id player, obj_id item) throws InterruptedException
    {
        if (!isValidId(player) || !isValidId(item))
        {
            return;
        }
        dictionary d = new dictionary();
        d.put("item", item);
        messageTo(player, "initiateCtsFromItem", d, 0.0f, false);
    }
}
