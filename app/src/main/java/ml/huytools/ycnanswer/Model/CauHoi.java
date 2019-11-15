package ml.huytools.ycnanswer.Model;

import ml.huytools.ycnanswer.Common.Model;

public class CauHoi extends Model {

    private String a;
    private int c;
    private int d;

    public CauHoi() {
        super();
        a ="1";
        c=2;
        d=10;
    }

    @Override
    protected Object GetAttributeValue(String attr) throws NoSuchFieldException, IllegalAccessException {
        return this.getClass().getDeclaredField(attr).get(this);
    }

    @Override
    public String[] GetAttributeExcept() {
        return new String[]{"c"};
    }
}
