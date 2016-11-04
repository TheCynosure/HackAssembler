import java.io.*;
import java.util.*;

/**
 * Created by jack on 10/23/16.
 */
public class Main {

    private static Map<String, Integer> symbolList = new HashMap<>();
    private static int symbolCounter = 1024;


    public static void main(String[] args) {
        File file = new File(args[0]);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "";
            int i = 0;
            //Building the Symbol File
            while((line = reader.readLine()) != null) {
                //Ignore Comments
                findSymbols(line, symbolList, i);
                i++;
            }
            reader = new BufferedReader(new FileReader(file));
            File hackFile = new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().indexOf(file.getName())) + file.getName().split("\\.")[0] + ".hack");
            BufferedWriter writer = new BufferedWriter(new FileWriter(hackFile));
            //Instruction loop
            while((line = reader.readLine()) != null) {
                if(!line.startsWith("//")) {
                    writer.write(getInstruction(line));
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getInstruction(String line) {
        System.out.println(symbolList);
    }

    private static void findSymbols(String line, Map<String, Integer> symbolList, int lineNum) {
        String[] strings = line.split(" ");
        //Check if it is a variable
        if(strings[0].contains("=")) {
            String variableName = strings[0].substring(0, strings[0].indexOf("="));
            if(!variableName.equals("D") && !variableName.equals("A") && !variableName.equals("M")) {
                symbolList.put(variableName, symbolCounter);
                symbolCounter++;
            }
        } else if(strings[0].contains(":")) { //Check if a label
            String labelName = strings[0].substring(0, strings[0].length() - 1);
            symbolList.put(labelName, lineNum);
        }
    }
}
