package script.library;

import script.*;

import java.util.Arrays;
import java.util.Vector;

public class storyteller extends script.base_script
{
    public storyteller()
    {
    }
    public static final String STORYTELLER_DATATABLE = "datatables/item/master_item/storyteller_item.iff";
    public static final String STATIC_ITEM_DATATABLE = "datatables/item/master_item/master_item.iff";
    public static final String THEATER_MODE = "theater_mode";
    public static final String EFFECT_TOKEN_NAME = "storytellerEffectName";
    public static final String EFFECT_ACTIVE_OBJVAR = "storytellerPersistedEffectActive";
    public static final String STORYTELLER_THEATER_CONTROL_SCRIPT = "systems.storyteller.theater_controller";
    public static final String STORYTELLER_PROP_CONTROL_SCRIPT = "systems.storyteller.prop_controller";
    public static final String STORYTELLER_NPC_CONTROL_SCRIPT = "systems.storyteller.npc_controller";
    public static final String STORYTELLER_DAILY_COUNT_OBJVAR = "storytellerToken.dailyCount";
    public static final String STORYTELLER_DAILY_COUNT_RESET = "storytellerToken.dailyResetTime";
    public static final String STORYTELLER_OBJECT_OBJVAR = "storytellerid";
    public static final int BLUEPRINT_MAX_NUM_OBJECTS = 200;
    public static final float BLUEPRINT_DEFAULT_RADIUS = 100.0f;
    public static final String BLUEPRINT_OBJECTS_OBJVAR = "blueprintObjects";
    public static final String BLUEPRINT_AUTHOR_OBJVAR = "blueprintAuthor";
    public static final String BLUEPRINT_AUTHOR_NAME_OBJVAR = "blueprintAuthorName";
    public static final String BLUEPRINT_DECRIPTION_OBJVAR = "blueprintDescription";
    public static final int BLUEPRINT_CREATE_CYCLE_MAX = 30;
    public static final int BLUEPRINT_CREATE_CYCLE_LOOP = 25;
    public static final int BLUEPRINT_VERSION_NUM = 1;
    public static final String BLUEPRINT_VERSION_OBJVAR = "blueprintVersion";
    public static final int BLUEPRINT_TOKEN_LOADED = 1;
    public static final int BLUEPRINT_TOKEN_NEEDED = 0;
    public static final int PROP = 0;
    public static final int STATIC_EFFECT = 1;
    public static final int IMMEDIATE_EFFECT = 2;
    public static final int COMBAT_NPC = 3;
    public static final int FLAVOR_NPC = 4;
    public static final int OTHER = 5;
    public static final int COSTUME = 6;
    public static final int THEATER = 7;
    public static final int DEFAULT_PROP_CLEANUP_TIME = 16 * 60 * 60;
    public static final int DEFAULT_NPC_CLEANUP_TIME = 16 * 60 * 60;
    public static final String VAR_AUTODECLINE_STORY_INVITES = "decline_story_invites";
    public static int getTokenType(obj_id token) throws InterruptedException
    {
        return dataTableGetInt(STORYTELLER_DATATABLE, getStaticItemName(token), "type");
    }
    public static int getTokenType(String tokenName) throws InterruptedException
    {
        return dataTableGetInt(STORYTELLER_DATATABLE, tokenName, "type");
    }
    public static boolean isProp(obj_id token) throws InterruptedException
    {
        return (dataTableGetInt(STORYTELLER_DATATABLE, getStaticItemName(token), "type") == PROP);
    }
    public static boolean isAnyNpc(obj_id token) throws InterruptedException
    {
        return (isFlavorNpc(token) || isCombatNpc(token));
    }
    public static boolean isFlavorNpc(obj_id token) throws InterruptedException
    {
        return (dataTableGetInt(STORYTELLER_DATATABLE, getStaticItemName(token), "type") == FLAVOR_NPC);
    }
    public static boolean isFlavorNpc(String tokenName) throws InterruptedException
    {
        return (dataTableGetInt(STORYTELLER_DATATABLE, tokenName, "type") == FLAVOR_NPC);
    }
    public static boolean isCombatNpc(obj_id token) throws InterruptedException
    {
        return (dataTableGetInt(STORYTELLER_DATATABLE, getStaticItemName(token), "type") == COMBAT_NPC);
    }
    public static boolean isAnyEffect(String st_effect_token) throws InterruptedException
    {
        return (isStaticEffect(st_effect_token) || isImmediateEffect(st_effect_token));
    }
    public static boolean isAnyEffect(obj_id token) throws InterruptedException
    {
        return (isStaticEffect(token) || isImmediateEffect(token));
    }
    public static boolean isStaticEffect(String st_effect_token) throws InterruptedException
    {
        return (dataTableGetInt(STORYTELLER_DATATABLE, st_effect_token, "type") == STATIC_EFFECT);
    }
    public static boolean isStaticEffect(obj_id token) throws InterruptedException
    {
        return (dataTableGetInt(STORYTELLER_DATATABLE, getStaticItemName(token), "type") == STATIC_EFFECT);
    }
    public static boolean isImmediateEffect(String st_effect_token) throws InterruptedException
    {
        return (dataTableGetInt(STORYTELLER_DATATABLE, st_effect_token, "type") == IMMEDIATE_EFFECT);
    }
    public static boolean isImmediateEffect(obj_id token) throws InterruptedException
    {
        return (dataTableGetInt(STORYTELLER_DATATABLE, getStaticItemName(token), "type") == IMMEDIATE_EFFECT);
    }
    public static boolean isTheater(obj_id token) throws InterruptedException
    {
        return (dataTableGetInt(STORYTELLER_DATATABLE, getStaticItemName(token), "type") == THEATER);
    }
    public static boolean isOther(obj_id token) throws InterruptedException
    {
        return (dataTableGetInt(STORYTELLER_DATATABLE, getStaticItemName(token), "type") == OTHER);
    }
    public static boolean isAnyStorytellerItem(obj_id token) throws InterruptedException
    {
        return (dataTableGetInt(STORYTELLER_DATATABLE, getStaticItemName(token), "type") != -1);
    }
    public static String getEffectName(String st_effect_name) throws InterruptedException
    {
        return dataTableGetString(STORYTELLER_DATATABLE, st_effect_name, "template_name");
    }
    public static String getNpcTemplate(obj_id token) throws InterruptedException
    {
        return dataTableGetString(STORYTELLER_DATATABLE, getStaticItemName(token), "template_name");
    }
    public static String getNpcTemplate(String tokenName) throws InterruptedException
    {
        return dataTableGetString(STORYTELLER_DATATABLE, tokenName, "template_name");
    }
    public static float getTheaterBuildoutRadius(obj_id theater) throws InterruptedException
    {
        return dataTableGetFloat(STORYTELLER_DATATABLE, getStaticItemName(theater), "buildout_radius");
    }
    public static obj_id createTheaterObject(obj_id token, boolean inBuildout) throws InterruptedException
    {
        obj_id player = getTopMostContainer(token);
        if (!isPlayer(player))
        {
            return null;
        }
        return createTheaterObject(token, inBuildout, getLocation(player), getYaw(player));
    }
    public static obj_id createTheaterObject(obj_id token, boolean inBuildout, location createLoc, float yaw) throws InterruptedException
    {
        String itemName = getStaticItemName(token);
        int row = dataTableSearchColumnForString(itemName, "name", storyteller.STORYTELLER_DATATABLE);
        if (row == -1)
        {
            return null;
        }
        dictionary dict = dataTableGetRow(storyteller.STORYTELLER_DATATABLE, itemName);
        String template = dict.getString("template_name");
        obj_id theater = create.object(template, createLoc);
        setYaw(theater, yaw);
        if (!isIdValid(theater))
        {
            return null;
        }
        String objVarString = dict.getString("objvar");
        String scriptString = dict.getString("scripts");
        static_item.setObjVarString(theater, objVarString);
        static_item.setScriptString(theater, scriptString);
        setStaticItemName(theater, getStaticItemName(token));
        setObjVar(theater, storyteller.THEATER_MODE, inBuildout);
        attachScript(theater, STORYTELLER_THEATER_CONTROL_SCRIPT);
        destroyObject(token);
        return theater;
    }
    public static obj_id createPropObject(obj_id token, boolean inBuildout) throws InterruptedException
    {
        obj_id player = utils.getContainingPlayer(token);
        if (!isIdValid(player))
        {
            return null;
        }
        return createPropObject(token, player, inBuildout, getLocation(player), getYaw(player));
    }
    public static obj_id createPropObject(obj_id token, obj_id player, boolean inBuildout, location createLoc, float yaw) throws InterruptedException
    {
        obj_id prop = null;
        if (canDeployStorytellerToken(player, createLoc, token))
        {
            String itemName = getStaticItemName(token);
            int row = dataTableSearchColumnForString(itemName, "name", STORYTELLER_DATATABLE);
            if (row == -1)
            {
                return null;
            }
            dictionary dict = dataTableGetRow(STORYTELLER_DATATABLE, itemName);
            String template = dict.getString("template_name");
            String objVarString = dict.getString("objvar");
            String scriptString = dict.getString("scripts");
            prop = create.object(template, createLoc);
            if (isIdValid(prop))
            {
                setYaw(prop, yaw);
                setInvulnerable(prop, true);
                static_item.setObjVarString(prop, objVarString);
                static_item.setScriptString(prop, scriptString);
                setStaticItemName(prop, getStaticItemName(token));
                if (utils.hasScriptVar(player, "storytellerAssistant"))
                {
                    obj_id storytellerId = utils.getObjIdScriptVar(player, "storytellerAssistant");
                    String storytellerName = utils.getStringScriptVar(player, "storytellerAssistantName");
                    setObjVar(prop, "storytellerid", storytellerId);
                    setObjVar(prop, "storytellerName", storytellerName);
                }
                else 
                {
                    setObjVar(prop, "storytellerid", player);
                    setObjVar(prop, "storytellerName", getName(player));
                }
                String finalName = dataTableGetRow(STATIC_ITEM_DATATABLE, itemName).getString("string_name");
                setObjVar(prop, "storytellerCreationLoc", createLoc);
                setObjVar(prop, "storytellerTokenName", itemName);
                attachScript(prop, STORYTELLER_PROP_CONTROL_SCRIPT);
                setName(prop, finalName);
                handleTokenUsage(token);
                String logMsg = "(" + player + ")" + getName(player) + " is deploying a prop storyteller token: " + getStaticItemName(token) + " at " + createLoc;
                CustomerServiceLog("storyteller", logMsg);
            }
        }
        return prop;
    }
    public static obj_id createPropObjectNoToken(String tokenName, obj_id player, boolean inBuildout, location createLoc, float yaw) throws InterruptedException
    {
        obj_id prop = null;
        if (canDeployStorytellerToken(player, createLoc, tokenName))
        {
            int row = dataTableSearchColumnForString(tokenName, "name", STORYTELLER_DATATABLE);
            if (row == -1)
            {
                return null;
            }
            dictionary dict = dataTableGetRow(STORYTELLER_DATATABLE, tokenName);
            String template = dict.getString("template_name");
            String objVarString = dict.getString("objvar");
            String scriptString = dict.getString("scripts");
            prop = create.object(template, createLoc);
            if (isIdValid(prop))
            {
                setYaw(prop, yaw);
                setInvulnerable(prop, true);
                static_item.setObjVarString(prop, objVarString);
                static_item.setScriptString(prop, scriptString);
                setStaticItemName(prop, tokenName);
                if (utils.hasScriptVar(player, "storytellerAssistant"))
                {
                    obj_id storytellerId = utils.getObjIdScriptVar(player, "storytellerAssistant");
                    String storytellerName = utils.getStringScriptVar(player, "storytellerAssistantName");
                    setObjVar(prop, "storytellerid", storytellerId);
                    setObjVar(prop, "storytellerName", storytellerName);
                }
                else 
                {
                    setObjVar(prop, "storytellerid", player);
                    setObjVar(prop, "storytellerName", getName(player));
                }
                String finalName = dataTableGetRow(STATIC_ITEM_DATATABLE, tokenName).getString("string_name");
                setObjVar(prop, "storytellerCreationLoc", createLoc);
                setObjVar(prop, "storytellerTokenName", tokenName);
                attachScript(prop, STORYTELLER_PROP_CONTROL_SCRIPT);
                setName(prop, finalName);
                String logMsg = "(" + player + ")" + getName(player) + " is deploying a prop storyteller token: " + tokenName + " at " + createLoc;
                CustomerServiceLog("storyteller", logMsg);
            }
        }
        return prop;
    }
    public static obj_id createNpcAtLocation(obj_id token) throws InterruptedException
    {
        obj_id player = utils.getContainingPlayer(token);
        return createNpcAtLocation(token, player, getLocation(player), getYaw(player));
    }
    public static obj_id createNpcAtLocation(obj_id token, obj_id player, location createLoc, float yaw) throws InterruptedException
    {
        obj_id npc = null;
        if (canDeployStorytellerToken(player, createLoc, token))
        {
            String toCreate = storyteller.getNpcTemplate(token);
            npc = create.object(toCreate, createLoc);
            if (isIdValid(npc))
            {
                if (utils.hasScriptVar(player, "storytellerAssistant"))
                {
                    obj_id storytellerId = utils.getObjIdScriptVar(player, "storytellerAssistant");
                    String storytellerName = utils.getStringScriptVar(player, "storytellerAssistantName");
                    setObjVar(npc, "storytellerid", storytellerId);
                    setObjVar(npc, "storytellerName", storytellerName);
                }
                else 
                {
                    setObjVar(npc, "storytellerid", player);
                    setObjVar(npc, "storytellerName", getName(player));
                }
                setObjVar(npc, "storytellerCreationLoc", createLoc);
                setObjVar(npc, "storytellerTokenName", getStaticItemName(token));
                setYaw(npc, yaw);
                setStaticItemName(npc, getStaticItemName(token));
                if (storyteller.isFlavorNpc(token))
                {
                    setInvulnerable(npc, true);
                }
                ai_lib.setDefaultCalmBehavior(npc, ai_lib.BEHAVIOR_SENTINEL);
                if (hasObjVar(token, "storytellerNpcCombatLevel"))
                {
                    int storytellerNpcCombatLevel = getIntObjVar(token, "storytellerNpcCombatLevel");
                    if (storytellerNpcCombatLevel < 1 || storytellerNpcCombatLevel > 90)
                    {
                        storytellerNpcCombatLevel = 1;
                    }
                    dictionary creatureDict = utils.dataTableGetRow(create.CREATURE_TABLE, toCreate);
                    if (creatureDict != null)
                    {
                        create.initializeCreature(npc, toCreate, creatureDict, storytellerNpcCombatLevel);
                    }
                }
                attachScript(npc, STORYTELLER_NPC_CONTROL_SCRIPT);
                String logMsg = "(" + player + ")" + getName(player) + " is deploying an npc storyteller token: " + getStaticItemName(token) + " at " + createLoc;
                CustomerServiceLog("storyteller", logMsg);
            }
        }
        return npc;
    }
    public static obj_id createNpcAtLocationNoToken(String tokenName, obj_id player, location createLoc, float yaw) throws InterruptedException
    {
        return createNpcAtLocationNoToken(tokenName, player, createLoc, yaw, 0);
    }
    public static obj_id createNpcAtLocationNoToken(String tokenName, obj_id player, location createLoc, float yaw, int combatLevel) throws InterruptedException
    {
        return createNpcAtLocationNoToken(tokenName, player, createLoc, yaw, combatLevel, 0);
    }
    public static obj_id createNpcAtLocationNoToken(String tokenName, obj_id player, location createLoc, float yaw, int combatLevel, int difficulty) throws InterruptedException
    {
        obj_id npc = null;
        if (canDeployStorytellerToken(player, createLoc, tokenName))
        {
            String toCreate = storyteller.getNpcTemplate(tokenName);
            npc = create.object(toCreate, createLoc);
            if (isIdValid(npc))
            {
                if (utils.hasScriptVar(player, "storytellerAssistant"))
                {
                    obj_id storytellerId = utils.getObjIdScriptVar(player, "storytellerAssistant");
                    String storytellerName = utils.getStringScriptVar(player, "storytellerAssistantName");
                    setObjVar(npc, "storytellerid", storytellerId);
                    setObjVar(npc, "storytellerName", storytellerName);
                }
                else 
                {
                    setObjVar(npc, "storytellerid", player);
                    setObjVar(npc, "storytellerName", getName(player));
                }
                setObjVar(npc, "storytellerCreationLoc", createLoc);
                setObjVar(npc, "storytellerTokenName", tokenName);
                setYaw(npc, yaw);
                setStaticItemName(npc, tokenName);
                if (storyteller.isFlavorNpc(tokenName))
                {
                    setInvulnerable(npc, true);
                }
                ai_lib.setDefaultCalmBehavior(npc, ai_lib.BEHAVIOR_SENTINEL);
                if (combatLevel > 0)
                {
                    if (combatLevel > 90)
                    {
                        combatLevel = 90;
                    }
                    dictionary creatureDict = utils.dataTableGetRow(create.CREATURE_TABLE, toCreate);
                    if (creatureDict != null)
                    {
                        creatureDict.put("difficultyClass", difficulty);
                        create.initializeCreature(npc, toCreate, creatureDict, combatLevel);
                        setNpcDifficulty(npc, difficulty);
                    }
                }
                attachScript(npc, STORYTELLER_NPC_CONTROL_SCRIPT);
                String logMsg = "(" + player + ")" + getName(player) + " is deploying an npc storyteller token: " + tokenName + " at " + createLoc;
                CustomerServiceLog("storyteller", logMsg);
            }
        }
        return npc;
    }
    public static boolean isStorytellerNpc(obj_id npc) throws InterruptedException
    {
        return hasObjVar(npc, "storytellerid");
    }
    public static boolean isStorytellerObject(obj_id object) throws InterruptedException
    {
        return hasObjVar(object, "storytellerid");
    }
    public static void removeStorytellerPersistedEffect(obj_id self) throws InterruptedException
    {
        obj_id[] players = getPlayerCreaturesInRange(getLocation(self), 165.0f);
        if (players != null && players.length > 0)
        {
            stopClientEffectObjByLabel(players, self, "storyteller_persisted_effect", false);
            removeObjVar(self, storyteller.EFFECT_ACTIVE_OBJVAR);
            removeTriggerVolume("storytellerPersistedEffect");
        }
    }
    public static boolean allowTokenPlacementInInteriors(obj_id token) throws InterruptedException
    {
        return allowTokenPlacementInInteriors(getStaticItemName(token));
    }
    public static boolean allowTokenPlacementInInteriors(String tokenName) throws InterruptedException
    {
        int allowInInteriors = dataTableGetInt(STORYTELLER_DATATABLE, tokenName, "allow_interior");
        return allowInInteriors == 1;
    }
    public static boolean allowTokenPlacementOnRoofs(obj_id token) throws InterruptedException
    {
        return allowTokenPlacementOnRoofs(getStaticItemName(token));
    }
    public static boolean allowTokenPlacementOnRoofs(String tokenName) throws InterruptedException
    {
        int allowOnRoofs = dataTableGetInt(STORYTELLER_DATATABLE, tokenName, "allow_roof");
        return allowOnRoofs == 1;
    }
    public static obj_id getStorytellerBeingAssisted(obj_id player) throws InterruptedException
    {
        obj_id storytellerBeingAssisted = null;
        if (utils.hasScriptVar(player, "storytellerAssistant"))
        {
            storytellerBeingAssisted = utils.getObjIdScriptVar(player, "storytellerAssistant");
        }
        return storytellerBeingAssisted;
    }
    public static location rotateLocationXZ(location locOrigin, location locPoint, float fltAngle) throws InterruptedException
    {
        float dx = locPoint.x - locOrigin.x;
        float dz = locPoint.z - locOrigin.z;
        float fltRadians = (float)Math.toRadians(fltAngle);
        float fltC = (float)Math.cos(fltRadians);
        float fltS = (float)Math.sin(fltRadians);
        location locNewOffset = (location)locOrigin.clone();
        locNewOffset.x += (dx * fltC) + (dz * fltS);
        locNewOffset.y = locPoint.y;
        locNewOffset.z += -(dx * fltS) + (dz * fltC);
        return locNewOffset;
    }
    public static int getTokenDailyUsageAmount(obj_id token) throws InterruptedException
    {
        return getTokenDailyUsageAmount(getStaticItemName(token));
    }
    public static int getTokenDailyUsageAmount(String tokenName) throws InterruptedException
    {
        return dataTableGetInt(STORYTELLER_DATATABLE, tokenName, "daily_uses");
    }
    public static boolean isTokenFlaggedWithDailyUsage(obj_id token) throws InterruptedException
    {
        return isTokenFlaggedWithDailyUsage(getStaticItemName(token));
    }
    public static boolean isTokenFlaggedWithDailyUsage(String tokenName) throws InterruptedException
    {
        return getTokenDailyUsageAmount(tokenName) > 0;
    }
    public static int getTokenDailyUsesAvailable(obj_id token) throws InterruptedException
    {
        int num = 0;
        if (isTokenFlaggedWithDailyUsage(getStaticItemName(token)))
        {
            int dailyUses = getTokenDailyUsageAmount(token);
            if (dailyUses < 0)
            {
                num = 9999;
            }
            else if (hasObjVar(token, STORYTELLER_DAILY_COUNT_OBJVAR))
            {
                int usedUses = getIntObjVar(token, STORYTELLER_DAILY_COUNT_OBJVAR);
                num = dailyUses - usedUses;
            }
            else 
            {
                num = dailyUses;
            }
        }
        return num;
    }
    public static boolean hasTokenUsesAvailable(obj_id token) throws InterruptedException
    {
        if (isTokenFlaggedWithDailyUsage(getStaticItemName(token)))
        {
            if (getTokenDailyUsesAvailable(token) > 0)
            {
                return true;
            }
        }
        else 
        {
            return true;
        }
        return false;
    }
    public static void handleTokenUsage(obj_id token) throws InterruptedException
    {
        int dailyUses = getTokenDailyUsageAmount(token);
        if (dailyUses == 0)
        {
            decrementCount(token);
        }
        else if (dailyUses > 0)
        {
            int dailyCount = 1;
            if (hasObjVar(token, STORYTELLER_DAILY_COUNT_OBJVAR))
            {
                dailyCount = getIntObjVar(token, STORYTELLER_DAILY_COUNT_OBJVAR) + 1;
            }
            setObjVar(token, STORYTELLER_DAILY_COUNT_OBJVAR, dailyCount);
            sendDirtyAttributesNotification(token);
        }
    }
    public static void resetTokenDailyCount(obj_id token) throws InterruptedException
    {
        if (isTokenFlaggedWithDailyUsage(token))
        {
            if (!hasObjVar(token, storyteller.STORYTELLER_DAILY_COUNT_RESET))
            {
                setTokenDailyCountResetTime(token);
            }
            else 
            {
                int currentTime = getCalendarTime();
                int alarmTime = getIntObjVar(token, storyteller.STORYTELLER_DAILY_COUNT_RESET);
                if (currentTime >= alarmTime)
                {
                    setTokenDailyCountResetTime(token);
                    if (hasObjVar(token, STORYTELLER_DAILY_COUNT_OBJVAR))
                    {
                        removeObjVar(token, STORYTELLER_DAILY_COUNT_OBJVAR);
                    }
                }
            }
        }
    }
    public static void setTokenDailyCountResetTime(obj_id token) throws InterruptedException
    {
        int timeUntilAlarm = createDailyAlarmClock(token, "storytellerEffectTokenDailyAlarm", null, 4, 0, 0);
        int alarmStamp = getCalendarTime() + timeUntilAlarm;
        setObjVar(token, storyteller.STORYTELLER_DAILY_COUNT_RESET, alarmStamp);
    }
    public static boolean canDeployStorytellerToken(obj_id player, location here, obj_id token) throws InterruptedException
    {
        if (!storyteller.hasTokenUsesAvailable(token))
        {
            sendSystemMessage(player, new string_id("storyteller", "placement_daily_uses_expended"));
            return false;
        }
        return canDeployStorytellerToken(player, here, getStaticItemName(token));
    }
    public static boolean canDeployStorytellerToken(obj_id player, location here, String tokenName) throws InterruptedException
    {
        if (!isIdValid(player) || here == null)
        {
            return false;
        }
        if (isGod(player))
        {
            String logMsg = "(" + player + ")" + getName(player) + " is using GodMode override to deploy or movea storyteller object: " + tokenName + " at " + here;
            CustomerServiceLog("storyteller", logMsg);
            if (!utils.hasScriptVar(player, "storyteller.godModeStopOverrideMessages"))
            {
                sendSystemMessage(player, new string_id("storyteller", "placement_god_mode"));
            }
            return true;
        }
        if (isFreeTrialAccount(player))
        {
            sendSystemMessage(player, new string_id("storyteller", "placement_no_trial_accounts"));
            return false;
        }
        obj_id myCell = here.cell;
        obj_id myContainer = getTopMostContainer(myCell);
        obj_id storytellerBeingAssisted = getStorytellerBeingAssisted(player);
        if (!isIdValid(storytellerBeingAssisted))
        {
            storytellerBeingAssisted = player;
        }
        if (instance.isInInstanceArea(player))
        {
            sendSystemMessage(player, new string_id("storyteller", "placement_not_in_this_location"));
            return false;
        }
        String planet = here.area;
        if (planet.startsWith("space_"))
        {
            if (isIdValid(myCell) && allowTokenPlacementInInteriors(tokenName))
            {
                obj_id owner = getOwner(myContainer);
                if (!isIdValid(owner) || owner != player || owner != storytellerBeingAssisted)
                {
                    sendSystemMessage(player, new string_id("storyteller", "placement_not_ship_owner"));
                    return false;
                }
                if (getState(player, STATE_SHIP_INTERIOR) != 1 || space_utils.isInStation(player))
                {
                    sendSystemMessage(player, new string_id("storyteller", "placement_not_in_station"));
                    return false;
                }
            }
            else 
            {
                sendSystemMessage(player, new string_id("storyteller", "placement_not_in_space"));
                return false;
            }
        }
        else 
        {
            if (isIdValid(myCell))
            {
                if (!allowTokenPlacementInInteriors(tokenName))
                {
                    sendSystemMessage(player, new string_id("storyteller", "placement_not_in_a_building"));
                    return false;
                }
                else 
                {
                    if (!player_structure.isAdmin(myContainer, player) && !player_structure.isAdmin(myContainer, storytellerBeingAssisted))
                    {
                        sendSystemMessage(player, new string_id("storyteller", "placement_not_building_admin"));
                        return false;
                    }
                }
            }
            else 
            {
                if (planet.equals("mustafar") || planet.startsWith("kashyyyk"))
                {
                    sendSystemMessage(player, new string_id("storyteller", "placement_not_on_blocked_planet"));
                    return false;
                }
                region[] rgnTest = getRegionsWithBuildableAtPoint(here, regions.BUILD_FALSE);
                if (rgnTest != null)
                {
                    sendSystemMessage(player, new string_id("storyteller", "placement_not_in_municipal_zone"));
                    return false;
                }
                if (isInStorytellerBlockedRegion(here))
                {
                    sendSystemMessage(player, new string_id("storyteller", "placement_not_in_this_location"));
                    return false;
                }
                obj_id whatAmIStandingOn = getStandingOn(player);
                if (isIdValid(whatAmIStandingOn) && player_structure.isBuilding(whatAmIStandingOn))
                {
                    if (!allowTokenPlacementOnRoofs(tokenName))
                    {
                        sendSystemMessage(player, new string_id("storyteller", "placement_not_on_a_building"));
                        return false;
                    }
                    else 
                    {
                        if (!player_structure.isAdmin(whatAmIStandingOn, player) && !player_structure.isAdmin(whatAmIStandingOn, storytellerBeingAssisted))
                        {
                            sendSystemMessage(player, new string_id("storyteller", "placement_not_building_admin"));
                            return false;
                        }
                    }
                }
                if (locations.isInMissionCity(here))
                {
                    int city_id = getCityAtLocation(here, 0);
                    if (city_id == 0)
                    {
                        sendSystemMessage(player, new string_id("storyteller", "placement_not_in_municipal_zone"));
                        return false;
                    }
                    if (cityExists(city_id) && city.isCityZoned(city_id) && isIdValid(player))
                    {
                        if (!city.hasStorytellerZoningRights(player, city_id))
                        {
                            sendSystemMessage(player, new string_id("storyteller", "placement_no_zoning_rights"));
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    public static boolean isInStorytellerBlockedRegion(location here) throws InterruptedException
    {
        region[] regionsHere = getRegionsAtPoint(here);
        if (regionsHere != null && regionsHere.length > 0)
        {
            for (region testRegion : regionsHere) {
                int nameCheck = testRegion.getName().indexOf("storytellerblocked");
                if (nameCheck > -1) {
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean allowedToSeeStorytellerObject(obj_id object, obj_id player) throws InterruptedException
    {
        obj_id storyteller = getObjIdObjVar(object, "storytellerid");
        if (isIdValid(storyteller))
        {
            if (allowedToUseStorytellerObject(object, player))
            {
                return true;
            }
            if (utils.hasScriptVar(player, "storytellerid"))
            {
                obj_id playersStoryteller = utils.getObjIdScriptVar(player, "storytellerid");
                if (isIdValid(playersStoryteller) && storyteller == playersStoryteller)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean allowedToUseStorytellerObject(obj_id object, obj_id player) throws InterruptedException
    {
        obj_id storyteller = getObjIdObjVar(object, "storytellerid");
        if (isIdValid(storyteller))
        {
            if (storyteller == player)
            {
                return true;
            }
            else if (utils.hasScriptVar(player, "storytellerAssistant"))
            {
                obj_id whoAmIAssisting = utils.getObjIdScriptVar(player, "storytellerAssistant");
                if (isIdValid(whoAmIAssisting) && storyteller == whoAmIAssisting)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean inSameStory(obj_id target1, obj_id target2) throws InterruptedException
    {
        obj_id storyteller_id_1 = obj_id.NULL_ID;
        obj_id storyteller_id_2 = obj_id.NULL_ID;
        if (utils.hasScriptVar(target1, "storytellerid"))
        {
            storyteller_id_1 = utils.getObjIdScriptVar(target1, "storytellerid");
        }
        if (utils.hasScriptVar(target2, "storytellerid"))
        {
            storyteller_id_2 = utils.getObjIdScriptVar(target2, "storytellerid");
        }
        if (isIdValid(storyteller_id_1) && isIdValid(storyteller_id_2))
        {
            if (storyteller_id_1 == storyteller_id_2)
            {
                return true;
            }
        }
        else if (isIdValid(storyteller_id_1) && storyteller_id_1 == target2)
        {
            return true;
        }
        else if (isIdValid(storyteller_id_2) && storyteller_id_2 == target1)
        {
            return true;
        }
        return false;
    }
    public static void showStorytellerEffectsInAreaToPlayer(obj_id player, obj_id storytellerPlayer) throws InterruptedException
    {
        obj_id[] storytellerObjects = getAllObjectsWithObjVar(getLocation(player), 165.0f, "storytellerid");
        if (storytellerObjects != null && storytellerObjects.length > 0)
        {
            obj_id myStoryteller;
            obj_id[] playerInStory;
            String effectName;
            for (obj_id object : storytellerObjects) {
                myStoryteller = getObjIdObjVar(object, "storytellerid");
                if (isIdValid(myStoryteller)) {
                    if (myStoryteller == storytellerPlayer)
                        if (hasObjVar(object, storyteller.EFFECT_ACTIVE_OBJVAR) && hasObjVar(object, storyteller.EFFECT_TOKEN_NAME)) {
                            effectName = storyteller.getEffectName(getStringObjVar(object, storyteller.EFFECT_TOKEN_NAME));
                            playerInStory = new obj_id[]{player};
                            playClientEffectObj(playerInStory, effectName, object, "", null, "storyteller_persisted_effect");
                        }
                }
            }
        }
    }
    public static void stopStorytellerEffectsInAreaToPlayer(obj_id player, obj_id storytellerPlayer) throws InterruptedException
    {
        obj_id[] storytellerObjects = getAllObjectsWithObjVar(getLocation(player), 165.0f, "storytellerid");
        if (storytellerObjects != null && storytellerObjects.length > 0)
        {
            obj_id myStoryteller;
            for (obj_id object : storytellerObjects) {
                myStoryteller = getObjIdObjVar(object, "storytellerid");
                if (isIdValid(myStoryteller)) {
                    if (myStoryteller == storytellerPlayer) {
                        if (hasObjVar(object, storyteller.EFFECT_ACTIVE_OBJVAR) && hasObjVar(object, storyteller.EFFECT_TOKEN_NAME)) {
                            stopClientEffectObjByLabel(player, object, "storyteller_persisted_effect", false);
                        }
                    }
                }
            }
        }
    }
    public static String[] recordBlueprintData(obj_id blueprint, obj_id[] storytellerObjects, obj_id player) throws InterruptedException
    {
        Vector blueprintString = new Vector();
        blueprintString.setSize(0);
        Vector validStorytellerObjects = new Vector();
        validStorytellerObjects.setSize(0);
        obj_id objectOwner;
        obj_id blueprintAuthor;
        String tokenName;
        String[] excludedTokens = {"st_fn_storyteller_vendor"};
        location tokLoc;

        for (obj_id object : storytellerObjects) {
            objectOwner = getObjIdObjVar(object, "storytellerid");
            if (!isIdValid(objectOwner) || objectOwner != player) {
                continue;
            }
            if (hasObjVar(object, storyteller.BLUEPRINT_AUTHOR_OBJVAR)) {
                blueprintAuthor = getObjIdObjVar(object, storyteller.BLUEPRINT_AUTHOR_OBJVAR);
                if (!isIdValid(blueprintAuthor) || blueprintAuthor != player) {
                    continue;
                }
            }
            tokenName = getStaticItemName(object);
            if (tokenName == null || tokenName.length() < 1) {
                continue;
            }
            boolean isExcludedToken = false;
            obj_id whatAmIStandingOn = getStandingOn(object);
            for (String excludedToken : excludedTokens) {
                if (tokenName.equals(excludedToken) || storyteller.getTokenType(tokenName) == storyteller.OTHER || isIdValid(getLocation(object).cell) || (isIdValid(whatAmIStandingOn) && player_structure.isBuilding(whatAmIStandingOn))) {
                    isExcludedToken = true;
                }
            }
            if (isExcludedToken) {
                continue;
            }
            utils.addElement(validStorytellerObjects, object);
        }
        if (validStorytellerObjects == null || validStorytellerObjects.size() < 1)
        {
            String[] _blueprintString = new String[0];
            if (blueprintString != null)
            {
                _blueprintString = new String[blueprintString.size()];
                blueprintString.toArray(_blueprintString);
            }
            return _blueprintString;
        }
        float maxX = -99999.0f;
        float maxZ = -99999.0f;
        float minX = 99999.0f;
        float minZ = 99999.0f;
        obj_id object;
        for (Object validStorytellerObject : validStorytellerObjects) {
            object = ((obj_id) validStorytellerObject);
            objectOwner = getObjIdObjVar(object, "storytellerid");
            if (!isIdValid(objectOwner) || objectOwner != player) {
                continue;
            }
            location objLoc = getLocation(object);
            if (objLoc.x > maxX) {
                maxX = objLoc.x;
            }
            if (objLoc.z > maxZ) {
                maxZ = objLoc.z;
            }
            if (objLoc.x < minX) {
                minX = objLoc.x;
            }
            if (objLoc.z < minZ) {
                minZ = objLoc.z;
            }
        }
        obj_id validStoryObject;
        location playerLoc;
        for (Object validStorytellerObject : validStorytellerObjects) {
            validStoryObject = ((obj_id) validStorytellerObject);
            tokenName = getStaticItemName(validStoryObject);
            playerLoc = getLocation(player);
            float playerYaw = getYaw(player);
            location objLoc = getLocation(validStoryObject);
            float objYaw = getYaw(validStoryObject);
            if (playerYaw != 0) {
                objLoc = storyteller.rotateSavedBlueprintDataXZ(playerLoc, objLoc, playerYaw);
            }
            float terrainHeight = objLoc.y;
            location creationLoc = getLocationObjVar(validStoryObject, "storytellerCreationLoc");
            if (creationLoc != null) {
                terrainHeight = creationLoc.y;
            }
            float locX = objLoc.x - playerLoc.x;
            float locY = objLoc.y - terrainHeight;
            float locZ = objLoc.z - playerLoc.z;
            if (locY < 0.001f) {
                locY = 0.0f;
            }
            int combatLevel = 0;
            int difficulty = 0;
            if (isMob(validStoryObject)) {
                combatLevel = getLevel(validStoryObject);
                difficulty = getIntObjVar(validStoryObject, "difficultyClass");
            }
            String effect = "none";
            if (hasObjVar(validStoryObject, storyteller.EFFECT_ACTIVE_OBJVAR) && hasObjVar(validStoryObject, storyteller.EFFECT_TOKEN_NAME)) {
                effect = getStringObjVar(validStoryObject, storyteller.EFFECT_TOKEN_NAME);
                String effectEntry = buildBlueprintObjectData(storyteller.BLUEPRINT_TOKEN_NEEDED, effect, 0.0f, 0.0f, 0.0f, 0.0f, 0, 0, "none");
                utils.addElement(blueprintString, effectEntry);
            }
            String newEntry = buildBlueprintObjectData(storyteller.BLUEPRINT_TOKEN_NEEDED, tokenName, locX, locY, locZ, playerYaw - objYaw, combatLevel, difficulty, effect);
            utils.addElement(blueprintString, newEntry);
            if (blueprintString.size() >= BLUEPRINT_MAX_NUM_OBJECTS) {
                break;
            }
        }
        String[] _blueprintString = new String[0];
        if (blueprintString != null)
        {
            _blueprintString = new String[blueprintString.size()];
            blueprintString.toArray(_blueprintString);
        }
        return _blueprintString;
    }
    public static location rotateSavedBlueprintDataXZ(location locOrigin, location locPoint, float fltAngle) throws InterruptedException
    {
        float dx = locPoint.x - locOrigin.x;
        float dz = locPoint.z - locOrigin.z;
        float fltRadians = (float)Math.toRadians(fltAngle);
        float fltC = (float)Math.cos(fltRadians);
        float fltS = (float)Math.sin(fltRadians);
        location locNewOffset = (location)locOrigin.clone();
        locNewOffset.x += -(dx * fltC) + (dz * fltS);
        locNewOffset.y = locPoint.y;
        locNewOffset.z += (dx * fltS) + (dz * fltC);
        return locNewOffset;
    }
    public static String changeBlueprintItemTokenStatus(int tokenStatus, String objectData) throws InterruptedException
    {
        return buildBlueprintObjectData(tokenStatus, getBlueprintItemName(objectData), getBlueprintItemLocX(objectData), getBlueprintItemLocY(objectData), getBlueprintItemLocZ(objectData), getBlueprintItemYaw(objectData), getBlueprintItemCombatLevel(objectData), getBlueprintItemDifficulty(objectData), getBlueprintItemEffect(objectData));
    }
    public static String buildBlueprintObjectData(int tokenLoaded, String tokenName, float locX, float locY, float locZ, float yaw, int combatLevel, int difficulty, String effect) throws InterruptedException
    {
        return tokenLoaded + "~" + tokenName + "~" + locX + "~" + locY + "~" + locZ + "~" + yaw + "~" + combatLevel + "~" + difficulty + "~" + effect;
    }
    public static int getBlueprintObjectDataSize(String blueprintObjectData) throws InterruptedException
    {
        return split(blueprintObjectData, '~').length;
    }
    public static int getRequiredBlueprintDataSize() throws InterruptedException
    {
        String blueprintDataTest = buildBlueprintObjectData(BLUEPRINT_TOKEN_NEEDED, "test_only", 0.0f, 0.0f, 0.0f, 0.0f, 0, 0, "none");
        return getBlueprintObjectDataSize(blueprintDataTest);
    }
    public static boolean validateBlueprintData(String blueprintObjectData) throws InterruptedException
    {
        return getBlueprintObjectDataSize(blueprintObjectData) == getRequiredBlueprintDataSize();
    }
    public static obj_id createBlueprintObject(String objectData, obj_id blueprintController, obj_id player, location targetLoc, float targetYaw) throws InterruptedException
    {
        obj_id blueprintObject = obj_id.NULL_ID;
        String tokenName = getBlueprintItemName(objectData);
        location atLoc = getBlueprintObjectLocation(objectData, targetLoc);
        float yawDiff = getBlueprintItemYaw(objectData);
        int combatLevel = getBlueprintItemCombatLevel(objectData);
        int difficulty = getBlueprintItemDifficulty(objectData);
        String effect = getBlueprintItemEffect(objectData);
        if (targetYaw != 0)
        {
            atLoc = storyteller.rotateSavedBlueprintDataXZ(targetLoc, atLoc, targetYaw);
        }
        float newYaw = targetYaw - yawDiff;
        int tokenType = storyteller.getTokenType(tokenName);
        switch (tokenType)
        {
            case storyteller.PROP:
            blueprintObject = storyteller.createPropObjectNoToken(tokenName, player, false, atLoc, newYaw);
            break;
            case storyteller.COMBAT_NPC:
            blueprintObject = storyteller.createNpcAtLocationNoToken(tokenName, player, atLoc, newYaw, combatLevel, difficulty);
            break;
            case storyteller.FLAVOR_NPC:
            blueprintObject = storyteller.createNpcAtLocationNoToken(tokenName, player, atLoc, newYaw, combatLevel, difficulty);
            break;
            case storyteller.STATIC_EFFECT:
            break;
        }
        if (isValidId(blueprintObject))
        {
            if (hasObjVar(blueprintController, storyteller.BLUEPRINT_AUTHOR_OBJVAR))
            {
                obj_id blueprintAuthor = getObjIdObjVar(blueprintController, storyteller.BLUEPRINT_AUTHOR_OBJVAR);
                if (isIdValid(blueprintAuthor))
                {
                    setObjVar(blueprintObject, storyteller.BLUEPRINT_AUTHOR_OBJVAR, blueprintAuthor);
                }
            }
            if (effect.length() > 0 && !effect.equals("none"))
            {
                setObjVar(blueprintObject, storyteller.EFFECT_TOKEN_NAME, effect);
                if (hasScript(blueprintObject, "systems.storyteller.effect_controller"))
                {
                    messageTo(blueprintObject, "handlePlayNewStorytellerEffect", null, 1, false);
                }
                else 
                {
                    attachScript(blueprintObject, "systems.storyteller.effect_controller");
                }
            }
            dictionary webster = new dictionary();
            webster.put("yOffset", getBlueprintItemLocY(objectData));
            webster.put("player", player);
            messageTo(blueprintObject, "handleBlueprintElevation", webster, 0.25f, false);
        }
        if (isGod(player))
        {
            utils.setScriptVar(player, "storyteller.godModeStopOverrideMessages", true);
        }
        return blueprintObject;
    }
    public static void handleBlueprintObjectElevation(obj_id blueprintObject, dictionary params) throws InterruptedException
    {
        if (params != null && !params.isEmpty())
        {
            float yOffset = params.getFloat("yOffset");
            location here = getLocation(blueprintObject);
            setObjVar(blueprintObject, "storytellerCreationLoc", here);
            location newElevationLoc = new location(here.x, here.y + yOffset, here.z);
            setLocation(blueprintObject, newElevationLoc);
        }
    }
    public static location getBlueprintObjectLocation(String spawnString, location centerLoc) throws InterruptedException
    {
        float locX = centerLoc.x + getBlueprintItemLocX(spawnString);
        float locZ = centerLoc.z + getBlueprintItemLocZ(spawnString);
        float locY = centerLoc.y;
        return new location(locX, locY, locZ, centerLoc.area, null);
    }
    public static boolean isBlueprintTokenLoaded(String spawnString) throws InterruptedException
    {
        return utils.stringToInt(split(spawnString, '~')[0]) == 1;
    }
    public static String getBlueprintItemName(String spawnString) throws InterruptedException
    {
        return split(spawnString, '~')[1];
    }
    public static float getBlueprintItemLocX(String spawnString) throws InterruptedException
    {
        return utils.stringToFloat(split(spawnString, '~')[2]);
    }
    public static float getBlueprintItemLocY(String spawnString) throws InterruptedException
    {
        return utils.stringToFloat(split(spawnString, '~')[3]);
    }
    public static float getBlueprintItemLocZ(String spawnString) throws InterruptedException
    {
        return utils.stringToFloat(split(spawnString, '~')[4]);
    }
    public static float getBlueprintItemYaw(String spawnString) throws InterruptedException
    {
        return utils.stringToFloat(split(spawnString, '~')[5]);
    }
    public static int getBlueprintItemCombatLevel(String spawnString) throws InterruptedException
    {
        return utils.stringToInt(split(spawnString, '~')[6]);
    }
    public static int getBlueprintItemDifficulty(String spawnString) throws InterruptedException
    {
        return utils.stringToInt(split(spawnString, '~')[7]);
    }
    public static String getBlueprintItemEffect(String spawnString) throws InterruptedException
    {
        return split(spawnString, '~')[8];
    }
    public static void blueprintParseConversion(obj_id blueprint) throws InterruptedException
    {
        String[] blueprintObjects = utils.getStringBatchObjVar(blueprint, storyteller.BLUEPRINT_OBJECTS_OBJVAR);
        if (blueprintObjects == null || blueprintObjects.length == 0)
        {
            return;
        }
        boolean conversionWasRequired = false;
        for (int i = 0; i < blueprintObjects.length; i++)
        {
            String objectData = blueprintObjects[i];
            String[] oldParse = split(objectData, '|');
            if (oldParse.length > 1)
            {
                int tokenLoaded = utils.stringToInt(oldParse[0]);
                String tokenName = oldParse[1];
                float locX = utils.stringToFloat(oldParse[2]);
                float locY = utils.stringToFloat(oldParse[3]);
                float locZ = utils.stringToFloat(oldParse[4]);
                float yaw = utils.stringToFloat(oldParse[5]);
                int combatLevel = utils.stringToInt(oldParse[6]);
                int difficulty = utils.stringToInt(oldParse[7]);
                String effect = oldParse[8];
                String convertedObjectData = buildBlueprintObjectData(tokenLoaded, tokenName, locX, locY, locZ, yaw, combatLevel, difficulty, effect);
                blueprintObjects[i] = convertedObjectData;
                conversionWasRequired = true;
            }
        }
        if (conversionWasRequired)
        {
            utils.setBatchObjVar(blueprint, storyteller.BLUEPRINT_OBJECTS_OBJVAR, blueprintObjects);
        }
    }
    public static boolean doIAutoDeclineStorytellerInvites(obj_id player) throws InterruptedException {
        return hasObjVar(player, storyteller.VAR_AUTODECLINE_STORY_INVITES) || utils.hasScriptVar(player, "battlefield.active");
    }
    public static int storyAssistantSui(obj_id storytellerPlayer, String storytellerName, obj_id player) throws InterruptedException
    {
        String title = utils.packStringId(new string_id("storyteller", "assistant_title"));
        prose_package pp = prose.getPackage(new string_id("storyteller", "assistant_invite"));
        prose.setTO(pp, storytellerName);
        String msg = "\0" + packOutOfBandProsePackage(null, pp);
        return sui.msgbox(storytellerPlayer, player, msg, 2, title, sui.YES_NO, "storyAssistantHandler");
    }
    public static void storyAssistantAcepted(obj_id storytellerPlayer, String storytellerName, obj_id player) throws InterruptedException
    {
        string_id message = new string_id("storyteller", "assistant_player_name");
        prose_package pp = prose.getPackage(message, player, player);
        prose.setTO(pp, storytellerName);
        sendSystemMessageProse(player, pp);
        dictionary webster = new dictionary();
        webster.put("assistantPlayerName", getName(player));
        messageTo(storytellerPlayer, "handleStorytellerAssistantHasBeenAdded", webster, 0, false);
        utils.setScriptVar(player, "storytellerAssistant", storytellerPlayer);
        utils.setScriptVar(player, "storytellerAssistantName", storytellerName);
    }
    public static void storyPlayerRemoveAssistant(obj_id storytellerPlayer, String storytellerName, obj_id player) throws InterruptedException
    {
        string_id message = new string_id("storyteller", "assistant_removed_player");
        prose_package pp = prose.getPackage(message, player, player);
        prose.setTO(pp, storytellerName);
        sendSystemMessageProse(player, pp);
        utils.removeScriptVar(player, "storytellerAssistant");
        utils.removeScriptVar(player, "storytellerAssistant");
        dictionary webster = new dictionary();
        webster.put("removedPlayerName", getName(player));
        messageTo(storytellerPlayer, "handleStorytellerAssistantHasBeenRemoved", webster, 0, false);
    }
    public static int storyInviteSui(obj_id storytellerPlayer, String storytellerName, obj_id player) throws InterruptedException
    {
        String title = utils.packStringId(new string_id("storyteller", "sui_invite_title"));
        prose_package pp = prose.getPackage(new string_id("storyteller", "sui_invite_body"));
        prose.setTO(pp, storytellerName);
        String msg = "\0" + packOutOfBandProsePackage(null, pp);
        return sui.msgbox(storytellerPlayer, player, msg, 2, title, sui.YES_NO, "storyInviteHandler");
    }
    public static void storyInviteAcepted(obj_id storytellerPlayer, String storytellerName, obj_id player, obj_id storytellerAssistant) throws InterruptedException
    {
        string_id message = new string_id("storyteller", "player_invited_name");
        prose_package pp = prose.getPackage(message, player, player);
        prose.setTO(pp, storytellerName);
        sendSystemMessageProse(player, pp);
        dictionary webster = new dictionary();
        webster.put("addedPlayerName", getName(player));
        webster.put("storytellerName", storytellerName);
        if (isIdValid(storytellerAssistant))
        {
            messageTo(storytellerAssistant, "handleStorytellerPlayerHasBeenAdded", webster, 0, false);
        }
        else 
        {
            messageTo(storytellerPlayer, "handleStorytellerPlayerHasBeenAdded", webster, 0, false);
        }
        utils.setScriptVar(player, "storytellerid", storytellerPlayer);
        utils.setScriptVar(player, "storytellerName", storytellerName);
        showStorytellerEffectsInAreaToPlayer(player, storytellerPlayer);
    }
    public static void storyPlayerRemovedFromStory(obj_id storytellerPlayer, String storytellerName, obj_id player) throws InterruptedException
    {
        string_id message = new string_id("storyteller", "removed_from_story");
        prose_package pp = prose.getPackage(message, player, player);
        prose.setTO(pp, storytellerName);
        sendSystemMessageProse(player, pp);
        utils.removeScriptVar(player, "storytellerid");
        utils.removeScriptVar(player, "storytellerName");
        dictionary webster = new dictionary();
        webster.put("removedPlayerName", getName(player));
        messageTo(storytellerPlayer, "handleStorytellerPlayerHasBeenRemoved", webster, 0, false);
    }
    public static boolean storytellerCombatCheck(obj_id attacker, obj_id target) throws InterruptedException
    {
        if (!isIdValid(attacker) || !exists(attacker) || !isIdValid(target) || !exists(target))
        {
            return false;
        }
        obj_id storyPlayer = null;
        obj_id storyNpc = null;
        if (isPlayer(attacker))
        {
            if (isPlayer(target))
            {
                return true;
            }
            else 
            {
                storyPlayer = attacker;
                storyNpc = target;
            }
        }
        else if (isPlayer(target))
        {
            storyPlayer = target;
            storyNpc = attacker;
        }
        else if (pet_lib.isPet(attacker) || beast_lib.isBeast(attacker))
        {
            if (pet_lib.isPet(target) || beast_lib.isBeast(target))
            {
                return true;
            }
            else 
            {
                storyPlayer = getMaster(attacker);
                storyNpc = target;
            }
        }
        else if (pet_lib.isPet(target) || beast_lib.isBeast(target))
        {
            storyPlayer = getMaster(target);
            storyNpc = attacker;
        }
        else if (!isMob(attacker))
        {
            if (hasScript(attacker, "systems.combat.combat_delayed_tracker"))
            {
                storyPlayer = utils.getObjIdScriptVar(attacker, "objOwner");
                storyNpc = target;
            }
            else 
            {
                storyNpc = attacker;
                storyPlayer = target;
            }
        }
        else if (!isMob(target))
        {
            if (hasScript(target, "systems.combat.combat_delayed_tracker"))
            {
                storyPlayer = utils.getObjIdScriptVar(target, "objOwner");
                storyNpc = attacker;
            }
            else 
            {
                storyNpc = target;
                storyPlayer = attacker;
            }
        }
        else if (hasObjVar(attacker, "storytellerid") || hasObjVar(target, "storytellerid"))
        {
            if (hasObjVar(attacker, "storytellerid") && hasObjVar(target, "storytellerid"))
            {
                obj_id attackerNpcStoryteller = getObjIdObjVar(attacker, "storytellerid");
                obj_id targetNpcStoryteller = getObjIdObjVar(target, "storytellerid");
                return attackerNpcStoryteller == targetNpcStoryteller;
            }
            else 
            {
                return false;
            }
        }
        if (isIdValid(storyPlayer) && isIdValid(storyNpc))
        {
            if (hasObjVar(storyNpc, "storytellerid"))
            {
                if (utils.hasScriptVar(storyPlayer, "storytellerid"))
                {
                    obj_id playerStorytellerId = utils.getObjIdScriptVar(storyPlayer, "storytellerid");
                    obj_id npcStorytellerId = getObjIdObjVar(storyNpc, "storytellerid");
                    return isIdValid(playerStorytellerId) && isIdValid(npcStorytellerId) && playerStorytellerId == npcStorytellerId;
                }
                else 
                {
                    return false;
                }
            }
        }
        return true;
    }
    public static boolean displayAvailableStorytellerTokenTypes(obj_id player, obj_id storytellerVendor) throws InterruptedException
    {
        if (getDistance(storytellerVendor, player) > 10.0f)
        {
            return false;
        }
        String title = utils.packStringId(new string_id("storyteller", "token_purchase_type_title"));
        String prompt = utils.packStringId(new string_id("storyteller", "token_purchase_type_prompt"));
        int pid = sui.listbox(storytellerVendor, player, prompt, sui.OK_CANCEL, title, getTokenTypeNamesArray(), "msgStorytellerTokenTypeSelected");
        if (pid > -1)
        {
            String scriptvar_path = "storytellerVendor." + player;
            utils.setScriptVar(storytellerVendor, scriptvar_path + ".pid", pid);
            return true;
        }
        return false;
    }
    public static String[] getTokenTypeNamesArray() throws InterruptedException
    {
        final String[] sid_storytellerTokenTypes = 
        {
            "token_type_prop",
            "token_type_persisted_effect",
            "token_type_immediate_effect",
            "token_type_combat_npc",
            "token_type_flavor_npc",
            "token_type_other",
            "token_type_costume"
        };
        Vector storytellerTokenTypes = new Vector();
        storytellerTokenTypes.setSize(0);
        String tokenType;
        for (String sid_storytellerTokenType : sid_storytellerTokenTypes) {
            tokenType = utils.packStringId(new string_id("storyteller", sid_storytellerTokenType));
            storytellerTokenTypes = utils.addElement(storytellerTokenTypes, tokenType);
        }
        String[] _storytellerTokenTypes = new String[0];
        if (storytellerTokenTypes != null)
        {
            _storytellerTokenTypes = new String[storytellerTokenTypes.size()];
            storytellerTokenTypes.toArray(_storytellerTokenTypes);
        }
        return _storytellerTokenTypes;
    }
    public static boolean displayStorytellerTokenPurchaseSUI(obj_id player, int tokenType, obj_id storytellerVendor) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(storytellerVendor))
        {
            return false;
        }
        if (getDistance(storytellerVendor, player) > 10.0f)
        {
            return false;
        }
        String scriptvar_path = "storytellerVendor." + player;
        if (utils.hasScriptVar(storytellerVendor, scriptvar_path + ".pid"))
        {
            int oldpid = utils.getIntScriptVar(storytellerVendor, scriptvar_path + ".pid");
            sui.closeSUI(player, oldpid);
            utils.removeScriptVar(storytellerVendor, scriptvar_path + ".pid");
            utils.removeBatchScriptVar(storytellerVendor, scriptvar_path + ".tokenReferences");
            utils.removeBatchScriptVar(storytellerVendor, scriptvar_path + ".tokens");
            utils.removeScriptVar(storytellerVendor, scriptvar_path + ".tokenType");
        }
        Vector tokenReferences = new Vector();
        tokenReferences.setSize(0);
        int num_items = dataTableGetNumRows(STORYTELLER_DATATABLE);
        for (int i = 0; i < num_items; i++)
        {
            dictionary row = dataTableGetRow(STORYTELLER_DATATABLE, i);
            if (row != null && !row.isEmpty())
            {
                int row_tokenType = row.getInt("type");
                if (row_tokenType == tokenType)
                {
                    String token_reference = row.getString("name");
                    int cost = row.getInt("cost");
                    if (cost > 0)
                    {
                        tokenReferences = utils.addElement(tokenReferences, token_reference);
                    }
                }
            }
        }
        String[] tokenTypeNames = getTokenTypeNamesArray();
        if (tokenReferences == null || tokenReferences.size() < 1)
        {
            storyteller.displayAvailableStorytellerTokenTypes(player, storytellerVendor);
            prose_package pp = prose.getPackage(new string_id("storyteller", "token_purchase_none_of_type"), tokenTypeNames[tokenType]);
            sendSystemMessageProse(player, pp);
            return false;
        }
        String prompt = getString(new string_id("storyteller", "token_purchase_select_token"));
        String[] alphabetizedTokenReferences = getAlphabetizedTokenList(utils.toStaticStringArray(tokenReferences), tokenType);
        String[] alphabetizedTokens = getTokenNamesList(alphabetizedTokenReferences, tokenType, player);
        String myHandler = "msgStorytellerTokenPurchaseSelected";
        int pid = sui.listbox(storytellerVendor, player, prompt, sui.OK_CANCEL_REFRESH, tokenTypeNames[tokenType], alphabetizedTokens, myHandler, false, false);
        if (pid > -1)
        {
            sui.listboxUseOtherButton(pid, "Back");
            sui.showSUIPage(pid);
            utils.setScriptVar(storytellerVendor, scriptvar_path + ".pid", pid);
            utils.setBatchScriptVar(storytellerVendor, scriptvar_path + ".tokenReferences", alphabetizedTokenReferences);
            utils.setBatchScriptVar(storytellerVendor, scriptvar_path + ".tokens", alphabetizedTokens);
            utils.setScriptVar(storytellerVendor, scriptvar_path + ".tokenType", tokenType);
            return true;
        }
        return false;
    }
    public static String[] getAlphabetizedTokenList(String[] tokenReferences, int tokenType) throws InterruptedException
    {
        String[] namesColonTokens = new String[tokenReferences.length];
        for (int i = 0; i < tokenReferences.length; i++)
        {
            String temp = getString(new string_id("static_item_n", tokenReferences[i])) + "|" + tokenReferences[i];
            namesColonTokens[i] = temp;
        }
        Arrays.sort(namesColonTokens);
        String[] finalList = new String[namesColonTokens.length];
        for (int j = 0; j < namesColonTokens.length; j++)
        {
            String[] splitText = split(namesColonTokens[j], '|');
            finalList[j] = splitText[1];
        }
        if (tokenType == COSTUME)
        {
            String[] tempList = new String[finalList.length];
            utils.copyArray(finalList, tempList);
            finalList[0] = "item_costume_kit";
            int nextFinalListSlot = 1;
            for (String ref : tempList) {
                if (!ref.equals("item_costume_kit")) {
                    finalList[nextFinalListSlot] = ref;
                    ++nextFinalListSlot;
                }
            }
        }
        return finalList;
    }
    public static String[] getTokenNamesList(String[] tokenReferences, int tokenType, obj_id player) throws InterruptedException
    {
        String[] tokenNamesList = new String[tokenReferences.length];
        for (int i = 0; i < tokenReferences.length; i++)
        {
            String token = tokenReferences[i];
            dictionary row = dataTableGetRow(STORYTELLER_DATATABLE, token);
            if (row != null && !row.isEmpty())
            {
                int cost = row.getInt("cost");
                String tokenName = utils.packStringId(new string_id("static_item_n", token));
                if (tokenType == COMBAT_NPC || tokenType == FLAVOR_NPC)
                {
                    tokenName = tokenName + " (Cost: " + cost + " per npc)";
                }
                else if (tokenType == PROP)
                {
                    tokenName = tokenName + " (Cost: " + cost + " per prop)";
                }
                else if (tokenType == COSTUME)
                {
                    if (token.equals("item_costume_kit"))
                    {
                        tokenName = tokenName + " (Cost: " + cost + " per holoshroud)";
                    }
                    else 
                    {
                        dictionary itemData = new dictionary();
                        itemData = dataTableGetRow(static_item.ITEM_STAT_BALANCE_TABLE, token);
                        if (itemData != null && !itemData.isEmpty())
                        {
                            String costumeName = itemData.getString("buff_name");
                            if (hasSkill(player, costumeName))
                            {
                                String tokenText = utils.packStringId(new string_id("static_item_n", token));
                                string_id contrastText_sid = new string_id("storyteller", "costume_already_known");
                                prose_package pp = prose.getPackage(contrastText_sid);
                                prose.setTO(pp, tokenText);
                                prose.setDI(pp, cost);
                                tokenName = "\0" + packOutOfBandProsePackage(null, pp);
                            }
                            else 
                            {
                                tokenName = tokenName + " (Cost: " + cost + ")";
                            }
                        }
                    }
                }
                else 
                {
                    tokenName = tokenName + " (Cost: " + cost + ")";
                }
                tokenNamesList[i] = tokenName;
            }
            else 
            {
                tokenNamesList[i] = utils.packStringId(new string_id("storyteller", "unknown_token")) + " : " + token;
            }
        }
        return tokenNamesList;
    }
    public static void storytellerTokenPurchased(dictionary params, obj_id storytellerVendor) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return;
        }
        if (getDistance(storytellerVendor, player) > 10.0f)
        {
            return;
        }
        String scriptvar_path = "storytellerVendor." + player;
        if (!utils.hasScriptVar(storytellerVendor, scriptvar_path + ".pid"))
        {
            return;
        }
        String[] tokenReferences = utils.getStringBatchScriptVar(storytellerVendor, scriptvar_path + ".tokenReferences");
        int tokenType = utils.getIntScriptVar(storytellerVendor, scriptvar_path + ".tokenType");
        utils.removeScriptVar(storytellerVendor, scriptvar_path + ".pid");
        utils.removeBatchScriptVar(storytellerVendor, scriptvar_path + ".tokenReferences");
        utils.removeBatchScriptVar(storytellerVendor, scriptvar_path + ".tokens");
        utils.removeScriptVar(storytellerVendor, scriptvar_path + ".tokenType");
        if (tokenReferences == null || tokenReferences.length == 0)
        {
            LOG("LOG_CHANNEL", "storyteller::storytellerTokenPurchased -- the token reference list is null.");
            return;
        }
        int button = sui.getIntButtonPressed(params);
        if (button == sui.BP_CANCEL)
        {
            return;
        }
        if (button == sui.BP_REVERT)
        {
            storyteller.displayAvailableStorytellerTokenTypes(player, storytellerVendor);
            return;
        }
        int token_selected = sui.getListboxSelectedRow(params);
        if (token_selected < 0)
        {
            return;
        }
        if (token_selected >= tokenReferences.length)
        {
            return;
        }
        String token_reference = tokenReferences[token_selected];
        if (token_reference == null)
        {
            LOG("LOG_CHANNEL", "storyteller::storytellerTokenPurchased -- the item template selected by " + storytellerVendor + " is null.");
            return;
        }
        int tokenIdx = dataTableSearchColumnForString(token_reference, "name", STORYTELLER_DATATABLE);
        if (tokenIdx == -1)
        {
            LOG("LOG_CHANNEL", "storyteller::storytellerTokenPurchased -- cannot find " + token_reference + " in the storyteller token datatable.");
            return;
        }
        int staticIdx = dataTableSearchColumnForString(token_reference, "name", STORYTELLER_DATATABLE);
        if (staticIdx == -1)
        {
            LOG("LOG_CHANNEL", "storyteller::storytellerTokenPurchased -- cannot find " + token_reference + " in the static item datatable.");
            return;
        }
        dictionary tokenData = dataTableGetRow(STORYTELLER_DATATABLE, tokenIdx);
        int cost = tokenData.getInt("cost");
        String tokenName = utils.packStringId(new string_id("static_item_n", token_reference));
        int maxCharges = tokenData.getInt("max_charges");
        if (requestNumCharges(tokenType, token_reference) && maxCharges > 1)
        {
            String prompt = getString(new string_id("storyteller", "token_how_many_charges")) + maxCharges;
            int pid = sui.inputbox(storytellerVendor, player, prompt, sui.OK_CANCEL, tokenName, sui.INPUT_NORMAL, null, "msgStorytellerChargesSelected");
            if (pid > -1)
            {
                sui.showSUIPage(pid);
                utils.setScriptVar(storytellerVendor, scriptvar_path + ".pid", pid);
                utils.setScriptVar(storytellerVendor, scriptvar_path + ".tokenChosen", token_reference);
                utils.setScriptVar(storytellerVendor, scriptvar_path + ".tokenType", tokenType);
                return;
            }
        }
        givePurchasedToken(storytellerVendor, player, token_reference, tokenName, cost, 0);
        storyteller.displayStorytellerTokenPurchaseSUI(player, tokenType, storytellerVendor);
    }
    public static boolean requestNumCharges(int tokenType, String token_reference) throws InterruptedException
    {
        if (tokenType == COMBAT_NPC)
        {
            return true;
        }
        if (tokenType == FLAVOR_NPC)
        {
            return true;
        }
        if (tokenType == PROP)
        {
            return true;
        }
        return token_reference.equals("item_costume_kit");
    }
    public static void storytellerSellTokenWithCharges(dictionary params, obj_id storytellerVendor) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        String text = sui.getInputBoxText(params);
        int numCharges = utils.stringToInt(text);
        String scriptvar_path = "storytellerVendor." + player;
        String token_reference = utils.getStringScriptVar(storytellerVendor, scriptvar_path + ".tokenChosen");
        int tokenType = utils.getIntScriptVar(storytellerVendor, scriptvar_path + ".tokenType");
        String tokenName = utils.packStringId(new string_id("static_item_n", token_reference));
        dictionary tokenData = dataTableGetRow(STORYTELLER_DATATABLE, token_reference);
        int cost = tokenData.getInt("cost");
        int maxCharges = tokenData.getInt("max_charges");
        if (btn == sui.BP_CANCEL)
        {
            storyteller.displayStorytellerTokenPurchaseSUI(player, tokenType, storytellerVendor);
            return;
        }
        if (numCharges < 1 || numCharges > maxCharges)
        {
            string_id message = new string_id("storyteller", "token_num_charges_invalid");
            prose_package pp = prose.getPackage(message, player, player);
            prose.setDI(pp, maxCharges);
            sendSystemMessageProse(player, pp);
            String prompt = getString(new string_id("storyteller", "token_how_many_charges")) + maxCharges;
            int pid = sui.inputbox(storytellerVendor, player, prompt, sui.OK_CANCEL, tokenName, sui.INPUT_NORMAL, null, "msgStorytellerChargesSelected");
            if (pid > -1)
            {
                sui.showSUIPage(pid);
                utils.setScriptVar(storytellerVendor, scriptvar_path + ".pid", pid);
                utils.setScriptVar(storytellerVendor, scriptvar_path + ".tokenChosen", token_reference);
                utils.setScriptVar(storytellerVendor, scriptvar_path + ".tokenType", tokenType);
            }
        }
        else 
        {
            givePurchasedToken(storytellerVendor, player, token_reference, tokenName, cost * numCharges, numCharges);
            storyteller.displayStorytellerTokenPurchaseSUI(player, tokenType, storytellerVendor);
        }
    }
    public static void givePurchasedToken(obj_id storytellerVendor, obj_id player, String tokenReference, String tokenName, int cost, int numCharges) throws InterruptedException
    {
        obj_id inv = getObjectInSlot(player, "inventory");
        if (!isIdValid(inv))
        {
            LOG("LOG_CHANNEL", "storyteller::storytellerTokenPurchased --  " + player + "'s inventory object is null.");
            return;
        }
        if (!money.hasFunds(player, money.MT_TOTAL, cost))
        {
            sendSystemMessageProse(player, prose.getPackage(new string_id("storyteller", "token_purchase_not_enough_credits"), tokenName));
        }
        else 
        {
            int free_space = getVolumeFree(inv);
            if (free_space < 1)
            {
                sendSystemMessage(player, faction_perk.SID_INVENTORY_FULL);
                return;
            }
            utils.moneyOutMetric(player, "STORYTELLER_TOKENS", cost);
            money.requestPayment(player, storytellerVendor, cost, "pass_fail", null, true);
            CustomerServiceLog("storyteller", "(" + player + ")" + getName(player) + " is attempting to purchase item: " + tokenReference);
            CustomerServiceLog("storyteller", "(" + player + ")" + getName(player) + "'s purchase cost: " + cost);
            obj_id item = static_item.createNewItemFunction(tokenReference, inv);
            if (!isIdValid(item))
            {
                LOG("LOG_CHANNEL", "storyteller::storytellerTokenPurchased --  unable to create " + tokenReference + " for " + storytellerVendor);
                CustomerServiceLog("storyteller", "(" + player + ")" + getName(player) + "'s purchase of " + tokenReference + " has failed!");
                return;
            }
            if (numCharges > 0)
            {
                setCount(item, numCharges);
            }
            CustomerServiceLog("storyteller", "(" + player + ")" + getName(player) + " has purchased (" + item + ")" + getName(item));
            logBalance("storyteller;" + getGameTime() + ";item;" + tokenReference + ";" + cost);
            sendSystemMessageProse(player, prose.getPackage(new string_id("storyteller", "token_purchase_complete"), player, item));
        }
    }
    public static void confirmCleanuptime(obj_id object) throws InterruptedException
    {
        int storytellerCreationTime = 0;
        int existTime = 0;
        if (hasObjVar(object, "storytellerCreationTime"))
        {
            storytellerCreationTime = getIntObjVar(object, "storytellerCreationTime");
        }
        if (hasObjVar(object, "storytellerCleanUpTime"))
        {
            existTime = getIntObjVar(object, "storytellerCleanUpTime");
        }
        if (storytellerCreationTime + existTime > getGameTime())
        {
            int timeRemaining = storytellerCreationTime + existTime - getGameTime();
            messageTo(object, "prepCleanupProp", null, timeRemaining, false);
            return;
        }
        trial.cleanupObject(object);
    }
    public static void setBonusExistTime(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "storytellerCleanUpTime"))
        {
            int cleanUpTime = getIntObjVar(self, "storytellerCleanUpTime");
            cleanUpTime += 43200;
            setObjVar(self, "storytellerCleanUpTime", cleanUpTime);
        }
    }
    public static void calculatePropBonusExistTime(obj_id object) throws InterruptedException
    {
        int cleanUpTime = DEFAULT_PROP_CLEANUP_TIME;
        int city_id = getCityAtLocation(getLocation(object), 0);
        if (city_id > 0)
        {
            cleanUpTime += 28800;
            obj_id cityHall = cityGetCityHall(city_id);
            dictionary outparams = new dictionary();
            outparams.put("queryObject", object);
            messageTo(cityHall, "st_citySpecBonusCheck", outparams, 0.0f, false);
        }
        setObjVar(object, "storytellerCleanUpTime", cleanUpTime);
    }
    public static void calculateNpcBonusExistTime(obj_id object) throws InterruptedException
    {
        int cleanUpTime = DEFAULT_NPC_CLEANUP_TIME;
        int city_id = getCityAtLocation(getLocation(object), 0);
        if (city_id > 0)
        {
            cleanUpTime += 28800;
            obj_id cityHall = cityGetCityHall(city_id);
            dictionary outparams = new dictionary();
            outparams.put("queryObject", object);
            messageTo(cityHall, "st_citySpecBonusCheck", outparams, 0.0f, false);
        }
        setObjVar(object, "storytellerCleanUpTime", cleanUpTime);
    }
}
