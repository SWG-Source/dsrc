package script.theme_park.dungeon.avatar_platform;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.player_structure;
import script.library.badge;
import script.library.space_dungeon;

public class terminal_main_console extends script.base_script
{
    public terminal_main_console()
    {
    }
    public static final String STF = "dungeon/avatar_platform";
    public static final string_id ENTER_CODE = new string_id(STF, "enter_code");
    public static final string_id WARNING_DESTRUCT = new string_id(STF, "warning_destruct");
    public static final string_id WARNING_ALREADY = new string_id(STF, "warning_already");
    public static final string_id NO_EFFECT = new string_id(STF, "no_effect");
    public static final string_id INCORRECT_CODE = new string_id(STF, "incorrect_code");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int menu = mi.addRootMenu(menu_info_types.ITEM_USE, ENTER_CODE);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (groundquests.isTaskActive(player, "ep3_avatar_self_destruct", "selfDestruct"))
            {
                keypad(player);
                return SCRIPT_CONTINUE;
            }
            if (groundquests.hasCompletedTask(player, "ep3_avatar_self_destruct", "selfDestruct"))
            {
                sendSystemMessage(player, WARNING_ALREADY);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                sendSystemMessage(player, NO_EFFECT);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void keypad(obj_id player) throws InterruptedException
    {
        obj_id self = getSelf();
        int pid = createSUIPage("Script.Keypad", self, player, "KeypadCallback");
        subscribeToSUIProperty(pid, "result.numberBox", "localtext");
        subscribeToSUIProperty(pid, "buttonEnter", "ButtonPressed");
        showSUIPage(pid);
        return;
    }
    public int KeypadCallback(obj_id self, dictionary params) throws InterruptedException
    {
        String result = params.getString("result.numberBox" + "." + "localtext");
        String button = params.getString("buttonEnter.ButtonPressed");
        obj_id player = params.getObjId("player");
        if (!isIdValid(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (button == null)
        {
            button = "none";
        }
        if (result == null)
        {
            result = "0";
        }
        String passkey = getStringObjVar(self, "passcode");
        if (passkey == null || passkey.equals(""))
        {
            passkey = "nothing";
        }
        if (button.equals("enter"))
        {
            if (passkey.equals(result))
            {
                obj_id structure = getTopMostContainer(self);
                obj_id[] players = space_dungeon.getParticipantIds(structure);
                if (players == null)
                {
                    return SCRIPT_CONTINUE;
                }
                int numPlayers = players.length;
                if (numPlayers > 0)
                {
                    for (int i = 0; i < numPlayers; i++)
                    {
                        if (groundquests.isTaskActive(players[i], "ep3_avatar_self_destruct", "selfDestruct"))
                        {
                            groundquests.sendSignal(players[i], "destructSequenceStarted");
                            groundquests.sendSignal(players[i], "avatarDestructSequence");
                        }
                        sendSystemMessage(players[i], WARNING_DESTRUCT);
                    }
                    badge.grantBadge(players, "bdg_kash_avatar_zssik");
                }
                setObjVar(structure, "avatar_platform.avatar_destruct_sequence", 1);
                dictionary who = new dictionary();
                who.put("player", player);
                messageTo(structure, "handleDestruct", who, 1f, false);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                sendSystemMessage(player, INCORRECT_CODE);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
