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

public class hospital_trainer_spawner extends script.base_script
{
    public hospital_trainer_spawner()
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
        location combatmedicloc = new location(-17.97f, 0.26f, 10.77f, "tatooine", room);
        obj_id combatmedic = create.object("trainer_combatmedic", combatmedicloc);
        create.addDestroyMessage(combatmedic, "combatmedicDied", 10f, self);
        setCreatureStatic(combatmedic, true);
        setInvulnerable(combatmedic, true);
        return;
    }
    public void spawnMedic(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "rooma");
        location medicloc = new location(-16.92f, 0.26f, -0.14f, "tatooine", room);
        obj_id medic = create.object("trainer_medic", medicloc);
        create.addDestroyMessage(medic, "medicDied", 10f, self);
        setCreatureStatic(medic, true);
        setInvulnerable(medic, true);
        return;
    }
    public void spawnDoctor(obj_id self) throws InterruptedException
    {
        if (utils.checkConfigFlag("ScriptFlags", "noEliteTrainers"))
        {
            return;
        }
        obj_id room = getCellId(self, "rooma");
        location doctorloc = new location(-25.52f, 0.26f, -3.48f, "tatooine", room);
        obj_id doctor = create.object("surgical_droid_21b", doctorloc);
        create.addDestroyMessage(doctor, "doctorDied", 10f, self);
        attachScript(doctor, "conversation.doctor");
        setCreatureStatic(doctor, true);
        setInvulnerable(doctor, true);
        return;
    }
    public void spawnCombatMedic2(obj_id self) throws InterruptedException
    {
        if (utils.checkConfigFlag("ScriptFlags", "noEliteTrainers"))
        {
            return;
        }
        obj_id room = getCellId(self, "roomb");
        location marksLoc = new location(27.82f, 0.26f, 6.8f, "tatooine", room);
        obj_id combatmedic = create.object("trainer_combatmedic", marksLoc);
        create.addDestroyMessage(combatmedic, "combatmedic2Died", 10f, self);
        setCreatureStatic(combatmedic, true);
        setInvulnerable(combatmedic, true);
        return;
    }
    public void spawnMedic2(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "roomb");
        location medic2loc = new location(13.95f, 0.26f, 5.08f, "tatooine", room);
        obj_id medic2 = create.object("trainer_medic", medic2loc);
        create.addDestroyMessage(medic2, "medic2Died", 10f, self);
        setCreatureStatic(medic2, true);
        setInvulnerable(medic2, true);
        return;
    }
    public void spawnDoctor2(obj_id self) throws InterruptedException
    {
        if (utils.checkConfigFlag("ScriptFlags", "noEliteTrainers"))
        {
            return;
        }
        obj_id room = getCellId(self, "roomb");
        location doctor2loc = new location(20.48f, 0.26f, -5.8f, "tatooine", room);
        obj_id doctor2 = create.object("trainer_doctor", doctor2loc);
        create.addDestroyMessage(doctor2, "doctor2Died", 10f, self);
        setCreatureStatic(doctor2, true);
        setInvulnerable(doctor2, true);
        return;
    }
    public int spawnThings(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id stuffs = params.getObjId("stuff");
        spawnEveryone(stuffs);
        return SCRIPT_CONTINUE;
    }
    public int combatmedicDied(obj_id self, dictionary params) throws InterruptedException
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
    public int combatmedic2Died(obj_id self, dictionary params) throws InterruptedException
    {
        spawnCombatMedic2(self);
        return SCRIPT_CONTINUE;
    }
    public int medic2Died(obj_id self, dictionary params) throws InterruptedException
    {
        spawnMedic2(self);
        return SCRIPT_CONTINUE;
    }
    public int doctor2Died(obj_id self, dictionary params) throws InterruptedException
    {
        spawnDoctor2(self);
        return SCRIPT_CONTINUE;
    }
}
