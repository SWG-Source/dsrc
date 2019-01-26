package script.structure;

import script.*;
import script.library.*;

import java.util.Arrays;
import java.util.Vector;

public class permanent_structure extends script.base_script
{
    public permanent_structure()
    {
    }
    public static final boolean LOGGING_ON = true;
    public static final String LOGGING_CATEGORY = "special_sign";
    public static final String SCRIPT_ITEM_STRUCTURE = "structure.item_structure";
    public static final string_id SID_YOURE_CITY_BANNED = new string_id("city/city", "youre_city_banned");
    public static final string_id SID_BEACON_MISSING = new string_id("space/space_interaction", "homing_beacon_missing");
    public static final string_id SID_NEW_CITY_FIXUP_CITIZEN_SUBJECT = new string_id("city/city", "city_fixup_add_citizens_subject");
    public static final string_id SID_NEW_CITY_FIXUP_CITIZEN_BODY = new string_id("city/city", "city_fixup_add_citizen_at_login_body");
    public static final string_id SID_INACTIVE_CITIZEN_WARNING_BODY = new string_id("city/city", "inactive_citizen_warning_body");
    public static final string_id SID_INACTIVE_CITIZEN_WARNING_SUBJECT = new string_id("city/city", "inactive_citizen_warning_subject");
    public static final string_id SID_PACKING_CITY_ABANDONED_STRUCTURE = new string_id("city/city", "packing_city_abandoned_structure");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        dictionary params = new dictionary();
        params.put("sender", self);
        params.put("senderLoc", getLocation(self));
        messageToRange(20.0f, "handleRequestCorpseMove", params, 0.0f);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "validateStructureStorageOverride", null, 2, false);
        if (hasObjVar(self, "guildId"))
        {
            removeObjVar(self, "guildId");
        }
        if (player_structure.isHarvester(self) || player_structure.isGenerator(self))
        {
            if (player_structure.isStructureCondemned(self))
            {
                player_structure.destroyStructure(self);
                CustomerServiceLog("playerStructure", "Structure " + self + " was old and Condemed, now is DESTROYED.");
                return SCRIPT_CONTINUE;
            }
        }
        if (utils.checkConfigFlag("GameServer", "enableLogStructureInfo"))
        {
            messageTo(self, "logStructureInfo", null, 60, false);
        }
        if (player_structure.needsPreAbandonCheck(self))
        {
            messageTo(self, "checkAndMarkForPreAbandon", null, 60, false);
        }
        if (player_structure.needsAbandonCheck(self))
        {
            messageTo(self, "checkAndMarkForAbandon", null, 60, false);
        }
        if (player_structure.needsCityAbandonCheck(self))
        {
            LOG("sissynoid", "Checking House: " + self);
            messageTo(self, "checkCityHousePackupStatus", null, 0.0f, false);
        }
        if (player_structure.isHarvester(self))
        {
            messageTo(self, "validateHopper", null, 1.0f, false);
        }
        if (hasObjVar(self, "player_structure.maintanence.rate"))
        {
            removeObjVar(self, "player_structure.maintanence.rate");
        }
        player_structure.convertPermissionsLists(self);
        float fltDistance = 80.0f;
        obj_id[] objLairs = getAllObjectsWithTemplate(getLocation(self), fltDistance, "object/tangible/lair/npc_lair.iff");
        if (objLairs != null)
        {
            for (obj_id objLair : objLairs) {
                messageTo(objLair, "proximityCleanup", null, 1, true);
            }
        }
        LOG("LOG_CHANNEL", "permanent_structure::OnIntialize -- " + self);
        if (hasObjVar(self, player_structure.VAR_VENDOR_LIST))
        {
            removeObjVar(self, player_structure.VAR_VENDOR_LIST);
        }
        player_structure.updateStructureVersion(self);
        int rate = player_structure.getMaintenanceRate(self);
        LOG("LOG_CHANNEL", "Rate is " + rate);
        if (rate > 0)
        {
            int time_stamp = getIntObjVar(self, player_structure.VAR_LAST_MAINTANENCE);
            if (time_stamp < 1)
            {
                time_stamp = getGameTime();
                setObjVar(self, player_structure.VAR_LAST_MAINTANENCE, time_stamp);
            }
            messageTo(self, "OnMaintenanceLoop", null, 30.0f, false);
        }
        if (player_structure.isCivic(self))
        {
            permissionsMakePublic(self);
        }
        city.validateCityStructure(self);
        int city_id = getCityAtLocation(getLocation(self), 0);
        obj_id mayor = cityGetLeader(city_id);
        updateOwnerAndAdmin(self, mayor);
        LOG("LOG_CHANNEL", "permanent_structure::OnInitialize -- complete");
        if (player_structure.isHarvester(self) || player_structure.isGenerator(self))
        {
            player_structure.validateHarvestedResources(self);
        }
        if (!player_structure.isInstallation(self))
        {
            if (hasObjVar(self, player_structure.VAR_SIGN_NAME))
            {
                String signName = getStringObjVar(self, player_structure.VAR_SIGN_NAME);
                if (signName != null && !signName.equals(""))
                {
                    if (!isAppropriateText(signName))
                    {
                        removeObjVar(self, player_structure.VAR_SIGN_NAME);
                    }
                }
            }
        }
        else 
        {
            if (!isAppropriateText(getName(self)))
            {
                setName(self, "");
            }
        }
        player_structure.createStructureSign(self);
        if (hasObjVar(self, "structure.player.pack"))
        {
            removeObjVar(self, "structure.player.pack");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "permanent_structure::OnAboutToReceiveItem --" + transferer + " - " + item);
        int myGot = getGameObjectType(self);
        if (isGameObjectTypeOf(myGot, GOT_building))
        {
            if (!isPlayer(item) && !isMob(item))
            {
                obj_id structure = getTopMostContainer(self);
                if (isIdValid(transferer))
                {
                    if (isPlayer(transferer))
                    {
                        if (player_structure.canPlaceObject(structure, transferer))
                        {
                            return SCRIPT_CONTINUE;
                        }
                        else 
                        {
                            return SCRIPT_OVERRIDE;
                        }
                    }
                }
                else 
                {
                    return SCRIPT_CONTINUE;
                }
            }
            else if (isPlayer(item))
            {
                if (player_structure.isCivic(self) && city.isCityBanned(item, self) && !player_structure.isOwner(item, self) && !isIdValid(srcContainer))
                {
                    sendSystemMessage(item, SID_YOURE_CITY_BANNED);
                    return SCRIPT_OVERRIDE;
                }
                if (player_structure.isStructureCondemned(self))
                {
                    player_structure.doCondemnedSui(self, item);
                    return SCRIPT_OVERRIDE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToLoseItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "permanent_structure::OnAboutToLooseItem --" + transferer + " - " + item);
        int myGot = getGameObjectType(self);
        if (isGameObjectTypeOf(myGot, GOT_building))
        {
            if (!isPlayer(item) && !isMob(item))
            {
                obj_id structure = getTopMostContainer(self);
                if (hasObjVar(item, player_structure.VAR_IS_PLACED_OBJECT))
                {
                    if (isPlayer(transferer))
                    {
                        if (player_structure.isAdmin(structure, transferer))
                        {
                            return SCRIPT_CONTINUE;
                        }
                        else 
                        {
                            LOG("LOG_CHANNEL", transferer + "-> You must be an admin to pick up an item here.");
                            sendSystemMessageTestingOnly(transferer, "You must be an admin to pick up an item here.");
                            return SCRIPT_OVERRIDE;
                        }
                    }
                }
                else 
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnReceivedItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "permanent_structure::OnReceivedItem --" + transferer + " - " + item);
        int myGot = getGameObjectType(self);
        if (isGameObjectTypeOf(myGot, GOT_building))
        {
            LOG("LOG_CHANNEL", "permanent_structure::OnReceivedItem --" + item);
            if (!isPlayer(item) && !isMob(item))
            {
                String template = getTemplateName(item);
                if (template == null || template.equals(""))
                {
                    return SCRIPT_CONTINUE;
                }
                if (template.equals("object/tangible/terminal/terminal_guild.iff"))
                {
                    return SCRIPT_CONTINUE;
                }
                else if (template.equals("object/tangible/terminal/terminal_player_structure.iff"))
                {
                    return SCRIPT_CONTINUE;
                }
            }
            else if (isPlayer(item))
            {
                if (player_structure.isOwner(self, item))
                {
                    if (player_structure.isResidence(self, item))
                    {
                        if (!hasObjVar(self, player_structure.VAR_RESIDENCE_BUILDING))
                        {
                            setObjVar(self, player_structure.VAR_RESIDENCE_BUILDING, item);
                        }
                        if (city.isInCity(self))
                        {
                            city.addCitizen(item, self);
                        }
                    }
                    else 
                    {
                        if (hasObjVar(self, player_structure.VAR_RESIDENCE_BUILDING))
                        {
                            obj_id resident = getObjIdObjVar(self, player_structure.VAR_RESIDENCE_BUILDING);
                            if (resident == item)
                            {
                                removeObjVar(self, player_structure.VAR_RESIDENCE_BUILDING);
                            }
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnLostItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        int myGot = getGameObjectType(self);
        if (isGameObjectTypeOf(myGot, GOT_building_player))
        {
            if (isPlayer(item))
            {
                if (!isIdValid(getLocation(item).cell))
                {
                    setAggroImmuneDuration(item, 30);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (player_structure.isBuilding(self))
        {
            player_structure.destroyStructureSign(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnMaintenanceLoop(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "permanent_structure::OnMaintenanceLoop");
        obj_id objSender = params.getObjId("objSender");
        boolean boolSender = false;
        boolean boolResetTimeStamp = false;
        if (isIdValid(objSender))
        {
            boolSender = true;
            utils.setScriptVar(self, "objSender", objSender);
        }
        if (boolSender)
        {
            sendSystemMessageTestingOnly(objSender, "Received loop");
        }
        int time_stamp = getIntObjVar(self, player_structure.VAR_LAST_MAINTANENCE);
        int current_time = getGameTime();
        if (!player_structure.isStructureCondemned(self))
        {
            if (boolSender)
            {
                sendSystemMessageTestingOnly(objSender, "structure is Not condemned");
                sendSystemMessageTestingOnly(objSender, "Timestamp is " + time_stamp);
                sendSystemMessageTestingOnly(objSender, "Current Time is " + current_time);
            }
            if (time_stamp < 1)
            {
                setObjVar(self, player_structure.VAR_LAST_MAINTANENCE, current_time);
                time_stamp = current_time;
            }
            int delta_time = current_time - time_stamp;
            if (delta_time < 0)
            {
                CustomerServiceLog("playerStructure", "***TIME WARP WARNING: (" + self + ") owned by " + player_structure.getStructureOwner(self) + " -> cycles prepaid = " + (-delta_time / player_structure.getMaintenanceHeartbeat()));
            }
            if (boolSender)
            {
                sendSystemMessageTestingOnly(objSender, "delta time is " + delta_time);
            }
            LOG("LOG", "delta_time is " + delta_time + " and heartbeat is " + player_structure.getMaintenanceHeartbeat());
            int loops = delta_time / player_structure.getMaintenanceHeartbeat();
            if (boolSender)
            {
                sendSystemMessageTestingOnly(objSender, "Loops 1 is " + loops);
            }
            if (loops < 0)
            {
                CustomerServiceLog("playerStructure", "Structure " + self + " has a loop of less than zero (" + loops + ")  Owner is " + player_structure.getStructureOwner(self));
                loops = 0;
            }
            if (loops > 200)
            {
                CustomerServiceLog("playerStructure", "Structure " + self + " has a suspicious number of unpaid maintenance loops (" + loops + ")  Owner is " + player_structure.getStructureOwner(self));
                loops = 200;
            }
            if (boolSender)
            {
                sendSystemMessageTestingOnly(objSender, "Loops 2 is " + loops);
            }
            int cost = player_structure.getMaintenanceRate(self) * loops;
            LOG("LOG_CHANNEL", current_time + "/" + time_stamp + "/" + player_structure.getMaintenanceHeartbeat() + " loops ->" + loops + " cost ->" + cost);
            if (boolSender)
            {
                sendSystemMessageTestingOnly(objSender, "Raw per loop cost is " + player_structure.getMaintenanceRate(self));
                sendSystemMessageTestingOnly(objSender, "Cost is " + cost);
            }
            if ((cost > 0) && !player_structure.isCivic(self))
            {
                boolResetTimeStamp = true;
                if (boolSender)
                {
                    sendSystemMessageTestingOnly(objSender, "Checkign pools ");
                }
                int pool_remaining = player_structure.decrementMaintenancePool(self, cost);
                if (boolSender)
                {
                    sendSystemMessageTestingOnly(objSender, "Pool remaing is " + pool_remaining);
                }
                obj_id objOwner = player_structure.getStructureOwnerObjId(self);
                if (boolSender)
                {
                    sendSystemMessageTestingOnly(objSender, "Owner is " + objOwner);
                }
                if (player_structure.isBuilding(self))
                {
                    CustomerServiceLog("playerStructure", "Paying maintenance of " + cost + " on structure " + self + ". Pool remaining is " + pool_remaining + ". Owner is " + player_structure.getStructureOwner(self));
                }
                if (pool_remaining == -1)
                {
                    LOG("LOG_CHANNEL", "permanent_structure::OnMaintenanceLoop -- unable to decrement maintenance pool.");
                }
                else if (pool_remaining == -2)
                {
                    if (player_structure.isBuilding(self) || player_structure.isFactory(self))
                    {
                        LOG("LOG_CHANNEL", "permanent_structure::OnMaintenanceLoop:BUILDING-HOUSE/FACTORY " + self + "-- insufficient funds. Attempting to take money out of owner: " + objOwner + " bank account.");
                        if (!hasObjVar(self, "structure.intNoMaintenance"))
                        {
                            setObjVar(self, "structure.intNoMaintenance", 1);
                            player_structure.sendOutOfMaintenanceMail(self);
                        }
                        dictionary dctParams = new dictionary();
                        dctParams.put("intLoops", loops);
                        int weeklyCost = (60 * 60 * 24 * 7 / player_structure.getMaintenanceHeartbeat()) * player_structure.getMaintenanceRate(self);
                        dctParams.put("intCost", weeklyCost);
                        dctParams.put("amount", weeklyCost);
                        dctParams.put("offlineAmount", weeklyCost);
                        dctParams.put("account", money.ACCT_STRUCTURE_MAINTENANCE);
                        dctParams.put("failCallback", "handleBankTransferError");
                        dctParams.put("successCallback", "moneyTransferredFromOwner");
                        dctParams.put("replyTo", self);
                        dctParams.put("owner", objOwner);
                        messageTo(objOwner, "transferMoneyToNamedAccount", dctParams, 0, false, self, "ownerNotOnline");
                        return SCRIPT_CONTINUE;
                    }
                    else 
                    {
                        LOG("LOG_CHANNEL", "permanent_structure::OnMaintenanceLoop:HARVESTER-GENERATOR " + self + "-- insufficient funds. Applying damage instead.");
                        int damage = player_structure.getDecayRate(self) * loops;
                        int condition = player_structure.damageStructure(self, damage);
                        LOG("LOG_CHANNEL", "damage ->" + damage + " condition ->" + condition);
                        if (condition == 0)
                        {
                            return SCRIPT_CONTINUE;
                        }
                    }
                }
                else 
                {
                    if (hasObjVar(self, "structure.intNoMaintenance"))
                    {
                        removeObjVar(self, "structure.intNoMaintenance");
                    }
                }
            }
        }
        if (boolResetTimeStamp)
        {
            if (boolSender)
            {
                sendSystemMessageTestingOnly(self, "Resetting timestamp");
            }
            setObjVar(self, player_structure.VAR_LAST_MAINTANENCE, current_time);
        }
        else 
        {
            if (boolSender)
            {
                sendSystemMessageTestingOnly(self, "No loops, so not resetting timestamp.");
            }
        }
        doHouseMaintenanceHousekeeping(self);
        return SCRIPT_CONTINUE;
    }
    public void doHouseMaintenanceHousekeeping(obj_id self) throws InterruptedException
    {
        messageTo(self, "OnMaintenanceLoop", null, player_structure.getMaintenanceHeartbeat(), false);
        LOG("LOG_CHANNEL", "New Maintenance Loop set. -----");
        player_structure.initiateRepairCheck(self);
        player_structure.validateBaseObjects(self);
        xp.grantCraftingXpChance(self, player_structure.getStructureOwnerObjId(self), 40);
        city.validateCityStructure(self);
        player_structure.sendMaintenanceMail(self);
        return;
    }
    public int handlePayment(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player_id");
        obj_id structure = params.getObjId("target_id");
        int amt = params.getInt("amount");
        CustomerServiceLog("playerStructure", amt + " credits paid into " + structure + " by " + player + ". Owner is " + player_structure.getStructureOwner(self));
        player_structure.initiateRepairCheck(self);
        int hiringSkillMod = getSkillStatisticModifier(player, "hiring");
        if (isIdValid(player) && exists(player) && player.isLoaded() && hiringSkillMod >= 90)
        {
            setObjVar(self, player_structure.VAR_MAINTENANCE_MOD_MERCHANT, player_structure.MERCHANT_SALES_MODIFIER);
        }
        else 
        {
            removeObjVar(self, player_structure.VAR_MAINTENANCE_MOD_MERCHANT);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgDestroyStructure(obj_id self, dictionary params) throws InterruptedException
    {
        CustomerServiceLog("playerStructure", "Intiating destroy for " + self + " due to character deletion of its owner " + player_structure.getStructureOwner(self));
        player_structure.destroyStructure(self);
        return SCRIPT_CONTINUE;
    }
    public int removeResidentVar(obj_id self, dictionary params) throws InterruptedException
    {
        removeObjVar(self, player_structure.VAR_RESIDENCE_BUILDING);
        city.removeStructureFromCity(self);
        return SCRIPT_CONTINUE;
    }
    public int setNewMayor(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            CustomerServiceLog("player_city_transfer", "Failed to transfer Structure(" + self + ") to New Mayor due to Empty Params.");
            return SCRIPT_CONTINUE;
        }
        if (!player_structure.isCivic(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id new_mayor = params.getObjId("mayor");
        obj_id ousted_mayor = params.getObjId("ousted_mayor");
        if (!isIdValid(new_mayor) || !isIdValid(self) || !isIdValid(ousted_mayor))
        {
            CustomerServiceLog("player_city_transfer", "Failed to transfer Structure(" + self + ") to New Mayor (" + new_mayor + ") due to Invalid Mayor/Self ID.");
            return SCRIPT_CONTINUE;
        }
        int city_id = getCityAtLocation(getLocation(self), 0);
        if (city_id <= 0)
        {
            CustomerServiceLog("player_city_transfer", "Failed to transfer Structure(" + self + ") to New Mayor (" + new_mayor + ") due to Invalid City ID (" + city_id + "). Used getCityAtLocation - using Self");
            return SCRIPT_CONTINUE;
        }
        boolean itemTransfer = false;
        String template = getTemplateName(self);
        if (template.contains("cloning_") || template.contains("cityhall_"))
        {
            itemTransfer = true;
            LOG("sissynoid", "City Hall/Cloning Center Found - itemTransfer == true");
            CustomerServiceLog("player_city_transfer", "City Hall/Cloning Center Found - Checking for noTrade Items to Transfer. Structure ID (" + self + ")");
            city.moveNoTradeItemsInStructureToOwner(self, ousted_mayor, city_id);
        }
        dictionary transferParams = new dictionary();
        transferParams.put("new_mayor", new_mayor);
        transferParams.put("structure", self);
        if (itemTransfer)
        {
            LOG("sissynoid", "City Hall/Cloning Center(" + self + ") Found - Messaging to transfer - 10 second delay");
            messageTo(self, "transferStructureToNewMayor", transferParams, 10.0f, false);
        }
        else 
        {
            messageTo(self, "transferStructureToNewMayor", transferParams, 0.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int transferStructureToNewMayor(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("sissynoid", "Entered transferStructureToNewMayor");
        if (params == null || params.isEmpty())
        {
            LOG("sissynoid", "transferStructureToNewMayor was NULL or EMPTY");
            CustomerServiceLog("player_city_transfer", "Failed to transfer Structure(" + self + ") to New Mayor due to Invalid/Empty Params - transferParams");
            return SCRIPT_CONTINUE;
        }
        obj_id new_mayor = params.getObjId("new_mayor");
        obj_id structure = params.getObjId("structure");
        if (!isIdValid(new_mayor) || !isIdValid(structure))
        {
            LOG("sissynoid", "Invalid ID - New Mayor(" + new_mayor + ")/Structure(" + structure + ") - Failing Structure Transfer");
            return SCRIPT_CONTINUE;
        }
        setOwner(structure, new_mayor);
        obj_id[] adminList = new obj_id[1];
        adminList[0] = new_mayor;
        setObjVar(self, player_structure.VAR_ADMIN_LIST, adminList);
        LOG("sissynoid", "Successful Transfer of Structure(" + structure + ") to New Mayor(" + new_mayor + ")");
        CustomerServiceLog("player_city_transfer", "Success - Structure(" + structure + ") has successfully transferred to New Mayor(" + new_mayor + ").");
        return SCRIPT_CONTINUE;
    }
    public void updateOwnerAndAdmin(obj_id self, obj_id new_mayor) throws InterruptedException
    {
        if (!player_structure.isCivic(self))
        {
            return;
        }
        if (!isIdValid(new_mayor) || !isIdValid(self))
        {
            return;
        }
        int city_id = getCityAtLocation(getLocation(self), 0);
        if (city_id <= 0)
        {
            return;
        }
        obj_id mayor = cityGetLeader(city_id);
        if (mayor == new_mayor)
        {
            return;
        }
        setOwner(self, new_mayor);
        obj_id[] adminList = new obj_id[1];
        adminList[0] = new_mayor;
        setObjVar(self, player_structure.VAR_ADMIN_LIST, adminList);
        return;
    }
    public int handleChangeSignSui(obj_id self, dictionary params) throws InterruptedException
    {
        blog("permanent_structure.handleChangeSignSui INITIALIZED");
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        boolean owner = player_structure.isOwner(self, player);
        if (hasObjVar(self, player_structure.MODIFIED_HOUSE_SIGN_MODEL))
        {
            blog("permanent_structure.handleChangeSignSui REMOVING OLD HALLOWEEN SIGN");
            player_structure.revertCustomSign(player, self);
        }
        blog("permanent_structure.handleChangeSignSui checking to see if special sign exists as objvar");
        if (hasObjVar(self, player_structure.SPECIAL_SIGN))
        {
            if (hasObjVar(self, player_structure.SPECIAL_SIGN_OWNER_ONLY))
            {
                boolean needsOwnerToRemove = getBooleanObjVar(self, player_structure.SPECIAL_SIGN_OWNER_ONLY);
                if (!owner && needsOwnerToRemove)
                {
                    sendSystemMessage(player, player_structure.SID_ONLY_OWNER_CAN_REMOVE);
                    return SCRIPT_CONTINUE;
                }
            }
            if (!player_structure.removeSpecialSign(player, self, false))
            {
                blog("permanent_structure.handleSpecialSignManagementSelection removeSpecialSign failed!!!!");
            }
        }
        setObjVar(self, player_structure.VAR_SIGN_TYPE, idx);
        player_structure.createStructureSign(self);
        return SCRIPT_CONTINUE;
    }
    public int cityMapRegister(obj_id self, dictionary params) throws InterruptedException
    {
        if (player_structure.isCivic(self) || player_structure.isCommercial(self))
        {
            String template = getTemplateName(self);
            if ((template != null) || (!template.equals("")))
            {
                if (template.contains("object/building/player/city/garden_"))
                {
                    return SCRIPT_CONTINUE;
                }
            }
            if (!hasScript(self, "planet_map.map_loc_both"))
            {
                attachScript(self, "planet_map.map_loc_both");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int cityMapUnregister(obj_id self, dictionary params) throws InterruptedException
    {
        if (player_structure.isCivic(self) || player_structure.isCommercial(self))
        {
            if (hasScript(self, "planet_map.map_loc_both"))
            {
                detachScript(self, "planet_map.map_loc_both");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int takeCityDamage(obj_id self, dictionary params) throws InterruptedException
    {
        int city_id = getCityAtLocation(getLocation(self), 0);
        if (!cityExists(city_id))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id mayor = cityGetLeader(city_id);
        String mayor_name = cityGetCitizenName(city_id, mayor);
        String structure_name = localize(getNameStringId(self));
        int condition = player_structure.damageStructure(self, 300);
        if (condition <= 0)
        {
            prose_package bodypp = prose.getPackage(city.STRUCTURE_DESTROYED_MAINT_BODY, structure_name, mayor_name);
            utils.sendMail(city.STRUCTURE_DESTROYED_MAINT_SUBJECT, bodypp, mayor_name, "City Hall");
        }
        else 
        {
            int cost = params.getInt("cost");
            prose_package bodypp = prose.getPackage(city.STRUCTURE_DAMAGED_BODY, null, null, null, null, mayor_name, null, null, structure_name, null, cost, 0.0f);
            utils.sendMail(city.STRUCTURE_DAMAGED_SUBJECT, bodypp, mayor_name, "City Hall");
        }
        return SCRIPT_CONTINUE;
    }
    public int repairCityDamage(obj_id self, dictionary params) throws InterruptedException
    {
        int city_id = getCityAtLocation(getLocation(self), 0);
        if (!cityExists(city_id))
        {
            return SCRIPT_CONTINUE;
        }
        int condition = player_structure.getStructureCondition(self);
        int max_condition = player_structure.getMaxCondition(self);
        if (condition < max_condition)
        {
            player_structure.repairStructure(self, 300);
            obj_id mayor = cityGetLeader(city_id);
            String mayor_name = cityGetCitizenName(city_id, mayor);
            String structure_name = localize(getNameStringId(self));
            prose_package bodypp = prose.getPackage(city.STRUCTURE_REPAIRED_BODY, structure_name, mayor_name);
            utils.sendMail(city.STRUCTURE_REPAIRED_SUBJECT, bodypp, mayor_name, "City Hall");
        }
        return SCRIPT_CONTINUE;
    }
    public int createStructureObjects(obj_id self, dictionary params) throws InterruptedException
    {
        int rotation = params.getInt("rotation");
        player_structure.createStructureObjects(self, rotation);
        float rot_float = (90 * rotation);
        player_structure.createStructureSign(self, rot_float);
        return SCRIPT_CONTINUE;
    }
    public int requestBankBalance(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id requestingObj = params.getObjId("requestingObj");
        int balance = getBankBalance(self);
        dictionary newParam = new dictionary();
        newParam.put("balance", balance);
        newParam.put("structure", self);
        messageTo(requestingObj, "returnRequestedBankBalance", newParam, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int requestName(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id requestingObj = params.getObjId("requestingObj");
        String name = "";
        if (!player_structure.isInstallation(self))
        {
            if (hasObjVar(self, player_structure.VAR_SIGN_NAME))
            {
                name = getStringObjVar(self, player_structure.VAR_SIGN_NAME);
            }
        }
        else 
        {
            name = getEncodedName(self);
        }
        dictionary newParam = new dictionary();
        newParam.put("name", name);
        newParam.put("structure", self);
        messageTo(requestingObj, "returnRequestedName", newParam, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int handleBankTransferError(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objSender = null;
        if (utils.hasScriptVar(self, "objSender"))
        {
            objSender = utils.getObjIdScriptVar(self, "objSender");
            if (!exists(objSender))
            {
                objSender = null;
            }
        }
        int loops = params.getInt("intLoops");
        int cost = params.getInt("intCost");
        if ((loops == 0) && (cost == 0))
        {
            return SCRIPT_CONTINUE;
        }
        int current_time = getGameTime();
        if (isIdValid(objSender))
        {
            sendSystemMessageTestingOnly(self, "FAILED BANK TRANSFER!");
            sendSystemMessageTestingOnly(self, "loops " + loops);
            sendSystemMessageTestingOnly(self, "Cost " + cost);
            sendSystemMessageTestingOnly(self, "current_time " + current_time);
        }
        int damage = player_structure.getDecayRate(self) * loops;
        int condition = player_structure.damageStructure(self, damage, true);
        if (isIdValid(objSender))
        {
            sendSystemMessageTestingOnly(self, "Damaging Structure to " + damage);
        }
        LOG("LOG_CHANNEL", "damage ->" + damage + " condition ->" + condition);
        if (condition == 0)
        {
            if (isIdValid(objSender))
            {
                sendSystemMessageTestingOnly(self, "COndemning " + self);
            }
            CustomerServiceLog("playerStructure", "Condemning (" + self + ") owned by " + player_structure.getStructureOwner(self));
            if (!hasObjVar(self, "player_structurestructure.condemned"))
            {
                player_structure.sendCondemnedMail(self);
                setObjVar(self, "player_structure.condemned", current_time);
            }
        }
        setObjVar(self, player_structure.VAR_LAST_MAINTANENCE, current_time);
        if (isIdValid(objSender))
        {
            sendSystemMessageTestingOnly(self, "Doing housekeeping");
        }
        doHouseMaintenanceHousekeeping(self);
        return SCRIPT_CONTINUE;
    }
    public int handleBankSuccess(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objSender = null;
        if (utils.hasScriptVar(self, "objSender"))
        {
            objSender = utils.getObjIdScriptVar(self, "objSender");
            if (!exists(objSender))
            {
                objSender = null;
            }
        }
        if (isIdValid(objSender))
        {
            sendSystemMessageTestingOnly(objSender, "Succesfully took maintenance, resetting timestamp");
        }
        int loops = params.getInt("intLoops");
        int cost = params.getInt("intCost");
        if ((loops == 0) && (cost == 0))
        {
            return SCRIPT_CONTINUE;
        }
        int current_time = getGameTime();
        setObjVar(self, player_structure.VAR_LAST_MAINTANENCE, current_time);
        doHouseMaintenanceHousekeeping(self);
        return SCRIPT_CONTINUE;
    }
    public int payCondemnedFees(obj_id self, dictionary params) throws InterruptedException
    {
        if (!player_structure.isStructureCondemned(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id objPlayer = sui.getPlayerId(params);
        if (!isIdValid(objPlayer))
        {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        int bp = sui.getIntButtonPressed(params);
        if (bp != sui.BP_OK)
        {
            return SCRIPT_CONTINUE;
        }
        float fltDistance = getDistance(objPlayer, self);
        if (fltDistance > 32)
        {
            string_id strSpam = new string_id("player_structure", "too_far");
            sendSystemMessage(objPlayer, strSpam);
            return SCRIPT_CONTINUE;
        }
        int intRepairCost = player_structure.getStructureRepairCost(self);
        int intBankCredits = getBankBalance(objPlayer);
        if (intBankCredits >= intRepairCost)
        {
            int max_condition = player_structure.getMaxCondition(self);
            LOG("building", "max_condition is " + max_condition);
            player_structure.repairStructure(self, max_condition);
            money.bankTo(objPlayer, money.ACCT_STRUCTURE_MAINTENANCE, intRepairCost);
            CustomerServiceLog("playerStructure", "UNCOMDEMNING (" + self + ") owned by " + player_structure.getStructureOwner(self) + " -> Becuase they paid = " + intRepairCost);
            string_id strSpam = new string_id("player_structure", "structure_uncondemned");
            sendSystemMessage(objPlayer, strSpam);
        }
        return SCRIPT_CONTINUE;
    }
    public int moneyTransferredFromOwner(obj_id self, dictionary params) throws InterruptedException
    {
        int transferredAmount = params.getInt("amount");
        transferBankCreditsFromNamedAccount(money.ACCT_STRUCTURE_MAINTENANCE, self, transferredAmount, null, null, null);
        utils.removeScriptVar(self, "maint.dontCheckDB");
        messageTo(self, "OnMaintenanceLoop", null, player_structure.getMaintenanceHeartbeat(), false);
        return SCRIPT_CONTINUE;
    }
    public int ownerNotOnline(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "maint.dontCheckDB"))
        {
            int amount = params.getInt("offlineAmount");
            params.put("amount", amount);
            obj_id owner = params.getObjId("owner");
            getMoneyFromOfflineObject(owner, amount, money.ACCT_STRUCTURE_MAINTENANCE, "moneyTransferredFromOwner", "ownerNSF", params);
        }
        else 
        {
            messageTo(self, "handleBankTransferError", params, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int ownerNSF(obj_id self, dictionary params) throws InterruptedException
    {
        utils.setScriptVar(self, "maint.dontCheckDB", 1);
        messageTo(self, "handleBankTransferError", params, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int validateHopper(obj_id self, dictionary params) throws InterruptedException
    {
        int objvar_hopper = getIntObjVar(self, player_structure.VAR_DEED_MAX_HOPPER);
        int hopperSize = player_structure.validateHopperSize(self);
        if (objvar_hopper != hopperSize)
        {
            setObjVar(self, player_structure.VAR_DEED_MAX_HOPPER, hopperSize);
            setMaxHopperAmount(self, hopperSize);
        }
        return SCRIPT_CONTINUE;
    }
    public void updateFactoryName(obj_id factory) throws InterruptedException
    {
        if (player_structure.isFactory(factory))
        {
            String assignedName = getAssignedName(factory);
            if (!hasObjVar(factory, "player_structure.name.original"))
            {
                setObjVar(factory, "player_structure.name.original", assignedName == null ? "" : assignedName);
            }
            else 
            {
                if (assignedName != null && assignedName.contains(player_structure.ABANDONED_TEXT))
                {
                    assignedName = getStringObjVar(factory, "player_structure.name.original");
                }
                else 
                {
                    setObjVar(factory, "player_structure.name.original", assignedName == null ? "" : assignedName);
                }
            }
            if (assignedName == null || assignedName.length() == 0)
            {
                String templateName = getTemplateName(factory);
                switch (templateName) {
                    case "object/installation/manufacture/food_factory.iff":
                        assignedName = "Food and Chemical Factory";
                        break;
                    case "object/installation/manufacture/clothing_factory.iff":
                        assignedName = "Wearables Factory";
                        break;
                    case "object/installation/manufacture/weapon_factory.iff":
                        assignedName = "Equipment Factory";
                        break;
                    case "object/installation/manufacture/structure_factory.iff":
                        assignedName = "Structure Factory";
                        break;
                    default:
                        assignedName = "Factory";
                        break;
                }
            }
            setName(factory, assignedName + player_structure.ABANDONED_TEXT);
        }
    }
    public int checkAndMarkForPreAbandon(obj_id self, dictionary params) throws InterruptedException
    {
        if (isInWorldCell(self) && player_structure.needsPreAbandonCheck(self))
        {
            obj_id ownerId = getOwner(self);
            final boolean isPreAbandoned = player_structure.isPreAbandoned(self);
            final boolean isAbandoned = player_structure.isAbandoned(self);
            if (isAccountQualifiedForHousePackup(ownerId))
            {
                if (isPreAbandoned || isAbandoned)
                {
                    return SCRIPT_CONTINUE;
                }
                player_structure.setPreAbandoned(self);
                CustomerServiceLog("house_packup", "Structure (" + self + ") marked as preabandoned for player (" + getPlayerName(ownerId) + ") station_id (" + getPlayerStationId(ownerId) + ")");
                updateFactoryName(self);
                player_structure.createStructureSign(self);
                String[] adminList = player_structure.getAdminListRaw(self);
                if (adminList != null)
                {
                    String admins = "";
                    for (String s : adminList) {
                        if (s != null && !(s.toLowerCase()).contains("guild:")) {
                            admins += "|" + getPlayerStationId(utils.stringToObjId(s));
                            if (admins.length() > 256) {
                                CustomerServiceLog("house_packup_adminlist", admins + "|");
                                admins = "";
                            }
                        }
                    }
                    if (admins.length() > 0)
                    {
                        CustomerServiceLog("house_packup_adminlist", admins + "|");
                    }
                }
            }
            else 
            {
                if (!isPreAbandoned && !isAbandoned)
                {
                    return SCRIPT_CONTINUE;
                }
                final String abandonedState = isPreAbandoned ? "preabandoned" : isAbandoned ? "abandoned" : "none";
                player_structure.setStructureAsNotAbandoned(self);
                CustomerServiceLog("house_packup", "Structure (" + self + ") cleared for player (" + getPlayerName(ownerId) + ") station_id (" + getPlayerStationId(ownerId) + ") state (" + abandonedState + ")");
                if (player_structure.isFactory(self) && hasObjVar(self, "player_structure.name.original"))
                {
                    final String assignedName = getAssignedName(self);
                    if (assignedName != null && assignedName.contains(player_structure.ABANDONED_TEXT))
                    {
                        final String originalName = getStringObjVar(self, "player_structure.name.original");
                        setName(self, originalName);
                    }
                    removeObjVar(self, "player_structure.name.original");
                }
                player_structure.createStructureSign(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int checkAndMarkForAbandon(obj_id self, dictionary params) throws InterruptedException
    {
        if (isInWorldCell(self) && player_structure.needsAbandonCheck(self))
        {
            obj_id ownerId = getOwner(self);
            final boolean isPreAbandoned = player_structure.isPreAbandoned(self);
            final boolean isAbandoned = player_structure.isAbandoned(self);
            final String abandonedState = isPreAbandoned ? "preabandoned" : isAbandoned ? "abandoned" : "none";
            if (!isAccountQualifiedForHousePackup(ownerId))
            {
                if (isPreAbandoned || isAbandoned)
                {
                    player_structure.setStructureAsNotAbandoned(self);
                    CustomerServiceLog("house_packup", "Structure (" + self + ") saved for player (" + getPlayerName(ownerId) + ") station_id (" + getPlayerStationId(ownerId) + ") state (" + abandonedState + ")");
                    if (player_structure.isFactory(self) && hasObjVar(self, "player_structure.name.original"))
                    {
                        final String assignedName = getAssignedName(self);
                        if (assignedName != null && assignedName.contains(player_structure.ABANDONED_TEXT))
                        {
                            final String originalName = getStringObjVar(self, "player_structure.name.original");
                            setName(self, originalName);
                        }
                        removeObjVar(self, "player_structure.name.original");
                    }
                    player_structure.createStructureSign(self);
                }
            }
            else 
            {
                if (!isAbandoned)
                {
                    player_structure.setAbandoned(self);
                    CustomerServiceLog("house_packup", "Structure (" + self + ") marked as abandoned for player (" + getPlayerName(ownerId) + ") station_id (" + getPlayerStationId(ownerId) + ") state (" + abandonedState + ")");
                }
                updateFactoryName(self);
                if (!isPreAbandoned && !isAbandoned)
                {
                    player_structure.createStructureSign(self);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int logStructureInfo(obj_id self, dictionary params) throws InterruptedException
    {
        CustomerServiceLog("house_packup_info", player_structure.getStructureInfo(self));
        return SCRIPT_CONTINUE;
    }
    public int handleStructurePackupLockoutRemoval(obj_id self, dictionary params) throws InterruptedException
    {
        blog("permanent_structure.handleStructurePackupLockoutRemoval() Message received by player to remove lockout timer.");
        utils.removeScriptVarTree(self, player_structure.SCRIPTVAR_HOUSE_PACKUP_TREE_PREFIX);
        return SCRIPT_CONTINUE;
    }
    public int packAbandonedBuilding(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        player_structure.packAbandonedBuilding(player, self, params);
        return SCRIPT_CONTINUE;
    }
    public int moveStructureToSCD(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        if (!params.containsKey("structure") || !params.containsKey("structure_owner"))
        {
            blog("player_building.moveStructureToSCD() did not have the needed params to function");
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        if (!isValidId(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id structure = params.getObjId("structure");
        if (player_structure.isPackedUp(structure))
        {
            blog("player_buidling.moveStructureToSCD() ALREADY PACKED!!!! removing lockout from player.");
            messageTo(player, "handlePlayerStructurePackupLockoutRemoval", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        obj_id structure_owner = params.getObjId("structure_owner");
        if (!isValidId(structure) || !isValidId(structure_owner))
        {
            blog("player_building.moveStructureToSCD() MISSING PARAMS. ABORTING AIRSTRIKE");
            messageTo(player, "handlePlayerStructurePackupLockoutRemoval", null, 0, false);
            return SCRIPT_CONTINUE;
        }
        location where = getLocation(structure);
        boolean isAbandoned = true;
        obj_id datapad = utils.getPlayerDatapad(player);
        if (!isIdValid(datapad))
        {
            blog("player_structure.finalizeAbandonedStructurePackUp datapad not valid.");
            CustomerServiceLog("housepackup", "Structure (" + structure + ") could not be packed by player (" + getPlayerName(player) + ") - datapad was not valid");
            return SCRIPT_CONTINUE;
        }
        if (structure_owner.isLoaded() && structure_owner.isAuthoritative())
        {
            blog("player_structure.finalizeAbandonedStructurePackUp The owner is loaded in game.");
            obj_id owner_datapad = utils.getPlayerDatapad(structure_owner);
            if (isIdValid(owner_datapad))
            {
                datapad = owner_datapad;
            }
        }
        else if (!player.isLoaded() || !player.isAuthoritative())
        {
            blog("player_structure.finalizeAbandonedStructurePackUp The owner is NOT loaded in game and the player packing is not loaded or not authoritative.");
            CustomerServiceLog("housepackup", "Structure (" + structure + ") could not be packed by player (" + getPlayerName(player) + ") player -" + " isLoaded = " + player.isLoaded() + " isAuthoritative = " + player.isAuthoritative());
            return SCRIPT_CONTINUE;
        }
        obj_id scd = createObjectOverloaded("object/intangible/house/generic_house_control_device.iff", datapad);
        if (!isIdValid(scd))
        {
            CustomerServiceLog("housepackup", "Structure (" + structure + ") could not be packed by player (" + getPlayerName(player) + ") - scd was not valid");
            return SCRIPT_CONTINUE;
        }
        attachScript(scd, "structure.house_control_device");
        setObjVar(scd, "abandoned.packer", player);
        setObjVar(scd, "abandoned.owner", structure_owner);
        setObjVar(scd, "abandoned.structure", structure);
        if (player_structure.isFactory(structure) && isHarvesterActive(structure))
        {
            CustomerServiceLog("housepackup", "Structure (" + structure + ") was a factory that was active. Turning off factory for non-owner player (" + getPlayerName(player) + ") OID (" + player + ")");
            deactivate(structure);
        }
        obj_id[] players = player_structure.getPlayersInBuilding(structure);
        if (players != null)
        {
            blog("player_structure.finalizeAbandonedStructurePackUp Players are in the building that is about to be packed.");
            for (obj_id player1 : players) {
                expelFromBuilding(player1);
            }
        }
        String[] cells = getCellNames(structure);
        if (cells != null)
        {
            for (String cell : cells) {
                obj_id cellid = getCellId(structure, cell);
                obj_id contents[] = getContents(cellid);
                if (contents != null) {
                    for (obj_id content : contents) {
                        if (hasCondition(content, CONDITION_VENDOR)) {
                            obj_id owner = getObjIdObjVar(content, "vendor_owner");
                            if (!isIdValid(owner)) {
                                owner = getOwner(content);
                            }
                            vendor_lib.finalizePackUp(owner, content, player, isAbandoned);
                        }
                        if (isIdValid(content)) {
                            messageTo(content, "OnPack", null, 1.0f, false);
                        }
                    }
                }
            }
        }
        String structName = player_structure.getStructureName(structure);
        if (structName == null || structName.length() < 1)
        {
            structName = "Building";
        }
        if (hasObjVar(structure, player_structure.VAR_WAYPOINT_STRUCTURE))
        {
            obj_id waypoint = getObjIdObjVar(structure, player_structure.VAR_WAYPOINT_STRUCTURE);
            if (isIdValid(waypoint))
            {
                destroyWaypointInDatapad(waypoint, structure_owner);
                removeObjVar(structure, player_structure.VAR_WAYPOINT_STRUCTURE);
            }
        }
        player_structure.destroyStructureSign(structure);
        setName(scd, structName);
        if (player_structure.hasBeenDeclaredResidence(structure))
        {
            obj_id resident = player_structure.getResidentPlayer(structure);
            setHouseId(resident, obj_id.NULL_ID);
            removeObjVar(structure, player_structure.VAR_RESIDENCE_BUILDING);
            int cityId = getCitizenOfCityId(resident);
            if (cityId != 0)
            {
                cityRemoveCitizen(cityId, resident);
            }
        }
        city.removeStructureFromCity(structure);
        String template = getTemplateName(structure);
        player_structure.setDeedTemplate(scd, template);
        if (hasObjVar(structure, player_structure.VAR_DEED_SCENE))
        {
            setObjVar(scd, player_structure.VAR_DEED_SCENE, player_structure.getDeedScene(structure));
        }
        if (isIdValid(structure_owner) && exists(structure_owner))
        {
            obj_id lotOverlimitStructure = getObjIdObjVar(structure_owner, "lotOverlimit.structure_id");
            if (isIdValid(lotOverlimitStructure) && (lotOverlimitStructure == structure))
            {
                setObjVar(structure_owner, "lotOverlimit.structure_location", "Datapad");
            }
        }
        putIn(structure, scd);
        if (!structure_owner.isLoaded())
        {
            final int maxDepth = player_structure.isFactory(structure) ? 101 : 1;
            moveToOfflinePlayerDatapadAndUnload(scd, structure_owner, maxDepth + 1);
            fixLoadWith(structure, structure_owner, maxDepth);
        }
        CustomerServiceLog("housepackup", "Player " + getPlayerName(player) + " (" + player + ") packed structure (" + structure + ",abandoned=true," + where.toString() + ") owned by player " + getPlayerName(structure_owner) + " (" + structure_owner + ") into device (" + scd + ")");
        blog("player_buidling.moveStructureToSCD() all params accounted for. removing lockout.");
        messageTo(player, "handlePlayerStructurePackupLockoutRemoval", null, 1, false);
        blog("player_buidling.moveStructureToSCD() Point Increase for player.");
        player_structure.housePackingPointIncrease(player);
        return SCRIPT_CONTINUE;
    }
    public int cityMoveStructureToSCD(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("sissynoid", "Entered: cityMoveStructureToSCD");
        if ((params == null) || (params.isEmpty()))
        {
            LOG("sissynoid", "cityMoveStructureToSCD: Params were empty - Failing");
            return SCRIPT_CONTINUE;
        }
        if (!params.containsKey("structure") || !params.containsKey("structure_owner"))
        {
            LOG("sissynoid", "cityMoveStructureToSCD: Did not contain all keys needed to packup a house - Missing either 'structure' or 'structure_owner' key.");
            CustomerServiceLog("city_house_packup", "Failing Packup - Unable to Pack at this time due to 'structure/structure_owner' params missing.");
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        if (!isValidId(player) || !exists(player))
        {
            CustomerServiceLog("city_house_packup", "Failing Packup - Unable to pack at this time - Player ID (" + player + ") is attempting to pack a structure, but his ID is invalid.");
            return SCRIPT_CONTINUE;
        }
        obj_id structure = params.getObjId("structure");
        if (player_structure.isPackedUp(structure))
        {
            CustomerServiceLog("city_house_packup", "Failing Packup - Unable to pack at this time - Player ID (" + player + ") is attempting to pack a structure (" + structure + "), but the structure ID is invalid.");
            return SCRIPT_CONTINUE;
        }
        obj_id structure_owner = params.getObjId("structure_owner");
        if (!isValidId(structure) || !isValidId(structure_owner))
        {
            CustomerServiceLog("city_house_packup", "Failing Packup - Unable to pack at this time - Player ID (" + player + ") is attempting to pack a structure (" + structure + "), but the structure OWNER ID(" + structure_owner + ") is invalid.");
            return SCRIPT_CONTINUE;
        }
        location where = getLocation(structure);
        boolean isCityAbandoned = player_structure.isCityAbandoned(structure);
        obj_id datapad = utils.getPlayerDatapad(player);
        if (!isIdValid(datapad))
        {
            LOG("sissynoid", "cityMoveStructureToSCD: Invalid Datapad ID");
            CustomerServiceLog("city_house_packup", "Structure (" + structure + ") could not be packed by player (" + getPlayerName(player) + ") - datapad was not valid");
            return SCRIPT_CONTINUE;
        }
        if (!player_structure.isCityPackupAuthoritative(player, structure))
        {
            LOG("sissynoid", "cityMoveStructureToSCD: Player is not Loaded");
            CustomerServiceLog("city_house_packup", "'cityMoveStructureToSCD' - Structure (" + structure + ") could not be packed by player (" + getPlayerName(player) + ") player -" + " isLoaded(Player) = " + player.isLoaded() + " isAuthoritative(Player) = " + player.isAuthoritative() + " isLoaded(Structure) = " + structure.isLoaded() + " isAuthoritative(Structure) = " + structure.isAuthoritative());
            return SCRIPT_CONTINUE;
        }
        obj_id scd = createObjectOverloaded("object/intangible/house/generic_house_control_device.iff", datapad);
        if (!isIdValid(scd))
        {
            LOG("sissynoid", "cityMoveStructureToSCD: SCD is not valid");
            CustomerServiceLog("city_house_packup", "Structure (" + structure + ") could not be packed by player (" + getPlayerName(player) + ") - scd was not valid");
            return SCRIPT_CONTINUE;
        }
        attachScript(scd, "structure.house_control_device");
        setObjVar(scd, "abandoned.packer", player);
        setObjVar(scd, "abandoned.owner", structure_owner);
        setObjVar(scd, "abandoned.structure", structure);
        if (player_structure.isFactory(structure) && isHarvesterActive(structure))
        {
            LOG("sissynoid", "cityMoveStructureToSCD: We are packing a harvester/factory");
            CustomerServiceLog("city_house_packup", "Structure (" + structure + ") was a factory that was active. Turning off factory for non-owner player (" + getPlayerName(player) + ") OID (" + player + ")");
            deactivate(structure);
        }
        sendSystemMessage(player, SID_PACKING_CITY_ABANDONED_STRUCTURE);
        obj_id[] players = player_structure.getPlayersInBuilding(structure);
        if (players != null)
        {
            LOG("sissynoid", "cityMoveStructureToSCD: Ejecting Players from the house.");
            for (obj_id player1 : players) {
                expelFromBuilding(player1);
            }
        }
        String[] cells = getCellNames(structure);
        if (cells != null)
        {
            LOG("sissynoid", "cityMoveStructureToSCD: Removing Vendors");
            for (String cell : cells) {
                obj_id cellid = getCellId(structure, cell);
                obj_id contents[] = getContents(cellid);
                if (contents != null) {
                    for (obj_id content : contents) {
                        if (hasCondition(content, CONDITION_VENDOR)) {
                            obj_id owner = getObjIdObjVar(content, "vendor_owner");
                            if (!isIdValid(owner)) {
                                owner = getOwner(content);
                            }
                            vendor_lib.finalizePackUp(owner, content, player, isCityAbandoned);
                        }
                        if (isIdValid(content)) {
                            messageTo(content, "OnPack", null, 1.0f, false);
                        }
                    }
                }
            }
        }
        String structName = player_structure.getStructureName(structure);
        if (structName == null || structName.length() < 1)
        {
            LOG("sissynoid", "cityMoveStructureToSCD: Getting Structure Name");
            structName = "Building";
        }
        if (hasObjVar(structure, player_structure.VAR_WAYPOINT_STRUCTURE))
        {
            LOG("sissynoid", "cityMoveStructureToSCD: Destroying Waypoint.");
            obj_id waypoint = getObjIdObjVar(structure, player_structure.VAR_WAYPOINT_STRUCTURE);
            if (isIdValid(waypoint))
            {
                destroyWaypointInDatapad(waypoint, structure_owner);
                removeObjVar(structure, player_structure.VAR_WAYPOINT_STRUCTURE);
            }
        }
        player_structure.destroyStructureSign(structure);
        setName(scd, structName);
        if (player_structure.hasBeenDeclaredResidence(structure))
        {
            LOG("sissynoid", "cityMoveStructureToSCD: Removing Residency.");
            obj_id resident = player_structure.getResidentPlayer(structure);
            setHouseId(resident, obj_id.NULL_ID);
            removeObjVar(structure, player_structure.VAR_RESIDENCE_BUILDING);
            int cityId = getCitizenOfCityId(resident);
            if (cityId != 0)
            {
                cityRemoveCitizen(cityId, resident);
            }
        }
        city.removeStructureFromCity(structure);
        String template = getTemplateName(structure);
        player_structure.setDeedTemplate(scd, template);
        if (hasObjVar(structure, player_structure.VAR_DEED_SCENE))
        {
            LOG("sissynoid", "cityMoveStructureToSCD: Deed Scene Initialization");
            setObjVar(scd, player_structure.VAR_DEED_SCENE, player_structure.getDeedScene(structure));
        }
        if (isIdValid(structure_owner) && exists(structure_owner))
        {
            obj_id lotOverlimitStructure = getObjIdObjVar(structure_owner, "lotOverlimit.structure_id");
            if (isIdValid(lotOverlimitStructure) && (lotOverlimitStructure == structure))
            {
                setObjVar(structure_owner, "lotOverlimit.structure_location", "Datapad");
            }
        }
        putIn(structure, scd);
        final int maxDepth = player_structure.isFactory(structure) ? 101 : 1;
        moveToOfflinePlayerDatapadAndUnload(scd, structure_owner, maxDepth + 1);
        fixLoadWith(structure, structure_owner, maxDepth);
        CustomerServiceLog("housepackup", "Player " + getPlayerName(player) + " (" + player + ") packed structure (" + structure + ",abandoned=true," + where.toString() + ") owned by player " + getPlayerName(structure_owner) + " (" + structure_owner + ") into device (" + scd + ")");
        return SCRIPT_CONTINUE;
    }
    public int checkResidenceLinks(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        boolean confirmedResidenceOfPlayer = false;
        if (hasObjVar(self, player_structure.VAR_RESIDENCE_BUILDING))
        {
            obj_id resident = getObjIdObjVar(self, player_structure.VAR_RESIDENCE_BUILDING);
            if (resident == player)
            {
                confirmedResidenceOfPlayer = true;
            }
        }
        if (!confirmedResidenceOfPlayer)
        {
            messageTo(player, "residentLinkFalse", null, 1, false);
        }
        else if (isInWorldCell(self))
        {
            int city_id = getCityAtLocation(getLocation(self), 0);
            if (city_id > 0)
            {
                if (getCitizenOfCityId(player) <= 0)
                {
                    String playerName = params.getString("playerName");
                    if ((playerName != null) && (playerName.length() > 0))
                    {
                        citySetCitizenInfo(city_id, player, playerName, null, city.CP_CITIZEN);
                        CustomerServiceLog("CityFixup", "added " + player + " (" + playerName + ") (logged in and has declared residence) as citizen of city " + city_id + " (" + cityGetName(city_id) + ").");
                        obj_id mayorId = cityGetLeader(city_id);
                        if (isIdValid(mayorId))
                        {
                            String mayorName = cityGetCitizenName(city_id, mayorId);
                            if ((mayorName != null) && (mayorName.length() > 0))
                            {
                                prose_package bodypp = prose.getPackage(SID_NEW_CITY_FIXUP_CITIZEN_BODY, playerName);
                                utils.sendMail(SID_NEW_CITY_FIXUP_CITIZEN_SUBJECT, bodypp, mayorName, "City Hall");
                            }
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int houseCheck(obj_id self, dictionary params) throws InterruptedException
    {
        location landingSpot = params.getLocation("landingSpot");
        String groundScene = params.getString("groundScene");
        obj_id player = params.getObjId("player");
        obj_id house = params.getObjId("house");
        String myPlanet = getCurrentSceneName();
        obj_id[] testHouses = getAllObjectsWithObjVar(landingSpot, 250, "player_structure.admin.adminList");
        Vector houses = new Vector(Arrays.asList(testHouses));
        if (myPlanet.equals(groundScene))
        {
            if (houses != null)
            {
                if (houses.contains(house))
                {
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    removeObjVar(player, "homingBeacon");
                    sendSystemMessage(player, SID_BEACON_MISSING);
                }
            }
        }
        else 
        {
            removeObjVar(player, "homingBeacon");
            sendSystemMessage(player, SID_BEACON_MISSING);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleUnloadedCityStructureReportData(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id city_terminal = params.getObjId("city_terminal");
        int city_id = params.getInt("city_id");
        if (!isIdValid(player) || !isIdValid(city_terminal))
        {
            return SCRIPT_CONTINUE;
        }
        if (!city.isNormalStructure(city_id, self))
        {
            location structureLocation = getLocation(self);
            String structureName = localize(getNameStringId(self));
            dictionary dict = new dictionary();
            dict.put("player", player);
            dict.put("city_id", city_id);
            dict.put("city_terminal", city_terminal);
            dict.put("unloadedStructureId", self);
            dict.put("unloadedStructureName", structureName);
            dict.put("unloadedStructureLocation", structureLocation);
            messageTo(city_terminal, "RecievedUnloadedStructureResponse", dict, 0.0f, false);
        }
        else if (player_structure.isCivic(self))
        {
            location structureLocation = getLocation(self);
            float cond = ((float)player_structure.getStructureCondition(self)) / player_structure.getMaxCondition(self);
            int outcond = (int)(cond * 100);
            String structureName = getEncodedName(self) + " (Condition : " + outcond + "%)";
            dictionary dict = new dictionary();
            dict.put("player", player);
            dict.put("city_id", city_id);
            dict.put("city_terminal", city_terminal);
            dict.put("unloadedStructureId", self);
            dict.put("unloadedStructureName", structureName);
            dict.put("unloadedStructureLocation", structureLocation);
            messageTo(city_terminal, "RecievedUnloadedStructureResponse", dict, 0.0f, false);
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int getStructureCitizenInformation(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        int city_id = params.getInt("city_id");
        if (city.isNormalStructure(city_id, self) && !player_structure.isCivic(self))
        {
            obj_id terminal = params.getObjId("terminal_id");
            if (hasObjVar(self, player_structure.VAR_RESIDENCE_BUILDING))
            {
                obj_id citizenId = getObjIdObjVar(self, player_structure.VAR_RESIDENCE_BUILDING);
                params.put("house_id", self);
                params.put("house_name", getEncodedName(self));
                params.put("house_loc", getLocation(self));
                params.put("citizen_id", citizenId);
                messageTo(terminal, "getStructureCitizenInformationReturn", params, 0.0f, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSpecialSignManagementSelection(obj_id self, dictionary params) throws InterruptedException
    {
        blog("permanent_structure.handleSpecialSignManagementSelection init");
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        blog("permanent_structure.handleSpecialSignManagementSelection getting button press");
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            removeSpecialSignScriptVars(player);
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            removeSpecialSignScriptVars(player);
            return SCRIPT_CONTINUE;
        }
        blog("permanent_structure.handleSpecialSignManagementSelection finding selection");
        String[] menu = utils.getStringArrayScriptVar(player, player_structure.VAR_SPECIAL_SIGN_MENU);
        if (menu == null || menu.length <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        blog("permanent_structure.handleSpecialSignManagementSelection retrieved scriptvar list of menu selections");
        String selection = menu[idx];
        if (selection == null || selection.length() <= 0)
        {
            removeSpecialSignScriptVars(player);
            return SCRIPT_CONTINUE;
        }
        blog("permanent_structure.handleSpecialSignManagementSelection the selection was: " + selection);
        boolean owner = player_structure.isOwner(self, player);
        if (hasObjVar(self, player_structure.SPECIAL_SIGN_OWNER_ONLY))
        {
            boolean needsOwnerToRemove = getBooleanObjVar(self, player_structure.SPECIAL_SIGN_OWNER_ONLY);
            if (!owner && needsOwnerToRemove)
            {
                sendSystemMessage(player, player_structure.SID_ONLY_OWNER_CAN_REMOVE);
                return SCRIPT_CONTINUE;
            }
        }
        blog("permanent_structure.handleSpecialSignManagementSelection Checking for data.");
        if (hasObjVar(self, player_structure.MODIFIED_HOUSE_SIGN_MODEL))
        {
            blog("permanent_structure.handleSpecialSignSelection REMOVING OLD HALLOWEEN SIGN");
            player_structure.revertCustomSign(player, self);
        }
        if (hasObjVar(self, player_structure.SPECIAL_SIGN) && selection.startsWith("remove"))
        {
            if (!player_structure.removeSpecialSign(player, self, true))
            {
                blog("permanent_structure.handleSpecialSignManagementSelection removeSpecialSign failed!!!!");
            }
            return SCRIPT_CONTINUE;
        }
        else if (hasObjVar(self, player_structure.SPECIAL_SIGN) && !selection.startsWith("remove"))
        {
            blog("permanent_structure.handleSpecialSignManagementSelection case was replace");
            if (!player_structure.removeSpecialSign(player, self, false))
            {
                blog("permanent_structure.handleSpecialSignManagementSelection removeSpecialSign failed!!!!");
            }
        }
        if (sui.hasPid(player, player_structure.VAR_SPECIAL_SIGN_MENU_PID))
        {
            int pid = sui.getPid(player, player_structure.VAR_SPECIAL_SIGN_MENU_PID);
            forceCloseSUIPage(pid);
        }
        blog("permanent_structure.handleSpecialSignManagementSelection getting specialSignList");
        String[] specialSignList = utils.getStringArrayScriptVar(player, player_structure.VAR_SPECIAL_SIGN_NAMES);
        if (specialSignList == null || specialSignList.length <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        blog("permanent_structure.handleSpecialSignManagementSelection: specialSignList  received, sending player to next menu");
        int pid = sui.listbox(self, player, "@base_player:special_sign_ui_prompt", sui.OK_CANCEL, "@base_player:special_sign_ui_title", specialSignList, "handleSpecialSignSelection", true);
        sui.setPid(player, pid, player_structure.VAR_SPECIAL_SIGN_MENU_PID);
        return SCRIPT_CONTINUE;
    }
    public int handleSpecialSignSelection(obj_id self, dictionary params) throws InterruptedException
    {
        blog("permanent_structure.handleSpecialSignSelection: init");
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            removeSpecialSignScriptVars(player);
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            removeSpecialSignScriptVars(player);
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, player_structure.MODIFIED_HOUSE_SIGN_MODEL))
        {
            blog("permanent_structure.handleSpecialSignSelection REMOVING OLD HALLOWEEN SIGN");
            player_structure.revertCustomSign(player, self);
        }
        blog("permanent_structure.handleSpecialSignSelection: validation completed");
        String[] specialSignList = utils.getStringArrayScriptVar(player, player_structure.VAR_SPECIAL_SIGN_LIST);
        if (specialSignList == null || specialSignList.length <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        blog("permanent_structure.handleSpecialSignSelection retrieved scriptvar list of menu selections");
        String selection = specialSignList[idx];
        if (selection == null || selection.length() <= 0)
        {
            removeSpecialSignScriptVars(player);
            return SCRIPT_CONTINUE;
        }
        blog("permanent_structure.handleSpecialSignSelection the selection was: " + selection);
        int row = dataTableSearchColumnForString(selection, "sign_template", player_structure.TBL_SPECIAL_SIGNS);
        if (row < 0)
        {
            return SCRIPT_CONTINUE;
        }
        dictionary dict = dataTableGetRow(player_structure.TBL_SPECIAL_SIGNS, row);
        if (dict == null)
        {
            return SCRIPT_CONTINUE;
        }
        int signType = dict.getInt("sign_type");
        if (signType < 0)
        {
            return SCRIPT_CONTINUE;
        }
        int decrementSkillFlag = dict.getInt("decrement_skillmod");
        if (decrementSkillFlag < 0)
        {
            return SCRIPT_CONTINUE;
        }
        String skillmod = dict.getString("skillmod_name");
        if (skillmod == null || skillmod.length() <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        int adminPlacement = dict.getInt("admin_placement");
        if (adminPlacement < 0)
        {
            return SCRIPT_CONTINUE;
        }
        if (decrementSkillFlag > 0)
        {
            blog("permanent_structure.handleSpecialSignManagementSelection decrementSkillFlag GREATER THAN ZERO");
            if (!applySkillStatisticModifier(player, skillmod, -1))
            {
                CustomerServiceLog("playerStructure", "Special Sign could not be applied to structure " + self + ". The player: " + player + ". Reason: skillmod could not be decremented.");
                sendSystemMessage(player, player_structure.SID_SPECIAL_SIGN_FAILED);
                return SCRIPT_CONTINUE;
            }
            setObjVar(self, player_structure.SPECIAL_SIGN_DECREMENT_MOD, true);
        }
        blog("permanent_structure.handleSpecialSignManagementSelection applyign the data to the structure");
        setObjVar(self, player_structure.SPECIAL_SIGN, true);
        setObjVar(self, player_structure.SPECIAL_SIGN_TEMPLATE, selection);
        setObjVar(self, player_structure.VAR_SIGN_TYPE, signType);
        setObjVar(self, player_structure.SPECIAL_SIGN_SKILLMOD, skillmod);
        if (adminPlacement == 0)
        {
            setObjVar(self, player_structure.SPECIAL_SIGN_OWNER_ONLY, true);
        }
        if (!isValidId(player_structure.createStructureSign(self)))
        {
            blog("permanent_structure.handleSpecialSignManagementSelection THIS DID NOT WORK CORRECTLY!! GIVING PLAYER STUFF BACK");
            CustomerServiceLog("playerStructure", "Special Sign could not be applied to structure " + self + ". The player: " + player);
            sendSystemMessage(player, player_structure.SID_SPECIAL_SIGN_FAILED);
            if (decrementSkillFlag > 0)
            {
                applySkillStatisticModifier(self, skillmod, 1);
            }
            removeObjVar(self, player_structure.SPECIAL_SIGN);
            removeObjVar(self, player_structure.SPECIAL_SIGN_TEMPLATE);
            removeObjVar(self, player_structure.VAR_SIGN_TYPE);
            removeObjVar(self, player_structure.SPECIAL_SIGN_SKILLMOD);
            removeObjVar(self, player_structure.SPECIAL_SIGN_DECREMENT_MOD);
            removeObjVar(self, player_structure.SPECIAL_SIGN_OWNER_ONLY);
            removeSpecialSignScriptVars(player);
            return SCRIPT_CONTINUE;
        }
        blog("permanent_structure.handleSpecialSignManagementSelection Looks like it all went ok");
        removeSpecialSignScriptVars(player);
        CustomerServiceLog("playerStructure", "Player: " + player + " has used Special Sign skillmod: " + skillmod + " on structure: " + self + ". The special sign was created on the structure and the skillmod has been decremented by 1.");
        sendSystemMessage(player, player_structure.SID_SPECIAL_SIGN_SUCCESS);
        return SCRIPT_CONTINUE;
    }
    public boolean removeSpecialSignScriptVars(obj_id player) throws InterruptedException
    {
        utils.removeScriptVarTree(player, player_structure.VAR_SPECIAL_SIGN_TREE);
        return true;
    }
    public int checkCityHousePackupStatus(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("sissynoid", "Checking Pack Status of HOuse " + self);
        if (!cityIsInactivePackupActive())
        {
            LOG("sissynoid", "City Packup is not active yet.");
            return SCRIPT_CONTINUE;
        }
        obj_id owner = player_structure.getStructureOwnerObjId(self);
        if (!isIdValid(owner))
        {
            LOG("sissynoid", "City Pack Up System - Owner of Structure(" + self + ") was invalid");
            CustomerServiceLog("city_house_packup", "City Pack Up System - Owner of Structure(" + self + ") was invalid");
            return SCRIPT_CONTINUE;
        }
        if (!isPlayer(owner))
        {
            LOG("sissynoid", "City Pack Up System - Owner of Structure(" + self + ") no longer exists in the DB");
            CustomerServiceLog("city_house_packup", "City Pack Up System - Owner of Structure(" + self + ") no longer exists in the DB");
            return SCRIPT_CONTINUE;
        }
        int curTime = getCalendarTime();
        if (player_structure.doesUnmarkedStructureQualifyForHousePackup(self))
        {
            int city_id = getCityAtLocation(getLocation(self), 0);
            if (cityExists(city_id))
            {
                int createTime = cityGetCreationTime(city_id);
                if ((curTime - createTime) > 1814400)
                {
                    obj_id city_mayor = cityGetLeader(city_id);
                    if (!city.hasMayorProtectionFlag(owner, city_id))
                    {
                        int loginTime = getPlayerLastLoginTime(owner);
                        if ((curTime - loginTime) > cityGetInactivePackupInactiveTimeSeconds())
                        {
                            if (!player_structure.isCityAbandoned(self))
                            {
                                player_structure.setCityAbandoned(self);
                                player_structure.updateCityAbandonedFactoryName(self);
                                LOG("sissynoid", "City Pack Up System - Structure(" + self + ") Owner(" + owner + ") has been offline for more than 90 Days.  Setting House to be Packed Up!");
                                CustomerServiceLog("city_house_packup", "City Pack Up System - Structure(" + self + ") Owner(" + owner + ") has been offline for more than 90 Days.  Setting House to be Packed Up!");
                                return SCRIPT_CONTINUE;
                            }
                        }
                        if ((curTime - loginTime) > (60 * 24 * 60 * 60))
                        {
                            if (hasObjVar(self, "city_packup.inactive_citizen_mail_sent"))
                            {
                                return SCRIPT_CONTINUE;
                            }
                            else 
                            {
                                sendMayorInactiveCitizenMail(self, owner, city_mayor, city_id);
                                LOG("sissynoid", "City Pack Up System - Structure(" + self + ") Owner(" + owner + ") has been offline for more than 60 Days.  Sending Mayor In-Game Mail");
                                CustomerServiceLog("city_house_packup", "City Pack Up System - Structure(" + self + ") Owner(" + owner + ") has been offline for more than 60 Days.  Sending Mayor In-Game Mail");
                                return SCRIPT_CONTINUE;
                            }
                        }
                        if (player_structure.isCityAbandoned(self))
                        {
                            player_structure.removeCityAbandoned(self);
                            if (hasObjVar(self, "city_packup.inactive_citizen_mail_sent"))
                            {
                                removeObjVar(self, "city_packup.inactive_citizen_mail_sent");
                            }
                        }
                    }
                    else 
                    {
                        LOG("sissynoid", "City Pack Up System - Structure(" + self + ") Owner(" + owner + ") is PROTECTED by the Mayor of City(" + city_id + ") " + cityGetName(city_id) + ".");
                        CustomerServiceLog("city_house_packup", "City Pack Up System - Structure(" + self + ") Owner(" + owner + ") is PROTECTED by the Mayor of City(" + city_id + ") " + cityGetName(city_id) + ".");
                    }
                }
                else 
                {
                    LOG("sissynoid", "City Pack Up System - Structure(" + self + ") Owner(" + owner + ") can not be flagged at this time due to City(" + city_id + ") " + cityGetName(city_id) + " being too young (less than 3 weeks old).");
                    CustomerServiceLog("city_house_packup", "City Pack Up System - Structure(" + self + ") Owner(" + owner + ") can not be flagged at this time due to City(" + city_id + ") " + cityGetName(city_id) + " being too young (less than 3 weeks old).");
                }
            }
            else 
            {
                LOG("sissynoid", "City Pack Up System - Structure(" + self + ") Owner(" + owner + ") is not currently within City Limits and is not eligible for City Pack Up System.");
                CustomerServiceLog("city_house_packup", "City Pack Up System - Structure(" + self + ") Owner(" + owner + ") is not currently within City Limits and is not eligible for City Pack Up System.");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void sendMayorInactiveCitizenMail(obj_id house, obj_id owner, obj_id city_mayor, int city_id) throws InterruptedException
    {
        prose_package bodypp = prose.getPackage(SID_INACTIVE_CITIZEN_WARNING_BODY);
        prose.setTT(bodypp, cityGetCitizenName(city_id, owner));
        utils.sendMail(SID_INACTIVE_CITIZEN_WARNING_SUBJECT, bodypp, city_mayor, "City Hall");
        setObjVar(house, "city_packup.inactive_citizen_mail_sent", 1);
    }
    public boolean blog(String msg) throws InterruptedException
    {
        if (LOGGING_ON && msg != null && !msg.equals(""))
        {
            LOG(LOGGING_CATEGORY, msg);
        }
        return true;
    }
    public int retrieveHouseCoords(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id requester = params.getObjId("requestingObject");
        obj_id homeOwner = params.getObjId("homeOwner");
        if (!isIdValid(homeOwner))
        {
            CustomerServiceLog("travel", "TCG HOME ITV: Invalid Home Owner when using Home ITV.");
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(requester))
        {
            CustomerServiceLog("travel", "TCG HOME ITV: Invalid Requester when using Home ITV.");
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "player_structure.residence.building"))
        {
            int city_id = getCityAtLocation(getLocation(self), 0);
            if (city.isTheCityMayor(homeOwner, city_id))
            {
                sendCoordsToRequester(self, homeOwner, requester);
            }
            else 
            {
                CustomerServiceLog("travel", "TCG HOME ITV: Player(" + homeOwner + ") did not have a residence set when using the Home ITV");
                sendSystemMessage(homeOwner, new string_id("tcg", "no_residence_home_itv"));
                return SCRIPT_CONTINUE;
            }
        }
        else 
        {
            obj_id residenceObjVarOwner = getObjIdObjVar(self, "player_structure.residence.building");
            if (residenceObjVarOwner != homeOwner)
            {
                CustomerServiceLog("travel", "TCG HOME ITV: Home Owner(" + homeOwner + ") and ITV user(" + residenceObjVarOwner + ") do not match.");
                sendSystemMessage(homeOwner, new string_id("tcg", "no_residence_home_itv"));
                return SCRIPT_CONTINUE;
            }
        }
        sendCoordsToRequester(self, homeOwner, requester);
        return SCRIPT_CONTINUE;
    }
    public void sendCoordsToRequester(obj_id structure, obj_id homeOwner, obj_id requester) throws InterruptedException
    {
        location residenceLoc = getBuildingEjectLocation(structure);
        String homePlanet = getCurrentSceneName();
        dictionary dict = new dictionary();
        dict.put("residenceLocation", residenceLoc);
        dict.put("homePlanet", homePlanet);
        dict.put("homeOwner", homeOwner);
        dict.put("itv", requester);
        messageTo(requester, "ownerResidenceLocationResponse", dict, 0.0f, false);
    }
    public int validateStructureStorageOverride(obj_id self, dictionary params) throws InterruptedException
    {
        String datatable = "datatables/structure/structure_storage_override.iff";
        String structureTemplate = getTemplateName(self);
        dictionary structureStorageOverrideData = dataTableGetRow(datatable, structureTemplate);
        if (structureStorageOverrideData != null && !structureStorageOverrideData.isEmpty())
        {
            int expectedStorageOverrideValue = structureStorageOverrideData.getInt("OVERRIDE_STORAGE_VALUE");
            if (expectedStorageOverrideValue <= 0)
            {
                return SCRIPT_CONTINUE;
            }
            if (hasObjVar(self, "structure.capacity_override"))
            {
                int currentStorageOverrideValue = getIntObjVar(self, "structure.capacity_override");
                if (expectedStorageOverrideValue != currentStorageOverrideValue)
                {
                    setObjVar(self, "structure.capacity_override", expectedStorageOverrideValue);
                }
            }
            else 
            {
                setObjVar(self, "structure.capacity_override", expectedStorageOverrideValue);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
