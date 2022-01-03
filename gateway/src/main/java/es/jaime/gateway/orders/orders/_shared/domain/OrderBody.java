package es.jaime.gateway.orders.orders._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;
import org.json.JSONObject;

import java.util.Map;

public class OrderBody extends StringValueObject {
    public OrderBody(String value) {
        super(value);
    }

    public OrderBody () {
        super(null);
    }

    public String getString(String key){
        return String.valueOf((toMap()).get(key));
    }

    public Object get(String key){
        return toMap().get(key);
    }

    public Integer getInteger(String key){
        return Integer.parseInt(String.valueOf((toMap()).get(key)));
    }

    public Double getDouble(String key){
        return Double.parseDouble(String.valueOf((toMap()).get(key)));
    }

    public Map<String, Object> toMap(){
        return (new JSONObject(value())).toMap();
    }

    public static OrderBody of(Map<String, Object> value){
        return new OrderBody((new JSONObject(value)).toString());
    }
}
