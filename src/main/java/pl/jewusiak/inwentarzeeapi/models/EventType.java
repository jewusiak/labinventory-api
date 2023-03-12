package pl.jewusiak.inwentarzeeapi.models;


public enum EventType {
    COMMENT(0), CALIBRATION(1), REPAIR(2), BREAKDOWN(3);

    private final int _selected;

    EventType(int enumNo) {
        _selected=enumNo;
    }

    public int getOrdinalValue(){
        return _selected;
    }
}
