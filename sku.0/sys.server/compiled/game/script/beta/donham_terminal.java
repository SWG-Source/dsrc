package script.beta;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;

public class donham_terminal extends script.base_script
{
    public donham_terminal()
    {
    }
    public static final String DATATABLE_ITEMS_TO_TEST = "datatables/beta/items_to_test.iff";
    public static final string_id SID_ORDER_ITEM = new string_id("sui", "order_item");
    public static final String CATEGORY_PROMPT = "Select an item category.";
    public static final String ITEM_REQUEST_PROMPT = "Select a template to test.";
    public static final String HANDLER_CATEGORY_RESPONSE = "handleCategoryResponse";
    public static final String HANDLER_ITEM_REQUEST = "handleItemRequest";
    public static final String VAR_ITEM_REQUEST_BASE = "item_request";
    public static final String VAR_ITEM_REQUEST_CATEGORY = VAR_ITEM_REQUEST_BASE + ".category";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int mnu = mi.addRootMenu(menu_info_types.SERVER_ITEM_OPTIONS, SID_ORDER_ITEM);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_ITEM_OPTIONS)
        {
            if (dataTableOpen(DATATABLE_ITEMS_TO_TEST))
            {
                String[] colName = dataTableGetStringColumn(DATATABLE_ITEMS_TO_TEST, "column_names");
                if ((colName == null) || (colName.length == 0))
                {
                    sendSystemMessageTestingOnly(player, "Unable to load test item information. Exiting.");
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    String[] entries = new String[colName.length];
                    for (int i = 0; i < colName.length; i++)
                    {
                        entries[i] = "@beta:" + colName[i];
                    }
                    sui.listbox(self, player, CATEGORY_PROMPT, entries, HANDLER_CATEGORY_RESPONSE);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleCategoryResponse(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id playerId = sui.getPlayerId(params);
        if ((playerId == null) || (playerId == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            int selRow = sui.getListboxSelectedRow(params);
            if (selRow == -1)
            {
                return SCRIPT_CONTINUE;
            }
            else 
            {
                if (dataTableOpen(DATATABLE_ITEMS_TO_TEST))
                {
                    String title = "unknown";
                    String[] colName = dataTableGetStringColumn(DATATABLE_ITEMS_TO_TEST, "column_names");
                    if ((colName == null) || (colName.length == 0))
                    {
                        return SCRIPT_CONTINUE;
                    }
                    else 
                    {
                        title = colName[selRow];
                    }
                    String[] templates = dataTableGetStringColumn(DATATABLE_ITEMS_TO_TEST, selRow + 1);
                    if ((templates == null) || (templates.length == 0))
                    {
                        return SCRIPT_CONTINUE;
                    }
                    else 
                    {
                        if (!title.equals("unknown"))
                        {
                            setObjVar(playerId, VAR_ITEM_REQUEST_CATEGORY, title);
                            sui.listbox(self, playerId, ITEM_REQUEST_PROMPT, sui.OK_ONLY, title, templates, HANDLER_ITEM_REQUEST);
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleItemRequest(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id playerId = sui.getPlayerId(params);
        if ((playerId == null) || (playerId == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            String category = getStringObjVar(playerId, VAR_ITEM_REQUEST_CATEGORY);
            removeObjVar(playerId, VAR_ITEM_REQUEST_BASE);
            int selRow = sui.getListboxSelectedRow(params);
            if (selRow == -1)
            {
                return SCRIPT_CONTINUE;
            }
            else 
            {
                if (dataTableOpen(DATATABLE_ITEMS_TO_TEST))
                {
                    String template = dataTableGetString(DATATABLE_ITEMS_TO_TEST, selRow, category);
                    if ((template == null) || (template.equals("")))
                    {
                    }
                    else 
                    {
                        obj_id pInv = utils.getInventoryContainer(playerId);
                        if ((pInv == null) || (pInv == obj_id.NULL_ID))
                        {
                            return SCRIPT_CONTINUE;
                        }
                        else 
                        {
                            obj_id itemId = createObject(template, pInv, "");
                            if ((itemId == null) || (itemId == obj_id.NULL_ID))
                            {
                                return SCRIPT_CONTINUE;
                            }
                            else 
                            {
                            }
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
