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
      double value = 0; // reset value for each new pokemon

      while(it2.hasNext()) {
        double attacking, defending;
        Pokemon opponent = it2.next();
        attacking = pokemon.offensivePotential(opponent);
        defending = opponent.offensivePotential(pokemon);
        value += (attacking / defending);
      }

      rankedList.put(pokemon, value);
    }

    return rankedList;
  }

public double defensivePoints(Pokemon pokemon) {
    double result = 0.0;

    return result;
}



}
