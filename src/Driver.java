import java.lang.reflect.Field;

public class Driver {
  
  public static void main(String args[]) throws Exception {

    Bot bot = new Bot();
    bot.initializeAttacks();
    bot.printAttacksDetails();
    bot.createPokemon();
    bot.printPokemonDetails();

  }

}
