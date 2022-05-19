import org.apache.commons.lang3.StringUtils;
 
public class Attack {
 
    private String name, effect;
    private int power, accuracy;
    private boolean utility;
 
    public Attack() {
    }
 
    public void setNamePowerAccuracy(String line) {
        String name[] = StringUtils.substringBetween(line, "/move/", "\"");
        this.name = name[0];
 
        String[] data = StringUtils.substringBetween(line, "cell-num\">", "</td>");
        this.power = Integer.valueOf(data[0]);
        this.accuracy = Integer.valueOf(data[1]);
 
    }
 
    public void setEffect(String line) {
        String effect[] = StringUtils.substringBetween(line, "cell-long-text\">", "</td>");
        this.effect = effect[0];
    }
 
    public void setUtility() {
        if (this.power == 0) {
            this.utility = true;
        }
    }
 
}