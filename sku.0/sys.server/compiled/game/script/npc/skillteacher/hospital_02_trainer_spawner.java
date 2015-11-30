package script.npc.skillteacher;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.utils;

public class hospital_02_trainer_spawner extends script.base_script
{
    public hospital_02_trainer_spawner()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        dictionary params = new dictionary();
        params.put("stuff", self);
        messageTo(self, "spawnThings", params, 20, true);
        return SCRIPT_CONTINUE;
    }
    public void spawnEveryone(obj_id self) throws InterruptedException
    {
        spawnDoctor(self);
    }
    public void spawnCombatMedic(obj_id self) throws InterruptedException
    {
        if (utils.checkConfigFlag("ScriptFlags", "noEliteTrainers"))
        {
            return;
        }
        obj_id room = getCellId(self, "rooma");
        location combatmedicloc = new location(3.13f, 0.18f, 3.42f, "tatooine", room);
        obj_id combatmedic = create.object("trainer_combatmedic", combatmedicloc);
        setCreatureStatic(combatmedic, true);
        create.addDestroyMessage(combatmedic, "combatMedicDied", 10f, self);
        setInvulnerable(combatmedic, true);
        return;
    }
    public void spawnMedic(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "mainroom");
        location medicloc = new location(3.64f, 0.18f, -8.19f, "tatooine", room);
        obj_id medic = create.object("trainer_1hsword", medicloc);
        setCreatureStatic(medic, true);
        create.addDestroyMessage(medic, "medicDied", 10f, self);
        setInvulnerable(medic, true);
        return;
    }
    public void spawnDoctor(obj_id self) throws InterruptedException
    {
        if (utils.checkConfigFlag("ScriptFlags", "noEliteTrainers"))
        {
            return;
        }
        obj_id room = getCellId(self, "mainroom");
        location doctorloc = new location(-1.19f, 0.18f, -1.89f, "tatooine", room);
        obj_id doctor = create.object("surgical_droid_21b", doctorloc);
        attachScript(doctor, "conversation.doctor");
        setCreatureStatic(doctor, true);
        create.addDestroyMessage(doctor, "doctorDied", 10f, self);
        setInvulnerable(doctor, true);
        return;
    }
    public int spawnThings(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id stuffs = params.getObjId("stuff");
        spawnEveryone(stuffs);
        return SCRIPT_CONTINUE;
    }
    public int combatMedicDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnCombatMedic(self);
        return SCRIPT_CONTINUE;
    }
    public int medicDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnMedic(self);
        return SCRIPT_CONTINUE;
    }
    public int doctorDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnDoctor(self);
        return SCRIPT_CONTINUE;
    }
}
