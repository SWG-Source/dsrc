package script.npe;

import script.library.create;
import script.library.utils;
import script.location;
import script.obj_id;

public class station_experience_manager extends script.base_script
{
    public station_experience_manager()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        obj_id droidCell = getCellId(self, "dockingbay2");
        location droidLoc = new location(92.0f, 0.8f, -11.4f, "thm_spc_nebula_orion_station", droidCell);
        obj_id droidInvis = create.object("object/tangible/npe/npe_node.iff", droidLoc);
        setName(droidInvis, "C-3P0");
        setObjVar(droidInvis, "convo.appearance", "object/mobile/c_3po.iff");
        utils.setScriptVar(self, "objDroidInvis", droidInvis);
        obj_id elevator = getCellId(self, "elevator");
        obj_id cloneLab = getCellId(self, "thrawn");
        obj_id hangar01 = getCellId(self, "hangarbay1");
        obj_id hangar02 = getCellId(self, "hangarbay2");
        obj_id hall1 = getCellId(self, "hall1");
        obj_id hall2 = getCellId(self, "hall2");
        obj_id hall3 = getCellId(self, "hall3");
        obj_id hall4 = getCellId(self, "hall4");
        obj_id armory = getCellId(self, "armory");
        obj_id ranged = getCellId(self, "cloninglab");
        obj_id grenade = getCellId(self, "basementroom02");
        obj_id cantina = getCellId(self, "cantina");
        attachScript(hangar02, "npe.npe_notify_journal");
        attachScript(cloneLab, "npe.npe_notify_clone");
        attachScript(elevator, "npe.npe_notify_elevator");
        attachScript(hangar01, "npe.npe_notify_objects");
        attachScript(hall1, "npe.npe_notify_map");
        attachScript(hall2, "npe.npe_notify_map");
        attachScript(hall3, "npe.npe_notify_map");
        attachScript(hall4, "npe.npe_notify_map");
        attachScript(armory, "npe.npe_armory_spawning");
        attachScript(ranged, "npe.npe_ranged_spawning");
        attachScript(grenade, "npe.npe_notify_grenade");
        setObjVar(cantina, "healing.canhealshock", true);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id elevator = getCellId(self, "elevator");
        obj_id cloneLab = getCellId(self, "thrawn");
        obj_id hangar01 = getCellId(self, "hangarbay1");
        obj_id hangar02 = getCellId(self, "hangarbay2");
        obj_id hall1 = getCellId(self, "hall1");
        obj_id hall2 = getCellId(self, "hall2");
        obj_id hall3 = getCellId(self, "hall3");
        obj_id hall4 = getCellId(self, "hall4");
        obj_id armory = getCellId(self, "armory");
        obj_id ranged = getCellId(self, "cloninglab");
        obj_id grenade = getCellId(self, "basementroom02");
        obj_id cantina = getCellId(self, "cantina");
        attachScript(cloneLab, "npe.npe_notify_clone");
        attachScript(hangar02, "npe.npe_notify_journal");
        attachScript(elevator, "npe.npe_notify_elevator");
        attachScript(hangar01, "npe.npe_notify_objects");
        attachScript(hall1, "npe.npe_notify_map");
        attachScript(hall2, "npe.npe_notify_map");
        attachScript(hall3, "npe.npe_notify_map");
        attachScript(hall4, "npe.npe_notify_map");
        attachScript(armory, "npe.npe_armory_spawning");
        attachScript(ranged, "npe.npe_ranged_spawning");
        attachScript(grenade, "npe.npe_notify_grenade");
        setObjVar(cantina, "healing.canhealshock", true);
        return SCRIPT_CONTINUE;
    }
}
