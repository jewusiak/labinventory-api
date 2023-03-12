package pl.jewusiak.inwentarzeeapi.models;

public enum ViewableAttachmentType {
    NV(0), PDF(1), DOC(2), SPREADSHEET(3), IMAGE(4);

    public static ViewableAttachmentType classifyFile(String extension){
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private final int _selected;

    ViewableAttachmentType(int type){
        _selected=type;
    }

    public int getOrdinalValue(){
        return _selected;
    }
}
