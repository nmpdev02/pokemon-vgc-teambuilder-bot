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

import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

public class Pokemon {

    private String name;
    private int hp, attack, defense, spattack, spdefense, speed, rbst;
    private double usage;
    private boolean physicalAttacker;
    private Type[] types;
    private CommonSet set;

    public Pokemon(String name, double usage) {
      this.name = name;
      this.usage = usage;
      types = new Type[2];
      set = new CommonSet();
    }

    public void createSet() throws IllegalArgumentException, IllegalAccessException {
      Item item = new Item();
      Field[] fields = Ability.class.getDeclaredFields();
      Ability ability = new Ability();
      String itemName, abilityName;
      Attack[] attacks = new Attack[4];
      Nature nature = new Nature();
      int[] EVs = new int[6];

      System.out.println("Creating set for: " + this.name);
      try {

        URL url = new URL("https://www.pikalytics.com/pokedex/ss/" + this.name);

        // read text returned by server
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

        String line;
        while ((line = in.readLine()) != null) { 

          /* SETTING TYPES */
          if (line.contains("pokedex-header-types") && line.contains("margin-block-start")) { 

            String[] pokemonTypes = StringUtils.substringsBetween(line, "type ", "</span>");

            if (!(this.name.equals("Sirfetch'd"))) {

            StringBuilder builder = new StringBuilder(pokemonTypes[0]);
            builder.delete(0, ((builder.length() / 2) + 1));
            Character upper = Character.toUpperCase(builder.charAt(0));
            String replace = "" + upper;
            builder.replace(0, 1, replace);

            this.types[0] = new Type().setType(builder.toString());

            if (pokemonTypes.length > 1) {

              builder = new StringBuilder(pokemonTypes[1]);
              upper = Character.toUpperCase(builder.charAt(0));
              builder.delete(0, ((builder.length() / 2) + 1));
              replace = "" + upper;
              builder.replace(0, 1, replace);

              this.types[1] = new Type().setType(builder.toString());

              }
            } else this.types[0] = Type.FIGHTING;
          }

          String stat;
          if (line.contains("Base Stats")) {

            if (!(this.name.equals("Sirfetch'd"))) {

            for (int i = 0; i < 10; i++) { // HP
              line = in.readLine();
            }
            stat = StringUtils.substringBetween(line, "20px;\">", "</div>");
            this.hp = Integer.parseInt(stat);

            for (int i = 0; i < 8; i++) { // ATTACK
              line = in.readLine();
            }
            stat = StringUtils.substringBetween(line, "20px;\">", "</div>");
            this.attack = Integer.parseInt(stat);

            for (int i = 0; i < 8; i++) { // DEFENSE
              line = in.readLine();
            }
            stat = StringUtils.substringBetween(line, "20px;\">", "</div>");
            this.defense = Integer.parseInt(stat);

            for (int i = 0; i < 8; i++) { // SP.ATTACK
              line = in.readLine();
            }
            stat = StringUtils.substringBetween(line, "20px;\">", "</div>");
            this.spattack = Integer.parseInt(stat);

            for (int i = 0; i < 8; i++) { // SP.DEFENSE
              line = in.readLine();
            }
            stat = StringUtils.substringBetween(line, "20px;\">", "</div>");
            this.spdefense = Integer.parseInt(stat);

            for (int i = 0; i < 8; i++) { // SPEED
              line = in.readLine();
            }
            stat = StringUtils.substringBetween(line, "20px;\">", "</div>");
            this.speed = Integer.parseInt(stat);

            if (this.name.equals("Regigigas")) {
              this.speed /= 2;
            }

            if (this.name.equals("Aegislash")) {
              this.attack = this.defense;
              this.spattack = this.spdefense;
            }

            this.setPhysicalAttacker();
            if (this.physicalAttacker) {
              this.rbst = (this.hp + this.defense + this.spdefense + this.speed + this.attack);
            } else this.rbst = (this.hp + this.defense + this.spdefense + this.speed + this.spattack);

            if (this.name.equals("Regigigas")) {
              this.rbst -= 180;
            }

            } else {
              this.hp = 62;
              this.attack = 135;
              this.defense = 95;
              this.spattack = 68;
              this.spdefense = 82;
              this.speed = 65;
            }
          }

          if (line.endsWith("Item") && !(line.contains("Add"))) { //ITEM SECTION

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

            if (this.name.equals("Ditto")) {
              Attack placeholder = new Attack();
              attacks[1] = placeholder;
              attacks[2] = placeholder;
              attacks[3] = placeholder;
            } else {

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
          }

          } else if (line.contains("EV Spreads -->")) { // EVs and Nature
            while (!(line.contains("pokedex-move-entry-new"))) {
              line = in.readLine();
            }
            line = in.readLine();

            //System.out.println(this.name + ", " + line);
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
    }
    catch (IllegalArgumentException e) {
        System.out.println("INVALID ABILITY " + e.getMessage());
    } 
    catch (IllegalAccessException e) {
        System.out.println("INVALID ABILITY " + e.getMessage());
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
          this.physicalAttacker = false;
          break;
        case ("Nidoking"):
          this.physicalAttacker = false;
          break;
        case ("Nidoqueen"):
          this.physicalAttacker = false;
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
      }
      return result;
    }

    public int statPoints() {
      return this.rbst;
    }
    
    public double calculateDamage(Pokemon opponent, Attack attack) {
      double attackingStat, defendingStat;
      double STAB = 1, AD, result = 0;
  
      if (attack.getPower() == 0) {
          return 50;
      }
  
      if (this.physicalAttacker) {
          attackingStat = this.set.getAttackStat();
          defendingStat = opponent.set.getDefenseStat();
          if (this.name.equals("Regigigas")) {
            attackingStat *= 0.5;
          }
      } else { 
          attackingStat = this.set.getSpattackStat();
          defendingStat = opponent.set.getSpdefenseStat();
          if (opponent.set.getItem().getName().equals("Assault Vest")) {
            defendingStat *= 1.4;
          }
      }

      if (opponent.set.getItem().getName().equals("Eviolite")) {
            defendingStat *= 1.4;
      }
  
      AD = (attackingStat/defendingStat);

      if (this.checkSTAB(attack)) {
          if (this.types[0].getName().equals(attack.getType().getName()) || this.types[1].getName().equals(attack.getType().getName())
            || this.set.getAbility().getName().equals("Protean") 
            || this.set.getAbility().getName().equals("Libero")) {

            if (this.set.getAbility().getName().equals("Adaptability")) {
              STAB = 2;
            } else STAB = 1.5;

          } else STAB = 1;
      }
  
      result = ((((22 * attack.getPower() * AD)/50)+2) * STAB * spreadMove(attack.getName()) * 
          /* this.itemMultipliers(opponent, attack) */ this.abilityMultipliers(opponent, attack) * 
          this.otherMultipliers(opponent, attack) * 
          attack.getType().calcDualTypeWeakness(opponent.types[0], opponent.types[1]));
        
      return result;
    }

    public double otherMultipliers(Pokemon opponent, Attack attack) {
      double result = 1;

      return result;
    }

    public double itemMultipliers(Pokemon opponent, Attack attack) {
      Item atkItem = this.set.getItem();
      Item defItem = opponent.set.getItem();
      Type attackType = attack.getType();
      double result = 1;

      if (defItem.getName().contains("Berry")) {
        if (defItem.getType() != null) {
          if ((attackType.calcDualTypeWeakness(opponent.types[0], opponent.types[1]) > 1) && attackType.getName().equals(defItem.getType().getName())) {
            result *= 0.5;
          }
        }
      }

      if (atkItem.getType() == null) {
        if (atkItem.getName().equals("Expert Belt")) {
          if (attackType.calcDualTypeWeakness(opponent.types[0], opponent.types[1]) > 1) {
            result *= 1.2;
          }
        } else if (atkItem.getName().contains("Choice")) {
          result *= 1.5; 
        } else if (atkItem.getName().equals("Thick Club")) {
          result *= 2;
        } else if (atkItem.getName().equals("Life Orb")) {
          result *= 1.3; 
        }
      } else if (attackType.getName().equals(atkItem.getType().getName())) {
        result *= 1.2;
      }

      return result;
    }

    public double intimidate(Pokemon opponent) {
      double result = 1;

      if (this.physicalAttacker) {
      if (opponent.set.getAbility().getName().equals("Intimidate")) {
        switch (this.set.getAbility().getName()) {
          case ("Full-Metal-Body"):
            return 1;
          case ("Clear Body"):
            return 1;
          case ("Oblivious"):
            return 1;
          case ("Inner Focus"):
            return 1;
          case ("White Smoke"):
            return 1;
          case ("Scrappy"):
            return 1;
          case ("Defiant"):
            return 1.5;
          case ("Competetive"):
            return 2;
          default:
            return 0.67;
        }
      }
    }

      return result;
    }

    public double abilityMultipliers(Pokemon opponent, Attack attack) {
      double result = 1;
      Ability attackAbility = this.set.getAbility();
      Ability opponentAbility = opponent.set.getAbility();
      Type attackType = attack.getType();

      double weather = this.weatherWars(opponent, attack);

      if (attackAbility.equals(Ability.NEUTRALIZING_GAS) || 
          opponentAbility.equals(Ability.NEUTRALIZING_GAS)) return 1;

      if (opponentAbility.getName().equals("Wonder Guard")) {
          if (attackType.calcDualTypeWeakness(Type.GHOST, Type.BUG) < 2) {
              result *= 0;
          }
      }

      if (opponentAbility.getBoost() == 0.75) { // Abilities like Prism Armor, Solid Rock
          if (attackType.calcDualTypeWeakness(opponent.types[0], opponent.types[1]) >= 2) {
              result *= 0.75;
          }
      } else if (opponentAbility.getBoost() == 0.5) { // Multiscale, Shadow Shield
          result *= 0.5;
      }

      if (opponentAbility.getTypeResist() != null && opponentAbility.getTypeBoost() == null) { // Abilities like Volt Absorb, Storm Drain, etc.
          if (attackType.equals(opponentAbility.getTypeResist())) {
              result *= opponentAbility.getBoost();
          }
      }

      if (attackAbility.getName().equals("Water Bubble")) {
        if (attackType.equals(Type.WATER)) {
          result *= 1.5;
        }
      }

      if (opponentAbility.getName().equals("Water Bubble")) {
        if (attackType.equals(Type.FIRE)) {
          result *= 0.5;
        }
      }

      if (!(attackAbility.getName().equals("Drizzle")) && !(attackAbility.getName().equals("Drought"))) {
      if (attackAbility.getTypeBoost() != null) {
          if (attackType.getName().equals(attackAbility.getTypeBoost().getName())) {
              result *= attackAbility.getBoost();
          }
      } else if (attackAbility.getBoost() > 1) { 
          if (!(attackAbility.getName().equals("Defiant") || attackAbility.getName().equals("Competetive"))) {
            result *= attackAbility.getBoost();
          }
      }
    }

      result *= weather;

      result *= intimidate(opponent);

      return result;
    }
    
  public double spreadMove(String attackName) {
    switch (attackName) {
        case ("earthquake"):
            return 0.75;
        case ("astral-barrage"):
            return 0.75;
        case ("glacial-lance"):
            return 0.75;
        case ("rock-slide"):
            return 0.75;
        case ("surf"):
            return 0.75;
        case ("muddy-water"):
            return 0.75;
        case ("blizzard"):
            return 0.75;
        case ("eruption"):
            return 0.75;
        case ("brutal-swing"):
            return 0.75;
        case ("water-spout"):
            return 0.75;
        case ("precipice-blades"):
            return 0.75;
        case ("origin-pulse"):
            return 0.75;
        case ("bulldoze"):
            return 0.75;
        case ("discharge"):
            return 0.75;
        case ("heat Wave"):
            return 0.75;
        case ("snarl"):
            return 0.75;
        case ("clanging-scales"):
            return 0.75;
        case ("dazzling-gleam"):
            return 0.75;
        case ("dragon-energy"):
            return 0.75;
        case ("thousand-arrows"):
            return 0.75;
        case ("icy-wind"):
            return 0.75;
        case ("thousand-waves"):
            return 0.75;
        case ("overdrive"):
            return 0.75;
        case ("burning-jealousy"):
            return 0.75;
        default:
            return 1;
    }
   
  }

  public double weatherWars(Pokemon opponent, Attack attack) {
      double result = 1;

      Ability ability1 = this.set.getAbility(), ability2 = opponent.set.getAbility();

      if (!(ability1.getName().equals("Air Lock") || ability2.getName().equals("Air Lock") ||
      ability1.getName().equals("Cloud Nine") || ability2.getName().equals("Cloud Nine"))) {

          if (ability1.getName().equals("Drought") || ability1.getName().equals("Sand Stream") || 
          ability1.getName().equals("Snow Warning") || ability1.getName().equals("Drizzle")) {

              if (ability2.getName().equals("Drizzle") || ability2.getName().equals("Sand Stream") || 
                  ability2.getName().equals("Snow Warning") || ability2.getName().equals("Drought")) {
                  if (this.set.getSpeedStat() <= opponent.set.getSpeedStat()) {
                      result = weatherBoost(ability1, attack);
                  } else result = weatherBoost(ability2, attack);
              } else result = weatherBoost(ability1, attack);
          } else if (ability2.getName().equals("Drizzle") || ability2.getName().equals("Drought")) {
              result = weatherBoost(ability2, attack);
          }
      }

      return result;
  }

  public double weatherBoost(Ability ability, Attack attack) {
      double result = 1;

      if (ability.getName().equals("Drizzle")) { // DRIZZLE
          if (attack.getType().getName().equals("Water")) {
              result = 1.5;
          } else if (attack.getType().getName().equals("Fire")) {
              result = 0.5;
          }
      } else if (ability.getName().equals("Drought")) { // DROUGHT
          if (attack.getType().getName().equals("Fire")) {
              result = 1.5;
          } else if (attack.getType().getName().equals("Water")) {
              result = 0.5;
          }
      }
      return result;
  }
  
  public double offensivePotential(Pokemon opponent) {
      Attack[] moveSet = this.set.getMoveSet();
      double[] damage = new double[4];
  
      for (int i = 0; i < moveSet.length; i++) {
          if (moveSet[i] != null) {
          damage[i] = this.calculateDamage(opponent, moveSet[i]);
          } else damage[i] = 35;
      }

      double max = 0;
      for (int counter = 0; counter < damage.length; counter++) {

        if (damage[counter] > max) {
          max = damage[counter];
        }   

     }
  
      return max;
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
  name = builder.toString().replace(" ", "-");
  name = name.toLowerCase();
  name = Bot.cleanTextContent(name);
  name = StringEscapeUtils.escapeXml11(name);

  Iterator<Attack> it = attacks.iterator();
  while(it.hasNext()) {
    Attack attack = it.next();

    if (attack.getName().equals(name)) {
      result = attack;
    }
  }

  if (result.getName() == null) {
    System.out.println("INVALID PARSING FOR: " + name);
  }
  return result;
}

public Nature setNature(String line) {
  String name = StringUtils.substringBetween(line, "block;\">", "</div>");
  Nature nature = new Nature(name);
  nature.applyModifiers(name);

  return nature;
}

public int[] setEVs(String line) {
  String[] EVs = StringUtils.substringsBetween(line, "block;\">", "</div>");
  int[] result = new int[6];
  for (int i = 0; i <= 5; i++) {
    
    StringBuilder builder = new StringBuilder(EVs[i]);
    if (i != 5) {
    builder.deleteCharAt(builder.length() - 1);
    String temp = builder.toString();
    result[i] = Integer.parseInt(temp);
    } else result[5] = Integer.parseInt(EVs[5]);
  }

  return result;
}

public boolean checkSTAB(Attack attack) {
  boolean result = false;

    if (this.types[1] != null) {
      if (this.types[0].getName().equals(attack.getType().getName()) && attack.getPower() > 0) {
        result = true;
      } else if (this.types[1].getName().equals(attack.getType().getName()) && attack.getPower() > 0) {
        result = true;
      }
    } else {
        if (this.types[0].getName().equals(attack.getType().getName()) && attack.getPower() > 0) {
        result = true;
        }
      }

  return result;
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

public CommonSet getSet() {
  return this.set;
}

public Type getType1() {
  return this.types[0];
}

public Type getType2() {
  return this.types[1];
}

}
