package script.theme_park.imperial;

import script.*;
import script.library.chat;

public class bldg_destroy extends script.base_script
{
    public bldg_destroy()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "bark", null, 10, true);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int mnu = mi.addRootMenu(menu_info_types.SELF_DESTRUCT, new string_id("self_destruct", "self_destruct"));
        mi.addSubMenu(mnu, menu_info_types.THIRTY_SEC, new string_id("self_destruct", "thirty"));
        mi.addSubMenu(mnu, menu_info_types.FIFTEEN_SEC, new string_id("self_destruct", "fifteen"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.THIRTY_SEC)
        {
            obj_id bldg = getTopMostContainer(self);
            playMusic(player, "sound/music_darth_vader_theme.snd");
            chat.chat(self, new string_id("self_destruct", "thirty_seconds"));
            dictionary destroyer = new dictionary();
            destroyer.put("player", player);
            destroyer.put("bldg", bldg);
            messageTo(self, "firstPop", destroyer, 10, true);
            messageTo(self, "firstPop", destroyer, 20, true);
            messageTo(self, "blowUp", destroyer, 30, true);
            return SCRIPT_CONTINUE;
        }
        else if (item == menu_info_types.FIFTEEN_SEC)
        {
            obj_id bldg = getTopMostContainer(self);
            chat.chat(self, new string_id("self_destruct", "fifteen_seconds"));
            dictionary destroyer = new dictionary();
            destroyer.put("player", player);
            destroyer.put("bldg", bldg);
            messageTo(self, "firstPop", destroyer, 5, true);
            messageTo(self, "blowUp", destroyer, 15, true);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int blowUp(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id viewer = params.getObjId("player");
        obj_id bldg = params.getObjId("bldg");
        location death = getLocation(bldg);
        playClientEffectLoc(viewer, "clienteffect/combat_explosion_lair_large.cef", death, 10.0f);
        death.x = death.x + 5;
        playClientEffectLoc(viewer, "clienteffect/combat_explosion_lair_large.cef", death, 10.0f);
        death.z = death.z + 5;
        playClientEffectLoc(viewer, "clienteffect/combat_explosion_lair_large.cef", death, 10.0f);
        death.x = death.x - 10;
        playClientEffectLoc(viewer, "clienteffect/combat_explosion_lair_large.cef", death, 10.0f);
        death.z = death.z - 10;
        playClientEffectLoc(viewer, "clienteffect/combat_explosion_lair_large.cef", death, 10.0f);
        destroyObject(bldg);
        return SCRIPT_CONTINUE;
    }
    public int firstPop(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id viewer = params.getObjId("player");
        obj_id bldg = getTopMostContainer(self);
        location death = getLocation(bldg);
        playClientEffectLoc(viewer, "clienteffect/combat_explosion_lair_large.cef", death, 10.0f);
        obj_id player = getObjIdObjVar(bldg, "player");
        if (!hasObjVar(self, "sentMsgToPlayer"))
        {
            messageTo(player, "finishQuest", null, 1, true);
            setObjVar(self, "sentMsgToPlayer", 1);
        }
        return SCRIPT_CONTINUE;
    }
    public int bark(obj_id self, dictionary params) throws InterruptedException
    {
        debugSpeakMsg(self, "I'm right here at " + getLocation(self));
        messageTo(self, "bark", null, 10, true);
        return SCRIPT_CONTINUE;
    }
}
