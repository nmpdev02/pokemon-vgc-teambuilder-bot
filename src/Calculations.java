import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Calculations {

  private List<Pokemon> listedPokemon;
  
  public Calculations(ArrayList<Pokemon> listedPokemon) {
    this.listedPokemon = listedPokemon;
  }

  public HashMap<Pokemon, Double> calculatePoints(ArrayList<Pokemon> listedPokemon) {
    HashMap<Pokemon, Double> rankedList = new HashMap<Pokemon, Double>();

    Iterator<Pokemon> it1 = listedPokemon.iterator();
    Iterator<Pokemon> it2 = listedPokemon.iterator();
    while(it1.hasNext()) {
      Pokemon pokemon = it1.next();
      double value = 0;
      while(it2.hasNext()) {
        Pokemon opponent = it2.next();
      }
    }

    return rankedList;
  }

  

public double offensivePoints(Pokemon pokemon, ArrayList<Pokemon> listedPokemon) {
    double result = 0.0;

    Iterator<Pokemon> in = listedPokemon.iterator();
    while (in.hasNext()) {
        result += pokemon.offensivePotential(pokemon, in.next());
    }

    return result;
}

public double defensivePotential(Pokemon pokemon) {
    double result = 0.0;

    return result;
}

public double defensivePoints(Pokemon pokemon) {
    double result = 0.0;

    return result;
}



}
