import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Bot {

  private List<Pokemon> listedPokemon;
  private List<String> pokemonNames;
  private Map<String, Integer> rankedPokemon;

  public Bot() {
    this.listedPokemon = new ArrayList<Pokemon>();
    pokemonNames = new ArrayList<String>();
    rankedPokemon = new HashMap<String, Integer>();
  }

  public void printPokemon() {
    Iterator<Pokemon> it = listedPokemon.iterator();
    while (it.hasNext()) {
      Pokemon pokemon = it.next();
      System.out.println(pokemon.getName() + " Usage: " + pokemon.getUsage() + "%");
    } 
  }

  public void printPokemonDetails() {
    Iterator<Pokemon> it = listedPokemon.iterator();
    while (it.hasNext()) {
      Pokemon pokemon = it.next();
      System.out.println(pokemon.getName() + " " + pokemon.getDetails());
    } 
  }

  public void createPokemon() throws Exception {

    try {
      Scanner myObj = new Scanner(System.in);   
      System.out.println("Enter URL for pokemon showdown usage stats:");
      //String usageStats = myObj.nextLine(); // Read user input
      
      // TEMPORARY, REMOVE ME!!!!!!!
      String usageStats = "https://www.smogon.com/stats/2022-04/gen8vgc2022-1760.txt";

      URL url = new URL(usageStats);
       
      // read text returned by server
      BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
       
      String line, name;
      double usage;
      while ((line = in.readLine()) != null) {
          
          if (Character.isDigit(line.charAt(3))) { // check if the line we are on contains a pokemon
            name = getName(line);
            if (name.contains("Silvally") || name.contains("Type Null") || name.equals("Arrokuda")) continue;
            pokemonNames.add(name);
            usage = getUsage(line);
            Pokemon pokemon = new Pokemon(name, usage);
            
            listedPokemon.add(pokemon); // add pokemon to list
          }
      }
      in.close();

      Scanner myObj2 = new Scanner(System.in);   
      System.out.println("Enter filepath for data set csv:");
      //String usageStats = myObj2.nextLine(); // Read user input
      
      // TEMPORARY, REMOVE ME!!!!!!!
      String dataSet = "D:\\Downloads\\Pokemon Database.csv";
       
      // read text returned by server
      BufferedReader in2 = new BufferedReader(new FileReader(dataSet));
       
      String line2;
      while ((line2 = in2.readLine()) != null) {
          StringBuilder builder = new StringBuilder(line2);
          for (int i = 0; i < builder.length(); i++) {
            if (builder.charAt(i) == '"') {
              builder.deleteCharAt(i);
              i--;
            }
            if (builder.charAt(i) == ',' && builder.charAt(i + 1) == ' ') {
              builder.deleteCharAt(i);
              i--;
            } 
          }
          line2 = builder.toString();
          String[] columns = line2.split(",");      
          if (validPokemon(columns)) {
              initializeDetails(columns);
            };
          }
      in2.close();
       
  }
  catch (MalformedURLException e) {
      System.out.println("Malformed URL: " + e.getMessage());
  }
  catch (IOException e) {
      System.out.println("I/O Error: " + e.getMessage());
  }

  }

  public void initializeDetails(String[] columns) throws Exception {
    String fullname = columns[2];
    Iterator<Pokemon> it = listedPokemon.iterator();

    if (!columns[4].contains("NULL")) {  
      if (columns[4].contains(" ")) {
        StringBuilder builder2 = new StringBuilder(columns[4]);
        builder2.delete(builder2.indexOf(" "), builder2.length());
        System.out.println(builder2.toString());
        while(it.hasNext()) {
          Pokemon pokemon = it.next();
          if (pokemon.getName().contains(fullname) && pokemon.getName().contains(builder2.toString())) {
            pokemon.setupPokemon(columns);
            break;
        }
      }
      } else {
        fullname = (fullname + "-" + columns[4]);
        if (fullname.contains("Indeedee")) fullname = "Indeedee-F";
        while(it.hasNext()) {
          Pokemon pokemon = it.next();
          if (pokemon.getName().equals(fullname)) {
            pokemon.setupPokemon(columns);
            break;
          }
        }
      } 
    } else {           
      while(it.hasNext()) {
        Pokemon pokemon = it.next();
        if (pokemon.getName().equals(fullname)) {
          pokemon.setupPokemon(columns);
          break;
        }
      }
    }

  }

  public boolean validPokemon(String[] line) {
    boolean result = true;

    // Excludes pokemon not in the usage stats, mega forms, mythicals, etc. stuff not allowed in VGC
    if (!pokemonNames.contains(line[2]) || line[4].contains("Mega") || line[4].contains("Starter")
      || line[4].contains("Eternamax") || line[6].contains("Mythical")) {
      result = false;
    }

    return result;
  }

  // returns all letters and '-' characters in the line argument
  public String getName(String line) {
    String result = "";
    int counter = 0;

    for (int i = 0; i < line.length(); i++) {
      char currentChar = line.charAt(i);

      if (currentChar == '|') counter++;

      if (counter == 2) {
        if (Character.isLetter(currentChar) || currentChar == '-' || currentChar == '.'
          || currentChar == ' ' || Character.isDigit(currentChar) 
          || currentChar == '\'' || currentChar == '%') {
        result += currentChar;
      }
      }
      
    }

    StringBuilder builder = new StringBuilder(result);
    builder.deleteCharAt(0);

    for (int i = builder.length() - 1; i >= 0; i--) {
      if (builder.charAt(i) == ' ') {
        builder.deleteCharAt(i);
      } else {
        break;
      }
    }

    return builder.toString();
  }

  public double getUsage(String line) {
    String usage = "";
    double result = 0.0;
    int counter = 0;

    for (int i = 0; i < line.length(); i++) {
      char currentChar = line.charAt(i);

      if (counter == 7) {
        if (Character.isDigit(currentChar) || currentChar == '.') {
          usage += currentChar;
        }
      }

      if (currentChar == '|') counter++;

    }

    result = Double.parseDouble(usage);

    return result;
  }



}

