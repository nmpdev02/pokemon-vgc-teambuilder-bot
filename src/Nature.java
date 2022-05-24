public class Nature {
 
	private String name;
	private double[] modifiers = {1, 1, 1, 1, 1, 1};

    public Nature() {}
 
	public Nature(String name) {
        this.name = name;
        applyModifiers(name);
    }
 
    public void applyModifiers(String name) {
        switch (name) {
            case ("Lonely"): 
                boostDrop(1, 2);
                break;
            case ("Adamant"):
                boostDrop(1, 3);
                break;
            case ("Naughty"):
                boostDrop(1, 4);
                break;
            case ("Brave"):
                boostDrop(1, 5);
                break;
            case ("Bold"):
                boostDrop(2, 1);
                break;
            case ("Impish"):
                boostDrop(2, 3);
                break;
            case ("Relaxed"):
                boostDrop(2, 5);
                break;
            case ("Modest"):
                boostDrop(3, 1);
                break;
            case ("Mild"):
                boostDrop(3, 2);
                break;
            case ("Rash"):
                boostDrop(3, 4);
                break;
            case ("Quiet"):
                boostDrop(3, 5);
                break;
            case ("Calm"):
                boostDrop(4, 3);
                break;
            case ("Careful"):
                boostDrop(4, 3);
                break;
            case ("Sassy"):
                boostDrop(4, 3);
                break;
            case ("Timid"):
                boostDrop(5, 1);
                break;
            case ("Hasty"):
                boostDrop(5, 2);
                break;
            case ("Jolly"):
                boostDrop(5, 3);
                break;
            case ("Naive"):
                boostDrop(5, 4);
                break;
            default:
                break;
        }
    }
 
    public void boostDrop(int boost, int drop) {
        modifiers[boost] = 1.1;
        modifiers[drop] = 0.9;
    }
 
 
	public String getName() { 
		return this.name;
	}
 
    public double[] getModifiers() {
        return this.modifiers;
    }
}