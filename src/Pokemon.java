import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Pokemon {

    private String name, type1, type2, ability1, ability2, hiddenAbility;
    private int hp, attack, defense, spattack, spdefense, speed, rbst;
    private double usage;
    private boolean physicalAttacker, restricted;

    public Pokemon(String name, double usage) {
      this.name = name;
      this.usage = usage;
    }

    public void setupPokemon(String[] line) throws Exception {

      // Determine if the pokemon is a restricted legendary
      if (line[6].contains("Legendary") && !(line[6].contains("Sub"))) {
        this.restricted = true;
      } else this.restricted = false;

      // Set primary and secondary types
      this.type1 = line[9];
      this.type2 = line[10];

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

      if (this.type1 != null) {
      if (this.type2.equals("NULL")) {
        result += ("Type: " + this.type1 + " ");
      } else {
        result += ("Types: " + this.type1 + ", " + this.type2 + " ");
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
  
  }
