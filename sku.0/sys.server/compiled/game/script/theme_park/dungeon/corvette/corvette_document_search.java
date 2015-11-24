package script.theme_park.dungeon.corvette;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class corvette_document_search extends script.base_script
{
    public corvette_document_search()
    {
    }
    public static final String DATATABLE_NAME = "datatables/dungeon/corellian_corvette_quest.iff";
    public static final string_id SEARCH_ITEM = new string_id("bestine", "search_item");
    public static final string_id ALREADY_SEARCHED_MSG = new string_id("bestine", "already_searched");
    public static final string_id DEFAULT_RECEIVE_MSG = new string_id("bestine", "default_receive_msg");
    public static final string_id INVENTORY_FULL_MSG = new string_id("bestine", "inventory_full");
    public static final String GATING_COLUMN = "gatingObjVar";
    public static final String ALREADY_SEARCHED_COLUMN = "alreadySearchedObjVar";
    public static final String INTEL_DOCUMENT_COLUMN = "intel_document";
    public static final String FILLER_01_DOCUMENT_COLUMN = "filler01_document";
    public static final String FILLER_02_DOCUMENT_COLUMN = "filler02_document";
    public static final String DOCUMENTS_LIST_OBJVAR_BASE = "corl_corvette.documentsList";
    public static final String SEARCH_OBJECT_INDEX_OBJVAR = "corl_corvette.searchObject";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (canSearch(self, player))
        {
            int menuOption = mi.addRootMenu(menu_info_types.SERVER_ITEM_OPTIONS, SEARCH_ITEM);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_ITEM_OPTIONS)
        {
            if (canSearch(self, player))
            {
                String objectTemplate = getTemplateName(self);
                dictionary templateRow = dataTableGetRow(DATATABLE_NAME, objectTemplate);
                String gatingObjVar = templateRow.getString(GATING_COLUMN);
                java.util.StringTokenizer foo = new java.util.StringTokenizer(gatingObjVar, ".");
                foo.nextToken();
                String questName = foo.nextToken();
                String documentsListObjvar = DOCUMENTS_LIST_OBJVAR_BASE + "." + questName;
                String documentFoundColumn = "";
                String[] documents = new String[3];
                int searchObjectIndex = getIntObjVar(self, SEARCH_OBJECT_INDEX_OBJVAR);
                if (hasObjVar(player, documentsListObjvar))
                {
                    String[] tempArray = getStringArrayObjVar(player, documentsListObjvar);
                    for (int i = 0; i < tempArray.length; i++)
                    {
                        documents[i] = tempArray[i];
                    }
                    documentFoundColumn = documents[searchObjectIndex];
                }
                else 
                {
                    String document01 = INTEL_DOCUMENT_COLUMN;
                    String document02 = FILLER_01_DOCUMENT_COLUMN;
                    String document03 = FILLER_02_DOCUMENT_COLUMN;
                    int documentSetup = rand(1, 6);
                    switch (documentSetup)
                    {
                        case 1:
                        document01 = INTEL_DOCUMENT_COLUMN;
                        document02 = FILLER_01_DOCUMENT_COLUMN;
                        document03 = FILLER_02_DOCUMENT_COLUMN;
                        break;
                        case 2:
                        document01 = INTEL_DOCUMENT_COLUMN;
                        document02 = FILLER_02_DOCUMENT_COLUMN;
                        document03 = FILLER_01_DOCUMENT_COLUMN;
                        break;
                        case 3:
                        document01 = FILLER_01_DOCUMENT_COLUMN;
                        document02 = INTEL_DOCUMENT_COLUMN;
                        document03 = FILLER_02_DOCUMENT_COLUMN;
                        break;
                        case 4:
                        document01 = FILLER_02_DOCUMENT_COLUMN;
                        document02 = INTEL_DOCUMENT_COLUMN;
                        document03 = FILLER_01_DOCUMENT_COLUMN;
                        break;
                        case 5:
                        document01 = FILLER_01_DOCUMENT_COLUMN;
                        document02 = FILLER_02_DOCUMENT_COLUMN;
                        document03 = INTEL_DOCUMENT_COLUMN;
                        break;
                        case 6:
                        document01 = FILLER_02_DOCUMENT_COLUMN;
                        document02 = FILLER_01_DOCUMENT_COLUMN;
                        document03 = INTEL_DOCUMENT_COLUMN;
                        break;
                    }
                    documents[0] = document01;
                    documents[1] = document02;
                    documents[2] = document03;
                    setObjVar(player, documentsListObjvar, documents);
                    documentFoundColumn = documents[searchObjectIndex];
                }
                if ((documentFoundColumn != null) && (!documentFoundColumn.equals("")))
                {
                    if (documentFoundColumn.equals("received"))
                    {
                        sendSystemMessage(player, ALREADY_SEARCHED_MSG);
                        return SCRIPT_CONTINUE;
                    }
                    String documentFoundTemplate = templateRow.getString(documentFoundColumn);
                    obj_id playerInv = utils.getInventoryContainer(player);
                    if (isIdValid(playerInv))
                    {
                        int free_space = getVolumeFree(playerInv);
                        if (free_space > 0)
                        {
                            obj_id objectReceived = createObject(documentFoundTemplate, playerInv, "");
                            if (isIdValid(objectReceived))
                            {
                                setObjVar(objectReceived, "noTrade", true);
                                if (!hasScript(objectReceived, "item.special.nomove"))
                                {
                                    attachScript(objectReceived, "item.special.nomove");
                                }
                                string_id receiveObjectMsg = DEFAULT_RECEIVE_MSG;
                                sendSystemMessage(player, receiveObjectMsg);
                                documents[searchObjectIndex] = "received";
                                setObjVar(player, documentsListObjvar, documents);
                            }
                        }
                        else 
                        {
                            sendSystemMessage(player, INVENTORY_FULL_MSG);
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean canSearch(obj_id self, obj_id player) throws InterruptedException
    {
        boolean result = false;
        String objectTemplate = getTemplateName(self);
        dictionary templateRow = dataTableGetRow(DATATABLE_NAME, objectTemplate);
        if (templateRow != null)
        {
            String gatingObjVar = templateRow.getString(GATING_COLUMN);
            if (hasObjVar(player, gatingObjVar))
            {
                result = true;
            }
        }
        return result;
    }
}
