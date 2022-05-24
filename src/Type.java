// Credit to https://github.com/Hsaylor/pokemonDamageCalc-android/blob/master/calculatorcore/src/main/java/com/calculatorcore/Type.java
 
public class Type {
 
	private String name;
	private int index;

  public Type() {
    
  }
 
	private Type(String n, int i){
		name = n;
		index = i;
	}

	public Type(String name) {
		Type type = setType(name);
		this.name = type.name;
		this.index = type.index;
	}
 
	//2Darray for type weakness/resistance multipliers
	//
	//follows the pattern [normal, fire, water, electric, grass, ice, fighting, poison, ground, 
	//						flying, psychic, bug, rock, ghost, dragon, dark, steel, fairy]
 
	//get in the order of [defender][attacker]
 
	private static final double[][] weakChart = { 
		{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.5, 0, 1.0, 1.0, 0.5, 1.0}, 
		{1.0, 0.5, 0.5, 1.0, 2.0, 2.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 0.5, 1.0, 0.5, 1.0, 2.0, 1.0}, 
		{1.0, 2.0, 0.5, 1.0, 0.5, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 1.0, 2.0, 1.0, 0.5, 1.0, 1.0, 1.0},
		{1.0, 1.0, 2.0, 0.5, 0.5, 1.0, 1.0, 1.0, 0, 2.0, 1.0, 1.0, 1.0, 1.0, 0.5, 1.0, 1.0, 1.0},
		{1.0, 0.5, 2.0, 1.0, 0.5, 1.0, 0.5, 2.0, 0.5, 1.0, 1.0, 0.5, 2.0, 1.0, 0.5, 1.0, 0.5, 1.0},
		{1.0, 0.5, 0.5, 1.0, 2.0, 0.5, 1.0, 1.0, 2.0, 2.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 0.5, 1.0},
		{2.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 0.5, 1.0, 0.5, 0.5, 0.5, 2.0, 0, 1.0, 2.0, 2.0, 0.5},
		{1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 0.5, 0.5, 1.0, 1.0, 1.0, 0.5, 0.5, 1.0, 1.0, 0, 2.0},
		{1.0, 2.0, 1.0, 2.0, 0.5, 1.0, 1.0, 2.0, 1.0, 0, 1.0, 0.5, 2.0, 1.0, 1.0, 1.0, 2.0, 1.0},
		{1.0, 1.0, 1.0, 0.5, 2.0, 1.0, 2.0, 1.0, 1.0, 1.0, 1.0, 2.0, 0.5, 1.0, 1.0, 1.0, 0.5, 1.0},
		{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 2.0, 1.0, 1.0, 0.5, 1.0, 1.0, 1.0, 1.0, 0, 0.5, 1.0},
		{1.0, 0.5, 1.0, 1.0, 2.0, 1.0, 0.5, 0.5, 1.0, 0.5, 2.0, 1.0, 1.0, 1.0, 1.0, 2.0, 0.5, 0.5},
		{1.0, 2.0, 1.0, 1.0, 1.0, 2.0, 0.5, 1.0, 0.5, 2.0, 1.0, 2.0, 1.0, 1.0, 1.0, 1.0, 0.5, 1.0},
		{0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 2.0, 1.0, 0.5, 1.0, 1.0},
		{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 0.5, 0},
		{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 2.0, 1.0, 0.5, 1.0, 0.5},
		{1.0, 0.5, 0.5, 0.5, 1.0, 2.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 1.0, 0.5, 2.0},
		{1.0, 0.5, 1.0, 1.0, 1.0, 1.0, 2.0, 0.5, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 2.0, 0.5, 1.0}
		};
 
 
	//static types
 
	public static final Type NORMAL = new Type("Normal", 0);
	public static final Type FIRE = new Type("Fire", 1);
	public static final Type WATER = new Type("Water", 2);
	public static final Type ELECTRIC = new Type("Electric", 3);
	public static final Type GRASS = new Type("Grass", 4);
	public static final Type ICE = new Type("Ice", 5);
	public static final Type FIGHTING = new Type("Fighting", 6);
	public static final Type POISON = new Type("Poison", 7);
	public static final Type GROUND = new Type("Ground", 8);
	public static final Type FLYING = new Type("Flying", 9);
	public static final Type PSYCHIC = new Type("Psychic", 10);
	public static final Type BUG = new Type("Bug", 11);
	public static final Type ROCK = new Type("Rock", 12);
	public static final Type GHOST = new Type("Ghost", 13);
	public static final Type DRAGON = new Type("Dragon", 14);
	public static final Type DARK = new Type("Dark", 15);
	public static final Type STEEL = new Type("Steel", 16);
	public static final Type FAIRY = new Type("Fairy", 17);
 
	//getters for Types
 
	public String getName(){
		return name;
	}
 
	public int getIndex(){
		return index;
	}
 
	public double getSpecificWeaknessMultiplier(Type t2){
		return weakChart[index][t2.getIndex()];
	}
 
	public double getSpecificResistMultiplier(Type t2){
		return weakChart[t2.getIndex()][index];
	}
 
	public double calcDualTypeWeakness(Type def1, Type def2){
 
		if(def2 != null){		
			double damM = getSpecificWeaknessMultiplier(def1);
			damM = damM * weakChart[index][def2.getIndex()];
			return damM;		
		}
 
		else{
		return getSpecificWeaknessMultiplier(def1);
		}
 
	}
 
	public double calcDualTypeResist(Type second, Type attack){
 
		if(second != null){		
			double damM = getSpecificResistMultiplier(attack);
			damM = damM * weakChart[second.getIndex()][attack.getIndex()];
			return damM;		
		}
 
		else{
		return getSpecificResistMultiplier(attack);
		}
 
	}

  public Type setType(String type) {
    switch (type) {
      case ("Normal"):
        return Type.NORMAL;
      case ("Fire"):
        return Type.FIRE;
      case ("Water"):
        return Type.WATER;
      case ("Electric"):
        return Type.ELECTRIC;
      case ("Grass"):
        return Type.GRASS;
      case ("Ice"):
        return Type.ICE;
      case ("Fighting"):
        return Type.FIGHTING;
      case ("Poison"):
        return Type.POISON;
      case ("Ground"):
        return Type.GROUND;
      case ("Flying"):
        return Type.FLYING;
      case ("Psychic"):
        return Type.PSYCHIC;
      case ("Bug"):
        return Type.BUG;
      case ("Rock"):
        return Type.ROCK;
      case ("Ghost"):
        return Type.GHOST;
      case ("Dragon"):
        return Type.DRAGON;
      case ("Dark"):
        return Type.DARK;
      case ("Steel"):
        return Type.STEEL;
      case ("Fairy"):
        return Type.FAIRY;
      default:
        return null;
    }
  }
}