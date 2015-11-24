package script.systems.beast;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.hue;
import script.library.utils;

public class enzyme_crafting_base extends script.base_script
{
    public enzyme_crafting_base()
    {
    }
    public static final int ITEM_CENTRIFUGE = 0;
    public static final int ITEM_PROCESSOR = 1;
    public static final int ITEM_COMBINER = 2;
    public static final int CENTRIFUGE_RUNTIME = 60 * 60 * 3;
    public static final int PROCESSOR_RUNTIME = 60 * 60 * 2;
    public static final int COMBINER_RUNTIME = 60 * 60 * 3;
    public static final float SINGLE_STAGE_CAP = 4.6f;
    public static final String ENZYME_TYPE_3 = "object/tangible/loot/beast/enzyme_3.iff";
    public static final String[] ENZYME_ELEMENT_LIST = 
    {
        "object/tangible/loot/beast/enzyme_3_element_1.iff",
        "object/tangible/loot/beast/enzyme_3_element_2.iff",
        "object/tangible/loot/beast/enzyme_3_element_3.iff",
        "object/tangible/loot/beast/enzyme_3_element_4.iff",
        "object/tangible/loot/beast/enzyme_3_element_5.iff"
    };
    public static final String CENTRIFUGE_USE = "object/tangible/component/genetic_engineering/centrifuge_use_tray.iff";
    public static final String PROCESSOR_USE = "object/tangible/component/genetic_engineering/processor_use_mold.iff";
    public static final String COMBINER_USE = "object/tangible/component/genetic_engineering/combiner_use_capsule.iff";
    public static final String CRAFTED_PURITY = "crafting.enzyme_purity";
    public static final String CRAFTED_MUTAGEN = "crafting.enzyme_mutagen";
    public static final String ENZYME_PURITY = "enzyme.enzyme_purity";
    public static final String ENZYME_MUTAGEN = "enzyme.enzyme_mutagen";
    public static final String ENZYME_TRAIT = "enzyme.enzyme_trait";
    public static final String ENZYME_PROCESSED = "enzyme.enzyme_processed";
    public static final String ELEMENT_PROCESSED = "enzyme.element_processed";
    public static final String SYSTEM = "system";
    public static final String FINISH_TIME = "system.finish_time";
    public static final String PROCESS_PURITY = "system.enzyme_purity";
    public static final String PROCESS_MUTAGEN = "system.enzyme_mutagen";
    public static final String PROCESS_TRAITS = "system.enzyme_traits";
    public static final string_id ERROR_ON_START = new string_id("beast", "error_on_start");
    public static final string_id NOT_BEASTMASTER = new string_id("beast", "beast_master_use_only");
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if (!canBeTransfered())
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToLoseItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (isInOperation())
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (isInOperation() && (isIdValid(transferer) && isPlayer(transferer)))
        {
            return SCRIPT_OVERRIDE;
        }
        else if (!isValidComponentForMachine(item))
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int getMachineType() throws InterruptedException
    {
        String template = utils.getTemplateFilenameNoPath(getSelf());
        if (template == null || template.equals(""))
        {
            return -1;
        }
        if (template.startsWith("centrifuge"))
        {
            return ITEM_CENTRIFUGE;
        }
        if (template.startsWith("processor"))
        {
            return ITEM_PROCESSOR;
        }
        if (template.startsWith("combiner"))
        {
            return ITEM_COMBINER;
        }
        return -1;
    }
    public boolean isInOperation() throws InterruptedException
    {
        return hasObjVar(getSelf(), FINISH_TIME);
    }
    public void completeAnyOperations() throws InterruptedException
    {
        if (!hasObjVar(getSelf(), FINISH_TIME))
        {
            return;
        }
        int finishTime = getIntObjVar(getSelf(), FINISH_TIME);
        if (getGameTime() < finishTime)
        {
            return;
        }
        completeProcess();
    }
    public void terminateProcess() throws InterruptedException
    {
        removeObjVar(getSelf(), SYSTEM);
    }
    public boolean canBeTransfered() throws InterruptedException
    {
        return (!isInOperation() && !containsItems());
    }
    public boolean isInValidOperatingLocation() throws InterruptedException
    {
        return utils.isInHouseCellSpace(getSelf());
    }
    public boolean containsItems() throws InterruptedException
    {
        return (getContents(getSelf()) != null && getContents(getSelf()).length > 0);
    }
    public int getTimeToFinishInt() throws InterruptedException
    {
        int timeAtComplete = hasObjVar(getSelf(), FINISH_TIME) ? getIntObjVar(getSelf(), FINISH_TIME) : -1;
        if (timeAtComplete == -1)
        {
            return -1;
        }
        return timeAtComplete - getGameTime();
    }
    public String getTimeToFinishString() throws InterruptedException
    {
        return utils.formatTimeVerbose(getTimeToFinishInt());
    }
    public boolean isValidComponentForMachine(obj_id passedItem) throws InterruptedException
    {
        String itemToCheck = getTemplateName(passedItem);
        int machineType = getMachineType();
        boolean isValidItem = false;
        switch (machineType)
        {
            case ITEM_CENTRIFUGE:
            if (itemToCheck.equals(ENZYME_TYPE_3) && !isEnzymeProcessed(passedItem))
            {
                isValidItem = true;
            }
            else if (itemToCheck.equals(CENTRIFUGE_USE))
            {
                isValidItem = true;
            }
            else if (getElementType(passedItem) > 0)
            {
                isValidItem = true;
            }
            break;
            case ITEM_PROCESSOR:
            if (getElementType(passedItem) > 0 && !isElementProcessed(passedItem))
            {
                isValidItem = true;
            }
            else if (itemToCheck.equals(PROCESSOR_USE))
            {
                isValidItem = true;
            }
            break;
            case ITEM_COMBINER:
            if (getElementType(passedItem) > 0 && isElementProcessed(passedItem))
            {
                isValidItem = true;
            }
            else if (itemToCheck.equals(COMBINER_USE))
            {
                isValidItem = true;
            }
            else if (itemToCheck.equals(ENZYME_TYPE_3))
            {
                isValidItem = true;
            }
            break;
            default:
            break;
        }
        return isValidItem;
    }
    public boolean isEnzymeProcessed(obj_id enzyme) throws InterruptedException
    {
        return hasObjVar(enzyme, ENZYME_PROCESSED) ? getBooleanObjVar(enzyme, ENZYME_PROCESSED) : false;
    }
    public boolean isElementProcessed(obj_id element) throws InterruptedException
    {
        return hasObjVar(element, ELEMENT_PROCESSED) ? getBooleanObjVar(element, ELEMENT_PROCESSED) : false;
    }
    public int getElementType(obj_id element) throws InterruptedException
    {
        String passedObject = getTemplateName(element);
        for (int i = 0; i < ENZYME_ELEMENT_LIST.length; i++)
        {
            if (passedObject.equals(ENZYME_ELEMENT_LIST[i]))
            {
                return i + 1;
            }
        }
        return -1;
    }
    public boolean isReadyToOperate() throws InterruptedException
    {
        int machineType = getMachineType();
        if (!containsItems())
        {
            return false;
        }
        boolean isReady = false;
        switch (machineType)
        {
            case ITEM_CENTRIFUGE:
            isReady = validateCentrifugeOperation();
            break;
            case ITEM_PROCESSOR:
            isReady = validateProcessorOperation();
            break;
            case ITEM_COMBINER:
            isReady = validateCombinerOperation();
            break;
            default:
            isReady = false;
            break;
        }
        return isReady;
    }
    public boolean validateCentrifugeOperation() throws InterruptedException
    {
        obj_id[] contents = getContents(getSelf());
        boolean hasTray = false;
        boolean hasUnprocessedEnzyme = false;
        for (int i = 0; i < contents.length; i++)
        {
            String objectTemplate = getTemplateName(contents[i]);
            if (objectTemplate.equals(ENZYME_TYPE_3) && !isEnzymeProcessed(contents[i]))
            {
                hasUnprocessedEnzyme = true;
            }
            if (objectTemplate.equals(CENTRIFUGE_USE))
            {
                hasTray = true;
            }
        }
        return (hasTray && hasUnprocessedEnzyme);
    }
    public boolean validateProcessorOperation() throws InterruptedException
    {
        obj_id[] contents = getContents(getSelf());
        boolean hasCapsule = false;
        boolean hasElement = false;
        for (int i = 0; i < contents.length; i++)
        {
            String objectTemplate = getTemplateName(contents[i]);
            if (objectTemplate.indexOf("enzyme_3_element") > -1 && !isElementProcessed(contents[i]))
            {
                hasElement = true;
            }
            if (objectTemplate.equals(PROCESSOR_USE))
            {
                hasCapsule = true;
            }
        }
        return (hasElement && hasCapsule);
    }
    public boolean validateCombinerOperation() throws InterruptedException
    {
        obj_id[] contents = getContents(getSelf());
        boolean hasMold = false;
        boolean hasFiveElements = false;
        int fiveElements = 11111;
        for (int i = 0; i < contents.length; i++)
        {
            String objectTemplate = getTemplateName(contents[i]);
            if (objectTemplate.equals(COMBINER_USE))
            {
                hasMold = true;
            }
            if (objectTemplate.equals(ENZYME_ELEMENT_LIST[0]))
            {
                fiveElements -= 1;
            }
            if (objectTemplate.equals(ENZYME_ELEMENT_LIST[1]))
            {
                fiveElements -= 10;
            }
            if (objectTemplate.equals(ENZYME_ELEMENT_LIST[2]))
            {
                fiveElements -= 100;
            }
            if (objectTemplate.equals(ENZYME_ELEMENT_LIST[3]))
            {
                fiveElements -= 1000;
            }
            if (objectTemplate.equals(ENZYME_ELEMENT_LIST[4]))
            {
                fiveElements -= 10000;
            }
        }
        if (fiveElements == 0)
        {
            hasFiveElements = true;
        }
        return (hasMold && hasFiveElements);
    }
    public int startProcess(obj_id player) throws InterruptedException
    {
        return startProcess(player, -1);
    }
    public int startProcess(obj_id player, int time) throws InterruptedException
    {
        int machineType = getMachineType();
        switch (machineType)
        {
            case ITEM_CENTRIFUGE:
            return startCentrifuge(player, time);
            case ITEM_PROCESSOR:
            return startProcessor(player, time);
            case ITEM_COMBINER:
            return startCombiner(player, time);
        }
        return -1;
    }
    public void completeProcess() throws InterruptedException
    {
        int machineType = getMachineType();
        switch (machineType)
        {
            case ITEM_CENTRIFUGE:
            stopCentrifuge();
            break;
            case ITEM_PROCESSOR:
            stopProcessor();
            break;
            case ITEM_COMBINER:
            stopCombiner();
            break;
        }
    }
    public int startCentrifuge(obj_id player, int time) throws InterruptedException
    {
        obj_id tray = getObjIdByTemplate(CENTRIFUGE_USE);
        float machinePurity = getFloatObjVar(getSelf(), CRAFTED_PURITY) / 2.0f;
        float trayPurity = getFloatObjVar(tray, CRAFTED_PURITY) * 1.5f;
        float machineMutagen = getFloatObjVar(getSelf(), CRAFTED_MUTAGEN) / 2.0f;
        float trayMutagen = getFloatObjVar(tray, CRAFTED_MUTAGEN) * 1.5f;
        float craftedPurity = (machinePurity + trayPurity) / 2.0f;
        float craftedMutagen = (machineMutagen + trayMutagen) / 2.0f;
        craftedPurity = craftedPurity > SINGLE_STAGE_CAP ? SINGLE_STAGE_CAP : craftedPurity;
        int geneticEngineeringMod = getEnhancedSkillStatisticModifierUncapped(player, "expertise_bm_genetic_engineering");
        float ge_mod = 1.0f + (geneticEngineeringMod / 100.0f);
        craftedPurity *= ge_mod;
        craftedMutagen *= ge_mod;
        int addedTime = time < 0 ? CENTRIFUGE_RUNTIME : time;
        int timeAtComplete = getGameTime() + addedTime;
        setObjVar(getSelf(), PROCESS_PURITY, craftedPurity);
        setObjVar(getSelf(), PROCESS_MUTAGEN, craftedMutagen);
        setObjVar(getSelf(), FINISH_TIME, timeAtComplete);
        destroyObject(tray);
        return timeAtComplete;
    }
    public int startProcessor(obj_id player, int time) throws InterruptedException
    {
        obj_id capsule = getObjIdByTemplate(PROCESSOR_USE);
        float machinePurity = getFloatObjVar(getSelf(), CRAFTED_PURITY) / 2.0f;
        float capsulePurity = getFloatObjVar(capsule, CRAFTED_PURITY) * 1.5f;
        float machineMutagen = getFloatObjVar(getSelf(), CRAFTED_MUTAGEN) / 2.0f;
        float capsuleMutagen = getFloatObjVar(capsule, CRAFTED_MUTAGEN) * 1.5f;
        float craftedPurity = (machinePurity + capsulePurity) / 2.0f;
        float craftedMutagen = (machineMutagen + capsuleMutagen) / 2.0f;
        craftedPurity = craftedPurity > SINGLE_STAGE_CAP ? SINGLE_STAGE_CAP : craftedPurity;
        int geneticEngineeringMod = getEnhancedSkillStatisticModifierUncapped(player, "expertise_bm_genetic_engineering");
        float ge_mod = 1 + (geneticEngineeringMod / 100.0f);
        craftedPurity *= ge_mod;
        craftedMutagen *= ge_mod;
        int addedTime = time < 0 ? PROCESSOR_RUNTIME : time;
        int timeAtComplete = getGameTime() + addedTime;
        setObjVar(getSelf(), PROCESS_PURITY, craftedPurity);
        setObjVar(getSelf(), PROCESS_MUTAGEN, craftedMutagen);
        setObjVar(getSelf(), FINISH_TIME, timeAtComplete);
        destroyObject(capsule);
        return timeAtComplete;
    }
    public int startCombiner(obj_id player, int time) throws InterruptedException
    {
        obj_id mold = getObjIdByTemplate(COMBINER_USE);
        float machinePurity = getFloatObjVar(getSelf(), CRAFTED_PURITY) / 2.0f;
        float moldPurity = getFloatObjVar(mold, CRAFTED_PURITY) * 1.5f;
        float machineMutagen = getFloatObjVar(getSelf(), CRAFTED_MUTAGEN) / 2.0f;
        float moldMutagen = getFloatObjVar(mold, CRAFTED_MUTAGEN) * 1.5f;
        float craftedPurity = (machinePurity + moldPurity) / 2.0f;
        float craftedMutagen = (machineMutagen + moldMutagen) / 2.0f;
        craftedPurity = craftedPurity > SINGLE_STAGE_CAP ? SINGLE_STAGE_CAP : craftedPurity;
        int geneticEngineeringMod = getEnhancedSkillStatisticModifierUncapped(player, "expertise_bm_genetic_engineering");
        float ge_mod = 1 + (geneticEngineeringMod / 100.0f);
        craftedPurity *= ge_mod;
        craftedMutagen *= ge_mod;
        int addedTime = time < 0 ? COMBINER_RUNTIME : time;
        int timeAtComplete = getGameTime() + addedTime;
        setObjVar(getSelf(), PROCESS_PURITY, craftedPurity);
        setObjVar(getSelf(), PROCESS_MUTAGEN, craftedMutagen);
        setObjVar(getSelf(), FINISH_TIME, timeAtComplete);
        destroyObject(mold);
        return timeAtComplete;
    }
    public void stopCentrifuge() throws InterruptedException
    {
        obj_id enzyme = getObjIdByTemplate(ENZYME_TYPE_3);
        float enzymePurity = getFloatObjVar(enzyme, ENZYME_PURITY);
        float enzymeMutagen = getFloatObjVar(enzyme, ENZYME_MUTAGEN);
        float craftedPurity = getFloatObjVar(getSelf(), PROCESS_PURITY);
        float craftedMutagen = getFloatObjVar(getSelf(), PROCESS_MUTAGEN);
        float newPurityOne = enzymePurity + rand(craftedPurity / 2.0f, craftedPurity);
        float newPurityTwo = enzymePurity + rand(craftedPurity / 2.0f, craftedPurity);
        float newMutagenOne = enzymeMutagen + rand(craftedMutagen / 2.0f, craftedMutagen);
        float newMutagenTwo = enzymeMutagen + rand(craftedMutagen / 2.0f, craftedMutagen);
        String trait = getStringObjVar(enzyme, ENZYME_TRAIT);
        destroyObject(enzyme);
        int elementOneType = rand(0, 4);
        int elementTwoType = rand(0, 4);
        obj_id elementOne = createObject(ENZYME_ELEMENT_LIST[elementOneType], getSelf(), "");
        obj_id elementTwo = createObject(ENZYME_ELEMENT_LIST[elementTwoType], getSelf(), "");
        setObjVar(elementOne, ENZYME_PURITY, newPurityOne);
        setObjVar(elementOne, ENZYME_MUTAGEN, newMutagenOne);
        setObjVar(elementOne, ENZYME_TRAIT, trait);
        setObjVar(elementTwo, ENZYME_PURITY, newPurityTwo);
        setObjVar(elementTwo, ENZYME_MUTAGEN, newMutagenTwo);
        setObjVar(elementTwo, ENZYME_TRAIT, trait);
        hue.setColor(elementOne, hue.INDEX_1, 5);
        hue.setColor(elementTwo, hue.INDEX_1, 5);
        removeObjVar(getSelf(), SYSTEM);
    }
    public void stopProcessor() throws InterruptedException
    {
        obj_id[] contents = getContents(getSelf());
        obj_id element = contents[0];
        float enzymePurity = getFloatObjVar(element, ENZYME_PURITY);
        float enzymeMutagen = getFloatObjVar(element, ENZYME_MUTAGEN);
        float craftedPurity = getFloatObjVar(getSelf(), PROCESS_PURITY);
        float craftedMutagen = getFloatObjVar(getSelf(), PROCESS_MUTAGEN);
        float newPurity = enzymePurity + rand(craftedPurity / 2.0f, craftedPurity);
        float newMutagen = enzymeMutagen + rand(craftedMutagen / 2.0f, craftedMutagen);
        setObjVar(element, ENZYME_PURITY, newPurity);
        setObjVar(element, ENZYME_MUTAGEN, newMutagen);
        setObjVar(element, ELEMENT_PROCESSED, true);
        colorElement(element);
        removeObjVar(getSelf(), SYSTEM);
    }
    public void stopCombiner() throws InterruptedException
    {
        float avgElementPurity = averagePurityFromElements();
        float avgElementMutagen = averageMutagenFromElements();
        float craftedPurity = getFloatObjVar(getSelf(), PROCESS_PURITY);
        float craftedMutagen = getFloatObjVar(getSelf(), PROCESS_MUTAGEN);
        float newPurity = avgElementPurity + rand(craftedPurity / 2.0f, craftedPurity);
        if (newPurity > 20.0f)
        {
            newPurity = 20.0f;
        }
        float newMutagen = avgElementMutagen + rand(craftedMutagen / 2.0f, craftedMutagen);
        clearToolContents();
        obj_id enzyme = createObject(ENZYME_TYPE_3, getSelf(), "");
        setObjVar(enzyme, ENZYME_PURITY, newPurity);
        setObjVar(enzyme, ENZYME_MUTAGEN, newMutagen);
        setObjVar(enzyme, ENZYME_TRAIT, "NONE");
        setObjVar(enzyme, ENZYME_PROCESSED, true);
        removeObjVar(getSelf(), SYSTEM);
    }
    public float averagePurityFromElements() throws InterruptedException
    {
        float total = 0.0f;
        for (int i = 0; i < ENZYME_ELEMENT_LIST.length; i++)
        {
            obj_id element = getObjIdByTemplate(ENZYME_ELEMENT_LIST[i]);
            total += getFloatObjVar(element, ENZYME_PURITY);
        }
        return (total / ENZYME_ELEMENT_LIST.length);
    }
    public float averageMutagenFromElements() throws InterruptedException
    {
        float total = 0.0f;
        for (int i = 0; i < ENZYME_ELEMENT_LIST.length; i++)
        {
            obj_id element = getObjIdByTemplate(ENZYME_ELEMENT_LIST[i]);
            total += getFloatObjVar(element, ENZYME_MUTAGEN);
        }
        return (total / ENZYME_ELEMENT_LIST.length);
    }
    public void clearToolContents() throws InterruptedException
    {
        obj_id[] contents = getContents(getSelf());
        if (contents == null || contents.length == 0)
        {
            return;
        }
        for (int i = 0; i < contents.length; i++)
        {
            destroyObject(contents[i]);
        }
    }
    public obj_id getObjIdByTemplate(String template) throws InterruptedException
    {
        obj_id[] contents = getContents(getSelf());
        if (contents == null || contents.length == 0)
        {
            return null;
        }
        for (int i = 0; i < contents.length; i++)
        {
            String objectTemplate = getTemplateName(contents[i]);
            if (objectTemplate.equals(template))
            {
                return contents[i];
            }
        }
        return null;
    }
    public void colorElement(obj_id element) throws InterruptedException
    {
        String template = getTemplateName(element);
        for (int i = 0; i < ENZYME_ELEMENT_LIST.length; i++)
        {
            if (template.equals(ENZYME_ELEMENT_LIST[i]))
            {
                hue.setColor(element, hue.INDEX_1, i);
            }
        }
    }
}
