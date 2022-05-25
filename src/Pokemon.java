import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

public class Pokemon {

    private String name, ability1, ability2, hiddenAbility;
    private int hp, attack, defense, spattack, spdefense, speed, rbst;
    private double usage;
    private boolean physicalAttacker, gmax, restricted;
    private Type[] types;
    private CommonSet set;

    public Pokemon(String name, double usage) {
      this.name = name;
      this.usage = usage;
      types = new Type[2];
      set = new CommonSet();
    }

    public void setupPokemon(String[] line) throws Exception {

      // Determine if the pokemon is a restricted legendary
      if (line[6].contains("Legendary") && !(line[6].contains("Sub"))) {
        this.restricted = true;
      } else this.restricted = false;

      // Set primary and secondary types
      this.types[0] = new Type(line[9]);
      if (line[10].equals("NULL")) {
        this.types[1] = null;
      } else this.types[1] = new Type(line[10]);

      // Set ability 1, 2, and hidden ability
      this.ability1 = line[11];
      this.ability2 = line[13];
      this.hiddenAbility = line[15];

      // Set base stats
      this.hp = Integer.parseInt(line[24]);
      this.attack = Integer.parseInt(line[25]);
      this.defense = Integer.parseInt(line[26]);
      this.spattack = Integer.parseInt(line[27]);
      this.spdefense = Integer.parseInt(line[28]);
      this.speed = Integer.parseInt(line[29]);

      // Determine if physical or special attacker to calculate real base stat total
      setPhysicalAttacker();
      if (this.physicalAttacker) {
        this.rbst = (Integer.parseInt(line[30]) - this.spattack);
      } else this.rbst = (Integer.parseInt(line[30]) - this.attack);

      createSet();
      
    }

    public void initializeDetails(String[] columns) throws Exception {
      String fullname = columns[2];
      Iterator<Pokemon> it = listedPokemon.iterator();
  
      if (!columns[4].contains("NULL")) {  
        if (columns[4].contains(" ")) {
          StringBuilder builder2 = new StringBuilder(columns[4]);
          builder2.delete(builder2.indexOf(" "), builder2.length());

            Pokemon pokemon = it.next();
            if (pokemon.getName().contains(fullname) && pokemon.getName().contains(builder2.toString())) {
              pokemon.setupPokemon(columns);
              break;
          }
        
        } else {
          fullname = (fullname + "-" + columns[4]);
          if (fullname.contains("Indeedee")) fullname = "Indeedee-F";
          while(it.hasNext()) {
            Pokemon pokemon = it.next();
            if (pokemon.getName().equals(fullname)) {
              pokemon.setupPokemon(columns);
              break;
            }
          }
        } 
      } else {           
        while(it.hasNext()) {
          Pokemon pokemon = it.next();
          if (pokemon.getName().equals(fullname)) {
            pokemon.setupPokemon(columns);
            break;
          }
        }
      }
  
    }

    public void createSet() throws IllegalArgumentException, IllegalAccessException {
      Item item = new Item();
      Field[] fields = Ability.class.getDeclaredFields();
      Ability ability = new Ability();
      String itemName, abilityName;
      Attack[] attacks = new Attack[4];
      Nature nature = new Nature();
      int[] EVs = new int[6];

      try {

        URL url = new URL("https://www.pikalytics.com/pokedex/ss/" + this.name);

        // read text returned by server
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

        String line;
        while ((line = in.readLine()) != null) { //ITEM SECTION
          // First, create the move set
          if (line.endsWith("Item")) {

            for (int i = 0; i < 11; i++) {
              line = in.readLine();
            }
            itemName = StringUtils.substringBetween(line, "block;\">", "</div>");
            item = setItem(itemName);

          } else if (line.endsWith("Ability")) { // ABILITY SECTION

            for (int i = 0; i < 6; i++) {
              line = in.readLine();
            }
            abilityName = StringUtils.substringBetween(line, "block;\">", "</div>");
            //REMOVE ME?
            if (abilityName == null || abilityName.equals("")) {
              System.out.println("ERROR PARSING ABILITY");
              break;
            }
            for (Field f : fields) {
              if (f.getType().equals(Ability.class)) {
                Ability temp = (Ability) f.get(null);
                if (temp.getName().equals(abilityName)) {
                  ability = temp;
                }
              }
            }

          } else if (line.contains("\">Moves")) { // MOVES SECTION

            for (int i = 0; i < 5; i++) {
              line = in.readLine();
            }
            attacks[0] = setAttack(line);

            for (int i = 0; i < 6; i++) {
              line = in.readLine();
            }
            attacks[1] = setAttack(line);

            for (int i = 0; i < 6; i++) {
              line = in.readLine();
            }
            attacks[2] = setAttack(line);

            for (int i = 0; i < 6; i++) {
              line = in.readLine();
            }
            attacks[3] = setAttack(line);

          } else if (line.contains("EV Spreads -->")) {
            for (int i = 0; i < 6; i++) {
              line = in.readLine();
            }

            nature = setNature(line);
            line = in.readLine();
            EVs = setEVs(line);
          }
          
        }
        in.close();

    }
    catch (MalformedURLException e) {
        System.out.println("Malformed URL: " + e.getMessage());
    }
    catch (IOException e) {
        System.out.println("There is no set for this Pokemon");
    }
    catch (IllegalArgumentException e) {
        System.out.println("INVALID ABILITY");
    } 
    catch (IllegalAccessException e) {
        System.out.println("INVALID ABILITY");
    }

    CommonSet set = new CommonSet(attacks, ability, item, nature, EVs);
    this.set = set;
    }
  
    public void setPhysicalAttacker() {
  
      switch (this.name) {
  
        // Hard coding cases where pokemon dont use their best attacking stat or they are equal but use physical attacks
        case ("Thundurus"):
          this.physicalAttacker = true;
          break;
        case ("Landorus"):
          this.physicalAttacker = true;
          break;
        default:
          if (this.attack > this.spattack) {
            this.physicalAttacker = true;
          } else this.physicalAttacker = false;
      }
  
    }

    public String getName() {
      return this.name;
    }

    public double getUsage() {
      return this.usage;
    }

    public String getDetails() {
      String result = "";

      if (this.types[0] != null) {
      if (this.types[1] == null) {
        result += ("Type: " + this.types[0].getName() + " ");
      } else {
        result += ("Types: " + this.types[0].getName() + ", " + this.types[1].getName() + " ");
      }

      if (ability2.equals("NULL") && hiddenAbility.equals("NULL")) {
        result += ("Ability: " + this.ability1 + " ");
      } else if (this.ability2.equals("NULL")) {
        result += ("Abilities: " + this.ability1 + ", " + this.hiddenAbility + " ");
      } else if (this.hiddenAbility.equals("NULL")) {
        result += ("Abilities: " + this.ability1 + ", " + this.ability2 + " ");
      } else {
      result += ("Abilities: " + this.ability1 + ", " + this.ability2 + ", " + this.hiddenAbility + " ");
      }
    }
      return result;
    }

    public int statPoints() {
      return (this.rbst + this.speed);
    }
    
    public int calculateDamage(Pokemon opponent, Attack attack) {
      int attackingStat, defendingStat;
      double STAB, AD, result = 0;
  
      if (attack.getPower() == 0) {
          return 100;
      }
  
      if (this.physicalAttacker) {
          attackingStat = this.set.getAttackStat();
          defendingStat = opponent.set.getDefenseStat();
      } else { 
          attackingStat = this.set.getSpattackStat();
          defendingStat = opponent.set.getSpdefenseStat();
      }
  
      AD = (attackingStat/defendingStat);

      if (checkSTAB(attack)) {
          if (pokemon.types[0].equals(attack.getType()) || pokemon.types[1].equals(attack.getType())
            || pokemon.set.getAbility().getName().equals("Protean") 
            || pokemon.set.getAbility().getName().equals("Libero")) {

            if (pokemon.set.getAbility().getName().equals("Adaptability")) {
              STAB = 2;
            } else STAB = 1.5;

          } else STAB = 1;
      }
  
      result = (((22 * attack.getPower() * AD)/50)+2) * STAB * spreadMove(attack.getName()) * 
          itemMultiplier(this.getItem()) * abilityMultiplier(this.getAbility() * otherMultiplier(this));
        
      return result;
    }
    
  public double spreadMove(String attackName) {
    switch (attackName) {
        case ("Earthquake"):
            return 0.75;
        case ("Astral Barrage"):
            return 0.75;
        case ("Glacial Lance"):
            return 0.75;
        case ("Rock Slide"):
            return 0.75;
        case ("Surf"):
            return 0.75;
        case ("Muddy Water"):
            return 0.75;
        case ("Blizzard"):
            return 0.75;
        case ("Eruption"):
            return 0.75;
        case ("Brutal Swing"):
            return 0.75;
        case ("Water Spout"):
            return 0.75;
        case ("Precipice Blades"):
            return 0.75;
        case ("Origin Pulse"):
            return 0.75;
        case ("Bulldoze"):
            return 0.75;
        case ("Discharge"):
            return 0.75;
        case ("Heat Wave"):
            return 0.75;
        case ("Snarl"):
            return 0.75;
        case ("Clanging Scales"):
            return 0.75;
        case ("Dazzling Gleam"):
            return 0.75;
        case ("Dragon Energy"):
            return 0.75;
        case ("Thousand Arrows"):
            return 0.75;
        case ("Icy Wind"):
            return 0.75;
        case ("Thousand Waves"):
            return 0.75;
        case ("Overdrive"):
            return 0.75;
        case ("Burning Jealousy"):
            return 0.75;
        default:
            return 1;
    }
   
  }
  
  public double offensivePotential(Pokemon opponent) {
      Attack[] moveSet = this.set.getMoveSet();
      double[] damage = new double[4];
  
      for (int i = 0; i < moveSet.length; i++) {
          damage[i] = calculateDamage(this, opponent, moveSet[i]);
      }

      double max = 0;
      for (int counter = 1; counter < damage.length; counter++) {

        if (damage[counter] > max) {
          max = damage[counter];
        }   

     }
  
      return max;
  }

  public double gmaxPoints() {
    if (this.gmax == true) {
        return 100.0;
    } else return 0;
}

public Item setItem(String name) {

  switch (name) {
    case ("Babiri Berry"):
      return Item.BABIRI;
    case ("Charti Berry"):
      return Item.CHARTI;
    case ("Chilan Berry"):
      return Item.CHILAN;
    case ("Chople Berry"):
      return Item.CHOPLE;
    case ("Coba Berry"):
      return Item.COBA;
    case ("Colbur Berry"):
      return Item.COLBUR;
    case ("Haban Berry"):
      return Item.HABAN;
    case ("Kasib Berry"):
      return Item.KASIB;
    case ("Kebia Berry"):
      return Item.KEBIA;
    case ("Occa Berry"):
      return Item.OCCA;
    case ("Passho Berry"):
      return Item.PASSHO;
    case ("Payapa Berry"):
      return Item.PAYAPA;
    case ("Rindo Berry"):
      return Item.RINDO;
    case ("Roseli Berry"):
      return Item.ROSELI;
    case ("Shuca Berry"):
      return Item.SHUCA;
    case ("Tanga Berry"):
      return Item.TANGA;
    case ("Wacan Berry"):
      return Item.WACAN;
    case ("Yache Berry"):
      return Item.YACHE;
    case ("Thick Club"):
      return Item.THICK_CLUB;
    case ("Choice Band"):
      return Item.CHOICE_BAND;
    case ("Choice Specs"):
      return Item.CHOICE_SPECS;
    case ("Life Orb"):
      return Item.LIFE_ORB;
    case ("Mystic Water"):
      return Item.MYSTIC_WATER;
    case ("Charcoal"):
      return Item.CHARCOAL;
    case ("Miracle Seed"):
      return Item.MIRACLE_SEED;
    case ("Magnet"):
      return Item.MAGNET;
    case ("Pixie Plate"):
      return Item.PIXIE_PLATE;
    case ("Sharp Beak"):
      return Item.SHARP_BEAK;
    case ("Spell Tag"):
      return Item.SPELL_TAG;
    case ("Black Glasses"):
      return Item.BLACK_GLASSES;
    case ("Dragon Fang"):
      return Item.DRAGON_FANG;
    case ("Soft Sand"):
      return Item.SOFT_SAND;
    case ("Never-Melt Ice"):
      return Item.NEVERMELT_ICE;
    case ("Metal Coat"):
      return Item.METAL_COAT;
    case ("Hard Stone"):
      return Item.HARD_STONE;
    case ("Twisted Spoon"):
      return Item.TWISTED_SPOON;
    case ("Silver Powder"):
      return Item.SILVER_POWDER;
    case ("Black Belt"):
      return Item.BLACK_BELT;
    case ("Poison Barb"):
      return Item.POISON_BARB;
    case ("Silk Scarf"):
      return Item.SILK_SCARF;
    case ("Wave Incense"):
      return Item.WAVE_INCENSE;
    case ("Sea Incense"):
      return Item.SEA_INCENSE;
    case ("Rose Incense"):
      return Item.ROSE_INCENSE;
    case ("Assault Vest"):
      return Item.ASSAULT_VEST;
    case ("Eviolite"):
      return Item.EVIOLITE;
    default:
      return new Item(name, 1, 1, null);
    }

}

public Attack setAttack(String line) {
  Attack result = new Attack();
  List<Attack> attacks = Bot.allAttacks;
  
  String name = StringUtils.substringBetween(line, "block;\">", "</div>");
  StringBuilder builder = new StringBuilder(name);
  String upper = "" + Character.toLowerCase(builder.charAt(0));
  builder.replace(0, 1, upper);
  name = builder.toString().replace(" ", "-");

  Iterator<Attack> it = attacks.iterator();
  while(it.hasNext()) {
    Attack attack = it.next();

    if (attack.getName().equals(name)) {
      result = attack;
    }
  }

  if (result.getName() == null) { System.out.println("INVALID PARSING FOR ATTACK"); }
  return result;
}

public Nature setNature(String line) {
  String name = StringUtils.substringBetween(line, "block;\">", "</div>");
  Nature nature = new Nature(name);

  return nature;
}

public int[] setEVs(String line) {
  String[] EVs = StringUtils.substringsBetween(line, "block;\">", "</div>");
  int[] result = new int[6];
  for (int i = 0; i <= 5; i++) {
    StringBuilder builder = new StringBuilder(EVs[i]);
    builder.delete((builder.length() - 2), (builder.length() - 1));
    String temp = builder.toString();
    result[i] = Integer.parseInt(temp);
  }

  return result;
}

public boolean checkSTAB(Attack attack) {
    if (this.types[0].equals(attack.getType()) && attack.getPower() > 0) {
        return true;
    } else if (this.types[1].equals(attack.getType()) && attack.getPower() > 0) {
        return true;
    } else return false;
}

public double checkWeakness(Attack attack, Pokemon opponent) {
    double result = 1;

    if (opponent.types[1] == null) {
        result *= attack.getType().getSpecificWeaknessMultiplier(opponent.types[0]);
    } else result *= attack.getType().calcDualTypeWeakness(opponent.types[0], opponent.types[1]);

    return result;
}

public int getHP() {
  return this.hp;
}

public int getAttack() {
  return this.attack;
}

public int getDefense() {
  return this.defense;
}
  
public int getSpattack() {
  return this.spattack;
}

public int getSpdefense() {
  return this.spdefense;
}

public int getSpeed() {
  return this.speed;
}

}
