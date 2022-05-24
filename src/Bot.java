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

  public static List<Pokemon> listedPokemon;
  private List<String> pokemonNames;
  public static List<Attack> allAttacks;
  private Map<Pokemon, Double> rankedPokemon;

  public Bot() {
    Bot.listedPokemon = new ArrayList<Pokemon>();
    pokemonNames = new ArrayList<String>();
    Bot.allAttacks = new ArrayList<Attack>();
    rankedPokemon = new HashMap<Pokemon, Double>();
  }

  public void calculatePoints(ArrayList<Pokemon> listedPokemon) {
    HashMap<Pokemon, Double> rankedList = new HashMap<Pokemon, Double>();

    Iterator<Pokemon> it1 = listedPokemon.iterator();
    Iterator<Pokemon> it2 = listedPokemon.iterator();
    while(it1.hasNext()) {
      Pokemon pokemon = it1.next();
      double value = 0; // reset value for each new pokemon

      while(it2.hasNext()) {
        double attacking, defending;
        Pokemon opponent = it2.next();
        attacking = pokemon.offensivePotential(opponent);
        defending = opponent.offensivePotential(pokemon);
        value += (attacking / defending);
      }

      value += pokemon.gmaxPoints();
      value += pokemon.statPoints();
      
      rankedList.put(pokemon, value);
    }

    this.rankedPokemon = rankedList;
  }

  public void initializeAttacks() throws Exception {
    if (allAttacks != null) {

        try {

            URL url = new URL("https://pokemondb.net/move/all");

            // read text returned by server
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;
            int lineNumber = 0;
            while ((line = in.readLine()) != null) {
                if (lineNumber > 203) {
                    line = cleanTextContent(line);
                    Attack attack = new Attack();
                    if (line.contains("cell-name")) {
                        attack.setDetails(line);

                        lineNumber ++;
                        line = in.readLine();
                        lineNumber++;
                        line = in.readLine();

                        attack.setEffect(line);
                        attack.setUtility();
                        allAttacks.add(attack);
                    }
                }
                lineNumber++;

            }
            in.close();

        }
        catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + e.getMessage());
        }
        catch (IOException e) {
            System.out.println("I/O Error: " + e.getMessage());
        }

    }
}

public void printAttacks() {
  Iterator<Attack> it = allAttacks.iterator();
  while(it.hasNext()) {
    Attack attack = it.next();
    System.out.println(attack.getName());
  }
}

public void printAttacksDetails() {
  Iterator<Attack> it = allAttacks.iterator();
  while(it.hasNext()) {
    Attack attack = it.next();
    if (attack != null && attack.getType() != null) {
      System.out.println(attack.getName() +": " + attack.getType().getName() + ", power: " + attack.getPower() + ", accuracy: " + attack.getAccuracy());
    }
  }
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

  private static String cleanTextContent(String text) {
      // strips off all non-ASCII characters
      text = text.replaceAll("[^\\x00-\\x7F]", "");

      // erases all the ASCII control characters
      text = text.replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");

      // removes non-printable characters from Unicode
      text = text.replaceAll("\\p{C}", "");

      text = text.replaceAll("[^ -~]","");

      text = text.replaceAll("[^\\p{ASCII}]", "");

      text = text.replaceAll("\\\\x\\p{XDigit}{2}", "");

      text = text.replaceAll("\\n","");

      text = text.replaceAll("[^\\x20-\\x7e]", "");

      return text.trim();
    }


}

