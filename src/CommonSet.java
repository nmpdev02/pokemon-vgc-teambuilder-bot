// Uses Pikalytics data. This doesnt include every pokemon in the usage list
public class CommonSet {
 
  private Item item;
  private Attack[] moveSet;
  private Ability ability;
  private Nature nature;
  private int[] EVs, stats;

  public CommonSet() {}

  public CommonSet(Attack[] moveSet, Ability ability, Item item, Nature nature, int[] EVs) {
    this.moveSet = moveSet;
    this.ability = ability;
    this.item = item;
    this.nature = nature;
    this.EVs = EVs;
    this.stats = new int[6];
  }

  public void initializeStats(Pokemon pokemon) throws NullPointerException {

    try {
      this.stats[0] = (pokemon.getHP() + 75 + calculateEVs(EVs[0]));
      this.stats[1] = (pokemon.getAttack() + 20 + calculateEVs(EVs[1]));
      this.stats[2] = (pokemon.getDefense() + 20 + calculateEVs(EVs[2]));
      this.stats[3] = (pokemon.getSpattack() + 20 + calculateEVs(EVs[3]));
      this.stats[4] = (pokemon.getSpdefense() + 20 + calculateEVs(EVs[4]));
      this.stats[5] = (pokemon.getSpeed() + 20 + calculateEVs(EVs[5]));
      for (int i = 0; i <= 5; i++) {
        this.stats[i] *= this.nature.getModifiers()[i];
      }
    }
    catch (NullPointerException e) {
        System.out.println("NULL POINTER EXCEPTION " + e.getMessage());
    }
  }

  public int calculateEVs(int EVs) {
      int result = 0;

      if (EVs >= 4) {
          result = ((EVs / 8) + 1);
      }

      return result;
  }

  public Ability getAbility() {
    return this.ability;
  }

  public Item getItem() {
    return this.item;
  }

  public Attack[] getMoveSet() {
    return this.moveSet;
  }

  public int getHPStat() {
    return this.stats[0];
  }

  public int getAttackStat() {
    return this.stats[1];
  }

  public int getDefenseStat() {
    return this.stats[2];
  }

  public int getSpattackStat() {
    return this.stats[3];
  }

  public int getSpdefenseStat() {
    return this.stats[4];
  }

  public int getSpeedStat() {
    return this.stats[5];
  }

  public Nature getNature() {
    return this.nature;
  }

  public int[] getEVs() {
    return this.EVs;
  }

}
