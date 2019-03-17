package script.systems.crafting;

import script.*;
import script.library.*;

public class base_tool extends script.base_script
{
    public base_tool()
    {
    }
    public static final String PROTOTYPE_SLOT = "anythingNoMod2";
    public static final String OBJVAR_CRAFTING_FAKE_PROTOTYPE = "crafting.isFakePrototype";
    public static final string_id INVENTORY_FULL = new string_id("spam", "inv_full");
    public static final string_id NO_SCHEMATICS = new string_id("system_msg", "no_valid_schematics");
    public static final float[] COMPLEXITY_LIMIT = 
    {
        15,
        20,
        25
    };
    public static final String[] STATION_BUFFS = 
    {
        "food_station",
        "armor_station",
        "structure_station",
        "weapon_station",
        "space_station"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, craftinglib.OBJVAR_PROTOTYPE_TIME, 0.0f);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        float time = getFloatObjVar(self, craftinglib.OBJVAR_PROTOTYPE_TIME);
        if (time == 0.0f)
        {
            removeObjVar(self, craftinglib.OBJVAR_CRAFTER);
        }
        String template = getTemplateName(self);
        if (template != null && template.contains("weapon_tool.iff"))
        {
            int craftingType = getIntObjVar(self, craftinglib.OBJVAR_CRAFTING_TYPE);
            craftingType &= ~CT_genericItem;
            craftingType |= CT_misc;
            setObjVar(self, craftinglib.OBJVAR_CRAFTING_TYPE, craftingType);
        }
        return SCRIPT_CONTINUE;
    }
    public int determineCraftingLevel(obj_id tool, obj_id player) throws InterruptedException
    {
        int craftingLevel = 0;
        obj_id stationId = obj_id.NULL_ID;
        boolean hasBuff = false;
        debugServerConsoleMsg(tool, "OnObjectMenuRequest initial crafting level 0");
        int toolType = getIntObjVar(tool, craftinglib.OBJVAR_CRAFTING_TYPE);
        if (toolType == 0)
        {
            LOG("crafting", "No crafting type defined for object " + tool);
            return -1;
        }
        String stationBuff = craftinglib.getCraftingStationBuffName(toolType);
        if (isInWorldCell(player) && stationBuff != null && buff.hasBuff(player, stationBuff))
        {
            int[] stationBuffs = buff.getAllBuffsByEffect(player, "station_buff");
            if (stationBuffs != null || stationBuffs.length > 0)
            {
                for (int stationBuff1 : stationBuffs) {
                    buff.removeBuff(player, stationBuff1);
                }
            }
        }
        if (stationBuff != null && buff.hasBuff(player, stationBuff))
        {
            stationId = buff.getBuffCaster(player, stationBuff);
            if (isIdValid(stationId) && exists(stationId))
            {
                hasBuff = true;
            }
            if ((ai_lib.aiGetNiche(stationId) == NICHE_DROID || ai_lib.aiGetNiche(stationId) == NICHE_ANDROID) && pet_lib.isLowOnPower(stationId))
            {
                hasBuff = false;
                stationId = null;
            }
            else if (toolType != CT_genericItem)
            {
                ++craftingLevel;
                int stationType = getIntObjVar(stationId, craftinglib.OBJVAR_CRAFTING_TYPE);
                if ((stationType & toolType) != 0)
                {
                    ++craftingLevel;
                    int privateStation = getIntObjVar(stationId, craftinglib.OBJVAR_PRIVATE_STATION);
                    if (privateStation == 1 && (ai_lib.aiGetNiche(stationId) != NICHE_DROID || ai_lib.aiGetNiche(stationId) != NICHE_ANDROID))
                    {
                        ++craftingLevel;
                        debugServerConsoleMsg(tool, "OnObjectMenuRequest crafting level 3");
                    }
                    else if (privateStation == 2 && (ai_lib.aiGetNiche(stationId) == NICHE_DROID || ai_lib.aiGetNiche(stationId) == NICHE_ANDROID))
                    {
                        craftingLevel = craftingLevel + 2;
                        debugServerConsoleMsg(tool, "OnObjectMenuRequest crafting level 4");
                    }
                }
            }
        }
        if (!hasBuff)
        {
            if (toolType != CT_genericItem)
            {
                ++craftingLevel;
                debugServerConsoleMsg(tool, "OnObjectMenuRequest crafting level 1");
                location myPos = getLocation(player);
                debugServerConsoleMsg(tool, "Looking for a crafting station, I think I'm at location " + myPos);
                obj_id[] testIds = getObjectsInRange(myPos, craftinglib.STATION_AREA);
                if (testIds != null)
                {
                    float closestLength = craftinglib.STATION_AREA + 100.0f;
                    for (obj_id testId : testIds) {
                        if (isIdValid(testId) && hasObjVar(testId, craftinglib.OBJVAR_STATION)) {
                            debugServerConsoleMsg(tool, "Testing crafting station " + testId + " at " + getLocation(testId));
                            float dist = getDistance(testId, myPos);
                            debugServerConsoleMsg(tool, "\tstation distance = " + dist);
                            if (dist >= 0 && dist < closestLength) {
                                if ((ai_lib.aiGetNiche(stationId) == NICHE_DROID || ai_lib.aiGetNiche(stationId) == NICHE_ANDROID) && pet_lib.isLowOnPower(stationId)) {
                                    continue;
                                }
                                closestLength = dist;
                                stationId = testId;
                            }
                        }
                    }
                    if (isIdValid(stationId) && ((ai_lib.getNiche(stationId) != NICHE_DROID && ai_lib.getNiche(stationId) != NICHE_ANDROID) || pet_lib.isMyPet(stationId, player)))
                    {
                        debugServerConsoleMsg(tool, "Found crafting station " + stationId + " at " + getLocation(stationId));
                        int stationType = getIntObjVar(stationId, craftinglib.OBJVAR_CRAFTING_TYPE);
                        if ((stationType & toolType) != 0)
                        {
                            debugServerConsoleMsg(tool, "OnObjectMenuRequest crafting level 2");
                            int privateStation = getIntObjVar(stationId, craftinglib.OBJVAR_PRIVATE_STATION);
                            if (privateStation == 1 && (ai_lib.aiGetNiche(stationId) != NICHE_DROID || ai_lib.aiGetNiche(stationId) != NICHE_ANDROID))
                            {
                                craftingLevel += 2;
                                debugServerConsoleMsg(tool, "OnObjectMenuRequest crafting level 3");
                            }
                            else if (privateStation == 2 && (ai_lib.aiGetNiche(stationId) == NICHE_DROID || ai_lib.aiGetNiche(stationId) == NICHE_ANDROID))
                            {
                                if (!pet_lib.isLowOnPower(stationId))
                                {
                                    craftingLevel += 3;
                                    debugServerConsoleMsg(tool, "OnObjectMenuRequest crafting level 4");
                                }
                            }
                            else 
                            {
                                craftingLevel++;
                            }
                        }
                        else 
                        {
                            debugServerConsoleMsg(tool, "OnObjectMenuRequest station is wrong type " + stationType);
                        }
                    }
                    else 
                    {
                        debugServerConsoleMsg(tool, "OnObjectMenuRequest no station objvar");
                    }
                }
            }
        }
        String template = getTemplateName(stationId);
        if (template != null && template.contains("weapon_station.iff"))
        {
            int craftingType = getIntObjVar(stationId, craftinglib.OBJVAR_CRAFTING_TYPE);
            craftingType &= ~CT_genericItem;
            craftingType |= CT_misc | CT_lightsaber;
            setObjVar(stationId, craftinglib.OBJVAR_CRAFTING_TYPE, craftingType);
        }
        if (!setCraftingLevelAndStation(player, craftingLevel, stationId))
        {
            debugServerConsoleMsg(tool, "OnObjectMenuRequest failed to set crafting level on player");
            craftingLevel = -1;
        }
        return craftingLevel;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int craftingLevel = determineCraftingLevel(self, player);
        if (craftingLevel == -1)
        {
            return SCRIPT_OVERRIDE;
        }
        menu_info_data craft_menu = mi.getMenuItemByType(menu_info_types.CRAFT_START);
        if (craft_menu != null)
        {
            float time = getFloatObjVar(self, craftinglib.OBJVAR_PROTOTYPE_TIME);
            if (time == 0.0f && isIdValid(getObjectInSlot(self, PROTOTYPE_SLOT)))
            {
                mi.addSubMenu(craft_menu.getId(), menu_info_types.CRAFT_HOPPER_OUTPUT, null);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if (hasObjVar(self, craftinglib.OBJVAR_PROTOTYPE_TIME))
        {
            if (getFloatObjVar(self, craftinglib.OBJVAR_PROTOTYPE_TIME) > 0.0f)
            {
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnRequestDraftSchematics(obj_id self, obj_id player, int[] schematics, float[] complexities) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        if (getVolumeFree(pInv) <= 0)
        {
            sendSystemMessage(player, INVENTORY_FULL);
            return SCRIPT_OVERRIDE;
        }
        debugServerConsoleMsg(self, "OnRequestDraftSchematics enter, num schematics = " + schematics.length);
        if (schematics.length > 0 && schematics.length == complexities.length)
        {
            int craftingLevel = determineCraftingLevel(self, player);
            if (craftingLevel == -1)
            {
                return SCRIPT_OVERRIDE;
            }
            debugServerConsoleMsg(self, "OnRequestDraftSchematics crafting level = " + craftingLevel);
            int[] allowedSchematics;
            if (craftingLevel < 3)
            {
                allowedSchematics = new int[schematics.length];
                for (int i = 0; i < schematics.length; ++i)
                {
                    if (complexities[i] <= COMPLEXITY_LIMIT[craftingLevel])
                    {
                        allowedSchematics[i] = schematics[i];
                    }
                    else 
                    {
                        allowedSchematics[i] = 0;
                    }
                }
            }
            else 
            {
                allowedSchematics = schematics;
            }
            if (sendUseableDraftSchematics(player, allowedSchematics))
            {
                return SCRIPT_CONTINUE;
            }
        }
        sendSystemMessage(player, NO_SCHEMATICS);
        debugServerConsoleMsg(self, "OnRequestDraftSchematics exit - override");
        return SCRIPT_OVERRIDE;
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (transferer == obj_id.NULL_ID || transferer == srcContainer)
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_OVERRIDE;
    }
    public int OnAboutToLoseItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        debugServerConsoleMsg(self, "Crafting station OnAboutToLoseItem enter");
        float time = getFloatObjVar(self, craftinglib.OBJVAR_PROTOTYPE_TIME);
        if (time != 0.0f)
        {
            debugServerConsoleMsg(self, "Crafting station OnAboutToLoseItem preventing prototype from being removed");
            return SCRIPT_OVERRIDE;
        }
        debugServerConsoleMsg(self, "Crafting station OnAboutToLoseItem allowing prototype to be removed");
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToOpenContainer(obj_id self, obj_id whoOpenedMe) throws InterruptedException
    {
        debugServerConsoleMsg(self, "Crafting station OnAboutToOpenContainer enter");
        float time = getFloatObjVar(self, craftinglib.OBJVAR_PROTOTYPE_TIME);
        if (time != 0.0f)
        {
            sendSystemMessage(whoOpenedMe, new string_id("system_msg", "cant_open_output_hopper"));
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnCraftingAddResource(obj_id self, obj_id player, obj_id resource, draft_schematic.slot ingredientSlot, modifiable_int[] resourceAmount) throws InterruptedException
    {
        int numResources = resourceAmount.length;
        int totalResources = ingredientSlot.amountRequired;
        int resourcesPer = totalResources / numResources;
        if (totalResources % numResources != 0)
        {
            ++resourcesPer;
        }
        for (modifiable_int modifiable_int : resourceAmount) {
            modifiable_int.set(resourcesPer);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnCraftingDone(obj_id self, obj_id player, String schematicName, int craftingStage, boolean normalExit) throws InterruptedException
    {
        session.logActivity(player, session.ACTIVITY_CRAFTING);
        if (craftingStage != CS_selectDraftSchematic && craftingStage != CS_assembly && schematicName != null && schematicName.length() > 0)
        {
            temp_schematic.decrement(player, getObjectTemplateCrc(schematicName));
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
        try {
            if (hasObjVar(self, "quality")) {
                names[idx] = "quality";
                float attrib = getFloatObjVar(self, "quality");
                attribs[idx] = " " + attrib;
                idx++;
                if (idx >= names.length) {
                    return SCRIPT_CONTINUE;
                }
            }
            int critAssembly = getIntObjVar(self, craftinglib.OBJVAR_FORCE_CRITICAL_ASSEMBLY);
            if (critAssembly > 0) {
                names[idx] = "@crafting:crit_assembly";
                attribs[idx] = "" + critAssembly;
                idx++;
                if (idx >= names.length) {
                    return SCRIPT_CONTINUE;
                }
            }
            int critExperiment = getIntObjVar(self, craftinglib.OBJVAR_FORCE_CRITICAL_EXPERIMENT);
            if (critExperiment > 0) {
                names[idx] = "@crafting:crit_experiment";
                attribs[idx] = "" + critExperiment;
                idx++;
                if (idx >= names.length) {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        catch(Exception e){
            System.out.println("Found problem while trying to get attributes.");
            if(isIdValid(self)){
                for(attribute attrib : getAttribs(self)){
                    System.out.println("Found: " + attrib.toString());
                }
            }
            System.out.println("Stack Trace:");
            e.printStackTrace();
        }
        return SCRIPT_CONTINUE;
    }
    public int prototypeDone(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id prototype = params.getObjId("prototype");
        obj_id crafter = params.getObjId("crafter");
        debugServerConsoleMsg(self, "prototypeDone enter, crafter = " + crafter + ", prototype = " + prototype);
        debugSpeakMsg(self, "PING! The prototype is done now.");
        setObjVar(self, craftinglib.OBJVAR_PROTOTYPE_TIME, 0.0f);
        removeObjVar(self, craftinglib.OBJVAR_CRAFTER);
        removeObjVar(self, craftinglib.OBJVAR_PROTOTYPE_START);
        removeObjVar(self, craftinglib.OBJVAR_CRAFTING_PROTOTYPE_OBJECT);
        removeObjVar(self, craftinglib.OBJVAR_CRAFTING_PROTOTYPE_CRAFTER);
        if (hasScript(crafter, "theme_park.new_player.new_player") || hasScript(crafter, jedi_trials.PADAWAN_TRIALS_SCRIPT))
        {
            if (isIdValid(prototype))
            {
                String templateName = getTemplateName(prototype);
                if (templateName != null && !templateName.equals(""))
                {
                    dictionary webster = new dictionary();
                    webster.put("prototype", prototype);
                    webster.put("prototypeTemplate", templateName);
                    messageTo(crafter, "handleQuestCraftingAction", webster, 1, false);
                }
                else 
                {
                    LOG("new_player", "New player " + crafter + " successfully made prototype item " + prototype + " but its template name is null, so the new player crafting action failed!");
                }
            }
        }
        endCraftingSession(crafter, self, params.getObjId("prototype"));
        getPrototype(self, params);
        logBalance("crafting;" + getGameTime() + ";" + crafter + ";" + prototype + ";" + getTemplateName(prototype));
        if (!isIdValid(crafter))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id relic = loot.chroniclesCraftingLootDrop(crafter);
        obj_id inv = utils.getInventoryContainer(crafter);
        boolean canDrop = scheduled_drop.canDropCard(scheduled_drop.SYSTEM_CRAFTER);
        boolean hasDelay = scheduled_drop.hasCardDelay(crafter, scheduled_drop.SYSTEM_CRAFTER);
        if (isGod(crafter) && hasObjVar(crafter, "qa_tcg_always_drop"))
        {
            canDrop = true;
            hasDelay = false;
        }
        if (isIdValid(inv) && canDrop && !hasDelay && isPlayerActive(crafter))
        {
            obj_id card = scheduled_drop.dropCard(scheduled_drop.SYSTEM_CRAFTER, inv);
            if (isIdValid(card))
            {
                String[] cardNameList = split(getName(card), ':');
                if (cardNameList != null && cardNameList.length > 1)
                {
                    string_id cardName = new string_id(cardNameList[0], cardNameList[1]);
                    String name = getString(cardName);
                    prose_package pp = new prose_package();
                    pp = prose.setStringId(pp, new string_id("spam", "tcg_space_loot"));
                    pp = prose.setTU(pp, name);
                    sendSystemMessageProse(crafter, pp);
                }
            }
            else 
            {
                if (isGod(crafter) && hasObjVar(crafter, "qa_tcg"))
                {
                    sendSystemMessageTestingOnly(crafter, "QA TCG CRAFTING NOT DROPPED.  Card is null. Random chance passed? " + canDrop + " Has Card Delay? " + hasDelay);
                }
            }
        }
        else 
        {
            if (isGod(crafter) && hasObjVar(crafter, "qa_tcg"))
            {
                sendSystemMessageTestingOnly(crafter, "QA TCG CRAFTING NOT DROPPED.  Random chance passed? " + canDrop + " Has Card Delay? " + hasDelay);
            }
        }
        utils.setScriptVar(crafter, scheduled_drop.PLAYER_SCRIPTVAR_DROP_TIME, getGameTime());
        return SCRIPT_CONTINUE;
    }
    public obj_id getFirstParentInWorldOrPlayer(obj_id obj) throws InterruptedException
    {
        obj_id firstParent = getFirstParentInWorld(obj);
        while (obj != firstParent && !isPlayer(obj))obj = getContainedBy(obj);
        return obj;
    }
    public int getPrototype(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id crafter = params.getObjId("crafter");
        obj_id owner = getFirstParentInWorldOrPlayer(self);
        float time = getFloatObjVar(self, craftinglib.OBJVAR_PROTOTYPE_TIME);
        if (time != 0.0f && isIdValid(owner))
        {
            sendSystemMessage(owner, new string_id("system_msg", "cant_open_output_hopper"));
            return SCRIPT_OVERRIDE;
        }
        if (isIdValid(owner))
        {
            obj_id inventory = getObjectInSlot(owner, utils.SLOT_INVENTORY);
            if (isIdValid(inventory))
            {
                obj_id prototype = params.getObjId("prototype");
                if (!isIdValid(prototype))
                {
                    obj_id mightBePrototype = getObjectInSlot(self, PROTOTYPE_SLOT);
                    if (!isIdValid(mightBePrototype))
                    {
                        return SCRIPT_CONTINUE;
                    }
                    prototype = mightBePrototype;
                }
                if (hasObjVar(prototype, OBJVAR_CRAFTING_FAKE_PROTOTYPE))
                {
                    destroyObject(prototype);
                }
                else 
                {
                    if (putInOverloaded(prototype, inventory))
                    {
                        sendSystemMessage(owner, new string_id("system_msg", "prototype_transferred"));
                    }
                    else 
                    {
                        sendSystemMessage(owner, new string_id("system_msg", "prototype_not_transferred"));
                    }
                }
            }
            else 
            {
                sendSystemMessage(owner, new string_id("system_msg", "prototype_done"));
            }
        }
        else if (isIdValid(crafter))
        {
            sendSystemMessage(crafter, new string_id("system_msg", "prototype_done"));
        }
        setCount(self, 0);
        return SCRIPT_CONTINUE;
    }
}
