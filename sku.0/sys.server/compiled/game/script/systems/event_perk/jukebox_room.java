package script.systems.event_perk;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class jukebox_room extends script.base_script
{
    public jukebox_room()
    {
    }
    public static final String JUKBOX_SONG_OBJVAR = "storyteller.jukebox_song";
    public static final String ROOMS_JUKEBOX_OBJVAR = "storyteller.room.myJukebox";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "stopAndCleanUp", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToLoseItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (isPlayer(item))
        {
            playMusic(item, "sound/music_silence.snd");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        String song = getMyJukeboxSong(self);
        if (isPlayer(item))
        {
            if (allowedToListen(self, item))
            {
                if (!song.equals("none") && song.length() > 0)
                {
                    playMusic(item, song);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean allowedToListen(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id storyteller = getObjIdObjVar(self, "storytellerid");
        if (isIdValid(storyteller))
        {
            if (storyteller == player)
            {
                return true;
            }
            else if (utils.hasScriptVar(player, "storytellerAssistant"))
            {
                obj_id whoAmIAssisting = utils.getObjIdScriptVar(player, "storytellerAssistant");
                if (isIdValid(whoAmIAssisting) && storyteller == whoAmIAssisting)
                {
                    return true;
                }
            }
            else if (utils.hasScriptVar(player, "storytellerid"))
            {
                obj_id playersStoryteller = utils.getObjIdScriptVar(player, "storytellerid");
                if (isIdValid(playersStoryteller) && storyteller == playersStoryteller)
                {
                    return true;
                }
            }
            else 
            {
                return true;
            }
        }
        return false;
    }
    public String getMyJukeboxSong(obj_id room) throws InterruptedException
    {
        String song = "none";
        obj_id myJukebox = obj_id.NULL_ID;
        if (hasObjVar(room, ROOMS_JUKEBOX_OBJVAR))
        {
            myJukebox = getObjIdObjVar(room, ROOMS_JUKEBOX_OBJVAR);
            if (isIdValid(myJukebox) && exists(myJukebox))
            {
                song = getStringObjVar(myJukebox, JUKBOX_SONG_OBJVAR);
            }
            else 
            {
                messageTo(room, "stopAndCleanUp", null, 1, false);
            }
        }
        return song;
    }
    public int prepareToPlaySelectedSong(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] contents = getContents(self);
        for (int i = 0; i < contents.length; i++)
        {
            obj_id item = contents[i];
            if (isPlayer(item))
            {
                if (allowedToListen(self, item))
                {
                    playMusic(item, "sound/music_silence.snd");
                }
            }
        }
        messageTo(self, "playSelectedSong", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int playSelectedSong(obj_id self, dictionary params) throws InterruptedException
    {
        String song = getMyJukeboxSong(self);
        if (!song.equals("none") && song.length() > 0)
        {
            obj_id[] contents = getContents(self);
            for (int i = 0; i < contents.length; i++)
            {
                obj_id item = contents[i];
                if (isPlayer(item))
                {
                    if (allowedToListen(self, item))
                    {
                        playMusic(item, song);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int stopSong(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] contents = getContents(self);
        for (int i = 0; i < contents.length; i++)
        {
            obj_id item = contents[i];
            if (isPlayer(item))
            {
                if (allowedToListen(self, item))
                {
                    playMusic(item, "sound/music_silence.snd");
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int stopAndCleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] contents = getContents(self);
        for (int i = 0; i < contents.length; i++)
        {
            obj_id item = contents[i];
            if (isPlayer(item))
            {
                if (allowedToListen(self, item))
                {
                    playMusic(item, "sound/music_silence.snd");
                }
            }
        }
        detachScript(self, "systems.event_perk.jukebox_room");
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, ROOMS_JUKEBOX_OBJVAR))
        {
            removeObjVar(self, ROOMS_JUKEBOX_OBJVAR);
        }
        return SCRIPT_CONTINUE;
    }
}
