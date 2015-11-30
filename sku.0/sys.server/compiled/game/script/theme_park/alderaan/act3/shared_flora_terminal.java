package script.theme_park.alderaan.act3;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;

public class shared_flora_terminal extends script.base_script
{
    public shared_flora_terminal()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id warehouse = getObjIdObjVar(self, "coa3.shared.warehouse");
        int numGuards = getIntObjVar(warehouse, "coa3.shared.numGuards");
        if (numGuards > 0)
        {
            return SCRIPT_CONTINUE;
        }
        int mnu = mi.addRootMenu(menu_info_types.SELF_DESTRUCT, new string_id("self_destruct", "self_destruct"));
        mi.addSubMenu(mnu, menu_info_types.THIRTY_SEC, new string_id("self_destruct", "thirty"));
        mi.addSubMenu(mnu, menu_info_types.FIFTEEN_SEC, new string_id("self_destruct", "fifteen"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.THIRTY_SEC)
        {
            obj_id warehouse = getObjIdObjVar(self, "coa3.shared.warehouse");
            playMusic(player, "sound/music_darth_vader_theme.snd");
            sendSystemMessage(player, new string_id("self_destruct", "thirty_seconds"));
            dictionary destroyer = new dictionary();
            destroyer.put("termUser", player);
            destroyer.put("warehouse", warehouse);
            messageTo(self, "firstPop", destroyer, 10, true);
            messageTo(self, "firstPop", destroyer, 20, true);
            messageTo(self, "blowUp", destroyer, 30, true);
            return SCRIPT_CONTINUE;
        }
        else if (item == menu_info_types.FIFTEEN_SEC)
        {
            obj_id warehouse = getObjIdObjVar(self, "coa3.shared.warehouse");
            sendSystemMessage(player, new string_id("self_destruct", "fifteen_seconds"));
            dictionary destroyer = new dictionary();
            destroyer.put("termUser", player);
            destroyer.put("warehouse", warehouse);
            messageTo(self, "firstPop", destroyer, 5, true);
            messageTo(self, "blowUp", destroyer, 15, true);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int blowUp(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id termUser = params.getObjId("termUser");
        obj_id warehouse = params.getObjId("warehouse");
        location death = getLocation(warehouse);
        playClientEffectLoc(termUser, "clienteffect/combat_explosion_lair_large.cef", death, 10f);
        death.x = death.x + 5;
        playClientEffectLoc(termUser, "clienteffect/combat_explosion_lair_large.cef", death, 10f);
        death.z = death.z + 5;
        playClientEffectLoc(termUser, "clienteffect/combat_explosion_lair_large.cef", death, 10f);
        death.x = death.x - 10;
        playClientEffectLoc(termUser, "clienteffect/combat_explosion_lair_large.cef", death, 10f);
        death.z = death.z - 10;
        playClientEffectLoc(termUser, "clienteffect/combat_explosion_lair_large.cef", death, 10f);
        obj_id player = getObjIdObjVar(warehouse, "coa3.shared.playerId");
        if (hasObjVar(warehouse, "coa3.imperial.playerId"))
        {
            player = getObjIdObjVar(warehouse, "coa3.imperial.playerId");
        }
        else if (hasObjVar(warehouse, "coa3.rebel.playerId"))
        {
            player = getObjIdObjVar(warehouse, "coa3.rebel.playerId");
        }
        params.put("player", player);
        messageTo(player, "handleFloraCheck", params, 1, true);
        boolean blowUp = destroyObject(warehouse);
        return SCRIPT_CONTINUE;
    }
    public int firstPop(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id termUser = params.getObjId("termUser");
        obj_id warehouse = getTopMostContainer(self);
        location death = getLocation(warehouse);
        playClientEffectLoc(termUser, "clienteffect/combat_explosion_lair_large.cef", death, 10f);
        return SCRIPT_CONTINUE;
    }
}
