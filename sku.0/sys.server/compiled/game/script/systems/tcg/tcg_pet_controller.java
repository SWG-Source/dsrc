package script.systems.tcg;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.buff;
import script.library.create;
import script.library.house_pet;
import script.library.locations;
import script.library.player_structure;
import script.library.space_transition;
import script.library.space_utils;
import script.library.utils;

public class tcg_pet_controller extends script.base_script
{
    public tcg_pet_controller()
    {
    }
    public static final boolean LOGGING_ON = true;
    public static final String LOGGING_CATEGORY = "massiff_pet";
    public static final String TCG_STR = new String("tcg");
    public static final String LAUGH = new String("sound/voc_kowakian_laugh.snd");
    public static final String ANIMATION_1 = new String("wave2");
    public static final String ANIMATION_2 = new String("laugh_cackle");
    public static final String ANIMATION_3 = new String("pound_fist_palm");
    public static final String ANIMATION_4 = new String("bow2");
    public static final String ANIMATION_5 = new String("wave1");
    public static final string_id SID_TURN_ON = new string_id(TCG_STR, "call_pet");
    public static final string_id SID_TURN_OFF = new string_id(TCG_STR, "store_pet");
    public static final string_id SID_FEED = new string_id(TCG_STR, "feed_pet");
    public static final string_id SID_SNACK = new string_id(TCG_STR, "scurrier_snack");
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        blog("OnDestroy - init");
        if (!isValidId(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, house_pet.PET_HAS_BEEN_CALLED))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, house_pet.CHILD_OBJ_ID))
        {
            obj_id oldPet = getObjIdObjVar(self, house_pet.CHILD_OBJ_ID);
            if (isIdValid(oldPet) && exists(oldPet))
            {
                destroyObject(oldPet);
            }
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
        if (hasObjVar(self, house_pet.MASSIFF_CURRENT_PHASE))
        {
            int currentStatus = getIntObjVar(self, house_pet.MASSIFF_CURRENT_PHASE);
            if (currentStatus < house_pet.MASSIFF_FED || currentStatus > house_pet.MASSIFF_DEAD)
            {
                blog("getSarlaccCollectionColumn currentStatus : " + currentStatus);
                return SCRIPT_CONTINUE;
            }
            names[idx] = "status";
            attribs[idx] = "@obj_attr_n:" + house_pet.MASSIFF_PHASES[currentStatus];
            idx++;
            if (hasObjVar(self, house_pet.MASSIFF_LAST_FED))
            {
                int lastFed = getIntObjVar(self, house_pet.MASSIFF_LAST_FED);
                if (lastFed < 0)
                {
                    return SCRIPT_CONTINUE;
                }
                names[idx] = "last_fed";
                attribs[idx] = getCalendarTimeStringLocal(lastFed);
                idx++;
                names[idx] = "next_feed";
                if (currentStatus == 0)
                {
                    attribs[idx] = getCalendarTimeStringLocal(lastFed + house_pet.getUpdateWeekly(self));
                }
                else if (currentStatus == 1)
                {
                    attribs[idx] = "Now";
                }
                else 
                {
                    attribs[idx] = "Hopefully Soon";
                }
                idx++;
                names[idx] = "time_now";
                attribs[idx] = getCalendarTimeStringLocal(getCalendarTime());
                idx++;
            }
            else 
            {
                names[idx] = "last_fed";
                attribs[idx] = "Never";
                idx++;
                names[idx] = "next_feed";
                attribs[idx] = "Hopefully Soon";
                idx++;
            }
        }
        String petType = getTemplateName(self);
        if ((petType.indexOf("scurrier") > -1) && (hasObjVar(self, house_pet.SCURRIER_SNACK_LAST_FED)))
        {
            names[idx] = "last_scurrier_snack";
            int lastSnack = getIntObjVar(self, house_pet.SCURRIER_SNACK_LAST_FED);
            attribs[idx] = getCalendarTimeStringLocal(lastSnack);
            idx++;
            names[idx] = "next_scurrier_snack";
            int nextSnack = getIntObjVar(self, house_pet.SCURRIER_SNACK_LAST_FED);
            attribs[idx] = getCalendarTimeStringLocal(nextSnack + house_pet.SCURRIER_SNACK_DELAY);
            idx++;
        }
        if (isValidId(getOwner(self)))
        {
            obj_id owner = getOwner(self);
            names[idx] = "owner";
            attribs[idx] = getPlayerFullName(owner);
            idx++;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        house_pet.setObjectOwner(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        blog("OnInitialize - init");
        if (!house_pet.isInAPlayerHouse(self))
        {
            return SCRIPT_CONTINUE;
        }
        spawnPetSomewhere(self);
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        blog("OnAboutToBeTransferred - init");
        if (hasObjVar(self, house_pet.PET_HAS_BEEN_CALLED))
        {
            removeObjVar(self, house_pet.PET_HAS_BEEN_CALLED);
        }
        if (hasObjVar(self, house_pet.CHILD_OBJ_ID))
        {
            obj_id oldPet = getObjIdObjVar(self, house_pet.CHILD_OBJ_ID);
            if (isValidId(oldPet) && exists(oldPet))
            {
                destroyObject(oldPet);
            }
            removeObjVar(self, house_pet.CHILD_OBJ_ID);
        }
        if (hasObjVar(self, house_pet.MASSIFF_FOOD_OBJ))
        {
            obj_id oldFood = getObjIdObjVar(self, house_pet.MASSIFF_FOOD_OBJ);
            if (isValidId(oldFood) && exists(oldFood))
            {
                destroyObject(oldFood);
                removeObjVar(self, house_pet.MASSIFF_FOOD_OBJ);
            }
        }
        removeObjVar(self, house_pet.MASSIFF_LAST_FED);
        removeObjVar(self, house_pet.MASSIFF_CURRENT_PHASE);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        blog("OnObjectMenuRequest - init");
        if (!house_pet.isInAPlayerHouse(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id owner = getOwner(self);
        if (!isValidId(owner))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, house_pet.PET_HAS_BEEN_CALLED))
        {
            if (player == owner)
            {
                mi.addRootMenu(menu_info_types.ITEM_USE, SID_TURN_OFF);
            }
            if (player == owner && !hasObjVar(self, house_pet.PET_KNOCKED_OUT) && hasObjVar(self, house_pet.PET_FEEDABLE) && hasObjVar(self, house_pet.MASSIFF_CURRENT_PHASE) && getIntObjVar(self, house_pet.MASSIFF_CURRENT_PHASE) >= 1)
            {
                mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_FEED);
            }
            if (player == owner && !hasObjVar(self, house_pet.PET_KNOCKED_OUT) && hasObjVar(self, house_pet.PET_FEEDABLE) && hasObjVar(self, house_pet.CHILD_OBJ_ID))
            {
                String petType = getTemplateName(getObjIdObjVar(self, house_pet.CHILD_OBJ_ID));
                if (petType.indexOf("scurrier") > -1)
                {
                    int lastFedTime = getIntObjVar(self, house_pet.SCURRIER_SNACK_LAST_FED);
                    int nextSnackTime = lastFedTime + house_pet.SCURRIER_SNACK_DELAY;
                    int currentGameTime = getCalendarTime();
                    if ((nextSnackTime <= currentGameTime) || !hasObjVar(self, house_pet.SCURRIER_SNACK_LAST_FED))
                    {
                        mi.addRootMenu(menu_info_types.SERVER_MENU2, SID_SNACK);
                    }
                }
            }
        }
        else if (player == owner)
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, SID_TURN_ON);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        blog("OnObjectMenuSelect - init");
        if (!house_pet.isInAPlayerHouse(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id owner = getOwner(self);
        if (!isValidId(owner))
        {
            return SCRIPT_CONTINUE;
        }
        if (owner == player && item == menu_info_types.ITEM_USE && !hasObjVar(self, house_pet.PET_HAS_BEEN_CALLED))
        {
            spawnPetByOwner(self, player);
            setObjVar(self, house_pet.MASSIFF_CURRENT_PHASE, house_pet.MASSIFF_HUNGRY);
        }
        else if (hasObjVar(self, house_pet.PET_HAS_BEEN_CALLED))
        {
            if (owner == player && item == menu_info_types.ITEM_USE)
            {
                blog("OnObjectMenuSelect - menu_info_types.ITEM_USE");
                if (hasObjVar(self, house_pet.CHILD_OBJ_ID))
                {
                    obj_id oldPet = getObjIdObjVar(self, house_pet.CHILD_OBJ_ID);
                    if (isValidId(oldPet) && exists(oldPet))
                    {
                        destroyObject(oldPet);
                    }
                    removeObjVar(self, house_pet.CHILD_OBJ_ID);
                    removeObjVar(self, house_pet.MASSIFF_LAST_FED);
                    removeObjVar(self, house_pet.MASSIFF_CURRENT_PHASE);
                    if (hasObjVar(self, house_pet.MASSIFF_FOOD_OBJ))
                    {
                        obj_id food = getObjIdObjVar(self, house_pet.MASSIFF_FOOD_OBJ);
                        if (isValidId(food) && exists(food))
                        {
                            destroyObject(food);
                        }
                        removeObjVar(self, house_pet.MASSIFF_FOOD_OBJ);
                    }
                }
                removeObjVar(self, house_pet.PET_HAS_BEEN_CALLED);
            }
            else if (item == menu_info_types.SERVER_MENU1)
            {
                blog("OnObjectMenuSelect - menu_info_types.SERVER_MENU1");
                spawnFoodAtLocation(self, getLocation(self));
            }
            else if (item == menu_info_types.SERVER_MENU2)
            {
                spawnSnackAtLocation(self, getLocation(self));
            }
        }
        sendDirtyObjectMenuNotification(self);
        return SCRIPT_CONTINUE;
    }
    public boolean spawnPetSomewhere(obj_id controller) throws InterruptedException
    {
        if (!isValidId(controller) || !exists(controller))
        {
            return false;
        }
        if (!house_pet.isInAPlayerHouse(controller))
        {
            return false;
        }
        if (!hasObjVar(controller, house_pet.CHILD_OBJ_ID))
        {
            return false;
        }
        obj_id pet = getObjIdObjVar(controller, house_pet.CHILD_OBJ_ID);
        if (isValidId(pet) && exists(pet))
        {
            return false;
        }
        if (!hasObjVar(controller, house_pet.HOUSE_PET_OBJVAR))
        {
            return false;
        }
        String petCreatureName = getStringObjVar(controller, house_pet.HOUSE_PET_OBJVAR);
        if (petCreatureName == null || petCreatureName.length() <= 0)
        {
            return false;
        }
        if (!hasObjVar(controller, house_pet.HOUSE_PET_SCRIPT))
        {
            return false;
        }
        String petCreatureScript = getStringObjVar(controller, house_pet.HOUSE_PET_SCRIPT);
        if (petCreatureScript == null || petCreatureScript.length() <= 0)
        {
            return false;
        }
        location controllerLoc = getLocation(controller);
        if (controllerLoc == null)
        {
            return false;
        }
        blog("controllerLoc " + controllerLoc);
        String strCell = getCellName(controllerLoc.cell);
        if (strCell == null || strCell.equals(""))
        {
            return false;
        }
        obj_id objBuilding = getTopMostContainer(controller);
        if (!isValidId(objBuilding) || !exists(objBuilding))
        {
            return false;
        }
        location spawnLoc = getGoodLocation(objBuilding, strCell);
        if (spawnLoc == null)
        {
            return false;
        }
        blog("spawnLoc " + spawnLoc);
        obj_id owner = getOwner(controller);
        if (!isValidId(owner))
        {
            return false;
        }
        obj_id newPet = create.object(petCreatureName, spawnLoc);
        setOwner(newPet, owner);
        ai_lib.setDefaultCalmBehavior(newPet, ai_lib.BEHAVIOR_WANDER);
        setObjVar(controller, house_pet.CHILD_OBJ_ID, newPet);
        setObjVar(newPet, house_pet.PARENT_OBJ_ID, controller);
        attachScript(newPet, petCreatureScript);
        setObjVar(controller, house_pet.PET_HAS_BEEN_CALLED, true);
        int currentPhase = getIntObjVar(controller, house_pet.MASSIFF_CURRENT_PHASE);
        if (hasObjVar(controller, house_pet.PET_KNOCKED_OUT) && getBooleanObjVar(controller, house_pet.PET_KNOCKED_OUT))
        {
            ai_lib.aiSetPosture(newPet, POSTURE_KNOCKED_DOWN);
            return true;
        }
        location pathToLoc = getLocation(controller);
        pathTo(newPet, pathToLoc);
        return true;
    }
    public boolean spawnPetByOwner(obj_id controller, obj_id owner) throws InterruptedException
    {
        blog("spawnPetAtLocation - init");
        if (!isValidId(controller) || !exists(controller))
        {
            return false;
        }
        if (!isValidId(owner) || !exists(owner))
        {
            return false;
        }
        if (!house_pet.isInAPlayerHouse(controller))
        {
            return false;
        }
        if (hasObjVar(controller, house_pet.CHILD_OBJ_ID))
        {
            obj_id oldPet = getObjIdObjVar(controller, house_pet.CHILD_OBJ_ID);
            if (isValidId(oldPet) && exists(oldPet))
            {
                return false;
            }
        }
        if (!hasObjVar(controller, house_pet.HOUSE_PET_OBJVAR))
        {
            return false;
        }
        String petCreatureName = getStringObjVar(controller, house_pet.HOUSE_PET_OBJVAR);
        if (petCreatureName == null || petCreatureName.length() <= 0)
        {
            return false;
        }
        if (!hasObjVar(controller, house_pet.HOUSE_PET_SCRIPT))
        {
            return false;
        }
        String petCreatureScript = getStringObjVar(controller, house_pet.HOUSE_PET_SCRIPT);
        if (petCreatureScript == null || petCreatureScript.length() <= 0)
        {
            return false;
        }
        location controllerLoc = getLocation(controller);
        if (controllerLoc == null)
        {
            return false;
        }
        blog("controllerLoc " + controllerLoc);
        String strCell = getCellName(controllerLoc.cell);
        if (strCell == null || strCell.equals(""))
        {
            return false;
        }
        obj_id objBuilding = getTopMostContainer(controller);
        if (!isValidId(objBuilding) || !exists(objBuilding))
        {
            return false;
        }
        location spawnLoc = getGoodLocation(objBuilding, strCell);
        if (spawnLoc == null)
        {
            return false;
        }
        obj_id pet = create.object(petCreatureName, spawnLoc);
        if(!isIdValid(pet)){
            LOG("tcg-pet","Unable to spawn pet (" + pet + ") with template (" + petCreatureName + ") near the owner (" + owner + ") in building (" + objBuilding + ") at location (" + spawnLoc.toString() + ").");
            return false;
        }
        setOwner(pet, owner);
        ai_lib.setDefaultCalmBehavior(pet, ai_lib.BEHAVIOR_WANDER);
        setObjVar(controller, house_pet.CHILD_OBJ_ID, pet);
        setObjVar(pet, house_pet.PARENT_OBJ_ID, controller);
        attachScript(pet, petCreatureScript);
        setObjVar(controller, house_pet.PET_HAS_BEEN_CALLED, true);
        location pathToLoc = getLocation(controller);
        pathTo(pet, pathToLoc);
        messageTo(pet, "findOwner", null, 2, false);
        messageTo(pet, "startLoitering", null, 30, false);
        return true;
    }
    public boolean spawnFoodAtLocation(obj_id controller, location foodLoc) throws InterruptedException
    {
        blog("spawnFoodAtLocation - init");
        if (!isValidId(controller) || !exists(controller))
        {
            return false;
        }
        if (foodLoc == null)
        {
            return false;
        }
        blog("spawnFoodAtLocation - initial validation completed");
        if (!hasObjVar(controller, house_pet.CHILD_OBJ_ID))
        {
            return false;
        }
        blog("spawnFoodAtLocation - validating pet");
        obj_id pet = getObjIdObjVar(controller, house_pet.CHILD_OBJ_ID);
        if (!isValidId(pet) && !exists(pet))
        {
            return false;
        }
        utils.setScriptVar(pet, house_pet.BEING_FED, true);
        blog("spawnFoodAtLocation - about to create food.");
        obj_id food = obj_id.NULL_ID;
        String petType = getTemplateName(pet);
        if (petType.indexOf("massif") > -1)
        {
            food = create.object("object/static/item/item_massiff_food.iff", foodLoc);
            if (!isValidId(food))
            {
                return false;
            }
            setObjVar(food, "timer", 10);
        }
        if (petType.indexOf("nuna") > -1)
        {
            foodLoc.y = foodLoc.y + 0.05f;
            food = create.object("object/static/item/item_nuna_food.iff", foodLoc);
            if (!isValidId(food))
            {
                return false;
            }
            setObjVar(food, "timer", 10);
        }
        if (petType.indexOf("scurrier") > -1)
        {
            foodLoc.y = foodLoc.y + 0.04f;
            food = create.object("object/static/item/item_scurrier_food.iff", foodLoc);
            if (!isValidId(food))
            {
                return false;
            }
            setObjVar(food, "timer", 20);
        }
        setObjVar(controller, house_pet.MASSIFF_FOOD_OBJ, food);
        blog("spawnFoodAtLocation - food: " + food);
        setObjVar(pet, house_pet.PET_FOOD_OID, food);
        messageTo(pet, "feedingTime", null, 2, false);
        return true;
    }
    public boolean spawnSnackAtLocation(obj_id controller, location foodLoc) throws InterruptedException
    {
        LOG("sissynoid", "Spawning Snack at a location");
        if (!isValidId(controller) || !exists(controller))
        {
            return false;
        }
        if (foodLoc == null)
        {
            return false;
        }
        if (!hasObjVar(controller, house_pet.CHILD_OBJ_ID))
        {
            return false;
        }
        obj_id pet = getObjIdObjVar(controller, house_pet.CHILD_OBJ_ID);
        if (!isValidId(pet) && !exists(pet))
        {
            return false;
        }
        utils.setScriptVar(pet, house_pet.BEING_FED, true);
        obj_id food = obj_id.NULL_ID;
        String petType = getTemplateName(pet);
        if (petType.indexOf("scurrier") > -1)
        {
            foodLoc.y = foodLoc.y + 0.04f;
            food = create.object("object/static/item/item_scurrier_food.iff", foodLoc);
        }
        else 
        {
            return false;
        }
        setObjVar(controller, house_pet.SCURRIER_SNACK_OBJ, food);
        setObjVar(food, "timer", 20);
        setObjVar(pet, house_pet.PET_SNACK_OID, food);
        messageTo(pet, "snackTime", null, 1, false);
        return true;
    }
    public boolean blog(String msg) throws InterruptedException
    {
        if (LOGGING_ON && msg != null && !msg.equals(""))
        {
            LOG(LOGGING_CATEGORY, msg);
        }
        return true;
    }
}
