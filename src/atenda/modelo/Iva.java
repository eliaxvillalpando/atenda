package atenda.modelo;


public enum Iva {
    
    TIPO_REDUCIDO,TIPO_SUPERREDUCIDO,TIPO_XERAL;
    
    
    public static Iva fromString(String ivaValue) {
        switch (ivaValue) {
            case "tipo_reducido":
                return TIPO_REDUCIDO;
            case "tipo_xeral":
                return TIPO_XERAL;
            case "tipo_superreducido":
                return TIPO_SUPERREDUCIDO;
            default:
                return TIPO_XERAL;
        }
    }
    
    public static Iva fromDouble(double ivaValue) {
    if (ivaValue == 10.0 / 100.0) {
        return TIPO_REDUCIDO;
    } else if (ivaValue == 21.0 / 100.0) {
        return TIPO_XERAL;
    } else if (ivaValue == 4.0 / 100.0) {
        return TIPO_SUPERREDUCIDO;
    } else {
        return TIPO_XERAL;
    }
}

    
}
