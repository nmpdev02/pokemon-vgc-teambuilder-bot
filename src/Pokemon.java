import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

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
    }

    public void setupPokemon(String[] line) throws Exception {

      // Determine if the pokemon is a restricted legendary
      if (line[6].contains("Legendary") && !(line[6].contains("Sub"))) {
        this.restricted = true;
      } else this.restricted = false;

      // Set primary and secondary types
      this.types[0] = setType(line[9]);
      this.types[1] = setType(line[10]);

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
    
    public int calculateDamage(Pokemon pokemon, Pokemon opponent, Attack attack) {
      int result = 0, attackingStat, defendingStat, AD;
      double STAB = 0;
  
      if (attack.getPower() == 0) {
          return 100;
      }
  
      if (this.physicalAttacker) {
          attackingStat = pokemon.attack;
          defendingStat = opponent.defense;
      } else { 
          attackingStat = pokemon.spattack;
          defendingStat = opponent.spdefense;
      }
  
      AD = (attackingStat/defendingStat);
      if (checkSTAB(attack)) {
          if (pokemon.set.getAbility().getName() == "Adaptability") {
              STAB = 2;
          } else STAB = 1.5;
      }
  
  
      return result;
  }
  
  public double offensivePotential(Pokemon pokemon, Pokemon opponent) {
      double result = 0.0;
  
      Attack[] moveSet = pokemon.set.getMoveSet();
  
      for (int i = 0; i < moveSet.length; i++) {
          result += calculateDamage(pokemon, opponent, moveSet[i]);
      }
  
      return result;
  }

  public double gmaxPoints(Pokemon pokemon) {
    if (pokemon.gmax == true) {
        return 100.0;
    } else return 0;
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

public double calculatePoints(Pokemon opponent) {
  double result = 0;



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
