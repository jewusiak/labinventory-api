package pl.jewusiak.inwentarzeeapi.models;

public enum ViewableAttachmentType {
    NV, PDF, JPEG, PNG;

    public static ViewableAttachmentType classifyFile(String extension) {
        return switch (extension) {
            case "jpeg", "jpg" -> JPEG;
            case "png" -> PNG;
            case "pdf" -> PDF;
            default -> NV;
        };
    }

    public static boolean isImage(ViewableAttachmentType vat) {
        return vat == JPEG || vat == PNG;
    }
}
