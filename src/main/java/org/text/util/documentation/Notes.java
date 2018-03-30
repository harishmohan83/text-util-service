package org.text.util.documentation;

public class Notes {

    public static final String SERVICE_DESCRIPTION = "Text Util Service has apis to support creation, read and deletion of text. It also provide an api to fetch sample json data. The service uses an in-memory cassandra database.";

    public static final String TEXT_CREATE = "Saves the input text to data base and returns the text id";
    public static final String TEXT_READ = "Reads the text from data base by text id";
    public static final String TEXT_DELETE = "Deletes the text in database by text id";
    public static final String FETCH_SAMPLE_JSON = "Returns sample json data";
 
}
