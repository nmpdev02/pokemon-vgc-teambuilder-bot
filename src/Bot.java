import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Map.Entry;

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

  public void printSinglePokemon(String name) {
    Iterator<Pokemon> it = listedPokemon.iterator();
    while(it.hasNext()) {
      Pokemon pokemon = it.next();
      if (pokemon.getName().equals(name)) {
        System.out.println();
        System.out.println(name + ": " + pokemon.getType1().getName() + ", " + pokemon.getType2().getName());
        System.out.println();
        System.out.print("Moves: " + pokemon.getSet().getMoveSet()[0].getName() + ", ");
        System.out.print(pokemon.getSet().getMoveSet()[1].getName() + ", ");
        System.out.print(pokemon.getSet().getMoveSet()[2].getName() + ", ");
        System.out.println(pokemon.getSet().getMoveSet()[3].getName());
        System.out.println();
        System.out.println("Item: " + pokemon.getSet().getItem().getName());
        System.out.println();
        System.out.println("Ability: " + pokemon.getSet().getAbility().getName());
        System.out.println();
        System.out.print("Stats: " + pokemon.getSet().getHPStat() + " ");
        System.out.print(pokemon.getSet().getAttackStat() + " ");
        System.out.print(pokemon.getSet().getDefenseStat() + " ");
        System.out.print(pokemon.getSet().getSpattackStat() + " ");
        System.out.print(pokemon.getSet().getSpdefenseStat() + " ");
        System.out.println(pokemon.getSet().getSpeedStat());
        System.out.println("spAttack EVs: " + pokemon.getSet().getEVs()[3]);
      }
    }
  }

  public void printRankedList() {

    Set<Entry<Pokemon, Double>> entrySet = rankedPokemon.entrySet();

    List<Entry<Pokemon, Double>> list = new ArrayList<>(entrySet);

    Collections.sort(list, new Comparator<Entry<Pokemon, Double>>() {

      @Override
      public int compare(Entry<Pokemon, Double> o1, Entry<Pokemon, Double> o2) {
        return o1.getValue().compareTo(o2.getValue());
      }
    });

    list.forEach(entry->{
      System.out.println(entry.getKey().getSet().getItem().getName() + " " + entry.getKey().getName() + ": " + entry.getValue().intValue() + " points");
    });

  }

  public void calculatePoints(List<Pokemon> listedPokemon) {
    HashMap<Pokemon, Double> rankedList = new HashMap<Pokemon, Double>();

    Iterator<Pokemon> it1 = listedPokemon.iterator();
    List<Pokemon> temp = listedPokemon;
    Iterator<Pokemon> it2 = temp.iterator();

    while(it1.hasNext()) {
      Pokemon pokemon = it1.next();
      double value = 0; // reset value for each new pokemon

      it2 = temp.iterator();

      if (pokemon.getName().equals("Flygon")) {
        break;
      }

      if (pokemon.getSet().getNature() == null || pokemon.getSet().getNature().getName() == null) continue;

      while(it2.hasNext()) {
        double attacking, defending;
        Pokemon opponent = it2.next();

        if (opponent.getName().equals("Krookodile")) {
          break;
        }

        if (opponent.getSet().getNature() == null || opponent.getSet().getNature().getName() == null) {
          continue;
        }

        if (opponent.getName().equals("Ditto")) {
          opponent = pokemon;
        }
        if (pokemon.getName().equals("Ditto")) {
          pokemon = opponent;
        }
        if (!(pokemon.getName().equals(opponent.getName()))) {
        attacking = (pokemon.offensivePotential(opponent)); // Max damage pokemon can do to opponent
        defending = (opponent.offensivePotential(pokemon)); // Max damage opponent can do to pokemon
        if (defending == 0) defending = 1;
        if (defending >= pokemon.getSet().getHPStat() && (opponent.getSet().getSpeedStat() > pokemon.getSet().getSpeedStat())) {
          continue;
        } 
        if (defending >= pokemon.getSet().getHPStat() && attacking < opponent.getSet().getHPStat()) {
          continue;
        } 
        value += ((attacking / defending) * opponent.getUsage());
        //value += (attacking * opponent.getUsage());
      }
      }

      //value += pokemon.statPoints();
      
      rankedList.put(pokemon, value);
    }

    this.rankedPokemon = rankedList;
    this.printRankedList();
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

            if (name.equals("Flygon")) break;

            pokemonNames.add(name);
            usage = getUsage(line);
            Pokemon pokemon = new Pokemon(name, usage);
            
            listedPokemon.add(pokemon); // add pokemon to list
            pokemon.createSet();
            pokemon.getSet().initializeStats(pokemon);
          }
      }
      in.close();

      // Iterator<Pokemon> it = listedPokemon.iterator();

      // while (it.hasNext()) {
      //   Pokemon pokemon = it.next();

      //   if (pokemon.getName().equals("Claydoll")) break;

      //   pokemon.createSet();
      //   pokemon.getSet().initializeStats(pokemon);
      // }
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

  public static String cleanTextContent(String text) {
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

