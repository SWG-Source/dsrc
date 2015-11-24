package script.systems.crafting.bio_engineer;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.city;
import script.library.craftinglib;
import script.library.bio_engineer;
import script.library.buff;

public class crafting_base_dna_template extends script.systems.crafting.crafting_base
{
    public crafting_base_dna_template()
    {
    }
    public static final String VERSION = "v1.00.00";
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        super.calcAndSetPrototypeProperties(prototype, itemAttributes, craftingValuesDictionary);
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
        int armorBase = 0;
        debugServerConsoleMsg(null, "Beginning assembly-phase prototype property setting");
        for (int i = 0; i < itemAttributes.length; ++i)
        {
            if (itemAttributes[i] == null)
            {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttributes[i]))
            {
                if (((itemAttributes[i].name).getAsciiId()).equals("fortitude"))
                {
                    armorBase = (int)itemAttributes[i].currentValue;
                }
                setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttributes[i].name).getAsciiId(), itemAttributes[i].currentValue);
            }
        }
        obj_id self = getSelf();
        String root = craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".";
        int quality = getIntObjVar(self, root + "quality");
        setObjVar(prototype, root + "quality", quality);
        int specialAttack1 = getIntObjVar(self, root + "specialAttack1");
        setObjVar(prototype, root + "specialAttack1", specialAttack1);
        int specialAttack2 = getIntObjVar(self, root + "specialAttack2");
        setObjVar(prototype, root + "specialAttack2", specialAttack2);
        String rangedWeapon = getStringObjVar(self, root + "rangedWeapon");
        if (rangedWeapon == null)
        {
            rangedWeapon = "";
        }
        setObjVar(prototype, root + "rangedWeapon", rangedWeapon);
    }
    public resource_weight[] getSlotResourceWeights() throws InterruptedException
    {
        LOG("crafting", "Called into crafting_base script getSlotResourceWeights()! This function should have been overridden!");
        return null;
    }
    public resource_weight[] getAssemblyDisplayWeights() throws InterruptedException
    {
        LOG("crafting", "Called into crafting_base script getSlotResourceWeights()! This function should have been overridden!");
        return null;
    }
    public static final String COMPONENT_RESOURCE_AVERAGE_DICTIONARY_NAME = "itemTotalComponentResourceAverage";
    public static final String COMPONENT_RESOURCE_QUALITY_INDEX_DICTIONARY_NAME = "componentResourceQualityIndex";
    public float calcResourceQualitySkillMod(dictionary craftingValuesDictionary) throws InterruptedException
    {
        if (craftingValuesDictionary == null)
        {
            return 0;
        }
        float resourceQualitySkillMod = 0;
        float[] resources = craftingValuesDictionary.getFloatArray(craftinglib.RESOURCE_AVERAGE_DICTIONARY_NAME);
        if (resources == null || resources.length != craftinglib.NUM_RESOURCE_ATTRIBS)
        {
            LOG("crafting", "Java craftinglib.scriptlib::calcResourceQualitySkillMod - no resource average array!");
        }
        else 
        {
            float averageResourceQuality = resources[craftinglib.RESOURCE_QUALITY];
            float objectQualityMultiMod = (averageResourceQuality - 500) / 200;
            resourceQualitySkillMod += objectQualityMultiMod * 5;
            debugServerConsoleMsg(null, "The resourceQualitySkillMod is " + resourceQualitySkillMod);
            float itemDefaultComplexity = craftingValuesDictionary.getFloat("itemDefaultComplexity");
            debugServerConsoleMsg(null, "object complexity value by default is " + itemDefaultComplexity);
            if (averageResourceQuality >= 900)
            {
                float itemCurrentComplexity = craftingValuesDictionary.getFloat("itemCurrentComplexity");
                itemCurrentComplexity = itemCurrentComplexity - 1;
                craftingValuesDictionary.put("itemCurrentComplexity", itemCurrentComplexity);
                debugServerConsoleMsg(null, "object complexity reduced by 1 thanks to high average quality of resources used");
                debugServerConsoleMsg(null, "object complexity value is now " + itemCurrentComplexity);
            }
        }
        float componentQuality = craftingValuesDictionary.getFloat(craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + bio_engineer.ATTRIB_DICT_QUALITY + "_average");
        if (componentQuality == 0)
        {
            LOG("crafting", "Java craftinglib.scriptlib::calcResourceQualitySkillMod - no resource average array!");
        }
        else 
        {
            float objectQualityMultiMod = componentQuality - bio_engineer.DNA_AVG_QUALITY;
            resourceQualitySkillMod += objectQualityMultiMod * 5;
            debugServerConsoleMsg(null, "The resourceQualitySkillMod is " + resourceQualitySkillMod);
            float itemDefaultComplexity = craftingValuesDictionary.getFloat("itemDefaultComplexity");
            debugServerConsoleMsg(null, "object complexity value by default is " + itemDefaultComplexity);
            if (componentQuality >= bio_engineer.DNA_V_HIGH_QUALITY)
            {
                float itemCurrentComplexity = craftingValuesDictionary.getFloat("itemCurrentComplexity");
                itemCurrentComplexity = itemCurrentComplexity - 1;
                craftingValuesDictionary.put("itemCurrentComplexity", itemCurrentComplexity);
                debugServerConsoleMsg(null, "object complexity reduced by 1 thanks to high average quality of resources used");
                debugServerConsoleMsg(null, "object complexity value is now " + itemCurrentComplexity);
            }
        }
        return resourceQualitySkillMod;
    }
    public float getAttributeResourceMod(float averagedAttributeSum) throws InterruptedException
    {
        float resourceAverageMod;
        if (averagedAttributeSum == 500)
        {
            resourceAverageMod = 1;
        }
        else 
        {
            resourceAverageMod = (averagedAttributeSum - 500) / 200;
        }
        float attributeResourceMod = resourceAverageMod * 5;
        return attributeResourceMod;
    }
    public float computeComponentWeightedAverage(dictionary craftingValuesDictionary, resource_weight.weight[] weights) throws InterruptedException
    {
        float total = 0;
        int count = 0;
        for (int i = 0; i < weights.length; ++i)
        {
            total += craftingValuesDictionary.getFloat(bio_engineer.COMPONENT_RESOURCE_ATTRIB_OBJVAR_NAME + "." + bio_engineer.CREATURE_ATTRIB_OBJVAR_NAMES[weights[i].resource] + "_average") * weights[i].weight;
            count += weights[i].weight;
        }
        if (count != 0)
        {
            return total / count;
        }
        return 0;
    }
    public int calcSkillDesignAssemblyCheck(obj_id player, dictionary craftingValuesDictionary, String[] skills) throws InterruptedException
    {
        boolean isPlayerQA = false;
        boolean isPlayerTester = false;
        if (hasObjVar(player, "crafting_qa"))
        {
            isPlayerQA = true;
        }
        if (hasObjVar(player, "crafting_tester"))
        {
            isPlayerTester = true;
        }
        float deltaMod = craftinglib.NUMERIC_RANGE_EXPRESSION / craftinglib.BASE_RANGE;
        if (player == null || craftingValuesDictionary == null)
        {
            CustomerServiceLog("Crafting", "VALIDATION FAILED -- Assembly returned a critical failure because player object ID or crafting dictionary was null");
            return craftinglib.STATE_CRITICAL_FAILURE;
        }
        int successState = craftinglib.STATE_UNSET;
        float dieRoll = rand(1, (int)craftinglib.NUMERIC_RANGE_EXPRESSION);
        float dieRollMod = 0f;
        float itemComplexitySkillMod = craftinglib.calcItemComplexitySkillMod(craftingValuesDictionary);
        float resourceMalleabilitySkillMod = 0;
        float resourceQualitySkillMod = calcResourceQualitySkillMod(craftingValuesDictionary);
        craftingValuesDictionary.put(craftinglib.RESOURCE_MALLEABILITY_SKILL_MOD, resourceMalleabilitySkillMod);
        if (craftingValuesDictionary.containsKey(craftinglib.OBJVAR_REFACTOR))
        {
            itemComplexitySkillMod = 0;
        }
        float craftingStationMod = 0;
        if (hasObjVar(getSelf(), craftinglib.OBJVAR_CRAFTING_STATION_BONUS) == true)
        {
            craftingStationMod = getIntObjVar(getSelf(), craftinglib.OBJVAR_CRAFTING_STATION_BONUS);
        }
        float playerSkillMod = 0;
        if (skills != null)
        {
            int[] mods = getEnhancedSkillStatisticModifiers(player, skills);
            for (int i = 0; i < mods.length; ++i)
            {
                playerSkillMod += mods[i];
            }
        }
        float forceSkillMod = getSkillStatisticModifier(player, "force_assembly");
        debugServerConsoleMsg(null, "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        debugServerConsoleMsg(null, "@@@@");
        debugServerConsoleMsg(null, "@@@@ calcSkillDesignAssemblyCheck: Base Roll = " + dieRoll);
        debugServerConsoleMsg(null, "@@@@ calcSkillDesignAssemblyCheck: Complexity Mod = " + itemComplexitySkillMod);
        debugServerConsoleMsg(null, "@@@@ calcSkillDesignAssemblyCheck: Malleability Mod = " + resourceMalleabilitySkillMod);
        debugServerConsoleMsg(null, "@@@@ calcSkillDesignAssemblyCheck: Quality Mod = " + resourceQualitySkillMod);
        debugServerConsoleMsg(null, "@@@@ calcSkillDesignAssemblyCheck: Station Mod = " + craftingStationMod);
        debugServerConsoleMsg(null, "@@@@ calcSkillDesignAssemblyCheck: Skill Mod = " + playerSkillMod);
        debugServerConsoleMsg(null, "@@@@ calcSkillDesignAssemblyCheck: Force Mod = " + forceSkillMod);
        if (isPlayerQA)
        {
            sendSystemMessageTestingOnly(player, "CRAFTING ---------------------------------");
        }
        int city_id = city.checkCity(player, false);
        if (city_id > 0 && (city.cityHasSpec(city_id, city.SF_SPEC_INDUSTRY) || city.cityHasSpec(city_id, city.SF_SPEC_MASTER_MANUFACTURING)))
        {
            dieRollMod += dieRoll * 1.1f;
            if (isPlayerQA)
            {
                sendSystemMessageTestingOnly(player, "CRAFTING -- 10% raw random bonus for industry city");
            }
        }
        dieRollMod += craftinglib.getCraftingInspirationBonus(player, craftingValuesDictionary, dieRoll, 5f);
        if (utils.hasScriptVar(player, "buff.dessert_pyollian_cake.value"))
        {
            int eff = (int)utils.getFloatScriptVar(player, "buff.dessert_pyollian_cake.value");
            buff.removeBuff(player, "dessert_pyollian_cake");
            dieRollMod += (eff * deltaMod);
            if (isPlayerQA)
            {
                sendSystemMessageTestingOnly(player, "CRAFTING -- Food buff effect applied to raw random roll");
            }
        }
        if (isPlayerQA)
        {
            sendSystemMessageTestingOnly(player, "CRAFTING -- Raw Random Roll = " + dieRoll);
            sendSystemMessageTestingOnly(player, "CRAFTING --");
            sendSystemMessageTestingOnly(player, "CRAFTING -- Item Complexity Mod = " + itemComplexitySkillMod);
            sendSystemMessageTestingOnly(player, "CRAFTING -- Resource Malleability Mod = " + resourceMalleabilitySkillMod);
            sendSystemMessageTestingOnly(player, "CRAFTING -- Resource Quality Mod = " + resourceQualitySkillMod);
            sendSystemMessageTestingOnly(player, "CRAFTING -- Assembly Skill Mod = " + playerSkillMod);
            sendSystemMessageTestingOnly(player, "CRAFTING -- Force Skill Mod = " + forceSkillMod);
            sendSystemMessageTestingOnly(player, "CRAFTING -- Crafting Station Mod = " + craftingStationMod);
        }
        debugServerConsoleMsg(null, "@@@@");
        float totalSkillMod = resourceMalleabilitySkillMod + resourceQualitySkillMod + playerSkillMod + forceSkillMod + craftingStationMod - itemComplexitySkillMod;
        debugServerConsoleMsg(null, "@@@@ calcSkillDesignAssemblyCheck: Total Skill Mod = " + totalSkillMod);
        debugServerConsoleMsg(null, "@@@@ calcSkillDesignAssemblyCheck: Delta = " + deltaMod);
        if (isPlayerQA)
        {
            sendSystemMessageTestingOnly(player, "CRAFTING -- Sum of Roll Modifiers = " + totalSkillMod);
            sendSystemMessageTestingOnly(player, "CRAFTING --");
            sendSystemMessageTestingOnly(player, "CRAFTING -- Modifier Balance Multiplier = " + deltaMod);
            sendSystemMessageTestingOnly(player, "CRAFTING --");
        }
        totalSkillMod *= deltaMod;
        debugServerConsoleMsg(null, "@@@@ calcSkillDesignAssemblyCheck: Adjusted Skill Mod = " + totalSkillMod);
        if (isPlayerQA)
        {
            sendSystemMessageTestingOnly(player, "CRAFTING -- Balanced Roll Modifiers = " + totalSkillMod);
        }
        float modifiedDieRoll = dieRoll + totalSkillMod + dieRollMod;
        debugServerConsoleMsg(null, "@@@@ calcSkillDesignAssemblyCheck: Total Roll = " + modifiedDieRoll);
        if (isPlayerQA)
        {
            sendSystemMessageTestingOnly(player, "CRAFTING -- FINAL MODIFIED ROLL = " + modifiedDieRoll);
        }
        float criticalAdjust = playerSkillMod / 4000.0f;
        if (criticalAdjust > 0.05f)
        {
            criticalAdjust = 0.05f;
        }
        float criticalSuccess = craftinglib.NUMERIC_RANGE_EXPRESSION * (0.95f - criticalAdjust);
        float criticalFailure = craftinglib.NUMERIC_RANGE_EXPRESSION * (0.05f - criticalAdjust);
        float skillMod = getEnhancedSkillStatisticModifier(player, "force_failure_reduction");
        skillMod *= 0.005f;
        criticalFailure -= skillMod;
        if (craftingValuesDictionary.containsKey(craftinglib.OBJVAR_REFACTOR))
        {
            criticalSuccess = craftinglib.NUMERIC_RANGE_EXPRESSION + 1;
            criticalFailure = 0;
        }
        final float BASE_DIFFICULTY_ADJUSTMENT = 0.25f;
        float defaultSkillCheckDifficulty = craftinglib.NUMERIC_RANGE_EXPRESSION * BASE_DIFFICULTY_ADJUSTMENT;
        float greatSuccess = defaultSkillCheckDifficulty + craftinglib.NUMERIC_RANGE_EXPRESSION * 0.45f;
        float goodSuccess = defaultSkillCheckDifficulty + craftinglib.NUMERIC_RANGE_EXPRESSION * 0.30f;
        float moderateSuccess = defaultSkillCheckDifficulty + craftinglib.NUMERIC_RANGE_EXPRESSION * 0.15f;
        float moderateFailure = defaultSkillCheckDifficulty - craftinglib.NUMERIC_RANGE_EXPRESSION * 0.15f;
        float bigFailure = defaultSkillCheckDifficulty - craftinglib.NUMERIC_RANGE_EXPRESSION * 0.30f;
        debugServerConsoleMsg(null, "@@@@");
        debugServerConsoleMsg(null, "@@@@");
        debugServerConsoleMsg(null, "@@@@ calcSkillDesignAssemblyCheck: Critical Success = " + criticalSuccess);
        debugServerConsoleMsg(null, "@@@@ calcSkillDesignAssemblyCheck: Critical Failure = " + criticalFailure);
        debugServerConsoleMsg(null, "@@@@");
        debugServerConsoleMsg(null, "@@@@ calcSkillDesignAssemblyCheck: Great Success = " + greatSuccess);
        debugServerConsoleMsg(null, "@@@@ calcSkillDesignAssemblyCheck: Good Success = " + goodSuccess);
        debugServerConsoleMsg(null, "@@@@ calcSkillDesignAssemblyCheck: Moderate Success = " + moderateSuccess);
        debugServerConsoleMsg(null, "@@@@ calcSkillDesignAssemblyCheck: Success = " + defaultSkillCheckDifficulty);
        debugServerConsoleMsg(null, "@@@@ calcSkillDesignAssemblyCheck: Failure = " + moderateFailure);
        debugServerConsoleMsg(null, "@@@@ calcSkillDesignAssemblyCheck: Big Failure = " + bigFailure);
        debugServerConsoleMsg(null, "@@@@");
        debugServerConsoleMsg(null, "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        if (isPlayerQA)
        {
            sendSystemMessageTestingOnly(player, "CRAFTING --");
            sendSystemMessageTestingOnly(player, "CRAFTING -- Critical Success: raw roll above " + criticalSuccess);
            sendSystemMessageTestingOnly(player, "CRAFTING -- Critical Failure: raw roll below " + criticalFailure);
            sendSystemMessageTestingOnly(player, "CRAFTING --");
            sendSystemMessageTestingOnly(player, "CRAFTING -- Great Success: mod roll above " + greatSuccess);
            sendSystemMessageTestingOnly(player, "CRAFTING -- Good Success: mod roll above " + goodSuccess);
            sendSystemMessageTestingOnly(player, "CRAFTING -- Moderate Success: mod roll above " + moderateSuccess);
            sendSystemMessageTestingOnly(player, "CRAFTING -- Success: mod roll above " + defaultSkillCheckDifficulty);
            sendSystemMessageTestingOnly(player, "CRAFTING -- Failure: mod roll above " + moderateFailure);
            sendSystemMessageTestingOnly(player, "CRAFTING -- Moderate Failure: mod roll above " + bigFailure);
            sendSystemMessageTestingOnly(player, "CRAFTING -- Big Failure: mod roll below " + bigFailure);
        }
        if (isPlayerTester)
        {
            CustomerServiceLog("crit_fail", "== ASSEMBLY_BEGIN == " + getName(player) + " (" + player + ") is starting to assemble an item -- Base Roll: " + dieRoll + ", Mod Roll: " + modifiedDieRoll);
        }
        if (dieRoll >= criticalSuccess)
        {
            successState = craftinglib.STATE_CRITICAL_SUCCESS;
        }
        else if (dieRoll <= criticalFailure)
        {
            if (isPlayerTester)
            {
                CustomerServiceLog("crit_fail", "== ASSEMBLY_FAILURE MOD: " + playerSkillMod + " == " + getName(player) + " (" + player + ") has critically failed while assembling an item -- " + dieRoll + " <= " + criticalFailure);
            }
            successState = craftinglib.STATE_CRITICAL_FAILURE;
            int craftingType = craftingValuesDictionary.getInt("craftingType");
            if ((craftingType & CT_installation) != 0 || (craftingType & CT_mission) != 0)
            {
                successState = craftinglib.STATE_CRITICAL_FAILURE_NODESTROY;
            }
        }
        else if (modifiedDieRoll > greatSuccess)
        {
            successState = craftinglib.STATE_GREAT_SUCCESS;
        }
        else if (modifiedDieRoll > goodSuccess)
        {
            successState = craftinglib.STATE_GOOD_SUCCESS;
        }
        else if (modifiedDieRoll > moderateSuccess)
        {
            successState = craftinglib.STATE_MODERATE_SUCCESS;
        }
        else if (modifiedDieRoll > defaultSkillCheckDifficulty)
        {
            successState = craftinglib.STATE_SUCCESS;
        }
        else if (modifiedDieRoll > moderateFailure)
        {
            successState = craftinglib.STATE_FAILURE;
        }
        else if (modifiedDieRoll > bigFailure)
        {
            successState = craftinglib.STATE_MODERATE_FAILURE;
        }
        else 
        {
            successState = craftinglib.STATE_BIG_FAILURE;
        }
        if (successState != craftinglib.STATE_CRITICAL_SUCCESS && craftingValuesDictionary.containsKey(craftinglib.OBJVAR_FORCE_CRITICAL_SUCCESS))
        {
            if (!craftingValuesDictionary.containsKey(craftinglib.OBJVAR_REFACTOR))
            {
                successState = craftinglib.STATE_CRITICAL_SUCCESS;
                craftingValuesDictionary.put(craftinglib.OBJVAR_FORCE_CRITICAL_SUCCESS_RESULT, true);
            }
        }
        craftingValuesDictionary.put("assemblySuccessState", successState);
        if (isPlayerQA)
        {
            if (successState >= 0 && successState < craftinglib.STATE_NAMES.length)
            {
                sendSystemMessageTestingOnly(player, "CRAFTING --");
                sendSystemMessageTestingOnly(player, "CRAFTING -- Success State: " + craftinglib.STATE_NAMES[successState]);
                sendSystemMessageTestingOnly(player, "CRAFTING ---------------------------------");
            }
        }
        if (successState >= 0 && successState < craftinglib.STATE_NAMES.length)
        {
            debugServerConsoleMsg(null, "calcSkillDesignAssemblyCheck: success state = " + successState + " (" + craftinglib.STATE_NAMES[successState] + ")");
        }
        else 
        {
            debugServerConsoleMsg(null, "calcSkillDesignAssemblyCheck: success state = " + successState);
        }
        return successState;
    }
    public void calcCraftingDesignStageIngredientAttribAverageValues(dictionary craftingValuesDictionary, draft_schematic.slot[] ingredientSlots, resource_weight[] slotWeights) throws InterruptedException
    {
        if (craftingValuesDictionary == null || ingredientSlots == null)
        {
            return;
        }
        dictionary componentDictionary = new dictionary();
        float[] totalResourceSum = new float[craftinglib.NUM_RESOURCE_ATTRIBS];
        float[] resourceUnits = new float[craftinglib.NUM_RESOURCE_ATTRIBS];
        for (int i = 0; i < ingredientSlots.length; i++)
        {
            if (ingredientSlots[i] == null)
            {
                continue;
            }
            switch (ingredientSlots[i].ingredientType)
            {
                case draft_schematic.IT_item:
                case draft_schematic.IT_template:
                case draft_schematic.IT_templateGeneric:
                debugServerConsoleMsg(null, "getting components for object " + ingredientSlots[i].ingredients[0].ingredient);
                obj_var_list crafting_components = getObjVarList(getSelf(), craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (ingredientSlots[i].name).getAsciiId());
                if (crafting_components != null)
                {
                    int componentCount = crafting_components.getNumItems();
                    debugServerConsoleMsg(null, "got components, count = " + componentCount);
                    LOG("creature_crafting", "Components found: count = " + componentCount);
                    for (int j = 0; j < componentCount; ++j)
                    {
                        obj_var ov = crafting_components.getObjVar(j);
                        if (ov != null)
                        {
                            Object data = ov.getData();
                            if (data instanceof Integer)
                            {
                                debugServerConsoleMsg(null, "setting component attrib " + ov.getName() + " = " + ov.getIntData());
                                LOG("creature_crafting", "Setting component attrib " + ov.getName() + " = (int)" + ov.getIntData());
                                craftingValuesDictionary.put(craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ov.getName(), ov.getIntData());
                            }
                            else if (data instanceof Float)
                            {
                                debugServerConsoleMsg(null, "adding component attrib " + ov.getName() + " = " + ov.getFloatData());
                                LOG("creature_crafting", "Setting component attrib " + ov.getName() + " = (float)" + ov.getFloatData());
                                craftingValuesDictionary.addFloat(craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ov.getName(), ov.getFloatData());
                            }
                            else if (ov instanceof obj_var_list && (ov.getName()).equals(craftinglib.TISSUE_SKILL_MODS))
                            {
                                obj_var_list specialObjvars = (obj_var_list)ov;
                                int[] mod_idx = specialObjvars.getIntArrayObjVar(craftinglib.TISSUE_SKILL_INDEX);
                                int[] mod_val = specialObjvars.getIntArrayObjVar(craftinglib.TISSUE_SKILL_VALUE);
                                int[] dict_mod_idx = null;
                                int[] dict_mod_val = null;
                                dictionary specialDictionary = (dictionary)(componentDictionary.get(craftinglib.TISSUE_SKILL_MODS));
                                if (specialDictionary == null)
                                {
                                    specialDictionary = new dictionary();
                                    dict_mod_idx = new int[6];
                                    dict_mod_val = new int[6];
                                }
                                else 
                                {
                                    dict_mod_idx = specialDictionary.getIntArray(craftinglib.TISSUE_SKILL_INDEX);
                                    dict_mod_val = specialDictionary.getIntArray(craftinglib.TISSUE_SKILL_VALUE);
                                }
                                int count = mod_idx.length;
                                for (int k = 0; k < count; ++k)
                                {
                                    for (int l = 0; l < dict_mod_idx.length; ++l)
                                    {
                                        if (dict_mod_idx[l] == mod_idx[k])
                                        {
                                            dict_mod_val[l] += mod_val[k];
                                            debugServerConsoleMsg(null, "\tadding to existing skill mod: idx " + dict_mod_idx[l] + " = " + dict_mod_val[l]);
                                            break;
                                        }
                                        else if (dict_mod_idx[l] == 0)
                                        {
                                            dict_mod_idx[l] = mod_idx[k];
                                            dict_mod_val[l] = mod_val[k];
                                            debugServerConsoleMsg(null, "\tadding new skill mod: idx " + dict_mod_idx[l] + " = " + dict_mod_val[l]);
                                            break;
                                        }
                                    }
                                }
                                specialDictionary.put(craftinglib.TISSUE_SKILL_INDEX, dict_mod_idx);
                                specialDictionary.put(craftinglib.TISSUE_SKILL_VALUE, dict_mod_val);
                                componentDictionary.put(craftinglib.TISSUE_SKILL_MODS, specialDictionary);
                            }
                            else 
                            {
                                debugServerConsoleMsg(null, "component attrib " + ov.getName() + " has unknown data type");
                                LOG("creature_crafting", "Component attrib " + ov.getName() + " has unknown data type");
                            }
                        }
                    }
                }
                else 
                {
                    LOG("creature_crafting", "In Java craftinglib.scriptlib.calcCraftingDesignStageIngredientAttribAverageValues:" + " component object " + ingredientSlots[i].ingredients[0].ingredient + " does not have the " + craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + " objvar list attached to it");
                }
                obj_var_list crafting_component_resources = getObjVarList(ingredientSlots[i].ingredients[0].ingredient, bio_engineer.COMPONENT_RESOURCE_ATTRIB_OBJVAR_NAME);
                if (crafting_component_resources != null)
                {
                    LOG("creature_crafting", "Components Resources found");
                    int slotWeightIndex = -1;
                    LOG("creature_crafting", "	Current Slot:" + (ingredientSlots[i].name).getAsciiId());
                    for (int j = 0; j < slotWeights.length; j++)
                    {
                        if (((ingredientSlots[i].name).getAsciiId()).equals(slotWeights[j].attribName))
                        {
                            slotWeightIndex = j;
                            break;
                        }
                    }
                    if (slotWeightIndex != -1)
                    {
                        int attribCount = crafting_component_resources.getNumItems();
                        debugServerConsoleMsg(null, "got component resources, attrib count = " + attribCount);
                        LOG("creature_crafting", "Components Resources Attrib count:" + attribCount);
                        for (int j = 0; j < attribCount; ++j)
                        {
                            obj_var ov = crafting_component_resources.getObjVar(j);
                            if (ov != null)
                            {
                                int resourceAttrib = 0;
                                Object data = ov.getData();
                                if (data instanceof Integer)
                                {
                                    resourceAttrib = (((Integer)data)).intValue();
                                }
                                else if (data instanceof Float)
                                {
                                    resourceAttrib = (int)(((Float)data)).floatValue();
                                }
                                else if (data instanceof int[])
                                {
                                    int[] armor_data = (int[])data;
                                    int[] dict_armor_data = null;
                                    dictionary specialDictionary = (dictionary)(componentDictionary.get("dna_data"));
                                    if (specialDictionary == null)
                                    {
                                        specialDictionary = new dictionary();
                                    }
                                    else 
                                    {
                                        dict_armor_data = specialDictionary.getIntArray("armorData");
                                    }
                                    if (dict_armor_data == null)
                                    {
                                        dict_armor_data = new int[10];
                                    }
                                    for (int k = 0; k < dict_armor_data.length; k++)
                                    {
                                        int value = armor_data[k];
                                        if (value == -1)
                                        {
                                            value = -100;
                                        }
                                        dict_armor_data[k] += value * slotWeights[slotWeightIndex].weights[1].weight;
                                    }
                                    specialDictionary.put("armorData", dict_armor_data);
                                    componentDictionary.put("dna_data", specialDictionary);
                                }
                                else 
                                {
                                    LOG("creature_crafting", "Unknown objvar type ");
                                }
                                if ((ov.getName()).startsWith("specialAttack"))
                                {
                                    if (bio_engineer.validateCreatureSpecialAttack(resourceAttrib))
                                    {
                                        dictionary specialDictionary = (dictionary)(componentDictionary.get("dna_data"));
                                        if (specialDictionary == null)
                                        {
                                            specialDictionary = new dictionary();
                                        }
                                        specialDictionary.put((ingredientSlots[i].name).getAsciiId() + "." + ov.getName(), resourceAttrib);
                                        specialDictionary.put((ingredientSlots[i].name).getAsciiId() + ".attackWeight", slotWeights[slotWeightIndex].weights[8].weight);
                                        componentDictionary.put("dna_data", specialDictionary);
                                        LOG("creature_crafting", "specialAttack(" + resourceAttrib + ") found: slot = " + (ingredientSlots[i].name).getAsciiId() + "; weight = " + slotWeights[slotWeightIndex].weights[8].weight);
                                    }
                                }
                                else if ((ov.getName()).equals("rangedWeapon"))
                                {
                                    String weapon = (String)data;
                                    if (weapon != null && !weapon.equals(""))
                                    {
                                        dictionary specialDictionary = (dictionary)(componentDictionary.get("dna_data"));
                                        if (specialDictionary == null)
                                        {
                                            specialDictionary = new dictionary();
                                        }
                                        specialDictionary.put((ingredientSlots[i].name).getAsciiId() + ".rangedWeapon", weapon);
                                        specialDictionary.put((ingredientSlots[i].name).getAsciiId() + ".rangedWeight", slotWeights[slotWeightIndex].weights[5].weight);
                                        componentDictionary.put("dna_data", specialDictionary);
                                        LOG("creature_crafting", "rangedWeapon found: slot = " + (ingredientSlots[i].name).getAsciiId() + "; weight = " + slotWeights[slotWeightIndex].weights[8].weight);
                                    }
                                }
                                else 
                                {
                                    if ((ov.getName()).equals("quality"))
                                    {
                                        dictionary specialDictionary = (dictionary)(componentDictionary.get("dna_data"));
                                        if (specialDictionary == null)
                                        {
                                            specialDictionary = new dictionary();
                                        }
                                        specialDictionary.put((ingredientSlots[i].name).getAsciiId() + ".quality", resourceAttrib);
                                        componentDictionary.put("dna_data", specialDictionary);
                                    }
                                    int weightIndex = -1;
                                    for (int x = 0; x < slotWeights[slotWeightIndex].weights.length; x++)
                                    {
                                        if (bio_engineer.CREATURE_ATTRIB_OBJVAR_NAMES[slotWeights[slotWeightIndex].weights[x].resource].equals(ov.getName()))
                                        {
                                            weightIndex = x;
                                            break;
                                        }
                                    }
                                    int resourceWeight = 1;
                                    if (weightIndex != -1)
                                    {
                                        resourceWeight = slotWeights[slotWeightIndex].weights[weightIndex].weight;
                                    }
                                    debugServerConsoleMsg(null, "adding component resource attrib " + ov.getName() + " = " + resourceAttrib + "; weight = " + resourceWeight);
                                    craftingValuesDictionary.addFloat(bio_engineer.COMPONENT_RESOURCE_ATTRIB_OBJVAR_NAME + "." + ov.getName() + "_sum", (resourceAttrib * resourceWeight));
                                    craftingValuesDictionary.addFloat(bio_engineer.COMPONENT_RESOURCE_ATTRIB_OBJVAR_NAME + "." + ov.getName() + "_weight", resourceWeight);
                                }
                            }
                            else 
                            {
                                LOG("creature_crafting", "No attribute obj_vars found!");
                            }
                        }
                    }
                    else 
                    {
                        LOG("creature_crafting", "Slot weight index for slot:" + (ingredientSlots[i].name).getAsciiId() + ", not found.");
                    }
                }
                break;
                case draft_schematic.IT_resourceType:
                case draft_schematic.IT_resourceClass:
                if (ingredientSlots[i].ingredients.length > 1)
                {
                    debugServerConsoleMsg(null, "ERROR! An amalgam/alloy was detected! Support for amalgams/alloys was ripped out!");
                }
                else 
                {
                    for (int j = 0; j < craftinglib.NUM_RESOURCE_ATTRIBS; ++j)
                    {
                        float resourceAttrib = craftinglib.getResourceAttribute(ingredientSlots[i].ingredients[0].ingredient, craftinglib.RESOURCE_OBJVAR_NAMES[j]);
                        if (resourceAttrib != craftinglib.RESOURCE_NOT_USED)
                        {
                            totalResourceSum[j] += resourceAttrib * ingredientSlots[i].ingredients[0].count;
                            resourceUnits[j] += ingredientSlots[i].ingredients[0].count;
                        }
                    }
                }
                break;
                default:
                break;
            }
        }
        dictionary specialDictionary = (dictionary)(componentDictionary.get("dna_data"));
        int[] armor_data = 
        {
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0
        };
        if (specialDictionary != null)
        {
            int[] temp_armor_data = specialDictionary.getIntArray("armorData");
            if (temp_armor_data != null && temp_armor_data.length == 10)
            {
                armor_data = temp_armor_data;
            }
        }
        else 
        {
            specialDictionary = new dictionary();
        }
        for (int i = 0; i < armor_data.length; i++)
        {
            armor_data[i] = armor_data[i] / 24;
            int cap = 100;
            if (i < 4)
            {
                cap = 60;
            }
            if (armor_data[i] > cap)
            {
                armor_data[i] = cap;
            }
        }
        specialDictionary.put("armorData", armor_data);
        componentDictionary.put("dna_data", specialDictionary);
        int specialAttack1 = 0;
        int specialAttack2 = 0;
        String rangedWeapon = "";
        for (int i = 0; i < bio_engineer.DNA_TEMPLATE_SLOT_NAMES.length; i++)
        {
            int quality = specialDictionary.getInt(bio_engineer.DNA_TEMPLATE_SLOT_NAMES[i] + ".quality");
            int specialAttackWeight = specialDictionary.getInt(bio_engineer.DNA_TEMPLATE_SLOT_NAMES[i] + ".attackWeight");
            int rangedWeaponWeight = specialDictionary.getInt(bio_engineer.DNA_TEMPLATE_SLOT_NAMES[i] + ".rangedWeight");
            int chance = (10 - quality) * 3;
            if (rand(0, chance) < specialAttackWeight)
            {
                if (specialAttack1 == 0)
                {
                    specialAttack1 = specialDictionary.getInt(bio_engineer.DNA_TEMPLATE_SLOT_NAMES[i] + ".specialAttack1");
                }
            }
            if (rand(0, chance) < specialAttackWeight)
            {
                if (specialAttack2 == 0)
                {
                    specialAttack2 = specialDictionary.getInt(bio_engineer.DNA_TEMPLATE_SLOT_NAMES[i] + ".specialAttack2");
                }
            }
            if (rand(0, chance) < rangedWeaponWeight)
            {
                String weapon = specialDictionary.getString(bio_engineer.DNA_TEMPLATE_SLOT_NAMES[i] + ".rangedWeapon");
                if (rangedWeapon.equals(""))
                {
                    if (weapon != null && !weapon.equals(""))
                    {
                        rangedWeapon = weapon;
                    }
                }
            }
        }
        LOG("creature_crafting", "specialAttack1 = " + specialAttack1);
        LOG("creature_crafting", "specialAttack2 = " + specialAttack2);
        LOG("creature_crafting", "rangedWeapon = " + rangedWeapon);
        specialDictionary.put("specialAttack1", specialAttack1);
        specialDictionary.put("specialAttack2", specialAttack2);
        specialDictionary.put("rangedWeapon", rangedWeapon);
        componentDictionary.put("dna_data", specialDictionary);
        float[] itemTotalResourceAverage = new float[craftinglib.NUM_RESOURCE_ATTRIBS];
        for (int i = 0; i < craftinglib.NUM_RESOURCE_ATTRIBS; ++i)
        {
            if (resourceUnits[i] != 0)
            {
                itemTotalResourceAverage[i] = totalResourceSum[i] / resourceUnits[i];
            }
            else 
            {
                itemTotalResourceAverage[i] = 0;
            }
        }
        craftingValuesDictionary.put(craftinglib.RESOURCE_AVERAGE_DICTIONARY_NAME, itemTotalResourceAverage);
        for (int i = 0; i < bio_engineer.CREATURE_ATTRIB_NUM; ++i)
        {
            float resourceSum = craftingValuesDictionary.getFloat(bio_engineer.COMPONENT_RESOURCE_ATTRIB_OBJVAR_NAME + "." + bio_engineer.CREATURE_ATTRIB_OBJVAR_NAMES[i] + "_sum");
            float resourceWeight = craftingValuesDictionary.getFloat(bio_engineer.COMPONENT_RESOURCE_ATTRIB_OBJVAR_NAME + "." + bio_engineer.CREATURE_ATTRIB_OBJVAR_NAMES[i] + "_weight");
            float resourceAverage = (float)resourceSum / resourceWeight;
            if (resourceAverage != 0)
            {
                craftingValuesDictionary.put(bio_engineer.COMPONENT_RESOURCE_ATTRIB_OBJVAR_NAME + "." + bio_engineer.CREATURE_ATTRIB_OBJVAR_NAMES[i] + "_average", resourceAverage);
                LOG("creature_crafting", "Component Resource Attrib: " + bio_engineer.CREATURE_ATTRIB_OBJVAR_NAMES[i] + ", Slot Weighted Average: " + resourceAverage);
            }
        }
        craftingValuesDictionary.put(craftinglib.RESOURCE_AVERAGE_DICTIONARY_NAME, itemTotalResourceAverage);
        utils.saveDictionaryAsObjVar(getSelf(), craftinglib.COMPONENT_ATTRIBUTE_INTERNAL_OBJVAR_NAME, componentDictionary);
    }
    public void calcResourceMaxItemAttributes(dictionary craftingValuesDictionary, draft_schematic.attribute[] itemAttributes, resource_weight[] weights) throws InterruptedException
    {
        calcResourceMaxItemAttributes(null, craftingValuesDictionary, itemAttributes, weights);
    }
    public void calcResourceMaxItemAttributes(obj_id player, dictionary craftingValuesDictionary, draft_schematic.attribute[] itemAttributes, resource_weight[] weights) throws InterruptedException
    {
        int assemblySuccessState = craftingValuesDictionary.getInt("assemblySuccessState");
        float[] averageResourceValues = craftingValuesDictionary.getFloatArray(craftinglib.RESOURCE_AVERAGE_DICTIONARY_NAME);
        float itemAttributeApplicableResourceValueAverage = 0;
        for (int i = 0; i < itemAttributes.length; ++i)
        {
            if (itemAttributes[i] == null || itemAttributes[i].minValue == itemAttributes[i].maxValue)
            {
                continue;
            }
            if ((itemAttributes[i].name).equals("complexity"))
            {
                itemAttributes[i].currentValue = craftingValuesDictionary.getFloat("itemCurrentComplexity");
            }
            else 
            {
                float lowAttributeRangeValue = itemAttributes[i].minValue;
                float highAttributeRangeValue = itemAttributes[i].maxValue;
                float attributeValueRangeSize = highAttributeRangeValue - lowAttributeRangeValue;
                float onePercentOfAttributeRange = attributeValueRangeSize * 0.01f;
                for (int j = 0; j < weights.length; ++j)
                {
                    if ((itemAttributes[i].name).equals(weights[j].attribName))
                    {
                        debugServerConsoleMsg(null, "**** craftinglib.scriptlib attrib " + itemAttributes[i].name);
                        String onePercentName = (itemAttributes[i].name).getAsciiId() + "OnePercent";
                        if (craftingValuesDictionary.containsKey(bio_engineer.COMPONENT_RESOURCE_ATTRIB_OBJVAR_NAME + "." + (itemAttributes[i].name).getAsciiId() + "_average"))
                        {
                            itemAttributeApplicableResourceValueAverage = computeComponentWeightedAverage(craftingValuesDictionary, weights[j].weights) / 10.0f;
                            LOG("creature_crafting", "Calculated Attribute: " + bio_engineer.COMPONENT_RESOURCE_ATTRIB_OBJVAR_NAME + "." + (itemAttributes[i].name).getAsciiId());
                            LOG("creature_crafting", "\tAdjusted Component Weighted Average: " + itemAttributeApplicableResourceValueAverage);
                        }
                        else 
                        {
                            itemAttributeApplicableResourceValueAverage = craftinglib.computeWeightedAverage(averageResourceValues, weights[j].weights) / 10.0f;
                        }
                        if (isIdValid(player))
                        {
                            itemAttributeApplicableResourceValueAverage += craftinglib.getCraftingInspirationBonus(player, craftingValuesDictionary, itemAttributeApplicableResourceValueAverage, 1f);
                            if (itemAttributeApplicableResourceValueAverage > 100.0f)
                            {
                                itemAttributeApplicableResourceValueAverage = 100.0f;
                            }
                        }
                        if (itemAttributeApplicableResourceValueAverage < 1.0f)
                        {
                            itemAttributeApplicableResourceValueAverage = 1.0f;
                        }
                        float resourceMaxRange = onePercentOfAttributeRange * itemAttributeApplicableResourceValueAverage;
                        itemAttributes[i].resourceMaxValue = lowAttributeRangeValue + resourceMaxRange;
                        itemAttributes[i].currentValue = lowAttributeRangeValue;
                        craftingValuesDictionary.put(onePercentName, resourceMaxRange / 100.0f);
                        LOG("creature_crafting", "\tResource Max Range: " + resourceMaxRange + ", Resource Max Value: " + itemAttributes[i].resourceMaxValue);
                        debugServerConsoleMsg(null, "**** craftinglib.scriptlib calcResourceMaxItemAttributes 1% = " + onePercentOfAttributeRange + ", average = " + itemAttributeApplicableResourceValueAverage + ", resourceMaxRange = " + resourceMaxRange + ", resourceMaxValue = " + itemAttributes[i].resourceMaxValue);
                        break;
                    }
                }
            }
        }
    }
    public void calcPostAssemblyResourceSetAttribValues(dictionary craftingValuesDictionary, draft_schematic.attribute[] itemAttributes, resource_weight[] weights) throws InterruptedException
    {
        float[] averageResourceValues = craftingValuesDictionary.getFloatArray(craftinglib.RESOURCE_AVERAGE_DICTIONARY_NAME);
        float averagedAttributeSum = 0;
        debugServerConsoleMsg(null, "Setting attribs for resources:");
        for (int i = 0; i < itemAttributes.length; ++i)
        {
            if (itemAttributes[i] == null || itemAttributes[i].minValue == itemAttributes[i].maxValue)
            {
                continue;
            }
            if (!(itemAttributes[i].name).equals("complexity"))
            {
                for (int j = 0; j < weights.length; ++j)
                {
                    if ((itemAttributes[i].name).equals(weights[j].attribName))
                    {
                        float onePercentOfPossibleAttribute = craftingValuesDictionary.getFloat(weights[j].attribName + "OnePercent");
                        if (craftingValuesDictionary.containsKey(bio_engineer.COMPONENT_RESOURCE_ATTRIB_OBJVAR_NAME + "." + (itemAttributes[i].name).getAsciiId() + "_average"))
                        {
                            averagedAttributeSum = computeComponentWeightedAverage(craftingValuesDictionary, weights[j].weights);
                        }
                        else 
                        {
                            averagedAttributeSum = craftinglib.computeWeightedAverage(averageResourceValues, weights[j].weights);
                        }
                        float resourceContribution = averagedAttributeSum * (100.0f / 1000.0f);
                        if (resourceContribution < 1.0f)
                        {
                            resourceContribution = 1.0f;
                        }
                        float attributeResourceValue = onePercentOfPossibleAttribute * resourceContribution;
                        itemAttributes[i].currentValue += attributeResourceValue;
                        debugServerConsoleMsg(null, "\t" + (itemAttributes[i].name).getAsciiId() + " += " + attributeResourceValue);
                        break;
                    }
                }
            }
        }
    }
    public int calcAssemblyPhaseAttributes(obj_id player, draft_schematic.slot[] itemIngredients, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary, String[] playerSkillMods, resource_weight[] maxResourceAttribWeights, resource_weight[] resourceAttribWeights) throws InterruptedException
    {
        debugServerConsoleMsg(null, "Beginning custom dna assembly-phase attribute resolution");
        calcCraftingDesignStageIngredientAttribAverageValues(craftingValuesDictionary, itemIngredients, getSlotResourceWeights());
        int success = calcSkillDesignAssemblyCheck(player, craftingValuesDictionary, playerSkillMods);
        if (success == craftinglib.STATE_CRITICAL_FAILURE || success == craftinglib.STATE_CRITICAL_FAILURE_NODESTROY)
        {
            return success;
        }
        calcResourceMaxItemAttributes(player, craftingValuesDictionary, itemAttributes, maxResourceAttribWeights);
        calcPostAssemblyResourceSetAttribValues(craftingValuesDictionary, itemAttributes, resourceAttribWeights);
        craftinglib.calcPostSkillAssemblyCheckSetAttribValues(craftingValuesDictionary, itemAttributes);
        obj_id self = getSelf();
        float complexity = 0;
        int quality = 0;
        draft_schematic.attribute xpAttrib = null;
        dictionary creatureDict = new dictionary();
        int[] dna_attributes = 
        {
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0
        };
        for (int i = 0; i < itemAttributes.length; ++i)
        {
            if (itemAttributes[i] == null)
            {
                continue;
            }
            if ((itemAttributes[i].name).equals("complexity"))
            {
                complexity = itemAttributes[i].currentValue;
            }
            else if ((itemAttributes[i].name).equals("quality"))
            {
                quality = (int)itemAttributes[i].currentValue;
            }
            else if ((itemAttributes[i].name).equals("hardiness"))
            {
                dna_attributes[bio_engineer.CREATURE_ATTRIB_HARDINESS] = (int)(itemAttributes[i].currentValue);
            }
            else if ((itemAttributes[i].name).equals("fortitude"))
            {
                dna_attributes[bio_engineer.CREATURE_ATTRIB_FORTITUDE] = (int)(itemAttributes[i].currentValue);
            }
            else if ((itemAttributes[i].name).equals("dexterity"))
            {
                dna_attributes[bio_engineer.CREATURE_ATTRIB_DEXTERITY] = (int)(itemAttributes[i].currentValue);
            }
            else if ((itemAttributes[i].name).equals("endurance"))
            {
                dna_attributes[bio_engineer.CREATURE_ATTRIB_ENDURANCE] = (int)(itemAttributes[i].currentValue);
            }
            else if ((itemAttributes[i].name).equals("intellect"))
            {
                dna_attributes[bio_engineer.CREATURE_ATTRIB_INTELLECT] = (int)(itemAttributes[i].currentValue);
            }
            else if ((itemAttributes[i].name).equals("cleverness"))
            {
                dna_attributes[bio_engineer.CREATURE_ATTRIB_CLEVERNESS] = (int)(itemAttributes[i].currentValue);
            }
            else if ((itemAttributes[i].name).equals("dependability"))
            {
                dna_attributes[bio_engineer.CREATURE_ATTRIB_DEPENDABILITY] = (int)(itemAttributes[i].currentValue);
            }
            else if ((itemAttributes[i].name).equals("courage"))
            {
                dna_attributes[bio_engineer.CREATURE_ATTRIB_COURAGE] = (int)(itemAttributes[i].currentValue);
            }
            else if ((itemAttributes[i].name).equals("fierceness"))
            {
                dna_attributes[bio_engineer.CREATURE_ATTRIB_FIERCENESS] = (int)(itemAttributes[i].currentValue);
            }
            else if ((itemAttributes[i].name).equals("power"))
            {
                dna_attributes[bio_engineer.CREATURE_ATTRIB_POWER] = (int)(itemAttributes[i].currentValue);
            }
            else if ((itemAttributes[i].name).equals("xp"))
            {
                xpAttrib = itemAttributes[i];
            }
        }
        creatureDict.put("maxHealth", bio_engineer.calcCreatureAttrib(0, dna_attributes));
        creatureDict.put("healthRegen", bio_engineer.calcCreatureHealthRegen(dna_attributes));
        creatureDict.put("toHitChance", bio_engineer.calcCreatureToHit(dna_attributes));
        creatureDict.put("defenseValue", bio_engineer.calcCreatureDefenseValue(dna_attributes));
        creatureDict.put("stateResist", bio_engineer.calcCreatureStateResist(dna_attributes));
        creatureDict.put("scale", bio_engineer.calcCreatureMaxScale(dna_attributes));
        int damage[] = bio_engineer.calcCreatureDamage(dna_attributes);
        creatureDict.put("minDamage", damage[0]);
        creatureDict.put("maxDamage", damage[1]);
        int armorBase = bio_engineer.calcCreatureArmor(dna_attributes);
        creatureDict.put("armorRating", (int)(armorBase / 50));
        creatureDict.put("armorEffectiveness", armorBase - (((int)(armorBase / 50)) * 50));
        creatureDict.put("critChance", bio_engineer.calcCreatureCritChance(dna_attributes));
        creatureDict.put("critSave", bio_engineer.calcCreatureCritSave(dna_attributes));
        creatureDict.put("aggroBonus", bio_engineer.calcCreatureAggroBonus(dna_attributes));
        int level = bio_engineer.getCraftedCreatureLevel(creatureDict);
        if (xpAttrib != null)
        {
            float numSlotsFilled = 0;
            if (itemIngredients != null)
            {
                numSlotsFilled = itemIngredients.length;
            }
            xpAttrib.currentValue = bio_engineer.grantTemplateCraftingExperience(level);
            LOG("creature_crafting", "XP granted = " + xpAttrib.currentValue + "; level(" + level + ")");
            debugServerConsoleMsg(null, "craftinglib.scriptlib calcAssemblyPhaseAttributes complexity = " + complexity + ", slots filled = " + numSlotsFilled + ", xp = " + xpAttrib.currentValue);
        }
        else 
        {
            debugServerConsoleMsg(null, "craftinglib.scriptlib calcAssemblyPhaseAttributes no xp attrib defined!");
        }
        return success;
    }
    public int OnManufacturingSchematicCreation(obj_id self, obj_id player, obj_id prototype, draft_schematic schematic, modifiable_int assemblyResult, modifiable_int experimentPoints) throws InterruptedException
    {
        debugServerConsoleMsg(self, "OnManufacturingSchematicCreation enter");
        dictionary craftingValuesDictionary = new dictionary();
        draft_schematic.slot[] slots = schematic.getSlots();
        draft_schematic.attribute[] objectAttribs = schematic.getAttribs();
        draft_schematic.attribute[] experimentalAttribs = schematic.getExperimentalAttribs();
        String[] obj_attributes = new String[objectAttribs.length];
        for (int i = 0; i < obj_attributes.length; ++i)
        {
            obj_attributes[i] = (objectAttribs[i].name).getAsciiId();
        }
        debugServerConsoleMsg(self, "Ingredient slot info (" + slots.length + " slots):");
        for (int i = 0; i < slots.length; ++i)
        {
            debugServerConsoleMsg(self, "\tslot " + slots[i].name + ", ingredient " + slots[i].ingredients[0].ingredient + ", amount = " + slots[i].ingredients[0].count);
        }
        debugServerConsoleMsg(self, "Item attribute info (" + objectAttribs.length + " attribs):");
        craftingValuesDictionary.put("craftingType", schematic.getCategory());
        for (int i = 0; i < objectAttribs.length; ++i)
        {
            if (((objectAttribs[i].name).getAsciiId()).equals("complexity"))
            {
                craftingValuesDictionary.put("itemDefaultComplexity", objectAttribs[i].minValue);
                craftingValuesDictionary.put("itemCurrentComplexity", objectAttribs[i].currentValue);
            }
            else if (((objectAttribs[i].name).getAsciiId()).equals("armorSpecialType"))
            {
                craftingValuesDictionary.put("itemDefaultArmorSpecialType", objectAttribs[i].minValue);
                craftingValuesDictionary.put("itemCurrentArmorSpecialType", objectAttribs[i].currentValue);
            }
            else if (((objectAttribs[i].name).getAsciiId()).equals("sockets"))
            {
                int[] mods = getEnhancedSkillStatisticModifiers(player, getExperimentSkillMods());
                if (mods != null)
                {
                    int experimentModTotal = 0;
                    for (int j = 0; j < mods.length; ++j)
                    {
                        experimentModTotal += mods[j];
                    }
                    if (experimentModTotal > craftinglib.socketThreshold)
                    {
                        int sockets = 0;
                        int chances = 1 + (experimentModTotal - craftinglib.socketThreshold) / craftinglib.socketDelta;
                        for (int j = 0; j < chances; ++j)
                        {
                            if (rand(1, 100) > craftinglib.socketChance)
                            {
                                ++sockets;
                            }
                        }
                        if (sockets > craftinglib.maxSockets)
                        {
                            sockets = craftinglib.maxSockets;
                        }
                        objectAttribs[i].minValue = sockets;
                        objectAttribs[i].maxValue = sockets;
                        objectAttribs[i].currentValue = sockets;
                        if (sockets > 0)
                        {
                            setCondition(prototype, CONDITION_MAGIC_ITEM);
                        }
                    }
                }
            }
            debugServerConsoleMsg(self, "\tattrib " + objectAttribs[i].name + ", values = " + objectAttribs[i].minValue + ".." + objectAttribs[i].maxValue);
        }
        grantExperience(player, slots);
        int forceCritSuccessCount = getIntObjVar(self, craftinglib.OBJVAR_FORCE_CRITICAL_SUCCESS);
        if (forceCritSuccessCount > 0)
        {
            craftingValuesDictionary.put(craftinglib.OBJVAR_FORCE_CRITICAL_SUCCESS, true);
        }
        int assemblySuccess = calcAssemblyPhaseAttributes(player, slots, objectAttribs, craftingValuesDictionary, getAssemblySkillMods(), getResourceMaxResourceWeights(), getAssemblyResourceWeights());
        assemblyResult.set(assemblySuccess);
        if (assemblySuccess == craftinglib.STATE_CRITICAL_FAILURE || assemblySuccess == craftinglib.STATE_CRITICAL_FAILURE_NODESTROY)
        {
            return SCRIPT_CONTINUE;
        }
        debugServerConsoleMsg(self, "OnManufacturingSchematicCreation calling craftinglib.calcExperimentalAttribs");
        craftinglib.calcExperimentalAttribs(schematic);
        debugServerConsoleMsg(self, "OnManufacturingSchematicCreation return from craftinglib.calcExperimentalAttribs");
        float resourceMalleabilitySkillMod = craftingValuesDictionary.getFloat(craftinglib.RESOURCE_MALLEABILITY_SKILL_MOD);
        utils.setScriptVar(self, craftinglib.RESOURCE_MALLEABILITY_SKILL_MOD, resourceMalleabilitySkillMod);
        experimentPoints.set(craftinglib.calcExperimentPoints(player, getExperimentSkillMods()));
        debugServerConsoleMsg(self, "OnManufacturingSchematicCreation setting current attributes");
        if (setSchematicAttributes(self, objectAttribs))
        {
            debugServerConsoleMsg(self, "OnManufacturingSchematicCreation set initial attributes of schematic");
        }
        else 
        {
            debugServerConsoleMsg(self, "OnManufacturingSchematicCreation failed to set initial attributes of schematic!");
        }
        debugServerConsoleMsg(self, "OnManufacturingSchematicCreation setting experimental attributes");
        if (setSchematicExperimentalAttributes(self, experimentalAttribs))
        {
            debugServerConsoleMsg(self, "OnManufacturingSchematicCreation set experimental attributes of schematic");
        }
        else 
        {
            debugServerConsoleMsg(self, "OnManufacturingSchematicCreation failed to set experimental attributes of schematic!");
        }
        craftinglib.calcPerExperimentationCheckMod(player, craftingValuesDictionary, 0, objectAttribs, getExperimentSkillMods());
        setSchematicExperimentMod(self, craftingValuesDictionary.getFloat("totalExperimentPointModifier"));
        if (slots != null && slots.length > 0)
        {
            String[] appearances = getAppearances(player, slots);
            if (appearances != null && appearances.length > 0)
            {
                setSchematicAppearances(self, appearances);
            }
        }
        debugServerConsoleMsg(self, "OnManufacturingSchematicCreation getting customization data");
        draft_schematic.custom[] customizations = schematic.getCustomizations();
        if (customizations != null && customizations.length > 0)
        {
            debugServerConsoleMsg(self, "OnManufacturingSchematicCreation calling getCustomizations");
            customizations = getCustomizations(player, customizations);
            if (customizations != null && customizations.length > 0)
            {
                setSchematicCustomizations(self, customizations);
            }
        }
        else 
        {
            if (customizations == null)
            {
                debugServerConsoleMsg(self, "OnManufacturingSchematicCreation customizations == null");
            }
            else 
            {
                debugServerConsoleMsg(self, "OnManufacturingSchematicCreation customizations.length == 0");
            }
        }
        calcAndSetPrototypeProperties(prototype, objectAttribs, craftingValuesDictionary);
        craftinglib.storeTissueDataAsObjvars(craftingValuesDictionary, objectAttribs, prototype, true);
        String root_internal = craftinglib.COMPONENT_ATTRIBUTE_INTERNAL_OBJVAR_NAME + ".";
        String root = craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".";
        int[] armor_data = getIntArrayObjVar(self, root_internal + "dna_data.armorData");
        if (armor_data != null && armor_data.length > 0)
        {
            setObjVar(self, root + "armorData", armor_data);
        }
        int quality = (getIntObjVar(self, root_internal + "dna_data.physique_profile.quality") + getIntObjVar(self, root_internal + "dna_data.prowess_profile.quality") + getIntObjVar(self, root_internal + "dna_data.mental_profile.quality") + getIntObjVar(self, root_internal + "dna_data.psychological_profile.quality") + getIntObjVar(self, root_internal + "dna_data.aggression_profile.quality")) / 5;
        setObjVar(self, root + "quality", quality);
        int specialAttack1 = getIntObjVar(self, root_internal + "dna_data.specialAttack1");
        setObjVar(self, root + "specialAttack1", specialAttack1);
        int specialAttack2 = getIntObjVar(self, root_internal + "dna_data.specialAttack2");
        setObjVar(self, root + "specialAttack2", specialAttack2);
        String rangedWeapon = getStringObjVar(self, root_internal + "dna_data.rangedWeapon");
        if (rangedWeapon == null)
        {
            rangedWeapon = "";
        }
        setObjVar(self, root + "rangedWeapon", rangedWeapon);
        calcAndSetPrototypeProperties(prototype, objectAttribs);
        return SCRIPT_CONTINUE;
    }
}
