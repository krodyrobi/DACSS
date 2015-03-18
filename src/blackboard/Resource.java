package blackboard;

public class Resource {
    protected boolean idle;
    protected boolean reserved;

    protected String data;

    public Resource(String data) {
        idle = true;
        reserved = false;

        this.data = data;
    }



    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void unreserve() {
        reserved = false;
    }

    public void reserve() {
        reserved = true;
    }

    public void toIdle() {
        idle = true;
    }

    public void toWorking() {
        idle = false;
    }

    public boolean isIdle() {
        return idle;
    }

    public String toString() {
        return data + "(" + reserved + ":" +idle + ")";
    }
}
