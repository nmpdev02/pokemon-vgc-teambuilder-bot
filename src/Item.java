public class Item {
  
  private String name;
  private double damageBoost, defenseBoost;
  private Type type;

  public Item() {}

  public Item(String name) {
    this.name = name;
  }

  public Item(String name, double damageBoost, double defenseBoost, Type type) {
    this.name = name;
    this.damageBoost = damageBoost;
    this.defenseBoost = defenseBoost;
    this.type = type;
  }

  /* type berries */
  public static final Item BABIRI = new Item("Babiri Berry", 1.0, 0.5, Type.STEEL);
  public static final Item CHARTI = new Item("Chart Berry", 1.0, 0.5, Type.ROCK);
  public static final Item CHILAN = new Item("Chilan Berry", 1.0, 0.5, Type.NORMAL);
  public static final Item CHOPLE = new Item("Chople Berry", 1.0, 0.5, Type.FIGHTING);
  public static final Item COBA = new Item("Coba Berry", 1.0, 0.5, Type.FLYING);
  public static final Item COLBUR = new Item("Colbur Berry", 1.0, 0.5, Type.DARK);
  public static final Item HABAN = new Item("Haban Berry", 1.0, 0.5, Type.DRAGON);
  public static final Item KASIB = new Item("Kasib Berry", 1.0, 0.5, Type.GHOST);
  public static final Item KEBIA = new Item("Kebia Berry", 1.0, 0.5, Type.POISON);
  public static final Item OCCA = new Item("Occa Berry", 1.0, 0.5, Type.FIRE);
  public static final Item PASSHO = new Item("Passho Berry", 1.0, 0.5, Type.WATER);
  public static final Item PAYAPA = new Item("Payapa Berry", 1.0, 0.5, Type.PSYCHIC);
  public static final Item RINDO = new Item("Rindo Berry", 1.0, 0.5, Type.GRASS);
  public static final Item ROSELI = new Item("Roseli Berry", 1.0, 0.5, Type.FAIRY);
  public static final Item SHUCA = new Item("Shuca Berry", 1.0, 0.5, Type.GROUND);
  public static final Item TANGA = new Item("Tanga Berry", 1.0, 0.5, Type.BUG);
  public static final Item WACAN = new Item("Wacan Berry", 1.0, 0.5, Type.ELECTRIC);
  public static final Item YACHE = new Item("Yache Berry", 1.0, 0.5, Type.ICE);

  /* Damage boosting items */
  public static final Item THICK_CLUB = new Item("Thick Club", 2.0, 1, null);
  public static final Item CHOICE_BAND = new Item("Choice Band", 1.5, 1, null);
  public static final Item CHOICE_SPECS = new Item("Choice Specs", 1.5, 1, null);
  public static final Item LIFE_ORB = new Item("Life Orb", 1.3, 1, null);
  public static final Item MYSTIC_WATER = new Item("Mystic Water", 1.2, 1, Type.WATER);
  public static final Item SEA_INCENSE = new Item("Sea Incense", 1.2, 1, Type.WATER);
  public static final Item WAVE_INCENSE = new Item("Wave Incense", 1.2, 1, Type.WATER);
  public static final Item CHARCOAL = new Item("Charcoal", 1.2, 1, Type.FIRE);
  public static final Item MIRACLE_SEED = new Item("Miracle Seed", 1.2, 1, Type.GRASS);
  public static final Item ROSE_INCENSE = new Item("Rose Incense", 1.2, 1, Type.GRASS);
  public static final Item MAGNET = new Item("Magnet", 1.2, 1, Type.ELECTRIC);
  public static final Item PIXIE_PLATE = new Item("Pixie Plate", 1.2, 1, Type.FAIRY);
  public static final Item SHARP_BEAK = new Item("Sharp Beak", 1.2, 1, Type.FLYING);
  public static final Item SPELL_TAG = new Item("Spell Tag", 1.2, 1, Type.GHOST);
  public static final Item BLACK_GLASSES = new Item("Black Glasses", 1.2, 1, Type.DARK);
  public static final Item DRAGON_FANG = new Item("Dragon Fang", 1.2, 1, Type.DRAGON);
  public static final Item SOFT_SAND = new Item("Soft Sand", 1.2, 1, Type.GROUND);
  public static final Item NEVERMELT_ICE = new Item("Never-Melt Ice", 1.2, 1, Type.ICE);
  public static final Item METAL_COAT = new Item("Metal Coat", 1.2, 1, Type.STEEL);
  public static final Item HARD_STONE = new Item("Hard Stone", 1.2, 1, Type.ROCK);
  public static final Item TWISTED_SPOON = new Item("Twisted Spoon", 1.2, 1, Type.PSYCHIC);
  public static final Item SILVER_POWDER = new Item("Silver Powder", 1.2, 1, Type.BUG);
  public static final Item BLACK_BELT = new Item("Black Belt", 1.2, 1, Type.FIGHTING);
  public static final Item POISON_BARB = new Item("Poison Barb", 1.2, 1, Type.POISON);
  public static final Item SILK_SCARF = new Item("Silk Scarf", 1.2, 1, Type.NORMAL);
  
  /* Other Iteams */
  public static final Item ASSAULT_VEST = new Item("Assault Vest");
  public static final Item EVIOLITE = new Item("Eviolite");

}
