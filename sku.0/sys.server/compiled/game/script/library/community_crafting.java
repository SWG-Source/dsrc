package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.objvar_mangle;
import script.library.utils;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class community_crafting extends script.base_script
{
    public community_crafting()
    {
    }
    public static final int MAX_PLAYERS_PER_PROJECT = 200;
    public static final float NONCRAFTED_COMPONENT_QUALITY = 0.25f * 0.25f;
    public static final String OBJVAR_COMMUNITY_CRAFTING_BASE = "community_crafting";
    public static final String OBJVAR_COMMUNITY_CRAFTING_TRACKER = OBJVAR_COMMUNITY_CRAFTING_BASE + ".craftingTracker";
    public static final String OBJVAR_COMMUNITY_CRAFTING_SCHEMATIC = OBJVAR_COMMUNITY_CRAFTING_BASE + ".schematic";
    public static final String OBJVAR_COMMUNITY_CRAFTING_SCHEMATICS = OBJVAR_COMMUNITY_CRAFTING_BASE + ".schematics";
    public static final String OBJVAR_COMMUNITY_CRAFTING_TRACK_QUANTITY = OBJVAR_COMMUNITY_CRAFTING_BASE + ".trackQuantity";
    public static final String OBJVAR_COMMUNITY_CRAFTING_TRACK_QUALITY = OBJVAR_COMMUNITY_CRAFTING_BASE + ".trackQuality";
    public static final String OBJVAR_COMMUNITY_CRAFTING_TRACK_SLOTS = OBJVAR_COMMUNITY_CRAFTING_BASE + ".trackSlots";
    public static final String OBJVAR_COMMUNITY_CRAFTING_PRIZES = OBJVAR_COMMUNITY_CRAFTING_BASE + ".prizeTable";
    public static final String OBJVAR_COMMUNITY_CRAFTING_MIN_QUALITY = OBJVAR_COMMUNITY_CRAFTING_BASE + ".minQuality";
    public static final String OBJVAR_COMMUNITY_CRAFTING_MIN_QUANTITY = OBJVAR_COMMUNITY_CRAFTING_BASE + ".minQuantity";
    public static final String OBJVAR_COMMUNITY_CRAFTING_PLAYER_SCHEMATICS = OBJVAR_COMMUNITY_CRAFTING_BASE + ".schematics";
    public static final String OBJVAR_COMMUNITY_CRAFTING_PLAYERS = OBJVAR_COMMUNITY_CRAFTING_BASE + ".players";
    public static final String OBJVAR_COMMUNITY_CRAFTING_ATTRIBUTES = OBJVAR_COMMUNITY_CRAFTING_BASE + ".attribs";
    public static final String OBJVAR_COMMUNITY_CRAFTING_PLAYER_QUALITY_TOTAL = OBJVAR_COMMUNITY_CRAFTING_BASE + ".playerQuality";
    public static final String OBJVAR_COMMUNITY_CRAFTING_PLAYER_QUANTITY_TOTAL = OBJVAR_COMMUNITY_CRAFTING_BASE + ".playerQuantity";
    public static final String OBJVAR_COMMUNITY_CRAFTING_SLOTS = OBJVAR_COMMUNITY_CRAFTING_BASE + ".slots";
    public static final String OBJVAR_COMMUNITY_CRAFTING_ACTIVE = OBJVAR_COMMUNITY_CRAFTING_BASE + ".active";
    public static final String OBJVAR_COMMUNITY_CRAFTING_QUALITY_SUFFIX = ".quality";
    public static final String OBJVAR_COMMUNITY_CRAFTING_QUANTITY_SUFFIX = ".quantity";
    public static final String MSG_HANDLER_END_CRAFTING = "handleCleanupCommunityCrafting";
    public static final String MSG_HANDLER_MIN_QUANTITY_MET = "handleCommunityCraftingMinimumQuantityMet";
    public static final String MSG_HANDLER_PRIZE_WON = "handleCommunityCraftingReward";
    public static final String PRIZE_DATATABLE_COLUMN_REWARD = "reward";
    public static final String PRIZE_DATATABLE_COLUMN_SLOT = "slot";
    public static final String PRIZE_DATATABLE_COLUMN_TYPE = "type";
    public static final String PRIZE_DATATABLE_COLUMN_SCRIPT = "script";
    public static final int QUANTITY_PRIZE_DATATABLE_ENUM = 0;
    public static final int QUALITY_PRIZE_DATATABLE_ENUM = 1;
    public static final String REWARD_PRIZE = "cc_prize";
    public static final String REWARD_PRIZE_SLOT = "cc_slot";
    public static final String REWARD_PRIZE_TYPE = "cc_type";
    public static final String REWARD_PRIZE_SCRIPT = "cc_script";
    public static final string_id SID_RESULTS_FROM = new string_id("crafting", "community_crafting_from");
    public static final string_id SID_RESULTS_SUBJECT = new string_id("crafting", "community_crafting_results");
    public static final string_id SID_RESULTS_BODY_1 = new string_id("crafting", "community_crafting_body_1");
    public static final string_id SID_RESULTS_BODY_2 = new string_id("crafting", "community_crafting_body_2");
    public static final string_id SID_NOT_CRAFTER = new string_id("crafting", "cc_not_item_crafter");
    public static final string_id SID_NOT_MIN_QUALITY = new string_id("crafting", "cc_not_min_quality");
    public static final string_id SID_NOT_VALID_ITEM = new string_id("crafting", "cc_not_valid_item");
    public static final String SCRIPT_COMMUNITY_CRAFTING_PLAYER = "player.player_community_crafting";
    public static boolean setMasterSchematic(obj_id craftingTracker, String schematic, boolean trackQuantity, boolean trackQuality, boolean trackSlots, String prizeTable) throws InterruptedException
    {
        if (!isIdValid(craftingTracker))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: setMasterSchematic called " + "with invalid craftingTracker");
            return false;
        }
        if (!craftingTracker.isAuthoritative())
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: setMasterSchematic called " + "with non-authoritative craftingTracker " + craftingTracker);
            return false;
        }
        if (isInitializedForCC(craftingTracker))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: setMasterSchematic called " + "with craftingTracker " + craftingTracker + " that is already running a project");
            return false;
        }
        if (schematic == null || schematic.length() == 0)
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: setMasterSchematic called " + "with invalid schematic");
            return false;
        }
        Vector schematics = new Vector();
        if (!getSchematics(schematics, schematic))
        {
            return false;
        }
        int[] schematicsCrcs = new int[schematics.size()];
        for (int i = 0; i < schematicsCrcs.length; ++i)
        {
            schematicsCrcs[i] = getStringCrc((String)schematics.get(i));
        }
        setObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_SCHEMATICS, schematicsCrcs);
        setObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_TRACK_QUANTITY, trackQuantity);
        setObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_TRACK_QUALITY, trackQuality);
        setObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_TRACK_SLOTS, trackSlots);
        if (prizeTable == null)
        {
            prizeTable = "";
        }
        setObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_PRIZES, prizeTable);
        int[] quantities = new int[1];
        float[] qualities = new float[1];
        obj_id[] players = new obj_id[1];
        players[0] = obj_id.NULL_ID;
        setObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_PLAYERS, players);
        if (trackQuality)
        {
            setObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_PLAYER_QUALITY_TOTAL, qualities);
        }
        if (trackQuantity || trackQuality)
        {
            setObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_PLAYER_QUANTITY_TOTAL, quantities);
        }
        draft_schematic schematicData = getSchematicData(schematic);
        draft_schematic.attribute[] attribs = schematicData.getAttribs();
        if (attribs == null || attribs.length == 0)
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: getSchematics could " + "not find attribute info for schematic " + schematic);
            return false;
        }
        float[] attribValues = new float[attribs.length];
        setObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_ATTRIBUTES, attribValues);
        if (trackSlots)
        {
            String slotdot = OBJVAR_COMMUNITY_CRAFTING_SLOTS + ".";
            draft_schematic.slot[] slots = schematicData.getSlots();
            for (int i = 0; i < slots.length; ++i)
            {
                String slotdotindex = slotdot + i;
                if (trackQuantity || trackQuality)
                {
                    setObjVar(craftingTracker, slotdotindex + OBJVAR_COMMUNITY_CRAFTING_QUANTITY_SUFFIX, quantities);
                }
                if (trackQuality)
                {
                    setObjVar(craftingTracker, slotdotindex + OBJVAR_COMMUNITY_CRAFTING_QUALITY_SUFFIX, qualities);
                }
            }
        }
        String[] scripts = schematicData.getScripts();
        if (scripts != null)
        {
            for (int i = 0; i < scripts.length; ++i)
            {
                if (scripts[i] != null)
                {
                    attachScript(craftingTracker, scripts[i]);
                }
            }
        }
        setObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_ACTIVE, true);
        return true;
    }
    public static boolean setMinimumQuantity(obj_id craftingTracker, int minQuantity) throws InterruptedException
    {
        if (!isIdValid(craftingTracker))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: setMinimumQuantity called " + "with invalid craftingTracker");
            return false;
        }
        if (!isInitializedForCC(craftingTracker))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: setMinimumQuantity called " + "with uninitialized craftingTracker " + craftingTracker);
            return false;
        }
        if (minQuantity < 1)
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: setMinimumQuantity called " + "with min quantity " + minQuantity + ", setting to 1");
            minQuantity = 1;
        }
        setObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_MIN_QUANTITY, minQuantity);
        utils.setScriptVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_MIN_QUANTITY, minQuantity);
        return true;
    }
    public static boolean setMinimumQuality(obj_id craftingTracker, float minQuality) throws InterruptedException
    {
        if (!isIdValid(craftingTracker))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: setMinimumQuality called " + "with invalid craftingTracker");
            return false;
        }
        if (!isInitializedForCC(craftingTracker))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: setMinimumQuality called " + "with uninitialized craftingTracker " + craftingTracker);
            return false;
        }
        if (minQuality < 0)
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: setMinimumQuality called " + "with min quality " + minQuality + ", setting to 0");
            minQuality = 0;
        }
        setObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_MIN_QUALITY, minQuality);
        utils.setScriptVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_MIN_QUALITY, minQuality);
        return true;
    }
    public static boolean grantSchematicToPlayer(obj_id craftingTracker, obj_id player) throws InterruptedException
    {
        if (!isIdValid(craftingTracker))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: grantSchematicToPlayer called " + "with invalid craftingTracker");
            return false;
        }
        if (!isInitializedForCC(craftingTracker))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: grantSchematicToPlayer called " + "with uninitialized craftingTracker " + craftingTracker);
            return false;
        }
        if (!isIdValid(player))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: grantSchematicToPlayer called " + "with invalid player");
            return false;
        }
        if (!isSessionActive(craftingTracker))
        {
            return false;
        }
        int[] schematics = getIntArrayObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_SCHEMATICS);
        if (schematics == null || schematics.length == 0)
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: grantSchematicToPlayer craftingTracker " + craftingTracker + "has bad a schematics objvar");
            return false;
        }
        for (int i = 0; i < schematics.length; ++i)
        {
            if (!grantSchematicToPlayer(craftingTracker, player, schematics[i]))
            {
                return false;
            }
        }
        return true;
    }
    public static boolean grantSchematicToPlayer(obj_id craftingTracker, obj_id player, String schematic) throws InterruptedException
    {
        return grantSchematicToPlayer(craftingTracker, player, getStringCrc(schematic));
    }
    public static boolean grantSchematicToPlayer(obj_id craftingTracker, obj_id player, int schematicCrc) throws InterruptedException
    {
        if (!isIdValid(craftingTracker))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: grantSchematicToPlayer(crc) called " + "with invalid craftingTracker");
            return false;
        }
        if (!isInitializedForCC(craftingTracker))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: grantSchematicToPlayer(crc) called " + "with uninitialized craftingTracker " + craftingTracker);
            return false;
        }
        if (!isIdValid(player))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: grantSchematicToPlayer(crc) called " + "with invalid player");
            return false;
        }
        if (schematicCrc == 0)
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: grantSchematicToPlayer(crc) called " + "with invalid schematic");
            return false;
        }
        if (!isSessionActive(craftingTracker))
        {
            return false;
        }
        Vector playerSchematics = null;
        int baseSchematic = getSchematic(craftingTracker);
        if (schematicCrc == baseSchematic)
        {
            if (isPlayerCrafting(craftingTracker, player))
            {
                CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: grantSchematicToPlayer(crc) called " + "with player %TU who is already crafting", player);
                return false;
            }
            if (!addPlayerToSystem(craftingTracker, player))
            {
                return false;
            }
            playerSchematics = new Vector();
        }
        else 
        {
            if (!isPlayerCrafting(craftingTracker, player))
            {
                CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: grantSchematicToPlayer(crc) called " + "with player %TU who is not crafting", player);
                return false;
            }
            playerSchematics = getResizeableIntArrayObjVar(player, OBJVAR_COMMUNITY_CRAFTING_PLAYER_SCHEMATICS);
        }
        if (!grantSchematic(player, schematicCrc))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: grantSchematicToPlayer(crc) failed " + "to add schematic " + schematicCrc + " to player %TU", player);
            return false;
        }
        playerSchematics.add(new Integer(schematicCrc));
        setObjVar(player, OBJVAR_COMMUNITY_CRAFTING_PLAYER_SCHEMATICS, playerSchematics);
        return true;
    }
    public static boolean addIngredient(obj_id craftingTracker, obj_id player, obj_id ingredient) throws InterruptedException
    {
        if (!isIdValid(craftingTracker))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: addIngredient called " + "with invalid craftingTracker");
            return false;
        }
        if (!isInitializedForCC(craftingTracker))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: addIngredient called " + "with uninitialized craftingTracker " + craftingTracker);
            return false;
        }
        if (!isIdValid(player))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: addIngredient called " + "with invalid player");
            return false;
        }
        if (!isPlayerCrafting(craftingTracker, player))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: addIngredient called " + "for craftingTracker " + craftingTracker + ", player %TU who is not crafting", player);
            return false;
        }
        if (!isIdValid(ingredient))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: addIngredient called " + "with invalid ingredient");
            return false;
        }
        if (!isSessionActive(craftingTracker))
        {
            return false;
        }
        int playerIndex = getCraftingPlayerIndex(craftingTracker, player);
        if (playerIndex < 0)
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: addIngredient could not " + "get player index for player %TU on craftingTracker " + craftingTracker, player);
            return false;
        }
        float[] objectAttributes = getFloatArrayObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_ATTRIBUTES);
        if (objectAttributes == null || objectAttributes.length == 0)
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: addIngredient could not " + "get object attributes objvar on craftingTracker " + craftingTracker);
            return false;
        }
        int matchSlot = -1;
        float ingredientQuality = 0;
        draft_schematic schematicData = getSchematicData(getSchematic(craftingTracker));
        if (schematicData == null)
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: addIngredient error " + "getting schematic from craftingTracker " + craftingTracker);
            return false;
        }
        draft_schematic.slot[] slots = schematicData.getSlots();
        if (slots == null || slots.length == 0)
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: addIngredient error " + "getting schematic slots from schemtic " + getSchematic(craftingTracker) + " on craftingTracker " + craftingTracker);
            return false;
        }
        int ingredientMultiplier = 1;
        if (isResourceContainer(ingredient))
        {
            boolean isRealResource = true;
            resource_attribute[] resourceAttribs = null;
            if (getGameObjectType(ingredient) != GOT_resource_container_pseudo)
            {
                obj_id resourceType = getResourceContainerResourceType(ingredient);
                if (!isIdValid(resourceType))
                {
                    CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: addIngredient error " + "getting resource type from container " + ingredient + " given by player %TU ", player);
                    return false;
                }
                resourceAttribs = getResourceAttributes(resourceType);
                if (resourceAttribs == null)
                {
                    CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: addIngredient " + "could not get attributes from resource type " + resourceType);
                    return false;
                }
                for (int i = 0; i < slots.length; ++i)
                {
                    switch (slots[i].ingredientType)
                    {
                        case draft_schematic.IT_resourceType:
                        if (resourceType == getResourceTypeByName(slots[i].ingredientName))
                        {
                            matchSlot = i;
                            i = slots.length;
                        }
                        break;
                        case draft_schematic.IT_resourceClass:
                        if (isResourceDerivedFrom(resourceType, slots[i].ingredientName))
                        {
                            matchSlot = i;
                            i = slots.length;
                        }
                        break;
                        default:
                        break;
                    }
                }
            }
            else 
            {
                isRealResource = false;
                int ingredientTemplateCrc = getStringCrc(getTemplateName(ingredient));
                if (ingredientTemplateCrc == 0)
                {
                    CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: addIngredient cannot " + "get template crc for ingredient " + ingredient + " by player %TU", player);
                    return false;
                }
                obj_id crafter = getCrafter(ingredient);
                if (isIdValid(crafter))
                {
                    if (crafter != player)
                    {
                        CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: addIngredient player %TU " + "adding crafted resource " + ingredient + " crafted by player %TT", player, crafter);
                        sendSystemMessage(player, SID_NOT_CRAFTER);
                        return false;
                    }
                }
                resourceAttribs = new resource_attribute[craftinglib.NUM_RESOURCE_ATTRIBS];
                for (int i = 0; i < craftinglib.NUM_RESOURCE_ATTRIBS; ++i)
                {
                    String ovname = craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + craftinglib.RESOURCE_OBJVAR_NAMES[i];
                    obj_var attribVar = getObjVar(ingredient, ovname);
                    if (attribVar != null)
                    {
                        int value;
                        if (attribVar.getData() instanceof Integer)
                        {
                            value = attribVar.getIntData();
                        }
                        else 
                        {
                            value = (int)(attribVar.getFloatData() + 0.5f);
                        }
                        resourceAttribs[i] = new resource_attribute(craftinglib.RESOURCE_OBJVAR_NAMES[i], value);
                    }
                    else 
                    {
                        resourceAttribs[i] = null;
                    }
                }
                for (int i = 0; i < slots.length; ++i)
                {
                    switch (slots[i].ingredientType)
                    {
                        case draft_schematic.IT_template:
                        case draft_schematic.IT_templateGeneric:
                        if (getStringCrc(slots[i].ingredientName) == ingredientTemplateCrc)
                        {
                            matchSlot = i;
                            i = slots.length;
                        }
                        break;
                        case draft_schematic.IT_schematic:
                        case draft_schematic.IT_schematicGeneric:
                        int createdIngredientTemplate = getTemplateCrcCreatedFromSchematic(slots[i].ingredientName);
                        if (createdIngredientTemplate == ingredientTemplateCrc)
                        {
                            matchSlot = i;
                            i = slots.length;
                        }
                        break;
                        default:
                        break;
                    }
                }
            }
            if (matchSlot < 0)
            {
                sendSystemMessage(player, SID_NOT_VALID_ITEM);
                return false;
            }
            int crateCount;
            if (isRealResource)
            {
                crateCount = getResourceContainerQuantity(ingredient);
            }
            else 
            {
                crateCount = 1;
            }
            int required = slots[matchSlot].amountRequired;
            if (required <= 0)
            {
                CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: addIngredient slot " + matchSlot + " of schematic " + getSchematic(craftingTracker) + " has invalid ingredient count");
                return false;
            }
            if (crateCount < required)
            {
                return false;
            }
            ingredientMultiplier = crateCount / required;
            resource_weight[] weights = getResourceWeightsFromScripts(craftingTracker);
            ingredientQuality = computeResourceAttributeQuality(resourceAttribs, schematicData, weights, objectAttributes);
            if (ingredientQuality < 0)
            {
                sendSystemMessage(player, SID_NOT_MIN_QUALITY);
                return false;
            }
        }
        else 
        {
            obj_id crafter = getCrafter(ingredient);
            if (isIdValid(crafter))
            {
                if (crafter != player)
                {
                    CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: addIngredient player %TU " + "adding crafted ingredient " + ingredient + " crafted by player %TT", player, crafter);
                    sendSystemMessage(player, SID_NOT_CRAFTER);
                    return false;
                }
            }
            int ingredientTemplateCrc = getStringCrc(getTemplateName(ingredient));
            if (ingredientTemplateCrc == 0)
            {
                CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: addIngredient cannot " + "get template crc for ingredient " + ingredient + " by player %TU", player);
                return false;
            }
            for (int i = 0; i < slots.length; ++i)
            {
                switch (slots[i].ingredientType)
                {
                    case draft_schematic.IT_template:
                    case draft_schematic.IT_templateGeneric:
                    if (getStringCrc(slots[i].ingredientName) == ingredientTemplateCrc)
                    {
                        matchSlot = i;
                        i = slots.length;
                    }
                    break;
                    case draft_schematic.IT_schematic:
                    case draft_schematic.IT_schematicGeneric:
                    int createdIngredientTemplate = getTemplateCrcCreatedFromSchematic(slots[i].ingredientName);
                    if (createdIngredientTemplate == ingredientTemplateCrc)
                    {
                        matchSlot = i;
                        i = slots.length;
                    }
                    break;
                    default:
                    break;
                }
            }
            if (matchSlot < 0)
            {
                sendSystemMessage(player, SID_NOT_VALID_ITEM);
                return false;
            }
            ingredientQuality = computeComponentAttributeQuality(ingredient, schematicData, objectAttributes);
            if (ingredientQuality < 0)
            {
                sendSystemMessage(player, SID_NOT_MIN_QUALITY);
                return false;
            }
        }
        if (matchSlot < 0)
        {
            sendSystemMessage(player, SID_NOT_VALID_ITEM);
            return false;
        }
        if (ingredientQuality < getMinimumQuality(craftingTracker))
        {
            sendSystemMessage(player, SID_NOT_MIN_QUALITY);
            return false;
        }
        CustomerServiceLog("community_crafting", "community_crafting.scriptlib: player %TU added ingredient " + ingredient + " to community crafting project " + craftingTracker, player);
        setObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_ATTRIBUTES, objectAttributes);
        boolean trackQuantity = utils.getBooleanScriptVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_TRACK_QUANTITY);
        boolean trackQuality = utils.getBooleanScriptVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_TRACK_QUALITY);
        boolean trackSlots = utils.getBooleanScriptVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_TRACK_SLOTS);
        if (trackQuality)
        {
            float[] qualities = objvar_mangle.getMangledFloatArrayObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_PLAYER_QUALITY_TOTAL);
            qualities[playerIndex] += ingredientQuality * ingredientMultiplier;
            objvar_mangle.setMangledFloatArrayObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_PLAYER_QUALITY_TOTAL, qualities);
        }
        if (trackQuantity || trackQuality)
        {
            int[] quantities = objvar_mangle.getMangledIntArrayObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_PLAYER_QUANTITY_TOTAL);
            if (trackQuantity)
            {
                int minQuantity = getMinimumQuantity(craftingTracker);
                if (quantities[playerIndex] < minQuantity && quantities[playerIndex] + ingredientMultiplier >= minQuantity)
                {
                    messageTo(player, MSG_HANDLER_MIN_QUANTITY_MET, null, 0.1f, true);
                }
            }
            quantities[playerIndex] += ingredientMultiplier;
            objvar_mangle.setMangledIntArrayObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_PLAYER_QUANTITY_TOTAL, quantities);
        }
        if (trackSlots)
        {
            String slotdot = OBJVAR_COMMUNITY_CRAFTING_SLOTS + ".";
            String slotdotindex = slotdot + matchSlot;
            if (trackQuantity || trackQuality)
            {
                int[] quantities = objvar_mangle.getMangledIntArrayObjVar(craftingTracker, slotdotindex + OBJVAR_COMMUNITY_CRAFTING_QUANTITY_SUFFIX);
                ++quantities[playerIndex];
                objvar_mangle.setMangledIntArrayObjVar(craftingTracker, slotdotindex + OBJVAR_COMMUNITY_CRAFTING_QUANTITY_SUFFIX, quantities);
            }
            if (trackQuality)
            {
                float[] qualities = objvar_mangle.getMangledFloatArrayObjVar(craftingTracker, slotdotindex + OBJVAR_COMMUNITY_CRAFTING_QUALITY_SUFFIX);
                qualities[playerIndex] += ingredientQuality;
                objvar_mangle.setMangledFloatArrayObjVar(craftingTracker, slotdotindex + OBJVAR_COMMUNITY_CRAFTING_QUALITY_SUFFIX, qualities);
            }
        }
        return true;
    }
    public static boolean finalizeItem(obj_id craftingTracker, boolean informAllPlayers) throws InterruptedException
    {
        if (!isIdValid(craftingTracker))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: finalizeItem called " + "with invalid craftingTracker");
            return false;
        }
        if (!isInitializedForCC(craftingTracker))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: finalizeItem called " + "with uninitialized craftingTracker " + craftingTracker);
            return false;
        }
        int schematicCrc = getSchematic(craftingTracker);
        draft_schematic schematicData = getSchematicData(schematicCrc);
        if (schematicData == null)
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: finalizeItem error " + "getting schematic from craftingTracker " + craftingTracker);
            return false;
        }
        obj_id[] players = objvar_mangle.getMangledObjIdArrayObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_PLAYERS);
        if (informAllPlayers && players != null)
        {
            draft_schematic.attribute[] schematicAttribs = schematicData.getAttribs();
            if (schematicAttribs == null || schematicAttribs.length == 0)
            {
                CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: finalizeItem could " + "not find attribute info for schematic " + schematicCrc);
                return false;
            }
            float[] objectAttributes = getFloatArrayObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_ATTRIBUTES);
            if (objectAttributes == null || objectAttributes.length == 0)
            {
                CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: finalizeItem could not " + "get object attributes objvar on craftingTracker " + craftingTracker);
                return false;
            }
            String emailBody = "@" + SID_RESULTS_BODY_1 + "\0@" + getNameFromTemplate(schematicData.getObjectTemplateCreated()) + "\0@" + SID_RESULTS_BODY_2;
            for (int i = 0; i < objectAttributes.length; ++i)
            {
                emailBody += "\0@" + schematicAttribs[i].name + "\0\\>200" + (int)objectAttributes[i] + "\\>000\n";
            }
            for (int i = 0; i < players.length; ++i)
            {
                if (isIdValid(players[i]))
                {
                    chatSendPersistentMessage("@" + SID_RESULTS_FROM, getPlayerName(players[i]), "@" + SID_RESULTS_SUBJECT, emailBody, null);
                }
            }
        }
        boolean trackQuantity = utils.getBooleanScriptVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_TRACK_QUANTITY);
        boolean trackQuality = utils.getBooleanScriptVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_TRACK_QUALITY);
        boolean trackSlots = utils.getBooleanScriptVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_TRACK_SLOTS);
        HashSet goodPlayers = null;
        if (players != null && trackQuantity)
        {
            int minQuantity = getMinimumQuantity(craftingTracker);
            if (minQuantity >= 1)
            {
                int[] quantities = objvar_mangle.getMangledIntArrayObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_PLAYER_QUANTITY_TOTAL);
                if (quantities != null && quantities.length == players.length)
                {
                    goodPlayers = new HashSet(players.length);
                    for (int i = 0; i < players.length; ++i)
                    {
                        if (quantities[i] >= minQuantity)
                        {
                            goodPlayers.add(players[i]);
                        }
                    }
                }
            }
        }
        String prizeTable = getStringObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_PRIZES);
        if (players != null && prizeTable != null && dataTableOpen(prizeTable))
        {
            int[] prizes = dataTableGetIntColumn(prizeTable, PRIZE_DATATABLE_COLUMN_REWARD);
            int[] prizeSlots = dataTableGetIntColumn(prizeTable, PRIZE_DATATABLE_COLUMN_SLOT);
            int[] prizeTypes = dataTableGetIntColumn(prizeTable, PRIZE_DATATABLE_COLUMN_TYPE);
            String[] prizeScripts = dataTableGetStringColumn(prizeTable, PRIZE_DATATABLE_COLUMN_SCRIPT);
            if (prizes != null && prizeSlots != null && prizeTypes != null && prizeTypes.length == prizeSlots.length && prizes.length == prizeTypes.length)
            {
                draft_schematic.slot[] slots = schematicData.getSlots();
                if (slots != null && slots.length > 0)
                {
                    HashSet winners = new HashSet();
                    Vector playerIds = new Vector();
                    Vector playerNames = new Vector();
                    Vector values = new Vector();
                    for (int i = 0; i <= slots.length; ++i)
                    {
                        if (i > 0 && !trackSlots)
                        {
                            break;
                        }
                        int qualityPrize = -1;
                        int quantityPrize = -1;
                        for (int j = 0; j < prizeSlots.length; ++j)
                        {
                            if (prizeSlots[j] == i)
                            {
                                if (prizeTypes[j] == QUALITY_PRIZE_DATATABLE_ENUM)
                                {
                                    qualityPrize = j;
                                }
                                else if (prizeTypes[j] == QUANTITY_PRIZE_DATATABLE_ENUM)
                                {
                                    quantityPrize = j;
                                }
                            }
                        }
                        if (qualityPrize >= 0 && trackQuality)
                        {
                            if (getPlayerRanking(craftingTracker, playerIds, playerNames, values, false, i))
                            {
                                if (playerIds.get(0) != null && isIdValid((obj_id)(playerIds.get(0))))
                                {
                                    obj_id winner = (obj_id)(playerIds.get(0));
                                    if (!winners.contains(winner) && (goodPlayers == null || goodPlayers.contains(winner)))
                                    {
                                        winners.add(winner);
                                        dictionary params = new dictionary();
                                        params.addInt(REWARD_PRIZE, prizes[qualityPrize]);
                                        params.addInt(REWARD_PRIZE_SLOT, prizeSlots[qualityPrize]);
                                        params.addInt(REWARD_PRIZE_TYPE, prizeTypes[qualityPrize]);
                                        if (prizeScripts != null && prizeScripts.length - 1 >= qualityPrize)
                                        {
                                            params.put(REWARD_PRIZE_SCRIPT, prizeScripts[qualityPrize]);
                                        }
                                        messageTo(winner, MSG_HANDLER_PRIZE_WON, params, 0.1f, true);
                                    }
                                }
                            }
                        }
                        if (quantityPrize >= 0 && trackQuantity)
                        {
                            if (getPlayerRanking(craftingTracker, playerIds, playerNames, values, true, i))
                            {
                                if (playerIds.get(0) != null && isIdValid((obj_id)(playerIds.get(0))))
                                {
                                    obj_id winner = (obj_id)(playerIds.get(0));
                                    if (!winners.contains(winner) && (goodPlayers == null || goodPlayers.contains(winner)))
                                    {
                                        winners.add(winner);
                                        dictionary params = new dictionary();
                                        params.addInt(REWARD_PRIZE, prizes[quantityPrize]);
                                        params.addInt(REWARD_PRIZE_SLOT, prizeSlots[quantityPrize]);
                                        params.addInt(REWARD_PRIZE_TYPE, prizeTypes[quantityPrize]);
                                        if (prizeScripts != null && prizeScripts.length - 1 >= quantityPrize)
                                        {
                                            params.put(REWARD_PRIZE_SCRIPT, prizeScripts[quantityPrize]);
                                        }
                                        messageTo(winner, MSG_HANDLER_PRIZE_WON, params, 0.1f, true);
                                    }
                                }
                            }
                        }
                    }
                }
                else 
                {
                    CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: finalizeItem error " + "getting schematic slots from schemtic " + schematicCrc + " on craftingTracker " + craftingTracker);
                }
            }
            else 
            {
                CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: finalizeItem prize table " + prizeTable + " contains bad data");
            }
        }
        if (players != null)
        {
            for (int i = 0; i < players.length; ++i)
            {
                if (isIdValid(players[i]))
                {
                    messageTo(players[i], MSG_HANDLER_END_CRAFTING, null, 1.0f, true);
                }
            }
        }
        setObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_ACTIVE, false);
        return true;
    }
    public static boolean createItem(obj_id craftingTracker, location pos) throws InterruptedException
    {
        return false;
    }
    public static boolean cleanup(obj_id craftingTracker) throws InterruptedException
    {
        if (!isIdValid(craftingTracker))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: cleanup called " + "with invalid craftingTracker");
            return false;
        }
        if (getBooleanObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_ACTIVE))
        {
            finalizeItem(craftingTracker, false);
        }
        draft_schematic schematicData = getSchematicData(getSchematic(craftingTracker));
        if (schematicData != null)
        {
            String[] scripts = schematicData.getScripts();
            if (scripts != null)
            {
                for (int i = 0; i < scripts.length; ++i)
                {
                    if (scripts[i] != null)
                    {
                        detachScript(craftingTracker, scripts[i]);
                    }
                }
            }
        }
        utils.removeScriptVarTree(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_BASE);
        removeObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_BASE);
        return true;
    }
    public static boolean isSessionActive(obj_id craftingTracker) throws InterruptedException
    {
        if (!isIdValid(craftingTracker))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: getSchematic called " + "with invalid craftingTracker");
            return false;
        }
        if (!isInitializedForCC(craftingTracker))
        {
            return false;
        }
        return getBooleanObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_ACTIVE);
    }
    public static int getSchematic(obj_id craftingTracker) throws InterruptedException
    {
        if (!isIdValid(craftingTracker))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: getSchematic called " + "with invalid craftingTracker");
            return 0;
        }
        if (!isInitializedForCC(craftingTracker))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: getSchematic called " + "with uninitialized craftingTracker " + craftingTracker);
            return 0;
        }
        if (!utils.hasScriptVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_SCHEMATIC))
        {
            return 0;
        }
        return utils.getIntScriptVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_SCHEMATIC);
    }
    public static String[] getSchematicScripts(obj_id craftingTracker) throws InterruptedException
    {
        if (!isIdValid(craftingTracker))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: getSchematicScripts called " + "with invalid craftingTracker");
            return null;
        }
        if (!isInitializedForCC(craftingTracker))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: getSchematicScripts called " + "with uninitialized craftingTracker " + craftingTracker);
            return null;
        }
        draft_schematic schematicData = getSchematicData(getSchematic(craftingTracker));
        if (schematicData != null)
        {
            return schematicData.getScripts();
        }
        return null;
    }
    public static boolean isPlayerCrafting(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: isPlayerCrafting called " + "with invalid player");
            return false;
        }
        return hasObjVar(player, OBJVAR_COMMUNITY_CRAFTING_BASE);
    }
    public static boolean isPlayerCrafting(obj_id craftingTracker, obj_id player) throws InterruptedException
    {
        if (!isIdValid(craftingTracker))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: isPlayerCrafting called " + "with invalid craftingTracker");
            return false;
        }
        if (!isInitializedForCC(craftingTracker))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: isPlayerCrafting called " + "with uninitialized craftingTracker " + craftingTracker);
            return false;
        }
        if (!isIdValid(player))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: isPlayerCrafting called " + "with invalid player");
            return false;
        }
        obj_id[] players = objvar_mangle.getMangledObjIdArrayObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_PLAYERS);
        if (players == null)
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: isPlayerCrafting " + "cannot find players list for craftingTracker " + craftingTracker);
            return false;
        }
        for (int i = 1; i < players.length; ++i)
        {
            if (player == players[i])
            {
                return true;
            }
        }
        return false;
    }
    public static int getNumPlayersCrafting(obj_id craftingTracker) throws InterruptedException
    {
        if (!isIdValid(craftingTracker))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: isPlayerCrafting called " + "with invalid craftingTracker");
            return 0;
        }
        if (!isInitializedForCC(craftingTracker))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: isPlayerCrafting called " + "with uninitialized craftingTracker " + craftingTracker);
            return 0;
        }
        obj_id[] players = objvar_mangle.getMangledObjIdArrayObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_PLAYERS);
        if (players == null)
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: isPlayerCrafting " + "cannot find players list for craftingTracker " + craftingTracker);
            return 0;
        }
        return players.length - 1;
    }
    public static int getMaxPlayersPerProject() throws InterruptedException
    {
        int playerLimit = utils.getIntConfigSetting("Quest", "CommunityCraftingLimit");
        if (playerLimit == 0)
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: getMaxPlayersPerProject failed " + "to get [Quest] CommunityCraftingLimit, using limit of " + MAX_PLAYERS_PER_PROJECT);
            playerLimit = MAX_PLAYERS_PER_PROJECT;
        }
        return playerLimit;
    }
    public static int getNumIngredientsNeededByPlayer(obj_id craftingTracker, obj_id player) throws InterruptedException
    {
        if (!isIdValid(craftingTracker))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: getNumIngredientsNeededByPlayer called " + "with invalid craftingTracker");
            return -1;
        }
        if (!isInitializedForCC(craftingTracker))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: getNumIngredientsNeededByPlayer called " + "with uninitialized craftingTracker " + craftingTracker);
            return -1;
        }
        if (!isIdValid(player))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: getNumIngredientsNeededByPlayer called " + "with invalid player");
            return -1;
        }
        if (!isPlayerCrafting(craftingTracker, player))
        {
            return -1;
        }
        if (!getIsTrackingQuantity(craftingTracker))
        {
            return 0;
        }
        int minQuantity = getMinimumQuantity(craftingTracker);
        if (minQuantity <= 0)
        {
            return 0;
        }
        obj_id[] players = objvar_mangle.getMangledObjIdArrayObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_PLAYERS);
        int[] quantities = objvar_mangle.getMangledIntArrayObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_PLAYER_QUANTITY_TOTAL);
        if (players == null || quantities == null || players.length != quantities.length)
        {
            return -1;
        }
        for (int i = 0; i < players.length; ++i)
        {
            if (player == players[i])
            {
                int needed = minQuantity - quantities[i];
                if (needed < 0)
                {
                    needed = 0;
                }
                return needed;
            }
        }
        return -1;
    }
    public static boolean getIsTrackingQuality(obj_id craftingTracker) throws InterruptedException
    {
        if (!isIdValid(craftingTracker))
        {
            return false;
        }
        if (!isInitializedForCC(craftingTracker))
        {
            return false;
        }
        return utils.getBooleanScriptVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_TRACK_QUALITY);
    }
    public static boolean getIsTrackingQuantity(obj_id craftingTracker) throws InterruptedException
    {
        if (!isIdValid(craftingTracker))
        {
            return false;
        }
        if (!isInitializedForCC(craftingTracker))
        {
            return false;
        }
        return utils.getBooleanScriptVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_TRACK_QUANTITY);
    }
    public static boolean getIsTrackingSlots(obj_id craftingTracker) throws InterruptedException
    {
        if (!isIdValid(craftingTracker))
        {
            return false;
        }
        if (!isInitializedForCC(craftingTracker))
        {
            return false;
        }
        return utils.getBooleanScriptVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_TRACK_SLOTS);
    }
    public static int getNumSlots(obj_id craftingTracker) throws InterruptedException
    {
        if (!isIdValid(craftingTracker))
        {
            return 0;
        }
        if (!isInitializedForCC(craftingTracker))
        {
            return 0;
        }
        draft_schematic schematicData = getSchematicData(getSchematic(craftingTracker));
        if (schematicData == null)
        {
            return 0;
        }
        draft_schematic.slot[] slots = schematicData.getSlots();
        if (slots == null)
        {
            return 0;
        }
        return slots.length;
    }
    public static boolean getProjectAttributes(obj_id craftingTracker, Vector names, Vector values) throws InterruptedException
    {
        if (!isIdValid(craftingTracker))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: getProjectAttributes called " + "with invalid craftingTracker");
            return false;
        }
        if (!isInitializedForCC(craftingTracker))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: getProjectAttributes called " + "with uninitialized craftingTracker " + craftingTracker);
            return false;
        }
        if (names == null)
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: getProjectAttributes called " + "with invalid names");
            return false;
        }
        if (values == null)
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: getProjectAttributes called " + "with invalid values");
            return false;
        }
        int schematicCrc = getSchematic(craftingTracker);
        draft_schematic schematicData = getSchematicData(schematicCrc);
        if (schematicData == null)
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: getProjectAttributes error " + "getting schematic from craftingTracker " + craftingTracker);
            return false;
        }
        draft_schematic.attribute[] schematicAttribs = schematicData.getAttribs();
        if (schematicAttribs == null || schematicAttribs.length == 0)
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: getProjectAttributes could " + "not find attribute info for schematic " + schematicCrc);
            return false;
        }
        float[] objectAttributes = getFloatArrayObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_ATTRIBUTES);
        if (objectAttributes == null || objectAttributes.length == 0)
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: getProjectAttributes could not " + "get object attributes objvar on craftingTracker " + craftingTracker);
            return false;
        }
        if (schematicAttribs.length != objectAttributes.length)
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: getProjectAttributes got " + "attribute names and values with different lengths (" + schematicAttribs.length + ", " + objectAttributes.length + ") on craftingTracker " + craftingTracker);
            return false;
        }
        names.clear();
        values.clear();
        for (int i = 0; i < schematicAttribs.length; ++i)
        {
            names.add(schematicAttribs[i].name);
            values.add(new Float(objectAttributes[i]));
        }
        return true;
    }
    public static boolean getPlayerRanking(obj_id craftingTracker, Vector playerIds, Vector playerNames, Vector values, boolean quantity, int slot) throws InterruptedException
    {
        if (!isIdValid(craftingTracker))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: getPlayerRanking called " + "with invalid craftingTracker");
            return false;
        }
        if (!isInitializedForCC(craftingTracker))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: getPlayerRanking called " + "with uninitialized craftingTracker " + craftingTracker);
            return false;
        }
        if (playerIds == null)
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: getPlayerRanking called " + "with null playerIds");
            return false;
        }
        if (playerNames == null)
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: getPlayerRanking called " + "with null playerNames");
            return false;
        }
        if (values == null)
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: getPlayerRanking called " + "with null values");
            return false;
        }
        draft_schematic schematicData = getSchematicData(getSchematic(craftingTracker));
        if (schematicData == null)
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: getPlayerRanking could " + "not find data for schematic " + getSchematic(craftingTracker));
            return false;
        }
        draft_schematic.slot[] slots = schematicData.getSlots();
        if (slots == null)
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: getSchematics could " + "not find slot info for schematic " + getSchematic(craftingTracker));
            return false;
        }
        playerIds.clear();
        playerNames.clear();
        values.clear();
        obj_id[] players = objvar_mangle.getMangledObjIdArrayObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_PLAYERS);
        if (quantity)
        {
            int[] rankings;
            if (slot >= 1 && slot <= slots.length)
            {
                String slotdot = OBJVAR_COMMUNITY_CRAFTING_SLOTS + "." + (slot - 1);
                rankings = objvar_mangle.getMangledIntArrayObjVar(craftingTracker, slotdot + OBJVAR_COMMUNITY_CRAFTING_QUANTITY_SUFFIX);
            }
            else 
            {
                rankings = objvar_mangle.getMangledIntArrayObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_PLAYER_QUANTITY_TOTAL);
            }
            if (players == null || rankings == null || players.length != rankings.length)
            {
                return false;
            }
            PROFILER_START("SORT_FS_VILLAGE_CRAFTERS_QUANTITY");
            doubleSort(rankings, players);
            for (int i = 0; i < rankings.length; ++i)
            {
                playerIds.add(players[i]);
                values.add(new Integer(rankings[i]));
            }
            PROFILER_STOP("SORT_FS_VILLAGE_CRAFTERS_QUANTITY");
        }
        else 
        {
            float[] rankings;
            int[] amounts;
            if (slot >= 1 && slot <= slots.length)
            {
                String slotdot = OBJVAR_COMMUNITY_CRAFTING_SLOTS + "." + (slot - 1);
                rankings = objvar_mangle.getMangledFloatArrayObjVar(craftingTracker, slotdot + OBJVAR_COMMUNITY_CRAFTING_QUALITY_SUFFIX);
                amounts = objvar_mangle.getMangledIntArrayObjVar(craftingTracker, slotdot + OBJVAR_COMMUNITY_CRAFTING_QUANTITY_SUFFIX);
            }
            else 
            {
                rankings = objvar_mangle.getMangledFloatArrayObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_PLAYER_QUALITY_TOTAL);
                amounts = objvar_mangle.getMangledIntArrayObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_PLAYER_QUANTITY_TOTAL);
            }
            if (players == null || rankings == null || amounts == null || players.length != rankings.length || rankings.length != amounts.length)
            {
                return false;
            }
            PROFILER_START("SORT_FS_VILLAGE_CRAFTERS_QUALITY");
            for (int i = 0; i < rankings.length; ++i)
            {
                if (amounts[i] > 0)
                {
                    rankings[i] /= amounts[i];
                }
                else 
                {
                    rankings[i] = 0;
                }
            }
            doubleSort(rankings, players);
            for (int i = 0; i < rankings.length; ++i)
            {
                playerIds.add(players[i]);
                values.add(new Float(rankings[i] * 100.0f));
            }
            PROFILER_STOP("SORT_FS_VILLAGE_CRAFTERS_QUALITY");
        }
        return true;
    }
    public static boolean isInitializedForCC(obj_id craftingTracker) throws InterruptedException
    {
        if (!isIdValid(craftingTracker))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: isInitializedForCC called " + "with invalid craftingTracker");
            return false;
        }
        if (!hasObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_SCHEMATICS))
        {
            return false;
        }
        if (!utils.hasScriptVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_SCHEMATIC))
        {
            int[] schematicsCrcs = getIntArrayObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_SCHEMATICS);
            utils.setScriptVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_SCHEMATIC, schematicsCrcs[0]);
            boolean flag = getBooleanObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_TRACK_QUANTITY);
            utils.setScriptVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_TRACK_QUANTITY, flag);
            flag = getBooleanObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_TRACK_QUALITY);
            utils.setScriptVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_TRACK_QUALITY, flag);
            flag = getBooleanObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_TRACK_SLOTS);
            utils.setScriptVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_TRACK_SLOTS, flag);
            float minimumQuality = getFloatObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_MIN_QUALITY);
            if (minimumQuality < 0)
            {
                minimumQuality = 0;
            }
            utils.setScriptVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_MIN_QUALITY, minimumQuality);
            int minimumQuantity = getIntObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_MIN_QUANTITY);
            if (minimumQuantity < 1)
            {
                minimumQuantity = 1;
            }
            utils.setScriptVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_MIN_QUANTITY, minimumQuantity);
        }
        return true;
    }
    public static boolean getSchematics(Vector schematics, String schematic) throws InterruptedException
    {
        draft_schematic schematicData = getSchematicData(schematic);
        if (schematicData == null)
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: getSchematics could " + "not find data for schematic " + schematic);
            return false;
        }
        schematics.add(schematic);
        draft_schematic.slot[] slots = schematicData.getSlots();
        if (slots == null)
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: getSchematics could " + "not find slot info for schematic " + schematic);
            return false;
        }
        for (int i = 0; i < slots.length; ++i)
        {
            if ((slots[i].ingredientType == draft_schematic.IT_schematic || slots[i].ingredientType == draft_schematic.IT_schematicGeneric) && slots[i].ingredientName != null && !schematics.contains(slots[i].ingredientName))
            {
                if (!getSchematics(schematics, slots[i].ingredientName))
                {
                    return false;
                }
            }
        }
        return true;
    }
    public static float getMinimumQuality(obj_id craftingTracker) throws InterruptedException
    {
        if (!isIdValid(craftingTracker))
        {
            return 0;
        }
        if (!isInitializedForCC(craftingTracker))
        {
            return 0;
        }
        return utils.getFloatScriptVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_MIN_QUALITY);
    }
    public static int getMinimumQuantity(obj_id craftingTracker) throws InterruptedException
    {
        if (!isIdValid(craftingTracker))
        {
            return 1;
        }
        if (!isInitializedForCC(craftingTracker))
        {
            return 1;
        }
        return utils.getIntScriptVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_MIN_QUANTITY);
    }
    public static boolean addPlayerToSystem(obj_id craftingTracker, obj_id player) throws InterruptedException
    {
        if (!isIdValid(craftingTracker))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: addPlayerToSystem called " + "with invalid craftingTracker");
            return false;
        }
        if (!isInitializedForCC(craftingTracker))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: addPlayerToSystem called " + "with uninitialized craftingTracker " + craftingTracker);
            return false;
        }
        if (!isIdValid(player))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: addPlayerToSystem called " + "with invalid player");
            return false;
        }
        int playerLimit = getMaxPlayersPerProject();
        boolean trackQuantity = utils.getBooleanScriptVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_TRACK_QUANTITY);
        boolean trackQuality = utils.getBooleanScriptVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_TRACK_QUALITY);
        boolean trackSlots = utils.getBooleanScriptVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_TRACK_SLOTS);
        List constListPlayers = Arrays.asList(objvar_mangle.getMangledObjIdArrayObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_PLAYERS));
        if (constListPlayers.size() > playerLimit)
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: addPlayerToSystem called " + "for full project with player %TU", player);
            return false;
        }
        Vector players = new Vector(constListPlayers);
        players.add(player);
        obj_id[] tempPlayers = new obj_id[players.size()];
        players.toArray(tempPlayers);
        objvar_mangle.setMangledObjIdArrayObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_PLAYERS, tempPlayers);
        tempPlayers = null;
        if (trackQuantity || trackQuality)
        {
            int[] quantities = objvar_mangle.getMangledIntArrayObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_PLAYER_QUANTITY_TOTAL);
            int[] newQuantities = new int[quantities.length + 1];
            for (int i = 0; i < quantities.length; ++i)
            {
                newQuantities[i] = quantities[i];
            }
            newQuantities[quantities.length] = 0;
            objvar_mangle.setMangledIntArrayObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_PLAYER_QUANTITY_TOTAL, newQuantities);
        }
        if (trackQuality)
        {
            float[] qualities = objvar_mangle.getMangledFloatArrayObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_PLAYER_QUALITY_TOTAL);
            float[] newQualities = new float[qualities.length + 1];
            for (int i = 0; i < qualities.length; ++i)
            {
                newQualities[i] = qualities[i];
            }
            newQualities[qualities.length] = 0;
            objvar_mangle.setMangledFloatArrayObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_PLAYER_QUALITY_TOTAL, newQualities);
        }
        draft_schematic schematicData = getSchematicData(getSchematic(craftingTracker));
        if (trackSlots)
        {
            String slotdot = OBJVAR_COMMUNITY_CRAFTING_SLOTS + ".";
            draft_schematic.slot[] slots = schematicData.getSlots();
            for (int i = 0; i < slots.length; ++i)
            {
                String slotdotindex = slotdot + i;
                if (trackQuantity || trackQuality)
                {
                    int[] quantities = objvar_mangle.getMangledIntArrayObjVar(craftingTracker, slotdotindex + OBJVAR_COMMUNITY_CRAFTING_QUANTITY_SUFFIX);
                    int[] newQuantities = new int[quantities.length + 1];
                    for (int j = 0; j < quantities.length; ++j)
                    {
                        newQuantities[j] = quantities[j];
                    }
                    newQuantities[quantities.length] = 0;
                    objvar_mangle.setMangledIntArrayObjVar(craftingTracker, slotdotindex + OBJVAR_COMMUNITY_CRAFTING_QUANTITY_SUFFIX, newQuantities);
                }
                if (trackQuality)
                {
                    float[] qualities = objvar_mangle.getMangledFloatArrayObjVar(craftingTracker, slotdotindex + OBJVAR_COMMUNITY_CRAFTING_QUALITY_SUFFIX);
                    float[] newQualities = new float[qualities.length + 1];
                    for (int j = 0; j < qualities.length; ++j)
                    {
                        newQualities[j] = qualities[j];
                    }
                    newQualities[qualities.length] = 0;
                    objvar_mangle.setMangledFloatArrayObjVar(craftingTracker, slotdotindex + OBJVAR_COMMUNITY_CRAFTING_QUALITY_SUFFIX, newQualities);
                }
            }
        }
        attachScript(player, SCRIPT_COMMUNITY_CRAFTING_PLAYER);
        CustomerServiceLog("community_crafting", "community_crafting.scriptlib: added player %TU to community crafting project " + craftingTracker, player);
        CustomerServiceLog("community_crafting", "community_crafting.scriptlib: total players = " + players.size());
        return true;
    }
    public static int getCraftingPlayerIndex(obj_id craftingTracker, obj_id player) throws InterruptedException
    {
        if (!isIdValid(craftingTracker))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: isPlayerCrafting called " + "with invalid craftingTracker");
            return -1;
        }
        if (!isInitializedForCC(craftingTracker))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: isPlayerCrafting called " + "with uninitialized craftingTracker " + craftingTracker);
            return -1;
        }
        if (!isIdValid(player))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: isPlayerCrafting called " + "with invalid player");
            return -1;
        }
        obj_id[] players = objvar_mangle.getMangledObjIdArrayObjVar(craftingTracker, OBJVAR_COMMUNITY_CRAFTING_PLAYERS);
        if (players == null)
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: isPlayerCrafting " + "cannot find players list for craftingTracker " + craftingTracker);
            return -1;
        }
        for (int i = 1; i < players.length; ++i)
        {
            if (player == players[i])
            {
                return i;
            }
        }
        return -1;
    }
    public static float computeResourceAttributeQuality(resource_attribute[] resourceAttribs, draft_schematic schematicData, resource_weight[] resourceWeights, float[] objectAttributes) throws InterruptedException
    {
        if (resourceAttribs == null)
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: computeResourceAttributeQuality " + "called with invalid resource attribs");
            return -1;
        }
        if (schematicData == null)
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: computeResourceAttributeQuality " + "called with null schematic data");
            return -1;
        }
        if (objectAttributes == null || objectAttributes.length == 0)
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: computeResourceAttributeQuality " + "called with invalid objectAttributes");
            return -1;
        }
        draft_schematic.attribute[] attribs = schematicData.getAttribs();
        if (attribs == null || attribs.length == 0)
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: computeResourceAttributeQuality " + "got bad attributes from schematic data");
            return -1;
        }
        if (attribs.length != objectAttributes.length)
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: computeResourceAttributeQuality " + "mismatched attributes lengths (" + attribs.length + ", " + objectAttributes.length + ")");
            return -1;
        }
        float totalQuality = 0;
        int attribsFound = 0;
        for (int i = 0; i < attribs.length; ++i)
        {
            if (attribs[i].maxValue <= attribs[i].minValue)
            {
                continue;
            }
            String attribName = (attribs[i].name).getAsciiId();
            for (int j = 0; j < resourceWeights.length; ++j)
            {
                if (attribName.equals(resourceWeights[j].attribName))
                {
                    float totalWeight = 0;
                    int weightCount = 0;
                    resource_weight.weight[] weights = resourceWeights[j].weights;
                    for (int k = 0; k < weights.length; ++k)
                    {
                        for (int m = 0; m < resourceAttribs.length; ++m)
                        {
                            if (resourceAttribs[m] != null && (resourceAttribs[m].getName()).equals(craftinglib.RESOURCE_OBJVAR_NAMES[weights[k].resource]))
                            {
                                totalWeight += resourceAttribs[m].getValue() * weights[k].weight;
                                weightCount += weights[k].weight;
                            }
                        }
                    }
                    if (weightCount > 0)
                    {
                        totalWeight /= weightCount;
                        totalWeight /= 1000.0f;
                        totalWeight = totalWeight * totalWeight;
                        objectAttributes[i] += totalWeight;
                        totalQuality += totalWeight;
                        ++attribsFound;
                    }
                }
            }
        }
        if (attribsFound > 0)
        {
            return totalQuality / attribsFound;
        }
        return -1;
    }
    public static float computeComponentAttributeQuality(obj_id component, draft_schematic schematicData, float[] objectAttributes) throws InterruptedException
    {
        if (!isIdValid(component))
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: computeComponentAttributeQuality " + "called with invalid component");
            return -1;
        }
        if (schematicData == null)
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: computeComponentAttributeQuality " + "called with null schematic data");
            return -1;
        }
        if (objectAttributes == null || objectAttributes.length == 0)
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: computeComponentAttributeQuality " + "called with invalid objectAttributes");
            return -1;
        }
        draft_schematic.attribute[] componentSchematicAttribs = null;
        int componentSchematic = getSourceDraftSchematic(component);
        if (componentSchematic != 0)
        {
            draft_schematic componentSchematicData = getSchematicData(componentSchematic);
            if (componentSchematicData == null)
            {
                CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: computeComponentAttributeQuality " + "could not get schematic data from schematic " + componentSchematic + " for component " + component);
                return -1;
            }
            componentSchematicAttribs = componentSchematicData.getAttribs();
            if (componentSchematicAttribs == null || componentSchematicAttribs.length == 0)
            {
                CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: computeComponentAttributeQuality " + "could not get attributes from schematic " + componentSchematic + " for component " + component);
                return -1;
            }
        }
        draft_schematic.attribute[] attribs = schematicData.getAttribs();
        if (attribs == null || attribs.length == 0)
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: computeComponentAttributeQuality " + "got bad attributes from schematic data");
            return -1;
        }
        if (attribs.length != objectAttributes.length)
        {
            CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: computeComponentAttributeQuality " + "mismatched attributes lengths (" + attribs.length + ", " + objectAttributes.length + ")");
            return -1;
        }
        float totalQuality = 0;
        int attribsFound = 0;
        for (int i = 0; i < attribs.length; ++i)
        {
            String attribObjVarName = craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (attribs[i].name).getAsciiId();
            if (hasObjVar(component, attribObjVarName))
            {
                float value = getFloatObjVar(component, attribObjVarName);
                float minValue = 0;
                float maxValue = 1000.0f;
                if (componentSchematicAttribs != null)
                {
                    for (int j = 0; j < componentSchematicAttribs.length; ++j)
                    {
                        if (((attribs[i].name).getAsciiId()).equals((componentSchematicAttribs[j].name).getAsciiId()))
                        {
                            if (componentSchematicAttribs[j].maxValue > componentSchematicAttribs[j].minValue)
                            {
                                minValue = componentSchematicAttribs[j].minValue;
                                maxValue = componentSchematicAttribs[j].maxValue;
                            }
                        }
                    }
                }
                if (value >= minValue && value <= maxValue)
                {
                    value = (value - minValue) / (maxValue - minValue);
                    value = value * value;
                    objectAttributes[i] += value;
                    totalQuality += value;
                    ++attribsFound;
                }
                else 
                {
                    CustomerServiceLog("community_crafting", "WARNING community_crafting.scriptlib: computeComponentAttributeQuality " + "has invalid value " + value + ", range = " + minValue + "-" + maxValue);
                }
            }
        }
        if (attribsFound > 0)
        {
            return totalQuality / attribsFound;
        }
        return -1;
    }
    public static resource_weight[] getResourceWeightsFromScripts(obj_id object) throws InterruptedException
    {
        final String FUNCTION_NAME = "getAssemblyResourceWeights";
        if (!isIdValid(object))
        {
            return null;
        }
        String[] scripts = object.getScripts();
        if (scripts == null)
        {
            return null;
        }
        try
        {
            for (int i = 0; i < scripts.length; ++i)
            {
                script_class_loader loader = script_class_loader.getClassLoader(scripts[i]);
                if (loader == null)
                {
                    return null;
                }
                Class cls = loader.getMyClass();
                if (cls == null)
                {
                    return null;
                }
                Object obj = loader.getMyObject();
                if (obj == null)
                {
                    return null;
                }
                Hashtable methods = loader.getMyMethods();
                if (methods == null)
                {
                    return null;
                }
                java.lang.reflect.Method method = (java.lang.reflect.Method)methods.get(FUNCTION_NAME);
                if (method == null)
                {
                    try
                    {
                        method = cls.getDeclaredMethod(FUNCTION_NAME);
                        if (method != null)
                        {
                            if (!method.isAccessible())
                            {
                                method.setAccessible(true);
                            }
                        }
                    }
                    catch(NoSuchMethodException err)
                    {
                        method = script_class_loader.NO_METHOD;
                    }
                    if (method != null)
                    {
                        methods.put(method, method);
                    }
                }
                if (method != null && method != script_class_loader.NO_METHOD)
                {
                    Object[] args = new Object[0];
                    Object result = method.invoke(obj, args);
                    if (result != null && result instanceof resource_weight[])
                    {
                        return (resource_weight[])result;
                    }
                }
            }
        }
        catch(Exception err)
        {
        }
        return null;
    }
    public static void doubleSort(int[] values, obj_id[] ids) throws InterruptedException
    {
        quickSort(values, ids, 0, values.length - 1);
        insertionSort(values, ids, 0, values.length - 1);
    }
    public static void doubleSort(float[] values, obj_id[] ids) throws InterruptedException
    {
        quickSort(values, ids, 0, values.length - 1);
        insertionSort(values, ids, 0, values.length - 1);
    }
    public static void quickSort(int[] values, obj_id[] ids, int lo, int hi) throws InterruptedException
    {
        final int M = 4;
        if (hi - lo > M)
        {
            int i = (hi + lo) / 2;
            if (values[lo] < values[i])
            {
                swap(values, ids, lo, i);
            }
            if (values[lo] < values[hi])
            {
                swap(values, ids, lo, hi);
            }
            if (values[i] < values[hi])
            {
                swap(values, ids, i, hi);
            }
            int j = hi - 1;
            swap(values, ids, i, j);
            i = lo;
            int v = values[j];
            for (; ; )
            {
                while (values[++i] > v);
                while (values[--j] < v);
                if (j < i)
                {
                    break;
                }
                swap(values, ids, i, j);
            }
            swap(values, ids, i, hi - 1);
            quickSort(values, ids, lo, j);
            quickSort(values, ids, i + 1, hi);
        }
    }
    public static void insertionSort(int[] values, obj_id[] ids, int lo, int hi) throws InterruptedException
    {
        for (int i = lo + 1; i <= hi; i++)
        {
            int v = values[i];
            obj_id w = ids[i];
            int j = i;
            while ((j > lo) && (values[j - 1] < v))
            {
                values[j] = values[j - 1];
                ids[j] = ids[j - 1];
                j--;
            }
            values[j] = v;
            ids[j] = w;
        }
    }
    public static void swap(int[] values, obj_id[] ids, int i, int j) throws InterruptedException
    {
        int T = values[i];
        values[i] = values[j];
        values[j] = T;
        obj_id t = ids[i];
        ids[i] = ids[j];
        ids[j] = t;
    }
    public static void quickSort(float[] values, obj_id[] ids, int lo, int hi) throws InterruptedException
    {
        final int M = 4;
        if (hi - lo > M)
        {
            int i = (hi + lo) / 2;
            if (values[lo] < values[i])
            {
                swap(values, ids, lo, i);
            }
            if (values[lo] < values[hi])
            {
                swap(values, ids, lo, hi);
            }
            if (values[i] < values[hi])
            {
                swap(values, ids, i, hi);
            }
            int j = hi - 1;
            swap(values, ids, i, j);
            i = lo;
            float v = values[j];
            for (; ; )
            {
                while (values[++i] > v);
                while (values[--j] < v);
                if (j < i)
                {
                    break;
                }
                swap(values, ids, i, j);
            }
            swap(values, ids, i, hi - 1);
            quickSort(values, ids, lo, j);
            quickSort(values, ids, i + 1, hi);
        }
    }
    public static void insertionSort(float[] values, obj_id[] ids, int lo, int hi) throws InterruptedException
    {
        for (int i = lo + 1; i <= hi; i++)
        {
            float v = values[i];
            obj_id w = ids[i];
            int j = i;
            while ((j > lo) && (values[j - 1] < v))
            {
                values[j] = values[j - 1];
                ids[j] = ids[j - 1];
                j--;
            }
            values[j] = v;
            ids[j] = w;
        }
    }
    public static void swap(float[] values, obj_id[] ids, int i, int j) throws InterruptedException
    {
        float T = values[i];
        values[i] = values[j];
        values[j] = T;
        obj_id t = ids[i];
        ids[i] = ids[j];
        ids[j] = t;
    }
}
