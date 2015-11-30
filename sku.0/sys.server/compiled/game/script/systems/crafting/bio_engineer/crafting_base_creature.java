package script.systems.crafting.bio_engineer;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.craftinglib;
import script.library.consumable;
import script.library.player_stomach;
import script.library.bio_engineer;
import script.library.create;
import script.library.pet_lib;

public class crafting_base_creature extends script.systems.crafting.crafting_base
{
    public crafting_base_creature()
    {
    }
    public static final String VERSION = "v1.00.00";
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        super.calcAndSetPrototypeProperties(prototype, itemAttributes, craftingValuesDictionary);
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
        obj_id self = getSelf();
        String root = craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".";
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
        float dnaAttrib = 0;
        for (int i = 0; i < itemAttributes.length; ++i)
        {
            if (itemAttributes[i] == null)
            {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttributes[i]))
            {
                setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttributes[i].name).getAsciiId(), itemAttributes[i].currentValue);
            }
        }
        setObjVar(prototype, "creature_attribs.type", getCreatureName());
        for (int i = 0; i < dna_attributes.length; i++)
        {
            LOG("creature_crafting", root + bio_engineer.CREATURE_ATTRIB_OBJVAR_NAMES[i] + " = " + getIntObjVar(self, root + bio_engineer.CREATURE_ATTRIB_OBJVAR_NAMES[i]));
            dna_attributes[i] = getIntObjVar(self, root + bio_engineer.CREATURE_ATTRIB_OBJVAR_NAMES[i]);
        }
        int health = bio_engineer.calcCreatureAttrib(HEALTH, dna_attributes);
        setObjVar(prototype, "creature_attribs." + create.MAXATTRIBNAMES[HEALTH], health);
        int healthRegen = bio_engineer.calcCreatureHealthRegen(dna_attributes);
        setObjVar(prototype, "creature_attribs." + create.MAXATTRIBNAMES[CONSTITUTION], healthRegen);
        int toHitChance = bio_engineer.calcCreatureToHit(dna_attributes);
        setObjVar(prototype, "creature_attribs.toHitChance", toHitChance);
        int defenseValue = bio_engineer.calcCreatureDefenseValue(dna_attributes);
        setObjVar(prototype, "creature_attribs.defenseValue", defenseValue);
        float stateResist = bio_engineer.calcCreatureStateResist(dna_attributes);
        setObjVar(prototype, "creature_attribs.stateResist", stateResist);
        float scale = bio_engineer.calcCreatureMaxScale(dna_attributes);
        setObjVar(prototype, "creature_attribs.scale", scale);
        int[] damage = bio_engineer.calcCreatureDamage(dna_attributes);
        setObjVar(prototype, "creature_attribs.minDamage", damage[0]);
        setObjVar(prototype, "creature_attribs.maxDamage", damage[1]);
        float critChance = bio_engineer.calcCreatureCritChance(dna_attributes);
        setObjVar(prototype, "creature_attribs.critChance", critChance);
        float critSave = bio_engineer.calcCreatureCritSave(dna_attributes);
        setObjVar(prototype, "creature_attribs.critSave", critSave);
        float aggroBonus = bio_engineer.calcCreatureAggroBonus(dna_attributes);
        setObjVar(prototype, "creature_attribs.aggroBonus", aggroBonus);
        int armorBase = bio_engineer.calcCreatureArmor(dna_attributes);
        setObjVar(prototype, "creature_attribs.general_protection", armorBase);
        int specialAttack1 = getIntObjVar(self, root + "specialAttack1");
        setObjVar(prototype, "creature_attribs.specialAttack1", specialAttack1);
        int specialAttack2 = getIntObjVar(self, root + "specialAttack2");
        setObjVar(prototype, "creature_attribs.specialAttack2", specialAttack2);
        String rangedWeapon = getStringObjVar(self, root + "rangedWeapon");
        if (rangedWeapon == null)
        {
            rangedWeapon = "";
        }
        setObjVar(prototype, "creature_attribs.rangedWeapon", rangedWeapon);
        int quality = getIntObjVar(self, root + "quality");
        dictionary creatureDict = new dictionary();
        creatureDict.put("maxHealth", health);
        creatureDict.put("healthRegen", healthRegen);
        creatureDict.put("toHitChance", toHitChance);
        creatureDict.put("defenseValue", defenseValue);
        creatureDict.put("stateResist", stateResist);
        creatureDict.put("minDamage", damage[0]);
        creatureDict.put("maxDamage", damage[1]);
        creatureDict.put("armorEffectiveness", armorBase);
        float minScale = dataTableGetFloat("datatables/bio_engineer/creature_ranges.iff", getCreatureName(), "minScale");
        float maxScale = dataTableGetFloat("datatables/bio_engineer/creature_ranges.iff", getCreatureName(), "maxScale");
        int minLevel = dataTableGetInt("datatables/bio_engineer/creature_ranges.iff", getCreatureName(), "minLevel");
        int maxLevel = dataTableGetInt("datatables/bio_engineer/creature_ranges.iff", getCreatureName(), "maxLevel");
        float scaleRange = maxScale - minScale;
        float levelRange = maxLevel - minLevel;
        LOG("creature_crafting", "Scale Range = " + scaleRange + " (" + minScale + "/" + maxScale + ")");
        LOG("creature_crafting", "Level Range = " + levelRange + " (" + minLevel + "/" + maxLevel + ")");
        int level = bio_engineer.getCraftedCreatureLevel(creatureDict);
        if (level < minLevel)
        {
            level = minLevel;
        }
        float levelRank = 1 - ((float)(maxLevel - level) / levelRange);
        LOG("creature_crafting", "Level Rank = " + levelRank);
        float finalScale = minScale + (scaleRange * levelRank);
        setObjVar(prototype, "creature_attribs.scale", finalScale);
        LOG("creature_crafting", "Final Scale = " + finalScale);
        int finalLevel = level - (int)(level * (quality / 100) + 0.5f);
        setObjVar(prototype, "creature_attribs.level", finalLevel);
    }
    public String getCreatureName() throws InterruptedException
    {
        LOG("crafting", "Called into crafting_base script getSlotResourceWeights()! This function should have been overridden!");
        return null;
    }
    public int OnManufactureObject(obj_id self, obj_id player, obj_id newObject, draft_schematic schematic, boolean isPrototype, boolean isRealObject) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
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
            else 
            {
            }
            debugServerConsoleMsg(self, "\tattrib " + objectAttribs[i].name + ", values = " + objectAttribs[i].minValue + ".." + objectAttribs[i].maxValue);
        }
        grantExperience(player, slots);
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
        calcAndSetPrototypeProperties(prototype, objectAttribs, craftingValuesDictionary);
        craftinglib.storeTissueDataAsObjvars(craftingValuesDictionary, objectAttribs, prototype, true);
        String root_internal = craftinglib.COMPONENT_ATTRIBUTE_INTERNAL_OBJVAR_NAME + ".";
        String root = craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".";
        for (int i = 0; i < bio_engineer.CREATURE_ATTRIB_NUM; i++)
        {
            int attrib = (int)getFloatObjVar(self, root_internal + bio_engineer.CREATURE_ATTRIB_OBJVAR_NAMES[i]);
            setObjVar(self, root + bio_engineer.CREATURE_ATTRIB_OBJVAR_NAMES[i], attrib);
        }
        int quality = getIntObjVar(self, root_internal + "quality");
        setObjVar(self, root + "quality", quality);
        int specialAttack1 = getIntObjVar(self, root_internal + "specialAttack1");
        setObjVar(self, root + "specialAttack1", specialAttack1);
        int specialAttack2 = getIntObjVar(self, root_internal + "specialAttack2");
        setObjVar(self, root + "specialAttack2", specialAttack2);
        String rangedWeapon = getStringObjVar(self, root_internal + "rangedWeapon");
        if (rangedWeapon == null)
        {
            rangedWeapon = "";
        }
        setObjVar(self, root + "rangedWeapon", rangedWeapon);
        calcAndSetPrototypeProperties(prototype, objectAttribs);
        return SCRIPT_CONTINUE;
    }
}
