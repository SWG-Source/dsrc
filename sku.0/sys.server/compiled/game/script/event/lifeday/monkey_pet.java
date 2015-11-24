package script.event.lifeday;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.utils;
import script.library.player_structure;
import script.library.locations;
import script.library.space_transition;
import script.library.space_utils;
import script.library.buff;
import script.library.ai_lib;

public class monkey_pet extends script.base_script
{
    public monkey_pet()
    {
    }
    public static final String LIFEDAY = new String("event/life_day");
    public static final String MONKEY_RED = new String("lifeday_monkey_lizard");
    public static final String MONKEY_GREEN = new String("lifeday_monkey_lizard_green");
    public static final String LAUGH = new String("sound/voc_kowakian_laugh.snd");
    public static final String ANIMATION_1 = new String("wave2");
    public static final String ANIMATION_2 = new String("laugh_cackle");
    public static final String ANIMATION_3 = new String("pound_fist_palm");
    public static final String ANIMATION_4 = new String("bow2");
    public static final String ANIMATION_5 = new String("wave1");
    public static final string_id SID_TURN_ON = new string_id(LIFEDAY, "free_monkey");
    public static final string_id SID_TURN_OFF = new string_id(LIFEDAY, "cage_monkey");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (inHouse(self))
        {
            location spawnLoc = getLocation(self);
            spawnLoc.x += 1;
            if (hasObjVar(self, "petCalled"))
            {
                if (hasObjVar(self, "petId"))
                {
                    obj_id oldPet = getObjIdObjVar(self, "petId");
                    if (!exists(oldPet))
                    {
                        obj_id monkey = create.object(whichMonkey(), spawnLoc);
                        ai_lib.setDefaultCalmBehavior(monkey, ai_lib.BEHAVIOR_WANDER);
                        setObjVar(self, "petId", monkey);
                        setObjVar(monkey, "momId", self);
                    }
                }
                else 
                {
                    obj_id monkey = create.object(whichMonkey(), spawnLoc);
                    setObjVar(self, "petId", monkey);
                    setObjVar(monkey, "momId", self);
                }
                createTriggerVolume("laughAura", 5, true);
            }
            else 
            {
                removeTriggerVolume("laughAura");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (inHouse(self))
        {
            location spawnLoc = getLocation(self);
            spawnLoc.x += 1;
            if (hasObjVar(self, "petCalled"))
            {
                if (hasObjVar(self, "petId"))
                {
                    obj_id oldPet = getObjIdObjVar(self, "petId");
                    if (!exists(oldPet))
                    {
                        obj_id monkey = create.object(whichMonkey(), spawnLoc);
                        ai_lib.setDefaultCalmBehavior(monkey, ai_lib.BEHAVIOR_LOITER);
                        setObjVar(self, "petId", monkey);
                        setObjVar(monkey, "momId", self);
                    }
                }
                else 
                {
                    obj_id monkey = create.object(whichMonkey(), spawnLoc);
                    setObjVar(self, "petId", monkey);
                    setObjVar(monkey, "momId", self);
                }
                createTriggerVolume("laughAura", 5, true);
            }
            else 
            {
                removeTriggerVolume("laughAura");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if (hasObjVar(self, "petCalled"))
        {
            if (hasObjVar(self, "petId"))
            {
                obj_id oldPet = getObjIdObjVar(self, "petId");
                if (exists(oldPet))
                {
                    destroyObject(oldPet);
                }
                removeObjVar(self, "petId");
            }
            removeObjVar(self, "petCalled");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (!buff.hasBuff(breacher, "event_lifeday_no_laugh"))
        {
            if (volumeName.equals("laughAura"))
            {
                if (hasObjVar(self, "petId"))
                {
                    obj_id monkey = getObjIdObjVar(self, "petId");
                    if (exists(monkey))
                    {
                        int idx = rand(0, 4);
                        dictionary params = new dictionary();
                        params.put("breacher", breacher);
                        params.put("monkey", monkey);
                        params.put("idx", idx);
                        messageTo(self, "animationOne", params, 3, false);
                        messageTo(self, "animationTwo", params, 6, false);
                        messageTo(self, "getMoving", params, 9, false);
                        faceTo(monkey, breacher);
                        suspendMovement(monkey);
                        buff.applyBuff(breacher, "event_lifeday_no_laugh");
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (inHouse(self))
        {
            menu_info_data data = mi.getMenuItemByType(menu_info_types.ITEM_USE);
            mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("", ""));
            if (hasObjVar(self, "petCalled"))
            {
                int monkeyCalled = mi.addRootMenu(menu_info_types.ITEM_USE, SID_TURN_OFF);
            }
            else 
            {
                int monkeyCalled = mi.addRootMenu(menu_info_types.ITEM_USE, SID_TURN_ON);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (inHouse(self))
        {
            if (item == menu_info_types.ITEM_USE && !hasObjVar(self, "petCalled"))
            {
                location spawnLoc = getLocation(self);
                spawnLoc.x += 1;
                obj_id monkey = create.object(whichMonkey(), spawnLoc);
                ai_lib.setDefaultCalmBehavior(monkey, ai_lib.BEHAVIOR_LOITER);
                setObjVar(self, "petCalled", 1);
                setObjVar(self, "petId", monkey);
                setObjVar(monkey, "momId", self);
                createTriggerVolume("laughAura", 5, true);
                return SCRIPT_CONTINUE;
            }
            if (item == menu_info_types.ITEM_USE && hasObjVar(self, "petCalled"))
            {
                if (hasObjVar(self, "petId"))
                {
                    obj_id oldPet = getObjIdObjVar(self, "petId");
                    if (exists(oldPet))
                    {
                        destroyObject(oldPet);
                    }
                    removeObjVar(self, "petId");
                }
                removeObjVar(self, "petCalled");
                removeTriggerVolume("laughAura");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public String whichMonkey() throws InterruptedException
    {
        int monkeyColor = rand(1, 2);
        if (monkeyColor > 1)
        {
            return MONKEY_GREEN;
        }
        else 
        {
            return MONKEY_RED;
        }
    }
    public boolean inHouse(obj_id device) throws InterruptedException
    {
        obj_id selfContainer = getContainedBy(device);
        obj_id ship = space_transition.getContainingShip(selfContainer);
        String templateName = "";
        if (isIdValid(ship))
        {
            templateName = getTemplateName(ship);
        }
        if ((utils.isInHouseCellSpace(device) || space_utils.isPobType(templateName)) && !utils.isNestedWithinAPlayer(device))
        {
            return true;
        }
        return false;
    }
    public int animationOne(obj_id self, dictionary params) throws InterruptedException
    {
        int idx = params.getInt("idx");
        obj_id breacher = params.getObjId("breacher");
        obj_id monkey = params.getObjId("monkey");
        play2dNonLoopingMusic(breacher, LAUGH);
        switch (idx)
        {
            case 0:
            doAnimationAction(monkey, ANIMATION_1);
            break;
            case 1:
            doAnimationAction(monkey, ANIMATION_2);
            break;
            case 2:
            doAnimationAction(monkey, ANIMATION_3);
            break;
            case 3:
            doAnimationAction(monkey, ANIMATION_4);
            break;
            case 4:
            doAnimationAction(monkey, ANIMATION_5);
            break;
            default:
        }
        return SCRIPT_CONTINUE;
    }
    public int animationTwo(obj_id self, dictionary params) throws InterruptedException
    {
        int idx = params.getInt("idx");
        obj_id breacher = params.getObjId("breacher");
        obj_id monkey = params.getObjId("monkey");
        switch (idx)
        {
            case 0:
            doAnimationAction(monkey, ANIMATION_1);
            break;
            case 1:
            doAnimationAction(monkey, ANIMATION_2);
            break;
            case 2:
            doAnimationAction(monkey, ANIMATION_3);
            break;
            case 3:
            doAnimationAction(monkey, ANIMATION_4);
            break;
            case 4:
            doAnimationAction(monkey, ANIMATION_5);
            break;
            default:
        }
        return SCRIPT_CONTINUE;
    }
    public int getMoving(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id monkey = params.getObjId("monkey");
        resumeMovement(monkey);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "petCalled"))
        {
            if (hasObjVar(self, "petId"))
            {
                obj_id oldPet = getObjIdObjVar(self, "petId");
                if (exists(oldPet))
                {
                    destroyObject(oldPet);
                }
                removeObjVar(self, "petId");
            }
            removeObjVar(self, "petCalled");
        }
        return SCRIPT_CONTINUE;
    }
}
