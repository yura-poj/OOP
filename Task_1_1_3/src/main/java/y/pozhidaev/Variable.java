package y.pozhidaev;

public class Variable extends Expression {
    private String name;
    private int value;

    public Variable(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getValue() {
        return value;
    }
    public void setValue(String str) {
        String[] values = str.replace(" ", "").split(";");
        for(String value : values){
            String[] results = value.split("=");
            if(results.length == 2){
                if(results[0].equals(name)){
                    this.value = Integer.parseInt(results[1]);
                }
            } else {
                System.out.println("Invalid variable value");
            }
        }
    }

    @Override
    public double evaluate(String str) {
        setValue(str);
        return value;
    }

    @Override
    public Expression derivative(String str) {
        if(name.equals(str)){
            return new Number(1);
        } else {
            return new Number(0);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
