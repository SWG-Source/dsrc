package script.item;

import script.dictionary;
import script.library.*;
import script.obj_id;
import script.string_id;

public class static_item_base extends script.base_script
{
    public static_item_base()
    {
    }
    public static final string_id SID_ITEM_LEVEL_TOO_LOW = new string_id("base_player", "level_too_low");
    public static final string_id SID_ITEM_NOT_ENOUGH_SKILL = new string_id("base_player", "not_correct_skill");
    public static final string_id SID_ITEM_MUST_NOT_BE_EQUIP = new string_id("base_player", "not_while_equipped");
    public static final string_id SID_ITEM_NO_UNIQUE_TRANSFER = new string_id("base_player", "unique_no_transfer");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        dictionary itemData = static_item.getMasterItemDictionary(self);
        if (itemData == null || itemData.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        int masterVersion = itemData.getInt("version");
        int currentVersion = getStaticItemVersion(self);
        if (currentVersion != masterVersion)
        {
            messageTo(self, "handlerVersionUpdate", itemData, 0.1f, true);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            static_item.initializeObject(self, itemData);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        boolean canTransfer = true;
        if (isPlayer(destContainer) || isAPlayerAppearanceInventoryContainer(destContainer))
        {
            dictionary itemData = static_item.getMasterItemDictionary(self);
            if (!isIdValid(transferer))
            {
                obj_id owner = getOwner(self);
                if (destContainer == owner)
                {
                    transferer = owner;
                }
                else if (isAPlayerAppearanceInventoryContainer(destContainer))
                {
                    transferer = getContainedBy(destContainer);
                }
            }
            int requiredLevel = itemData.getInt("required_level");
            String requiredSkill = itemData.getString("required_skill");
            if (!static_item.validateLevelRequired(transferer, requiredLevel))
            {
                sendSystemMessage(transferer, SID_ITEM_LEVEL_TOO_LOW);
                canTransfer = false;
            }
            if (requiredSkill != null && !requiredSkill.equals(""))
            {
                String classTemplate = getSkillTemplate(transferer);
                if (classTemplate != null && !classTemplate.equals(""))
                {
                    if (!classTemplate.startsWith(requiredSkill))
                    {
                        sendSystemMessage(transferer, SID_ITEM_NOT_ENOUGH_SKILL);
                        canTransfer = false;
                    }
                }
            }
            if (hasObjVar(self, "armor.fake_armor"))
            {
                int ohMyGOT = getGameObjectType(self);
                if (!hasCommand(transferer, "wear_all_armor") && isGameObjectTypeOf(ohMyGOT, GOT_armor))
                {
                    sendSystemMessage(transferer, SID_ITEM_NOT_ENOUGH_SKILL);
                    canTransfer = false;
                }
            }
        }
        else 
        {
            if (static_item.isUniqueStaticItem(self))
            {
                obj_id owner = getOwner(self);
                obj_id inv = utils.getInventoryContainer(owner);
                obj_id bank = utils.getPlayerBank(owner);
                if (!isIdValid(inv) || ((destContainer != inv) && (destContainer != bank)))
                {
                    sendSystemMessage(transferer, SID_ITEM_NO_UNIQUE_TRANSFER);
                    canTransfer = false;
                }
            }
        }
        if (static_item.isUniqueStaticItem(self))
        {
            obj_id[] destContents = getContents(destContainer);
            if (destContents != null && destContents.length > 0)
            {
                for (obj_id destContent : destContents) {
                    if (static_item.isStaticItem(destContent)) {
                        if ((getStaticItemName(destContent)).equals(getStaticItemName(self))) {
                            canTransfer = false;
                        }
                    }
                }
            }
        }
        if (!canTransfer)
        {
            if (isGod(transferer))
            {
                sendSystemMessageTestingOnly(transferer, "GOD MODE:You can Equip This due to being in GOD MODE");
                return SCRIPT_CONTINUE;
            }
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTransferred(obj_id self, obj_id sourceContainer, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if (isIdValid(destContainer) && isPlayer(destContainer))
        {
            proc.buildCurrentReacList(destContainer);
        }
        if (isIdValid(sourceContainer) && isPlayer(sourceContainer))
        {
            proc.buildCurrentReacList(sourceContainer);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (isPlayer(getContainedBy(self)))
        {
            obj_id player = utils.getContainingPlayer(self);
            sendSystemMessage(player, SID_ITEM_MUST_NOT_BE_EQUIP);
            CustomerServiceLog("static_item", "Player " + getFirstName(player) + "(" + player + ") tried to destroy item " + getStaticItemName(self) + "(" + self + "), while it was equiped. It was not destroyed.");
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int free = getFirstFreeIndex(names);
        if (free == -1)
        {
            return SCRIPT_CONTINUE;
        }
        dictionary itemData = static_item.getMasterItemDictionary(self);
        if (itemData == null)
        {
            return SCRIPT_CONTINUE;
        }
        dictionary typeData = null;
        switch (itemData.getInt("type"))
        {
            case 1:
            typeData = static_item.getStaticItemWeaponDictionary(self);
            break;
            case 2:
            typeData = static_item.getStaticArmorDictionary(self);
            break;
            case 3:
            typeData = static_item.getStaticItemDictionary(self);
            break;
        }
        if (typeData == null)
        {
            LOG("static_item_base", getStaticItemName(self) + ": typeData is null. Could not find this item in its static item stats datatable.");
            return SCRIPT_CONTINUE;
        }
        int requiredLevelToEquip = itemData.getInt("required_level");
        if (requiredLevelToEquip != 0 && !hasScript(self, "systems.combat.combat_weapon"))
        {
            names[free] = utils.packStringId(new string_id("proc/proc", "required_combat_level"));
            attribs[free++] = "" + requiredLevelToEquip;
        }
        String requiredSkillToEquip = itemData.getString("required_skill");
        if (requiredSkillToEquip != null && !requiredSkillToEquip.equals("") && !hasScript(self, "systems.combat.combat_weapon"))
        {
            names[free] = utils.packStringId(new string_id("proc/proc", "required_skill"));
            attribs[free++] = utils.packStringId(new string_id("ui_roadmap", requiredSkillToEquip));
        }
        int buffIdentity = typeData.getInt("hide_buff_identity");
        int reuseTime = typeData.getInt("reuse_time");
        if (reuseTime != 0 && buffIdentity == 0)
        {
            names[free] = utils.packStringId(new string_id("proc/proc", "reuse_time"));
            attribs[free++] = utils.assembleTimeRemainToUse(reuseTime);
        }
        if (reuseTime > 30 && buffIdentity == 0)
        {
            names[free] = utils.packStringId(new string_id("spam", "reuse_time_counted"));
            String coolDownGroup = typeData.getString("cool_down_group");
            String varName = "clickItem." + coolDownGroup;
            int current_time = getGameTime();
            int timestamp = -1;
            if (hasObjVar(player, varName))
            {
                timestamp = getIntObjVar(player, varName);
            }
            else if (beast_lib.hasActiveBeast(player))
            {
                obj_id activeBeast = beast_lib.getBeastOnPlayer(player);
                if (hasObjVar(activeBeast, varName))
                {
                    timestamp = getIntObjVar(activeBeast, varName);
                }
            }
            int time_remaining = timestamp - current_time;
            if (timestamp == -1 || time_remaining <= 0)
            {
                attribs[free] = "0";
            }
            else 
            {
                attribs[free] = utils.assembleTimeRemainToUse(time_remaining);
            }
            free++;
            if (free >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        int requiredLevelForEffect = typeData.getInt("required_level_for_effect");
        if (requiredLevelForEffect != 0)
        {
            names[free] = utils.packStringId(new string_id("proc/proc", "effect_level"));
            attribs[free++] = "" + requiredLevelForEffect;
        }
        String buffName = typeData.getString("buff_name");
        if (buffName != null && !buffName.equals("") && buffIdentity == 0)
        {
            names[free] = utils.packStringId(new string_id("proc/proc", "proc_name"));
            attribs[free++] = utils.packStringId(new string_id("ui_buff", buffName));
        }
        if (hasObjVar(self, "armor.general_protection_clothing"))
        {
            int pseudoLevel = getIntObjVar(self, "armor.general_protection_clothing");
            String forceLevel = armor.getPseudoArmorLevel(pseudoLevel);
            names[free] = utils.packStringId(new string_id("obj_attr_n", "protection_level"));
            attribs[free++] = utils.packStringId(new string_id("obj_attr_n", forceLevel));
        }
        if (hasObjVar(self, "armor.fake_armor"))
        {
            names[free] = "armor_category";
            attribs[free++] = utils.packStringId(new string_id("obj_attr_n", "special"));
            String[][] entries = 
            {
                
                {
                    "kinetic",
                    "energy"
                },
                
                {
                    "heat",
                    "cold",
                    "acid",
                    "electricity"
                }
            };
            String[] protections = 
            {
                "kinetic",
                "energy",
                "heat",
                "cold",
                "acid",
                "electricity"
            };
            String[] tooltipProtections = 
            {
                "armor_eff_kinetic",
                "armor_eff_energy",
                "armor_eff_elemental_heat",
                "armor_eff_elemental_cold",
                "armor_eff_elemental_acid",
                "armor_eff_elemental_electrical"
            };
            for (String[] entry : entries) {
                for (String s : entry) {
                    if (free < names.length) {
                        if (hasObjVar(self, "armor.fake_armor." + s)) {
                            int displayedProtections = getIntObjVar(self, "armor.fake_armor." + s);
                            names[free] = (String) (armor.SPECIAL_PROTECTION_MAP.get(s));
                            attribs[free++] = Integer.toString(displayedProtections);
                        }
                    }
                }
            }
            for (int i = 0; i < protections.length; i++)
            {
                if (hasObjVar(self, "armor.fake_armor." + protections[i]))
                {
                    int displayedProtections = getIntObjVar(self, "armor.fake_armor." + protections[i]);
                    names[free] = "tooltip." + tooltipProtections[i];
                    attribs[free++] = Integer.toString(displayedProtections);
                }
            }
            if (utils.hasScriptVar(self, armor.SCRIPTVAR_SPECIES_RESTRICTIONS))
            {
                String speciesRequirements = utils.getStringScriptVar(self, armor.SCRIPTVAR_SPECIES_RESTRICTIONS);
                if (speciesRequirements != null && speciesRequirements.length() > 0)
                {
                    names[free] = "species_restrictions.species_name";
                    attribs[free++] = speciesRequirements;
                    names[free] = "tooltip.species_restrictions";
                    attribs[free++] = speciesRequirements;
                }
            }
        }
        int tier = itemData.getInt("tier");
        names[free] = "tier";
        attribs[free++] = "" + tier;
        names[free] = "tooltip.tier";
        attribs[free++] = "" + tier;
        if (static_item.isUniqueStaticItem(self))
        {
            names[free] = "unique";
            attribs[free++] = "1";
        }
        if (static_item.isSetBonusItem(self))
        {
            int setBonusIndex = static_item.getSetBonusIndex(self);
            if (setBonusIndex == -1)
            {
                return SCRIPT_CONTINUE;
            }
            int numTableRows = dataTableGetNumRows(static_item.SET_BONUS_TABLE);
            for (int i = 0; i < numTableRows; i++)
            {
                int thisRowSetBonusIndex = dataTableGetInt(static_item.SET_BONUS_TABLE, i, "SETID");
                int numberOfSetItems = dataTableGetInt(static_item.SET_BONUS_TABLE, i, "NUM_ITEMS");
                if (setBonusIndex == thisRowSetBonusIndex)
                {
                    String setBonusBuffName = dataTableGetString(static_item.SET_BONUS_TABLE, i, "EFFECT");
                    names[free] = utils.packStringId(new string_id("set_bonus", "piece_bonus_count_" + numberOfSetItems));
                    attribs[free++] = utils.packStringId(new string_id("set_bonus", setBonusBuffName));
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handlerVersionUpdate(obj_id self, dictionary params) throws InterruptedException
    {
        static_item.versionUpdate(self, params);
        return SCRIPT_CONTINUE;
    }
    public int handleStaticReEquipItem(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        equip(self, player);
        return SCRIPT_CONTINUE;
    }
}
