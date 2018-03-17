package script.event.lifeday;

import script.*;
import script.library.*;

public class monkey_pet extends script.base_script
{
    public monkey_pet()
    {
    }
    private static final String LIFEDAY = "event/life_day";
    private static final String MONKEY_RED = "lifeday_monkey_lizard";
    private static final String MONKEY_GREEN = "lifeday_monkey_lizard_green";
    private static final String LAUGH = "sound/voc_kowakian_laugh.snd";
    private static final String ANIMATION_1 = "wave2";
    private static final String ANIMATION_2 = "laugh_cackle";
    private static final String ANIMATION_3 = "pound_fist_palm";
    private static final String ANIMATION_4 = "bow2";
    private static final String ANIMATION_5 = "wave1";
    private static final string_id SID_TURN_ON = new string_id(LIFEDAY, "free_monkey");
    private static final string_id SID_TURN_OFF = new string_id(LIFEDAY, "cage_monkey");

    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (inHouse(self) && hasObjVar(self, "petCalled"))
        {
            location spawnLoc = getLocation(self);
            spawnLoc.x += 1;
            if (hasObjVar(self, "petId"))
            {
                if (!exists(getObjIdObjVar(self, "petId")))
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
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (inHouse(self) && hasObjVar(self, "petCalled"))
        {
            location spawnLoc = getLocation(self);
            spawnLoc.x += 1;
            if (hasObjVar(self, "petId"))
            {
                if (!exists(getObjIdObjVar(self, "petId")))
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
        if (!buff.hasBuff(breacher, "event_lifeday_no_laugh") && volumeName.equals("laughAura") && hasObjVar(self, "petId")) {
            obj_id monkey = getObjIdObjVar(self, "petId");
            if (exists(monkey)) {
                dictionary params = new dictionary();
                params.put("breacher", breacher);
                params.put("monkey", monkey);
                params.put("idx", rand(0, 4));
                messageTo(self, "animationOne", params, 3, false);
                messageTo(self, "animationTwo", params, 6, false);
                messageTo(self, "getMoving", params, 9, false);
                faceTo(monkey, breacher);
                suspendMovement(monkey);
                buff.applyBuff(breacher, "event_lifeday_no_laugh");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (inHouse(self))
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("", ""));
            if (hasObjVar(self, "petCalled"))
            {
                mi.addRootMenu(menu_info_types.ITEM_USE, SID_TURN_OFF);
            }
            else 
            {
                mi.addRootMenu(menu_info_types.ITEM_USE, SID_TURN_ON);
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
    private String whichMonkey() throws InterruptedException
    {
        if (rand(1, 2) == 2)
        {
            return MONKEY_GREEN;
        }
        return MONKEY_RED;
    }
    private boolean inHouse(obj_id device) throws InterruptedException
    {
        obj_id selfContainer = getContainedBy(device);
        obj_id ship = space_transition.getContainingShip(selfContainer);
        String templateName = "";
        if (isIdValid(ship))
        {
            templateName = getTemplateName(ship);
        }
        return (utils.isInHouseCellSpace(device) || space_utils.isPobType(templateName)) && !utils.isNestedWithinAPlayer(device);
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
        resumeMovement(params.getObjId("monkey"));
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
