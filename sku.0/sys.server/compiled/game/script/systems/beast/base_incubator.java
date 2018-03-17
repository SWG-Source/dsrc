package script.systems.beast;

import script.*;
import script.library.*;

public class base_incubator extends script.base_script
{
    public base_incubator()
    {
    }
    public static final string_id SID_WHILE_DEAD = new string_id("player_structure", "while_dead");
    public static final string_id SID_TERMINAL_MANAGEMENT = new string_id("player_structure", "incubator_management");
    public static final string_id SID_TERMINAL_MANAGEMENT_POWER_REMOVE = new string_id("player_structure", "incubator_power_remove");
    public static final string_id SID_TERMINAL_MANAGEMENT_POWER_ADD = new string_id("player_structure", "incubator_power");
    public static final string_id SID_TERMINAL_MANAGEMENT_ACTIVATE = new string_id("player_structure", "incubator_activate");
    public static final string_id SID_TERMINAL_MANAGEMENT_MINIGAME = new string_id("player_structure", "incubator_minigame");
    public static final string_id SID_TERMINAL_MANAGEMENT_END_CREATION = new string_id("player_structure", "incubator_cancel");
    public static final string_id SID_TERMINAL_MANAGEMENT_HATCH = new string_id("player_structure", "incubator_hatch");
    public static final string_id SID_TERMINAL_MANAGEMENT_TIMERCLEAR = new string_id("player_structure", "incubator_clear_cooldown");
    public static final string_id SID_ACTIVATE_MSG_PROMPT = new string_id("beast", "incubator_activate_text");
    public static final string_id SID_ACTIVATE_MSG_TITLE = new string_id("beast", "incubator_activate_title");
    public static final string_id SID_CLEANSE_MSG_PROMPT = new string_id("beast", "incubator_cleanse_text");
    public static final string_id SID_HOME_OWNER_CLEANSE_MSG_PROMPT = new string_id("beast", "incubator_home_owner_cleanse");
    public static final string_id SID_CLEANSE_MSG_TITLE = new string_id("beast", "incubator_cleanse_title");
    public static final string_id SID_TERMINAL_OPEN = new string_id("beast", "incubator_open");
    public static final string_id SID_CHEATER_TRANSFER = new string_id("incubator", "cheater_transfer");
    public static final string_id SID_CHEATER_BAD_ID = new string_id("incubator", "cheater_bad_id");
    public static final string_id SID_CHEATER_INVALID_STATS = new string_id("bio_engineer", "pet_sui_fix_error");
    public static final string_id SID_SESSION_TOO_SOON = new string_id("incubator", "not_enough_time_since_last_session");
    public static final string_id SID_NO_REMOVE_DNA = new string_id("incubator", "station_no_remove_dna");
    public static final string_id SID_PLACE_DNA = new string_id("incubator", "place_dna");
    public static final string_id SID_REMOVE_EGG = new string_id("incubator", "remove_egg");
    public static final string_id SID_ONLY_DNA_CAN_ADD = new string_id("incubator", "only_dna_can_add");
    public static final string_id SID_STATION_HAS_DNA_ALREADY = new string_id("incubator", "station_has_dna_already");
    public static final string_id SID_NO_TRIAL_ACCOUNTS = new string_id("incubator", "no_trial_accounts");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (utils.isInHouseCellSpace(self))
        {
            createTriggerVolume("particle", 15f, true);
            messageTo(self, "refreshCurrentParticle", null, 5, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volName, obj_id breecher) throws InterruptedException
    {
        if (isPlayer(breecher))
        {
            messageTo(self, "refreshCurrentParticle", null, 2, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id station = self;
        if (isDead(player) || isIncapacitated(player))
        {
            sendSystemMessage(player, SID_WHILE_DEAD);
            return SCRIPT_CONTINUE;
        }
        if (isFreeTrialAccount(player))
        {
            sendSystemMessage(player, SID_NO_TRIAL_ACCOUNTS);
            return SCRIPT_CONTINUE;
        }
        if (incubator.hasActiveUser(station) && incubator.hasActiveIncubator(player))
        {
            incubator.checkIncubatorForMismatch(station, player);
        }
        if (beast_lib.isBeastMaster(player) && utils.isInHouseCellSpace(station))
        {
            obj_id structure = getTopMostContainer(station);
            if (!player_structure.isAdmin(structure, player))
            {
                return SCRIPT_CONTINUE;
            }
            int management_root = mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_TERMINAL_MANAGEMENT);
            if (incubator.validateActiveUser(station, player) && incubator.hasPower(station))
            {
                mi.addSubMenu(management_root, menu_info_types.SERVER_MENU2, SID_TERMINAL_MANAGEMENT_POWER_REMOVE);
            }
            if (incubator.validateActiveUser(station, player) && utils.hasResourceInInventory(player, resource.RT_ENERGY_GEO))
            {
                mi.addSubMenu(management_root, menu_info_types.SERVER_MENU3, SID_TERMINAL_MANAGEMENT_POWER_ADD);
            }
            if (!incubator.hasActiveUser(station) && !incubator.validateActiveUser(station, player))
            {
                mi.addSubMenu(management_root, menu_info_types.SERVER_MENU4, SID_TERMINAL_MANAGEMENT_ACTIVATE);
            }
            if (incubator.validateActiveUser(station, player))
            {
                mi.addSubMenu(management_root, menu_info_types.SERVER_MENU7, SID_TERMINAL_MANAGEMENT_END_CREATION);
            }
            if (incubator.isSessionEligible(player) && incubator.validateActiveUser(station, player) && incubator.stationHasDnaInInventory(station) && incubator.getCurrentSessionNumber(station) <= 3)
            {
                mi.addSubMenu(management_root, menu_info_types.SERVER_MENU5, SID_TERMINAL_MANAGEMENT_MINIGAME);
            }
            if (incubator.isIncubationComplete(station, player) && incubator.validateActiveUser(station, player) && !incubator.isReadyToRetrieveEgg(station))
            {
                mi.addSubMenu(management_root, menu_info_types.SERVER_MENU6, SID_TERMINAL_MANAGEMENT_HATCH);
            }
            if (incubator.canOpenInventory(station) && incubator.validateActiveUser(station, player))
            {
                mi.addSubMenu(management_root, menu_info_types.ITEM_OPEN, SID_TERMINAL_OPEN);
            }
            if (isGod(player))
            {
                mi.addSubMenu(management_root, menu_info_types.SERVER_MENU8, SID_TERMINAL_MANAGEMENT_TIMERCLEAR);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        sendDirtyObjectMenuNotification(self);
        if (isDead(player) || isIncapacitated(player))
        {
            sendSystemMessage(player, SID_WHILE_DEAD);
            return SCRIPT_CONTINUE;
        }
        if (isFreeTrialAccount(player))
        {
            sendSystemMessage(player, SID_NO_TRIAL_ACCOUNTS);
            return SCRIPT_CONTINUE;
        }
        obj_id station = self;
        if (beast_lib.isBeastMaster(player) && utils.isInHouseCellSpace(station))
        {
            obj_id structure = getTopMostContainer(station);
            if (!player_structure.isAdmin(structure, player))
            {
                return SCRIPT_CONTINUE;
            }
            if (item == menu_info_types.SERVER_MENU1)
            {
            }
            else if (item == menu_info_types.SERVER_MENU2)
            {
                if (incubator.validateActiveUser(station, player))
                {
                    queueCommand(player, (-805193027), station, "", COMMAND_PRIORITY_DEFAULT);
                }
            }
            else if (item == menu_info_types.SERVER_MENU3)
            {
                if (incubator.validateActiveUser(station, player))
                {
                    queueCommand(player, (1833062743), station, "", COMMAND_PRIORITY_DEFAULT);
                }
            }
            else if (item == menu_info_types.SERVER_MENU4)
            {
                if (incubator.hasActiveUser(station))
                {
                    sendSystemMessage(player, new string_id("beast", "incubator_already_active"));
                }
                else 
                {
                    int pid = sui.msgbox(station, player, "@" + SID_ACTIVATE_MSG_PROMPT, sui.YES_NO, "@" + SID_ACTIVATE_MSG_TITLE, "handleActivateSui");
                }
            }
            else if (item == menu_info_types.SERVER_MENU5)
            {
                if (!incubator.validateActiveUser(station, player))
                {
                    sendSystemMessage(player, new string_id("beast", "incubator_not_yours"));
                }
                else 
                {
                    if (utils.hasScriptVar(player, incubator.GUI_SCRIPT_VAR) && !isGod(player))
                    {
                        int lastSessionTime = utils.getIntScriptVar(player, incubator.GUI_SCRIPT_VAR);
                        int currentTime = getGameTime();
                        int timeSinceSession = currentTime - lastSessionTime;
                        if (timeSinceSession > incubator.GUI_TIME_TO_REFRESH)
                        {
                            incubator.startSession(station, player);
                        }
                        else 
                        {
                            sendSystemMessage(player, SID_SESSION_TOO_SOON);
                        }
                    }
                    else 
                    {
                        incubator.startSession(station, player);
                    }
                }
            }
            else if (item == menu_info_types.SERVER_MENU6)
            {
                if (incubator.isIncubationComplete(station, player))
                {
                    obj_id egg = incubator.convertDnaToEgg(station, player);
                    if (isIdNull(egg))
                    {
                    }
                }
            }
            else if (item == menu_info_types.SERVER_MENU7)
            {
                if (incubator.validateActiveUser(station, player))
                {
                    int pid = sui.msgbox(station, player, "@" + SID_CLEANSE_MSG_PROMPT, sui.YES_NO, "@" + SID_CLEANSE_MSG_TITLE, "handleCleanseSui");
                }
                else 
                {
                    sendSystemMessage(player, new string_id("beast", "not_active_user"));
                }
            }
            else if (item == menu_info_types.SERVER_MENU8 && isGod(player))
            {
                incubator.clearMiniGameCooldown(player);
                sendSystemMessage(player, new string_id("beast", "incubator_cooldown_reset"));
            }
            else if (item == menu_info_types.ITEM_OPEN && incubator.canOpenInventory(station) && !hasObjVar(station, incubator.OBJVAR_EGG_CREATED))
            {
                sendSystemMessage(player, SID_PLACE_DNA);
            }
            else if (item == menu_info_types.ITEM_OPEN && incubator.canOpenInventory(station) && hasObjVar(station, incubator.OBJVAR_EGG_CREATED))
            {
                sendSystemMessage(player, SID_REMOVE_EGG);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id player = incubator.getIncubatorActiveUser(self);
        dictionary params = new dictionary();
        params.put("station", self);
        messageTo(player, "removeIncubatorFromUser", params, 0, true);
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id dest, obj_id transferer) throws InterruptedException
    {
        if (isSpaceScene())
        {
            return SCRIPT_OVERRIDE;
        }
        if (!incubator.hasActiveUser(self) && player_structure.isOwner(getTopMostContainer(transferer), transferer))
        {
            return SCRIPT_CONTINUE;
        }
        if (player_structure.isOwner(getTopMostContainer(transferer), transferer))
        {
            int pid = sui.msgbox(self, transferer, "@" + SID_HOME_OWNER_CLEANSE_MSG_PROMPT, sui.YES_NO, "@" + SID_CLEANSE_MSG_TITLE, "handleHomeOwnerCleanseSui");
            return SCRIPT_OVERRIDE;
        }
        if (incubator.hasActiveUser(self))
        {
            sendSystemMessage(transferer, new string_id("beast", "cant_pickup"));
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTransferred(obj_id self, obj_id sourceContainer, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        obj_id station = self;
        if (utils.isInHouseCellSpace(station))
        {
            utils.setScriptVar(station, incubator.PARTICLE_LABEL_SCRIPT_VAR, incubator.PARTICLE_LABEL_DEFAULT);
            playClientEffectObj(station, incubator.PARTICLE_DEFAULT, station, incubator.PARTICLE_HARDPOINT_TWO, null, incubator.PARTICLE_LABEL_DEFAULT);
            createTriggerVolume("particle", 15f, true);
        }
        if (utils.isNestedWithinAPlayer(station))
        {
            removeTriggerVolume("particle");
            incubator.stopAllSessionParticles(station);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (exists(self) && hasObjVar(self, "crafting.stationMod"))
        {
            names[idx] = "stationmod";
            float attrib = utils.roundFloatByDecimal(getFloatObjVar(self, "crafting.stationMod"));
            attribs[idx] = "" + attrib;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (exists(self) && hasObjVar(self, "crafting.stationMod_1"))
        {
            names[idx] = "stationmod_1";
            float attrib = utils.roundFloatByDecimal(getFloatObjVar(self, "crafting.stationMod_1"));
            attribs[idx] = "" + attrib;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (exists(self) && incubator.hasActiveUser(self))
        {
            names[idx] = "active_user";
            String attrib = getPlayerName(incubator.getIncubatorActiveUser(self));
            attribs[idx] = "" + attrib;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (exists(self) && incubator.hasPower(self))
        {
            String resourceName = incubator.getStationPowerName(self);
            names[idx] = "resourceName";
            attribs[idx] = "" + resourceName;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (exists(self) && incubator.hasPower(self))
        {
            int resourceAmount = incubator.getStationPowerAmount(self);
            names[idx] = "resourceAmount";
            attribs[idx] = "" + resourceAmount;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (exists(self) && incubator.hasPower(self))
        {
            int resourceQuality = incubator.getStationPowerQuality(self);
            names[idx] = "resourceQuality";
            attribs[idx] = "" + resourceQuality;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (exists(self) && hasObjVar(self, incubator.STATION_DNA_CREATURE))
        {
            names[idx] = "bm_template";
            int hashType = getIntObjVar(self, incubator.STATION_STORED_CREATURE_TEMPLATE);
            String template = getStringObjVar(self, incubator.STATION_DNA_CREATURE);
            String attrib = "";
            string_id templateId = new string_id("mob/creature_names", template);
            if (localize(templateId) == null)
            {
                template = incubator.getCreatureTypeFromHashTemplate(self);
                templateId = new string_id("monster_name", template);
                if (localize(templateId) == null)
                {
                    templateId = new string_id("mob/creature_names", template);
                }
            }
            attrib = localize(templateId);
            attribs[idx] = "" + attrib;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (exists(self) && hasObjVar(self, incubator.STATION_STORED_CREATURE_TEMPLATE))
        {
            names[idx] = "beast_type";
            int hashType = getIntObjVar(self, incubator.STATION_STORED_CREATURE_TEMPLATE);
            String template = incubator.getCreatureTypeFromHashTemplate(self);
            template = beast_lib.stripBmFromType(template);
            String attrib = "";
            string_id templateId = new string_id("monster_name", template);
            if (localize(templateId) == null)
            {
                templateId = new string_id("mob/creature_names", template);
            }
            attrib = localize(templateId);
            attribs[idx] = "" + attrib;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        for (int i = 0; i < incubator.ARRAY_SKILLS.length; ++i)
        {
            if (hasObjVar(self, incubator.ARRAY_SKILLS[i]))
            {
                names[idx] = incubator.SKILL_DISPLAY_NAMES[i];
                int attrib = getIntObjVar(self, incubator.ARRAY_SKILLS[i]);
                attribs[idx] = "" + attrib;
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        for (int i = 0; i < incubator.ARRAY_ATTRIBUTES.length; ++i)
        {
            if (hasObjVar(self, incubator.ARRAY_ATTRIBUTES[i]))
            {
                String name = incubator.ATTRIBUTE_DISPLAY_NAMES[i];
                if (!name.equals("block_value_bonus"))
                {
                    names[idx] = incubator.ATTRIBUTE_DISPLAY_NAMES[i];
                    float bonusVal = getFloatObjVar(self, incubator.ARRAY_ATTRIBUTES[i]);
                    float attrib = bonusVal * incubator.ATTRIBUTE_DISPLAY_CONVERSION_RATES[i];
                    attrib = utils.roundFloatByDecimal(attrib);
                    attribs[idx] = "" + attrib + "%";
                    idx++;
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                }
                else 
                {
                    names[idx] = incubator.ATTRIBUTE_DISPLAY_NAMES[i];
                    float bonusVal = getFloatObjVar(self, incubator.ARRAY_ATTRIBUTES[i]);
                    float attrib = bonusVal * incubator.ATTRIBUTE_DISPLAY_CONVERSION_RATES[i];
                    attrib = utils.roundFloatByDecimal(attrib);
                    attribs[idx] = "" + attrib;
                    idx++;
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        if (beast_lib.isBeastMaster(player))
        {
            names[idx] = "time_until_session";
            int playerTime = (getIntObjVar(player, incubator.NEXT_SESSION));
            int timeToUse = (playerTime - getGameTime());
            if (timeToUse <= 0 || playerTime <= 0)
            {
                attribs[idx] = "Ready";
            }
            else 
            {
                attribs[idx] = utils.assembleTimeRemainToUse(timeToUse);
            }
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        obj_id station = self;
        if (isPlayer(transferer))
        {
            if (!incubator.canOpenInventory(station))
            {
                sendSystemMessage(transferer, SID_NO_REMOVE_DNA);
                return SCRIPT_OVERRIDE;
            }
            String template = getTemplateName(item);
            if (!template.equals("object/tangible/loot/beast/dna_container.iff"))
            {
                sendSystemMessage(transferer, SID_ONLY_DNA_CAN_ADD);
                return SCRIPT_OVERRIDE;
            }
            int session = incubator.getCurrentSessionNumber(station);
            if (session > 1 && !hasObjVar(station, incubator.OBJVAR_EGG_CREATED))
            {
                sendSystemMessage(transferer, SID_STATION_HAS_DNA_ALREADY);
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToLoseItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        obj_id station = self;
        if (!incubator.canOpenInventory(station))
        {
            sendSystemMessage(transferer, SID_NO_REMOVE_DNA);
            return SCRIPT_OVERRIDE;
        }
        int session = incubator.getCurrentSessionNumber(station);
        if (session > 1)
        {
            String template = getTemplateName(item);
            if (template.equals("object/tangible/item/beast/bm_egg.iff") && hasObjVar(station, incubator.OBJVAR_EGG_CREATED))
            {
                return SCRIPT_CONTINUE;
            }
            sendSystemMessage(transferer, SID_NO_REMOVE_DNA);
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnLostItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        obj_id station = self;
        if (!exists(item))
        {
            return SCRIPT_CONTINUE;
        }
        String template = getTemplateName(item);
        if (template.equals("object/tangible/item/beast/bm_egg.iff") && hasObjVar(station, incubator.OBJVAR_EGG_CREATED))
        {
            setObjVar(station, incubator.ACTIVE_SESSION, 1);
            messageTo(station, "refreshCurrentParticle", null, 0, false);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleActivateSui(obj_id self, dictionary params) throws InterruptedException
    {
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!utils.outOfRange(self, player, 5.0f, false))
        {
            boolean cleanUpOldIncubator = false;
            obj_id oldIncubator = incubator.getActiveIncubator(player);
            if (incubator.hasActiveIncubator(player) && oldIncubator != self)
            {
                cleanUpOldIncubator = true;
            }
            if (incubator.setActiveUser(self, player))
            {
                if (cleanUpOldIncubator && isIdValid(oldIncubator))
                {
                    messageTo(oldIncubator, "cleanseOldIncubator", null, 0, true);
                }
                npe.commTutorialPlayer(self, player, 13.0f, incubator.SID_COMM_ACTIVATION, incubator.COMM_SFX_ACTIVATE, incubator.COMM_APPEARANCE_ACTIVATE);
                sendSystemMessage(player, new string_id("beast", "incubator_activated"));
            }
            else 
            {
                sendSystemMessage(player, new string_id("beast", "incubator_not_activated"));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleCleanseSui(obj_id self, dictionary params) throws InterruptedException
    {
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!utils.outOfRange(self, player, 5.0f, false))
        {
            if (incubator.removeActiveUser(self, player))
            {
                if (incubator.incubatorTotalCleanse(self))
                {
                    sendSystemMessage(player, new string_id("beast", "incubator_cleansed"));
                }
                else 
                {
                    sendSystemMessage(player, new string_id("beast", "incubator_cleanse_fail"));
                }
            }
            else 
            {
                sendSystemMessage(player, new string_id("beast", "incubator_cleanse_fail"));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleHomeOwnerCleanseSui(obj_id self, dictionary params) throws InterruptedException
    {
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        incubator.incubatorTotalCleanse(self);
        return SCRIPT_CONTINUE;
    }
    public int cleanseOldIncubator(obj_id self, dictionary params) throws InterruptedException
    {
        incubator.incubatorTotalCleanse(self);
        return SCRIPT_CONTINUE;
    }
    public int handlerIncubatorSessionUpdate(obj_id self, dictionary params) throws InterruptedException
    {
        if (params.isEmpty() || params == null)
        {
            return SCRIPT_CONTINUE;
        }
        float dpsBonus = 0.0f;
        float armorBonus = 0.0f;
        String newTemplate = "";
        float healthBonus = 0;
        float hitChanceBonus = 0;
        float dodgeBonus = 0;
        float glancingBlowBonus = 0;
        float parryBonus = 0;
        float blockChanceBonus = 0;
        float blockValueBonus = 0;
        float criticalHitBonus = 0;
        float evasionBonus = 0;
        float evasionRatingBonus = 0;
        float strikethroughBonus = 0;
        float strikethroughRatingBonus = 0;
        int survivalUpdate = 0;
        int beastialResilienceUpdate = 0;
        int cunningUpdate = 0;
        int intelligenceUpdate = 0;
        int aggressionUpdate = 0;
        int huntersInstinctUpdate = 0;
        obj_id player = params.getObjId("player");
        obj_id station = params.getObjId("station");
        obj_id slot1Id = params.getObjId("slot1Id");
        obj_id slot2Id = params.getObjId("slot2Id");
        obj_id slot3Id = params.getObjId("slot3Id");
        obj_id slot4Id = params.getObjId("slot4Id");
        int survivalSkillIncrement = params.getInt("initialPointsSurvival");
        int beastialSkillIncrement = params.getInt("initialPointsBeastialResilience");
        int cunningSkillIncrement = params.getInt("initialPointsCunning");
        int intelligenceSkillIncrement = params.getInt("initialPointsIntelligence");
        int aggressionSkillIncrement = params.getInt("initialPointsAggression");
        int huntersSkillIncrement = params.getInt("initialPointsHuntersInstinct");
        int survivalTotal = params.getInt("totalPointsSurvival");
        int beastialResilienceTotal = params.getInt("totalPointsBeastialResilience");
        int cunningTotal = params.getInt("totalPointsCunning");
        int intelligenceTotal = params.getInt("totalPointsIntelligence");
        int aggressionTotal = params.getInt("totalPointsAggression");
        int huntersInstinctTotal = params.getInt("totalPointsHuntersInstinct");
        int tempGaugeSliderPos = params.getInt("temperatureGauge");
        int nutGaugeSliderPos = params.getInt("nutrientGauge");
        int newCreatureColorIndex = params.getInt("newCreatureColorIndex");
        int survivalPointsSpent = survivalTotal - survivalSkillIncrement;
        int beastialResiliencePointsSpent = beastialResilienceTotal - beastialSkillIncrement;
        int cunningPointsSpent = cunningTotal - cunningSkillIncrement;
        int intelligencePointsSpent = intelligenceTotal - intelligenceSkillIncrement;
        int aggressionPointsSpent = aggressionTotal - aggressionSkillIncrement;
        int huntersInstinctPointsSpent = huntersInstinctTotal - huntersSkillIncrement;
        float slotOneQuality = 0.0f;
        int slotOneColor = 0;
        if (isIdValid(slot1Id) && exists(slot1Id))
        {
            if (incubator.isQualityEnzyme(slot1Id))
            {
                slotOneQuality = incubator.getEnzymeQuality(slot1Id);
                if (incubator.isConvertedPet(station))
                {
                    slotOneQuality += 5.0f;
                }
            }
            if (incubator.hasEnzymeColor(slot1Id))
            {
                slotOneColor = incubator.getEnzyemColor(slot1Id);
            }
        }
        int slotTwoRandomStatCount = 0;
        String slotTwoStat = "";
        int slotTwoColor = 0;
        if (isIdValid(slot2Id) && exists(slot2Id))
        {
            if (incubator.isStatEnzyme(slot2Id))
            {
                slotTwoRandomStatCount = incubator.getEnzymeRandomStats(slot2Id);
                slotTwoStat = incubator.getEnzymeFreeStat(slot2Id);
            }
            if (incubator.hasEnzymeColor(slot2Id))
            {
                slotTwoColor = incubator.getEnzyemColor(slot2Id);
            }
        }
        float slotThreeQuality = 0.0f;
        int slotThreeColor = 0;
        if (isIdValid(slot3Id) && exists(slot3Id))
        {
            if (incubator.isQualityEnzyme(slot3Id))
            {
                slotThreeQuality = incubator.getEnzymeQuality(slot3Id);
                if (incubator.isConvertedPet(station))
                {
                    slotThreeQuality += 5.0f;
                }
            }
            if (incubator.hasEnzymeColor(slot3Id))
            {
                slotThreeColor = incubator.getEnzyemColor(slot3Id);
            }
        }
        float slotFourMutagen = 0.0f;
        float slotFourPurity = 0.0f;
        String slotFourTrait = "";
        if (isIdValid(slot4Id) && exists(slot4Id))
        {
            if (incubator.isSkillEnzyme(slot4Id))
            {
                slotFourPurity = incubator.getEnzymeSkillPoints(slot4Id);
                slotFourMutagen = incubator.getEnzyemMutagen(slot4Id);
                slotFourTrait = incubator.getEnzyemTrait(slot4Id);
            }
        }
        String template = "";
        int hashTemplate = 0;
        int sessionNumber = incubator.getCurrentSessionNumber(station);
        if (station != self)
        {
            return SCRIPT_CONTINUE;
        }
        if (!incubator.hasPowerForSession(station))
        {
            sendSystemMessage(player, incubator.RESOURCE_POWER_NOT_ENOUGH);
            return SCRIPT_CONTINUE;
        }
        if (isIdValid(slot1Id) && exists(slot1Id))
        {
            CustomerServiceLog("BeastIncubator: ", "Player " + getFirstName(player) + "(" + player + ") has used Enzyme (" + slot1Id + "), with a quality of " + slotOneQuality + ", in incubation session " + sessionNumber);
            destroyObject(slot1Id);
        }
        if (isIdValid(slot2Id) && exists(slot2Id))
        {
            CustomerServiceLog("BeastIncubator: ", "Player " + getFirstName(player) + "(" + player + ") has used Enzyme (" + slot2Id + "), with " + slotTwoRandomStatCount + " random stats and " + slotTwoStat + " free stat, in incubation session " + sessionNumber);
            destroyObject(slot2Id);
        }
        if (isIdValid(slot3Id) && exists(slot3Id))
        {
            CustomerServiceLog("BeastIncubator: ", "Player " + getFirstName(player) + "(" + player + ") has used Enzyme (" + slot3Id + "), with a quality of " + slotThreeQuality + ", in incubation session " + sessionNumber);
            destroyObject(slot3Id);
        }
        if (isIdValid(slot4Id) && exists(slot4Id))
        {
            CustomerServiceLog("BeastIncubator: ", "Player " + getFirstName(player) + "(" + player + ") has used Enzyme (" + slot4Id + "), with a purity of " + slotFourPurity + " and a mutagen of " + slotFourMutagen + ", in incubation session " + sessionNumber);
            destroyObject(slot4Id);
        }
        if (sessionNumber > 1)
        {
            hashTemplate = incubator.getIncubatorCreatureTemplate(station);
            template = incubator.convertHashTemplateToString(hashTemplate, station);
        }
        else 
        {
            hashTemplate = getIntObjVar(station, incubator.STATION_DNA_CREATURE_TEMPLATE);
            template = incubator.convertHashTemplateToString(hashTemplate, station);
        }
        int expertiseBonuse = getEnhancedSkillStatisticModifierUncapped(player, "expertise_bm_incubation_quality");
        if (isGod(player))
        {
            sendSystemMessageTestingOnly(player, "Your expertiseBonuse is " + expertiseBonuse);
        }
        float stationBonus = incubator.getIncubatorQuality(station);
        if (isGod(player))
        {
            sendSystemMessageTestingOnly(player, "Your stationBonus is " + stationBonus);
        }
        int stationPowerQuality = incubator.getStationPowerQuality(station);
        if (isGod(player))
        {
            sendSystemMessageTestingOnly(player, "Your stationPowerQuality is " + stationPowerQuality);
        }
        float powerPercentToMax = (float)stationPowerQuality / (float)incubator.MAX_POWER_QUALITY;
        if (isGod(player))
        {
            sendSystemMessageTestingOnly(player, "Your powerPercentToMax is " + powerPercentToMax);
        }
        float powerBonus = powerPercentToMax * incubator.MAX_BONUS_FOR_POWER_QUALITY;
        if (isGod(player))
        {
            sendSystemMessageTestingOnly(player, "Your powerBonus is " + powerBonus);
        }
        int unmodifiedExoticDpsArmorBonus = getEnhancedSkillStatisticModifierUncapped(player, "bm_incubator_dps_armor");
        if (isGod(player))
        {
            sendSystemMessageTestingOnly(player, "Your unmodifiedExoticDpsArmorBonus is " + unmodifiedExoticDpsArmorBonus);
        }
        if (unmodifiedExoticDpsArmorBonus > incubator.MAX_RE_EXOTIC_DPS_ARMOR_SKILLMOD)
        {
            unmodifiedExoticDpsArmorBonus = incubator.MAX_RE_EXOTIC_DPS_ARMOR_SKILLMOD;
        }
        float exoticDpsArmorBonus = unmodifiedExoticDpsArmorBonus * 2.0f;
        if (isGod(player))
        {
            sendSystemMessageTestingOnly(player, "Your modified exoticDpsArmorBonus is " + exoticDpsArmorBonus);
        }
        float expertiseAndStationQualityBonus = (float)expertiseBonuse + stationBonus + powerBonus + exoticDpsArmorBonus;
        incubator.blog("INCUBATOR", "session(" + sessionNumber + ") expertiseAndStationQualityBonus = " + expertiseAndStationQualityBonus);
        float expertiseQualityBonusPercent = 1.0f;
        if (expertiseAndStationQualityBonus > 0)
        {
            expertiseQualityBonusPercent = 1 + (.01f * expertiseAndStationQualityBonus);
        }
        incubator.blog("INCUBATOR", "session(" + sessionNumber + ") expertiseQualityBonusPercent = " + expertiseQualityBonusPercent);
        if (isGod(player))
        {
            sendSystemMessageTestingOnly(player, "Your expertiseQualityBonusPercent is " + expertiseQualityBonusPercent);
        }
        float slotOneQualityWithBonuses = slotOneQuality * expertiseQualityBonusPercent;
        incubator.blog("INCUBATOR", "session(" + sessionNumber + ") slotOneQualityWithBonuses = " + slotOneQualityWithBonuses);
        if (isGod(player))
        {
            sendSystemMessageTestingOnly(player, "Your slotOneQuality is " + slotOneQuality);
        }
        if (isGod(player))
        {
            sendSystemMessageTestingOnly(player, "Your slotOneQualityWithBonuses is " + slotOneQualityWithBonuses);
        }
        float pointsToArmorOrDps = (slotOneQualityWithBonuses * .01f) * incubator.MAX_POINTS_PER_SESSION_DPS_ARMOR;
        incubator.blog("INCUBATOR", "session(" + sessionNumber + ") pointsToArmorOrDps = " + pointsToArmorOrDps);
        if (isGod(player))
        {
            sendSystemMessageTestingOnly(player, "Your pointsToArmorOrDps is " + pointsToArmorOrDps);
        }
        float percentTowardsDps = tempGaugeSliderPos * incubator.TEMP_SCALE_CONVERSION_TO_PERCENT;
        float pointsTowardDps = pointsToArmorOrDps * percentTowardsDps;
        incubator.blog("INCUBATOR", "session(" + sessionNumber + ") pointsTowardDps = " + pointsTowardDps);
        if (pointsTowardDps > incubator.MAX_ADJUSTED_POINTS_PER_SESSION_DPS_ARMOR)
        {
            pointsTowardDps = incubator.MAX_ADJUSTED_POINTS_PER_SESSION_DPS_ARMOR;
            incubator.blog("INCUBATOR", "session(" + sessionNumber + ") pointsTowardDps was over max, reseting to max of " + incubator.MAX_ADJUSTED_POINTS_PER_SESSION_DPS_ARMOR);
        }
        if (isGod(player))
        {
            sendSystemMessageTestingOnly(player, "Your pointsTowardDps is " + pointsTowardDps);
        }
        float percentTowardsArmor = (incubator.TEMP_SCALE_MAX_RANGE - tempGaugeSliderPos) * incubator.TEMP_SCALE_CONVERSION_TO_PERCENT;
        float pointsTowardArmor = pointsToArmorOrDps * percentTowardsArmor;
        incubator.blog("INCUBATOR", "session(" + sessionNumber + ") pointsTowardArmor = " + pointsTowardArmor);
        if (pointsTowardArmor > incubator.MAX_ADJUSTED_POINTS_PER_SESSION_DPS_ARMOR)
        {
            pointsTowardArmor = incubator.MAX_ADJUSTED_POINTS_PER_SESSION_DPS_ARMOR;
            incubator.blog("INCUBATOR", "session(" + sessionNumber + ") pointsTowardArmor was over max, reseting to max of " + incubator.MAX_ADJUSTED_POINTS_PER_SESSION_DPS_ARMOR);
        }
        if (isGod(player))
        {
            sendSystemMessageTestingOnly(player, "Your pointsTowardArmor is " + pointsTowardArmor);
        }
        dpsBonus += pointsTowardDps;
        incubator.blog("INCUBATOR", "session(" + sessionNumber + ") dpsBonus = " + dpsBonus);
        armorBonus += pointsTowardArmor;
        incubator.blog("INCUBATOR", "session(" + sessionNumber + ") armorBonus = " + armorBonus);
        String[] attributes = incubator.STAT_LIST;
        float[] attributesUpdateAmount = 
        {
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f
        };
        expertiseQualityBonusPercent = 1.0f;
        if (expertiseAndStationQualityBonus > 0)
        {
            expertiseQualityBonusPercent = 1 + (.01f * expertiseAndStationQualityBonus);
        }
        incubator.blog("INCUBATOR", "session(" + sessionNumber + ") expertiseQualityBonusPercent = " + expertiseQualityBonusPercent);
        float slotThreeQualityWithBonuses = slotThreeQuality * expertiseQualityBonusPercent;
        incubator.blog("INCUBATOR", "session(" + sessionNumber + ") slotThreeQualityWithBonuses = " + slotThreeQualityWithBonuses);
        float pointsTowardAttrib = (slotThreeQualityWithBonuses * .01f) * incubator.MAX_POINTS_PER_SESSION_ATTRIBUTES;
        incubator.blog("INCUBATOR", "session(" + sessionNumber + ") pointsTowardAttrib = " + pointsTowardAttrib);
        if (pointsTowardAttrib > incubator.MAX_ADJUSTED_POINTS_PER_SESSION_ATTRIBUTES)
        {
            pointsTowardAttrib = incubator.MAX_ADJUSTED_POINTS_PER_SESSION_ATTRIBUTES;
            incubator.blog("INCUBATOR", "session(" + sessionNumber + ") pointsTowardAttrib was over max, reseting to max of " + incubator.MAX_ADJUSTED_POINTS_PER_SESSION_ATTRIBUTES);
        }
        if (slotTwoStat != null && !slotTwoStat.equals(""))
        {
            for (int i = 0; i < attributes.length; ++i)
            {
                if (attributes[i].equals(slotTwoStat))
                {
                    attributesUpdateAmount[i] += pointsTowardAttrib;
                    incubator.blog("INCUBATOR", "session(" + sessionNumber + ") " + attributes[i] + " increasing by " + attributesUpdateAmount[i]);
                }
            }
        }
        if (slotTwoRandomStatCount > 0)
        {
            int i = 1;
            String[] attribUpdated = new String[slotTwoRandomStatCount];
            incubator.blog("INCUBATOR", "session(" + sessionNumber + ") attribUpdated.length = " + attribUpdated.length);
            while (i <= slotTwoRandomStatCount)
            {
                int attrib = rand(0, attributes.length - 1);
                incubator.blog("INCUBATOR", "session(" + sessionNumber + ") attrib = " + attrib);
                String randomAttrib = attributes[attrib];
                incubator.blog("INCUBATOR", "session(" + sessionNumber + ") randomAttrib = " + randomAttrib);
                if (!randomAttrib.equals(slotTwoStat))
                {
                    for (int j = 0; j < attribUpdated.length; ++j)
                    {
                        incubator.blog("INCUBATOR", "session(" + sessionNumber + ") attribUpdated.length = " + attribUpdated.length);
                        incubator.blog("INCUBATOR", "session(" + sessionNumber + ") j = " + j);
                        incubator.blog("INCUBATOR", "session(" + sessionNumber + ") attribUpdated[j] = " + attribUpdated[j]);
                        if (attribUpdated[j] == null)
                        {
                            incubator.blog("INCUBATOR", "session(" + sessionNumber + ") attribUpdated[j] was null");
                            attribUpdated[j] = randomAttrib;
                            incubator.blog("INCUBATOR", "session(" + sessionNumber + ") Attribute Added = " + attribUpdated[j]);
                            ++i;
                            break;
                        }
                        if (randomAttrib.equals(attribUpdated[j]))
                        {
                            break;
                        }
                    }
                }
            }
            for (int k = 0; k < attribUpdated.length; ++k)
            {
                for (int m = 0; m < attributes.length; ++m)
                {
                    if (attribUpdated[k].equals(attributes[m]))
                    {
                        attributesUpdateAmount[m] += pointsTowardAttrib;
                        incubator.blog("INCUBATOR", "session(" + sessionNumber + ") " + attributes[m] + " increasing by " + attributesUpdateAmount[m]);
                    }
                }
            }
        }
        survivalUpdate += survivalPointsSpent;
        if (survivalUpdate > incubator.MAX_SESSION_SKILL_INCREMENT)
        {
            survivalUpdate = incubator.MAX_SESSION_SKILL_INCREMENT;
            incubator.blog("INCUBATOR", "session(" + sessionNumber + ") survivalUpdate was larger than max. Setting to max of " + survivalUpdate);
        }
        beastialResilienceUpdate += beastialResiliencePointsSpent;
        if (beastialResilienceUpdate > incubator.MAX_SESSION_SKILL_INCREMENT)
        {
            beastialResilienceUpdate = incubator.MAX_SESSION_SKILL_INCREMENT;
            incubator.blog("INCUBATOR", "session(" + sessionNumber + ") beastialSkillIncrement was larger than max. Setting to max of " + beastialResilienceUpdate);
        }
        cunningUpdate += cunningPointsSpent;
        if (cunningUpdate > incubator.MAX_SESSION_SKILL_INCREMENT)
        {
            cunningUpdate = incubator.MAX_SESSION_SKILL_INCREMENT;
            incubator.blog("INCUBATOR", "session(" + sessionNumber + ") cunningSkillIncrement was larger than max. Setting to max of " + cunningUpdate);
        }
        intelligenceUpdate += intelligencePointsSpent;
        if (intelligenceUpdate > incubator.MAX_SESSION_SKILL_INCREMENT)
        {
            intelligenceUpdate = incubator.MAX_SESSION_SKILL_INCREMENT;
            incubator.blog("INCUBATOR", "session(" + sessionNumber + ") intelligenceSkillIncrement was larger than max. Setting to max of " + intelligenceUpdate);
        }
        aggressionUpdate += aggressionPointsSpent;
        if (aggressionUpdate > incubator.MAX_SESSION_SKILL_INCREMENT)
        {
            aggressionUpdate = incubator.MAX_SESSION_SKILL_INCREMENT;
            incubator.blog("INCUBATOR", "session(" + sessionNumber + ") aggressionSkillIncrement was larger than max. Setting to max of " + aggressionUpdate);
        }
        huntersInstinctUpdate += huntersInstinctPointsSpent;
        if (huntersInstinctUpdate > incubator.MAX_SESSION_SKILL_INCREMENT)
        {
            huntersInstinctUpdate = incubator.MAX_SESSION_SKILL_INCREMENT;
            incubator.blog("INCUBATOR", "session(" + sessionNumber + ") huntersSkillIncrement was larger than max. Setting to max of " + huntersInstinctUpdate);
        }
        if (sessionNumber > 1)
        {
            int previousSession = sessionNumber - 1;
            float dpsLastBonus = incubator.getFloatBonusLastSession(station, previousSession, "dps");
            float armorLastBonus = incubator.getFloatBonusLastSession(station, previousSession, "armor");
            String lastTemplate = incubator.getTemplateLastSession(station, previousSession);
            float healthLastBonus = incubator.getFloatBonusLastSession(station, previousSession, "health");
            float hitChanceLastBonus = incubator.getFloatBonusLastSession(station, previousSession, "hit_chance");
            float dodgeLastBonus = incubator.getFloatBonusLastSession(station, previousSession, "dodge");
            float glancingBlowLastBonus = incubator.getFloatBonusLastSession(station, previousSession, "glancing_blow");
            float parryBonusLastBonus = incubator.getFloatBonusLastSession(station, previousSession, "parry_chance");
            float blockChanceLastBonus = incubator.getFloatBonusLastSession(station, previousSession, "block_chance");
            float blockValueLastBonus = incubator.getFloatBonusLastSession(station, previousSession, "block_value");
            float criticalHitLastBonus = incubator.getFloatBonusLastSession(station, previousSession, "critical_hit");
            float evasionLastBonus = incubator.getFloatBonusLastSession(station, previousSession, "evasion");
            float evasionRatingLastBonus = incubator.getFloatBonusLastSession(station, previousSession, "evasion_rating");
            float strikethroughLastBonus = incubator.getFloatBonusLastSession(station, previousSession, "strikethrough");
            float strikethroughRatingLastBonus = incubator.getFloatBonusLastSession(station, previousSession, "strikethrough_rating");
            int survivalLastTotal = incubator.getIntBonusLastSession(station, previousSession, "survival");
            int beastialResilienceLastTotal = incubator.getIntBonusLastSession(station, previousSession, "beastialResilience");
            int cunningLastTotal = incubator.getIntBonusLastSession(station, previousSession, "cunning");
            int intelligenceLastTotal = incubator.getIntBonusLastSession(station, previousSession, "intelligence");
            int aggressionLastTotal = incubator.getIntBonusLastSession(station, previousSession, "aggression");
            int huntersInstinctLastTotal = incubator.getIntBonusLastSession(station, previousSession, "huntersInstinct");
            dpsBonus += dpsLastBonus;
            incubator.blog("INCUBATOR", "session(" + sessionNumber + ") dpsBonus = " + dpsBonus);
            armorBonus += armorLastBonus;
            incubator.blog("INCUBATOR", "session(" + sessionNumber + ") armorBonus = " + armorBonus);
            hitChanceBonus += hitChanceLastBonus;
            incubator.blog("INCUBATOR", "session(" + sessionNumber + ") hitChanceBonus = " + hitChanceBonus);
            dodgeBonus += dodgeLastBonus;
            incubator.blog("INCUBATOR", "session(" + sessionNumber + ") dodgeBonus = " + dodgeBonus);
            glancingBlowBonus += glancingBlowLastBonus;
            incubator.blog("INCUBATOR", "session(" + sessionNumber + ") glancingBlowBonus = " + glancingBlowBonus);
            parryBonus += parryBonusLastBonus;
            incubator.blog("INCUBATOR", "session(" + sessionNumber + ") parryBonus = " + parryBonus);
            blockChanceBonus += blockChanceLastBonus;
            incubator.blog("INCUBATOR", "session(" + sessionNumber + ") blockChanceBonus = " + blockChanceBonus);
            blockValueBonus += blockValueLastBonus;
            incubator.blog("INCUBATOR", "session(" + sessionNumber + ") blockValueBonus = " + blockValueBonus);
            criticalHitBonus += criticalHitLastBonus;
            incubator.blog("INCUBATOR", "session(" + sessionNumber + ") criticalHitBonus = " + criticalHitBonus);
            evasionBonus += evasionLastBonus;
            incubator.blog("INCUBATOR", "session(" + sessionNumber + ") evasionBonus = " + evasionBonus);
            evasionRatingBonus += evasionRatingLastBonus;
            incubator.blog("INCUBATOR", "session(" + sessionNumber + ") evasionRatingBonus = " + evasionRatingBonus);
            strikethroughBonus += strikethroughLastBonus;
            incubator.blog("INCUBATOR", "session(" + sessionNumber + ") strikethroughBonus = " + strikethroughBonus);
            strikethroughRatingBonus += strikethroughRatingLastBonus;
            incubator.blog("INCUBATOR", "session(" + sessionNumber + ") strikethroughRatingBonus = " + strikethroughRatingBonus);
            healthBonus += healthLastBonus;
            incubator.blog("INCUBATOR", "session(" + sessionNumber + ") healthBonus = " + healthBonus);
            survivalUpdate += survivalLastTotal;
            incubator.blog("INCUBATOR", "session(" + sessionNumber + ") survivalUpdate = " + survivalUpdate);
            beastialResilienceUpdate += beastialResilienceLastTotal;
            incubator.blog("INCUBATOR", "session(" + sessionNumber + ") beastialResilienceUpdate = " + beastialResilienceUpdate);
            cunningUpdate += cunningLastTotal;
            incubator.blog("INCUBATOR", "session(" + sessionNumber + ") cunningUpdate = " + cunningUpdate);
            intelligenceUpdate += intelligenceLastTotal;
            incubator.blog("INCUBATOR", "session(" + sessionNumber + ") intelligenceUpdate = " + intelligenceUpdate);
            aggressionUpdate += aggressionLastTotal;
            incubator.blog("INCUBATOR", "session(" + sessionNumber + ") aggressionUpdate = " + aggressionUpdate);
            huntersInstinctUpdate += huntersInstinctLastTotal;
            incubator.blog("INCUBATOR", "session(" + sessionNumber + ") huntersInstinctUpdate = " + huntersInstinctUpdate);
        }
        boolean canMutate = incubator.getMutationChance(template, slotOneColor, slotTwoColor, slotThreeColor, station, player);
        if (hasObjVar(station, "qa.forceMutate") && isGod(player))
        {
            canMutate = true;
        }
        if (canMutate)
        {
            incubator.blog("INCUBATOR", "session(" + sessionNumber + ") canMutate = " + canMutate);
            CustomerServiceLog("BeastIncubator: ", "player " + getFirstName(player) + "(" + player + ") can mutate, they have the right color combinations.");
            float incubatorQuality = incubator.getIncubatorQuality(station);
            CustomerServiceLog("BeastIncubator: ", "player " + getFirstName(player) + "(" + player + ") incubator quality is " + incubatorQuality);
            float dnaQuality = incubator.getIncubatorDnaQuality(station);
            CustomerServiceLog("BeastIncubator: ", "player " + getFirstName(player) + "(" + player + ") dna quality is " + dnaQuality);
            float totalEnzymeQuality = slotOneQuality + slotThreeQuality;
            CustomerServiceLog("BeastIncubator: ", "player " + getFirstName(player) + "(" + player + ") total Enzyme quality is " + totalEnzymeQuality);
            float qualityMean = totalEnzymeQuality / 2;
            incubator.blog("INCUBATOR", "session(" + sessionNumber + ") qualityMean = " + qualityMean);
            CustomerServiceLog("BeastIncubator: ", "player " + getFirstName(player) + "(" + player + ") Enzyme quality mean is " + qualityMean);
            float enzymePercent = qualityMean / incubator.MAX_QUALITY_RANGE;
            CustomerServiceLog("BeastIncubator: ", "player " + getFirstName(player) + "(" + player + ") Enzyme percent of max was " + enzymePercent);
            CustomerServiceLog("BeastIncubator: ", "this is calculated by qualityMean(" + qualityMean + ") / incubator.MAX_QUALITY_RANGE(" + incubator.MAX_QUALITY_RANGE + ").");
            float enzymeBonus = enzymePercent * incubator.MUTATION_BONUS_ENZYME;
            CustomerServiceLog("BeastIncubator: ", "player " + getFirstName(player) + "(" + player + ") Enzyme bonus is " + enzymeBonus);
            CustomerServiceLog("BeastIncubator: ", "This is calculated by enzymePercent(" + enzymePercent + ") * incubator.MUTATION_BONUS_ENZYME(" + incubator.MUTATION_BONUS_ENZYME + ").");
            if (isGod(player))
            {
                sendSystemMessageTestingOnly(player, "enzymeBonus is " + enzymeBonus);
            }
            float dnaPercent = dnaQuality / incubator.MAX_QUALITY_RANGE;
            CustomerServiceLog("BeastIncubator: ", "player " + getFirstName(player) + "(" + player + ") dna percent of max was " + dnaPercent);
            CustomerServiceLog("BeastIncubator: ", "This is calculated by dnaQuality(" + dnaQuality + ") / incubator.MAX_QUALITY_RANGE(" + incubator.MAX_QUALITY_RANGE + ").");
            float dnaBonus = dnaPercent * incubator.MUTATION_BONUS_DNA;
            CustomerServiceLog("BeastIncubator: ", "player " + getFirstName(player) + "(" + player + ") dna bonus " + dnaBonus);
            CustomerServiceLog("BeastIncubator: ", "This is calculated by dnaPercent(" + dnaPercent + ") * incubator.MUTATION_BONUS_DNA(" + incubator.MUTATION_BONUS_DNA + ").");
            if (isGod(player))
            {
                sendSystemMessageTestingOnly(player, "dnaBonus is " + dnaBonus);
            }
            float percentToMutagenCap = slotFourMutagen / incubator.MAX_MUTAGEN_POINTS;
            CustomerServiceLog("BeastIncubator: ", "player " + getFirstName(player) + "(" + player + ") percent to mutagen cap " + percentToMutagenCap);
            CustomerServiceLog("BeastIncubator: ", "This is calculated by slotFourMutagen(" + slotFourMutagen + ") / incubator.MAX_MUTAGEN_POINTS(" + incubator.MAX_MUTAGEN_POINTS + ").");
            float mutagenBonus = percentToMutagenCap * incubator.MUTATION_BONUS_MUTAGEN;
            CustomerServiceLog("BeastIncubator: ", "player " + getFirstName(player) + "(" + player + ") mutagen bonus " + mutagenBonus);
            CustomerServiceLog("BeastIncubator: ", "This is calculated by percentToMutagenCap(" + percentToMutagenCap + ") * incubator.MUTATION_BONUS_MUTAGEN(" + incubator.MUTATION_BONUS_MUTAGEN + ").");
            if (isGod(player))
            {
                sendSystemMessageTestingOnly(player, "mutagenBonus is " + mutagenBonus);
            }
            float incubatorPercent = incubatorQuality / incubator.STATION_QUALITY_MAX;
            CustomerServiceLog("BeastIncubator: ", "player " + getFirstName(player) + "(" + player + ") incubator percent of max was " + incubatorPercent);
            CustomerServiceLog("BeastIncubator: ", "This is calculated by incubatorQuality(" + incubatorQuality + ") / incubator.STATION_QUALITY_MAX(" + incubator.STATION_QUALITY_MAX + ").");
            float incubatorBonus = incubatorPercent * incubator.MUTATION_BONUS_INCUBATOR;
            CustomerServiceLog("BeastIncubator: ", "player " + getFirstName(player) + "(" + player + ") incubator bonus " + incubatorBonus);
            CustomerServiceLog("BeastIncubator: ", "This is calculated by incubatorPercent(" + incubatorPercent + ") * incubator.MUTATION_BONUS_INCUBATOR(" + incubator.MUTATION_BONUS_INCUBATOR + ").");
            if (isGod(player))
            {
                sendSystemMessageTestingOnly(player, "incubatorBonus is " + incubatorBonus);
            }
            int unmodifiedExoticMutationBonus = getEnhancedSkillStatisticModifierUncapped(player, "bm_mutation_chance_increase");
            if (isGod(player))
            {
                sendSystemMessageTestingOnly(player, "Your unmodifiedExoticMutationBonus is " + unmodifiedExoticMutationBonus);
            }
            if (unmodifiedExoticMutationBonus > incubator.MAX_RE_EXOTIC_MUTATION_SKILLMOD)
            {
                unmodifiedExoticMutationBonus = incubator.MAX_RE_EXOTIC_MUTATION_SKILLMOD;
            }
            float exoticMutationBonus = unmodifiedExoticDpsArmorBonus * 0.075f;
            if (isGod(player))
            {
                sendSystemMessageTestingOnly(player, "Your modified exoticMutationBonus is " + exoticMutationBonus);
            }
            int mutationChance = Math.round(incubator.BASE_MUTATION_CHANCE + enzymeBonus + dnaBonus + mutagenBonus + incubatorBonus + exoticMutationBonus);
            CustomerServiceLog("BeastIncubator: ", "player " + getFirstName(player) + "(" + player + ") mutation chance was " + mutationChance);
            CustomerServiceLog("BeastIncubator: ", "This is calculated by Math.round(enzymeBonus(" + enzymeBonus + ") + dnaBonus(" + dnaBonus + ") + mutagenBonus(" + mutagenBonus + ") + incubatorBonus(" + incubatorBonus + ") + exoticMutationBonus(" + exoticMutationBonus + ")).");
            if (mutationChance > incubator.MUTATION_MAX_INCREASE)
            {
                CustomerServiceLog("BeastIncubator: ", "player " + getFirstName(player) + "(" + player + ") mutation chance was larger than cap, so we are setting it to cap of " + incubator.MUTATION_MAX_INCREASE);
                mutationChance = incubator.MUTATION_MAX_INCREASE;
            }
            incubator.blog("INCUBATOR", "session(" + sessionNumber + ") MutationChance is: " + mutationChance + " = Math.round(" + enzymeBonus + " + " + dnaBonus + " + " + mutagenBonus + " + " + incubatorBonus + " + " + exoticMutationBonus + ")");
            int chance = rand(1, 100);
            if (isGod(player))
            {
                sendSystemMessageTestingOnly(player, "To get a mutation you need to roll less than or equal to your mutation chance.");
                sendSystemMessageTestingOnly(player, "mutationChance is " + mutationChance);
                sendSystemMessageTestingOnly(player, "and you rolled an " + chance);
            }
            if (hasObjVar(station, "qa.forceMutate") && isGod(player))
            {
                CustomerServiceLog("BeastIncubator: ", "player " + getFirstName(player) + "(" + player + ") has an incubator with the objvar 'qa.forceMutate', so we are setting mutation chance to 100");
                mutationChance = 100;
            }
            if (chance <= mutationChance)
            {
                CustomerServiceLog("BeastIncubator: ", "player " + getFirstName(player) + "(" + player + ") is mutating they rolled a " + chance + " when they needed " + mutationChance + " or lower");
                newTemplate = incubator.getMutatedTemplate(template, sessionNumber, station, player);
                incubator.blog("INCUBATOR", "session(" + sessionNumber + ") Mutated::newTemplate = " + newTemplate);
                if (template.equals(newTemplate))
                {
                    incubator.giveMutationAttributeBonus(station, player, attributesUpdateAmount, attributes);
                }
                else 
                {
                    int[] skillArray = 
                    {
                        survivalUpdate,
                        beastialResilienceUpdate,
                        cunningUpdate,
                        intelligenceUpdate,
                        aggressionUpdate,
                        huntersInstinctUpdate
                    };
                    incubator.giveMutationSkillBonus(station, player, skillArray, newTemplate);
                    survivalUpdate = skillArray[0];
                    beastialResilienceUpdate = skillArray[1];
                    cunningUpdate = skillArray[2];
                    intelligenceUpdate = skillArray[3];
                    aggressionUpdate = skillArray[4];
                    huntersInstinctUpdate = skillArray[5];
                }
            }
            else 
            {
                newTemplate = template;
                incubator.blog("INCUBATOR", "session(" + sessionNumber + ") nonMutated::newTemplate = " + newTemplate);
            }
        }
        else 
        {
            newTemplate = template;
            incubator.blog("INCUBATOR", "session(" + sessionNumber + ") canMutate = " + canMutate);
            incubator.blog("INCUBATOR", "session(" + sessionNumber + ") NoChanceMutate::newTemplate = " + newTemplate);
        }
        hitChanceBonus += attributesUpdateAmount[0];
        incubator.blog("INCUBATOR", "session(" + sessionNumber + ") hitChanceBonus = " + hitChanceBonus);
        dodgeBonus += attributesUpdateAmount[1];
        incubator.blog("INCUBATOR", "session(" + sessionNumber + ") dodgeBonus = " + dodgeBonus);
        parryBonus += attributesUpdateAmount[2];
        incubator.blog("INCUBATOR", "session(" + sessionNumber + ") parryBonus = " + parryBonus);
        glancingBlowBonus += attributesUpdateAmount[3];
        incubator.blog("INCUBATOR", "session(" + sessionNumber + ") glancingBlowBonus = " + glancingBlowBonus);
        blockChanceBonus += attributesUpdateAmount[4];
        incubator.blog("INCUBATOR", "session(" + sessionNumber + ") blockChanceBonus = " + blockChanceBonus);
        blockValueBonus += attributesUpdateAmount[5];
        incubator.blog("INCUBATOR", "session(" + sessionNumber + ") blockValueBonus = " + blockValueBonus);
        criticalHitBonus += attributesUpdateAmount[6];
        incubator.blog("INCUBATOR", "session(" + sessionNumber + ") glancingBlowBonus = " + criticalHitBonus);
        evasionBonus += attributesUpdateAmount[7];
        incubator.blog("INCUBATOR", "session(" + sessionNumber + ") evasionBonus = " + evasionBonus);
        evasionRatingBonus += attributesUpdateAmount[8];
        incubator.blog("INCUBATOR", "session(" + sessionNumber + ") evasionRatingBonus = " + evasionRatingBonus);
        strikethroughBonus += attributesUpdateAmount[9];
        incubator.blog("INCUBATOR", "session(" + sessionNumber + ") strikethroughBonus = " + strikethroughBonus);
        strikethroughRatingBonus += attributesUpdateAmount[10];
        incubator.blog("INCUBATOR", "session(" + sessionNumber + ") strikethroughRatingBonus = " + strikethroughRatingBonus);
        healthBonus += attributesUpdateAmount[11];
        incubator.blog("INCUBATOR", "session(" + sessionNumber + ") healthBonus = " + healthBonus);
        int row = -1;
        int lastMutatedSession = incubator.getLastSessionMutated(station);
        if (lastMutatedSession >= 1)
        {
            row = dataTableSearchColumnForString(newTemplate, "mutated_template_" + lastMutatedSession, incubator.DATATABLE_INCUBATOR_TEMPLATES);
            hashTemplate = dataTableGetInt(incubator.DATATABLE_INCUBATOR_TEMPLATES, row, "hash_mutated_template_" + lastMutatedSession);
        }
        else 
        {
            row = dataTableSearchColumnForString(newTemplate, "initial_template", incubator.DATATABLE_INCUBATOR_TEMPLATES);
            hashTemplate = dataTableGetInt(incubator.DATATABLE_INCUBATOR_TEMPLATES, row, "hash_initial_template");
        }
        if (isGod(player))
        {
            sendSystemMessageTestingOnly(player, "Your total dps Bonus = " + dpsBonus);
            sendSystemMessageTestingOnly(player, "Your total armor Bonus = " + armorBonus);
            sendSystemMessageTestingOnly(player, "Your total dps+Armor Bonus = " + (dpsBonus + armorBonus));
        }
        dictionary dict = new dictionary();
        dict.put("dpsBonus", dpsBonus);
        dict.put("pointsTowardDps", pointsTowardDps);
        dict.put("armorBonus", armorBonus);
        dict.put("pointsTowardArmor", pointsTowardArmor);
        dict.put("newTemplate", hashTemplate);
        dict.put("hitChanceBonus", hitChanceBonus);
        dict.put("hitChanceUpdate", attributesUpdateAmount[0]);
        dict.put("dodgeBonus", dodgeBonus);
        dict.put("dodgeUpdate", attributesUpdateAmount[1]);
        dict.put("parryBonus", parryBonus);
        dict.put("parryChanceUpdate", attributesUpdateAmount[2]);
        dict.put("glancingBlowBonus", glancingBlowBonus);
        dict.put("glancingBlowUpdate", attributesUpdateAmount[3]);
        dict.put("blockChanceBonus", blockChanceBonus);
        dict.put("blockChanceUpdate", attributesUpdateAmount[4]);
        dict.put("blockValueBonus", blockValueBonus);
        dict.put("blockValueUpdate", attributesUpdateAmount[5]);
        dict.put("criticalHitBonus", criticalHitBonus);
        dict.put("criticalHitUpdate", attributesUpdateAmount[6]);
        dict.put("evasionBonus", evasionBonus);
        dict.put("evasionUpdate", attributesUpdateAmount[7]);
        dict.put("evasionRatingBonus", evasionRatingBonus);
        dict.put("evasionRatingUpdate", attributesUpdateAmount[8]);
        dict.put("strikethroughBonus", strikethroughBonus);
        dict.put("strikethroughUpdate", attributesUpdateAmount[9]);
        dict.put("strikethroughRatingBonus", strikethroughRatingBonus);
        dict.put("strikethroughRatingUpdate", attributesUpdateAmount[10]);
        dict.put("healthBonus", healthBonus);
        dict.put("healthUpdate", attributesUpdateAmount[11]);
        dict.put("survivalUpdate", survivalUpdate);
        dict.put("beastialResilienceUpdate", beastialResilienceUpdate);
        dict.put("cunningUpdate", cunningUpdate);
        dict.put("intelligenceUpdate", intelligenceUpdate);
        dict.put("aggressionUpdate", aggressionUpdate);
        dict.put("huntersInstinctUpdate", huntersInstinctUpdate);
        dict.put("sessionNumber", sessionNumber);
        dict.put("slotOneQuality", slotOneQuality);
        dict.put("slotThreeQuality", slotThreeQuality);
        dict.put("slotTwoRandomStatCount", slotTwoRandomStatCount);
        dict.put("slotFourMutagen", slotFourMutagen);
        dict.put("slotFourPurity", slotFourPurity);
        dict.put("slotFourTrait", slotFourTrait);
        dict.put("survivalSkillIncrement", survivalPointsSpent);
        dict.put("beastialSkillIncrement", beastialResiliencePointsSpent);
        dict.put("cunningSkillIncrement", cunningPointsSpent);
        dict.put("intelligenceSkillIncrement", intelligencePointsSpent);
        dict.put("aggressionSkillIncrement", aggressionPointsSpent);
        dict.put("huntersSkillIncrement", huntersInstinctPointsSpent);
        dict.put("tempGaugeSliderPos", tempGaugeSliderPos);
        dict.put("nutGaugeSliderPos", nutGaugeSliderPos);
        dict.put("slotOneColor", slotOneColor);
        dict.put("slotTwoColor", slotTwoColor);
        dict.put("slotThreeColor", slotThreeColor);
        dict.put("slotTwoStat", slotTwoStat);
        dict.put("creatureHueIndex", newCreatureColorIndex);
        boolean updated = incubator.updateSessionVariables(station, dict);
        if (!updated)
        {
            incubator.blog("INCUBATOR", "session(" + sessionNumber + ") Something went wrong, we didnt update right.");
        }
        incubator.setNextSessionTime(station, player);
        incubator.decrementPowerForSession(station);
        int currentTime = getGameTime();
        utils.setScriptVar(player, incubator.GUI_SCRIPT_VAR, currentTime);
        incubator.advanceSessionParticle(station);
        return SCRIPT_CONTINUE;
    }
    public int OnIncubatorCommitted(obj_id self, obj_id playerId, obj_id terminalId, obj_id slot1Id, obj_id slot2Id, obj_id slot3Id, obj_id slot4Id, int initialPointsSurvival, int initialPointsBeastialResilience, int initialPointsCunning, int initialPointsIntelligence, int initialPointsAggression, int initialPointsHuntersInstinct, int totalPointsSurvival, int totalPointsBeastialResilience, int totalPointsCunning, int totalPointsIntelligence, int totalPointsAggression, int totalPointsHuntersInstinct, int temperatureGauge, int nutrientGauge, int newCreatureColorIndex) throws InterruptedException
    {
        prose_package pp = new prose_package();
        String playerName = getPlayerName(playerId);
        pp = prose.setTT(pp, playerName);
        pp = prose.setStringId(pp, SID_CHEATER_TRANSFER);
        if (isIdValid(slot1Id) && !utils.isNestedWithin(slot1Id, playerId) && exists(slot1Id))
        {
            obj_id newOwner = getContainedBy(slot1Id);
            CustomerServiceLog("SuspectedCheaterChannel", "Player (" + playerId + ") has moved Enzyme (" + slot1Id + ") from their inventory to container (" + newOwner + "). While trying to use it simultaneously in an incubator. Possible cheater.");
            sendSystemMessageProse(playerId, pp);
            return SCRIPT_CONTINUE;
        }
        if (isIdValid(slot2Id) && !utils.isNestedWithin(slot2Id, playerId) && exists(slot2Id))
        {
            obj_id newOwner = getContainedBy(slot2Id);
            CustomerServiceLog("SuspectedCheaterChannel", "Player (" + playerId + ") has moved Enzyme (" + slot2Id + ") from their inventory to container (" + newOwner + "). While trying to use it simultaneously in an incubator. Possible cheater.");
            sendSystemMessageProse(playerId, pp);
            return SCRIPT_CONTINUE;
        }
        if (isIdValid(slot3Id) && !utils.isNestedWithin(slot3Id, playerId) && exists(slot3Id))
        {
            obj_id newOwner = getContainedBy(slot3Id);
            CustomerServiceLog("SuspectedCheaterChannel", "Player (" + playerId + ") has moved Enzyme (" + slot3Id + ") from their inventory to container (" + newOwner + "). While trying to use it simultaneously in an incubator. Possible cheater.");
            sendSystemMessageProse(playerId, pp);
            return SCRIPT_CONTINUE;
        }
        if (isIdValid(slot4Id) && !utils.isNestedWithin(slot4Id, playerId) && exists(slot4Id))
        {
            obj_id newOwner = getContainedBy(slot4Id);
            CustomerServiceLog("SuspectedCheaterChannel", "Player (" + playerId + ") has moved Enzyme (" + slot4Id + ") from their inventory to container (" + newOwner + "). While trying to use it simultaneously in an incubator. Possible cheater.");
            sendSystemMessageProse(playerId, pp);
            return SCRIPT_CONTINUE;
        }
        obj_id selfActiveUser = obj_id.NULL_ID;
        obj_id passedTerminalActiveUser = obj_id.NULL_ID;
        if (incubator.hasActiveUser(self))
        {
            selfActiveUser = incubator.getIncubatorActiveUser(self);
        }
        if (incubator.hasActiveUser(terminalId))
        {
            passedTerminalActiveUser = incubator.getIncubatorActiveUser(terminalId);
        }
        if (playerId != selfActiveUser || playerId != passedTerminalActiveUser)
        {
            sendSystemMessage(playerId, SID_CHEATER_BAD_ID);
            return SCRIPT_CONTINUE;
        }
        if (terminalId != self) {
            sendSystemMessage(playerId, SID_CHEATER_BAD_ID);
            return SCRIPT_CONTINUE;
        }
        dictionary dict = new dictionary();
        incubator.blog("INCUBATOR", "Player " + getFirstName(playerId) + " (" + playerId + ") creating new beast on terminal " + terminalId + " with the following PRE FIX stats: ");
        incubator.blog("INCUBATOR", "--- InitialPointsSurvival: " + initialPointsSurvival);
        incubator.blog("INCUBATOR", "--- initialPointsBeastialResilience: " + initialPointsBeastialResilience);
        incubator.blog("INCUBATOR", "--- initialPointsCunning: " + initialPointsCunning);
        incubator.blog("INCUBATOR", "--- initialPointsIntelligence: " + initialPointsIntelligence);
        incubator.blog("INCUBATOR", "--- initialPointsAggression: " + initialPointsAggression);
        incubator.blog("INCUBATOR", "--- initialPointsHuntersInstinct: " + initialPointsHuntersInstinct);
        incubator.blog("INCUBATOR", "--- totalPointsSurvival: " + totalPointsSurvival);
        incubator.blog("INCUBATOR", "--- totalPointsBeastialResilience: " + totalPointsBeastialResilience);
        incubator.blog("INCUBATOR", "--- totalPointsCunning: " + totalPointsCunning);
        incubator.blog("INCUBATOR", "--- totalPointsIntelligence: " + totalPointsIntelligence);
        incubator.blog("INCUBATOR", "--- totalPointsAggression: " + totalPointsAggression);
        incubator.blog("INCUBATOR", "--- totalPointsHuntersInstinct: " + totalPointsHuntersInstinct);
        if (initialPointsAggression > 10 || initialPointsAggression < 0 ||
                initialPointsBeastialResilience > 10 || initialPointsBeastialResilience < 0 ||
                initialPointsCunning > 10 || initialPointsCunning < 0 ||
                initialPointsHuntersInstinct > 10 || initialPointsHuntersInstinct < 0 ||
                initialPointsIntelligence > 10 || initialPointsIntelligence < 0 ||
                initialPointsSurvival > 10 || initialPointsSurvival < 0)
        {
            CustomerServiceLog("SuspectedCheaterChannel", "Player (" + playerId + ") has modified stats to incredible values - possibly buffer overruns. Possible cheater.");
            CustomerServiceLog("SuspectedCheaterChannel", "InitialPointsSurvival: " + initialPointsSurvival);
            CustomerServiceLog("SuspectedCheaterChannel", "initialPointsBeastialResilience: " + initialPointsBeastialResilience);
            CustomerServiceLog("SuspectedCheaterChannel", "initialPointsCunning: " + initialPointsCunning);
            CustomerServiceLog("SuspectedCheaterChannel", "initialPointsIntelligence: " + initialPointsIntelligence);
            CustomerServiceLog("SuspectedCheaterChannel", "initialPointsAggression: " + initialPointsAggression);
            CustomerServiceLog("SuspectedCheaterChannel", "initialPointsHuntersInstinct: " + initialPointsHuntersInstinct);
            CustomerServiceLog("SuspectedCheaterChannel", "totalPointsSurvival: " + totalPointsSurvival);
            CustomerServiceLog("SuspectedCheaterChannel", "totalPointsBeastialResilience: " + totalPointsBeastialResilience);
            CustomerServiceLog("SuspectedCheaterChannel", "totalPointsCunning: " + totalPointsCunning);
            CustomerServiceLog("SuspectedCheaterChannel", "totalPointsIntelligence: " + totalPointsIntelligence);
            CustomerServiceLog("SuspectedCheaterChannel", "totalPointsAggression: " + totalPointsAggression);
            CustomerServiceLog("SuspectedCheaterChannel", "totalPointsHuntersInstinct: " + totalPointsHuntersInstinct);
            sendSystemMessage(playerId, SID_CHEATER_INVALID_STATS);
            return SCRIPT_CONTINUE;
        }
        dict.put("player", playerId);
        dict.put("station", terminalId);
        dict.put("slot1Id", slot1Id);
        dict.put("slot2Id", slot2Id);
        dict.put("slot3Id", slot3Id);
        dict.put("slot4Id", slot4Id);
        dict.put("initialPointsSurvival", initialPointsSurvival);
        dict.put("initialPointsBeastialResilience", initialPointsBeastialResilience);
        dict.put("initialPointsCunning", initialPointsCunning);
        dict.put("initialPointsIntelligence", initialPointsIntelligence);
        dict.put("initialPointsAggression", initialPointsAggression);
        dict.put("initialPointsHuntersInstinct", initialPointsHuntersInstinct);
        dict.put("totalPointsSurvival", totalPointsSurvival);
        dict.put("totalPointsBeastialResilience", totalPointsBeastialResilience);
        dict.put("totalPointsCunning", totalPointsCunning);
        dict.put("totalPointsIntelligence", totalPointsIntelligence);
        dict.put("totalPointsAggression", totalPointsAggression);
        dict.put("totalPointsHuntersInstinct", totalPointsHuntersInstinct);
        dict.put("temperatureGauge", temperatureGauge);
        dict.put("nutrientGauge", nutrientGauge);
        dict.put("newCreatureColorIndex", newCreatureColorIndex);
        messageTo(terminalId, "handlerIncubatorSessionUpdate", dict, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int refreshCurrentParticle(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id station = self;
        String labelName = incubator.getCurrentSessionParticleLabelName(station);
        String sessionParticle = incubator.getCurrentSessionParticle(station);
        String hardPoint = incubator.getCurrentParticleHardpoint(sessionParticle);
        if ((!labelName.equals("") && labelName != null) && (!sessionParticle.equals("") && sessionParticle != null) && (!hardPoint.equals("") && hardPoint != null))
        {
            incubator.stopAllSessionParticles(station);
            utils.setScriptVar(station, incubator.PARTICLE_LABEL_SCRIPT_VAR, labelName);
            playClientEffectObj(station, sessionParticle, station, hardPoint, null, labelName);
        }
        return SCRIPT_CONTINUE;
    }
}
