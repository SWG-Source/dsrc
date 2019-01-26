package script.theme_park.dungeon.death_watch_bunker;

import script.dictionary;
import script.location;
import script.obj_id;

public class debris_reload_check extends script.base_script
{
    public debris_reload_check()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        boolean empty = true;
        obj_id mom = getTopMostContainer(self);
        obj_id room = getCellId(mom, "smallroom35");
        obj_id[] cell_contents = getContents(room);
        int numInCell = cell_contents.length;
        if (cell_contents != null && numInCell > 0)
        {
            for (obj_id cell_content : cell_contents) {
                if (isIdValid(cell_content) && isPlayer(cell_content)) {
                    empty = false;
                }
            }
        }
        if (empty != true)
        {
            destroyObject(self);
            int spawnNum = getIntObjVar(self, "spawn_number");
            obj_id dad = getObjIdObjVar(self, "mom");
            if (dad != null && spawnNum != 0)
            {
                dictionary info = new dictionary();
                info.put("spawnNumber", spawnNum);
                info.put("spawnMob", self);
                messageTo(dad, "tellingMomIDied", info, 600, false);
            }
        }
        else 
        {
            createTerminal(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void createTerminal(obj_id self) throws InterruptedException
    {
        location here = getLocation(self);
        String planet = here.area;
        obj_id top = getTopMostContainer(self);
        obj_id cell = getCellId(top, "smallroom35");
        location terminal = new location(115.3f, -64, -108.92f, planet, cell);
        obj_id term = createObject("object/tangible/dungeon/terminal_free_s1.iff", terminal);
        setYaw(term, -90);
        setObjVar(self, "term", term);
        setObjVar(term, "mom", self);
        attachScript(term, "theme_park.dungeon.death_watch_bunker.debris_terminal");
        return;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "term"))
        {
            obj_id terminal = getObjIdObjVar(self, "term");
            if (isIdValid(terminal))
            {
                messageTo(terminal, "cleanUpTime", null, 30, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDisabled(obj_id self, obj_id killer) throws InterruptedException
    {
        obj_id mom = getObjIdObjVar(self, "mom");
        if (mom == null)
        {
            return SCRIPT_OVERRIDE;
        }
        int spawnNum = getIntObjVar(self, "spawn_number");
        if (spawnNum == 0)
        {
            return SCRIPT_OVERRIDE;
        }
        dictionary info = new dictionary();
        info.put("spawnNumber", spawnNum);
        info.put("spawnMob", self);
        debugSpeakMsg(self, "TELLING MOM I DIED ");
        messageTo(mom, "tellingMomIDied", info, 120, false);
        return SCRIPT_CONTINUE;
    }
}
