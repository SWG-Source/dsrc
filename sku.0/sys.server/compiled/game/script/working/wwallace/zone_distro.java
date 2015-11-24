package script.working.wwallace;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.datatable;
import script.library.objvar_mangle;

public class zone_distro extends script.base_script
{
    public zone_distro()
    {
    }
    public int OnSpeaking(obj_id self, String strText) throws InterruptedException
    {
        String[] strCommands = split(strText, ' ');
        if (strCommands[0].equals("zoneDistro"))
        {
            String strDataTable = "datatables/space_zones/zone_distro.tab";
            String[] strHeaderTypes = 
            {
                "s",
                "s",
                "s",
                "s",
                "s",
                "s"
            };
            String[] strHeaders = 
            {
                "strMob",
                "strZone",
                "strSquad",
                "strTreasure",
                "strShipType",
                "strTier"
            };
            boolean boolTest = datatable.createDataTable(strDataTable, strHeaders, strHeaderTypes);
            if (!boolTest)
            {
                sendSystemMessageTestingOnly(self, "No datatable made");
                return SCRIPT_CONTINUE;
            }
            String[] strZones = new String[1];
            strZones = new String[10];
            strZones[0] = "space_tatooine";
            strZones[1] = "space_corellia";
            strZones[2] = "space_dantooine";
            strZones[3] = "space_dathomir";
            strZones[4] = "space_endor";
            strZones[5] = "space_heavy1";
            strZones[6] = "space_lok";
            strZones[7] = "space_naboo";
            strZones[8] = "space_tatooine";
            strZones[9] = "space_yavin4";
            for (int intM = 0; intM < strZones.length; intM++)
            {
                debugSpeakMsg(self, "Now doing " + strZones[intM]);
                String strFileName = "datatables/space_zones/buildout/" + strZones[intM] + ".iff";
                String[] strObjVars = dataTableGetStringColumn(strFileName, "strObjVars");
                String[] strObject = dataTableGetStringColumn(strFileName, "strObject");
                for (int intI = 0; intI < strObject.length; intI++)
                {
                    if (strObject[intI].equals("object/tangible/space/content_infrastructure/basic_spawner.iff"))
                    {
                        obj_id objTest = createObject("object/weapon/ranged/rifle/rifle_dlt20a.iff", getLocation(self));
                        try
                        {
                            setPackedObjvars(objTest, strObjVars[intI]);
                        }
                        catch(Throwable err)
                        {
                            LOG("space_error", "OBJVAR ENTRY " + intM + " in " + strFileName + " is TRUNCATED value is " + strObjVars);
                        }
                        dictionary dctRow = new dictionary(2);
                        String[] strSpawns = objvar_mangle.getMangledStringArrayObjVar(objTest, "strSpawns");
                        if (strSpawns == null)
                        {
                            strSpawns = objvar_mangle.getBrokenMangledStringArrayObjVar(objTest, "strSpawns");
                            if (strSpawns == null)
                            {
                                LOG("space", "FUCKED OBJVARS!@!@!@!@! ON " + objTest);
                            }
                            else 
                            {
                                LOG("space", "BROKEN MANGLED OBJVAR GOTTEN");
                            }
                        }
                        for (int i = 0; i < strSpawns.length; i++)
                        {
                            if (strSpawns[i].startsWith("squad_"))
                            {
                                String squadFile = "datatables/space_content/spawners/squads.iff";
                                String spaceMobile = "datatables/space_mobile/space_mobile.iff";
                                dictionary squad = dataTableGetRow(squadFile, strSpawns[i]);
                                for (int j = 1; j < 11; j++)
                                {
                                    String temp = squad.getString("strShip" + j);
                                    if (temp != null && !temp.equals(""))
                                    {
                                        dctRow.put("strMob", temp);
                                        dctRow.put("strZone", strZones[intM]);
                                        dctRow.put("strSquad", strSpawns[i]);
                                        dictionary dctSpaceMobile = dataTableGetRow(spaceMobile, temp);
                                        if (dctSpaceMobile == null)
                                        {
                                            LOG("space_error", "SPACE MOBILE IS NULL HERE" + ":  SHIP IS" + strSpawns[i]);
                                        }
                                        else 
                                        {
                                            String tier = dctSpaceMobile.getString("strPilot");
                                            if (tier == null)
                                            {
                                                tier = "NULL";
                                            }
                                            String treasure = dctSpaceMobile.getString("strLootLookup");
                                            if (treasure == null)
                                            {
                                                treasure = "NULL";
                                            }
                                            String shiptype = dctSpaceMobile.getString("strShip");
                                            if (shiptype == null)
                                            {
                                                shiptype = "NULL";
                                            }
                                            dctRow.put("strTreasure", treasure);
                                            dctRow.put("strShipType", shiptype);
                                            dctRow.put("strTier", tier);
                                        }
                                        datatable.serverDataTableAddRow(strDataTable, dctRow);
                                    }
                                }
                            }
                            else 
                            {
                                if (strSpawns[i] != null && !strSpawns[i].equals(""))
                                {
                                    String spaceMobile = "datatables/space_mobile/space_mobile.iff";
                                    dctRow.put("strMob", strSpawns[i]);
                                    dctRow.put("strZone", strZones[intM]);
                                    dictionary dctSpaceMobile = dataTableGetRow(spaceMobile, strSpawns[i]);
                                    if (dctSpaceMobile == null)
                                    {
                                        LOG("space_error", "SPACE MOBILE IS NULL HERE" + ":  SHIP IS" + strSpawns[i]);
                                    }
                                    else 
                                    {
                                        String tier = dctSpaceMobile.getString("strPilot");
                                        if (tier == null)
                                        {
                                            tier = "NULL";
                                        }
                                        String treasure = dctSpaceMobile.getString("strLootLookup");
                                        if (treasure == null)
                                        {
                                            treasure = "NULL";
                                        }
                                        String shiptype = dctSpaceMobile.getString("strShip");
                                        if (shiptype == null)
                                        {
                                            shiptype = "NULL";
                                        }
                                        dctRow.put("strTreasure", treasure);
                                        dctRow.put("strShipType", shiptype);
                                        dctRow.put("strTier", tier);
                                    }
                                    datatable.serverDataTableAddRow(strDataTable, dctRow);
                                }
                            }
                        }
                        datatable.serverDataTableAddRow(strDataTable, dctRow);
                        destroyObject(objTest);
                    }
                    LOG("space_error", "FINISHED WITH " + strZones[intM]);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
