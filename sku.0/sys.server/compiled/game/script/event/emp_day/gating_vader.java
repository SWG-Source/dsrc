package script.event.emp_day;

import script.dictionary;
import script.library.create;
import script.library.factions;
import script.library.groundquests;
import script.location;
import script.obj_id;
import script.string_id;

public class gating_vader extends script.base_script
{
    public gating_vader()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "spawnStormtroopers", null, 30, false);
        return SCRIPT_CONTINUE;
    }
    public int spawnStormtroopers(obj_id self, dictionary params) throws InterruptedException
    {
        String spawn = "stormtrooper_novatrooper";
        location here = getLocation(self);
        location outsideLoc = (location)here.clone();
        outsideLoc.z += 15;
        for (int i = 0; i < 10; i++)
        {
            create.object(spawn, outsideLoc);
        }
        // create AT-STs
        create.object("at_st", outsideLoc);
        create.object("at_st", outsideLoc);

        obj_id room1Cell = getCellId(self, "room1");
        obj_id room2Cell = getCellId(self, "room2");
        obj_id room3Cell = getCellId(self, "room3");
        location room1Loc = (location)here.clone();
        location room2Loc = (location)here.clone();
        location room3Loc = (location)here.clone();
        room1Loc.x = 2.1f;
        room1Loc.z = 1.5f;
        room1Loc.cell = room1Cell;
        room2Loc.x = 2.5f;
        room2Loc.z = -3.7f;
        room2Loc.cell = room2Cell;
        room3Loc.x = -2.4f;
        room3Loc.z = -3.7f;
        room3Loc.cell = room3Cell;
        obj_id room1St = create.object(spawn, room1Loc);
        obj_id room2St = create.object(spawn, room2Loc);
        obj_id room3St = create.object(spawn, room3Loc);
        setYaw(room1St, -90);
        setYaw(room2St, -90);
        setYaw(room3St, 90);
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        int questId = questGetQuestId("quest/emp_day_imperial");
        int task7 = groundquests.getTaskId(questId, "toVader");
        int task8 = groundquests.getTaskId(questId, "empDayComplete");
        int factionHashCode = factions.pvpGetAlignedFaction(item);
        String whichFaction = factions.getFactionNameByHashCode(factionHashCode);
        if (questIsTaskActive(questId, task8, item) || questIsTaskActive(questId, task7, item) || questIsQuestComplete(questId, item))
        {
            playMusic(item, "sound/music_emperor_theme_stereo.snd");
            return SCRIPT_CONTINUE;
        }
        if (whichFaction == null || whichFaction.equals("Rebel"))
        {
            sendSystemMessage(item, new string_id("event/empire_day", "no_entry"));
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
}
