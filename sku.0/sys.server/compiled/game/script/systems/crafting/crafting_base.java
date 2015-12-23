package script.systems.crafting;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.craftinglib;
import script.library.collection;
import script.library.weapons;
import script.library.temp_schematic;
import script.library.xp;
import script.library.sui;

public class crafting_base extends script.base_script
{
    public crafting_base()
    {
    }
    public static final String VERSION = "v0.00.00";
    public String[] getRequiredSkills() throws InterruptedException
    {
        LOG("crafting", "Called into crafting_base script getRequiredSkills()! This function should have been overridden!");
        return null;
    }
    public String[] getAssemblySkillMods() throws InterruptedException
    {
        LOG("crafting", "Called into crafting_base script getAssemblySkillMods()! This function should have been overridden!");
        return null;
    }
    public String[] getExperimentSkillMods() throws InterruptedException
    {
        LOG("crafting", "Called into crafting_base script getExperimentSkillMods()! This function should have been overridden!");
        return null;
    }
    public resource_weight[] getResourceMaxResourceWeights() throws InterruptedException
    {
        LOG("crafting", "Called into craftin_base script getResourceMaxResourceWeights()! This function should have been overridden!");
        return null;
    }
    public resource_weight[] getAssemblyResourceWeights() throws InterruptedException
    {
        LOG("crafting", "Called into crafting_base script getAssemblyResourceWeights()! This function should have been overridden!");
        return null;
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
        LOG("crafting", "Called into crafting_base script calcAndSetPrototypeProperties()! This function should have been overridden!");
    }
    public String[] getAppearances(obj_id player, draft_schematic.slot[] slots) throws InterruptedException
    {
        LOG("crafting", "Called into crafting_base script getAppearances()! This function should have been overridden!");
        return null;
    }
    public draft_schematic.custom[] getCustomizations(obj_id player, draft_schematic.custom[] customizations) throws InterruptedException
    {
        LOG("crafting", "Called into crafting_base script getCustomizations()! This function should have been overridden!");
        return null;
    }
    public void modifyForCustomAppearance(obj_id self, obj_id newObject) throws InterruptedException
    {
    }
    public boolean calcAndSetPrototypeProperty(obj_id prototype, draft_schematic.attribute itemAttribute) throws InterruptedException
    {
        if (itemAttribute == null)
        {
            return false;
        }
        boolean result = false;
        if (itemAttribute.name.equals("complexity"))
        {
            setComplexity(prototype, itemAttribute.currentValue);
            setComplexity(getSelf(), itemAttribute.currentValue);
            result = true;
        }
        else if (itemAttribute.name.equals("hitPoints"))
        {
            setMaxHitpoints(prototype, (int)itemAttribute.currentValue);
            result = true;
        }
        else if (itemAttribute.name.equals("xp"))
        {
            utils.setScriptVar(prototype, "crafting.creator.xp", (int)(itemAttribute.currentValue + 0.5f));
            result = true;
        }
        else if (itemAttribute.name.equals("sockets"))
        {
            if ((int)itemAttribute.currentValue > 0 && getGameObjectType(prototype) != GOT_misc_container_wearable)
            {
                if (getGameObjectType(prototype) == GOT_cybernetic_legs || getGameObjectType(prototype) == GOT_cybernetic_arm)
                {
                    setSkillModSockets(prototype, 2);
                }
                else if (getGameObjectType(prototype) == GOT_cybernetic_forearm)
                {
                    setSkillModSockets(prototype, 1);
                }
                else if (getGameObjectType(prototype) == GOT_cybernetic_hand)
                {
                    setSkillModSockets(prototype, 0);
                }
                else 
                {
                    setSkillModSockets(prototype, 1);
                }
            }
            else 
            {
                setSkillModSockets(prototype, 0);
            }
            result = true;
        }
        else if ((itemAttribute.name.getTable()).equals(craftinglib.OBJVAR_SKILLMOD_BONUS))
        {
            setSkillModBonus(prototype, itemAttribute.name.getAsciiId(), (int)itemAttribute.currentValue);
            result = true;
        }
        return result;
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        debugServerConsoleMsg(null, "*** crafting base calcAndSetPrototypeProperties enter ***");
        obj_var_list componentData = getObjVarList(getSelf(), craftinglib.COMPONENT_ATTRIBUTE_INTERNAL_OBJVAR_NAME);
        if (componentData != null)
        {
            for (int i = 0; i < itemAttributes.length; ++i)
            {
                if (itemAttributes[i] == null)
                {
                    continue;
                }
                obj_var component = componentData.getObjVar((itemAttributes[i].name).getAsciiId());
                if (component != null)
                {
                    if (component.getData() instanceof Integer)
                    {
                        itemAttributes[i].currentValue = (float) component.getIntData();
                    }
                    else if (itemAttributes[i].minValue != itemAttributes[i].maxValue)
                    {
                        debugServerConsoleMsg(null, "updating attrib " + (itemAttributes[i].name).getAsciiId() + ", current value = " + itemAttributes[i].currentValue + " by component value " + component.getFloatData());
                        // Cekis: This may be a bug - why incremenet if a float (in this condition), but just re-assign if an int?
                        itemAttributes[i].currentValue += component.getFloatData();
                    }
                    else 
                    {
                        debugServerConsoleMsg(null, "updating attrib " + (itemAttributes[i].name).getAsciiId() + ", current value = " + itemAttributes[i].currentValue + " by component value " + component.getFloatData());
                        itemAttributes[i].currentValue = itemAttributes[i].minValue + component.getFloatData();
                    }
                }
            }
            if (componentData.hasObjVar(craftinglib.OBJVAR_COMPONENT_ATTRIB_BONUSES))
            {
                float[] componentBonuses = componentData.getFloatArrayObjVar(craftinglib.OBJVAR_COMPONENT_ATTRIB_BONUSES);
                if (componentBonuses != null && componentBonuses.length == NUM_ATTRIBUTES)
                {
                    int[] bonuses = new int[componentBonuses.length];
                    for (int i = 0; i < componentBonuses.length; ++i)
                    {
                        bonuses[i] = (int)(componentBonuses[i]);
                    }
                    setAttributeBonuses(getSelf(), bonuses);
                }
            }
        }
        debugServerConsoleMsg(null, "*** crafting base calcAndSetPrototypeProperties exit ***");
    }
    public void grantExperience(obj_id player, draft_schematic.slot[] slots) throws InterruptedException
    {
        if (slots == null)
        {
            return;
        }
        for (int i = 0; i < slots.length; ++i)
        {
            if (slots[i].ingredients == null)
            {
                continue;
            }
            if (slots[i].ingredientType == draft_schematic.IT_item || slots[i].ingredientType == draft_schematic.IT_template || slots[i].ingredientType == draft_schematic.IT_templateGeneric)
            {
                for (int j = 0; j < slots[i].ingredients.length; ++j)
                {
                    if (isIdValid(slots[i].ingredients[j].source) && slots[i].ingredients[j].source != player)
                    {
                        grantExperiencePoints(slots[i].ingredients[j].source, slots[i].ingredients[j].xpType, slots[i].ingredients[j].xpAmount);
                    }
                }
            }
        }
    }
    public int OnManufacturingSchematicCreation(obj_id self, obj_id player, obj_id prototype, draft_schematic schematic, modifiable_int assemblyResult, modifiable_int experimentPoints) throws InterruptedException
    {
        if (!craftinglib.validateReComponent(player, schematic))
        {
            assemblyResult.set(9);
            endCraftingSession(player, obj_id.NULL_ID, prototype);
            return SCRIPT_CONTINUE;
        }
        if (schematic.getCategory() == CT_genetics || schematic.getCategory() == CT_animalBreeding)
        {
            return SCRIPT_CONTINUE;
        }
        debugServerConsoleMsg(self, "OnManufacturingSchematicCreation enter");
        dictionary craftingValuesDictionary = new dictionary();
        draft_schematic.slot[] slots = schematic.getSlots();
        draft_schematic.attribute[] objectAttribs = schematic.getAttribs();
        draft_schematic.attribute[] experimentalAttribs = schematic.getExperimentalAttribs();
        /* Cekis: pointless loop.
        for (int i = 0; i < objectAttribs.length; ++i)
        {
            float modifiedValue = objectAttribs[i].currentValue;
            String attribName = (objectAttribs[i].name).getAsciiId();
        }
        */
        String[] obj_attributes = new String[objectAttribs.length];
        for (int i = 0; i < obj_attributes.length; ++i)
        {
            obj_attributes[i] = (objectAttribs[i].name).getAsciiId();
        }
        float refactorPercent = 0;
        float refactorComplexity = 0;
        if (hasObjVar(self, craftinglib.OBJVAR_REFACTOR))
        {
            refactorPercent = getFloatObjVar(self, craftinglib.OBJVAR_REFACTOR);
            if (refactorPercent > 0 && refactorPercent < 1.0f)
            {
                debugServerConsoleMsg(null, "Using refactor value " + refactorPercent);
                craftingValuesDictionary.put(craftinglib.OBJVAR_REFACTOR, refactorPercent);
                int myDraftSchematic = getDraftSchematicCrc(self);
                if (myDraftSchematic != 0)
                {
                    for (int i = 0; i < slots.length; ++i)
                    {
                        if (slots[i].ingredientType == draft_schematic.IT_schematic && myDraftSchematic == getSourceDraftSchematic(slots[i].ingredients[0].ingredient))
                        {
                            refactorComplexity = getComplexity(slots[i].ingredients[0].ingredient);
                            break;
                        }
                    }
                }
                for (int i = 0; i < objectAttribs.length; ++i)
                {
                    if (objectAttribs[i].maxValue > objectAttribs[i].minValue && !obj_attributes[i].equals("complexity") && !obj_attributes[i].equals("armorSpecialType"))
                    {
                        debugServerConsoleMsg(null, "Refactoring attribute max " + obj_attributes[i] + " from " + objectAttribs[i].maxValue + " to " + objectAttribs[i].maxValue * refactorPercent);
                        objectAttribs[i].maxValue *= refactorPercent;
                    }
                }
            }
            else 
            {
                CustomerServiceLog("crafting", "WARNING: schematic " + self + " has invalid refactor objvar value " + refactorPercent);
            }
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
            if ((objectAttribs[i].name).equals("complexity"))
            {
                float defaultComplexity = objectAttribs[i].minValue;
                float currentComplexity = objectAttribs[i].currentValue + refactorComplexity;
                craftingValuesDictionary.put("itemDefaultComplexity", defaultComplexity);
                craftingValuesDictionary.put("itemCurrentComplexity", currentComplexity);
                debugServerConsoleMsg(self, "\tdefault complexity " + defaultComplexity + ", current complexity " + currentComplexity);
            }
            else if ((objectAttribs[i].name).equals("armorSpecialType"))
            {
                craftingValuesDictionary.put("itemDefaultArmorSpecialType", objectAttribs[i].minValue);
                craftingValuesDictionary.put("itemCurrentArmorSpecialType", objectAttribs[i].currentValue);
            }
            else if ((objectAttribs[i].name).equals("sockets"))
            {
                int[] mods = getEnhancedSkillStatisticModifiersUncapped(player, getAssemblySkillMods());
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
                        int chance = experimentModTotal - 50;
                        if (chance > rand(1, 100))
                        {
                            sockets = 1;
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
        collection.updateCraftingSlot(player, getTemplateName(prototype));
        int forceCritSuccessCount = getIntObjVar(self, craftinglib.OBJVAR_FORCE_CRITICAL_SUCCESS);
        if (forceCritSuccessCount > 0)
        {
            craftingValuesDictionary.put(craftinglib.OBJVAR_FORCE_CRITICAL_SUCCESS, true);
        }
        int assemblySuccess = craftinglib.calcAssemblyPhaseAttributes(player, slots, objectAttribs, craftingValuesDictionary, getAssemblySkillMods(), getResourceMaxResourceWeights(), getAssemblyResourceWeights());
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
        if (refactorPercent != 0)
        {
            for (int i = 0; i < objectAttribs.length; ++i)
            {
                if (objectAttribs[i].maxValue > objectAttribs[i].minValue && !obj_attributes[i].equals("complexity") && !obj_attributes[i].equals("armorSpecialType"))
                {
                    objectAttribs[i].resourceMaxValue /= refactorPercent;
                    objectAttribs[i].maxValue /= refactorPercent;
                    debugServerConsoleMsg(self, "OnManufacturingSchematicCreation final attrib " + (objectAttribs[i].name).getAsciiId() + " values: current = " + objectAttribs[i].currentValue + ", rmax = " + objectAttribs[i].resourceMaxValue + ", max = " + objectAttribs[i].maxValue);
                }
            }
        }
        if (craftingValuesDictionary.containsKey(craftinglib.OBJVAR_FORCE_CRITICAL_SUCCESS_RESULT))
        {
            setObjVar(self, craftinglib.OBJVAR_FORCE_CRITICAL_SUCCESS, forceCritSuccessCount - 1);
        }
        weapons.storeWeaponCraftingValues(prototype, self, schematic);
        craftinglib.storeSecondarySkillBonuses(prototype, schematic);
        calcAndSetPrototypeProperties(prototype, objectAttribs, craftingValuesDictionary);
        craftinglib.storeTissueDataAsObjvars(craftingValuesDictionary, objectAttribs, prototype, true);
        calcAndSetPrototypeProperties(prototype, objectAttribs);
        return SCRIPT_CONTINUE;
    }
    public int OnCraftingExperiment(obj_id self, obj_id player, obj_id prototype, string_id[] attributeNames, int[] experimentPoints, int totalPoints, modifiable_int experimentResult, int coreLevel) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "skipCraftingExperiment"))
        {
            utils.removeScriptVar(self, "skipCraftingExperiment");
            return SCRIPT_CONTINUE;
        }
        if (doCraftingExperiment(self, player, prototype, attributeNames, experimentPoints, totalPoints, experimentResult, coreLevel) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_OVERRIDE;
    }
    public int doCraftingExperiment(obj_id self, obj_id player, obj_id prototype, string_id[] attributeNames, int[] experimentPoints, int totalPoints, modifiable_int experimentResult, int coreLevel) throws InterruptedException
    {
        debugServerConsoleMsg(self, "OnCraftingExperiment enter");
        draft_schematic schematic = getSchematicForExperimentalAttributes(self, attributeNames);
        if (schematic == null)
        {
            debugServerConsoleMsg(self, "OnCraftingExperiment NO SCHEMATIC");
            return SCRIPT_OVERRIDE;
        }
        draft_schematic.attribute[] objectAttribs = schematic.getAttribs();
        draft_schematic.attribute[] experimentalAttribs = schematic.getExperimentalAttribs();
        if (objectAttribs == null || experimentalAttribs == null)
        {
            debugServerConsoleMsg(self, "OnCraftingExperiment NO ATTRIBS");
            return SCRIPT_OVERRIDE;
        }
        String[] obj_attributes = new String[objectAttribs.length];
        for (int i = 0; i < obj_attributes.length; ++i)
        {
            obj_attributes[i] = (objectAttribs[i].name).getAsciiId();
            if (obj_attributes[i].equals("coreLevel"))
            {
                objectAttribs[i].currentValue = coreLevel;
            }
        }
        dictionary craftingValuesDictionary = new dictionary();
        float refactorPercent = 0;
        float refactorComplexity = 0;
        if (hasObjVar(self, craftinglib.OBJVAR_REFACTOR))
        {
            refactorPercent = getFloatObjVar(self, craftinglib.OBJVAR_REFACTOR);
            if (refactorPercent > 0 && refactorPercent < 1.0f)
            {
                debugServerConsoleMsg(null, "Using refactor value " + refactorPercent);
                craftingValuesDictionary.put(craftinglib.OBJVAR_REFACTOR, refactorPercent);
                for (int i = 0; i < objectAttribs.length; ++i)
                {
                    if (objectAttribs[i].maxValue > objectAttribs[i].minValue && !obj_attributes[i].equals("complexity") && !obj_attributes[i].equals("armorSpecialType"))
                    {
                        debugServerConsoleMsg(null, "Refactoring attribute max " + obj_attributes[i] + " from " + objectAttribs[i].maxValue + " to " + objectAttribs[i].maxValue * refactorPercent);
                        objectAttribs[i].maxValue *= refactorPercent;
                    }
                }
            }
            else 
            {
                CustomerServiceLog("crafting", "WARNING: schematic " + self + " has invalid refactor objvar value " + refactorPercent);
            }
        }
        float[] objExperimentPoints = craftinglib.convertExperimentalPointsToObjectPoints(schematic, attributeNames, experimentPoints);
        if (objExperimentPoints == null)
        {
            debugServerConsoleMsg(self, "OnCraftingExperiment NO POINTS");
            return SCRIPT_OVERRIDE;
        }
        if (getIntObjVar(self, craftinglib.OBJVAR_FORCE_CRITICAL_SUCCESS) > 0)
        {
            craftingValuesDictionary.put(craftinglib.OBJVAR_FORCE_CRITICAL_SUCCESS, true);
        }
        float resourceMalleabilitySkillMod = utils.getFloatScriptVar(self, craftinglib.RESOURCE_MALLEABILITY_SKILL_MOD);
        craftingValuesDictionary.put(craftinglib.RESOURCE_MALLEABILITY_SKILL_MOD, resourceMalleabilitySkillMod);
        craftinglib.calcExpFullSinglePointValue(craftingValuesDictionary, objectAttribs);
        craftinglib.calcPerExperimentationCheckMod(player, craftingValuesDictionary, experimentPoints.length, objectAttribs, getExperimentSkillMods());
        craftinglib.calcSuccessPerAttributeExperimentation(craftingValuesDictionary, objExperimentPoints, objectAttribs, player, getRequiredSkills());
        craftinglib.calcExperimentalAttribs(schematic);
        experimentResult.set(craftingValuesDictionary.getInt("experimentSuccessState"));
        for (int i = 0; i < objectAttribs.length; ++i)
        {
            if (objectAttribs[i] != null && (objectAttribs[i].name).equals("complexity"))
            {
                ++objectAttribs[i].currentValue;
                break;
            }
        }
        debugServerConsoleMsg(self, "OnCraftingExperiment setting current attributes");
        if (setSchematicAttributes(self, objectAttribs))
        {
            debugServerConsoleMsg(self, "OnCraftingExperiment set initial attributes of schematic");
        }
        else 
        {
            debugServerConsoleMsg(self, "OnCraftingExperiment failed to set initial attributes of schematic!");
        }
        debugServerConsoleMsg(self, "OnCraftingExperiment setting experimental attributes");
        if (setSchematicExperimentalAttributes(self, experimentalAttribs))
        {
            debugServerConsoleMsg(self, "OnCraftingExperiment set experimental attributes of schematic");
        }
        else 
        {
            debugServerConsoleMsg(self, "OnCraftingExperiment failed to set experimental attributes of schematic!");
        }
        if (refactorPercent != 0)
        {
            for (int i = 0; i < objectAttribs.length; ++i)
            {
                if (objectAttribs[i].maxValue > objectAttribs[i].minValue && !obj_attributes[i].equals("complexity") && !obj_attributes[i].equals("armorSpecialType"))
                {
                    objectAttribs[i].resourceMaxValue /= refactorPercent;
                    objectAttribs[i].maxValue /= refactorPercent;
                }
            }
        }
        if (craftingValuesDictionary.containsKey(craftinglib.OBJVAR_FORCE_CRITICAL_SUCCESS_RESULT))
        {
            setObjVar(self, craftinglib.OBJVAR_FORCE_CRITICAL_SUCCESS, getIntObjVar(self, craftinglib.OBJVAR_FORCE_CRITICAL_SUCCESS) - 1);
        }
        calcAndSetPrototypeProperties(prototype, objectAttribs, craftingValuesDictionary);
        craftinglib.storeTissueDataAsObjvars(craftingValuesDictionary, objectAttribs, prototype, true);
        calcAndSetPrototypeProperties(prototype, objectAttribs);
        return SCRIPT_CONTINUE;
    }
    public int OnFinalizeSchematic(obj_id self, obj_id player, obj_id prototype, draft_schematic schematic) throws InterruptedException
    {
        int myDraftSchematic = getDraftSchematicCrc(self);
        obj_id bioLink = temp_schematic.getBioLinkSchematicId(player, myDraftSchematic);
        if (isIdValid(bioLink))
        {
            setBioLink(prototype, bioLink);
        }
        draft_schematic.attribute[] objectAttribs = schematic.getAttribs();
        dictionary craftingValuesDictionary = new dictionary();
        /* Cekis: this is a totally pointless loop!  Commenting out for now.
        for (int i = 0; i < objectAttribs.length; ++i)
        {
            float modifiedValue = objectAttribs[i].currentValue;
            String attribName = (objectAttribs[i].name).getAsciiId();
        }
        */
        calcAndSetPrototypeProperties(prototype, objectAttribs, craftingValuesDictionary);
        setSchematicAttributes(self, objectAttribs);
        craftinglib.storeTissueDataAsObjvars(craftingValuesDictionary, objectAttribs, prototype, true);
        weapons.storeWeaponCraftingValues(prototype, self, schematic);
        weapons.setSchematicVariablesFromSchematic(self, prototype);
        removeObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_INTERNAL_OBJVAR_NAME);
        return SCRIPT_CONTINUE;
    }
    public int getInspirationBuffXpBonus(obj_id self, int category, int amt) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "buff.general_inspiration.value"))
        {
            float mod = utils.getFloatScriptVar(self, "buff.general_inspiration.value");
            mod /= 100f;
            float modAmt = (float)amt * mod;
            return Math.round(modAmt);
        }
        if (!utils.hasScriptVar(self, "buff.xpBonus.types") && !utils.hasScriptVar(self, "buff.xpBonusGeneral.types"))
        {
            return 0;
        }
        if (!utils.hasScriptVar(self, "buff.craftBonus.types"))
        {
            return 0;
        }
        int flags = utils.getIntScriptVar(self, "buff.craftBonus.types");
        if ((flags & category) > 0)
        {
            float mod = 0;
            if (utils.hasScriptVar(self, "buff.xpBonus.value"))
            {
                mod += utils.getFloatScriptVar(self, "buff.xpBonus.value");
            }
            if (utils.hasScriptVar(self, "buff.xpBonusGeneral.value"))
            {
                mod += utils.getFloatScriptVar(self, "buff.xpBonusGeneral.value");
            }
            float modAmt = (float)amt * mod;
            return Math.round(modAmt);
        }
        return 0;
    }
    public int OnManufactureObject(obj_id self, obj_id player, obj_id newObject, draft_schematic schematic, boolean isPrototype, boolean isRealObject) throws InterruptedException
    {
        /* Cekis: unused vars and a pointless loop.  Commenting out for now.
        draft_schematic.attribute[] objectAttribs = schematic.getAttribs();
        draft_schematic.attribute[] experimentalAttribs = schematic.getExperimentalAttribs();
        for (int i = 0; i < objectAttribs.length; ++i)
        {
            float modifiedValue = objectAttribs[i].currentValue;
            String attribName = (objectAttribs[i].name).getAsciiId();
        }
        */
        if (isPrototype)
        {
            OnFinalizeSchematic(self, player, newObject, schematic);
        }
        calcAndSetPrototypeProperties(newObject, schematic.getAttribs());
        if (getSkillModSockets(newObject) > 0)
        {
            setCondition(newObject, CONDITION_MAGIC_ITEM);
        }
        int[] bonuses = getAttributeBonuses(self);
        if (bonuses != null)
        {
            setAttributeBonuses(newObject, bonuses);
        }
        if (isIdValid(player))
        {
            obj_id id = getObjIdObjVar(newObject, "crafting.creator.id");
            String name = getStringObjVar(newObject, "crafting.creator.name");
            String xpType = getStringExperienceType(getIntObjVar(newObject, "crafting.creator.xpType"));
            int xpAmount = utils.getIntScriptVar(newObject, "crafting.creator.xp");
            int buffXP = getInspirationBuffXpBonus(player, schematic.getCategory(), xpAmount);
            xpAmount += buffXP;
            if (!isPrototype)
            {
                xpAmount = (int)(xpAmount * 0.1);
            }
            else if (!isRealObject)
            {
                xpAmount = (int)(xpAmount * 1.05);
            }
            if (xpType != null)
            {
                xp.grantCraftingStyleXp(player, xpType, xpAmount);
            }
            setObjVar(newObject, "crafting.creator.id", id);
            setObjVar(newObject, "crafting.creator.name", name);
            String expertiseSkill = craftinglib.getCraftingSubskill(getRequiredSkills());
            int complexityDecrease = (int)getSkillStatisticModifier(player, "expertise_complexity_decrease_" + expertiseSkill);
            if (complexityDecrease > 0)
            {
                int newComplexity = (int)getComplexity(newObject) - complexityDecrease;
                if (newComplexity > 0)
                {
                    setComplexity(newObject, newComplexity);
                    setComplexity(getSelf(), newComplexity);
                }
                else 
                {
                    setComplexity(newObject, 1);
                    setComplexity(getSelf(), 1);
                }
            }
        }
        if (hasObjVar(self, "customAppearance"))
        {
            modifyForCustomAppearance(self, newObject);
        }
        weapons.validateDamage(player, newObject);
        return SCRIPT_CONTINUE;
    }
    public int OnMakeCraftedItem(obj_id self, obj_id prototype, draft_schematic schematic, float qualityPercent) throws InterruptedException
    {
        draft_schematic.attribute[] objectAttribs = schematic.getAttribs();
        /* Cekis: look kids!  Pointless vars and Another pointless loop!  Commenting out for now.
        draft_schematic.attribute[] experimentalAttribs = schematic.getExperimentalAttribs();
        for (int i = 0; i < objectAttribs.length; ++i)
        {
            float modifiedValue = objectAttribs[i].currentValue;
            String attribName = (objectAttribs[i].name).getAsciiId();
        }
        */
        debugServerConsoleMsg(null, "OnMakeCraftedItem enter, quality = " + qualityPercent);
        dictionary craftingValuesDictionary = new dictionary();
        for (int i = 0; i < objectAttribs.length; ++i)
        {
            objectAttribs[i].currentValue = objectAttribs[i].maxValue * (qualityPercent / 100.0f);
        }
        setSchematicAttributes(self, objectAttribs);
        calcAndSetPrototypeProperties(prototype, objectAttribs);
        OnManufactureObject(self, null, prototype, schematic, true, true);
        debugServerConsoleMsg(null, "OnMakeCraftedItem exit");
        return SCRIPT_CONTINUE;
    }
    public int OnRequestResourceWeights(obj_id self, obj_id player, String[] desiredAttribs, String[] attribs, int[] slots, int[] counts, int[] data) throws InterruptedException
    {
        int attribIndex = 0;
        int dataIndex = 0;
        java.util.HashSet attribSet = new java.util.HashSet(java.util.Arrays.asList(desiredAttribs));
        resource_weight[] weights = getAssemblyResourceWeights();
        for (int i = 0; i < 2; ++i)
        {
            if (weights == null)
            {
                return SCRIPT_OVERRIDE;
            }
            for (int j = 0; j < weights.length; ++j)
            {
                if (attribSet.contains(weights[j].attribName))
                {
                    attribs[attribIndex] = weights[j].attribName;
                    slots[attribIndex] = weights[j].slot;
                    counts[attribIndex++] = weights[j].weights.length;
                    resource_weight.weight[] resources = weights[j].weights;
                    for (int k = 0; k < resources.length; ++k)
                    {
                        data[dataIndex++] = resources[k].resource;
                        data[dataIndex++] = resources[k].weight;
                    }
                }
            }
            if (i == 0)
            {
                weights = getResourceMaxResourceWeights();
            }
        }
        return SCRIPT_CONTINUE;
    }
}
