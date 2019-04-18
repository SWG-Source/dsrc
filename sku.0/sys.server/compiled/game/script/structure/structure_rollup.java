package script.structure;

import script.library.*;
import script.location;
import script.obj_id;
import script.prose_package;
import script.string_id;

public class structure_rollup extends script.base_script
{
    public structure_rollup()
    {
    }
    public int OnDoStructureRollup(obj_id self, obj_id player, boolean warnOnly) throws InterruptedException
    {
        String structName = player_structure.getStructureName(self);
        if (structName.length() < 1)
        {
            structName = "Structure";
        }
        location where = getLocation(self);
        if (warnOnly)
        {
            string_id subject = new string_id("player_structure", "structure_purge_warning_subject");
            string_id body = new string_id("player_structure", "structure_purge_warning_body");
            prose_package pp = new prose_package();
            prose.setStringId(pp, body);
            prose.setTO(pp, getPlayerFullName(player));
            String body_oob = chatMakePersistentMessageOutOfBandBody(null, pp);
            String subject_str = "@" + subject.toString();
            body_oob = chatAppendPersistentMessageWaypointData(body_oob, where.area, where.x, where.z, null, structName);
            String[] admins = player_structure.getAdminListNames(self);
            for (String admin : admins) {
                chatSendPersistentMessage("Galactic Housing Authority", admin, subject_str, null, body_oob);
            }
            if (city.isInCity(self))
            {
                int cityId = city.getStructureCityId(self);
                if (cityId != 0)
                {
                    obj_id mayor = cityGetLeader(cityId);
                    if (mayor != obj_id.NULL_ID)
                    {
                        chatSendPersistentMessage("Galactic Housing Authority", mayor, subject_str, null, body_oob);
                    }
                }
            }
            obj_id nameTarget = self;
            if (player_structure.isBuilding(nameTarget))
            {
                obj_id sign = getObjIdObjVar(nameTarget, player_structure.VAR_SIGN_ID);
                if (isIdValid(sign))
                {
                    nameTarget = sign;
                }
            }
            String abandoned = " \\#FF0000\\(Abandoned)";
            setName(nameTarget, structName + abandoned);
            if (nameTarget != self)
            {
                setObjVar(self, player_structure.VAR_SIGN_NAME, structName + abandoned);
            }
        }
        else 
        {
            if (player_structure.isBuilding(self))
            {
                obj_id[] players = player_structure.getPlayersInBuilding(self);
                if (players != null)
                {
                    for (obj_id player1 : players) {
                        expelFromBuilding(player1);
                    }
                }
                String[] cells = getCellNames(self);
                if (cells != null)
                {
                    for (String cell : cells) {
                        obj_id cellid = getCellId(self, cell);
                        obj_id contents[] = getContents(cellid);
                        if (contents != null) {
                            for (obj_id content : contents) {
                                if (hasCondition(content, CONDITION_VENDOR)) {
                                    obj_id owner = getObjIdObjVar(content, "vendor_owner");
                                    if (!isIdValid(owner)) {
                                        owner = getOwner(content);
                                    }
                                    vendor_lib.finalizePackUp(owner, content, player, player_structure.isAbandoned(self));
                                }
                            }
                        }
                    }
                }
            }
            obj_id scd = createObject("object/intangible/house/generic_house_control_device.iff", where);
            setName(scd, structName);
            setOwner(scd, player);
            if (!persistObject(scd))
            {
                LOG("LOG_CHANNEL", "structure_rollup.OnDoStructureRollup persist SCD failed!");
                return SCRIPT_CONTINUE;
            }
            attachScript(scd, "structure.house_control_device");
            if (hasObjVar(self, player_structure.VAR_WAYPOINT_STRUCTURE))
            {
                obj_id waypoint = getObjIdObjVar(self, player_structure.VAR_WAYPOINT_STRUCTURE);
                if (isIdValid(waypoint))
                {
                    destroyWaypointInDatapad(waypoint, player);
                    removeObjVar(self, player_structure.VAR_WAYPOINT_STRUCTURE);
                }
            }
            player_structure.destroyStructureSign(self);
            if (player_structure.isResidence(self, player))
            {
                setHouseId(player, obj_id.NULL_ID);
                removeObjVar(self, player_structure.VAR_RESIDENCE_BUILDING);
                city.removeCitizen(player, self);
            }
            city.removeStructureFromCity(self);
            String template = getTemplateName(self);
            player_structure.setDeedTemplate(scd, template);
            if (isIdValid(player) && exists(player))
            {
                obj_id lotOverlimitStructure = getObjIdObjVar(player, "lotOverlimit.structure_id");
                if (isIdValid(lotOverlimitStructure) && (lotOverlimitStructure == self))
                {
                    setObjVar(player, "lotOverlimit.structure_location", "Datapad");
                }
            }
            putIn(self, scd);
            if (hasObjVar(self, "purge_process.rollup_on_load"))
            {
                removeObjVar(self, "purge_process.rollup_on_load");
            }
            final int maxDepth = player_structure.isFactory(self) ? 101 : 1;
            moveToOfflinePlayerDatapadAndUnload(scd, player, maxDepth + 1);
            fixLoadWith(self, player, maxDepth);
            trace.log("housepackup", getPlayerFullName(player) + "(" + player + ") had their house rolled up by the purge process (" + self + ", loc " + where.toString() + ") into structure control device " + scd, player, trace.TL_CS_LOG);
        }
        return SCRIPT_CONTINUE;
    }
}
