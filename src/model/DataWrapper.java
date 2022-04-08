package model;

import java.io.Serializable;

public class DataWrapper implements Serializable {
    private int flag;
    private Object data;

    public DataWrapper(int flag, Object data) {
        this.flag = flag;
        this.data = data;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
