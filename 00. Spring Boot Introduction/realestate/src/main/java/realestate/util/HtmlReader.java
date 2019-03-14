package realestate.util;

import java.io.*;

public class HtmlReader {
    public String readHtmlFile(String htmlFilePath) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(htmlFilePath)));

        StringBuilder htmFileContent = new StringBuilder();

        String line;

        while ((line = bufferedReader.readLine()) != null) {
            htmFileContent.append(line).append(System.lineSeparator());
        }

        return htmFileContent.toString().trim();
    }
}
