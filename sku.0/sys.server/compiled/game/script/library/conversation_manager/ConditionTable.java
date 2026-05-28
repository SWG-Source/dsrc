package script.library.conversation_manager;

import script.dictionary;
import script.library.datatable_api;
import script.library.conversation_manager.ConversationManagerData.Condition;

public class ConditionTable
{
    public static final String DEFAULT_DIRECTORY = "datatables/conversation";
    public static final String TABLE_SUFFIX = "_conditions.iff";

    public ConditionTable()
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

    public static Condition[] getConditions(String baseName) throws InterruptedException
    {
        return getConditionsFromTable(getTableName(baseName));
    }

    public static Condition[] getConditions(String directory, String baseName) throws InterruptedException
    {
        return getConditionsFromTable(getTableName(directory, baseName));
    }

    public static Condition getCondition(String directory, String baseName, String id) throws InterruptedException
    {
        return getConditionFromTable(getTableName(directory, baseName), id);
    }

    public static Condition[] getConditionsFromTable(String table) throws InterruptedException
    {
        dictionary[] rows = datatable_api.getRows(table);
        Condition[] conditions = new Condition[rows.length];
        for (int i = 0; i < rows.length; i++)
        {
            conditions[i] = Condition.fromDictionary(rows[i]);
        }
        return conditions;
    }

    public static Condition getConditionFromTable(String table, String id) throws InterruptedException
    {
        dictionary row = datatable_api.firstRowByString(table, Condition.COLUMN_ID, id);
        return Condition.fromDictionary(row);
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
