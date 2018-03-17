package script.library;

import script.*;

import java.util.Vector;

public class craftinglib extends script.base_script
{
    public craftinglib()
    {
    }
    public static final String VERSION = "v0.00.00";
    public static final float COMPLEXITY_INCREMENT_MOD = 3.0f;
    public static final float RESOURCE_NOT_USED = -9999;
    public static final String COMPONENT_ATTRIBUTE_OBJVAR_NAME = "crafting_components";
    public static final String COMPONENT_ATTRIBUTE_INTERNAL_OBJVAR_NAME = "crafting_components_internal";
    public static final String OBJVAR_COMPONENT_ATTRIB_BONUSES = "attrib_bonuses";
    public static final String RESOURCE_AVERAGE_DICTIONARY_NAME = "itemTotalResourceAverage";
    public static final String SLOT_RESOURCE_DICTIONARY_NAME = "slotResourceAverage";
    public static final String RESOURCE_MALLEABILITY_SKILL_MOD = "resourceMalleabilitySkillMod";
    public static final float NUMERIC_RANGE_EXPRESSION = 10000.0f;
    public static final float BASE_RANGE = 100.0f;
    public static final float EXPERIENCE_GRANTED_MULTIPLIER = 3.0f;
    public static final int socketThreshold = 60;
    public static final int socketDelta = 10;
    public static final int maxSockets = 1;
    public static final int socketChance = 50;
    public static final float STATION_AREA = 20.0f;
    public static final String OBJVAR_STATION = "crafting.station";
    public static final String OBJVAR_PRIVATE_STATION = "crafting.private";
    public static final String OBJVAR_CRAFTER = "crafting.crafter";
    public static final String OBJVAR_CRAFTING_TYPE = "crafting.type";
    public static final String OBJVAR_PROTOTYPE_START = "crafting.prototypeStartTime";
    public static final String OBJVAR_PROTOTYPE_TIME = "crafting.prototypeTime";
    public static final String OBJVAR_CRAFTING_PROTOTYPE_OBJECT = "crafting.prototypeObject";
    public static final String OBJVAR_CRAFTING_PROTOTYPE_CRAFTER = "crafting.prototypeCrafter";
    public static final String OBJVAR_PACKED_CRAFTING_VALUES_DICTIONARY = "crafting.valuesDictionary";
    public static final String OBJVAR_CRAFTING_STATION_BONUS = "crafting.stationMod";
    public static final String OBJVAR_SKILLMOD_BONUS = "skillmod.bonus";
    public static final String OBJVAR_REFACTOR = "crafting.refactor";
    public static final String OBJVAR_FORCE_CRITICAL_ASSEMBLY = "crafting.force_critical_assembly";
    public static final String OBJVAR_FORCE_CRITICAL_EXPERIMENT = "crafting.force_critical_experiment";
    public static final String OBJVAR_FORCE_CRITICAL_SUCCESS = "crafting.force_critical_success";
    public static final String OBJVAR_FORCE_CRITICAL_SUCCESS_RESULT = "crafting.force_critical_success_result";
    public static final String OBJVAR_NO_CRIT_FAIL = "crafting.no_critical_fail";
    public static final String OBJVAR_IN_CRAFTING_SESSION = "crafting.inCraftingSession";
    public static final float EXPERIMENTATION_EFFECTIVENESS_MULTIPLIER = 1.0f;
    public static final String EXPERIENCE_NAME_RESOURCE_ORGANIC = "resource_harvesting_organic";
    public static final String EXPERIENCE_NAME_RESOURCE_INORGANIC = "resource_harvesting_inorganic";
    public static final float EXPERIENCE_PER_RESOURCE = 1.0f;
    public static final int STATE_UNSET = -1;
    public static final int STATE_CRITICAL_SUCCESS = 0;
    public static final int STATE_GREAT_SUCCESS = 1;
    public static final int STATE_GOOD_SUCCESS = 2;
    public static final int STATE_MODERATE_SUCCESS = 3;
    public static final int STATE_SUCCESS = 4;
    public static final int STATE_FAILURE = 5;
    public static final int STATE_MODERATE_FAILURE = 6;
    public static final int STATE_BIG_FAILURE = 7;
    public static final int STATE_CRITICAL_FAILURE = 8;
    public static final int STATE_CRITICAL_FAILURE_NODESTROY = 9;
    public static final int MAX_SUCCESS_STATE = 9;
    
    public static final String[] STATE_NAMES = 
    {
        "crit success",
        "great success",
        "good success",
        "mod success",
        "success",
        "fail",
        "mod fail",
        "big fail",
        "crit fail",
        "crit fail (nodestroy)"
    };

    public static final int RESOURCE_BULK = 0;
    public static final int RESOURCE_COLD_RESIST = 1;
    public static final int RESOURCE_CONDUCTIVITY = 2;
    public static final int RESOURCE_DECAY_RESIST = 3;
    public static final int RESOURCE_HEAT_RESIST = 4;
    public static final int RESOURCE_FLAVOR = 5;
    public static final int RESOURCE_MALLEABILITY = 6;
    public static final int RESOURCE_POTENTIAL_ENERGY = 7;
    public static final int RESOURCE_QUALITY = 8;
    public static final int RESOURCE_SHOCK_RESIST = 9;
    public static final int RESOURCE_TOUGHNESS = 10;
    public static final int RESOURCE_VOLUME = 11;
    public static final int NUM_RESOURCE_ATTRIBS = 12;
    public static final int SLOT_WEIGHT_SLOT_01 = 100;
    public static final int SLOT_WEIGHT_SLOT_02 = 101;
    public static final int SLOT_WEIGHT_SLOT_03 = 102;
    public static final int SLOT_WEIGHT_SLOT_04 = 103;
    public static final int SLOT_WEIGHT_SLOT_05 = 104;
    public static final int SLOT_WEIGHT_SLOT_06 = 105;
    public static final int SLOT_WEIGHT_SLOT_07 = 106;
    public static final int SLOT_WEIGHT_SLOT_08 = 107;
    public static final String[] RESOURCE_OBJVAR_NAMES = 
    {
        "res_bulk",
        "res_cold_resist",
        "res_conductivity",
        "res_decay_resist",
        "res_heat_resist",
        "res_flavor",
        "res_malleability",
        "res_potential_energy",
        "res_quality",
        "res_shock_resistance",
        "res_toughness",
        "res_volume"
    };
    public static final String TISSUE_SKILL_MODS = "tissue_skill_mods";
    public static final String TISSUE_SKILL_INDEX = "tissue_skill_index";
    public static final String TISSUE_SKILL_VALUE = "tissue_skill_value";
    public static final string_id SID_INSPIRE_BONUS = new string_id("performance", "perform_inspire_crafting_bonus");
    public static final String TABLE_SCHEMATIC_GROUPS = "datatables/crafting/schematic_group.iff";
    public static final String SKILL_MOD_2_BONUS = "skillmod2.bonus.";
    public static final float RE_POWER_MODIFIER = 0.40f;
    public static final String OBJVAR_RE_STAT_MODIFIED = "crafting.re_stat_modified";
    public static final String OBJVAR_RE_RATIO = "crafting.re_ratio";
    public static final String OBJVAR_RE_VALUE = "crafting.re_value";
    public static final String SCRIPTVAR_CELL_STATIONS = "crafting.stations";
    public static final int OBJVAR_SCHEM_VERSION = 1;
    public static final int STATION_TYPE_FOOD = 8644;
    public static final int STATION_TYPE_FOOD_AND_STRUCTURE = 10180;
    public static final int STATION_TYPE_ARMOR = 10;
    public static final int STATION_TYPE_STRUCTURE = 1536;
    public static final int STATION_TYPE_WEAPON = 526369;
    public static final int STATION_TYPE_WEAPON_TOOL = 524321;
    public static final int STATION_TYPE_SPACE = 393216;
    public static final string_id RESEARCH_CENTER_MESSAGE = new string_id("city/city", "research_center_message");
    public static final string_id MANUFACTURING_CENTER_MESSAGE = new string_id("city/city", "manufacturing_center_message");
    public static float calcResourceQualitySkillMod(dictionary craftingValuesDictionary) throws InterruptedException
    {
        if (craftingValuesDictionary == null)
        {
            return 0;
        }
        float[] resources = craftingValuesDictionary.getFloatArray(RESOURCE_AVERAGE_DICTIONARY_NAME);
        if (resources == null || resources.length != NUM_RESOURCE_ATTRIBS)
        {
            LOG("crafting", "Java craftinglib.scriptlib::calcResourceQualitySkillMod - no resource average array!");
            return 0;
        }
        float averageResourceQuality = resources[RESOURCE_QUALITY];
        float objectQualityMultiMod = (averageResourceQuality - 500) / 200;
        float resourceQualitySkillMod = objectQualityMultiMod * 5;
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
        return resourceQualitySkillMod;
    }
    public static float calcItemComplexitySkillMod(dictionary craftingValuesDictionary) throws InterruptedException
    {
        if (craftingValuesDictionary == null)
        {
            return 0;
        }
        float itemDefaultComplexity = craftingValuesDictionary.getFloat("itemDefaultComplexity");
        float itemCurrentComplexity = craftingValuesDictionary.getFloat("itemCurrentComplexity");
        float itemComplexitySubMod = itemCurrentComplexity - itemDefaultComplexity;
        float itemComplexitySkillMod = 0;
        if (itemComplexitySubMod > 0)
        {
            itemComplexitySkillMod = itemComplexitySubMod * COMPLEXITY_INCREMENT_MOD;
        }
        debugServerConsoleMsg(null, "The itemComplexitySkillMod is " + itemComplexitySkillMod);
        return itemComplexitySkillMod;
    }
    public static float calcResourceMalleabilitySkillMod(dictionary craftingValuesDictionary) throws InterruptedException
    {
        if (craftingValuesDictionary == null)
        {
            return 0;
        }
        float[] resources = craftingValuesDictionary.getFloatArray(RESOURCE_AVERAGE_DICTIONARY_NAME);
        if (resources == null || resources.length != NUM_RESOURCE_ATTRIBS)
        {
            LOG("crafting", "Java craftinglib.scriptlib::calcResourceMalleabilitySkillMod - no resource average array!");
            return 0;
        }
        float averageResourceMalleability = resources[RESOURCE_MALLEABILITY];
        float objectMalleabilityMultiMod = (averageResourceMalleability - 500) / 200;
        float resourceMalleabilitySkillMod = objectMalleabilityMultiMod * 5;
        debugServerConsoleMsg(null, "The resourceMalleabilitySkillMod is " + resourceMalleabilitySkillMod);
        craftingValuesDictionary.put(RESOURCE_MALLEABILITY_SKILL_MOD, resourceMalleabilitySkillMod);
        return resourceMalleabilitySkillMod;
    }
    public static float getAttributeResourceMod(float averagedAttributeSum) throws InterruptedException
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
    public static float computeWeightedAverage(float[] resourceValues, resource_weight.weight[] weights) throws InterruptedException
    {
        float total = 0;
        int count = 0;
        for (int i = 0; i < weights.length; ++i)
        {
            debugServerConsoleMsg(null, "@@@@@craftinglib.scriptlib computeWeightedAverage, resource = " + weights[i].resource + ", value = " + resourceValues[weights[i].resource] + ", weight = " + weights[i].weight);
            total += resourceValues[weights[i].resource] * weights[i].weight;
            count += weights[i].weight;
            debugServerConsoleMsg(null, "\t@@@@@craftinglib.scriptlib computeWeightedAverage, total = " + total + ", count = " + count);
        }
        if (count != 0)
        {
            return total / count;
        }
        return 0;
    }
    public static int calcSkillDesignAssemblyCheck(obj_id player, dictionary craftingValuesDictionary, String[] skills) throws InterruptedException
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
        float deltaMod = NUMERIC_RANGE_EXPRESSION / BASE_RANGE;
        if (!isIdValid(player) || craftingValuesDictionary == null)
        {
            CustomerServiceLog("Crafting", "VALIDATION FAILED -- Assembly returned a critical failure because player object ID or crafting dictionary was null");
            return STATE_CRITICAL_FAILURE;
        }
        int successState = STATE_UNSET;
        float dieRoll = rand(1, (int)NUMERIC_RANGE_EXPRESSION);
        float dieRollMod = 0f;
        float itemComplexitySkillMod = calcItemComplexitySkillMod(craftingValuesDictionary);
        float resourceMalleabilitySkillMod = calcResourceMalleabilitySkillMod(craftingValuesDictionary);
        float resourceQualitySkillMod = calcResourceQualitySkillMod(craftingValuesDictionary);
        if (craftingValuesDictionary.containsKey(OBJVAR_REFACTOR))
        {
            itemComplexitySkillMod = 0;
        }
        float craftingStationMod = 0;
        obj_id stationId = getCraftingStation(player);
        if (!isIdValid(stationId))
        {
            stationId = getSelf();
        }
        if (isIdValid(stationId) && exists(stationId) && hasObjVar(stationId, OBJVAR_CRAFTING_STATION_BONUS))
        {
            craftingStationMod = getFloatObjVar(stationId, OBJVAR_CRAFTING_STATION_BONUS) / 10;
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
            sendSystemMessage(player, new string_id("city/city", "manufacturing_center_message"));
            if (isPlayerQA)
            {
                sendSystemMessageTestingOnly(player, "CRAFTING -- 10% raw random bonus for industry city");
            }
        }
        dieRollMod += getCraftingInspirationBonus(player, craftingValuesDictionary, dieRoll, 5f);
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
        float criticalSuccess = NUMERIC_RANGE_EXPRESSION * (0.95f - criticalAdjust);
        float criticalFailure = NUMERIC_RANGE_EXPRESSION * (0.05f - criticalAdjust);
        float skillMod = getEnhancedSkillStatisticModifier(player, "force_failure_reduction");
        skillMod *= 0.005f;
        criticalFailure -= skillMod;
        if (craftingValuesDictionary.containsKey(OBJVAR_REFACTOR))
        {
            criticalSuccess = NUMERIC_RANGE_EXPRESSION + 1;
            criticalFailure = dieRoll - 1;
        }
        else if (hasObjVar(getSelf(), OBJVAR_NO_CRIT_FAIL))
        {
            debugServerConsoleMsg(null, "Schematic is no crit fail, ain't that nice?");
            criticalFailure = dieRoll - 1;
        }
        final float BASE_DIFFICULTY_ADJUSTMENT = 0.25f;
        float defaultSkillCheckDifficulty = NUMERIC_RANGE_EXPRESSION * BASE_DIFFICULTY_ADJUSTMENT;
        float greatSuccess = defaultSkillCheckDifficulty + NUMERIC_RANGE_EXPRESSION * 0.45f;
        float goodSuccess = defaultSkillCheckDifficulty + NUMERIC_RANGE_EXPRESSION * 0.30f;
        float moderateSuccess = defaultSkillCheckDifficulty + NUMERIC_RANGE_EXPRESSION * 0.15f;
        float moderateFailure = defaultSkillCheckDifficulty - NUMERIC_RANGE_EXPRESSION * 0.15f;
        float bigFailure = defaultSkillCheckDifficulty - NUMERIC_RANGE_EXPRESSION * 0.30f;
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
        if (luck.isLucky(player, 0.01f))
        {
            successState = STATE_CRITICAL_SUCCESS;
        }
        else if (dieRoll >= criticalSuccess)
        {
            successState = STATE_CRITICAL_SUCCESS;
        }
        else if (modifiedDieRoll > greatSuccess)
        {
            successState = STATE_GREAT_SUCCESS;
        }
        else if (modifiedDieRoll > goodSuccess)
        {
            successState = STATE_GOOD_SUCCESS;
        }
        else if (modifiedDieRoll > moderateSuccess)
        {
            successState = STATE_MODERATE_SUCCESS;
        }
        else if (modifiedDieRoll > defaultSkillCheckDifficulty)
        {
            successState = STATE_SUCCESS;
        }
        else if (modifiedDieRoll > moderateFailure)
        {
            successState = STATE_FAILURE;
        }
        else if (modifiedDieRoll > bigFailure)
        {
            successState = STATE_MODERATE_FAILURE;
        }
        else 
        {
            successState = STATE_BIG_FAILURE;
        }
        if (successState != STATE_CRITICAL_SUCCESS && craftingValuesDictionary.containsKey(OBJVAR_FORCE_CRITICAL_SUCCESS))
        {
            if (!craftingValuesDictionary.containsKey(OBJVAR_REFACTOR))
            {
                successState = STATE_CRITICAL_SUCCESS;
                craftingValuesDictionary.put(OBJVAR_FORCE_CRITICAL_SUCCESS_RESULT, true);
            }
        }
        craftingValuesDictionary.put("assemblySuccessState", successState);
        if (isPlayerQA)
        {
            if (successState >= 0 && successState < STATE_NAMES.length)
            {
                sendSystemMessageTestingOnly(player, "CRAFTING --");
                sendSystemMessageTestingOnly(player, "CRAFTING -- Success State: " + STATE_NAMES[successState]);
                sendSystemMessageTestingOnly(player, "CRAFTING ---------------------------------");
            }
        }
        if (successState >= 0 && successState < STATE_NAMES.length)
        {
            debugServerConsoleMsg(null, "calcSkillDesignAssemblyCheck: success state = " + successState + " (" + STATE_NAMES[successState] + ")");
        }
        else 
        {
            debugServerConsoleMsg(null, "calcSkillDesignAssemblyCheck: success state = " + successState);
        }
        return successState;
    }
    public static float calcAssemblySuccessAttributeMultiplier(int assemblySuccessState, dictionary craftingValuesDictionary) throws InterruptedException
    {
        if (craftingValuesDictionary == null)
        {
            return 0;
        }
        float attributeMultiplier = 0;
        switch (assemblySuccessState)
        {
            case craftinglib.STATE_CRITICAL_SUCCESS:
            attributeMultiplier = 15;
            float currentComplexityTemp = craftingValuesDictionary.getFloat("itemCurrentComplexity");
            currentComplexityTemp -= 1.0f;
            craftingValuesDictionary.put("itemCurrentComplexity", currentComplexityTemp);
            break;
            case craftinglib.STATE_CRITICAL_FAILURE:
            case craftinglib.STATE_CRITICAL_FAILURE_NODESTROY:
            attributeMultiplier = -4;
            break;
            case craftinglib.STATE_GREAT_SUCCESS:
            attributeMultiplier = 15;
            break;
            case craftinglib.STATE_GOOD_SUCCESS:
            attributeMultiplier = 10;
            break;
            case craftinglib.STATE_MODERATE_SUCCESS:
            attributeMultiplier = 5;
            break;
            case craftinglib.STATE_SUCCESS:
            attributeMultiplier = 1;
            break;
            case craftinglib.STATE_FAILURE:
            attributeMultiplier = -1;
            break;
            case craftinglib.STATE_MODERATE_FAILURE:
            attributeMultiplier = -2;
            break;
            case craftinglib.STATE_BIG_FAILURE:
            attributeMultiplier = -3;
            break;
            default:
            debugServerConsoleMsg(null, "Wacky assembly skill success-state detected. No attribute multiplier set");
            attributeMultiplier = -5;
            break;
        }
        debugServerConsoleMsg(null, "Crafting assembly skill check gave a " + assemblySuccessState + " result, for a " + attributeMultiplier + " success multiplier");
        craftingValuesDictionary.put("assemblySuccessMultiplier", attributeMultiplier);
        return attributeMultiplier;
    }
    public static void calcCraftingDesignStageIngredientAttribAverageValues(dictionary craftingValuesDictionary, draft_schematic.slot[] ingredientSlots) throws InterruptedException
    {
        if (craftingValuesDictionary == null || ingredientSlots == null)
        {
            return;
        }
        dictionary componentDictionary = new dictionary();
        float[][] slotResourceAverage = new float[ingredientSlots.length][NUM_RESOURCE_ATTRIBS];
        float[] totalResourceSum = new float[NUM_RESOURCE_ATTRIBS];
        float[] resourceUnits = new float[NUM_RESOURCE_ATTRIBS];
        for (int i = 0; i < ingredientSlots.length; i++)
        {
            if (ingredientSlots[i] == null)
            {
                continue;
            }
            obj_id ingredient = ingredientSlots[i].ingredients[0].ingredient;
            int ingredientCount = ingredientSlots[i].ingredients[0].count;
            switch (ingredientSlots[i].ingredientType)
            {
                case draft_schematic.IT_item:
                case draft_schematic.IT_template:
                case draft_schematic.IT_templateGeneric:
                case draft_schematic.IT_schematic:
                case draft_schematic.IT_schematicGeneric:
                debugServerConsoleMsg(null, "getting components for object " + ingredient);
                int itemGOT = getGameObjectType(ingredient);
                if (itemGOT == GOT_resource_container_pseudo)
                {
                    debugServerConsoleMsg(null, "got pseudo resource for slot " + ingredientSlots[i].name);
                    for (int j = 0; j < ingredientSlots[i].ingredients.length; ++j)
                    {
                        ingredient = ingredientSlots[i].ingredients[j].ingredient;
                        ingredientCount = ingredientSlots[i].ingredients[j].count;
                        for (int k = 0; k < NUM_RESOURCE_ATTRIBS; ++k)
                        {
                            String ovname = COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + RESOURCE_OBJVAR_NAMES[k];
                            if (hasObjVar(ingredient, ovname))
                            {
                                float resourceAttrib = getIntObjVar(ingredient, ovname);
                                totalResourceSum[k] += resourceAttrib * ingredientCount;
                                resourceUnits[k] += ingredientCount;
                            }
                        }
                    }
                    continue;
                }
                obj_var_list component_data = getObjVarList(getSelf(), COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (ingredientSlots[i].name).getAsciiId());
                if (component_data != null)
                {
                    int dataCount = component_data.getNumItems();
                    debugServerConsoleMsg(null, "got component for slot " + ingredientSlots[i].name + ", count = " + dataCount);
                    for (int j = 0; j < dataCount; ++j)
                    {
                        obj_var ov = component_data.getObjVar(j);
                        if (ov != null)
                        {
                            Object data = ov.getData();
                            if (data instanceof Integer)
                            {
                                debugServerConsoleMsg(null, "\tsetting component attrib " + ov.getName() + " = " + ov.getIntData());
                                componentDictionary.put(ov.getName(), ov.getIntData());
                            }
                            else if (data instanceof Float)
                            {
                                debugServerConsoleMsg(null, "\tadding component attrib " + ov.getName() + " = " + ov.getFloatData());
                                componentDictionary.addFloat(ov.getName(), ov.getFloatData());
                            }
                            else if (data instanceof int[])
                            {
                                debugServerConsoleMsg(null, "\tsetting component attrib " + ov.getName() + " = " + ov.getIntArrayData());
                                componentDictionary.put(ov.getName(), ov.getIntArrayData());
                            }
                            else if (data instanceof float[])
                            {
                                debugServerConsoleMsg(null, "\tadding component attrib " + ov.getName() + " = " + ov.getFloatArrayData());
                                componentDictionary.addFloatArray(ov.getName(), ov.getFloatArrayData());
                            }
                            else if (data instanceof String)
                            {
                                debugServerConsoleMsg(null, "\tadding component attrib " + ov.getName() + " = " + ov.getStringData());
                                componentDictionary.put(ov.getName(), ov.getStringData());
                            }
                            else if (ov instanceof obj_var_list && (ov.getName()).equals(TISSUE_SKILL_MODS))
                            {
                                obj_var_list specialObjvars = (obj_var_list)ov;
                                int[] mod_idx = specialObjvars.getIntArrayObjVar(TISSUE_SKILL_INDEX);
                                int[] mod_val = specialObjvars.getIntArrayObjVar(TISSUE_SKILL_VALUE);
                                int[] dict_mod_idx = null;
                                int[] dict_mod_val = null;
                                dictionary specialDictionary = (dictionary)(componentDictionary.get(TISSUE_SKILL_MODS));
                                if (specialDictionary == null)
                                {
                                    specialDictionary = new dictionary();
                                    dict_mod_idx = new int[6];
                                    dict_mod_val = new int[6];
                                }
                                else 
                                {
                                    dict_mod_idx = specialDictionary.getIntArray(TISSUE_SKILL_INDEX);
                                    dict_mod_val = specialDictionary.getIntArray(TISSUE_SKILL_VALUE);
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
                                specialDictionary.put(TISSUE_SKILL_INDEX, dict_mod_idx);
                                specialDictionary.put(TISSUE_SKILL_VALUE, dict_mod_val);
                                componentDictionary.put(TISSUE_SKILL_MODS, specialDictionary);
                            }
                            else 
                            {
                                debugServerConsoleMsg(null, "\tcomponent attrib " + ov.getName() + " has unknown data type " + data.getClass());
                            }
                        }
                    }
                }
                else 
                {
                    LOG("crafting", "In Java craftinglib.scriptlib.calcCraftingDesignStageIngredientAttribAverageValues:" + " component object " + ingredient + " does not have the " + COMPONENT_ATTRIBUTE_OBJVAR_NAME + " objvar list attached to it");
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
                    resource_attribute[] resourceAttribs = getScaledResourceAttributes(ingredient, ingredientSlots[i].ingredientName);
                    if (resourceAttribs != null)
                    {
                        for (int j = 0; j < resourceAttribs.length; ++j)
                        {
                            if (resourceAttribs[j] != null && resourceAttribs[j].getValue() != RESOURCE_NOT_USED)
                            {
                                for (int k = 0; k < RESOURCE_OBJVAR_NAMES.length; ++k)
                                {
                                    if (RESOURCE_OBJVAR_NAMES[k].equals(resourceAttribs[j].getName()))
                                    {
                                        totalResourceSum[k] += resourceAttribs[j].getValue() * ingredientCount;
                                        resourceUnits[k] += ingredientCount;
                                        slotResourceAverage[i][k] = resourceAttribs[j].getValue();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                break;
                default:
                break;
            }
        }
        float[] itemTotalResourceAverage = new float[NUM_RESOURCE_ATTRIBS];
        for (int i = 0; i < NUM_RESOURCE_ATTRIBS; ++i)
        {
            if (resourceUnits[i] != 0)
            {
                itemTotalResourceAverage[i] = totalResourceSum[i] / resourceUnits[i];
            }
            else 
            {
                itemTotalResourceAverage[i] = 0;
            }
            debugServerConsoleMsg(null, "\t@@@@@craftinglib.scriptlib calcCraftingDesignStageIngredientAttribAverageValues, itemTotalResourceAverage unit [i]# " + i + ", value = " + itemTotalResourceAverage[i]);
        }
        craftingValuesDictionary.put(RESOURCE_AVERAGE_DICTIONARY_NAME, itemTotalResourceAverage);
        craftingValuesDictionary.put(SLOT_RESOURCE_DICTIONARY_NAME, slotResourceAverage);
        utils.saveDictionaryAsObjVar(getSelf(), COMPONENT_ATTRIBUTE_INTERNAL_OBJVAR_NAME, componentDictionary);
    }
    public static float getCraftingInspirationBonus(obj_id player, dictionary craftingValuesDictionary, float currentValue, float multiplier) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return 0f;
        }
        if (!utils.hasScriptVar(player, "buff.craftBonus.types"))
        {
            return 0f;
        }
        int flags = utils.getIntScriptVar(player, "buff.craftBonus.types");
        int type = craftingValuesDictionary.getInt("craftingType");
        if ((flags & type) > 0)
        {
            float mod = utils.getFloatScriptVar(player, "buff.craftBonus.value");
            float bonus = currentValue * (multiplier * mod);
            return bonus;
        }
        return 0f;
    }
    public static void calcResourceMaxItemAttributes(dictionary craftingValuesDictionary, draft_schematic.attribute[] itemAttributes, resource_weight[] weights) throws InterruptedException
    {
        calcResourceMaxItemAttributes(null, craftingValuesDictionary, itemAttributes, weights);
    }
    public static void calcResourceMaxItemAttributes(obj_id player, dictionary craftingValuesDictionary, draft_schematic.attribute[] itemAttributes, resource_weight[] weights) throws InterruptedException
    {
        float[] averageResourceValues = craftingValuesDictionary.getFloatArray(RESOURCE_AVERAGE_DICTIONARY_NAME);
        float[][] slotResourceValues = (float[][])(craftingValuesDictionary.get(SLOT_RESOURCE_DICTIONARY_NAME));
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
            else if (weights != null)
            {
                float lowAttributeRangeValue = itemAttributes[i].minValue;
                float highAttributeRangeValue = itemAttributes[i].maxValue;
                float attributeValueRangeSize = highAttributeRangeValue - lowAttributeRangeValue;
                float onePercentOfAttributeRange = attributeValueRangeSize / 100;
                for (int j = 0; j < weights.length; ++j)
                {
                    if ((itemAttributes[i].name).equals(weights[j].attribName))
                    {
                        debugServerConsoleMsg(null, "**** craftinglib.scriptlib attrib " + itemAttributes[i].name);
                        String onePercentName = (itemAttributes[i].name).getAsciiId() + "OnePercent";
                        float itemAttributeApplicableResourceValueAverage = 0;
                        if (weights[j].slot >= 0 && weights[j].slot < slotResourceValues.length)
                        {
                            itemAttributeApplicableResourceValueAverage = computeWeightedAverage(slotResourceValues[weights[j].slot], weights[j].weights) / 10.0f;
                        }
                        else 
                        {
                            itemAttributeApplicableResourceValueAverage = computeWeightedAverage(averageResourceValues, weights[j].weights) / 10.0f;
                        }
                        if (isIdValid(player))
                        {
                            itemAttributeApplicableResourceValueAverage += getCraftingInspirationBonus(player, craftingValuesDictionary, itemAttributeApplicableResourceValueAverage, 1f);
                            itemAttributeApplicableResourceValueAverage += (int)getSkillStatisticModifier(player, "expertise_resource_quality_increase");
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
                        debugServerConsoleMsg(null, "**** craftinglib.scriptlib calcResourceMaxItemAttributes 1% = " + onePercentOfAttributeRange + ", average = " + itemAttributeApplicableResourceValueAverage + ", resourceMaxRange = " + resourceMaxRange + ", resourceMaxValue = " + itemAttributes[i].resourceMaxValue);
                        break;
                    }
                }
            }
            else 
            {
                itemAttributes[i].resourceMaxValue = itemAttributes[i].maxValue;
                itemAttributes[i].currentValue = itemAttributes[i].minValue;
                String onePercentName = (itemAttributes[i].name).getAsciiId() + "OnePercent";
                float resourceMaxRange = itemAttributes[i].maxValue - itemAttributes[i].minValue;
                craftingValuesDictionary.put(onePercentName, resourceMaxRange / 100.0f);
                debugServerConsoleMsg(null, "**** craftinglib.scriptlib calcResourceMaxItemAttributes 1% (non-resource item) = " + (resourceMaxRange / 100.0f) + ", resourceMaxRange = " + resourceMaxRange + ", resourceMaxValue = " + itemAttributes[i].resourceMaxValue);
            }
        }
    }
    public static void calcPostAssemblyResourceSetAttribValues(dictionary craftingValuesDictionary, draft_schematic.attribute[] itemAttributes, resource_weight[] weights) throws InterruptedException
    {
        float[] averageResourceValues = craftingValuesDictionary.getFloatArray(RESOURCE_AVERAGE_DICTIONARY_NAME);
        float[][] slotResourceValues = (float[][])(craftingValuesDictionary.get(SLOT_RESOURCE_DICTIONARY_NAME));
        debugServerConsoleMsg(null, "Setting attribs for resources:");
        for (int i = 0; i < itemAttributes.length; ++i)
        {
            if (itemAttributes[i] == null || itemAttributes[i].minValue == itemAttributes[i].maxValue)
            {
                continue;
            }
            if (!(itemAttributes[i].name).equals("complexity") && weights != null)
            {
                for (int j = 0; j < weights.length; ++j)
                {
                    if ((itemAttributes[i].name).equals(weights[j].attribName))
                    {
                        float onePercentOfPossibleAttribute = craftingValuesDictionary.getFloat(weights[j].attribName + "OnePercent");
                        float averagedAttributeSum = 0;
                        if (weights[j].slot >= 0 && weights[j].slot < slotResourceValues.length)
                        {
                            averagedAttributeSum = computeWeightedAverage(slotResourceValues[weights[j].slot], weights[j].weights);
                        }
                        else 
                        {
                            averagedAttributeSum = computeWeightedAverage(averageResourceValues, weights[j].weights);
                        }
                        float resourceContribution = averagedAttributeSum * (15.0f / 1000.0f);
                        if (resourceContribution < 1.0f)
                        {
                            resourceContribution = 1.0f;
                        }
                        float attributeResourceValue = onePercentOfPossibleAttribute * resourceContribution;
                        itemAttributes[i].currentValue += attributeResourceValue;
                        debugServerConsoleMsg(null, "\t" + itemAttributes[i].name + " += " + attributeResourceValue);
                        break;
                    }
                }
            }
        }
    }
    public static void calcPostSkillAssemblyCheckSetAttribValues(dictionary craftingValuesDictionary, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
        if (craftingValuesDictionary == null || itemAttributes == null)
        {
            return;
        }
        int assemblySuccessState = craftingValuesDictionary.getInt("assemblySuccessState");
        float assemblySuccessStateMultiplier = calcAssemblySuccessAttributeMultiplier(assemblySuccessState, craftingValuesDictionary);
        if (assemblySuccessStateMultiplier < 0)
        {
            return;
        }
        debugServerConsoleMsg(null, "Setting attribs for assembly success:");
        for (int i = 0; i < itemAttributes.length; ++i)
        {
            if (itemAttributes[i] == null || itemAttributes[i].minValue == itemAttributes[i].maxValue)
            {
                continue;
            }
            if ((itemAttributes[i].name).equals("complexity"))
            {
                itemAttributes[i].currentValue = craftingValuesDictionary.getFloat("itemCurrentComplexity");
                debugServerConsoleMsg(null, "\t" + itemAttributes[i].name + " = " + itemAttributes[i].currentValue);
            }
            else if (!(itemAttributes[i].name).equals("armorSpecialType"))
            {
                float onePercentOfPossibleAttribute = craftingValuesDictionary.getFloat((itemAttributes[i].name).getAsciiId() + "OnePercent");
                float attributePostSkillAssemblyValue = onePercentOfPossibleAttribute * assemblySuccessStateMultiplier;
                itemAttributes[i].currentValue += attributePostSkillAssemblyValue;
                debugServerConsoleMsg(null, "\t" + itemAttributes[i].name + " += " + attributePostSkillAssemblyValue);
            }
        }
    }
    public static int calcAssemblyPhaseAttributes(obj_id player, draft_schematic.slot[] itemIngredients, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary, String[] playerSkillMods, resource_weight[] maxResourceAttribWeights, resource_weight[] resourceAttribWeights) throws InterruptedException
    {
        debugServerConsoleMsg(null, "Beginning assembly-phase attribute resolution");
        calcCraftingDesignStageIngredientAttribAverageValues(craftingValuesDictionary, itemIngredients);
        int success = calcSkillDesignAssemblyCheck(player, craftingValuesDictionary, playerSkillMods);
        if (success == STATE_CRITICAL_FAILURE || success == STATE_CRITICAL_FAILURE_NODESTROY)
        {
            return success;
        }
        calcResourceMaxItemAttributes(player, craftingValuesDictionary, itemAttributes, maxResourceAttribWeights);
        calcPostAssemblyResourceSetAttribValues(craftingValuesDictionary, itemAttributes, resourceAttribWeights);
        calcPostSkillAssemblyCheckSetAttribValues(craftingValuesDictionary, itemAttributes);
        float complexity = 0;
        draft_schematic.attribute xpAttrib = null;
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
            else if ((itemAttributes[i].name).equals("xp"))
            {
                xpAttrib = itemAttributes[i];
            }
        }
        if (xpAttrib != null)
        {
            float numSlotsFilled = 0;
            if (itemIngredients != null)
            {
                numSlotsFilled = itemIngredients.length;
            }
            xpAttrib.currentValue = xpAttrib.minValue;
            debugServerConsoleMsg(null, "craftinglib.scriptlib calcAssemblyPhaseAttributes complexity = " + complexity + ", slots filled = " + numSlotsFilled + ", xp = " + xpAttrib.currentValue);
        }
        else 
        {
            debugServerConsoleMsg(null, "craftinglib.scriptlib calcAssemblyPhaseAttributes no xp attrib defined!");
        }
        return success;
    }
    public static void calcExperimentalAttribs(draft_schematic schematic) throws InterruptedException
    {
        debugServerConsoleMsg(null, "craftinglib.scriptlib calcExperimentalAttribs enter");
        if (schematic == null)
        {
            debugServerConsoleMsg(null, "craftinglib.scriptlib calcExperimentalAttribs error - no schematic");
            return;
        }
        draft_schematic.attribute[] objectAttribs = schematic.getAttribs();
        draft_schematic.attribute[] experimentalAttribs = schematic.getExperimentalAttribs();
        if (objectAttribs == null || experimentalAttribs == null)
        {
            debugServerConsoleMsg(null, "craftinglib.scriptlib calcExperimentalAttribs error - no attribs");
            return;
        }
        for (int i = 0; i < experimentalAttribs.length; ++i)
        {
            experimentalAttribs[i].minValue = 0;
            experimentalAttribs[i].maxValue = 1.0f;
            experimentalAttribs[i].resourceMaxValue = 0;
            experimentalAttribs[i].currentValue = 0;
            experimentalAttribs[i].scratch = 0;
        }
        for (int i = 0; i < objectAttribs.length; ++i)
        {
            if (objectAttribs[i] == null)
            {
                continue;
            }
            debugServerConsoleMsg(null, "object attrib " + objectAttribs[i].name + ", min = " + objectAttribs[i].minValue + ", resourceMax = " + objectAttribs[i].resourceMaxValue + ", max = " + objectAttribs[i].maxValue + ", current = " + objectAttribs[i].currentValue);
            draft_schematic.attribute expAttr = schematic.getExperimentalAttrib(objectAttribs[i]);
            if (expAttr != null)
            {
                debugServerConsoleMsg(null, "Got matching experiment attrib " + expAttr.name);
                float denom = objectAttribs[i].maxValue - objectAttribs[i].minValue;
                if (denom != 0)
                {
                    expAttr.resourceMaxValue += (objectAttribs[i].resourceMaxValue - objectAttribs[i].minValue) / denom;
                    expAttr.currentValue += (objectAttribs[i].currentValue - objectAttribs[i].minValue) / denom;
                }
                ++expAttr.scratch;
            }
        }
        for (int i = 0; i < experimentalAttribs.length; ++i)
        {
            if (experimentalAttribs[i].scratch > 0)
            {
                experimentalAttribs[i].resourceMaxValue /= experimentalAttribs[i].scratch;
                experimentalAttribs[i].currentValue /= experimentalAttribs[i].scratch;
                debugServerConsoleMsg(null, "experiment attrib " + experimentalAttribs[i].name + ", min = " + experimentalAttribs[i].minValue + ", resourceMax = " + experimentalAttribs[i].resourceMaxValue + ", max = " + experimentalAttribs[i].maxValue + ", current = " + experimentalAttribs[i].currentValue);
            }
        }
        debugServerConsoleMsg(null, "craftinglib.scriptlib calcExperimentalAttribs exit");
    }
    public static float[] convertExperimentalPointsToObjectPoints(draft_schematic schematic, string_id[] attributeNames, int[] experimentPoints) throws InterruptedException
    {
        debugServerConsoleMsg(null, "craftinglib::convertExperimentalPointsToObjectPoints enter");
        if (schematic == null || attributeNames == null || attributeNames.length == 0 || experimentPoints == null || experimentPoints.length == 0 || attributeNames.length != experimentPoints.length)
        {
            debugServerConsoleMsg(null, "convertExperimentalPointsToObjectPoints BAD INPUT");
            return null;
        }
        draft_schematic.attribute[] objectAttribs = schematic.getAttribs();
        draft_schematic.attribute[] experimentalAttribs = schematic.getExperimentalAttribs();
        if (objectAttribs == null || objectAttribs.length == 0 || experimentalAttribs == null || experimentalAttribs.length != experimentPoints.length)
        {
            debugServerConsoleMsg(null, "convertExperimentalPointsToObjectPoints NO ATTRIBS");
            return null;
        }
        for (int i = 0; i < objectAttribs.length; ++i)
        {
            if (objectAttribs[i] != null)
            {
                debugServerConsoleMsg(null, "object attrib " + objectAttribs[i].name + " = " + objectAttribs[i].currentValue);
            }
        }
        for (int i = 0; i < experimentalAttribs.length; ++i)
        {
            if (experimentalAttribs[i] != null)
            {
                debugServerConsoleMsg(null, "exp attrib " + experimentalAttribs[i].name + " = " + experimentalAttribs[i].currentValue);
            }
        }
        float[] objectPoints = new float[objectAttribs.length];
        float[] adjustedObjectAttribs = new float[objectAttribs.length];
        for (int i = 0; i < objectAttribs.length; ++i)
        {
            if (objectAttribs[i] != null)
            {
                objectAttribs[i].scratch = i;
                float denom = objectAttribs[i].maxValue - objectAttribs[i].minValue;
                if (denom != 0)
                {
                    adjustedObjectAttribs[i] = (objectAttribs[i].currentValue - objectAttribs[i].minValue) / denom;
                }
            }
        }
        for (int i = 0; i < attributeNames.length; ++i)
        {
            int experimentalAttribIndex = -1;
            for (int j = 0; j < experimentalAttribs.length; ++j)
            {
                if (attributeNames[i].equals(experimentalAttribs[j].name))
                {
                    experimentalAttribIndex = j;
                    break;
                }
            }
            if (experimentalAttribIndex == -1)
            {
                debugServerConsoleMsg(null, "convertExperimentalPointsToObjectPoints NO EXP ATTRIB");
                return null;
            }
            int attribCount = 0;
            draft_schematic.attribute[] objExpAttribs = schematic.getObjectAttribs(experimentalAttribs[experimentalAttribIndex]);
            if (objExpAttribs != null)
            {
                for (int j = 0; j < objExpAttribs.length; ++j)
                {
                    if (objExpAttribs[j] == null)
                    {
                        continue;
                    }
                    schematic.setExperimentalAttrib(objExpAttribs[j], experimentalAttribs[experimentalAttribIndex]);
                    debugServerConsoleMsg(null, "convertExperimentalPointsToObjectPoints attrib " + objectAttribs[objExpAttribs[j].scratch].name);
                    debugServerConsoleMsg(null, "\texp attrib value = " + experimentalAttribs[experimentalAttribIndex].currentValue + ", real attrib value = " + objExpAttribs[j].currentValue + ", adjusted real value = " + adjustedObjectAttribs[objExpAttribs[j].scratch]);
                    if (experimentalAttribs[experimentalAttribIndex].currentValue != 0)
                    {
                        float numer = experimentalAttribs[experimentalAttribIndex].currentValue - adjustedObjectAttribs[objExpAttribs[j].scratch];
                        float ratio = numer / experimentalAttribs[experimentalAttribIndex].currentValue;
                        objectPoints[objExpAttribs[j].scratch] = experimentPoints[i] * (1.0f + ratio);
                        if (objectPoints[objExpAttribs[j].scratch] < 0)
                        {
                            objectPoints[objExpAttribs[j].scratch] = 0;
                        }
                        debugServerConsoleMsg(null, "\tnumer = " + numer + ", ratio = " + ratio + ", exp points = " + objectPoints[objExpAttribs[j].scratch]);
                    }
                }
            }
        }
        debugServerConsoleMsg(null, "convertExperimentalPointsToObjectPoints EXIT OK!");
        return objectPoints;
    }
    public static int calcExperimentPoints(obj_id player, String[] skillMods) throws InterruptedException
    {
        int[] mods = getEnhancedSkillStatisticModifiers(player, skillMods);
        int totalMod = 0;
        if (mods != null)
        {
            for (int i = 0; i < mods.length; ++i)
            {
                totalMod += mods[i];
            }
        }
        totalMod /= 10;
        if (totalMod < 1)
        {
            totalMod = 1;
        }
        return totalMod;
    }
    public static void calcExpFullSinglePointValue(dictionary craftingValuesDictionary, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
        for (int i = 0; i < itemAttributes.length; ++i)
        {
            if (itemAttributes[i] == null)
            {
                continue;
            }
            if (!(itemAttributes[i].name).equals("complexity"))
            {
                float onePercent = (itemAttributes[i].maxValue - itemAttributes[i].minValue) / 100.0f;
                float experimentationRange = onePercent * 70.0f;
                float oneExperimentPoint = experimentationRange * 0.1f;
                craftingValuesDictionary.put((itemAttributes[i].name).getAsciiId() + "TenPercent", oneExperimentPoint);
                debugServerConsoleMsg(null, "craftinglib.calcExpFullSinglePointValue: " + (itemAttributes[i].name).getAsciiId() + "TenPercent = " + oneExperimentPoint);
            }
        }
    }
    public static void calcPerExperimentationCheckMod(obj_id player, dictionary craftingValuesDictionary, int experimentPointCount, draft_schematic.attribute[] itemAttributes, String[] skills) throws InterruptedException
    {
        int numExperimentedAttributes = experimentPointCount;
        float numAttributesExperimentedMod = 0;
        float complexityModifier = 0;
        float skillModifier = 0;
        for (int i = 0; i < itemAttributes.length; ++i)
        {
            if (itemAttributes[i] == null)
            {
                continue;
            }
            if ((itemAttributes[i].name).equals("complexity"))
            {
                complexityModifier = 5.0f * (itemAttributes[i].currentValue - itemAttributes[i].minValue) / COMPLEXITY_INCREMENT_MOD;
                break;
            }
        }
        debugServerConsoleMsg(null, "craftinglib::calcPerExperimentationCheckMod - complexityModifier = " + complexityModifier);
        if (numExperimentedAttributes > 1)
        {
            numAttributesExperimentedMod = (numExperimentedAttributes - 1) * 5.0f;
        }
        debugServerConsoleMsg(null, "craftinglib::calcPerExperimentationCheckMod - numAttributesExperimentedMod = " + numAttributesExperimentedMod);
        float craftingStationMod = 0;
        obj_id stationId = getCraftingStation(player);
        if (!isIdValid(stationId))
        {
            stationId = getSelf();
        }
        if (hasObjVar(stationId, OBJVAR_CRAFTING_STATION_BONUS) == true)
        {
            // Cekis: not sure why a float divided by an int is desired here (was just 10), so changing the int to a float
            // as the former would have resulted in potential loss in precision.
            craftingStationMod = getFloatObjVar(stationId, OBJVAR_CRAFTING_STATION_BONUS) / 10.0f;
        }
        debugServerConsoleMsg(null, "craftinglib::calcPerExperimentationCheckMod - craftingStationMod = " + craftingStationMod);
        float playerSkillMod = 0;
        if (skills != null)
        {
            int[] mods = getEnhancedSkillStatisticModifiers(player, skills);
            if (mods != null)
            {
                for (int i = 0; i < mods.length; ++i)
                {
                    playerSkillMod += mods[i];
                }
            }
        }
        debugServerConsoleMsg(null, "craftinglib::calcPerExperimentationCheckMod - playerSkillMod = " + playerSkillMod);
        float malleabilitySkillMod = craftingValuesDictionary.getFloat(RESOURCE_MALLEABILITY_SKILL_MOD);
        debugServerConsoleMsg(null, "craftinglib::calcPerExperimentationCheckMod - malleabilitySkillMod = " + malleabilitySkillMod);
        float totalExperimentPointModifier = playerSkillMod + malleabilitySkillMod + craftingStationMod - complexityModifier - numAttributesExperimentedMod;
        debugServerConsoleMsg(null, "craftinglib::calcPerExperimentationCheckMod - total mod = " + totalExperimentPointModifier);
        craftingValuesDictionary.put("totalExperimentPointModifier", totalExperimentPointModifier);
        craftingValuesDictionary.put("playerExperimentSkillMod", playerSkillMod);
        craftingValuesDictionary.put("expItemComplexitySkillMod", complexityModifier);
        craftingValuesDictionary.put("expCraftingStationMod", craftingStationMod);
        craftingValuesDictionary.put("expNumAttributesExperimentMod", numAttributesExperimentedMod);
    }
    public static void calcSuccessPerAttributeExperimentation(dictionary craftingValuesDictionary, float[] experimentPoints, draft_schematic.attribute[] itemAttributes, obj_id player, String[] craftingSkills) throws InterruptedException
    {
        debugServerConsoleMsg(null, "calcSuccessPerAttributeExperimentation enter");
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
        float totalExperimentPointModifier = craftingValuesDictionary.getFloat("totalExperimentPointModifier");
        float playerSkillMod = craftingValuesDictionary.getFloat("playerExperimentSkillMod");
        float complexityModifier = craftingValuesDictionary.getFloat("expItemComplexitySkillMod");
        float craftingStationMod = craftingValuesDictionary.getFloat("expCraftingStationMod");
        float numAttributesExperimentedMod = craftingValuesDictionary.getFloat("expNumAttributesExperimentMod");
        float malleabilitySkillMod = craftingValuesDictionary.getFloat(RESOURCE_MALLEABILITY_SKILL_MOD);
        final float deltaMod = NUMERIC_RANGE_EXPRESSION / BASE_RANGE;
        if (isPlayerQA)
        {
            sendSystemMessageTestingOnly(player, " ");
            sendSystemMessageTestingOnly(player, "EXPERIMENT ---------------------------------");
            sendSystemMessageTestingOnly(player, "EXPERIMENT -- Experimentation Skill Mod = " + playerSkillMod);
            sendSystemMessageTestingOnly(player, "EXPERIMENT -- Resource Malleability Mod = " + malleabilitySkillMod);
            sendSystemMessageTestingOnly(player, "EXPERIMENT -- Crafting Station Mod = " + craftingStationMod);
            sendSystemMessageTestingOnly(player, "EXPERIMENT -- Item Complexity Mod = " + (-1f * complexityModifier));
            sendSystemMessageTestingOnly(player, "EXPERIMENT -- Attributes Mod = " + (-1f * numAttributesExperimentedMod));
            sendSystemMessageTestingOnly(player, "EXPERIMENT -- Sum of Roll Modifiers = " + totalExperimentPointModifier);
            sendSystemMessageTestingOnly(player, "EXPERIMENT --");
            sendSystemMessageTestingOnly(player, "EXPERIMENT -- Modifier Balance Multiplier = " + deltaMod);
            sendSystemMessageTestingOnly(player, "EXPERIMENT --");
        }
        float numericRangeModifiedTargetNumberModifier = totalExperimentPointModifier;
        numericRangeModifiedTargetNumberModifier *= deltaMod;
        if (isPlayerQA)
        {
            sendSystemMessageTestingOnly(player, "EXPERIMENT -- Balanced Roll Modifiers = " + numericRangeModifiedTargetNumberModifier);
            sendSystemMessageTestingOnly(player, "EXPERIMENT --");
        }
        int averageSuccessState = 0;
        int averageSuccessStateCount = 0;
        float critical_s = 0.0f;
        float great_s = 0.0f;
        float good_s = 0.0f;
        float moderate_s = 0.0f;
        float s = 0.0f;
        float moderate_f = 0.0f;
        float big_f = 0.0f;
        float critical_f = 0.0f;
        if (isPlayerTester)
        {
            CustomerServiceLog("crit_fail", "== EXPERIMENTATION_BEGIN == " + getName(player) + " (" + player + ") is starting to experiment on an item");
        }
        float cityRollAdjust = 1.0f;
        int city_id = city.checkCity(player, false);
        if (city_id > 0 && (city.cityHasSpec(city_id, city.SF_SPEC_RESEARCH) || city.cityHasSpec(city_id, city.SF_SPEC_MASTER_MANUFACTURING)))
        {
            cityRollAdjust = 1.15f;
            sendSystemMessage(player, new string_id("city/city", "research_center_message"));
            if (isPlayerQA)
            {
                sendSystemMessageTestingOnly(player, "EXPERIMENT -- 15% raw random bonus for research city");
            }
        }
        debugServerConsoleMsg(null, "craftinglib::calcSuccessPerAttributeExperimentation city adjustment = " + cityRollAdjust);
        float foodRollAdjust = 0;
        if (utils.hasScriptVar(player, "buff.drink_bespin_port.value"))
        {
            int eff = (int)utils.getFloatScriptVar(player, "buff.drink_bespin_port.value");
            buff.removeBuff(player, "drink_bespin_port");
            foodRollAdjust = eff * deltaMod;
            if (isPlayerQA)
            {
                sendSystemMessageTestingOnly(player, "EXPERIMENT -- Food buff effect applied to raw random rolls");
            }
        }
        debugServerConsoleMsg(null, "craftinglib::calcSuccessPerAttributeExperimentation food adjustment = " + foodRollAdjust);
        float forceRollAdjust = 0;
        
        {
            int forceBonus = getSkillStatisticModifier(player, "force_experimentation");
            forceRollAdjust = forceBonus * deltaMod;
            if (isPlayerQA)
            {
                sendSystemMessageTestingOnly(player, "EXPERIMENT -- Force Experimentation bonus applied to raw random rolls");
            }
        }
        debugServerConsoleMsg(null, "craftinglib::calcSuccessPerAttributeExperimentation force adjustment = " + forceRollAdjust);
        String expertiseSkill = getCraftingSubskill(craftingSkills);
        float expertiseRollAdjust = 0;
        
        {
            int expertiseBonus = getSkillStatisticModifier(player, "expertise_experimentation_increase_" + expertiseSkill);
            expertiseRollAdjust = expertiseBonus * deltaMod;
            if (isPlayerQA)
            {
                sendSystemMessageTestingOnly(player, "EXPERIMENT -- Expertise Experimentation bonus applied to raw random rolls: " + expertiseRollAdjust + " expertiseSkill: " + expertiseSkill);
            }
        }
        for (int i = 0; i < itemAttributes.length; ++i)
        {
            if (itemAttributes[i] == null)
            {
                continue;
            }
            int successState = STATE_UNSET;
            if (!(itemAttributes[i].name).equals("complexity") && experimentPoints[i] > 0)
            {
                float baseRoll = rand(1, (int)NUMERIC_RANGE_EXPRESSION);
                float dieRoll = baseRoll * cityRollAdjust;
                dieRoll += getCraftingInspirationBonus(player, craftingValuesDictionary, baseRoll, 5f);
                dieRoll += foodRollAdjust;
                dieRoll += forceRollAdjust;
                dieRoll += expertiseRollAdjust;
                debugServerConsoleMsg(null, "craftinglib::calcSuccessPerAttributeExperimentation - base roll for attrib " + itemAttributes[i].name + " (" + itemAttributes[i].currentValue + ") = " + baseRoll + ", adjusted roll = " + dieRoll);
                float successAdjust = playerSkillMod / 4000.0f;
                float successThreshold = 5.0f * ((10.0f + experimentPoints[i]) * (1 - successAdjust)) * deltaMod;
                float modifiedSkillRoll = dieRoll + numericRangeModifiedTargetNumberModifier;
                debugServerConsoleMsg(null, "\texpPts = " + experimentPoints[i] + ", success threshold = " + successThreshold + ", mod roll = " + modifiedSkillRoll);
                float moderateSuccess = successThreshold * 1.15f;
                float goodSuccess = successThreshold * 1.30f;
                float greatSuccess = successThreshold * 1.45f;
                float moderateFailure = successThreshold * 0.85f;
                float bigFailure = successThreshold * 0.70f;
                float criticalSuccess = NUMERIC_RANGE_EXPRESSION * (0.95f - successAdjust);
                float criticalFailure = NUMERIC_RANGE_EXPRESSION * (0.05f - successAdjust);
                if (craftingValuesDictionary.containsKey(OBJVAR_REFACTOR))
                {
                    criticalSuccess = NUMERIC_RANGE_EXPRESSION + 1;
                    criticalFailure = dieRoll - 1;
                }
                else if (hasObjVar(getSelf(), OBJVAR_NO_CRIT_FAIL))
                {
                    debugServerConsoleMsg(null, "Schematic is no crit fail, ain't that nice?");
                    criticalFailure = dieRoll - 1;
                }
                critical_s = criticalSuccess;
                great_s = greatSuccess;
                good_s = goodSuccess;
                moderate_s = moderateSuccess;
                s = successThreshold;
                moderate_f = moderateFailure;
                big_f = bigFailure;
                critical_f = criticalFailure;
                if (isPlayerTester)
                {
                    CustomerServiceLog("crit_fail", "== EXPERIMENTATION_ATTRIBUTE_BEGIN == " + getName(player) + " (" + player + ") is starting to experiment on " + itemAttributes[i].name + " -- Base Roll: " + dieRoll + ", Mod Roll: " + modifiedSkillRoll);
                }
                float oneExperimentPoint = craftingValuesDictionary.getFloat((itemAttributes[i].name).getAsciiId() + "TenPercent");
                float totalAttribExperimentPoints = oneExperimentPoint * experimentPoints[i];
                float successPointScale = 0;
                if (luck.isLucky(player, 0.005f))
                {
                    successState = STATE_CRITICAL_SUCCESS;
                    successPointScale = 1.20f;
                }
                else if (dieRoll >= criticalSuccess)
                {
                    successState = STATE_CRITICAL_SUCCESS;
                    successPointScale = 1.15f;
                }
                else if (dieRoll <= criticalFailure)
                {
                    if (isPlayerTester)
                    {
                        CustomerServiceLog("crit_fail", "== EXPERIMENTATION_ATTRIBUTE_FAILURE MOD: " + playerSkillMod + " == " + getName(player) + " (" + player + ") has critically failed while experimenting on " + itemAttributes[i].name + " -- " + dieRoll + " <= " + criticalFailure);
                    }
                    successState = STATE_CRITICAL_FAILURE;
                    successPointScale = -1.0f;
                    int screwedAttributeDieRoll = rand(0, itemAttributes.length - 1);
                    while (itemAttributes[screwedAttributeDieRoll].minValue == itemAttributes[screwedAttributeDieRoll].maxValue)
                    {
                        screwedAttributeDieRoll = rand(0, itemAttributes.length - 1);
                    }
                    float oneScrewedAttributeExperimentPoint = craftingValuesDictionary.getFloat((itemAttributes[screwedAttributeDieRoll].name).getAsciiId() + "TenPercent");
                    float randomlyScrewedAttributeValue = itemAttributes[screwedAttributeDieRoll].currentValue - oneScrewedAttributeExperimentPoint;
                    if (randomlyScrewedAttributeValue < itemAttributes[screwedAttributeDieRoll].minValue)
                    {
                        randomlyScrewedAttributeValue = itemAttributes[screwedAttributeDieRoll].minValue;
                    }
                    debugServerConsoleMsg(null, "craftinglib.calcSuccessPerAttributeExperimentation: crit fail screwing attribute " + itemAttributes[screwedAttributeDieRoll].name + " from " + itemAttributes[screwedAttributeDieRoll].currentValue + " to " + randomlyScrewedAttributeValue);
                    itemAttributes[screwedAttributeDieRoll].currentValue = randomlyScrewedAttributeValue;
                }
                else if (modifiedSkillRoll > greatSuccess)
                {
                    successState = STATE_GREAT_SUCCESS;
                    successPointScale = 1.0f;
                }
                else if (modifiedSkillRoll > goodSuccess)
                {
                    successState = STATE_GOOD_SUCCESS;
                    successPointScale = 0.5f;
                }
                else if (modifiedSkillRoll > moderateSuccess)
                {
                    successState = STATE_MODERATE_SUCCESS;
                    successPointScale = 0.25f;
                }
                else if (modifiedSkillRoll > successThreshold)
                {
                    successState = STATE_SUCCESS;
                    successPointScale = 0.15f;
                }
                else if (modifiedSkillRoll > moderateFailure)
                {
                    successState = STATE_FAILURE;
                }
                else if (modifiedSkillRoll > bigFailure)
                {
                    successState = STATE_MODERATE_FAILURE;
                    successPointScale = -0.5f;
                }
                else 
                {
                    successState = STATE_BIG_FAILURE;
                    successPointScale = -1.0f;
                }
                if (successState != STATE_CRITICAL_SUCCESS && craftingValuesDictionary.containsKey(OBJVAR_FORCE_CRITICAL_SUCCESS))
                {
                    if (!craftingValuesDictionary.containsKey(OBJVAR_REFACTOR))
                    {
                        successState = STATE_CRITICAL_SUCCESS;
                        successPointScale = 1.15f;
                        craftingValuesDictionary.put(OBJVAR_FORCE_CRITICAL_SUCCESS_RESULT, true);
                    }
                }
                if (isPlayerQA)
                {
                    sendSystemMessageTestingOnly(player, "EXPERIMENT -- Raw Random Roll: " + itemAttributes[i].name + " = " + dieRoll);
                    sendSystemMessageTestingOnly(player, "EXPERIMENT -- FINAL MODIFIED ROLL: " + itemAttributes[i].name + " = " + modifiedSkillRoll);
                    if (successState >= 0 && successState < STATE_NAMES.length)
                    {
                        sendSystemMessageTestingOnly(player, "EXPERIMENT -- Success state for " + itemAttributes[i].name + ": " + STATE_NAMES[successState]);
                    }
                    sendSystemMessageTestingOnly(player, "EXPERIMENT --");
                }
                if (successState >= 0 && successState < STATE_NAMES.length)
                {
                    debugServerConsoleMsg(null, "\tsuccess state = " + successState + "(" + STATE_NAMES[successState] + "), point scale = " + successPointScale);
                }
                else 
                {
                    debugServerConsoleMsg(null, "\tsuccess state = " + successState + ", point scale = " + successPointScale);
                }
                float postExperimentationAttributeDelta = totalAttribExperimentPoints * successPointScale * EXPERIMENTATION_EFFECTIVENESS_MULTIPLIER;
                float postExperimentationAttributeValue = itemAttributes[i].currentValue + postExperimentationAttributeDelta;
                LOG("crafting", "EXPERIMENT -- postExperimentationAttributeDelta: " + postExperimentationAttributeDelta + " postExperimentationAttributeValue: " + postExperimentationAttributeValue);
                LOG("crafting", "EXPERIMENT -- itemAttributes[i].resourceMaxValue: " + itemAttributes[i].resourceMaxValue + " itemAttributes[i].minValue: " + itemAttributes[i].minValue);
                if (postExperimentationAttributeValue > itemAttributes[i].resourceMaxValue)
                {
                    postExperimentationAttributeValue = itemAttributes[i].resourceMaxValue;
                }
                else if (postExperimentationAttributeValue < itemAttributes[i].minValue)
                {
                    postExperimentationAttributeValue = itemAttributes[i].minValue;
                }
                itemAttributes[i].currentValue = postExperimentationAttributeValue;
                debugServerConsoleMsg(null, "\tvalue delta = " + postExperimentationAttributeDelta + ", final value = " + postExperimentationAttributeValue);
                averageSuccessState += successState;
                ++averageSuccessStateCount;
            }
            craftingValuesDictionary.put((itemAttributes[i].name).getAsciiId() + "successState", successState);
        }
        if (isPlayerQA)
        {
            sendSystemMessageTestingOnly(player, "EXPERIMENT -- Critical Success: raw roll above " + critical_s);
            sendSystemMessageTestingOnly(player, "EXPERIMENT -- Critical Failure: raw roll below " + critical_f);
            sendSystemMessageTestingOnly(player, "EXPERIMENT --");
            sendSystemMessageTestingOnly(player, "EXPERIMENT -- Great Success: mod roll above " + great_s);
            sendSystemMessageTestingOnly(player, "EXPERIMENT -- Good Success: mod roll above " + good_s);
            sendSystemMessageTestingOnly(player, "EXPERIMENT -- Moderate Success: mod roll above " + moderate_s);
            sendSystemMessageTestingOnly(player, "EXPERIMENT -- Success: mod roll above " + s);
            sendSystemMessageTestingOnly(player, "EXPERIMENT -- Failure: mod roll above " + moderate_f);
            sendSystemMessageTestingOnly(player, "EXPERIMENT -- Moderate Failure: mod roll above " + big_f);
            sendSystemMessageTestingOnly(player, "EXPERIMENT -- Big Failure: mod roll below " + big_f);
        }
        averageSuccessState = (int)(((float)averageSuccessState / (float)averageSuccessStateCount) + 0.5f);
        if (averageSuccessState >= STATE_CRITICAL_FAILURE)
        {
            if (isPlayerTester)
            {
                CustomerServiceLog("crit_fail", "== EXPERIMENTATION_FAILURE MOD: " + playerSkillMod + " == " + getName(player) + " (" + player + ") has critically failed the experimentation process -- Average State: " + averageSuccessState);
            }
        }
        if (isPlayerQA)
        {
            if (averageSuccessState >= 0 && averageSuccessState < STATE_NAMES.length)
            {
                sendSystemMessageTestingOnly(player, "EXPERIMENT --");
                sendSystemMessageTestingOnly(player, "EXPERIMENT -- Overall success state: " + STATE_NAMES[averageSuccessState]);
            }
            sendSystemMessageTestingOnly(player, "EXPERIMENT ---------------------------------");
        }
        if (averageSuccessState > STATE_CRITICAL_FAILURE)
        {
            averageSuccessState = STATE_CRITICAL_FAILURE;
        }
        craftingValuesDictionary.put("experimentSuccessState", averageSuccessState);
    }
    public static void storeTissueDataAsObjvars(dictionary craftingValuesDictionary, draft_schematic.attribute[] itemAttributes, obj_id prototype, boolean includeComponents) throws InterruptedException
    {
        obj_id target = getSelf();
        debugServerConsoleMsg(target, "craftinglib.storeTissueDataAsObjvars enter");
        if (includeComponents)
        {
            obj_var_list componentData = getObjVarList(getSelf(), COMPONENT_ATTRIBUTE_INTERNAL_OBJVAR_NAME);
            if (componentData != null)
            {
                obj_var skill_mods = componentData.getObjVar(TISSUE_SKILL_MODS);
                if (skill_mods != null)
                {
                    if (skill_mods instanceof obj_var_list)
                    {
                        obj_var_list skillModList = (obj_var_list)skill_mods;
                        int[] mod_idx = skillModList.getIntArrayObjVar(TISSUE_SKILL_INDEX);
                        int[] mod_val = skillModList.getIntArrayObjVar(TISSUE_SKILL_VALUE);
                        String root = COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + TISSUE_SKILL_MODS + ".";
                        if (((mod_idx != null) && (mod_idx.length > 0)) && ((mod_val != null) && (mod_val.length > 0)))
                        {
                            setObjVar(target, root + TISSUE_SKILL_INDEX, mod_idx);
                            setObjVar(target, root + TISSUE_SKILL_VALUE, mod_val);
                        }
                    }
                }
            }
        }
        debugServerConsoleMsg(target, "craftinglib.storeTissueDataAsObjvars exit");
    }
    public static draft_schematic.custom[] standardCustomizationProcedure(obj_id player, draft_schematic.custom[] customizations, String[] customizationSkillMods) throws InterruptedException
    {
        LOG("customization", "New customization code activated - NOW IN CRAFTING LIB");
        String[] skills = customizationSkillMods;
        int playerCustomizationMod = 0;
        float playerCustModPercentage = 0;
        if (skills != null)
        {
            int[] mods = getEnhancedSkillStatisticModifiers(player, skills);
            for (int i = 0; i < mods.length; ++i)
            {
                playerCustomizationMod += mods[i];
            }
        }
        if (playerCustomizationMod > 255)
        {
            playerCustomizationMod = 255;
        }
        playerCustModPercentage = playerCustomizationMod / 255f;
        int validCustomizations = 0;
        for (int i = 0; i < customizations.length; i++)
        {
            if (customizations[i].maxValue > 4)
            {
                if (customizations[i].maxValue > playerCustomizationMod)
                {
                    customizations[i].maxValue = playerCustomizationMod;
                }
                validCustomizations++;
            }
            else 
            {
                customizations[i] = null;
            }
        }
        int playerAccessibleCustomizations = 1;
        if (playerCustomizationMod > 64)
        {
            playerAccessibleCustomizations++;
        }
        if (playerCustomizationMod > 128)
        {
            playerAccessibleCustomizations++;
        }
        if (playerCustomizationMod > 192)
        {
            playerAccessibleCustomizations++;
        }
        int usedPlayerCustomizations = 0;
        for (int j = 0; j < customizations.length; j++)
        {
            if (usedPlayerCustomizations < playerAccessibleCustomizations)
            {
                if (customizations[j] != null)
                {
                    usedPlayerCustomizations++;
                }
            }
            else 
            {
                customizations[j] = null;
            }
        }
        return customizations;
    }
    public static String getCraftingSubskill(String[] craftingSkills) throws InterruptedException
    {
        String strSkill = "artisan";
        if(craftingSkills != null) {
            String[] skillFragment =
                    {
                            "crafting_artisan",
                            "crafting_armorsmith",
                            "crafting_architect",
                            "crafting_chef",
                            "crafting_droid",
                            "crafting_shipwright",
                            "crafting_tailor",
                            "crafting_weaponsmith",
                            "crafting_cybernetic"
                    };
            String[] skillList =
                    {
                            "artisan",
                            "armorsmith",
                            "architect",
                            "chef",
                            "droid",
                            "shipwright",
                            "tailor",
                            "weaponsmith",
                            "cybernetic"
                    };
            for (int i = 0; i < craftingSkills.length; i++) {
                for (int j = 0; j < skillFragment.length; j++) {
                    if (craftingSkills[i].startsWith(skillFragment[j])) {
                        return skillList[j];
                    }
                }
            }
        }
        return strSkill;
    }
    public static String[] getDraftSchematicGroupsFromSkill(String skillName) throws InterruptedException
    {
        if (skillName == null || skillName.length() <= 0)
        {
            return null;
        }
        String schematicsGranted = dataTableGetString(skill.TBL_SKILL, skillName, "SCHEMATICS_GRANTED");
        if (schematicsGranted == null || schematicsGranted.equals(""))
        {
            return null;
        }
        return split(schematicsGranted, ',');
    }
    public static String[] getDraftSchematicGroupsFromSkills(String[] craftingSkills) throws InterruptedException
    {
        Vector schematicGroups = new Vector();
        schematicGroups.setSize(0);
        for (int i = 0; i < craftingSkills.length; i++)
        {
            String[] groups = getDraftSchematicGroupsFromSkill(craftingSkills[i]);
            if (groups != null && groups.length > 0)
            {
                for (int j = 0; j < groups.length; j++)
                {
                    schematicGroups.add(groups[j]);
                }
            }
        }
        String[] _schematicGroups = new String[0];
        if (schematicGroups != null)
        {
            _schematicGroups = new String[schematicGroups.size()];
            schematicGroups.toArray(_schematicGroups);
        }
        return _schematicGroups;
    }
    public static String[] getDraftSchematicsFromGroup(String schematicGroup) throws InterruptedException
    {
        if (schematicGroup == null || schematicGroup.length() <= 0)
        {
            return null;
        }
        int numRows = dataTableGetNumRows(TABLE_SCHEMATIC_GROUPS);
        Vector schematicTemplates = new Vector();
        schematicTemplates.setSize(0);
        for (int i = 0; i < numRows; i++)
        {
            String group = dataTableGetString(TABLE_SCHEMATIC_GROUPS, i, "GroupId");
            if (group.equals(schematicGroup))
            {
                schematicTemplates.add(dataTableGetString(TABLE_SCHEMATIC_GROUPS, i, "SchematicName"));
            }
        }
        String[] _schematicTemplates = new String[0];
        if (schematicTemplates != null)
        {
            _schematicTemplates = new String[schematicTemplates.size()];
            schematicTemplates.toArray(_schematicTemplates);
        }
        return _schematicTemplates;
    }
    public static String[] getDraftSchematicsFromGroups(String[] schematicGroups) throws InterruptedException
    {
        Vector allSchematics = new Vector();
        allSchematics.setSize(0);
        for (int i = 0; i < schematicGroups.length; i++)
        {
            String[] schematics = getDraftSchematicsFromGroup(schematicGroups[i]);
            if (schematics != null && schematics.length > 0)
            {
                for (int j = 0; j < schematics.length; j++)
                {
                    allSchematics.add(schematics[j]);
                }
            }
        }
        String[] _allSchematics = new String[0];
        if (allSchematics != null)
        {
            _allSchematics = new String[allSchematics.size()];
            allSchematics.toArray(_allSchematics);
        }
        return _allSchematics;
    }
    public static boolean storeSecondarySkillBonuses(obj_id prototype, draft_schematic schematic) throws InterruptedException
    {
        draft_schematic.slot[] slots = schematic.getSlots();
        // Cekis: never used - waste of processing.
        // draft_schematic.attribute[] objectAttribs = schematic.getAttribs();
        String category = getProtoTypeCategory(prototype);
        for (int i = 0; i < slots.length; ++i)
        {
            if (!isIdValid(slots[i].ingredients[0].ingredient) || !exists(slots[i].ingredients[0].ingredient))
            {
                continue;
            }
            if (hasObjVar(slots[i].ingredients[0].ingredient, OBJVAR_RE_STAT_MODIFIED))
            {
                LOG("crafting", "storeSecondarySkillBonuses::ingredient " + slots[i].ingredients[0].ingredient + " has the objvar " + OBJVAR_RE_STAT_MODIFIED);
                String statModified = getStringObjVar(slots[i].ingredients[0].ingredient, OBJVAR_RE_STAT_MODIFIED);
                LOG("crafting", "storeSecondarySkillBonuses::statModified " + statModified);
                float power = getFloatObjVar(slots[i].ingredients[0].ingredient, OBJVAR_RE_VALUE);
                LOG("crafting", "storeSecondarySkillBonuses::power " + power);
                int ratio = getIntObjVar(slots[i].ingredients[0].ingredient, OBJVAR_RE_RATIO);
                LOG("crafting", "storeSecondarySkillBonuses::ratio " + ratio);
                power = power / ratio;
                LOG("crafting", "storeSecondarySkillBonuses::power after ratio divisor " + power);
                LOG("crafting", "storeSecondarySkillBonuses::power after changed to Int " + (int)power);
                setCategorizedSkillModBonus(prototype, category, statModified, (int)power);
            }
        }
        return true;
    }
    public static String getProtoTypeCategory(obj_id prototype) throws InterruptedException
    {
        String category = "";
        if (weapons.isCoredWeapon(prototype))
        {
            category = "weapon";
        }
        else 
        {
            category = "armor";
        }
        return category;
    }
    public static boolean validateReComponent(obj_id player, obj_id resource) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return false;
        }
        if (!isIdValid(resource) || !exists(resource))
        {
            return false;
        }
        if (hasObjVar(resource, "reverse_engineering.reverse_engineering_modifier"))
        {
            CustomerServiceLog("new_weapon_crafting", "validateReComponent::ingredient " + resource + " has the objvar reverse_engineering.reverse_engineering_modifier");
            String reStatMod = getStringObjVar(resource, "reverse_engineering.reverse_engineering_modifier");
            if (!reverse_engineering.canMakePowerUp(reStatMod))
            {
                CustomerServiceLog("new_weapon_crafting", "validateReComponent::Player " + getFirstName(player) + "(" + player + ") has tried to use an invalid RE modifier. We are killing the crafting process.");
                return false;
            }
        }
        return true;
    }
    public static boolean validateReComponent(obj_id player, draft_schematic schematic) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return false;
        }
        draft_schematic.slot[] slots = schematic.getSlots();
        int ratio = 0;
        int powerMod = 0;
        obj_id powerBitId = obj_id.NULL_ID;
        String reStatMod;
        obj_id ingredient;
        for (draft_schematic.slot slot : slots) {
            ingredient = slot.ingredients[0].ingredient;
            if (!isIdValid(ingredient) || !exists(ingredient)) {
                continue;
            }
            if (hasObjVar(ingredient, "reverse_engineering.reverse_engineering_modifier")) {
                CustomerServiceLog("new_weapon_crafting", "ingredient " + ingredient + " has the objvar reverse_engineering.reverse_engineering_modifier");
                reStatMod = getStringObjVar(ingredient, "reverse_engineering.reverse_engineering_modifier");
                ratio = getIntObjVar(ingredient, "reverse_engineering.reverse_engineering_ratio");
                if (!reverse_engineering.canMakePowerUp(reStatMod)) {
                    CustomerServiceLog("new_weapon_crafting", "Player " + getFirstName(player) + "(" + player + ") has tried to use an invalid RE modifier. We are killing the crafting process.");
                    sui.msgbox(player, new string_id("spam", "bad_re_modifier"));
                    return false;
                }
            }
            if (hasObjVar(ingredient, "reverse_engineering.reverse_engineering_power")) {
                powerBitId = ingredient;
                powerMod = getIntObjVar(ingredient, "reverse_engineering.reverse_engineering_power");
            }
        }
        float modifiedPower = 0.0f;
        modifiedPower = powerMod * RE_POWER_MODIFIER;
        modifiedPower = modifiedPower / ratio;
        if (modifiedPower < 1)
        {
            prose_package pp = new prose_package();
            prose.setStringId(pp, new string_id("spam", "low_re_power"));
            prose.setDF(pp, modifiedPower);
            prose.setDI(pp, ratio);
            prose.setTO(pp, Float.toString(RE_POWER_MODIFIER * 100));
            sui.msgbox(player, pp);
            CustomerServiceLog("new_weapon_crafting", "Ingredient " + powerBitId + " has too low reverse engineering power for the modified stat. Refusing to allow Player " + getFirstName(player) + "(" + player + ") to craft.");
            CustomerServiceLog("new_weapon_crafting", "Ingredient " + powerBitId + " would have a modified power level of " + modifiedPower + ". Notifying Player " + getFirstName(player) + "(" + player + ") to craft.");
            return false;
        }
        return true;
    }
    public static boolean updatePermanentSchematics(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return false;
        }
        if (hasObjVar(player, "schematicsUpdateVersion"))
        {
            int version = getIntObjVar(player, "schematicsUpdateVersion");
            CustomerServiceLog("new_weapon_conversion", "updatePermanentSchematics::player " + getFirstName(player) + "(" + player + ") has already been updated.");
            if (version == OBJVAR_SCHEM_VERSION)
            {
                return true;
            }
        }
        int[] schemsList = getSchematicListingForPlayer(player);
        if (schemsList == null || schemsList.length <= 0)
        {
            return false;
        }
        for (int i = 0; i < schemsList.length; ++i)
        {
            int row = dataTableSearchColumnForInt(schemsList[i], "crc_schematic_name", weapons.WEAPON_DATA_TABLE);
            if (row < 0)
            {
                continue;
            }
            String schemToGrant = dataTableGetString(weapons.WEAPON_DATA_TABLE, row, "schematic_conversion");
            if (schemToGrant != null && !schemToGrant.equals(""))
            {
                if (!grantSchematic(player, schemToGrant))
                {
                    CustomerServiceLog("new_weapon_conversion", "updatePermanentSchematics::player " + getFirstName(player) + "(" + player + ") was NOT granted schematic " + schemToGrant + ", the grantSchematic call returned a false.");
                    continue;
                }
                CustomerServiceLog("new_weapon_conversion", "updatePermanentSchematics::player " + getFirstName(player) + "(" + player + ") was granted schematic " + schemToGrant);
                revokeSchematic(player, schemsList[i]);
            }
        }
        setObjVar(player, "schematicsUpdateVersion", OBJVAR_SCHEM_VERSION);
        return true;
    }
    public static String getCraftingStationBuffName(int craftingType) throws InterruptedException
    {
        if (craftingType <= 0)
        {
            return null;
        }
        if (craftingType == STATION_TYPE_FOOD)
        {
            return "food_station";
        }
        if (craftingType == STATION_TYPE_ARMOR)
        {
            return "armor_station";
        }
        if (craftingType == STATION_TYPE_STRUCTURE)
        {
            return "structure_station";
        }
        if (craftingType == STATION_TYPE_WEAPON || craftingType == STATION_TYPE_WEAPON_TOOL)
        {
            return "weapon_station";
        }
        if (craftingType == STATION_TYPE_SPACE)
        {
            return "space_station";
        }
        if (craftingType == STATION_TYPE_FOOD_AND_STRUCTURE)
        {
            return "food_station,structure_station,tcg_diner_crafting_station";
        }
        return null;
    }
    public static String[] getAllStationBuffNames(int craftingType) throws InterruptedException
    {
        if (craftingType < 0)
        {
            return null;
        }
        String buffs = getCraftingStationBuffName(craftingType);
        if (buffs == null || buffs.equals(""))
        {
            return null;
        }
        return split(buffs, ',');
    }
    public static void makeBestResource(obj_id player, String resource, int quantity) throws InterruptedException
    {
        obj_id[] resourceTypes = getAllResourceChildren(resource);
        if (resourceTypes == null || resourceTypes.length == 0)
        {
            return;
        }
        if (quantity <= 0)
        {
            return;
        }
        if (quantity > 1000000)
        {
            quantity = 1000000;
        }
        obj_id best = findBestResourceByAverage(resourceTypes);
        obj_id inv = utils.getInventoryContainer(player);
        obj_id resourceCrate = createResourceCrate(best, quantity, inv);
    }
    public static obj_id[] getAllResourceChildren(String resourceClass) throws InterruptedException
    {
        obj_id[] resources = getResourceTypes(resourceClass);
        String[] resourceChildren = getImmediateResourceChildClasses(resourceClass);
        for (int i = 0; i < resourceChildren.length; i++)
        {
            obj_id[] nextResources = getAllResourceChildren(resourceChildren[i]);
            resources = utils.concatArrays(resources, nextResources);
        }
        return resources;
    }
    public static obj_id findBestResourceByAverage(obj_id[] resourceTypes) throws InterruptedException
    {
        int bestIndex = 0;
        int bestAverage = 0;
        for (int i = 0; i < resourceTypes.length; i++)
        {
            resource_attribute[] resourceAttribs = getResourceAttributes(resourceTypes[i]);
            int resourceAverage = 0;
            for (int j = 0; j < resourceAttribs.length; j++)
            {
                resourceAverage += resourceAttribs[j].getValue();
            }
            resourceAverage = resourceAverage / resourceAttribs.length;
            if (bestAverage < resourceAverage)
            {
                bestIndex = i;
                bestAverage = resourceAverage;
            }
        }
        return resourceTypes[bestIndex];
    }
    public static void makeBestResourceByAttribute(obj_id player, String resource, String attribute, int quantity) throws InterruptedException
    {
        obj_id[] resourceTypes = getAllResourceChildren(resource);
        if (resourceTypes == null || resourceTypes.length == 0)
        {
            return;
        }
        if (quantity <= 0)
        {
            return;
        }
        if (quantity > 1000000)
        {
            quantity = 1000000;
        }
        obj_id best = findBestResourceByAttribute(resourceTypes, attribute);
        obj_id inv = utils.getInventoryContainer(player);
        obj_id resourceCrate = createResourceCrate(best, quantity, inv);
    }
    public static obj_id findBestResourceByAttribute(obj_id[] resourceTypes, String attributeName) throws InterruptedException
    {
        int bestIndex = 0;
        int bestValue = 0;
        for (int i = 0; i < resourceTypes.length; i++)
        {
            resource_attribute[] resourceAttribs = getResourceAttributes(resourceTypes[i]);
            int resourceValue = 0;
            for (int j = 0; j < resourceAttribs.length; j++)
            {
                if (attributeName.equals(resourceAttribs[j].getName()))
                {
                    resourceValue = resourceAttribs[j].getValue();
                }
            }
            if (bestValue < resourceValue)
            {
                bestIndex = i;
                bestValue = resourceValue;
            }
        }
        return resourceTypes[bestIndex];
    }
    public static String[] getResourceAttribNames(obj_id resource) throws InterruptedException
    {
        resource_attribute[] resourceAttribs = getResourceAttributes(resource);
        if (resourceAttribs == null || resourceAttribs.length <= 0)
        {
            return null;
        }
        String[] tempAttribNames = new String[resourceAttribs.length];
        for (int i = 0, j = resourceAttribs.length; i < j; i++)
        {
            tempAttribNames[i] = resourceAttribs[i].getName();
        }
        return tempAttribNames;
    }
    public static String[] getAttribNamesByResourceClass(String resourceClass) throws InterruptedException
    {
        obj_id[] resources = getResourceTypes(resourceClass);
        String[] resourceChildren = getImmediateResourceChildClasses(resourceClass);
        for (int i = 0; i < resourceChildren.length; i++)
        {
            obj_id[] nextResources = getAllResourceChildren(resourceChildren[i]);
            resources = utils.concatArrays(resources, nextResources);
        }
        for (int i = 0, j = resources.length; i < j; i++)
        {
            String[] tempAttribNames = getResourceAttribNames(resources[i]);
            if (tempAttribNames != null && tempAttribNames.length > 0)
            {
                return tempAttribNames;
            }
        }
        return null;
    }
    public static boolean isTrader(obj_id who) throws InterruptedException
    {
        if (!isIdValid(who) || !exists(who))
        {
            return false;
        }
        String profession = skill.getProfessionName(getSkillTemplate(who));
        if (profession.equals("trader"))
        {
            return true;
        }
        return false;
    }
}
