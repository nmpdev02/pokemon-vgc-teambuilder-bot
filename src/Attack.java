import org.apache.commons.lang3.StringUtils;
 
public class Attack {
 
    private String name, effect;
    private int power, accuracy;
    private boolean utility;
    private Type type;
 
    public Attack() {}
 
    public void setDetails(String line) {
        String name = StringUtils.substringBetween(line, "/move/", "\"");
        this.name = name; // SET NAME OF ATTACK

        String typeName = StringUtils.substringBetween(line, "type-icon type-", "\" href=\"");
        if (typeName != null) {
        StringBuilder builder = new StringBuilder(typeName);
        String upper = "";
        upper += Character.toUpperCase(builder.charAt(0));
        builder.replace(0, 1, upper);
        typeName = builder.toString();
        this.type = new Type(typeName); // SET TYPE OF ATTACK
        } else this.type = null;
 
        String data[] = StringUtils.substringsBetween(line, "cell-num\">", "</td>");
        if (data[0].equals("")) {
            this.power = 0; 
        } else this.power = Integer.valueOf(data[0]); // SET POWER OF ATTACK
        if (data[1].equals("") || data[1].equals("&infin")) {
            this.accuracy = 100; 
        } else {
            this.accuracy = Integer.valueOf(data[1]); // SET ACCURACY OF ATTACK
            double acc = this.accuracy / 100;
            this.power *= acc;
        }
        
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