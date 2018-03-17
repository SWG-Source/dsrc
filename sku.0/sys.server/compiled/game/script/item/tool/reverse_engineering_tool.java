package script.item.tool;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.static_item;
import script.library.utils;
import script.library.craftinglib;
import script.library.reverse_engineering;

public class reverse_engineering_tool extends script.base_script
{
    public reverse_engineering_tool()
    {
    }
    public static final String STAT_OBJVAR_SUFFIX = "skillmod.bonus.";
    public static final String MODS_TABLE = "datatables/crafting/reverse_engineering_mods.iff";
    public static final String STATIC_ITEM_TABLE = "datatables/item/master_item/master_item.iff";
    public static final String JUNK_TABLE = "datatables/crafting/reverse_engineering_junk.iff";
    public static final String SPECIAL_MOD_TABLE = "datatables/crafting/reverse_engineering_special_mods.iff";
    public static final String MOD_BIT_TEMPLATE = "object/tangible/component/reverse_engineering/modifier_bit.iff";
    public static final String NO_PUP = "no_pup";
    public static final String[] POWER_BIT_TEMPLATE = 
    {
        "object/tangible/component/reverse_engineering/power_bit_1.iff",
        "object/tangible/component/reverse_engineering/power_bit_2.iff",
        "object/tangible/component/reverse_engineering/power_bit_3.iff"
    };
    public static final String[] BASIC_MOD_LIST = 
    {
        "precision_modified",
        "strength_modified",
        "agility_modified",
        "stamina_modified",
        "constitution_modified",
        "luck_modified",
        "camouflage",
        "combat_block_value"
    };
    public static final String[] FINAL_ATTACHMENT_TEMPLATE = 
    {
        "object/tangible/gem/clothing.iff",
        "object/tangible/gem/armor.iff",
        "object/tangible/gem/weapon.iff",
        "object/tangible/gem/weapon.iff",
        "object/tangible/gem/shirt_only.iff",
        "object/tangible/gem/bp_armor_only.iff",
        "object/tangible/gem/weapon.iff",
        "object/tangible/gem/weapon.iff"
    };
    public static final String[] NON_BP_ATTACHMENTS = 
    {
        "object/tangible/gem/clothing.iff",
        "object/tangible/gem/armor.iff"
    };
    public static final String[] SKILL_LIST = 
    {
        "class_domestics_phase1_novice",
        "class_munitions_phase1_novice",
        "class_engineering_phase1_novice"
    };
    public static final String[] POWERUP_STATIC_NAMES = 
    {
        "item_reverse_engineering_powerup_clothing_02_01",
        "item_reverse_engineering_powerup_armor_02_01",
        "item_reverse_engineering_powerup_weapon_02_01"
    };
    public static final String[] BANNED_SCRIPTS = 
    {
        "item.special.nomove",
        "item.special.autoinsure",
        "item.special.nodestroy",
        "item.special.nomove_base",
        "item.special.nomove_container",
        "item.special.nomove_cube_loot",
        "item.special.nomove_furniture",
        "item.special.officer_drop_item"
    };
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (utils.hasLocalVar(self, "ctsBeingUnpacked"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasReverseEngineeringSkill(transferer))
        {
            sendSystemMessage(transferer, new string_id("spam", "re_no_skillz"));
            return SCRIPT_OVERRIDE;
        }
        if (!utils.isNestedWithin(self, transferer))
        {
            sendSystemMessage(transferer, new string_id("spam", "trap_not_in_inventory"));
            return SCRIPT_OVERRIDE;
        }
        if (!canPutInTool(item, transferer, self))
        {
            sendSystemMessage(transferer, new string_id("spam", "not_re_item"));
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public boolean canPutInTool(obj_id item, obj_id transferer, obj_id self) throws InterruptedException
    {
        obj_id[] stuff = getContents(self);
        for (int i = 0; i < BANNED_SCRIPTS.length; i++)
        {
            if (hasScript(item, BANNED_SCRIPTS[i]))
            {
                return false;
            }
        }
        if (isGameObjectTypeOf(getGameObjectType(item), GOT_cybernetic))
        {
            sendSystemMessage(transferer, new string_id("spam", "re_no_cybernetics"));
            return false;
        }
        if (static_item.isStaticItem(item))
        {
            if (!reverse_engineering.canStaticItemBeReversedEngineered(item))
            {
                return false;
            }
        }
        if (isEnhancementModule(item))
        {
            return enhanceTool(self, transferer, item);
        }
        if (stuff.length == 0)
        {
            if (isJunk(item))
            {
                return true;
            }
            else if (isItemWithNPEMod(item))
            {
                return true;
            }
            else if (getPowerBitType(item) > 0)
            {
                return true;
            }
            else if (isModifierBit(item))
            {
                return true;
            }
            else if (getFinalAttachmentLevel(item) > 1 && getFinalAttachmentLevel(item) < 4)
            {
                return true;
            }
        }
        else if (stuff.length == 1)
        {
            if (isJunk(item) && isJunk(stuff[0]))
            {
                return true;
            }
            else if (isItemWithNPEMod(item) && isItemWithNPEMod(stuff[0]))
            {
                return true;
            }
            else if (isModifierBit(item) && ((getFinalAttachmentLevel(stuff[0]) > 1 && getFinalAttachmentLevel(stuff[0]) < 4) || getPowerBitType(stuff[0]) > 0))
            {
                return true;
            }
            else if (getFinalAttachmentLevel(item) > 1 && getFinalAttachmentLevel(item) < 4)
            {
                int finalAttachmentLevel = getFinalAttachmentLevel(item);
                if (getPowerBitType(stuff[0]) == finalAttachmentLevel || isModifierBit(stuff[0]))
                {
                    return true;
                }
            }
            else if (getPowerBitType(item) > 0)
            {
                int powerBitType = getPowerBitType(item);
                if (getFinalAttachmentLevel(stuff[0]) == powerBitType || isModifierBit(stuff[0]))
                {
                    return true;
                }
            }
        }
        else if (stuff.length == 2 && canUpgradeAttachment(transferer))
        {
            if (getFinalAttachmentLevel(item) > 1 && getFinalAttachmentLevel(item) < 4)
            {
                int finalAttachmentLevel = getFinalAttachmentLevel(item);
                if ((getPowerBitType(stuff[0]) == finalAttachmentLevel && isModifierBit(stuff[1])) || (getPowerBitType(stuff[1]) == finalAttachmentLevel && isModifierBit(stuff[0])))
                {
                    return true;
                }
            }
            else if (getPowerBitType(item) > 0)
            {
                int powerBitType = getPowerBitType(item);
                if ((getFinalAttachmentLevel(stuff[0]) == powerBitType && isModifierBit(stuff[1])) || (getFinalAttachmentLevel(stuff[1]) == powerBitType && isModifierBit(stuff[0])))
                {
                    return true;
                }
            }
            else if (isModifierBit(item))
            {
                int powerBitType = getPowerBitType(stuff[0]);
                int theOtherPowerBitType = getPowerBitType(stuff[1]);
                int finalAttachmentLevel = getFinalAttachmentLevel(stuff[0]);
                int theOtherFinalAttachmentLevel = getFinalAttachmentLevel(stuff[1]);
                if ((powerBitType > 0 || theOtherPowerBitType > 0) && (finalAttachmentLevel > 0 || theOtherFinalAttachmentLevel > 0) && ((powerBitType == theOtherFinalAttachmentLevel) || (theOtherPowerBitType == finalAttachmentLevel)))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasReverseEngineeringSkill(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] stuff = getContents(self);
        string_id strSpam = new string_id("spam", "reverse_engineering_reverse_engineer");
        mi.addRootMenu(menu_info_types.SERVER_MENU3, strSpam);
        string_id lookMaSpam = new string_id("spam", "reverse_engineering_create_sea");
        mi.addRootMenu(menu_info_types.SERVER_MENU4, lookMaSpam);
        string_id moreSpam = new string_id("spam", "reverse_engineering_create_powerup");
        mi.addRootMenu(menu_info_types.SERVER_MENU5, moreSpam);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasReverseEngineeringSkill(player))
        {
            sendSystemMessage(player, new string_id("spam", "re_no_skillz"));
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU3)
        {
            doReverseEngineering(self, player);
        }
        if (item == menu_info_types.SERVER_MENU4)
        {
            createSkillEnhancingAttachment(self, player);
        }
        if (item == menu_info_types.SERVER_MENU5)
        {
            createPowerup(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean enhanceTool(obj_id self, obj_id player, obj_id item) throws InterruptedException
    {
        if (isIdValid(item) && exists(item) && isEnhancementModule(item))
        {
            float res_quality = getFloatObjVar(item, "crafting_components.res_quality");
            float itemResQuality = getFloatObjVar(self, "res_quality");
            if (res_quality <= itemResQuality)
            {
                sendSystemMessage(player, new string_id("spam", "re_refused_enhancement_module"));
                return false;
            }
            setObjVar(self, "res_quality", res_quality);
            dictionary params = new dictionary();
            params.put("enhancementModule", item);
            messageTo(self, "destroyEnhancementModule", params, 1.0f, false);
            sendSystemMessage(player, new string_id("spam", "re_added_enhancement_module"));
            return true;
        }
        sendSystemMessage(player, new string_id("spam", "re_no_enhancement_module"));
        return false;
    }
    public int destroyEnhancementModule(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isIdValid(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id item = params.getObjId("enhancementModule");
        if (!isIdValid(item) || !exists(item))
        {
            return SCRIPT_CONTINUE;
        }
        destroyObject(item);
        return SCRIPT_CONTINUE;
    }
    public void doReverseEngineering(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id[] stuff = getContents(self);
        if (stuff.length == 0)
        {
            sendSystemMessage(player, new string_id("spam", "re_tool_empty"));
            return;
        }
        if ((stuff.length == 1 && isItemWithNPEMod(stuff[0])) || ((stuff.length == 2) && isItemWithNPEMod(stuff[0]) && isItemWithNPEMod(stuff[1])))
        {
            generatePowerBit(self, player);
            return;
        }
        else if (stuff.length == 2 && isJunk(stuff[0]) && isJunk(stuff[1]))
        {
            generateModifierBit(self, player);
            return;
        }
        sendSystemMessage(player, new string_id("spam", "dont_make_nothin"));
    }
    public void createSkillEnhancingAttachment(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id[] stuff = getContents(self);
        if (stuff.length == 2 && ((getPowerBitType(stuff[0]) > 0 && isModifierBit(stuff[1])) || (getPowerBitType(stuff[1]) > 0 && isModifierBit(stuff[0]))))
        {
            obj_id inventory = utils.getInventoryContainer(player);
            int power = 1;
            int ratio = 100;
            String mod = "strength_modified";
            for (int i = 0; i < stuff.length; i++)
            {
                if (getPowerBitType(stuff[i]) > 0)
                {
                    power = getIntObjVar(stuff[i], "reverse_engineering.reverse_engineering_power");
                }
                else if (isModifierBit(stuff[i]))
                {
                    mod = getStringObjVar(stuff[i], "reverse_engineering.reverse_engineering_modifier");
                    ratio = getIntObjVar(stuff[i], "reverse_engineering.reverse_engineering_ratio");
                }
            }
            obj_id finalAttachment = createObjectOverloaded(getGemTemplateByClass(player, ratio, 0), inventory);
            if (isIdValid(finalAttachment))
            {
                power = power / ratio;
                setObjVar(finalAttachment, "skillmod.bonus." + mod, power);
                setObjVar(finalAttachment, "reverse_engineering.attachment_level", 2);
                for (int i = 0; i < stuff.length; i++)
                {
                    if (getCount(stuff[i]) > 1)
                    {
                        int count = getCount(stuff[i]);
                        setCount(stuff[i], count - 1);
                    }
                    else 
                    {
                        destroyObject(stuff[i]);
                    }
                }
                prependModNameToObject(finalAttachment, mod);
            }
            return;
        }
        else if (stuff.length == 3)
        {
            if ((getPowerBitType(stuff[0]) > 0 && isModifierBit(stuff[1]) && getFinalAttachmentLevel(stuff[2]) > 1) || (getPowerBitType(stuff[1]) > 0 && isModifierBit(stuff[2]) && getFinalAttachmentLevel(stuff[0]) > 1) || (getPowerBitType(stuff[2]) > 0 && isModifierBit(stuff[0]) && getFinalAttachmentLevel(stuff[1]) > 1) || (getPowerBitType(stuff[0]) > 0 && isModifierBit(stuff[2]) && getFinalAttachmentLevel(stuff[1]) > 1) || (getPowerBitType(stuff[1]) > 0 && isModifierBit(stuff[0]) && getFinalAttachmentLevel(stuff[2]) > 1) || (getPowerBitType(stuff[2]) > 0 && isModifierBit(stuff[1]) && getFinalAttachmentLevel(stuff[0]) > 1))
            {
                obj_id inventory = utils.getInventoryContainer(player);
                int power = 1;
                int attachmentLevel = 1;
                int ratio = 100;
                String mod = "strength_modified";
                int[] whosWho = 
                {
                    -1,
                    -1,
                    -1
                };
                boolean success = false;
                for (int i = 0; i < stuff.length; i++)
                {
                    if (getPowerBitType(stuff[i]) > 0)
                    {
                        power = getIntObjVar(stuff[i], "reverse_engineering.reverse_engineering_power");
                        whosWho[i] = 0;
                    }
                    else if (isModifierBit(stuff[i]))
                    {
                        mod = getStringObjVar(stuff[i], "reverse_engineering.reverse_engineering_modifier");
                        ratio = getIntObjVar(stuff[i], "reverse_engineering.reverse_engineering_ratio");
                        whosWho[i] = 1;
                    }
                    else if (getFinalAttachmentLevel(stuff[i]) > 1)
                    {
                        attachmentLevel = getIntObjVar(stuff[i], "reverse_engineering.attachment_level") + 1;
                        whosWho[i] = 2;
                    }
                }
                for (int i = 0; i < whosWho.length; i++)
                {
                    if (whosWho[i] == 2 && putIn(stuff[i], inventory, player))
                    {
                        if ((getTemplateName(stuff[i])).equals(NON_BP_ATTACHMENTS[0]) || (getTemplateName(stuff[i])).equals(NON_BP_ATTACHMENTS[1]))
                        {
                            boolean bpOnlyMod = true;
                            for (int j = 0; j < BASIC_MOD_LIST.length; j++)
                            {
                                if (mod.equals(BASIC_MOD_LIST[j]))
                                {
                                    bpOnlyMod = false;
                                }
                            }
                            if (bpOnlyMod)
                            {
                                sendSystemMessage(player, new string_id("spam", "bp_only_mod"));
                                return;
                            }
                        }
                        if (hasObjVar(stuff[i], "skillmod.bonus." + mod))
                        {
                            sendSystemMessage(player, new string_id("spam", "re_already_has_mod"));
                            return;
                        }
                        else 
                        {
                            power = power / ratio;
                            setObjVar(stuff[i], "skillmod.bonus." + mod, power);
                            setObjVar(stuff[i], "reverse_engineering.attachment_level", attachmentLevel);
                            success = true;
                        }
                    }
                }
                for (int i = 0; i < whosWho.length; i++)
                {
                    if ((whosWho[i] == 0 || whosWho[i] == 1) && success)
                    {
                        if (getCount(stuff[i]) > 1)
                        {
                            int count = getCount(stuff[i]);
                            setCount(stuff[i], count - 1);
                        }
                        else 
                        {
                            destroyObject(stuff[i]);
                        }
                    }
                }
                return;
            }
        }
        sendSystemMessage(player, new string_id("spam", "sea_wrong_components"));
    }
    public void createPowerup(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id[] stuff = getContents(self);
        if (stuff.length == 2 && ((getPowerBitType(stuff[0]) > 0 && isModifierBit(stuff[1])) || (getPowerBitType(stuff[1]) > 0 && isModifierBit(stuff[0]))))
        {
            obj_id inventory = utils.getInventoryContainer(player);
            int power = 1;
            int ratio = 100;
            String mod = "strength_modified";
            for (int i = 0; i < stuff.length; i++)
            {
                if (getPowerBitType(stuff[i]) > 0)
                {
                    power = getIntObjVar(stuff[i], "reverse_engineering.reverse_engineering_power");
                    float skillMod = getEnhancedSkillStatisticModifierUncapped(player, "expertise_reverse_engineering_bonus");
                    float toolMod = getFloatObjVar(self, "crafting.stationMod");
                    skillMod += toolMod;
                    float quality = getFloatObjVar(self, "res_quality");
                    float randomRollMin = 0.85f;
                    LOG("reverse_engineering", "createPowerup quality: " + quality);
                    if (quality > 0)
                    {
                        randomRollMin += 0.15f * (quality / 100.0f);
                        LOG("reverse_engineering", "createPowerup randomRollMin: " + randomRollMin);
                        removeObjVar(self, "res_quality");
                    }
                    float powerFloat = (((skillMod * rand(randomRollMin, 1.25f)) / 100) + rand(1.0f, 1.25f)) * power;
                    power = (int)powerFloat;
                }
                else if (isModifierBit(stuff[i]))
                {
                    mod = getStringObjVar(stuff[i], "reverse_engineering.reverse_engineering_modifier");
                    if (dataTableGetInt(SPECIAL_MOD_TABLE, mod, NO_PUP) > 0)
                    {
                        sendSystemMessage(player, new string_id("spam", "invalid_powerup"));
                        return;
                    }
                    ratio = getIntObjVar(stuff[i], "reverse_engineering.reverse_engineering_ratio");
                }
            }
            if (mod.endsWith("_experimentation"))
            {
                sendSystemMessage(player, new string_id("spam", "invalid_powerup"));
                return;
            }
            obj_id powerup = static_item.createNewItemFunction(getGemTemplateByClass(player, ratio, 1), inventory);
            if (isIdValid(powerup))
            {
                setObjVar(powerup, "reverse_engineering.reverse_engineering_power", power);
                setObjVar(powerup, "reverse_engineering.reverse_engineering_modifier", mod);
                setObjVar(powerup, "reverse_engineering.reverse_engineering_ratio", ratio);
                float skillMod = getEnhancedSkillStatisticModifierUncapped(player, "expertise_reverse_engineering_bonus");
                float toolMod = getFloatObjVar(self, "crafting.stationMod");
                skillMod += toolMod;
                float numPupCharges = ((skillMod / 11.5f) * ((skillMod * rand(0.85f, 1.25f)) / 11.5f)) + 20;
                setCount(powerup, (int)numPupCharges);
                for (int i = 0; i < stuff.length; i++)
                {
                    if (getCount(stuff[i]) > 1)
                    {
                        int count = getCount(stuff[i]);
                        setCount(stuff[i], count - 1);
                    }
                    else 
                    {
                        destroyObject(stuff[i]);
                    }
                }
                prependModNameToObject(powerup, mod);
            }
        }
        else 
        {
            sendSystemMessage(player, new string_id("spam", "powerup_wrong_components"));
        }
        return;
    }
    public void generatePowerBit(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id[] stuff = getContents(self);
        int skillMod = 0;
        int numStats = 0;
        int statTotal = 0;
        int powerOrderSuccessLevel = 0;
        int maxStat = 0;
        int finalPower = 1;
        boolean containsCraftedItem = false;
        skillMod += getEnhancedSkillStatisticModifierUncapped(player, "expertise_reverse_engineering_bonus");
        float toolMod = getFloatObjVar(self, "crafting.stationMod");
        skillMod += toolMod;
        int powerOrderResult = 0;
        for (int i = 0; i < stuff.length; i++)
        {
            int rows = dataTableGetNumRows(MODS_TABLE);
            for (int j = 0; j < rows; j++)
            {
                if (hasObjVar(stuff[i], STAT_OBJVAR_SUFFIX + dataTableGetString(MODS_TABLE, j, "name")))
                {
                    numStats++;
                    int stat = getIntObjVar(stuff[i], STAT_OBJVAR_SUFFIX + dataTableGetString(MODS_TABLE, j, "name"));
                    statTotal += stat;
                    if (stat > maxStat)
                    {
                        maxStat = stat;
                    }
                }
            }
            if (!hasScript(stuff[i], "item.static_item_base") && !hasScript(stuff[i], "item.armor.dynamic_armor"))
            {
                containsCraftedItem = true;
            }
            if (hasObjVar(stuff[i], "reverse_engineering.retrofitted"))
            {
                containsCraftedItem = true;
            }
        }
        if (canUpgradeAttachment(player))
        {
            powerOrderResult = skillMod + (numStats * 100) + rand(1, 100);
            if (powerOrderResult > 480)
            {
                powerOrderSuccessLevel = 2;
            }
            else if (powerOrderResult > 298)
            {
                powerOrderSuccessLevel = 1;
            }
        }
        float quality = getFloatObjVar(self, "res_quality");
        int randomRollMin = 30;
        LOG("reverse_engineering", "generatePowerBit quality: " + quality);
        if (quality > 0)
        {
            randomRollMin += (int)(40.0f * (quality / 100.0f));
            removeObjVar(self, "res_quality");
            LOG("reverse_engineering", "generatePowerBit randomRollMin: " + randomRollMin);
        }
        float powerResult = (maxStat * (50 + ((skillMod + rand(randomRollMin, 130)) / 3.4f))) / 100;
        if (powerResult / maxStat > 1.25f)
        {
            powerResult = maxStat * 1.25f;
        }
        finalPower = (int)powerResult;
        if (containsCraftedItem && powerResult > maxStat)
        {
            double chanceFloat = (Math.pow(maxStat + 1, 4) / 502.00) + 11.00;
            int chance = (int)chanceFloat;
            int randomRoll = rand(1, chance);
            int luckMod = getEnhancedSkillStatisticModifierUncapped(player, "luck");
            luckMod += getEnhancedSkillStatisticModifierUncapped(player, "luck_modified");
            skillMod += luckMod;
            if (skillMod > randomRoll)
            {
                finalPower = maxStat + 1;
            }
            else 
            {
                finalPower = maxStat;
            }
        }
        if (finalPower > 35)
        {
            finalPower = 35;
        }
        if (finalPower < 1)
        {
            finalPower = 1;
        }
        obj_id inventory = utils.getInventoryContainer(player);
        obj_id powerBit = createObjectOverloaded(POWER_BIT_TEMPLATE[powerOrderSuccessLevel], inventory);
        if (isIdValid(powerBit))
        {
            setObjVar(powerBit, "reverse_engineering.reverse_engineering_power", finalPower);
            attachScript(powerBit, "item.component.reverse_engineer_component");
            for (int i = 0; i < stuff.length; i++)
            {
                if (getCount(stuff[i]) > 1)
                {
                    int count = getCount(stuff[i]);
                    setCount(stuff[i], count - 1);
                }
                else 
                {
                    destroyObject(stuff[i]);
                }
            }
            String baseName = getName(powerBit);
            String[] parsedString = split(baseName, ':');
            if (parsedString.length > 1)
            {
                String stfFile = parsedString[0];
                String reference = parsedString[1];
                string_id itemNameSID = new string_id(stfFile, reference);
                String stringName = getString(itemNameSID);
                String finalName = "+" + finalPower + " " + stringName;
                setName(powerBit, finalName);
            }
        }
        return;
    }
    public void generateModifierBit(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id[] stuff = getContents(self);
        boolean isRare = false;
        String modName = "";
        int ratio = 1;
        String junkTemplateOne = getTemplateName(stuff[0]);
        String junkTemplateTwo = getTemplateName(stuff[1]);
        int junkIndexOne = dataTableGetInt(JUNK_TABLE, dataTableSearchColumnForString(junkTemplateOne, "name", JUNK_TABLE), "index");
        int junkIndexTwo = dataTableGetInt(JUNK_TABLE, dataTableSearchColumnForString(junkTemplateTwo, "name", JUNK_TABLE), "index");
        int junkProduct = junkIndexOne * junkIndexTwo;
        if (junkProduct < 4)
        {
            sendSystemMessage(player, new string_id("spam", "dont_make_nothin"));
            return;
        }
        int[] minSpecial = dataTableGetIntColumn(SPECIAL_MOD_TABLE, "min");
        int[] maxSpecial = dataTableGetIntColumn(SPECIAL_MOD_TABLE, "max");
        for (int i = 0; i < minSpecial.length; i++)
        {
            if (junkProduct >= minSpecial[i] && junkProduct <= maxSpecial[i])
            {
                isRare = true;
                modName = dataTableGetString(SPECIAL_MOD_TABLE, i, "mod");
                ratio = dataTableGetInt(SPECIAL_MOD_TABLE, i, "ratio");
                break;
            }
        }
        if (!isRare)
        {
            int modReference = junkProduct % 6;
            if (modReference < 0 || modReference > 5)
            {
                modReference = 0;
            }
            modName = BASIC_MOD_LIST[modReference];
        }
        obj_id inventory = utils.getInventoryContainer(player);
        obj_id powerBit = createObjectOverloaded(MOD_BIT_TEMPLATE, inventory);
        if (isIdValid(powerBit))
        {
            setObjVar(powerBit, "reverse_engineering.reverse_engineering_modifier", modName);
            setObjVar(powerBit, "reverse_engineering.reverse_engineering_ratio", ratio);
            attachScript(powerBit, "item.component.reverse_engineer_component");
            for (int i = 0; i < stuff.length; i++)
            {
                if (getCount(stuff[i]) > 1)
                {
                    int count = getCount(stuff[i]);
                    setCount(stuff[i], count - 1);
                }
                else 
                {
                    destroyObject(stuff[i]);
                }
            }
            prependModNameToObject(powerBit, modName);
        }
        return;
    }
    public boolean isJunk(obj_id item) throws InterruptedException
    {
        if (hasScript(item, "item.static_item_base") && hasScript(item, "object.autostack"))
        {
            String myName = getStaticItemName(item);
            int myRow = dataTableSearchColumnForString(myName, "name", STATIC_ITEM_TABLE);
            int myTier = dataTableGetInt(STATIC_ITEM_TABLE, myRow, "tier");
            int value = dataTableGetInt(STATIC_ITEM_TABLE, myRow, "value");
            String myTemplate = getTemplateName(item);
            int junkListTemplatePosition = dataTableSearchColumnForString(myTemplate, "name", JUNK_TABLE);
            if (myTier == 1 && value > 0 && junkListTemplatePosition > 0)
            {
                return true;
            }
        }
        return false;
    }
    public boolean isItemWithNPEMod(obj_id item) throws InterruptedException
    {
        if (getFinalAttachmentLevel(item) > -1)
        {
            return false;
        }
        if (hasObjVar(item, "reverse_engineering.reverse_engineering_power"))
        {
            return false;
        }
        if (hasObjVar(item, "skillmod.bonus"))
        {
            return true;
        }
        return false;
    }
    public int getPowerBitType(obj_id item) throws InterruptedException
    {
        int modifierBitType = -1;
        String itemTemplate = getTemplateName(item);
        for (int i = 0; i < POWER_BIT_TEMPLATE.length; i++)
        {
            if (itemTemplate.equals(POWER_BIT_TEMPLATE[i]))
            {
                return i + 1;
            }
        }
        return -1;
    }
    public boolean isEnhancementModule(obj_id item) throws InterruptedException
    {
        if (!isIdValid(item))
        {
            return false;
        }
        String templateName = getTemplateName(item);
        if (templateName.equals("object/tangible/component/reverse_engineering/enhancement_module.iff"))
        {
            return true;
        }
        return false;
    }
    public boolean isModifierBit(obj_id item) throws InterruptedException
    {
        if (hasObjVar(item, "reverse_engineering.reverse_engineering_modifier") && hasObjVar(item, "reverse_engineering.reverse_engineering_ratio") && !hasScript(item, "item.tool.reverse_engineering_powerup") && !hasScript(item, "item.tool.reverse_engineering_poweredup_item"))
        {
            return true;
        }
        return false;
    }
    public int getFinalAttachmentLevel(obj_id item) throws InterruptedException
    {
        int attachmentLevel = -1;
        if (hasObjVar(item, "reverse_engineering.attachment_level"))
        {
            attachmentLevel = getIntObjVar(item, "reverse_engineering.attachment_level");
            return attachmentLevel;
        }
        return attachmentLevel;
    }
    public boolean hasReverseEngineeringSkill(obj_id player) throws InterruptedException
    {
        for (int i = 0; i < SKILL_LIST.length; i++)
        {
            if (hasSkill(player, SKILL_LIST[i]))
            {
                return true;
            }
        }
        return false;
    }
    public boolean canUpgradeAttachment(obj_id player) throws InterruptedException
    {
        float skillMod = getEnhancedSkillStatisticModifierUncapped(player, "expertise_attachment_upgrade");
        if (skillMod > 0)
        {
            return true;
        }
        return false;
    }
    public String getGemTemplateByClass(obj_id player, int ratio, int type) throws InterruptedException
    {
        int classType = 0;
        for (int i = 0; i < SKILL_LIST.length; i++)
        {
            if (hasSkill(player, SKILL_LIST[i]))
            {
                if (type == 1)
                {
                    return POWERUP_STATIC_NAMES[i];
                }
                if (ratio > 1 && type == 0)
                {
                    classType = i + 4;
                    break;
                }
                else 
                {
                    classType = i;
                    break;
                }
            }
        }
        if (classType < 0 || classType > 7)
        {
            classType = 0;
        }
        return FINAL_ATTACHMENT_TEMPLATE[classType];
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        if (!isIdValid(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        int idx = utils.getValidAttributeIndex(names);
        if (hasObjVar(self, "crafting.stationMod"))
        {
            float power = getFloatObjVar(self, "crafting.stationMod");
            names[idx] = "@obj_attr_n:experiment_bonus_eff";
            attribs[idx] = "" + power;
            idx++;
        }
        if (hasObjVar(self, "res_quality"))
        {
            names[idx] = "quality_bonus";
            attribs[idx] = "" + getFloatObjVar(self, "res_quality");
            idx++;
        }
        return SCRIPT_CONTINUE;
    }
    public void prependModNameToObject(obj_id object, String modSIDreference) throws InterruptedException
    {
        String baseName = getName(object);
        String modName = getString(new string_id("stat_n", modSIDreference));
        String[] parsedString = split(baseName, ':');
        if (!modName.startsWith("[") && !modName.startsWith("@") && !modName.equals("") && parsedString.length > 1)
        {
            String stfFile = parsedString[0];
            String reference = parsedString[1];
            string_id itemNameSID = new string_id(stfFile, reference);
            String stringName = getString(itemNameSID);
            String newName = modName + " " + stringName;
            setName(object, newName);
        }
        return;
    }
}
