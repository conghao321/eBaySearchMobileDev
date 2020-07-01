public class InputData {
    protected String keyWord;
    protected double price1;
    protected double price2;
    protected boolean newItem;
    protected boolean used;
    protected boolean unspec;
    public String sortBy;

    public  InputData(){
        this.sortBy="Best Match";
    }

    public InputData(String keyWord, int minPrice, int maxPrice, boolean newItem, boolean used, boolean unspec, String sortBy) {
        this.keyWord = keyWord;
        this.price1 = minPrice;
        this.price2 = maxPrice;
        this.newItem = newItem;
        this.used = used;
        this.unspec = unspec;
        this.sortBy = sortBy;
    }


    public String getKeyWord() {
        return keyWord;
    }


    public boolean isNewItem() {
        return newItem;
    }

    public boolean isUsed() {
        return used;
    }

    public boolean isUnspec() {
        return unspec;
    }

    public String getSortBy() {
        return sortBy;
    }

    public double getPrice1() {
        return price1;
    }

    public double getPrice2() {
        return price2;
    }

    @Override
    public String toString() {
        return "InputData{" +
                "keyWord='" + keyWord + '\'' +
                ", price1=" + price1 +
                ", price2=" + price2 +
                ", newItem=" + newItem +
                ", used=" + used +
                ", unspec=" + unspec +
                ", sortBy='" + sortBy + '\'' +
                '}';
    }
}
