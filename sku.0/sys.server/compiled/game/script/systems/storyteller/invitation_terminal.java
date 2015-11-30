package script.systems.storyteller;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.prose;
import script.library.storyteller;
import script.library.sui;
import script.library.utils;

public class invitation_terminal extends script.base_script
{
    public invitation_terminal()
    {
    }
    public static final String JUKEBOX_SCRIPT = "systems.event_perk.jukebox";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "initializeStorytellerInvitationTerminal", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int initializeStorytellerInvitationTerminal(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "storytellerName"))
        {
            String storytellerName = getStringObjVar(self, "storytellerName");
            setName(self, getString(utils.unpackString(getName(self))) + " (" + storytellerName + ")");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        string_id menuTxt = new string_id("storyteller", "invitation_terminal_menu");
        mi.addRootMenu(menu_info_types.ITEM_USE, menuTxt);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            obj_id storytellerPlayer = getObjIdObjVar(self, "storytellerid");
            String storytellerName = getStringObjVar(self, "storytellerName");
            terminalStoryInviteSui(storytellerPlayer, storytellerName, player, self);
        }
        return SCRIPT_CONTINUE;
    }
    public int terminalStoryInviteSui(obj_id storytellerPlayer, String storytellerName, obj_id player, obj_id terminal) throws InterruptedException
    {
        String title = utils.packStringId(new string_id("storyteller", "sui_invite_title"));
        prose_package pp = prose.getPackage(new string_id("storyteller", "sui_invite_body"));
        prose.setTO(pp, storytellerName);
        String msg = "\0" + packOutOfBandProsePackage(null, pp);
        int pid = sui.msgbox(terminal, player, msg, 2, title, sui.YES_NO, "terminalStoryInviteHandler");
        return pid;
    }
    public int terminalStoryInviteHandler(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id storytellerId = getObjIdObjVar(self, "storytellerid");
        String storytellerName = getStringObjVar(self, "storytellerName");
        
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        dictionary webster = new dictionary();
        webster.put("storytellerPlayer", storytellerId);
        webster.put("storytellerName", storytellerName);
        messageTo(player, "handleStoryTellerInviteAccepted", webster, 0, false);
        return SCRIPT_CONTINUE;
    }
}
