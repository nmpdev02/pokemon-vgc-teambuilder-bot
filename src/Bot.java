import java.io.BufferedReader;
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
  private Map<String, Integer> rankedPokemon;

  public Bot() {
    this.listedPokemon = new ArrayList<Pokemon>();
    rankedPokemon = new HashMap<String, Integer>();
  }

  public void printPokemon() {
    Iterator<Pokemon> it = listedPokemon.iterator();
    while (it.hasNext()) {
      Pokemon pokemon = it.next();
      System.out.println(pokemon.getName() + " Usage%: " + pokemon.getUsage());
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
            usage = getUsage(line);
            Pokemon pokemon = new Pokemon(name, usage);
            
            listedPokemon.add(pokemon); // add pokemon to list
          }
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

  // returns all letters and '-' characters in the line argument
  public String getName(String line) {
    String result = "";

    for (int i = 0; i < line.length(); i++) {
      char currentChar = line.charAt(i);
      if (Character.isLetter(currentChar) || currentChar == '-') {
        result += currentChar;
      }
    }

    return result;
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

