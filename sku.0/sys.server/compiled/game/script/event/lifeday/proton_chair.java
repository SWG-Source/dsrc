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

public class proton_chair extends script.base_script
{
    public proton_chair()
    {
    }
    public static final String LIFEDAY = new String("event/life_day");
    public static final String SOUND_1 = new String("sound/proton_chair_adorable.snd");
    public static final String SOUND_2 = new String("sound/proton_chair_create_me.snd");
    public static final String SOUND_3 = new String("sound/proton_chair_exist_for_you.snd");
    public static final String SOUND_4 = new String("sound/proton_chair_relax.snd");
    public static final String SOUND_5 = new String("sound/proton_chair_searching_for.snd");
    public static final String SOUND_6 = new String("sound/proton_chair_time.snd");
    public static final String SOUND_7 = new String("sound/proton_chair_voice_for_you_alone.snd");
    public static final string_id SID_TURN_ON = new string_id(LIFEDAY, "chair_on");
    public static final string_id SID_TURN_OFF = new string_id(LIFEDAY, "chair_off");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "chairOn"))
        {
            removeObjVar(self, "chairOn");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "chairOn"))
        {
            removeObjVar(self, "chairOn");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (inHouse(self))
        {
            menu_info_data data = mi.getMenuItemByType(menu_info_types.SERVER_MENU6);
            if (getPosture(player) == POSTURE_SITTING)
            {
                mi.addRootMenu(menu_info_types.SERVER_MENU6, new string_id("", ""));
                if (hasObjVar(self, "chairOn"))
                {
                    int chairOn = mi.addRootMenu(menu_info_types.SERVER_MENU6, SID_TURN_OFF);
                }
                else 
                {
                    int chairOn = mi.addRootMenu(menu_info_types.SERVER_MENU6, SID_TURN_ON);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        sendDirtyObjectMenuNotification(self);
        if (inHouse(self))
        {
            if (item == menu_info_types.SERVER_MENU6 && !hasObjVar(self, "chairOn"))
            {
                if (getPosture(player) == POSTURE_SITTING)
                {
                    dictionary params = new dictionary();
                    params.put("player", player);
                    messageTo(self, "soundLoop", params, 3, false);
                    setObjVar(self, "chairOn", 1);
                    return SCRIPT_CONTINUE;
                }
            }
            if (item == menu_info_types.SERVER_MENU6 && hasObjVar(self, "chairOn"))
            {
                removeObjVar(self, "chairOn");
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
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
    public int soundLoop(obj_id self, dictionary params) throws InterruptedException
    {
        if (!inHouse(self))
        {
            removeObjVar(self, "chairOn");
        }
        if (hasObjVar(self, "chairOn"))
        {
            int idx = rand(1, 7);
            int lastSound = params.getInt("lastSound");
            if (idx == lastSound)
            {
                idx = rand(1, 7);
            }
            obj_id player = params.getObjId("player");
            if (getPosture(player) == POSTURE_SITTING)
            {
                switch (idx)
                {
                    case 1:
                    play2dNonLoopingMusic(player, SOUND_1);
                    break;
                    case 2:
                    play2dNonLoopingMusic(player, SOUND_2);
                    break;
                    case 3:
                    play2dNonLoopingMusic(player, SOUND_3);
                    break;
                    case 4:
                    play2dNonLoopingMusic(player, SOUND_4);
                    break;
                    case 5:
                    play2dNonLoopingMusic(player, SOUND_5);
                    break;
                    case 6:
                    play2dNonLoopingMusic(player, SOUND_6);
                    break;
                    case 7:
                    play2dNonLoopingMusic(player, SOUND_7);
                    break;
                    default:
                }
                params.put("lastSound", idx);
                params.put("player", player);
                messageTo(self, "soundLoop", params, 10, false);
            }
            else 
            {
                removeObjVar(self, "chairOn");
            }
        }
        return SCRIPT_CONTINUE;
    }
}
