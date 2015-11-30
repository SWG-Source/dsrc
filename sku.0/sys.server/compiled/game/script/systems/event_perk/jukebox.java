package script.systems.event_perk;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;

public class jukebox extends script.base_script
{
    public jukebox()
    {
    }
    public static final String TBL_SONGS = "datatables/event_perk/jukebox_songs.iff";
    public static final String JUKBOX_SONG_OBJVAR = "storyteller.jukebox_song";
    public static final String JUKBOX_RANGE_OBJVAR = "storyteller.jukebox_range";
    public static final String JUKEBOX_ROOM_SCRIPT = "systems.event_perk.jukebox_room";
    public static final String JUKEBOX_ROOM_OBJVAR = "storyteller.room.myJukebox";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "initializeJukebox", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "initializeJukebox", null, 9, false);
        return SCRIPT_CONTINUE;
    }
    public int initializeJukebox(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "storytellerid"))
        {
            if (!hasTriggerVolume(self, "storytellerJukebox"))
            {
                float jukebox_range = 100f;
                obj_id myBuilding = getTopMostContainer(self);
                if (isIdValid(myBuilding) && myBuilding != self)
                {
                    jukebox_range = 30f;
                }
                setObjVar(self, JUKBOX_RANGE_OBJVAR, jukebox_range);
                createTriggerVolume("storytellerJukebox", jukebox_range, true);
            }
        }
        else 
        {
            messageTo(self, "cleanUpJukebox", null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (!isIdValid(breacher) || !isPlayer(breacher))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, JUKBOX_SONG_OBJVAR))
        {
            return SCRIPT_CONTINUE;
        }
        if (allowedToListen(self, breacher))
        {
            if (volumeName.equals("storytellerJukebox"))
            {
                String song = getStringObjVar(self, JUKBOX_SONG_OBJVAR);
                if (song != null && song.length() > 0 && !song.equals("none"))
                {
                    playMusic(breacher, song);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeExited(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (!isIdValid(breacher) || !isPlayer(breacher))
        {
            return SCRIPT_CONTINUE;
        }
        if (allowedToListen(self, breacher))
        {
            if (volumeName.equals("storytellerJukebox"))
            {
                playMusic(breacher, "sound/music_silence.snd");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (allowedToUse(self, player))
        {
            int userMenu = mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("event_perk", "jukebox_options"));
            mi.addSubMenu(userMenu, menu_info_types.SERVER_MENU4, new string_id("event_perk", "jukebox_start_music"));
            mi.addSubMenu(userMenu, menu_info_types.SERVER_MENU5, new string_id("event_perk", "jukebox_stop_music"));
            mi.addSubMenu(userMenu, menu_info_types.SERVER_MENU6, new string_id("event_perk", "jukebox_start_listening"));
            mi.addSubMenu(userMenu, menu_info_types.SERVER_MENU7, new string_id("event_perk", "jukebox_stop_listening"));
        }
        else if (allowedToListen(self, player))
        {
            int listenerMenu = mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("event_perk", "jukebox_options"));
            mi.addSubMenu(listenerMenu, menu_info_types.SERVER_MENU6, new string_id("event_perk", "jukebox_start_listening"));
            mi.addSubMenu(listenerMenu, menu_info_types.SERVER_MENU7, new string_id("event_perk", "jukebox_stop_listening"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE || item == menu_info_types.SERVER_MENU4)
        {
            if (allowedToUse(self, player))
            {
                showMusicSelection(self, player);
            }
            else if (allowedToListen(self, player))
            {
                startListeningToMusic(self, player);
            }
        }
        else if (item == menu_info_types.SERVER_MENU5)
        {
            if (allowedToUse(self, player))
            {
                messageTo(self, "stopMusic", null, 1, false);
            }
        }
        if (item == menu_info_types.SERVER_MENU6)
        {
            if (allowedToListen(self, player))
            {
                startListeningToMusic(self, player);
            }
        }
        if (item == menu_info_types.SERVER_MENU7)
        {
            playMusic(player, "sound/music_silence.snd");
        }
        return SCRIPT_CONTINUE;
    }
    public boolean allowedToUse(obj_id self, obj_id player) throws InterruptedException
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
        }
        return false;
    }
    public boolean allowedToListen(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id storyteller = getObjIdObjVar(self, "storytellerid");
        if (isIdValid(storyteller))
        {
            if (allowedToUse(self, player))
            {
                return true;
            }
            if (utils.hasScriptVar(player, "storytellerid"))
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
    public void showMusicSelection(obj_id self, obj_id player) throws InterruptedException
    {
        String[] rawSongs = dataTableGetStringColumn(TBL_SONGS, 0);
        String[] alphabetizedSongList = getAlphabetizedSongList(rawSongs);
        String[] songs = new String[alphabetizedSongList.length];
        for (int i = 0; i < alphabetizedSongList.length; i++)
        {
            songs[i] = "@event_perk_jukebox_songs:" + alphabetizedSongList[i];
        }
        sui.listbox(self, player, "@event_perk_jukebox_songs:songs_d", sui.OK_CANCEL, "@event_perk_jukebox_songs:songs_t", songs, "selectMusic", true);
        return;
    }
    public String[] getAlphabetizedSongList(String[] songList) throws InterruptedException
    {
        String[] namesColonSongs = new String[songList.length];
        for (int i = 0; i < songList.length; i++)
        {
            String temp = getString(new string_id("event_perk_jukebox_songs", songList[i])) + "|" + songList[i];
            namesColonSongs[i] = temp;
        }
        Arrays.sort(namesColonSongs);
        String[] finalList = new String[namesColonSongs.length];
        for (int j = 0; j < namesColonSongs.length; j++)
        {
            String[] splitText = split(namesColonSongs[j], '|');
            finalList[j] = splitText[1];
        }
        return finalList;
    }
    public void startListeningToMusic(obj_id self, obj_id player) throws InterruptedException
    {
        if (hasObjVar(self, JUKBOX_SONG_OBJVAR))
        {
            String song = getStringObjVar(self, JUKBOX_SONG_OBJVAR);
            if (song != null && song.length() > 0 && !song.equals("none"))
            {
                playMusic(player, song);
            }
        }
        return;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        removeObjVar(self, JUKBOX_SONG_OBJVAR);
        return SCRIPT_CONTINUE;
    }
    public int selectMusic(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            idx = 0;
        }
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        String[] songTemplate = dataTableGetStringColumn(TBL_SONGS, 1);
        String[] rawSongs = dataTableGetStringColumn(TBL_SONGS, 0);
        String[] alphabetizedSongList = getAlphabetizedSongList(rawSongs);
        String songTitle = alphabetizedSongList[idx];
        int songIndex = dataTableSearchColumnForString(songTitle, 0, TBL_SONGS);
        String song = songTemplate[songIndex];
        setObjVar(self, JUKBOX_SONG_OBJVAR, song);
        messageTo(self, "prepareToPlaySelectedSong", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int stopMusic(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, JUKBOX_SONG_OBJVAR, "none");
        stopPlayingMusic(self);
        return SCRIPT_CONTINUE;
    }
    public void stopPlayingMusic(obj_id self) throws InterruptedException
    {
        location here = getLocation(self);
        float jukebox_range = getFloatObjVar(self, JUKBOX_RANGE_OBJVAR) + 10;
        obj_id[] players = getPlayerCreaturesInRange(here, jukebox_range);
        for (int i = 0; i < players.length; i++)
        {
            obj_id player = players[i];
            if (isIdValid(player))
            {
                if (allowedToListen(self, player))
                {
                    playMusic(player, "sound/music_silence.snd");
                }
            }
        }
        obj_id thisRoom = here.cell;
        if (isIdValid(thisRoom))
        {
            if (!hasScript(thisRoom, JUKEBOX_ROOM_SCRIPT))
            {
                attachScript(thisRoom, JUKEBOX_ROOM_SCRIPT);
            }
            if (!hasObjVar(thisRoom, JUKEBOX_ROOM_OBJVAR))
            {
                setObjVar(thisRoom, JUKEBOX_ROOM_OBJVAR, self);
            }
            messageTo(thisRoom, "stopSong", null, 1, false);
        }
        return;
    }
    public int prepareToPlaySelectedSong(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id storyteller = getObjIdObjVar(self, "storytellerid");
        if (!isIdValid(storyteller))
        {
            return SCRIPT_CONTINUE;
        }
        location here = getLocation(self);
        float jukebox_range = getFloatObjVar(self, JUKBOX_RANGE_OBJVAR);
        obj_id[] players = getPlayerCreaturesInRange(here, jukebox_range);
        for (int i = 0; i < players.length; i++)
        {
            obj_id player = players[i];
            if (allowedToListen(self, player))
            {
                playMusic(player, "sound/music_silence.snd");
            }
        }
        obj_id thisRoom = here.cell;
        if (isIdValid(thisRoom))
        {
            if (!hasScript(thisRoom, JUKEBOX_ROOM_SCRIPT))
            {
                attachScript(thisRoom, JUKEBOX_ROOM_SCRIPT);
            }
            if (!hasObjVar(thisRoom, JUKEBOX_ROOM_OBJVAR))
            {
                setObjVar(thisRoom, JUKEBOX_ROOM_OBJVAR, self);
            }
            messageTo(thisRoom, "prepareToPlaySelectedSong", null, 1, false);
        }
        messageTo(self, "playSelectedSong", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int playSelectedSong(obj_id self, dictionary params) throws InterruptedException
    {
        String song = getStringObjVar(self, JUKBOX_SONG_OBJVAR);
        if (!song.equals("none"))
        {
            obj_id storyteller = getObjIdObjVar(self, "storytellerid");
            if (!isIdValid(storyteller))
            {
                return SCRIPT_CONTINUE;
            }
            location here = getLocation(self);
            float jukebox_range = getFloatObjVar(self, JUKBOX_RANGE_OBJVAR);
            obj_id[] players = getPlayerCreaturesInRange(here, jukebox_range);
            for (int i = 0; i < players.length; i++)
            {
                obj_id player = players[i];
                if (allowedToListen(self, player))
                {
                    playMusic(player, song);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int cleanUpJukebox(obj_id self, dictionary params) throws InterruptedException
    {
        cleanUpRoom(self);
        messageTo(self, "destroyJukebox", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int destroyJukebox(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        cleanUpRoom(self);
        stopPlayingMusic(self);
        return SCRIPT_CONTINUE;
    }
    public void cleanUpRoom(obj_id self) throws InterruptedException
    {
        if (isIdValid(self))
        {
            location here = getLocation(self);
            obj_id thisRoom = here.cell;
            if (isIdValid(thisRoom) && hasScript(thisRoom, JUKEBOX_ROOM_SCRIPT))
            {
                messageTo(thisRoom, "stopAndCleanUp", null, 1, false);
            }
        }
        return;
    }
}
