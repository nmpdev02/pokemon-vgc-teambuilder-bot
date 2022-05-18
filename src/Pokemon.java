public class Pokemon {

    private String name, type1, type2, ability;
    private int hp, attack, defense, spattack, spdef, speed, rbst, isRestricted;
    private double usage;
    private boolean physicalAttacker;

    public Pokemon(String name, double usage) {
      this.name = name;
      this.usage = usage;
    }

    public String getName() {
      return this.name;
    }

    public double getUsage() {
      return this.usage;
    }
  
    public void setPhysicalAttacker() {
  
      switch (this.name) {
  
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
  
  }
