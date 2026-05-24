package script.library.conversation_manager;

import script.dictionary;
import script.library.datatable_api;
import script.library.conversation_manager.ConversationManagerData.ResponseAction;

public class ResponseActionTable
{
    public static final String DEFAULT_DIRECTORY = "datatables/conversation";
    public static final String TABLE_SUFFIX = "_response_actions.iff";

    public ResponseActionTable()
    {
    }

    public static String getTableName(String baseName)
    {
        return getTableName(DEFAULT_DIRECTORY, baseName);
    }

    public static String getTableName(String directory, String baseName)
    {
        if (baseName == null)
        {
            return null;
        }
        String normalizedBaseName = normalizeBaseName(baseName);
        String normalizedDirectory = normalizeDirectory(directory);
        if (normalizedDirectory.length() == 0)
        {
            return normalizedBaseName + TABLE_SUFFIX;
        }
        if (normalizedDirectory.endsWith("/"))
        {
            return normalizedDirectory + normalizedBaseName + TABLE_SUFFIX;
        }
        return normalizedDirectory + "/" + normalizedBaseName + TABLE_SUFFIX;
    }

    public static ResponseAction[] getResponseActions(String baseName) throws InterruptedException
    {
        return getResponseActionsFromTable(getTableName(baseName));
    }

    public static ResponseAction[] getResponseActions(String directory, String baseName) throws InterruptedException
    {
        return getResponseActionsFromTable(getTableName(directory, baseName));
    }

    public static ResponseAction[] getResponseActionsByStateId(String directory, String baseName, String responseStateId) throws InterruptedException
    {
        return getResponseActionsByStateIdFromTable(getTableName(directory, baseName), responseStateId);
    }

    public static ResponseAction[] getResponseActionsFromTable(String table) throws InterruptedException
    {
        dictionary[] rows = datatable_api.getRows(table);
        return fromDictionaries(rows, true);
    }

    public static ResponseAction[] getResponseActionsByStateIdFromTable(String table, String responseStateId) throws InterruptedException
    {
        dictionary[] rows = datatable_api.findRowsByString(table, ResponseAction.COLUMN_RESPONSE_STATE_ID, responseStateId);
        return fromDictionaries(rows, true);
    }

    private static ResponseAction[] fromDictionaries(dictionary[] rows, boolean sortByOrder)
    {
        ResponseAction[] actions = new ResponseAction[rows.length];
        for (int i = 0; i < rows.length; i++)
        {
            actions[i] = ResponseAction.fromDictionary(rows[i]);
        }
        if (sortByOrder)
        {
            sortByOrder(actions);
        }
        return actions;
    }

    private static void sortByOrder(ResponseAction[] actions)
    {
        for (int i = 1; i < actions.length; i++)
        {
            ResponseAction current = actions[i];
            int j = i - 1;
            while (j >= 0 && actions[j] != null && current != null && actions[j].order > current.order)
            {
                actions[j + 1] = actions[j];
                j--;
            }
            actions[j + 1] = current;
        }
    }

    private static String normalizeDirectory(String directory)
    {
        if (directory == null || directory.length() == 0)
        {
            return DEFAULT_DIRECTORY;
        }
        if (directory.startsWith("datatables/"))
        {
            return directory;
        }
        if (directory.startsWith("conversation/"))
        {
            return "datatables/" + directory;
        }
        return DEFAULT_DIRECTORY + "/" + directory;
    }

    private static String normalizeBaseName(String baseName)
    {
        if (baseName.endsWith("_") && TABLE_SUFFIX.startsWith("_"))
        {
            return baseName.substring(0, baseName.length() - 1);
        }
        return baseName;
    }
}
