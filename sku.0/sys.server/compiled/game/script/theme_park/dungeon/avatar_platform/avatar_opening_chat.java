package script.theme_park.dungeon.avatar_platform;

import script.dictionary;
import script.library.chat;
import script.library.groundquests;
import script.location;
import script.obj_id;
import script.string_id;

public class avatar_opening_chat extends script.base_script
{
    public avatar_opening_chat()
    {
    }
    public static final String STF = "dungeon/avatar_platform";
    public static final string_id OPENING = new string_id(STF, "trando_chat_01");
    public static final string_id RESPONSE = new string_id(STF, "trando_chat_02");
    public int OnReceivedItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        if (!groundquests.isQuestActive(item, "ep3_trando_hssissk_zssik_10"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id structure = getTopMostContainer(self);
        if (hasObjVar(structure, "avatar_platform.avatar_destruct_sequence") && !hasObjVar(structure, "avatar_platform.explosion_sequence"))
        {
            startExplosionFinal(item, self);
        }
        if (hasObjVar(structure, "avatar_platform.trando_chat"))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "handleStartChatting", null, 3.0f, false);
        setObjVar(structure, "avatar_platform.trando_chat", 1);
        return SCRIPT_CONTINUE;
    }
    public int handleStartChatting(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        obj_id trando1 = getObjIdObjVar(structure, "avatar_platform.trando1");
        chat.chat(trando1, OPENING);
        messageTo(self, "handleResponse", null, 5.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleResponse(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        obj_id trando2 = getObjIdObjVar(structure, "avatar_platform.trando2");
        chat.chat(trando2, RESPONSE);
        return SCRIPT_CONTINUE;
    }
    public void startExplosionFinal(obj_id player, obj_id self) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        obj_id[] items = getContents(self);
        if (items != null && items.length > 0)
        {
            for (obj_id item : items) {
                if (isPlayer(item)) {
                    startExplosionSequence(player, self);
                    setObjVar(structure, "avatar_platform.explosion_sequence", 1);
                    return;
                }
            }
        }
        if (hasObjVar(structure, "avatar_platform.explosion_sequence"))
        {
            removeObjVar(structure, "avatar_platform.explosion_sequence");
        }
        return;
    }
    public void startExplosionSequence(obj_id player, obj_id self) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        obj_id hangar = getCellId(structure, "mainhangar");
        location here = getLocation(self);
        String planet = here.area;
        dictionary info = new dictionary();
        info.put("player", player);
        int explosion = rand(1, 9);
        if (explosion == 1)
        {
            location explosionPnt = new location(33.9f, 0.0f, 6.7f, planet, hangar);
            playClientEffectLoc(player, "clienteffect/avatar_room_explosion.cef", explosionPnt, 0.0f);
            messageTo(self, "handleMoreExplosions", info, 3.0f, false);
            return;
        }
        if (explosion == 2)
        {
            location explosionPnt = new location(48.5f, 0.0f, 8.8f, planet, hangar);
            playClientEffectLoc(player, "clienteffect/avatar_room_explosion.cef", explosionPnt, 0.0f);
            messageTo(self, "handleMoreExplosions", info, 3.0f, false);
            return;
        }
        if (explosion == 3)
        {
            location explosionPnt = new location(62.5f, 0.0f, 8.5f, planet, hangar);
            playClientEffectLoc(player, "clienteffect/avatar_room_explosion.cef", explosionPnt, 0.0f);
            messageTo(self, "handleMoreExplosions", info, 3.0f, false);
            return;
        }
        if (explosion == 4)
        {
            location explosionPnt = new location(77.5f, 0.0f, 7.2f, planet, hangar);
            playClientEffectLoc(player, "clienteffect/avatar_room_explosion.cef", explosionPnt, 0.0f);
            messageTo(self, "handleMoreExplosions", info, 3.0f, false);
            return;
        }
        if (explosion == 5)
        {
            location explosionPnt = new location(76.7f, 0.0f, 37.1f, planet, hangar);
            playClientEffectLoc(player, "clienteffect/avatar_room_explosion.cef", explosionPnt, 0.0f);
            messageTo(self, "handleMoreExplosions", info, 3.0f, false);
            return;
        }
        if (explosion == 6)
        {
            location explosionPnt = new location(61.6f, 0.0f, 35.6f, planet, hangar);
            playClientEffectLoc(player, "clienteffect/avatar_room_explosion.cef", explosionPnt, 0.0f);
            messageTo(self, "handleMoreExplosions", info, 3.0f, false);
            return;
        }
        if (explosion == 7)
        {
            location explosionPnt = new location(35.9f, 0.0f, 37.1f, planet, hangar);
            playClientEffectLoc(player, "clienteffect/avatar_room_explosion.cef", explosionPnt, 0.0f);
            messageTo(self, "handleMoreExplosions", info, 3.0f, false);
            return;
        }
        if (explosion == 8)
        {
            location explosionPnt = new location(27.4f, 0.0f, 21.6f, planet, hangar);
            playClientEffectLoc(player, "clienteffect/avatar_room_explosion.cef", explosionPnt, 0.0f);
            messageTo(self, "handleMoreExplosions", info, 3.0f, false);
            return;
        }
        if (explosion == 9)
        {
            location explosionPnt = new location(57.5f, 0.0f, 22.5f, planet, hangar);
            playClientEffectLoc(player, "clienteffect/avatar_room_explosion.cef", explosionPnt, 0.0f);
            messageTo(self, "handleMoreExplosions", info, 3.0f, false);
            return;
        }
        return;
    }
    public int handleMoreExplosions(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        startExplosionFinal(player, self);
        return SCRIPT_CONTINUE;
    }
}
