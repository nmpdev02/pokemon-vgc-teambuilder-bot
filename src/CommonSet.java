// Uses Pikalytics data. This doesnt include every pokemon in the usage list
public class CommonSet {
 
  private Item item;
  private Attack[] moveSet;
  private Ability ability;
  private Nature nature;
  private int[] EVs, stats;

  public void commonSet() {
    this.moveSet = new Attack[4];
    this.ability = new Ability("adaptability");
  }

  public void createSet(Pokemon pokemon) {
      initializeStats(pokemon);
  }

  public void initializeStats(Pokemon pokemon) {
      this.stats[0] = (pokemon.getHP() + 75 + calculateEVs(EVs[0]));
      this.stats[1] = (pokemon.getAttack() + 20 + calculateEVs(EVs[1]));
      this.stats[2] = (pokemon.getDefense() + 20 + calculateEVs(EVs[2]));
      this.stats[3] = (pokemon.getSpattack() + 20 + calculateEVs(EVs[3]));
      this.stats[4] = (pokemon.getSpdefense() + 20 + calculateEVs(EVs[4]));
      this.stats[5] = (pokemon.getSpeed() + 20 + calculateEVs(EVs[5]));
  }

  public int calculateEVs(int EVs) {
      int result = 0;

      if (EVs >= 4) {
          result = ((EVs % 8) + 1);
      }

      return result;
  }

  public Ability getAbility() {
    return this.ability;
  }

  public Attack[] getMoveSet() {
    return this.moveSet;
  }


}
