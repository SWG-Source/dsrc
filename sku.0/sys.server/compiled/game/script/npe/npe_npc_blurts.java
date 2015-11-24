package script.npe;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.create;
import script.library.chat;
import java.util.HashSet;
import script.library.ai_lib;

public class npe_npc_blurts extends script.base_script
{
    public npe_npc_blurts()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setAnimationMood(self, "standing");
        messageTo(self, "npeSetName", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setAnimationMood(self, "standing");
        messageTo(self, "npeSetName", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        setCondition(self, CONDITION_CONVERSABLE);
        String room = getStringObjVar(self, "npe.blurt.room");
        String name = getStringObjVar(self, "npe.blurt.name");
        blurtChosenStatement(self, player, room, name);
        return SCRIPT_CONTINUE;
    }
    public int npeSetName(obj_id self, dictionary params) throws InterruptedException
    {
        String myName = utils.getStringObjVar(self, "npe.blurt.name");
        string_id stIdName = new string_id("npe", myName);
        String stfName = utils.packStringId(stIdName);
        if (myName.equals("entertainer_2"))
        {
            setAnimationMood(self, "npc_dance_basic");
        }
        if (myName.equals("patron_1"))
        {
            setAnimationMood(self, "npc_sitting_chair");
        }
        if (myName.startsWith("pilot"))
        {
            stIdName = new string_id("npe", "pilot");
            stfName = utils.packStringId(stIdName);
        }
        if (myName.startsWith("guard"))
        {
            stIdName = new string_id("npe", "guard");
            stfName = utils.packStringId(stIdName);
        }
        if (myName.startsWith("patient"))
        {
            stIdName = new string_id("npe", "patient");
            stfName = utils.packStringId(stIdName);
        }
        setName(self, stfName);
        return SCRIPT_CONTINUE;
    }
    public string_id blurtChosenStatement(obj_id npc, obj_id player, String roomName, String npcName) throws InterruptedException
    {
        string_id message = randomizeChat(roomName, npcName);
        chat.chat(npc, player, message, chat.ChatFlag_targetOnly);
        return message;
    }
    public string_id randomizeChat(String roomName, String npcName) throws InterruptedException
    {
        String[] myArray = new String[3];
        myArray[0] = npcName + "_1";
        myArray[1] = npcName + "_2";
        myArray[2] = npcName + "_3";
        int max = myArray.length;
        string_id message = new string_id("npe/" + roomName, myArray[rand(0, max - 1)]);
        return message;
    }
}
