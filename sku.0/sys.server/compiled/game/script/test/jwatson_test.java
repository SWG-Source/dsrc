package script.test;

import script.combat_engine.hit_result;
import script.*;
import script.library.*;

public class jwatson_test extends script.base_script
{
    public jwatson_test()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!isGod(self) || getGodLevel(self) < 50 || !isPlayer(self)) {
            detachScript(self, "test.jwatson_test");
        }
        return SCRIPT_CONTINUE;
    }
    public void colorize(obj_id player, obj_id target, String customizationVar) throws InterruptedException
    {
        int pId = createSUIPage("Script.ColorPicker", player, player, "ColorizeCallback");
        setSUIProperty(pId, "ColorPicker", "TargetNetworkId", target.toString());
        setSUIProperty(pId, "ColorPicker", "TargetVariable", customizationVar);
        setSUIProperty(pId, "ColorPicker", "TargetRange", "500");
        subscribeToSUIProperty(pId, "ColorPicker", "SelectedIndex");
        setSUIAssociatedObject(pId, target);
        showSUIPage(pId);
    }
    public void maxStats(obj_id objPlayer) throws InterruptedException
    {
        addAttribModifier(objPlayer, HEALTH, 2000, 0, 0, MOD_POOL);
        addAttribModifier(objPlayer, ACTION, 2000, 0, 0, MOD_POOL);
        addAttribModifier(objPlayer, MIND, 2000, 0, 0, MOD_POOL);
    }
    public boolean sendStartingLocations(obj_id player) throws InterruptedException
    {
        newbieTutorialSendStartingLocationsToPlayer(player, null);
        return true;
    }
    public int newbieRequestStartingLocations(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        sendStartingLocations(self);
        return SCRIPT_CONTINUE;
    }
    public int newbieSelectStartingLocation(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        String name = params;
        boolean available = isStartingLocationAvailable(name);
        newbieTutorialSendStartingLocationSelectionResult(self, name, available);
        if (available)
        {
            location loc = getStartingLocationInfo(name);
            if (loc != null)
            {
                if (loc.cell != null && loc.cell != obj_id.NULL_ID)
                {
                    warpPlayer(self, loc.area, 0.0f, 0.0f, 0.0f, loc.cell, loc.x, loc.y, loc.z);
                }
                else 
                {
                    warpPlayer(self, loc.area, loc.x, loc.y, loc.z, null, 0.0f, 0.0f, 0.0f);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnApplyPowerup(obj_id self, obj_id playerId, obj_id targetId) throws InterruptedException
    {
        chat.chat(playerId, "OnApplyPowerup " + self.toString() + " -> " + targetId.toString());
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id playerId, String[] names, String[] attribs) throws InterruptedException
    {
        names[0] = "jwatson_test";
        attribs[0] = "What's up my homies?\nYeah";
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        java.util.StringTokenizer tok = new java.util.StringTokenizer(text);
        if (tok.hasMoreTokens())
        {
            String command = tok.nextToken();
            debugConsoleMsg(self, "command is: " + command);
            switch (command) {
                case "jw_colorize":
                    if (tok.countTokens() < 2) {
                        debugSpeakMsg(self, "Not enough arguments: colorize <obj_id> <customization var>");
                    } else {
                        tok.nextToken();
                        String idString = tok.nextToken();
                        String customizationVar = tok.nextToken();
                        obj_id id = obj_id.getObjId(Long.parseLong(idString));
                        colorize(self, id, customizationVar);
                    }
                    break;
                case "jw_scale":
                    if (tok.countTokens() < 1) {
                        debugSpeakMsg(self, "Not enough arguments: scale <factor> [obj_id]");
                    } else {
                        float scaleFactor = Float.parseFloat(tok.nextToken());
                        obj_id id = null;
                        if (tok.countTokens() > 0) {
                            String idString = tok.nextToken();
                            id = obj_id.getObjId(Long.parseLong(idString));
                        } else {
                            id = self;
                        }
                        setScale(id, scaleFactor);
                    }
                    break;
                case "jw_systemMessage":
                    debugSpeakMsg(self, "jwatson_test jw_systemMessage: " + text);
                    if (tok.countTokens() < 2) {
                        debugSpeakMsg(self, "Not enough arguments: jw_systemMessage <id> <msg>");
                    } else {
                        obj_id id = null;
                        String idString = tok.nextToken();
                        if (!idString.equals("0")) {
                            id = obj_id.getObjId(Long.parseLong(idString));
                        } else {
                            id = self;
                        }
                        sendSystemMessageTestingOnly(id, text);
                    }
                    break;
                case "jw_systemMessagePlanet":
                    sendSystemMessagePlanetTestingOnly(text);
                    break;
                case "jw_systemMessageGalaxy": {
                    LOG("jw", "jw_systemMessageGalaxy");
                    prose_package pp = new prose_package();
                    LOG("jw", "jw_systemMessageGalaxy prose created");
                    pp.stringId = new string_id("ui", "test_pp");
                    LOG("jw", "jw_systemMessageGalaxy stringId set");
                    pp.actor.set(self);
                    LOG("jw", "jw_systemMessageGalaxy actor set");
                    pp.target.set(getLookAtTarget(self));
                    LOG("jw", "jw_systemMessageGalaxy target set");
                    pp.other.set("other_here");
                    LOG("jw", "jw_systemMessageGalaxy other set");
                    pp.digitInteger = 666;
                    pp.digitFloat = 0.333f;
                    LOG("jw", "jw_systemMessageGalaxy sending");
                    String oob = packOutOfBandProsePackage(null, pp);
                    sendSystemMessageGalaxyOob(oob);
                    LOG("jw", "jw_systemMessageGalaxy  oob size=" + oob.length());
                    sendSystemMessageGalaxyProse(pp);
                    break;
                }
                case "jw_maxStats":
                    maxStats(self);
                    break;
                case "jw_pm1": {
                    prose_package bodyProse = new prose_package();
                    prose_package subjectProse = new prose_package();
                    bodyProse.stringId = new string_id("pm", "body_id");
                    String oob = chatMakePersistentMessageOutOfBandBody(null, bodyProse);
                    String subject_str = "@" + (new string_id("pm", "subject_id")).toString();
                    String sender_str = "@" + (new string_id("pm", "sender_id")).toString();
                    LOG("jw", "jw_pm1 oob size = " + oob.length());
                    chatSendPersistentMessage(self, subject_str, "Here is the body", oob);
                    break;
                }
                case "jw_pm2":
                    chatSendPersistentMessage(self, "This is a message (2)", "Here is the body (2)", null);
                    break;
                case "jw_pm3": {
                    String oob = chatAppendPersistentMessageWaypoint(null, self);
                    LOG("jw", "jw_pm3 oob size = " + oob.length());
                    chatSendPersistentMessage(self, "This is a message (waypoint)", "Here is the body", oob);
                    oob = chatAppendPersistentMessageWaypointData(null, null, -666.0f, 999.0f, null, "dummytext");
                    chatSendPersistentMessage(self, "This is a message (object)", "Here is the body", oob);
                    break;
                }
                case "jw_pm4":
                    String from = "default_from";
                    String subj = "default_subj";
                    String body = "default_body";
                    if (tok.hasMoreTokens()) {
                        from = tok.nextToken();
                        if (tok.hasMoreTokens()) {
                            subj = tok.nextToken();
                            if (tok.hasMoreTokens()) {
                                body = tok.nextToken();
                            }
                        }
                    }
                    chatSendPersistentMessage(from, getChatName(self), subj, body, null);
                    break;
                case "jw_setMaster": {
                    obj_id target = getLookAtTarget(self);
                    if (target != null) {
                        setMaster(target, self);
                    }
                    break;
                }
                case "jw_joinMe": {
                    obj_id target = getLookAtTarget(self);
                    if (target != null) {
                        queueCommand(target, (-1449236473), null, "", COMMAND_PRIORITY_DEFAULT);
                    }
                    break;
                }
                case "jw_inviteMe": {
                    obj_id target = getLookAtTarget(self);
                    if (target != null) {
                        queueCommand(target, (-2007999144), self, "", COMMAND_PRIORITY_DEFAULT);
                    }
                    break;
                }
                case "jw_speak": {
                    obj_id target = getLookAtTarget(self);
                    if (target != null) {
                        StringBuilder output = new StringBuilder();
                        while (tok.hasMoreTokens()) output.append(tok.nextToken());
                        queueCommand(target, (-296481545), null, output.toString(), COMMAND_PRIORITY_DEFAULT);
                    }
                    break;
                }
                case "jw_speakProse": {
                    obj_id target = getLookAtTarget(self);
                    if (target != null) {
                        prose_package pp = new prose_package();
                        pp.stringId = new string_id("ui", "test_pp");
                        pp.actor.set(self);
                        pp.target.set(getLookAtTarget(self));
                        pp.other.set("other_here");
                        pp.digitInteger = 666;
                        pp.digitFloat = 0.333f;
                        chat.publicChat(target, null, null, null, pp);
                        chat.chat(target, chat.CHAT_PARROT, chat.MOOD_PLAYFUL, new string_id("ui", "test_pp_2"));
                        chat.chat(target, "This is the third message");
                    }
                    break;
                }
                case "jw_testSui": {
                    obj_id target = getLookAtTarget(self);
                    if (target != null) {
                        int pid = createSUIPage(sui.SUI_MSGBOX, self, target, "");
                        setSUIAssociatedObject(pid, target);
                        setSUIProperty(pid, "bg.caption.lbltitle", "Text", "MY TITLE");
                        setSUIProperty(pid, "%text%", "Text", "WTF2");
                        showSUIPage(pid);
                    }
                    break;
                }
                case "jw_money":
                    StringBuilder output = new StringBuilder();
                    if (tok.hasMoreTokens()) {
                        String amountStr = tok.nextToken();
                        int amount = Integer.parseInt(amountStr);
                        if (amount > 0) {
                            money.bankTo(money.ACCT_CHARACTER_CREATION, self, amount);
                        } else {
                            money.bankTo(self, money.ACCT_CHARACTER_CREATION, -amount);
                        }
                    }
                    break;
                case "jw_kill": {
                    obj_id target = getLookAtTarget(self);
                    if (target != null) {
                        hit_result cbtHitData = new hit_result();
                        cbtHitData.success = true;
                        cbtHitData.baseRoll = 1000;
                        cbtHitData.finalRoll = 100000;
                        cbtHitData.canSee = true;
                        cbtHitData.hitLocation = 0;
                        cbtHitData.damage = 100000000;
                        doDamage(self, target, getCurrentWeapon(self), cbtHitData);
                        pclib.coupDeGrace(self, target, false);
                    }
                    break;
                }
                case "jw_ownVendor": {
                    obj_id target = getLookAtTarget(self);
                    if (target != null) {
                        createVendorMarket(self, target, 0);
                    }
                    break;
                }
                case "jw_putMeInCell":
                    if (tok.countTokens() < 3) {
                        debugSpeakMsg(self, "Not enough arguments: jw_putMeInCell <planet> <obj_id> <cell name>");
                    } else {
                        String arg1 = tok.nextToken();
                        String arg2 = tok.nextToken();
                        String arg3 = tok.nextToken();
                        String planet = arg1;
                        obj_id building = obj_id.getObjId(Long.valueOf(arg2));
                        obj_id cellId = getCellId(building, arg3);
                        debugSpeakMsg(self, "Warping to " + building + ", " + cellId);
                        warpPlayer(self, planet, 0, 0, 0, cellId, 0, 0, 0);
                    }
                    break;
                case "jw_planetmap":
                    addPlanetaryMapLocation(obj_id.getObjId(1), "city 1", -4000, -3000, "city", "", MLT_STATIC, 0);
                    addPlanetaryMapLocation(obj_id.getObjId(2), "city 2", -2500, 3500, "city", "", MLT_STATIC, 0);
                    addPlanetaryMapLocation(obj_id.getObjId(3), "city 3", 3000, 7000, "city", "", MLT_STATIC, 0);
                    addPlanetaryMapLocation(obj_id.getObjId(4), "city 4", 1000, -6000, "city", "", MLT_STATIC, 0);
                    addPlanetaryMapLocation(obj_id.getObjId(5), "vendor 1", 100, -4300, "cantina", "", MLT_PERSIST, 0);
                    addPlanetaryMapLocation(obj_id.getObjId(6), "vendor 2", 2100, 6300, "cantina", "", MLT_PERSIST, 0);
                    addPlanetaryMapLocation(obj_id.getObjId(7), "vendor 3", -3100, -6300, "cantina", "", MLT_PERSIST, 0);
                    addPlanetaryMapLocation(obj_id.getObjId(8), "vendor 4", -3400, -6500, "cantina", "hospital", MLT_DYNAMIC, MLF_INACTIVE);
                    addPlanetaryMapLocation(obj_id.getObjId(9), "vendor 5", 4400, -2600, "cantina", "hospital", MLT_DYNAMIC, MLF_ACTIVE);
                    break;
                case "jw_mapget": {
                    String arg1 = tok.nextToken();
                    obj_id id = obj_id.getObjId(Long.valueOf(arg1));
                    map_location loc = getPlanetaryMapLocation(id);
                    debugSpeakMsg(self, "got [" + loc + "]");
                    break;
                }
                case "jw_mapRegisterSelf": {
                    String arg1 = tok.nextToken();
                    String arg2 = tok.nextToken();
                    String arg3 = null;
                    if (tok.hasMoreTokens()) {
                        arg3 = tok.nextToken();
                    }
                    addPlanetaryMapLocation(self, arg1, -4000, -3000, arg2, arg3 != null ? arg3 : "", MLT_DYNAMIC, 0);
                    break;
                }
                case "jw_vset": {
                    int index = Integer.parseInt(tok.nextToken());
                    float value = Float.parseFloat(tok.nextToken());
                    int ivalue = vehicle.setValue(getLookAtTarget(self), value, index);
                    debugSpeakMsg(self, "set value to " + ivalue);
                    break;
                }
                case "jw_vget": {
                    int index = Integer.parseInt(tok.nextToken());
                    float value = vehicle.getValue(getLookAtTarget(self), index);
                    debugSpeakMsg(self, "value is " + value);
                    break;
                }
                case "jw_dirtyAttributes": {
                    String idString = tok.nextToken();
                    obj_id id = obj_id.getObjId(Long.parseLong(idString));
                    sendDirtyAttributesNotification(id);
                    debugSpeakMsg(self, "dirty attrs");
                    break;
                }
                case "jw_dirtyMenu": {
                    String idString = tok.nextToken();
                    obj_id id = obj_id.getObjId(Long.parseLong(idString));
                    sendDirtyObjectMenuNotification(id);
                    debugSpeakMsg(self, "dirty attrs");
                    break;
                }
                case "jw_suiTest":
                    break;
                case "jw_shipInstall": {
                    String idString = tok.nextToken();
                    int index = Integer.parseInt(tok.nextToken());
                    obj_id componentId = obj_id.getObjId(Long.parseLong(idString));
                    boolean result = shipInstallComponent(self, getLookAtTarget(self), index, componentId);
                    debugSpeakMsg(self, "installed result:" + result);
                    break;
                }
                case "jw_shipUninstall": {
                    int index = Integer.parseInt(tok.nextToken());
                    obj_id componentId = shipUninstallComponent(self, getLookAtTarget(self), index, getObjectInSlot(self, "inventory"));
                    debugSpeakMsg(self, "uninstalled component:" + componentId);
                    break;
                }
                case "jw_shipPurge": {
                    int index = Integer.parseInt(tok.nextToken());
                    obj_id componentId = shipUninstallComponent(null, getLookAtTarget(self), index, null);
                    debugSpeakMsg(self, "uninstalled component:" + componentId);
                    break;
                }
                case "jw_shipGetSlots":
                    debugSpeakMsg(self, "... getting slots ...");
                    obj_id shipId = getLookAtTarget(self);
                    int[] shipChassisSlots = getShipChassisSlots(shipId);
                    if (shipChassisSlots != null) {
                        for (int shipChassisSlot : shipChassisSlots) {
                            boolean installed = isShipSlotInstalled(shipId, shipChassisSlot);
                            debugSpeakMsg(self, "slot " + shipChassisSlot + ", installed:" + installed);
                        }
                    }
                    break;
                case "jw_shipSetEnergyRequirement": {
                    int index = Integer.parseInt(tok.nextToken());
                    float requirement = Float.parseFloat(tok.nextToken());
                    setShipComponentEnergyMaintenanceRequirement(getLookAtTarget(self), index, requirement);
                    break;
                }
                case "jw_shipSetEfficiencyGeneral": {
                    int index = Integer.parseInt(tok.nextToken());
                    float requirement = Float.parseFloat(tok.nextToken());
                    setShipComponentEfficiencyGeneral(getLookAtTarget(self), index, requirement);
                    break;
                }
                case "jw_shipSetEfficiencyEnergy": {
                    int index = Integer.parseInt(tok.nextToken());
                    float requirement = Float.parseFloat(tok.nextToken());
                    setShipComponentEfficiencyEnergy(getLookAtTarget(self), index, requirement);
                    break;
                }
                case "jw_shipSetArmor": {
                    int index = Integer.parseInt(tok.nextToken());
                    float cur = Float.parseFloat(tok.nextToken());
                    float max = Float.parseFloat(tok.nextToken());
                    setShipComponentArmorHitpointsMaximum(getLookAtTarget(self), index, max);
                    setShipComponentArmorHitpointsCurrent(getLookAtTarget(self), index, cur);
                    break;
                }
                case "jw_shipSetHp": {
                    int index = Integer.parseInt(tok.nextToken());
                    float cur = Float.parseFloat(tok.nextToken());
                    float max = Float.parseFloat(tok.nextToken());
                    setShipComponentHitpointsMaximum(getLookAtTarget(self), index, max);
                    setShipComponentHitpointsCurrent(getLookAtTarget(self), index, cur);
                    break;
                }
                case "jw_shipSetFlags": {
                    int index = Integer.parseInt(tok.nextToken());
                    int flags = Integer.parseInt(tok.nextToken());
                    setShipComponentFlags(getLookAtTarget(self), index, flags);
                    break;
                }
                case "jw_shipSetShipHp": {
                    float cur = Float.parseFloat(tok.nextToken());
                    float max = Float.parseFloat(tok.nextToken());
                    setShipMaximumChassisHitPoints(getLookAtTarget(self), max);
                    setShipCurrentChassisHitPoints(getLookAtTarget(self), cur);
                    break;
                }
                case "jw_shipSetReactorGeneration": {
                    float val = Float.parseFloat(tok.nextToken());
                    setShipReactorEnergyGenerationRate(getLookAtTarget(self), val);
                    break;
                }
                case "jw_shipSetShieldHpFrontCurrent": {
                    float val = Float.parseFloat(tok.nextToken());
                    setShipShieldHitpointsFrontCurrent(getLookAtTarget(self), val);
                    break;
                }
                case "jw_shipSetShieldHpFrontMax": {
                    float val = Float.parseFloat(tok.nextToken());
                    setShipShieldHitpointsFrontMaximum(getLookAtTarget(self), val);
                    break;
                }
                case "jw_shipSetShieldHpBackCurrent": {
                    float val = Float.parseFloat(tok.nextToken());
                    setShipShieldHitpointsBackCurrent(getLookAtTarget(self), val);
                    break;
                }
                case "jw_shipSetShieldHpBackMax": {
                    float val = Float.parseFloat(tok.nextToken());
                    setShipShieldHitpointsBackMaximum(getLookAtTarget(self), val);
                    break;
                }
                case "jw_shipSetShieldRechargeRate": {
                    float val = Float.parseFloat(tok.nextToken());
                    setShipShieldRechargeRate(getLookAtTarget(self), val);
                    break;
                }
                case "jw_terminalSet": {
                    debugSpeakMsg(self, "terminal setting");
                    obj_id id = obj_id.getObjId(Long.parseLong(tok.nextToken()));
                    setObjVar(id, "space.destination", getWorldLocation(id));
                    break;
                }
                case "jw_weaponComponentSetup": {
                    debugSpeakMsg(self, "component setup");
                    obj_id id = obj_id.getObjId(Long.parseLong(tok.nextToken()));
                    setObjVar(id, "ship_comp.weapon.refire_rate", 20.0f);
                    setObjVar(id, "ship_comp.weapon.projectile_speed", 150.0f);
                    setObjVar(id, "ship_comp.weapon.energy_per_shot", 50.0f);
                    setObjVar(id, "ship_comp.weapon.damage_maximum", 10.0f);
                    break;
                }
                case "jw_shipComponentName": {
                    int index = Integer.parseInt(tok.nextToken());
                    String name = tok.nextToken();
                    setShipComponentName(getLookAtTarget(self), index, name);
                    break;
                }
                case "jw_shipDestroy": {
                    float val = Float.parseFloat(tok.nextToken());
                    handleShipDestruction(getLookAtTarget(self), val);
                    break;
                }
                case "jw_shipDestroyComponent": {
                    int index = Integer.parseInt(tok.nextToken());
                    float val = Float.parseFloat(tok.nextToken());
                    handleShipComponentDestruction(getLookAtTarget(self), index, val);
                    break;
                }
                case "jw_shipHitMe": {
                    obj_id objTarget = getLookAtTarget(self);
                    obj_id objShip = getPilotedShip(self);
                    sendSystemMessageTestingOnly(self, "objTarget of " + objTarget + " is attacking " + objShip);
                    ship_ai.spaceAttack(objTarget, objShip);
                    break;
                }
                case "jw_shipSetWeaponEfficiencyRefireRate": {
                    int index = Integer.parseInt(tok.nextToken());
                    float eff = Float.parseFloat(tok.nextToken());
                    setShipWeaponEfficiencyRefireRate(getLookAtTarget(self), index, eff);
                    break;
                }
                case "jw_shipSetWeaponDamage": {
                    int index = Integer.parseInt(tok.nextToken());
                    sendSystemMessageTestingOnly(self, "index=" + index);
                    setShipWeaponDamageMaximum(getLookAtTarget(self), ship_chassis_slot_type.SCST_weapon_0 + index, 500.0f);
                    break;
                }
                case "jw_leet": {
                    obj_id objTarget = getLookAtTarget(self);
                    if (objTarget == null) {
                        objTarget = getPilotedShip(self);
                    }
                    for (int i = 0; i < ship_chassis_slot_type.SCST_num_types; ++i) {
                        if (isShipSlotInstalled(objTarget, i)) {
                            setShipComponentEfficiencyEnergy(objTarget, i, 10.0f);
                            setShipComponentEfficiencyGeneral(objTarget, i, 2.0f);
                        }
                    }
                    for (int i = ship_chassis_slot_type.SCST_weapon_first; i < ship_chassis_slot_type.SCST_weapon_last; ++i) {
                        if (isShipSlotInstalled(objTarget, i)) {
                            setShipWeaponEfficiencyRefireRate(objTarget, i, 10.0f);
                            setShipComponentEfficiencyEnergy(objTarget, i, 10.0f);
                            setShipComponentEfficiencyGeneral(objTarget, i, 10.0f);
                            setShipWeaponAmmoCurrent(objTarget, i, getShipWeaponAmmoMaximum(objTarget, i));
                        }
                    }
                    break;
                }
                case "jw_fullAmmo": {
                    obj_id objTarget = getLookAtTarget(self);
                    if (objTarget == null) {
                        objTarget = getPilotedShip(self);
                    }
                    for (int i = ship_chassis_slot_type.SCST_weapon_first; i < ship_chassis_slot_type.SCST_weapon_last; ++i) {
                        if (isShipSlotInstalled(objTarget, i)) {
                            sendSystemMessageTestingOnly(self, "up ammo");
                            setShipWeaponAmmoCurrent(objTarget, i, getShipWeaponAmmoMaximum(objTarget, i));
                        }
                    }
                    break;
                }
                case "jw_clientEffect":
                    String cef = "clienteffect/space_scram_spark.cef";
                    if (tok.hasMoreTokens()) {
                        cef = tok.nextToken();
                    }
                    transform t = new transform();
                    t = t.move_p(new vector(10.0f, 0.0f, 0.0f));
                    playClientEffectObj(self, cef, getLookAtTarget(self), null, t);
                    break;
                case "jw_powerDebug": {
                    obj_id[] objPcds = space_transition.findShipControlDevicesForPlayer(self);
                    obj_id objShip = space_transition.getShipFromShipControlDevice(objPcds[0]);
                    objShip = space_transition.getContainingShip(self);
                    setShipReactorEnergyGenerationRate(objShip, 500);
                    setShipComponentEnergyMaintenanceRequirement(objShip, space_crafting.SHIELD_GENERATOR, 200);
                    setShipComponentEnergyMaintenanceRequirement(objShip, space_crafting.ENGINE, 100);
                    setShipComponentEnergyMaintenanceRequirement(objShip, space_crafting.WEAPON_0, 100);
                    setShipComponentEnergyMaintenanceRequirement(objShip, space_crafting.WEAPON_1, 100);
                    setShipComponentEnergyMaintenanceRequirement(objShip, space_crafting.CAPACITOR, 100);
                    sendSystemMessageTestingOnly(self, "whacked ");
                    break;
                }
                case "jw_sprTest": {
                    obj_id[] objPcds = space_transition.findShipControlDevicesForPlayer(self);
                    obj_id objShip = space_transition.getShipFromShipControlDevice(objPcds[0]);
                    objShip = space_transition.getContainingShip(self);
                    setShipEngineSpeedRotationFactorMaximum(objShip, 3.88f);
                    setShipEngineSpeedRotationFactorMinimum(objShip, 6.33f);
                    setShipEngineSpeedRotationFactorOptimal(objShip, 0.75f);
                    break;
                }
                case "jw_chassisMod": {
                    obj_id[] objPcds = space_transition.findShipControlDevicesForPlayer(self);
                    obj_id objShip = space_transition.getShipFromShipControlDevice(objPcds[0]);
                    objShip = space_transition.getContainingShip(self);
                    float val = Float.parseFloat(tok.nextToken());
                    setShipChassisSpeedMaximumModifier(objShip, val);
                    break;
                }
                case "jw_maDynVel": {
                    obj_id objTarget = getLookAtTarget(self);
                    float x = Float.parseFloat(tok.nextToken());
                    float y = Float.parseFloat(tok.nextToken());
                    float z = Float.parseFloat(tok.nextToken());
                    setDynamicMiningAsteroidVelocity(objTarget, new vector(x, y, z));
                    break;
                }
                case "jw_maSpawnStatic":
                    break;
                case "jw_maSpawnDynamic": {
                    location selfLocation = getLocation(self);
                    obj_id spawnDynamicAsteroid = createObject("object/ship/asteroid/mining_asteroid_dynamic_default.iff", selfLocation);
                    setDynamicMiningAsteroidVelocity(spawnDynamicAsteroid, new vector(0.0f, 40.0f, 0.0f));
                    break;
                }
                case "jw_dt": {
                    obj_id ticket = space_dungeon.createTicket(self, "tatooine", "tatooine", "avatar_platform");
                    break;
                }
                case "jw_dt_curr": {
                    String planet = getCurrentSceneName();
                    obj_id ticket = space_dungeon.createTicket(self, planet, planet, "avatar_platform");
                    break;
                }
                case "jw_spaceTicket": {
                    obj_id objTarget = getLookAtTarget(self);
                    space_dungeon.selectDungeonTicket(objTarget, self);
                    break;
                }
                case "jw_spaceEject":
                    sendSystemMessageTestingOnly(self, "now ejecting... ");
                    space_dungeon.ejectPlayerFromDungeon(self);
                    break;
                case "jw_avatarPlatform": {
                    location selfLocation = getLocation(self);
                    selfLocation.x += 400.0f;
                    obj_id platform = createObject("object/ship/spacestation_avatar_platform.iff", selfLocation);
                    break;
                }
                case "jw_spaceDungeonInit": {
                    long id = Long.parseLong(tok.nextToken());
                    obj_id dungeon = obj_id.getObjId(id);
                    messageTo(dungeon, "msgManualDungeonReset", new dictionary(), 0.0f, false);
                    break;
                }
                case "jw_spaceLaunch":
                    sendSystemMessageTestingOnly(self, "now launching... ");
                    space_dungeon.launchPlayerFromDungeon(self);
                    break;
                case "jw_spaceDupe": {
                    obj_id[] scds = space_transition.findShipControlDevicesForPlayer(self);
                    obj_id ship = space_transition.getShipFromShipControlDevice(scds[0]);
                    if (null == ship) {
                        ship = getPilotedShip(self);
                    }
                    sendSystemMessageTestingOnly(self, "scds.length=" + scds.length + ", ship=" + ship);
                    if (null == ship) {
                    } else {
                        location selfLocation = getLocation(self);
                        selfLocation.y += 2.0f;
                        obj_id copy = createObject(ship, selfLocation);
                        for (int chassisSlot = 0; chassisSlot < ship_chassis_slot_type.SCST_weapon_10; ++chassisSlot) {
                            int crc = getShipComponentCrc(ship, chassisSlot);
                            if (0 != crc) {
                                shipPseudoInstallComponent(copy, chassisSlot, crc);
                                setShipComponentArmorHitpointsMaximum(copy, chassisSlot, getShipComponentArmorHitpointsMaximum(ship, chassisSlot));
                                setShipComponentArmorHitpointsCurrent(copy, chassisSlot, getShipComponentArmorHitpointsCurrent(ship, chassisSlot));
                                setShipComponentHitpointsMaximum(copy, chassisSlot, getShipComponentHitpointsMaximum(ship, chassisSlot));
                                setShipComponentHitpointsCurrent(copy, chassisSlot, getShipComponentHitpointsCurrent(ship, chassisSlot));
                                setShipComponentFlags(copy, chassisSlot, getShipComponentFlags(ship, chassisSlot) & ~ship_component_flags.SCF_active);
                            }
                        }
                        space_utils.setComponentDisabled(copy, ship_chassis_slot_type.SCST_engine, true);
                        custom_var[] cvars = getAllCustomVars(ship);
                        if (null != cvars) {
                            for (custom_var cvar : cvars) {
                                if (cvar.isRangedInt()) {
                                    ranged_int_custom_var ricvar = (ranged_int_custom_var) cvar;
                                    String varName = cvar.getVarName();
                                    setRangedIntCustomVarValue(copy, varName, ricvar.getValue());
                                }
                            }
                        }
                    }
                    break;
                }
                case "jw_ticketCollector": {
                    location selfLocation = getLocation(self);
                    obj_id collector = createObject("object/tangible/travel/ticket_collector/ticket_collector.iff", selfLocation);
                    attachScript(collector, "item.travel_ticket.travel_space_dungeon");
                    setObjVar(collector, "space_dungeon.ticket.dungeon", "avatar_platform");
                    setObjVar(collector, "space_dungeon.ticket.point", getCurrentSceneName());
                    break;
                }
                case "jw_getDungeonRegistered": {
                    long id = Long.parseLong(tok.nextToken());
                    obj_id dungeon = obj_id.getObjId(id);
                    if (!isValidId(dungeon)) {
                        sendSystemMessageTestingOnly(self, "NO SUCH OBJECT: [" + dungeon + "]");
                        return SCRIPT_CONTINUE;
                    }
                    obj_id[] registered = space_dungeon.getRegisteredObjects(dungeon);
                    if (null == registered) {
                        sendSystemMessageTestingOnly(self, "REGISTERED NULL!");
                    } else {
                        sendSystemMessageTestingOnly(self, "REGISTERED " + registered.length);
                        for (obj_id rid : registered) {
                            if (null != rid) {
                                sendSystemMessageTestingOnly(self, "   ... " + rid);
                            } else {
                                sendSystemMessageTestingOnly(self, "   ... NULL!");
                            }
                        }
                    }
                    break;
                }
                case "jw_getCollisionRadius": {
                    long id = Long.parseLong(tok.nextToken());
                    obj_id obj = obj_id.getObjId(id);
                    float f = getObjectCollisionRadius(obj);
                    sendSystemMessageTestingOnly(self, "radius=" + f);
                    break;
                }
                case "jw_dungeonLandTicketless": {
                    obj_id npc = getLookAtTarget(self);
                    String planet = getCurrentSceneName();
                    space_dungeon.sendGroupToDungeonWithoutTicket(self, "avatar_platform", planet, planet, "quest_type", npc);
                    break;
                }
                case "jw_shipCargoDump": {
                    obj_id ship = getPilotedShip(self);
                    obj_id[] resources = getShipCargoHoldContentsResourceTypes(ship);
                    for (obj_id resource : resources) {
                        setShipCargoHoldContent(ship, resource, 0);
                    }
                    break;
                }
                case "jw_spaceMiningSale": {
                    obj_id ship = getPilotedShip(self);
                    openSpaceMiningUi(self, self, "tatooine");
                    break;
                }
                case "jw_shipTestCargo": {
                    obj_id ship = getPilotedShip(self);
                    setShipCargoHoldContentsMaximum(ship, 100);
                    setShipCargoHoldContent(ship, "space_gem_diamond", 44);
                    setShipCargoHoldContent(ship, "space_metal_carbonaceous", 22);
                    int cd = getShipCargoHoldContent(ship, "space_gem_diamond");
                    int cc = getShipCargoHoldContent(ship, "space_metal_carbonaceous");
                    sendSystemMessageTestingOnly(self, "1) cargo " + getShipCargoHoldContentsCurrent(ship) + "/" + getShipCargoHoldContentsMaximum(ship) + ", contains=" + cd + "," + cc);
                    modifyShipCargoHoldContent(ship, "space_gem_diamond", 4);
                    modifyShipCargoHoldContent(ship, "space_metal_carbonaceous", 2);
                    cd = getShipCargoHoldContent(ship, "space_gem_diamond");
                    cc = getShipCargoHoldContent(ship, "space_metal_carbonaceous");
                    sendSystemMessageTestingOnly(self, "2) cargo " + getShipCargoHoldContentsCurrent(ship) + "/" + getShipCargoHoldContentsMaximum(ship) + ", contains=" + cd + "," + cc);
                    modifyShipCargoHoldContent(ship, "space_gem_diamond", -8);
                    modifyShipCargoHoldContent(ship, "space_metal_carbonaceous", -4);
                    cd = getShipCargoHoldContent(ship, "space_gem_diamond");
                    cc = getShipCargoHoldContent(ship, "space_metal_carbonaceous");
                    sendSystemMessageTestingOnly(self, "3) cargo " + getShipCargoHoldContentsCurrent(ship) + "/" + getShipCargoHoldContentsMaximum(ship) + ", contains=" + cd + "," + cc);
                    break;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int TestSUICallback(obj_id self, dictionary params) throws InterruptedException
    {
        debugServerConsoleMsg(self, "callback started");
        obj_id player = params.getObjId("player");
        int pageId = -5;
        pageId = params.getInt("pageId");
        debugSpeakMsg(player, Integer.toString(pageId));
        String[] props = params.getStringArray("propertyStrings");
        for (String prop : props) {
            debugSpeakMsg(player, prop);
        }
        return SCRIPT_CONTINUE;
    }
    public int ColorizeCallback(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnSpaceMiningSellResource(obj_id self, obj_id player, obj_id ship, obj_id station, obj_id resourceId, int amount) throws InterruptedException
    {
        int amountDeducted = -modifyShipCargoHoldContent(ship, resourceId, -amount);
        sendSystemMessageTestingOnly(player, "Sold Resources: " + amountDeducted + "units");
        return SCRIPT_CONTINUE;
    }
}
