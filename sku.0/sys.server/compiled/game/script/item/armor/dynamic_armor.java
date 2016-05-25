package script.item.armor;

import script.library.armor;
import script.library.combat;
import script.library.static_item;
import script.library.utils;
import script.obj_id;
import script.string_id;

public class dynamic_armor extends script.base_script
{
    public dynamic_armor()
    {
    }
    public static final string_id SID_ITEM_LEVEL_TOO_LOW = new string_id("base_player", "level_too_low");
    public static final string_id SID_ITEM_NOT_ENOUGH_SKILL = new string_id("base_player", "not_correct_skill");
    public static final string_id SID_ITEM_MUST_NOT_BE_EQUIP = new string_id("base_player", "not_while_equipped");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasScript(self, "item.armor.new_armor"))
        {
            int intLevel = getIntObjVar(self, "dynamic_item.intLevelRequired");
            static_item.setupDynamicArmor(self, intLevel);
        }
        if (!hasObjVar(self, "armor.armorCategory"))
        {
            String selfTemplate = getTemplateName(self);
            int rowNum = dataTableSearchColumnForString(selfTemplate, "strTemplate", "datatables/item/dynamic_item/appearances/armor_standard.iff");
            if (rowNum > 0)
            {
                int datatableArmorType = dataTableGetInt("datatables/item/dynamic_item/appearances/armor_standard.iff", rowNum, "armorType");
                if (datatableArmorType > -1)
                {
                    setObjVar(self, "armor.armorCategory", datatableArmorType);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if (combat.isInCombat(transferer))
        {
            sendSystemMessage(transferer, new string_id("base_player", "not_while_in_combat"));
            return SCRIPT_OVERRIDE;
        }
        boolean canTransfer = true;
        if (isPlayer(destContainer) || isAPlayerAppearanceInventoryContainer(destContainer))
        {
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
            int requiredLevel = getIntObjVar(self, "dynamic_item.intLevelRequired");
            int playerLevel = getLevel(transferer);
            if (playerLevel < requiredLevel)
            {
                sendSystemMessage(transferer, SID_ITEM_LEVEL_TOO_LOW);
                canTransfer = false;
            }
            String requiredSkill = getStringObjVar(self, "dynamic_item.required_skill");
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
        obj_id player = transferer;
        if (isIdValid(self) && hasObjVar(self, "armor.fake_armor") && (getContainedBy(self) == sourceContainer || getContainedBy(self) == destContainer))
        {
            armor.recalculateArmorForPlayer(player);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id player = utils.getContainingPlayer(self);
        if (getContainedBy(self) == player)
        {
            armor.recalculateArmorForPlayer(player);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        if(self == null || self == obj_id.NULL_ID || !isIdValid(self) || !exists(self)){
            return SCRIPT_CONTINUE;
        }
        int free = getFirstFreeIndex(names);
        if (free == -1)
        {
            return SCRIPT_CONTINUE;
        }
        int requiredLevelToEquip = getIntObjVar(self, "dynamic_item.intLevelRequired");
        if (requiredLevelToEquip != 0)
        {
            names[free] = utils.packStringId(new string_id("proc/proc", "required_combat_level"));
            attribs[free++] = "" + requiredLevelToEquip;
        }
        String requiredSkillToEquip = getStringObjVar(self, "dynamic_item.required_skill");
        if (requiredSkillToEquip != null && !requiredSkillToEquip.equals(""))
        {
            names[free] = utils.packStringId(new string_id("proc/proc", "required_skill"));
            attribs[free++] = utils.packStringId(new string_id("ui_roadmap", requiredSkillToEquip));
        }
        boolean displayProtections = true;
        int myGot = getGameObjectType(self);
        if (myGot == GOT_armor_foot || myGot == GOT_armor_hand || myGot == GOT_clothing_hand || myGot == GOT_clothing_foot)
        {
            displayProtections = false;
        }
        if (hasObjVar(self, "armor.fake_armor"))
        {
            names[free] = "armor_category";
            attribs[free++] = utils.packStringId(new string_id("obj_attr_n", "special"));
            if (displayProtections)
            {
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
                for (int i = 0; i < entries.length; ++i)
                {
                    for (int j = 0; j < entries[i].length; ++j)
                    {
                        if (free < names.length)
                        {
                            if (hasObjVar(self, "armor.fake_armor." + entries[i][j]))
                            {
                                int displayedProtections = getIntObjVar(self, "armor.fake_armor." + entries[i][j]);
                                names[free] = (String)(armor.SPECIAL_PROTECTION_MAP.get(entries[i][j]));
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
        return SCRIPT_CONTINUE;
    }
}
