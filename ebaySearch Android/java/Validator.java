package usc.csci571.ebays;

public class Validator {
    public boolean keyWordRequired=false;
    public boolean priceOk=false;

    private String keyword;
    private double price1;
    private double price2;

    public Validator(InputData formData){
        keyWordRequired=checkKeyWord(formData.keyWord);
        priceOk=checkPrice(formData.price1,formData.price2);
    }

    private boolean checkKeyWord(String keyword){
        if(keyword.length()==0)
            return false;
        return true;
    }

    private boolean checkPrice(double price1,double price2){
        if(price1<0||price2<0||price1>price2)
            return false;
        return true;
    }

    public boolean isKeyWordRequired() {
        return keyWordRequired;
    }

    public boolean isPriceOk() {
        return priceOk;
    }


}
