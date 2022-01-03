package es.jaime.gateway.orders.orders._shared.domain;

import es.jaime.gateway._shared.domain.valueObject.StringValueObject;
import org.json.JSONObject;

import java.util.Map;

public class OrderBody extends StringValueObject {
    protected Map<String, Object> valueToMap;

    public OrderBody(String value) {
        super(value);
    }

    public OrderBody () {
        super(null);
    }

    public Object get(String key){
        this.convertValueToHashmapIfNotConverted();

        return toMap().get(key);
    }

    public String getString(String key){
        this.convertValueToHashmapIfNotConverted();

        return String.valueOf((toMap()).get(key));
    }

    public Integer getInteger(String key){
        this.convertValueToHashmapIfNotConverted();

        return Integer.parseInt(String.valueOf(valueToMap.get(key)));
    }

    public Double getDouble(String key){
        this.convertValueToHashmapIfNotConverted();

        return Double.parseDouble(String.valueOf(valueToMap.get(key)));
    }

    public Map<String, Object> toMap(){
        convertValueToHashmapIfNotConverted();

        return this.valueToMap;
    }

    private void convertValueToHashmapIfNotConverted(){
        if(valueToMap == null || valueToMap.size() == 0){
            this.valueToMap = (new JSONObject(value())).toMap();
        }
    }

    public static OrderBody of(Map<String, Object> value){
        return new OrderBody((new JSONObject(value)).toString());
    }
}
