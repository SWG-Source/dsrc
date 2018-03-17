package script.event.lifeday;

import script.*;
import script.library.space_transition;
import script.library.space_utils;
import script.library.utils;

public class proton_chair extends script.base_script
{
    public proton_chair()
    {
    }
    private static final String LIFEDAY = "event/life_day";
    private static final String SOUND_1 = "sound/proton_chair_adorable.snd";
    private static final String SOUND_2 = "sound/proton_chair_create_me.snd";
    private static final String SOUND_3 = "sound/proton_chair_exist_for_you.snd";
    private static final String SOUND_4 = "sound/proton_chair_relax.snd";
    private static final String SOUND_5 = "sound/proton_chair_searching_for.snd";
    private static final String SOUND_6 = "sound/proton_chair_time.snd";
    private static final String SOUND_7 = "sound/proton_chair_voice_for_you_alone.snd";
    private static final string_id SID_TURN_ON = new string_id(LIFEDAY, "chair_on");
    private static final string_id SID_TURN_OFF = new string_id(LIFEDAY, "chair_off");

    public int OnInitialize(obj_id self) throws InterruptedException
    {
        removeObjVar(self, "chairOn");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        removeObjVar(self, "chairOn");
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (inHouse(self) && getPosture(player) == POSTURE_SITTING)
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU6, new string_id("", ""));
            if (hasObjVar(self, "chairOn"))
            {
                mi.addRootMenu(menu_info_types.SERVER_MENU6, SID_TURN_OFF);
            }
            else
            {
                mi.addRootMenu(menu_info_types.SERVER_MENU6, SID_TURN_ON);
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
                }
            }
            else if (item == menu_info_types.SERVER_MENU6 && hasObjVar(self, "chairOn"))
            {
                removeObjVar(self, "chairOn");
            }
        }
        return SCRIPT_CONTINUE;
    }
    private boolean inHouse(obj_id device) throws InterruptedException
    {
        obj_id ship = space_transition.getContainingShip(getContainedBy(device));
        String templateName = "";
        if (isIdValid(ship))
        {
            templateName = getTemplateName(ship);
        }
        return (utils.isInHouseCellSpace(device) || space_utils.isPobType(templateName)) && !utils.isNestedWithinAPlayer(device);
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
