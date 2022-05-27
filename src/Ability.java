import java.lang.reflect.Field;

public class Ability {
  
  private String name;
  private double attackBoost, defenseBoost;
  private Type boost, resist;

  public Ability() {
    this.name = "uselessAbility";
    this.attackBoost = 1;
    this.defenseBoost = 1;
  }

  public Ability(String name) {
    this.name = name;
  }

  public Ability(String name, double attackBoost, double defenseBoost, Type boost, Type resist) {
    this.name = name;
    this.attackBoost = attackBoost;
    this.defenseBoost = defenseBoost;
    this.boost = boost;
    this.resist = resist;
  }

  /* ATTACK MODIFIERS */
  public static final Ability INTREPID_SWORD = new Ability("Intrepid Sword", 1.5, 1, null, null);
  public static final Ability HUGE_POWER = new Ability("Huge Power", 2.0, 1, null, null);
  public static final Ability PURE_POWER = new Ability("Pure Power", 2.0, 1, null, null);
  public static final Ability SLOW_START = new Ability("Slow Start", 0.5, 1, null, null);
  public static final Ability GORILLA_TACTICS = new Ability("Gorilla Tactics", 1.5, 1, null, null);
  public static final Ability HUSTLE = new Ability("Hustle", 1.5, 1, null, null);
  public static final Ability STRONG_JAW = new Ability("Strong Jaw", 1.5, 1, null, null);
  public static final Ability SHEER_FORCE = new Ability("Sheer Force", 1.25, 1, null, null);
  public static final Ability TECHNICIAN = new Ability("Technician", 1.5, 1, null, null);
  public static final Ability SUPER_LUCK = new Ability("Super Luck", 1.5, 1, null, null);

  /* OPPONENTS ATTACK MODIFIERS */
  public static final Ability INTIMIDATE = new Ability("Intimidate", 0.66, 1, null, null);
  public static final Ability PRISM_ARMOR = new Ability("Prism Armor", 0.75, 1, null, null);
  public static final Ability SOLID_ROCK = new Ability("Solid Rock", 0.75, 1, null, null);
  public static final Ability MULTISCALE = new Ability("Multiscale", 0.75, 1, null, null);
  public static final Ability SHADOW_SHIELD = new Ability("Shadow Shield", 0.75, 1, null, null);

  /* DEFENSE MODIFIERS */
  public static final Ability DAUNTLESS_SHIELD = new Ability("Dauntless Shield", 1, 1.5, null, null);
  public static final Ability FUR_COAT = new Ability("Fur Coat", 1, 2, null, null);
  public static final Ability FLUFFY = new Ability("Fluffy", 1, 2, null, null);
  public static final Ability SAND_STREAM = new Ability("Sand Stream", 1, 1.5, Type.ROCK, null);

  /* CHECK FOR INTIMIDATE FOR THESE ABILITIES */
  public static final Ability DEFIANT = new Ability("Defiant", 1.5, 1, null, null); 
  public static final Ability COMPETETIVE = new Ability("Competetive", 2.0, 1, null, null);
  public static final Ability FULL_METAL_BODY = new Ability("Full Metal Body", 1, 1, null, null);
  public static final Ability CLEAR_BODY = new Ability("Clear Body", 1, 1, null, null);
  public static final Ability OBLIVIOUS = new Ability("Oblivious", 1, 1, null, null);
  public static final Ability INNER_FOCUS = new Ability("Inner Focus", 1, 1, null, null);
  public static final Ability WHITE_SMOKE = new Ability("White Smoke", 1, 1, null, null);
  // Check for if attack is normal or fighting and if opponent is ghost
  public static final Ability SCRAPPY = new Ability("Scrappy", 1, 1, null, null); 

  /* TYPE RELATED ABILITIES */
  public static final Ability MISTY_SURGE = new Ability("Misty Surge", 1, 1, null, Type.DRAGON);
  public static final Ability ELECTRIC_SURGE = new Ability("Electric Surge", 1.33, 1, Type.ELECTRIC, null);
  public static final Ability GRASSY_SURGE = new Ability("Grassy Surge", 1.33, 1, Type.GRASS, null);
  public static final Ability PSYCHIC_SURGE = new Ability("Psychic Surge", 1.33, 1, Type.PSYCHIC, null);
  public static final Ability TRANSITOR = new Ability("Transitor", 1.5, 1, Type.ELECTRIC, null);
  public static final Ability DRAGONS_MAW = new Ability("Dragon's Maw", 1.5, 1, Type.DRAGON, null);
  public static final Ability DARK_AURA = new Ability("Dark Aura", 1.5, 1, Type.DARK, null);
  public static final Ability FAIRY_AURA = new Ability("Fairy Aura", 1.5, 1, Type.FAIRY, null);
  public static final Ability WATER_BUBBLE = new Ability("Water Bubble", 1.5, 0.5, Type.WATER, Type.FIRE);
  public static final Ability DRIZZLE = new Ability("Drizzle", 1.5, 0.5, Type.WATER, Type.FIRE);
  public static final Ability DROUGHT = new Ability("Drought", 1.5, 0.5, Type.FIRE, Type.WATER);
  public static final Ability STORM_DRAIN = new Ability("Storm Drain", 0, 1, null, Type.WATER);
  public static final Ability WATER_ABSORB = new Ability("Water Absorb", 0, 1, null, Type.WATER);
  public static final Ability VOLT_ABSORB = new Ability("Volt Absorb", 0, 1, null, Type.ELECTRIC);
  public static final Ability LIGHTNING_ROD = new Ability("Lightning Rod", 0, 1, null, Type.ELECTRIC);
  public static final Ability FLASH_FIRE = new Ability("Flase Fire", 0, 1, null, Type.FIRE);
  public static final Ability SAP_SIPPER = new Ability("Sap Sipper", 0, 1, null, Type.GRASS);
  public static final Ability LEVITATE = new Ability("Levitate", 0, 1, null, Type.GROUND);
  public static final Ability DRY_SKIN = new Ability("Dry Skin", 0, 1, null, Type.WATER);

  /* MISC. ABILITIES */
  public static final Ability NEUTRALIZING_GAS = new Ability("Neutralizing Gas", 1, 1, null, null);
  public static final Ability AIR_LOCK = new Ability("Air Lock", 1, 1, null, null);
  public static final Ability CLOUD_NINE = new Ability("Cloud Nine", 1, 1, null, null);
  public static final Ability ADAPTABILITY = new Ability("Adaptability", 1, 1, null, null);
  public static final Ability PROTEAN = new Ability("Protean", 1, 1, null, null);
  public static final Ability LIBERO = new Ability("Libero", 1, 1, null, null);
  public static final Ability WONDER_GUARD = new Ability("Wonder Guard", 1, 1, null, null);
  public static final Ability THICK_FAT = new Ability("Thick Fat", 1, 1, null, null);
  public static final Ability STANCE_CHANGE = new Ability("Stance Change", 1, 1, null, null);

  public String getName() {
    return this.name;
  }

  public double getBoost() {
    return this.attackBoost;
  }

  public double getResist() {
    return this.defenseBoost;
  }

  public Type getTypeBoost() {
    return this.boost;
  }

  public Type getTypeResist() {
    return this.resist;
  }

}
