import org.apache.commons.lang3.StringUtils;
 
public class Attack {
 
    private String name, effect;
    private int power, accuracy;
    private boolean utility;
    private Type type;
 
    public Attack() {
    }
 
    public void setNamePowerAccuracy(String line) {
        String name = StringUtils.substringBetween(line, "/move/", "\"");
        this.name = name;
 
        String data[] = StringUtils.substringsBetween(line, "cell-num\">", "</td>");
        if (data[0].equals("")) {
            this.power = 0;
        } else this.power = Integer.valueOf(data[0]);
        if (data[1].equals("") || data[1].equals("&infin")) {
            this.accuracy = 100;
        } else this.accuracy = Integer.valueOf(data[1]);
    }
 
    public void setEffect(String line) {
        String effect = StringUtils.substringBetween(line, "cell-long-text\">", "</td>");
        this.effect = effect;
    }
 
    public void setUtility() {
        if (this.power == 0) {
            this.utility = true;
        }
    }

    public String getName() {
        return this.name;
    }

    public int getPower() {
        return this.power;
    }

    public int getAccuracy() {
        return this.accuracy;
    }

    public Type getType() {
        return this.type;
    }
 
}