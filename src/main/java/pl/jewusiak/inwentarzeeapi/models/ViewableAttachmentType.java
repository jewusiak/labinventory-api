package pl.jewusiak.inwentarzeeapi.models;


public enum ViewableAttachmentType {
    NV, PDF, IMAGE;

    public static ViewableAttachmentType classifyFile(String extension) {
        return switch (extension) {
            case "jpeg", "png", "bmp", "jpg" -> IMAGE;
            case "pdf" -> PDF;
            default -> NV;
        };
    }
}
