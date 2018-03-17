package script.event.halloween;

import script.*;
import script.library.*;

public class trick_device extends script.base_script
{
    public trick_device()
    {
    }
    private static final String HALLOWEEN = "event/halloween";
    private static final string_id TRICK_OPTION_1_ID = new string_id(HALLOWEEN, "halloween_trick_1");
    private static final string_id TRICK_OPTION_2_ID = new string_id(HALLOWEEN, "halloween_trick_2");
    private static final string_id TRICK_OPTION_3_ID = new string_id(HALLOWEEN, "halloween_trick_3");
    private static final string_id TRICK_OPTION_4_ID = new string_id(HALLOWEEN, "halloween_trick_4");
    private static final string_id TRICK_OPTION_5_ID = new string_id(HALLOWEEN, "halloween_trick_5");
    private static final String TRICK_1 = "appearance/pt_spider_flock_trick_device.prt";
    private static final String TRICK_2 = "appearance/pt_moon_skulls.prt";
    private static final String TRICK_3 = "appearance/pt_moon_ghost_s05.prt";
    private static final String TRICK_4 = "appearance/pt_fire_face.prt";
    private static final String TRICK_5 = "appearance/pt_moon_vortex.prt";
    private static final string_id FAULTY_DEVICE = new string_id(HALLOWEEN, "halloween_faulty_device");
    private static final string_id IN_COMBAT = new string_id(HALLOWEEN, "halloween_in_combat");
    private static final string_id SID_TURN_ON = new string_id(HALLOWEEN, "fog_machine_on");
    private static final string_id SID_TURN_OFF = new string_id(HALLOWEEN, "fog_machine_off");
    private static final string_id CANT_USE = new string_id(HALLOWEEN, "cant_use_device");
    public static final string_id TEN_COINS = new string_id(HALLOWEEN, "ten_coins");
    public static final string_id FIVE_COINS = new string_id(HALLOWEEN, "five_coins");
    private static final string_id OUT_OF_RANGE = new string_id(HALLOWEEN, "trick_device_oor");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        int projectorTier = getIntObjVar(self, "item.projector");
        if (projectorTier > 2)
        {
            if (inHouse(self))
            {
                if (hasObjVar(self, "fogOn"))
                {
                    createTriggerVolume("fogTrigger", 80f, true);
                    startFog(self);
                }
                createTriggerVolume("trickAura", 5, true);
            }
            else 
            {
                removeTriggerVolume("trickAura");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if (hasObjVar(self, "fogOn"))
        {
            removeObjVar(self, "fogOn");
            removeObjVar(self, "unmoveable");
            stopFog(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTransferred(obj_id self, obj_id sourceContainer, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        int projectorTier = getIntObjVar(self, "item.projector");
        obj_id ship = space_transition.getContainingShip(destContainer);
        String templateName = "";
        if (isIdValid(ship))
        {
            templateName = getTemplateName(ship);
        }
        if (projectorTier > 2)
        {
            if (utils.isInHouseCellSpace(self) || space_utils.isPobType(templateName))
            {
                createTriggerVolume("trickAura", 5, true);
            }
            else 
            {
                removeTriggerVolume("trickAura");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (volumeName.equals("trickAura"))
        {
            if (!buff.hasBuff(breacher, "event_halloween_no_trick"))
            {
                switch (rand(0, 4))
                {
                    case 0:
                        playClientEffectObj(self, TRICK_1, breacher, "");
                        break;
                    case 1:
                        playClientEffectObj(self, TRICK_2, breacher, "");
                        break;
                    case 2:
                        playClientEffectObj(self, TRICK_3, breacher, "");
                        break;
                    case 3:
                        playClientEffectObj(self, TRICK_4, breacher, "");
                        break;
                    case 4:
                        playClientEffectObj(self, TRICK_5, breacher, "");
                        break;
                }
                buff.applyBuff(breacher, "event_halloween_no_trick");
            }
        }
        if (volumeName.equals("fogTrigger"))
        {
            startFog(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("", ""));
        if (inHouse(self))
        {
            if (hasObjVar(self, "fogOn"))
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
            if (item == menu_info_types.ITEM_USE && !hasObjVar(self, "fogOn"))
            {
                startFog(self);
            }
            else if (item == menu_info_types.ITEM_USE && hasObjVar(self, "fogOn"))
            {
                stopFog(self);
            }
            return SCRIPT_CONTINUE;
        }
        else if (canUse(player, self))
        {
            if (item == menu_info_types.ITEM_USE)
            {
                startDevice(player);
            }
            return SCRIPT_CONTINUE;
        }
        else if (item == menu_info_types.ITEM_USE)
        {
            sendSystemMessage(player, CANT_USE);
        }
        return SCRIPT_CONTINUE;
    }
    private boolean canUse(obj_id player, obj_id device) throws InterruptedException
    {
        if (!inHouse(device))
        {
            if (!event_perk.timeStampCheck(player))
            {
                return false;
            }
            if(!isValidId(getIntendedTarget(player))) {
                return false;
            }
            obj_id correctPlayer = utils.getObjIdScriptVar(getIntendedTarget(player), "readyForTrickFromPlayer." + player);
            return (correctPlayer == player);
        }
        return false;
    }
    private void startDevice(obj_id player) throws InterruptedException
    {
        String TRICK_OPTION_1 = utils.packStringId(TRICK_OPTION_1_ID);
        String TRICK_OPTION_2 = utils.packStringId(TRICK_OPTION_2_ID);
        String TRICK_OPTION_3 = utils.packStringId(TRICK_OPTION_3_ID);
        String TRICK_OPTION_4 = utils.packStringId(TRICK_OPTION_4_ID);
        String TRICK_OPTION_5 = utils.packStringId(TRICK_OPTION_5_ID);
        final String[] TRICK_OPTIONS_1 = 
        {
            TRICK_OPTION_1,
            TRICK_OPTION_2,
            TRICK_OPTION_3
        };
        final String[] TRICK_OPTIONS_2 = 
        {
            TRICK_OPTION_1,
            TRICK_OPTION_2,
            TRICK_OPTION_3,
            TRICK_OPTION_4
        };
        final String[] TRICK_OPTIONS_3 = 
        {
            TRICK_OPTION_1,
            TRICK_OPTION_2,
            TRICK_OPTION_3,
            TRICK_OPTION_4,
            TRICK_OPTION_5
        };
        obj_id self = getSelf();
        String prompt = "Select the desired trick";
        String title = "Trick or Treat Projector";
        closeOldWindow(player);
        if (getIntObjVar(self, "item.projector") > 2)
        {
            int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, TRICK_OPTIONS_3, "handleOptionSelect", true, false);
            setWindowPid(player, pid);
        }
        else if (getIntObjVar(self, "item.projector") > 1)
        {
            int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, TRICK_OPTIONS_2, "handleOptionSelect", true, false);
            setWindowPid(player, pid);
        }
        else if (getIntObjVar(self, "item.projector") > 0)
        {
            int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, TRICK_OPTIONS_1, "handleOptionSelect", true, false);
            setWindowPid(player, pid);
        }
    }
    public int handleOptionSelect(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (btn == sui.BP_CANCEL)
        {
            cleanScriptVars(player);
            return SCRIPT_CONTINUE;
        }
        if (idx == -1)
        {
            cleanScriptVars(player);
            return SCRIPT_CONTINUE;
        }
        obj_id target = getIntendedTarget(player);
        if (isIdValid(target) && exists(target))
        {
            location selfLoc = getLocation(player);
            location targetLoc = getLocation(target);
            float fltDistance = getDistance(selfLoc, targetLoc);
            if (fltDistance > 30.0f)
            {
                sendSystemMessage(player, OUT_OF_RANGE);
                return SCRIPT_CONTINUE;
            }
        }
        if (utils.hasScriptVar(target, "readyForTrickFromPlayer." + player))
        {
            obj_id correctPlayer = utils.getObjIdScriptVar(target, "readyForTrickFromPlayer." + player);
            if(correctPlayer == player) {
                switch (idx) {
                    case 0:
                        utils.removeScriptVar(target, "readyForTrickFromPlayer." + player);
                        playTrick(self, player, target, 1);
                        break;
                    case 1:
                        utils.removeScriptVar(target, "readyForTrickFromPlayer." + player);
                        playTrick(self, player, target, 2);
                        break;
                    case 2:
                        utils.removeScriptVar(target, "readyForTrickFromPlayer." + player);
                        playTrick(self, player, target, 3);
                        break;
                    case 3:
                        utils.removeScriptVar(target, "readyForTrickFromPlayer." + player);
                        playTrick(self, player, target, 4);
                        break;
                    case 4:
                        utils.removeScriptVar(target, "readyForTrickFromPlayer." + player);
                        playTrick(self, player, target, 5);
                        break;
                    default:
                        cleanScriptVars(player);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    private void playTrick(obj_id self, obj_id player, obj_id target, int trick) throws InterruptedException
    {
        if (!ai_lib.isInCombat(player) || !ai_lib.isInCombat(target))
        {
            int deviceQuality = getIntObjVar(self, "item.projector");
            int scareChance;
            if (deviceQuality == 3)
            {
                scareChance = rand(1, 70);
            }
            else if (deviceQuality == 2)
            {
                scareChance = rand(1, 80);
            }
            else if (deviceQuality == 1)
            {
                scareChance = rand(1, 100);
            }
            else
            {
                sendSystemMessage(player, FAULTY_DEVICE);
                return;
            }
            int scareDialogue = rand(1, 5);
            int scariestChance = rand(1, 5);
            switch (trick)
            {
                case 1:
                    playClientEffectObj(player, TRICK_1, target, "");
                    break;
                case 2:
                    playClientEffectObj(player, TRICK_2, target, "");
                    break;
                case 3:
                    playClientEffectObj(player, TRICK_3, target, "");
                    break;
                case 4:
                    playClientEffectObj(player, TRICK_4, target, "");
                    break;
                case 5:
                    playClientEffectObj(player, TRICK_5, target, "");
                    break;
            }
            if (scariestChance == trick)
            {
                chat.chat(target, player, chat.CHAT_SCREAM, new string_id(HALLOWEEN, "super_scared_" + scareDialogue), chat.ChatFlag_targetOnly);
                event_perk.giveTreat(player, 2);
                doAnimationAction(target, "cover_eyes");
            }
            else if (scareChance < 51)
            {
                doAnimationAction(target, "scared");
                chat.chat(target, player, chat.CHAT_STUTTER, new string_id(HALLOWEEN, "scared_" + scareDialogue), chat.ChatFlag_targetOnly);
                event_perk.giveTreat(player, 1);
            }
            else
            {
                doAnimationAction(target, "dismiss");
                chat.chat(target, player, chat.CHAT_SAY, new string_id(HALLOWEEN, "not_scared_" + scareDialogue), chat.ChatFlag_targetOnly);
            }
        }
        else 
        {
            sendSystemMessage(player, IN_COMBAT);
        }
    }
    public void cleanScriptVars(obj_id player) throws InterruptedException
    {
        utils.removeScriptVarTree(player, "trick_or_treat_device");
        utils.removeScriptVarTree(getSelf(), "trick_or_treat_device");
        setObjVar(player, "trick_or_treat_device", true);
    }
    public void closeOldWindow(obj_id player) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "trick_or_treat_device.pid"))
        {
            forceCloseSUIPage(utils.getIntScriptVar(player, "trick_or_treat_device.pid"));
            utils.removeScriptVar(player, "trick_or_treat_device.pid");
        }
    }
    public void setWindowPid(obj_id player, int pid) throws InterruptedException
    {
        if (pid > -1)
        {
            utils.setScriptVar(player, "trick_or_treat_device.pid", pid);
        }
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
        if ((utils.isInHouseCellSpace(device) || space_utils.isPobType(templateName)) && !utils.isNestedWithinAPlayer(device))
        {
            return true;
        }
        return false;
    }
    private void startFog(obj_id device) throws InterruptedException
    {
        stopClientEffectObjByLabel(device, "halloweenFog");
        if (!hasObjVar(device, "fogOn"))
        {
            setObjVar(device, "fogOn", 1);
        }
        setObjVar(device, "unmoveable", 1);
        playClientEffectObj(device, "clienteffect/halloween_fog_machine.cef", device, "", null, "halloweenFog");
        messageTo(device, "continueFog", null, 18.0f, false);
    }
    private void stopFog(obj_id device) throws InterruptedException
    {
        stopClientEffectObjByLabel(device, "halloweenFog");
        if (hasObjVar(device, "fogOn"))
        {
            removeObjVar(device, "fogOn");
        }
        if (hasObjVar(device, "unmoveable"))
        {
            removeObjVar(device, "unmoveable");
        }
    }
    public int continueFog(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "fogOn"))
        {
            playClientEffectObj(self, "clienteffect/halloween_fog_machine.cef", self, "", null, "halloweenFog");
            messageTo(self, "continueFog", null, 18.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (getIntObjVar(self, "item.projector") > 2)
        {
            if (hasObjVar(self, "deviceOwner"))
            {
                obj_id player = getObjIdObjVar(self, "deviceOwner");
                if (getCollectionSlotValue(player, "received_halloween_reward") > 0)
                {
                    modifyCollectionSlotValue(player, "received_halloween_reward", -1);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
