package bluoh.feed.model;

import org.hibernate.validator.constraints.NotBlank;

public class Media {

    private String type;
    @NotBlank
    private String url;
    @NotBlank
    private String source;

    public Media(String type, String url, String source) {
        this.type = type;
        this.url = url;
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
