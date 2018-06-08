package script.systems.gcw.space;

import script.dictionary;
import script.library.prose;
import script.library.space_utils;
import script.library.utils;
import script.obj_id;
import script.prose_package;
import script.string_id;

import java.util.Vector;

public class hero_ship extends support_ship {
    private static final String HERO_PILOT_DATA = "datatables/npc/space/space_gcw_hero.iff";
    private final int TAUNT_QUANTITY = 5;
    private final String DESPAWN_TAUNT = "despawn";
    private final String ENTER_COMBAT_TAUNT = "entercombat";
    private final String GOT_HIT_TAUNT = "gothit";
    private final String HIT_YOU_TAUNT = "hityou";
    private final String VANQUISH_TAUNT = "vanquished";

    @Override
    public int OnAttach(obj_id self) throws InterruptedException {
        return SCRIPT_CONTINUE;
    }

    @Override
    public int OnShipWasHit(obj_id self, obj_id attacker, int weaponIndex, boolean isMissile, int missileType, int intSlot, boolean fromPlayerAutoTurret, float hitLocationX_o, float hitLocationY_o, float hitLocationZ_o) throws InterruptedException {
        if(!isValidId(attacker) || !space_utils.isPlayerControlledShip(attacker)){
            return SCRIPT_CONTINUE;
        }
            // get this ship's pilot information
        dictionary pilotData = getPilotRow(getStringObjVar(self, "ace_name"));

        // check if this is a new attacker - if so, add it to the list of attackers.
        Vector attackers = getAttackers();
        boolean newAttacker = false;
        if(attackers != null){
            newAttacker = !attackers.contains(attacker);
        }
        if(attackers == null || newAttacker){
            addAttacker(attacker);
        }
        if(newAttacker){
            handleTauntNewAttacker(attacker, pilotData);
        }
        else{
            handleTauntKnownAttacker(attacker, pilotData);
        }

        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        dictionary pilotData = getPilotRow(getStringObjVar(self, "ace_name"));

        // make the ship immune to damage.
        setObjVar(self, "intNoPlayerDamage", 1);

        obj_id motherShip = getObjIdObjVar(self, "motherShip");
        obj_id attacker = params.getObjId("attacker");

        params.put("destroyedShip", self);
        pilotData.put("attacker", attacker);

        handleTauntVanquished(attacker, pilotData);

        messageTo(self, "handleDespawn", pilotData, 10.0f, false);
        messageTo(motherShip, "removeSupportShip", params, 0.0f, false);

        return SCRIPT_CONTINUE;
    }

    private int getPilotRowIndex(String hero_name){
        String[] pilotNames = dataTableGetStringColumn(HERO_PILOT_DATA, 0);
        for(int i = 0; i < pilotNames.length; i++){
            if(pilotNames[i].equals(hero_name)) return i;
        }
        return -1;
    }

    private dictionary getPilotRow(String hero_name){
        return dataTableGetRow(HERO_PILOT_DATA, getPilotRowIndex(hero_name));
    }

    private Vector getAttackers(){
        return getResizeableObjIdArrayObjVar(getSelf(), "attackers");
    }

    private void addAttacker(obj_id attacker){
        // get all current attackers and add the supplied attacker.
        Vector attackers = getAttackers();
        if(attackers == null) attackers = new Vector();
        attackers.add(attacker);
        setObjVar(getSelf(), "attackers", attackers);
    }

    private String getTauntStringFile(dictionary pilotData){
        return pilotData.getString("taunt_file");
    }

    private boolean pilotWillTaunt(){
        obj_id self = getSelf();
        int TAUNT_DELAY = 3;

        if(getGameTime() < (getIntObjVar(self,  "lastTauntTime") + TAUNT_DELAY)) {
            dictionary pilotData = getPilotRow(getStringObjVar(self, "ace_name"));
            float tauntChance = pilotData.getFloat("taunt_chance");
            return (rand() * 100) < tauntChance;
        }
        return false;

    }

    private void handleTauntNewAttacker(obj_id attacker, dictionary pilotData) throws InterruptedException{
        if(pilotWillTaunt()) {
            String taunt = ENTER_COMBAT_TAUNT + rand(1, TAUNT_QUANTITY);
            doTaunt(attacker, pilotData, taunt);
        }
    }

    private void handleTauntKnownAttacker(obj_id attacker, dictionary pilotData) throws InterruptedException{
        if(pilotWillTaunt()) {
            String taunt = GOT_HIT_TAUNT + rand(1, TAUNT_QUANTITY);
            doTaunt(attacker, pilotData, taunt);
        }
    }

    private void handleTauntVanquished(obj_id attacker, dictionary pilotData) throws InterruptedException{
        String taunt = VANQUISH_TAUNT + rand(1, TAUNT_QUANTITY);
        doTaunt(attacker, pilotData, taunt);
    }

    private void handleTauntDespawn(obj_id attacker, dictionary pilotData) throws InterruptedException{
        String taunt = DESPAWN_TAUNT + rand(1, TAUNT_QUANTITY);
        doTaunt(attacker, pilotData, taunt);
    }

    private void doTaunt(obj_id attacker, dictionary pilotData, String taunt) throws InterruptedException{
        obj_id self = getSelf();

        // get the taunt string_id
        string_id strSpam = new string_id(getTauntStringFile(pilotData), taunt);

        setObjVar(self, "convo.appearance", pilotData.getString("hero_template"));

        obj_id objOwner = getOwner(attacker);

        // taunt the player
        prose_package pp = prose.getPackage(strSpam, 0);
        pp.actor.set(objOwner);
        pp.target.set(objOwner);
        space_utils.tauntShip(attacker, self, pp, true, false, true, true);

        // save the taunt time to make sure we can reference when we last taunted (don't need to taunt toooooo much!)
        utils.setLocalVar(self, "lastTauntTime", getGameTime());
    }

    public int handleDespawn(obj_id self, dictionary pilotData) throws InterruptedException{
        String taunt = DESPAWN_TAUNT + rand(1, TAUNT_QUANTITY);
        doTaunt(pilotData.getObjId("attacker"), pilotData, taunt);
        destroyObjectHyperspace(self);
        return SCRIPT_CONTINUE;
    }
}
