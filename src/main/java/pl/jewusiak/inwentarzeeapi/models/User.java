package pl.jewusiak.inwentarzeeapi.models;

public class User {
    private long id;
    private String displayName;
    private Attachment profilePhoto;
    private String email;
    private byte[] passwordHash;
    private byte[] passwordSalt;
}
